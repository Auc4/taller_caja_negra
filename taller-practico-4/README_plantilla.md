# Taller Práctico 4: PICT + pytest (PagoYa)

**Integrantes:** Sebastián Aucapiña, Bryan Montaguano

## Resumen del modelo
Se modeló la pasarela de pagos PagoYa con 7 parámetros de entrada (Dispositivo, País, Método de pago, Moneda, Envío, Cupón y Monto). Se implementaron seis restricciones de negocio (R1 a R6) que aseguran la correspondencia de la moneda por país, limitan el pago contraentrega por monto y región, restringen billeteras digitales a dispositivos móviles, excluyen envíos express internacionales, fijan montos mínimos para cupones y restringen montos altos a métodos bancarios formales.

## Resultados
| Medida | Valor |
|---|---|
| Combinaciones exhaustivas sin restricciones | 5184 |
| Combinaciones válidas (/o:max) | 1046 |
| Pruebas con t=2 | 22 |
| Pruebas con t=3 | 79 |
| Fallos antes de corregir (t=2 / t=3) | 2 en t=2 / 6 en t=3 (8 en total) |

## El error que encontramos
El error se dispara cuando coinciden el cupón "Bienvenida" y el tipo de envío "Express". Provoca que el descuento del 5% se anule forzadamente a 0.00, haciendo que el sistema cobre el valor total sin aplicar la promoción prometida. Se corrigió eliminando las líneas que sobreescribían la variable `descuento` bajo esa condición específica en `pasarela.py`.

## Cómo ejecutar
    pict checkout.pict /o:2 > checkout_t2.tsv
    pict checkout.pict /o:3 > checkout_t3.tsv
    pytest