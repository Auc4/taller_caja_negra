# Taller de Pruebas de Caja Negra

## Datos del trabajo

| Dato | Información |
|---|---|
| **Universidad** | Universidad Internacional del Ecuador |
| **Carrera** | Sistemas de la Información |
| **Materia** | Diseño de Pruebas, Control de Calidad y Mantenimiento |
| **Profesor** | Pablo Javier Robayo Castellanos |
| **Integrantes** | Sebastián Aucapiña y Bryan Montaguano |
| **Fecha de entrega** | 07/09/2026 |
| **Repositorio** | [taller_caja_negra](https://github.com/Auc4/taller_caja_negra) |

---

## Actividad 1 - Mapa conceptual

Este es el mapa conceptual que realizamos sobre **QA, QC, Testing**, los **7 principios de ISTQB** y la relación **Error → Defecto → Fallo**.

![Mapa conceptual](mapa_conceptual.png)

También dejamos el archivo original del mapa por si se necesita revisar:

[Ver mapa conceptual en Google Drive](https://drive.google.com/file/d/10zU0H-B_1V8JXdKSbsW8ImlpdFSi0mMe/view?usp=sharing)

---

## Actividad 2 - Código base

Guardamos el archivo `presupuesto_analisis.py` y comprobamos que el programa inicia y permite ingresar los datos.

En esta parte todavía no corregimos el código, porque primero debíamos diseñar y ejecutar los casos de prueba.

---

## Actividad 3 - Diseño de los casos de prueba

Antes de ejecutar el programa planteamos tres casos de prueba usando **partición de equivalencia** y **valores límite**.

La tabla de abajo ya incluye las columnas **Real** y **Estado** porque fueron completadas después, durante la Actividad 4.

| ID | Descripción | Precondición | Entrada | Esperado | Real | Estado |
|---|---|---|---|---|---|---|
| **CP-01** | Probar el programa con datos normales | Programa iniciado y listo para recibir datos | `presupuesto=1000`, `socios=2`, `meses=2` | El programa debería calcular `$40.00` de intereses, un total de `$1040.00` y una cuota de `$520.00` por socio. | El programa calculó `$80.00` de intereses, un total de `$1080.00` y una cuota de `$540.00` por socio. | **Failed** |
| **CP-02** | Probar el límite inválido de socios | Programa iniciado; presupuesto y meses válidos | `presupuesto=1000`, `socios=0`, `meses=2` | El programa debería detectar que no puede trabajar con `0` socios y mostrar un mensaje controlado. | El programa se cerró con `ZeroDivisionError: float division by zero`. | **Failed** |
| **CP-03** | Probar una cantidad negativa de meses | Programa iniciado; presupuesto y socios válidos | `presupuesto=1000`, `socios=2`, `meses=-1` | El programa debería rechazar el valor negativo y mostrar un mensaje indicando que los meses no son válidos. | El programa aceptó `-1` y realizó el cálculo: intereses `$20.00`, total `$1020.00` y cuota `$510.00`. | **Failed** |

### Técnicas usadas

- **CP-01:** partición de equivalencia, usando datos que consideramos normales y válidos.
- **CP-02:** valor límite, usando `socios=0`, justo debajo del mínimo válido de `1`.
- **CP-03:** partición de equivalencia con un valor inválido. Para este caso asumimos que una inversión no debería tener una cantidad negativa de meses.

---

## Actividad 4 - Ejecución y localización de defectos

Después de ejecutar los tres casos comparamos lo que esperábamos con lo que realmente hizo el programa. Los tres casos terminaron en **Failed**, pero por razones diferentes.

### CP-01 - Cálculo incorrecto de intereses

**Fallo que vimos:**  
Con `presupuesto=1000`, `socios=2` y `meses=2`, esperábamos `$40.00` de intereses, pero el programa mostró `$80.00`.

**Defecto encontrado:**  
El cálculo está elevando los meses al cuadrado:

```python
intereses = presupuesto * tasa_interes_mensual * (meses ** 2)
```

En el archivo copiado con el mismo formato del PDF, esta instrucción queda en la **línea 9**.

Al usar `meses ** 2`, para 2 meses el programa usa `4` en el cálculo. Por eso el interés termina siendo mayor al esperado.

---

### CP-02 - División entre cero

**Fallo que vimos:**  
Al ingresar `socios=0`, el programa se detuvo y apareció:

```text
ZeroDivisionError: float division by zero
```

**Defecto encontrado:**  
El programa divide el total entre la cantidad de socios sin comprobar antes si el valor es cero:

```python
cuota_por_socio = total / socios
```

En el archivo copiado con el mismo formato del PDF, esta instrucción queda en la **línea 12**.

Antes de hacer esta división debería existir una validación para evitar que `socios` sea `0` o un valor inválido.

---

### CP-03 - Se aceptan meses negativos

**Fallo que vimos:**  
Ingresamos `meses=-1` y el programa lo aceptó como si fuera un valor normal. Incluso mostró un interés positivo de `$20.00`.

**Defecto encontrado:**  
El valor de `meses` se recibe directamente:

```python
meses = int(input("Ingrese los meses de inversión: "))
```

En el archivo copiado con el mismo formato del PDF, esta instrucción queda en la **línea 5**.

Después de recibir este dato no existe una validación que compruebe que los meses sean mayores que cero. Además, como más adelante los meses se elevan al cuadrado, `-1` termina convirtiéndose en un valor positivo para el cálculo.

---

## Resumen de la Actividad 4

| Caso | Fallo encontrado | Defecto relacionado |
|---|---|---|
| **CP-01** | Los intereses calculados son mayores a los esperados | Se usa `(meses ** 2)` en el cálculo de intereses |
| **CP-02** | El programa se cierra cuando hay `0` socios | Se divide entre `socios` sin validar que sea diferente de cero |
| **CP-03** | El programa acepta meses negativos | No se valida el valor de `meses` después de ingresarlo |

Hasta esta actividad únicamente identificamos y documentamos los fallos y sus causas. No modificamos todavía el código original.
