# taller_caja_negra

Repositorio del taller de Pruebas de Caja Negra realizado por **Sebastián Aucapiña** y **Bryan Montaguano**.

## Estructura del proyecto

El repositorio está organizado por semanas, de acuerdo con las actividades desarrolladas durante el taller:

```bash
.
├── README.md
├── semana-1
│   ├── casos_prueba.md
│   ├── media
│   │   └── Mapa_Conceptual.png
│   └── presupuesto_analisis.py
├── semana-2
│   └── test_presupuesto.py
└── semana-3
    └── invstigacion_clases_04_05.md
```

### Semana 1

Contiene las actividades iniciales relacionadas con el análisis y diseño de pruebas de caja negra:

- `presupuesto_analisis.py`: código base utilizado para realizar las pruebas.
- `casos_prueba.md`: documentación de los casos de prueba, análisis y resultados de las actividades.
- `media/Mapa_Conceptual.png`: mapa conceptual elaborado para la Actividad 1.

### Semana 2

Contiene la implementación de las pruebas automatizadas:

- `test_presupuesto.py`: pruebas automatizadas desarrolladas utilizando Pytest para validar el comportamiento del código de presupuestos.

### Semana 3

Directorio destinado a las actividades correspondientes a la tercera semana del taller.

## Cierre y validación conceptual

### Desafío lógico 1

> ¿Puede existir un defecto en el código durante años sin llegar a causar un fallo?

Sí, un defecto puede estar presente en el código durante mucho tiempo sin ser detectado debido a que no se presentan las condiciones necesarias para que se manifieste.

Por ejemplo, el caso de **Shellshock**, relacionado con el intérprete Bash, es un ejemplo de un defecto que permaneció durante varios años sin ser descubierto. El problema estaba relacionado con el procesamiento de determinadas variables de entorno y fue descubierto públicamente en 2014, aunque el código vulnerable llevaba muchos años presente.

El defecto puede mantenerse oculto durante mucho tiempo, mientras que el fallo únicamente aparece cuando el programa se ejecuta bajo una condición específica que activa dicho defecto.

### Desafío lógico 2

> Si el programa funciona correctamente, pero el cliente necesitaba un sistema de nóminas y no uno de presupuestos, ¿qué principio se estaría incumpliendo?

Se estaría incumpliendo el principio de la **falacia de ausencia de errores**.

Este principio indica que un software puede funcionar correctamente y no presentar errores visibles, pero aun así resultar inútil si no satisface las necesidades reales del usuario.

En este caso, aunque el sistema de presupuestos funcione correctamente y las pruebas no encuentren defectos, el producto no cumple con el requerimiento principal del cliente, ya que este necesitaba un sistema de nóminas.

## Desafío Autónomo con CI y Pytest

### 1. Software Testing Life Cycle

El **Software Testing Life Cycle (STLC)** es un proceso compuesto por seis fases orientadas a garantizar la calidad del software:

1. **Análisis de Requisitos**: revisión de las especificaciones funcionales para determinar qué aspectos del software deben ser probados.
2. **Planificación de Pruebas**: definición de la estrategia de pruebas, objetivos, recursos, herramientas y actividades necesarias para ejecutar el proceso de testing.
3. **Diseño de Casos de Prueba**: elaboración de escenarios y datos de prueba aplicando técnicas como la Partición de Equivalencia y el Análisis de Valores Límite.
4. **Configuración del Entorno de Pruebas**: preparación del hardware, software y entorno necesarios para ejecutar las pruebas.
5. **Ejecución de Pruebas**: ejecución manual o automatizada de los casos de prueba y registro de los defectos encontrados.
6. **Cierre del Ciclo de Pruebas**: evaluación de métricas, resultados, cobertura, lecciones aprendidas y cierre formal del ciclo.

#### SDLC vs. STLC

- **SDLC (Software Development Life Cycle)**: se enfoca en el ciclo completo de creación y mantenimiento del software, incluyendo análisis, diseño, desarrollo, pruebas, despliegue y mantenimiento.
- **STLC (Software Testing Life Cycle)**: se enfoca específicamente en las actividades de pruebas necesarias para verificar y validar la calidad del software.

Ambos procesos están relacionados y pueden desarrollarse de manera paralela, permitiendo detectar problemas de calidad durante las diferentes etapas del desarrollo.

#### Shift-Left Testing

El **Shift-Left Testing** es un principio que busca trasladar las actividades de prueba lo más temprano posible dentro del ciclo de desarrollo.

En este proyecto, la incorporación de pruebas automatizadas con Pytest y su ejecución mediante integración continua permite detectar errores rápidamente cada vez que se realizan cambios en el código. Esto ayuda a reducir el costo de corrección de defectos y evita que problemas detectados durante el desarrollo lleguen a etapas posteriores.

### 2. Criterios de Calidad

| Tipo de Criterio | Definición | Aplicación en el Proyecto |
|---|---|---|
| **Entry Criteria** (Criterios de Entrada) | Condiciones previas necesarias para iniciar formalmente la ejecución de las pruebas. | 1. Entorno de ejecución configurado correctamente.<br>2. Python instalado y disponible en el entorno de pruebas.<br>3. Dependencias necesarias, como `pytest`, instaladas correctamente.<br>4. Código libre de errores de sintaxis y módulos correctamente importables. |
| **Exit Criteria** (Criterios de Salida) | Condiciones que determinan que la ejecución de las pruebas ha finalizado satisfactoriamente. | 1. Todos los casos de prueba ejecutados.<br>2. Los casos de prueba deben finalizar correctamente (PASSED).<br>3. No deben existir excepciones no controladas durante la ejecución.<br>4. El proceso de integración continua debe finalizar con código de salida 0. |

### 3. Pruebas Automatizadas

Las pruebas automatizadas de la Semana 2 se implementaron utilizando **Pytest**. El archivo `test_presupuesto.py` contiene los casos de prueba destinados a verificar el comportamiento del programa `presupuesto_analisis.py`.

La automatización permite ejecutar los casos de prueba de manera repetible y facilita la identificación temprana de errores cuando se realizan modificaciones en el código.

La estructura del proyecto separa el código fuente de los casos de prueba y de la documentación, facilitando la organización y el mantenimiento del trabajo realizado durante cada semana.

## Conclusión

El desarrollo del taller permitió aplicar conceptos fundamentales de Pruebas de Caja Negra, diseño de casos de prueba, criterios de calidad y automatización con Pytest.

La organización del repositorio por semanas permite mantener una separación clara de las actividades realizadas y facilita la revisión de los resultados obtenidos durante cada etapa del taller.