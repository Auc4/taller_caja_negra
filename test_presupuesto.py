from presupuesto_analisis import calcular_presupuesto

# 1. CASO FELIZ (Happy Path)
def test_calculo_normal():
    intereses, total, cuota = calcular_presupuesto(1000.0, 2, 2)
    # 1000 * 0.02 * (2**2) = 80.0
    assert intereses == 80.0
    assert total == 1080.0
    assert cuota == 540.0


# 2. VALORES LÍMITE
def test_valores_limite():
    intereses, total, cuota = calcular_presupuesto(0.0, 5, 12)
    assert intereses == 0.0
    assert total == 0.0
    assert cuota == 0.0


# 3. DIVISIÓN POR CERO (Excepción al ingresar 0 meses)
def test_division_por_cero():
    with pytest.raises(ZeroDivisionError):
        calcular_presupuesto(1000.0, 2, 0)


# 4. INPUTS NEGATIVOS (Manejo de entradas inválidas)
def test_inputs_negativos():
    # Si la función lanza ValueError para montos negativos:
    with pytest.raises(ValueError):
        calcular_presupuesto(-500.0, 2, 12)