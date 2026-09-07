from presupuesto_analisis import calcular_presupuesto

def test_calculo_normal():
    intereses, total, cuota = calcular_presupuesto(
        1000.0,
        2,
        2
    )

    # 1000 * 0.02 * (2**2) = 80.0
    assert intereses == 80.0
    assert total == 1080.0
    assert cuota == 540.0