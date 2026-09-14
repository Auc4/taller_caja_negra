# Taller Autónomo de Investigación Teórica

| Dato | Información |
|-|-|
| **Integrantes** | Sebastián Aucapiña y Bryan Montaguano |
| **Fecha de entrega** | 17/09/2026 |

---

## 1. Taxonomía de Niveles de Prueba
En las pruebas de software existen varios niveles que se encargan de revisar cada parte del sistema con el objetivo de encontrar errores específicos. En este caso, se utiliza como referencia el documento [ISO-29119 - 2022](.fonts/ISO_IEC_IEEE_29119-1_2022.pdf) en el que los niveles principales se componen por:
- Pruebas Unitarias
- Pruebas de Integración
- Pruebas de Sistema
- Pruebas de Aceptación

| Nivel | Objeto de prueba | Base de prueba | Defectos típicos buscados | Rol responsable | Entorno de ejecución |
|---|---|---|---|---|---|
| **Pruebas unitarias** | Funciones, métodos, clases o módulos pequeños | Código, diseño del componente y requisitos específicos | Errores de lógica, cálculos incorrectos, condiciones mal programadas o manejo incorrecto de errores | Principalmente el desarrollador | Entorno de desarrollo, usando frameworks de pruebas, mocks o datos simulados |
| **Pruebas de integración** | Comunicación entre dos o más componentes del sistema | Arquitectura, diseño de interfaces y flujo de datos | Datos que no llegan bien, parámetros incorrectos, errores en la secuencia de llamadas o incompatibilidad entre módulos | Desarrolladores y equipo de pruebas | Entorno de integración; puede usar stubs o drivers |
| **Pruebas de sistema** | El sistema completo funcionando como un todo | Requisitos funcionales y no funcionales, casos de uso y especificaciones | Funciones que no cumplen requisitos, problemas de rendimiento, seguridad, usabilidad o configuración | Equipo de pruebas | Entorno parecido al de producción |
| **Pruebas de aceptación** | El sistema completo visto desde la necesidad del usuario o negocio | Criterios de aceptación, procesos del negocio y necesidades del usuario | El sistema funciona técnicamente, pero no cumple lo que el usuario o negocio necesita | Usuarios, cliente, Product Owner o responsables del negocio | Entorno de aceptación o preproducción |

## 2. Estrategias de Integración y Análisis V&V

## 3. Matemáticas de la Partición de Equivalencia

## 4. Ingeniería de Especificaciones

## 5. Síntesis metacognitiva y Estándares de Referencia
