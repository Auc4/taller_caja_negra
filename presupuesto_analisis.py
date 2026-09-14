def calcular_presupuesto(presupuesto: float, socios: int, meses: int):
    if presupuesto < 0:
        raise ValueError("El presupuesto no puede ser negativo")

    if socios <= 0:
        raise ValueError("El numero de socios debe ser mayor que cero")

    if meses < 0:
        raise ValueError("Los meses no pueden ser negativos")

    tasa_interes_mensual = 0.02

    intereses = presupuesto * tasa_interes_mensual * meses
    total = presupuesto + intereses
    cuota_por_socio = total / socios

    return intereses, total, cuota_por_socio


def main():
    print("=== Sistema de Analisis de Presupuesto ===\n")

    presupuesto = float(input("Ingrese el presupuesto total: "))
    socios = int(input("Ingrese el numero de socios: "))
    meses = int(input("Ingrese los meses de inversion: "))

    intereses, total, cuota = calcular_presupuesto(
        presupuesto, socios, meses
    )

    print(f"\nPresupuesto inicial: ${presupuesto:.2f}")
    print(f"Intereses generados: ${intereses:.2f}")
    print(f"Total con intereses: ${total:.2f}")
    print(f"Cuota por socio ({socios} socios): ${cuota:.2f}")


if __name__ == "__main__":
    main()