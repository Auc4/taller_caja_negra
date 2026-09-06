# taller_caja_negra

Repositorio del taller de **Pruebas de Caja Negra** realizado por **Sebastián Aucapiña** y **Bryan Montaguano**.

## Estructura del proyecto

```bash
|- presupuesto_analisis.py # Código entregado para realizar las pruebas.
|- casos_prueba.md # Imagen del mapa conceptual, casos de prueba y resultados de las actividades.
|-- Media/
|-- Mapa_Conceptual.png # Mapa conceptual realizado para la Actividad 1.
```

---

## Cierre y validación conceptual

### Desafío lógico 1

**¿Puede existir un defecto en el código durante años sin llegar a causar un fallo?**

Si, un defecto puede estar presente en el código durante mucho tiempo y nadie lo notará debido a que no se presentan las condiciones necesarias para que se manifieste. 

Por ejemplo, el caso de *shellshock* con el ínterprete *bash* es un ejemplo de un defecto que se presentó durante varios años; esto se debe a una falta de control de límites dentro del programa. Permaneció oculto desde 1989 hasta 2014. 

El defecto se mantuvo durante mucho tiempo, pero el fallo solo aparece cuando el programa se ejecuta bajo una condición que lo activa.


### Desafío lógico 2

**Si el programa funciona correctamente, pero el cliente necesitaba un sistema de nóminas y no uno de presupuestos, ¿qué principio se estaría incumpliendo?**

Se estaría incumpliendo el principio de **falacia de ausencia de errores**.

Esto indica que, aunque el programa no presente fallos visibles y su funcionamiento sea correcto, no sirve como solución si no responde a la necesidad real del usuario. En este caso se construyó correctamente un sistema de presupuestos, pero el cliente necesitaba otra cosa.

