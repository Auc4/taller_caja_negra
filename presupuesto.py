def calcular_presupuesto(presupuesto, socios, meses):

    tasa_interes_mensual = 0.02

    intereses = presupuesto * tasa_interes_mensual * meses

    total = presupuesto + intereses

    cuota_por_socio = total / socios

    return intereses, total, cuota_por_socio