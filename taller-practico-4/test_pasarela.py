import csv
from pathlib import Path

import pytest

from pasarela import procesar_pago

ARCHIVOS = ["checkout_t2.tsv", "checkout_t3.tsv"]

# Oraculo: valores esperados escritos AQUI, independientes del sistema bajo prueba
ENVIO_ESPERADO = {"Estandar": 3, "Express": 8, "Retiro": 0}
DESCUENTO_ESPERADO = {"Ninguno": 0.00, "Bienvenida": 0.05, "Estudiante": 0.10}
MONEDA_ESPERADA = {"Ecuador": "USD", "Colombia": "COP", "Peru": "PEN", "Otro": "USD"}


def cargar_casos(nombre_archivo):
    """Lee un archivo .tsv generado por PICT y devuelve una lista de diccionarios."""
    ruta = Path(__file__).parent / nombre_archivo
    with open(ruta, newline="", encoding="utf-8-sig") as f:
        return list(csv.DictReader(f, delimiter="\t"))


def todos_los_casos():
    casos = []
    for archivo in ARCHIVOS:
        for i, fila in enumerate(cargar_casos(archivo), start=1):
            casos.append(pytest.param(fila, id=f"{archivo[9:11]}-caso{i:02d}"))
    return casos


@pytest.mark.parametrize("caso", todos_los_casos())
def test_pago_valido_es_aprobado(caso):
    resultado = procesar_pago(**{k.lower(): v for k, v in caso.items()})
    assert resultado["estado"] == "APROBADO"


@pytest.mark.parametrize("caso", todos_los_casos())
def test_total_correcto(caso):
    resultado = procesar_pago(**{k.lower(): v for k, v in caso.items()})
    esperado = round(
        float(caso["Monto"]) * (1 - DESCUENTO_ESPERADO[caso["Cupon"]])
        + ENVIO_ESPERADO[caso["Envio"]],
        2,
    )
    assert resultado["total"] == esperado


@pytest.mark.parametrize("caso", todos_los_casos())
def test_moneda_coherente_con_el_pais(caso):
    assert caso["Moneda"] == MONEDA_ESPERADA[caso["Pais"]]

def test_regla_negativa_billetera_laptop():
    """Verifica que una combinacion prohibida por R3 sea rechazada."""
    resultado = procesar_pago(
        dispositivo="Laptop",
        pais="Ecuador",
        metodo="Billetera",
        moneda="USD",
        envio="Estandar",
        cupon="Ninguno",
        monto=50,
    )
    assert resultado["estado"] == "RECHAZADO"
    assert resultado["total"] == 0.0