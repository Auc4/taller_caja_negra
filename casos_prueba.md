# Taller Autónomo de Pruebas de Caja Negra

## Casos de prueba

### Datos generales

| Campo | Información |
|---|---|
| **Institución** | Universidad Internacional del Ecuador |
| **Carrera** | Sistemas de la Información |
| **Materia** | Diseño de Pruebas, Control de Calidad y Mantenimiento |
| **Profesor** | Pablo Javier Robayo Castellanos |
| **Integrantes** | Sebastián Aucapiña y Bryan Montaguano |
| **Fecha de entrega** | 07/09/2026 |
| **Repositorio** | [taller_caja_negra](https://github.com/Auc4/taller_caja_negra) |

---

## Actividad 1 - Investigación y Mapa Conceptual

El siguiente mapa conceptual relaciona **QA, QC y Testing**, los **7 principios de ISTQB** y la secuencia **Error → Defecto → Fallo**, utilizando explicaciones redactadas por el equipo.

![Mapa conceptual](mapa_conceptual.png)

**Enlace al archivo original del mapa conceptual (solo lectura):**  
[Ver mapa conceptual en Google Drive](https://drive.google.com/file/d/10zU0H-B_1V8JXdKSbsW8ImlpdFSi0mMe/view?usp=sharing)

---

## Actividad 2 - Recepción y Verificación del Código Base

El entregable correspondiente a esta actividad es el archivo `presupuesto_analisis.py`.

En esta etapa el código debe conservarse sin correcciones y sin realizar todavía un análisis interno línea por línea, ya que la localización de defectos corresponde a la Actividad 4.

---

## Actividad 3 - Diseño del Plan de Pruebas (Caja Negra Estática)

**Estado del plan:** Planeado.

Los casos se diseñan antes de ejecutar el programa, aplicando **partición de equivalencia** y **análisis de valores límite**. Las columnas **Real** y **Estado** se dejan vacías hasta la Actividad 4.

| ID | Descripción | Precondición | Entrada | Esperado | Real | Estado |
|---|---|---|---|---|---|---|
| **CP-01** | Procesamiento con datos válidos normales | Sistema iniciado y disponible para recibir datos | `presupuesto=1000`, `socios=2`, `meses=2` | El sistema debe procesar los datos sin errores. Tomando una tasa mensual del 2%, el interés esperado para 2 meses es `$40.00`, el total esperado es `$1040.00` y la cuota esperada por socio es `$520.00`. |  |  |
| **CP-02** | Validación del límite inválido para el número de socios | Sistema iniciado; presupuesto y meses con valores válidos | `presupuesto=1000`, `socios=0`, `meses=2` | El sistema debe detectar que no es posible calcular una cuota con `0` socios y mostrar un mensaje de error controlado, sin cerrarse abruptamente. |  |  |
| **CP-03** | Validación de una duración de inversión inválida | Sistema iniciado; presupuesto y número de socios con valores válidos | `presupuesto=1000`, `socios=2`, `meses=-1` | El sistema debe rechazar una cantidad negativa de meses y mostrar un mensaje de validación, sin realizar el cálculo. |  |  |

### Técnicas aplicadas

- **CP-01 — Partición de equivalencia:** representa una combinación de entradas válidas y permite comprobar el comportamiento normal del sistema.
- **CP-02 — Análisis de valores límite:** usa `socios=0`, valor inmediatamente inferior al mínimo válido de `1`.
- **CP-03 — Partición de equivalencia:** usa un valor negativo para representar una duración de inversión inválida.

> **Nota sobre CP-03:** el taller no establece explícitamente un rango permitido para `meses`. Este caso se diseña bajo el supuesto razonable de que la duración de una inversión no puede ser negativa.

### Reporte de avance

`Equipo — 3 casos de prueba diseñados.`

---

> **Pendiente para la Actividad 4:** completar las columnas **Real** y **Estado** después de ejecutar cada caso, e identificar las líneas de código defectuosas cuando corresponda.
