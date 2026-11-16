# Tabla de casos de prueba QA

| ID | Caso de prueba | Clasificación | Justificación |
|---|---|---|---|
| C01 | Iniciar sesión con credenciales válidas | No ejecutar | Era Smoke. Ruta crítica: sin login no hay app. |
| C02 | Abrir el catálogo y ver la lista de productos | No ejecutar | Era Smoke. Ruta crítica del negocio. |
| C03 | Completar una compra de punta a punta (agregar y pagar) | **Smoke** | Correcto. El flujo que produce ingresos decide si el build sirve. |
| C04 | Cambiar la cantidad de un producto y ver el total actualizado | **Sanity** | Correcto. Es exactamente el bug #482. |
| C05 | Eliminar un producto del carrito y ver el total recalculado | **Sanity** | Correcto. Vecindad inmediata del fix: también llama a `recalcularTotal()`. |
| C06 | Aplicar un cupón del 10% sobre el total del carrito | **Regresión selectiva** | Correcto. `CuponService` usa `recalcularTotal()`. |
| C07 | El resumen de pago muestra el mismo total que el carrito | **Regresión selectiva** | Correcto. `ResumenPagoViewModel` usa `recalcularTotal()`. |
| C08 | El carrito conserva productos y total al cerrar y abrir la app | **Regresión selectiva** | Correcto. `CarritoRepository` persiste el total calculado. |
| C09 | Cambiar la foto de perfil | No ejecutar | Correcto. Perfil no depende del cambio. |
| C10 | Recibir una notificación push de promoción | No ejecutar | Correcto. Notificaciones no depende del cambio. |
| C11 | Cambiar el idioma de la app | No ejecutar | Correcto. Configuración no depende del cambio. |

## Resumen

| Clasificación | Cantidad | Casos |
|---|---:|---|
| Smoke | 1 | C03 |
| Sanity | 2 | C04, C05 |
| Regresión selectiva | 3 | C06, C07, C08 |
| No ejecutar | 5 | C01, C02, C09, C10, C11 |
| **Total** | **11** | **C01–C11** |

**Resultado:** 6 casos seleccionados para ejecución y 5 excluidos.
