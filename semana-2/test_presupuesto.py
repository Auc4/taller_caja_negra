import pytest

from presupuesto_analisis import calcular_presupuesto


# 1. Caso feliz
def test_calculo_normal():
    intereses, total, cuota = calcular_presupuesto(1000.0, 2, 2)

    assert intereses == pytest.approx(40.0)
    assert total == pytest.approx(1040.0)
    assert cuota == pytest.approx(520.0)