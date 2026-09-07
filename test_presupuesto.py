from presupuesto import calcular_presupuesto

def test_calculo_normal():

    intereses, total, cuota = calcular_presupuesto(
        1000,
        2,
        2
    )

    assert intereses == 40
    assert total == 1040
    assert cuota == 520