# taller_caja_negra

Repositorio del taller de **Pruebas de Caja Negra** realizado por **Sebastián Aucapiña** y **Bryan Montaguano**.

## Archivos del proyecto

- `presupuesto_analisis.py`: código base entregado para realizar las pruebas.
- `casos_prueba.md`: mapa conceptual, casos de prueba y resultados de las actividades.
- `mapa_conceptual.png`: mapa conceptual realizado para la Actividad 1.

---

## Cierre y validación conceptual

### Desafío lógico 1

**¿Puede existir un defecto en el código durante años sin llegar a causar un fallo?**

Sí. Un defecto puede quedarse en el código durante mucho tiempo sin que nadie lo note si nunca se ejecuta la parte del programa donde está o si no se presentan las condiciones necesarias para que se manifieste.

Por ejemplo, un error relacionado con un valor muy poco común podría permanecer oculto mientras los usuarios nunca ingresen ese dato. El defecto sigue existiendo, pero el fallo solo aparece cuando el programa se ejecuta bajo una condición que lo activa.

Por eso las pruebas pueden demostrar que existen defectos cuando los encuentran, pero no pueden garantizar que un programa esté completamente libre de ellos.

### Desafío lógico 2

**Si el programa funciona correctamente, pero el cliente necesitaba un sistema de nóminas y no uno de presupuestos, ¿qué principio se estaría incumpliendo?**

Se estaría incumpliendo la **falacia de ausencia de errores**.

Aunque el programa no tenga fallos técnicos y todos sus cálculos funcionen bien, no sirve como solución si no responde a la necesidad real del usuario. En este caso se construyó correctamente un sistema de presupuestos, pero el cliente necesitaba otra cosa.

Esto también muestra la diferencia entre hacer que un programa funcione bien y asegurarse de que realmente sea el producto que el usuario necesita.
