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

## Desafío Autónomo con CI y Pytest

### 1.  Software Testing Life Cycle 
Proceso compuesto por 6 fases orientadas a garantizar la calidad del software:
1. **Análisis de Requisitos:** Revisión de las especificaciones funcionales para determinar qué aspectos del software deben ser probados.
2. **Planificación de Pruebas:** Es la fase más crucial del ciclo de pruebas, aquí se define una estrategia de pruebas, objetivos, estimación de recursos, selección de herramientas, entre otras aproximaciones para el ciclo.
3. **Diseño de Casos de Prueba:** Elaboración de escenarios y datos de prueba aplicando técnicas como Partición de Equivalencia y Análisis de Valores Límite.
4. **Configuración del Entorno de Pruebas:** Fase de preparación del hardware, software y entorno donde se correrán las pruebas.
5. **Ejecución de Pruebas:** Ejecución automática o manual de los scripts de prueba reportando defectos encontrados.
6. **Cierre del Ciclo de Pruebas:** Evaluación de métricas, reporte de cobertura, análisis de lecciones aprendidas y cierre formal del ciclo.

### SDLC vs. STLC
* **SDLC (Software Development Life Cycle):** Se enfoca en el ciclo completo de creación y mantenimiento del producto (análisis, arquitectura, codificación, despliegue y soporte).
* **STLC (Software Testing Life Cycle):** Se enfoca exclusivamente en la verificación y validación del producto. Corre de forma paralela al SDLC en cada una de sus fases para detectar desviaciones de calidad de manera continua.

### Shift-Left Testing
Principio metodológico que traslada las actividades de prueba lo más temprano posible dentro del ciclo de desarrollo. Al integrar pruebas unitarias automatizadas en cada `push` mediante GitHub Actions, los defectos se detectan y corrigen de inmediato, reduciendo drásticamente los costos de refactorización y evitando que errores críticos lleguen a producción.

---

## 2. Criterios de Calidad

| Tipo de Criterio | Definición | Aplicación en el Proyecto |
| :--- | :--- | :--- |
| **Entry Criteria** (Criterios de Entrada) | Condiciones previas requeridas para iniciar formalmente la fase de ejecución de pruebas. | 1. Runner de GitHub Actions aprovisionado correctamente con Ubuntu y Python 3.11.<br>2. Dependencias (`pip`, `pytest`) instaladas sin errores de resolución.<br>3. Código libre de errores de sintaxis y módulos importables. |
| **Exit Criteria** (Criterios de Salida) | Condiciones que determinan que la fase de pruebas ha finalizado satisfactoriamente (Definition of Done). | 1. 100% de los casos de prueba ejecutados y reportando estado `PASSED`.<br>2. Cero excepciones no controladas en tiempo de ejecución.<br>3. Código de salida `0` en el workflow de GitHub Actions; cualquier fallo bloquea la integración. |