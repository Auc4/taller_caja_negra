import pytest

from presupuesto_analisis import calcular_presupuesto


# 1. Caso feliz
def test_calculo_normal():
    intereses, total, cuota = calcular_presupuesto(1000.0, 2, 2)

    assert intereses == pytest.approx(40.0)
    assert total == pytest.approx(1040.0)
    assert cuota == pytest.approx(520.0)


# 2. Valor limite
def test_valores_limite():
    intereses, total, cuota = calcular_presupuesto(0.0, 1, 0)

    assert intereses == pytest.approx(0.0)
    assert total == pytest.approx(0.0)
    assert cuota == pytest.approx(0.0)


# 3. Division por cero
def test_division_por_cero():
    with pytest.raises(ValueError):
        calcular_presupuesto(1000.0, 0, 2)


# 4. Inputs negativos
def test_inputs_negativos():
    with pytest.raises(ValueError):
        calcular_presupuesto(1000.0, 2, -1)