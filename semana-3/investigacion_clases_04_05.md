# Taller Autónomo de Investigación Teórica
## Fundamentos Matemáticos y Arquitectónicos del Aseguramiento de Calidad — Clases 04 y 05

| Dato | Información |
|---|---|
| **Universidad** | Universidad Internacional del Ecuador |
| **Carrera** | Sistemas de la Información |
| **Materia** | Diseño de Pruebas, Control de Calidad y Mantenimiento |
| **Profesor** | Pablo Javier Robayo Castellanos |
| **Integrantes** | Sebastián Aucapiña y Bryan Montaguano |
| **Fecha de entrega** | 17/09/2026 |

---

# Actividad 1. Taxonomía de niveles de prueba

En las pruebas de software existen distintos niveles. Cada uno revisa una parte diferente del sistema y busca tipos de errores específicos. De acuerdo con ISTQB e ISO/IEC/IEEE 29119, los niveles principales son pruebas unitarias, de integración, de sistema y de aceptación [1], [2].

## 1.1 Matriz comparativa de los cuatro niveles

| Nivel | Objeto de prueba | Base de prueba | Defectos típicos buscados | Rol responsable | Entorno de ejecución |
|---|---|---|---|---|---|
| **Pruebas unitarias** | Funciones, métodos, clases o módulos pequeños | Código, diseño del componente y requisitos específicos | Errores de lógica, cálculos incorrectos, condiciones mal programadas o manejo incorrecto de errores | Principalmente el desarrollador | Entorno de desarrollo, usando frameworks de pruebas, mocks o datos simulados |
| **Pruebas de integración** | Comunicación entre dos o más componentes del sistema | Arquitectura, diseño de interfaces y flujo de datos | Datos que no llegan bien, parámetros incorrectos, errores en la secuencia de llamadas o incompatibilidad entre módulos | Desarrolladores y equipo de pruebas | Entorno de integración; puede usar stubs o drivers |
| **Pruebas de sistema** | El sistema completo funcionando como un todo | Requisitos funcionales y no funcionales, casos de uso y especificaciones | Funciones que no cumplen requisitos, problemas de rendimiento, seguridad, usabilidad o configuración | Equipo de pruebas | Entorno parecido al de producción |
| **Pruebas de aceptación** | El sistema completo visto desde la necesidad del usuario o negocio | Criterios de aceptación, procesos del negocio y necesidades del usuario | El sistema funciona técnicamente, pero no cumple lo que el usuario o negocio necesita | Usuarios, cliente, Product Owner o responsables del negocio | Entorno de aceptación o preproducción |

## 1.2 Diferencia entre *Component Integration Testing* y *System Integration Testing*

 La diferencia principal entre ambos está en los elementos que se conectan.
 - **Component Integration Testing:** comprueba la comunicación entre componentes que forman parte del mismo sistema. Por ejemplo, se puede verificar que el módulo que calcula el puntaje crediticio envíe correctamente el resultado al módulo encargado de aprobar o rechazar la solicitud.
- **System Integration Testing:** comprueba la comunicación entre el sistema y otros sistemas externos. Un ejemplo sería verificar que el sistema bancario pueda comunicarse correctamente con un buró de crédito o con otro servicio externo.

En pocas palabras, el primero se centra en la comunicación entre componentes internos, mientras que el segundo revisa la integración con sistemas externos [2].

---

# Actividad 2. Estrategias de integración y análisis de Verificación y Validación (V&V)

## 2.1 ¿Por qué Big-Bang es una estrategia riesgosa?

El enfoque Big-Bang consiste en desarrollar y probar los módulos por separado para después integrarlos todos al mismo tiempo [3] [4]. El problema de hacerlo de esta manera es que, cuando algo falla después de la integración, puede ser difícil saber exactamente dónde se originó el problema. 

Además, pueden aparecer errores de comunicación entre los módulos, como datos con formatos diferentes, parámetros incorrectos o problemas en el orden de las llamadas. También, si estos errores se encuentran al final, las correcciones pueden requerir repetir varias pruebas. Por estas razones, en sistemas medianos o grandes suele ser conveniente realizar la integración de forma progresiva, comprobando cada componente a medida que se incorpora.

## 2.2 Verificación en pruebas de sistema y validación en UAT

La diferencia se puede entender de manera sencilla:

- **Verificación:** La verificación consiste en comprobar que el sistema fue construido de acuerdo con los requisitos y especificaciones establecidos. En cambio, la validación busca comprobar que el sistema realmente responde a las necesidades del usuario o del negocio.

En una **prueba de sistema**, En las pruebas de sistema se comprueba que el sistema completo cumpla los requisitos definidos. En una prueba de aceptación o UAT, el usuario o el área de negocio comprueba si el sistema resulta adecuado para el trabajo que necesita realizar [2].

### Ejemplo

Supongamos que un sistema bancario tiene la regla: **“rechazar solicitudes con DTI mayor al 40 %”**.

- En una **prueba de sistema**, se ingresa un DTI de 41 % y se comprueba que el sistema rechace la solicitud según lo especificado.
- En una **UAT**, un analista de crédito realiza todo el proceso como lo haría normalmente y confirma que el resultado, los mensajes y el flujo sean útiles para su trabajo diario.

## 2.3 Comparación de estrategias incrementales

| Estrategia | Cómo se integra | Elementos auxiliares | Ventajas | Desventajas |
|---|---|---|---|---|
| **Top-Down** | Se empieza por los módulos de nivel superior y se avanza hacia los inferiores | **Stubs** | Permite revisar temprano el flujo principal y la lógica general del sistema | Los módulos inferiores se prueban más tarde y se deben crear stubs |
| **Bottom-Up** | Se empieza por los módulos inferiores y se avanza hacia los superiores | **Drivers** | Permite probar temprano servicios básicos, utilidades y componentes de bajo nivel | El flujo completo del sistema se ve más tarde y se deben crear drivers |
| **Sandwich** | Combina Top-Down y Bottom-Up al mismo tiempo | Puede usar **stubs y drivers** | Permite trabajar desde ambos extremos y avanzar más rápido en sistemas grandes | Requiere más coordinación entre equipos y puede ser más complejo de organizar |

Un **stub** simula un módulo que todavía no está disponible y que debe ser llamado por otro componente. Un **driver** simula el componente que realiza la llamada [3], [4].

---

# Actividad 3. Matemáticas de la partición de equivalencia

## 3.1 Relación de equivalencia aplicada a datos de entrada

La partición de equivalencia es un tipo de relación que satisface 3 propiedads fundamentales. Su objetivo es definir una partición dentro de un conjunto dónde los elementos se agrupan en relaciones de equivalencia en función de su similitud e igualdad.

Sea \(D\) el conjunto de posibles datos de entrada. Podemos decir que dos valores \(x\) y \(y\) son equivalentes cuando esperamos que el sistema los trate de la misma manera:

$$
x \sim y
$$

Para que exista una relación de equivalencia se deben cumplir tres propiedades:

1. **Reflexividad:** todo valor es equivalente a sí mismo.
2. **Simetría:** si \(x\) es equivalente a \(y\), entonces \(y\) también es equivalente a \(x\).
3. **Transitividad:** si \(x\) es equivalente a \(y\), y \(y\) es equivalente a \(z\), entonces \(x\) es equivalente a \(z\).

Una clase de equivalencia puede escribirse como:

$$
[x] = \{y \in D \mid y \sim x\}
$$

La idea práctica es que no necesitamos probar todos los valores posibles. Podemos elegir un valor representativo de cada grupo [5].

## 3.2 Clases válidas e inválidas

Dentro de las definiciones de clases válidas o inválidas se definen: 
- Una **clase válida** Una clase válida contiene valores que, de acuerdo con la especificación del sistema, deben ser procesados por el objeto de prueba o cuyo procesamiento está definido [2].
- Una **clase inválida** Una clase inválida contiene valores que, de acuerdo con la especificación, deberían ser ignorados o rechazados por el objeto de prueba, o cuyo procesamiento no está definido [2].

La interpretación de qué valores son válidos o inválidos puede variar según el equipo, la organización o las reglas establecidas en la especificación del sistema.

Por ejemplo, si una edad válida está entre 18 y 75 años:

- 30 pertenece a una clase válida.
- 17 pertenece a una clase inválida.
- 76 pertenece a otra clase inválida.

## 3.3 *Failure Masking* o enmascaramiento de fallos

El *failure masking* puede ocurrir cuando colocamos varias entradas inválidas dentro del mismo caso de prueba.

Por ejemplo, imaginemos un formulario con dos campos, A y B. Si A es inválido y B también es inválido, el sistema puede rechazar el formulario únicamente por el error de A. Si la validación de B estuviera mal programada, no lo notaríamos porque A ya hizo que el caso fuera rechazado.

De forma sencilla:

$$
Aceptar(A,B) = P_A(A) \land P_B(B)
$$

Si A ya es falso, el resultado completo será falso aunque exista un problema en la validación de B.

Por esta razón, para este ejercicio es mejor usar **una sola entrada inválida por caso de prueba**, manteniendo las demás entradas con valores válidos. Así es más fácil identificar qué condición produjo el fallo [5].

## 3.4 Fórmula de cobertura de particiones de equivalencia

$$
\text{Cobertura} = \frac{\text{Número de Particiones de Equivalencia Cubiertas}}{\text{Número Total de Particiones de Equivalencia Identificadas}} \times 100
$$

---

# Actividad 4. Ingeniería de especificaciones — Caso bancario

## 4.1 Datos del sistema

Se analiza un sistema de aprobación de créditos con los siguientes parámetros:

- Edad: 18 a 75 años.
- Ingreso neto: USD 500 a USD 10 000.
- Scoring crediticio: 300 a 850.
- DTI (*Debt-to-Income*): máximo 40 %.

Los valores de los límites, como 18, 75, 500, 10 000, 300, 850 y 40 %, se consideran válidos según la especificación entregada.

## 4.2 Identificación de particiones válidas e inválidas

| Variable | ID | Tipo | Partición | Ejemplo |
|---|---|---|---|---:|
| Edad | E1 | Inválida | \(edad < 18\) | 17 |
| Edad | E2 | Válida | \(18 \le edad \le 75\) | 30 |
| Edad | E3 | Inválida | \(edad > 75\) | 76 |
| Ingreso neto | I1 | Inválida | \(ingreso < 500\) | 499 |
| Ingreso neto | I2 | Válida | \(500 \le ingreso \le 10000\) | 3000 |
| Ingreso neto | I3 | Inválida | \(ingreso > 10000\) | 10001 |
| Scoring | S1 | Inválida | \(scoring < 300\) | 299 |
| Scoring | S2 | Válida | \(300 \le scoring \le 850\) | 700 |
| Scoring | S3 | Inválida | \(scoring > 850\) | 851 |
| DTI | D1 | Válida | \(DTI \le 40\%\) | 25 % |
| DTI | D2 | Inválida | \(DTI > 40\%\) | 41 % |

En total se identifican **11 particiones**: 4 válidas y 7 inválidas.

## 4.3 Conjunto mínimo de casos para lograr 100 % de cobertura

Para evitar el *failure masking*, cada caso negativo tiene una sola entrada inválida. Las demás variables se mantienen con valores válidos.

| Caso | Edad | Ingreso neto (USD) | Scoring | DTI | Partición que se prueba | Resultado esperado |
|---|---:|---:|---:|---:|---|---|
| CP-01 | 30 | 3000 | 700 | 25 % | E2, I2, S2, D1 | Datos válidos; el sistema puede continuar el proceso |
| CP-02 | 17 | 3000 | 700 | 25 % | E1 | Rechazo por edad menor a 18 |
| CP-03 | 76 | 3000 | 700 | 25 % | E3 | Rechazo por edad mayor a 75 |
| CP-04 | 30 | 499 | 700 | 25 % | I1 | Rechazo por ingreso menor a USD 500 |
| CP-05 | 30 | 10001 | 700 | 25 % | I3 | Rechazo por ingreso mayor a USD 10 000 |
| CP-06 | 30 | 3000 | 299 | 25 % | S1 | Rechazo por scoring menor a 300 |
| CP-07 | 30 | 3000 | 851 | 25 % | S3 | Rechazo por scoring mayor a 850 |
| CP-08 | 30 | 3000 | 700 | 41 % | D2 | Rechazo por DTI mayor a 40 % |

### ¿Por qué el mínimo es de 8 casos?

Existen 7 particiones inválidas. Como queremos probar cada una por separado, necesitamos 7 casos negativos. Además, necesitamos un caso en el que todas las entradas sean válidas.

Por lo tanto:

$$
N_{mínimo} = 7 + 1 = 8 \text{ casos}
$$

Los 8 casos cubren las 11 particiones identificadas:

$$
\text{Cobertura} = \frac{11}{11} \times 100 = 100\%
$$

No se considera redundante que algunos valores válidos se repitan, porque sirven para mantener estable el resto de las variables mientras se revisa una condición específica.

---

# Actividad 5. Síntesis metacognitiva y estándares de referencia

## 5.1 Relación entre partición de equivalencia y el Principio 2 de ISTQB

El Principio 2 de ISTQB dice que **hacer pruebas exhaustivas es imposible**, excepto en casos muy simples [2]. Esto significa que no podemos probar todas las combinaciones posibles de datos, estados y condiciones de un sistema real.

La partición de equivalencia ayuda justamente con este problema. En lugar de probar todos los valores posibles, dividimos los datos en grupos y elegimos uno o varios valores representativos de cada grupo.

Si el dominio de entrada se divide en varias clases:

$$
D = C_1 \cup C_2 \cup \dots \cup C_n
$$

podemos probar representantes de cada clase sin tener que probar cada valor individual.

Por ejemplo, si una edad válida va de 18 a 75 años, no tendría sentido probar todos los números uno por uno. Podemos elegir un valor representativo de esa clase válida y luego valores de las clases inválidas.

Por eso, la partición de equivalencia permite reducir la cantidad de pruebas sin dejar de revisar las principales condiciones del sistema.

## 5.2 ¿Por qué 100 % de éxito en pruebas unitarias no garantiza que la integración funcione?

Que todas las pruebas unitarias pasen significa que cada componente funcionó correctamente **por separado**. Sin embargo, cuando los componentes se conectan pueden aparecer errores que no existían durante las pruebas individuales.

Algunos ejemplos son:

- Un módulo envía un dato con un formato diferente al que espera el otro.
- Los componentes usan distintas unidades o tipos de datos.
- Las llamadas se realizan en un orden incorrecto.
- Existen problemas de conexión, tiempo de espera o red.
- Un mock utilizado en la prueba unitaria no se comporta igual que el sistema real.
- Dos módulos funcionan bien de forma individual, pero interpretan una regla de manera diferente.

Por ejemplo, un módulo puede enviar la fecha como `17/09/2026`, mientras otro espera `2026-09-17`. Ambos podrían pasar sus pruebas unitarias, pero al conectarlos aparecería un error.

Por esta razón existen diferentes niveles de pruebas. Las pruebas unitarias revisan cada parte de forma aislada, mientras que las pruebas de integración revisan si esas partes realmente funcionan correctamente cuando se comunican entre sí [1], [2].


Que todas las pruebas unitarias pasen significa que cada componente funcionó correctamente por separado. Sin embargo, cuando los componentes se conectan pueden aparecer errores que no existían durante las pruebas individuales.

Algunos ejemplos son:

- Un servicio devuelve un código de respuesta diferente al que espera el sistema que lo consume.
- Un componente envía información incompleta y el otro necesita datos adicionales.
- Los módulos manejan de manera diferente los valores nulos o vacíos.
- Una función puede ejecutarse correctamente de forma individual, pero fallar cuando depende de otro servicio.
- Un sistema puede procesar correctamente una solicitud, pero otro componente puede rechazarla debido a permisos o autenticación.
- Dos componentes funcionan correctamente por separado, pero generan resultados incorrectos cuando comparten información.

Por ejemplo, un sistema puede enviar un precio como 19.99, mientras otro componente espera recibir el valor en centavos, como 1999. Ambos podrían pasar sus pruebas unitarias, pero al conectarlos el precio podría procesarse de manera incorrecta.

Por esta razón existen diferentes niveles de pruebas. Las pruebas unitarias revisan cada parte de forma aislada, mientras que las pruebas de integración revisan si esas partes realmente funcionan correctamente cuando se comunican entre sí [1], [2].
---

# Bibliografía

[1] ISO/IEC/IEEE, **ISO/IEC/IEEE 29119-1:2022, Software and systems engineering—Software testing—Part 1: General concepts**, 2nd ed. Geneva, Switzerland: International Organization for Standardization, 2022. [Online]. Available: https://www.iso.org/standard/81291.html. [Accessed: Sep. 14, 2026].

[2] International Software Testing Qualifications Board, **Certified Tester Foundation Level Syllabus, v4.0.1**, Sep. 2024. [Online]. Available: https://istqb.org/wp-content/uploads/2024/11/ISTQB_CTFL_Syllabus_v4.0.1.pdf. [Accessed: Sep. 14, 2026].

[3] G. J. Myers, C. Sandler, and T. Badgett, **The Art of Software Testing**, 3rd ed. Hoboken, NJ, USA: Wiley, 2012, doi: 10.1002/9781119202486.

[4] R. S. Pressman and B. R. Maxim, **Software Engineering: A Practitioner’s Approach**, 9th ed. New York, NY, USA: McGraw-Hill, 2020.

[5] P. C. Jorgensen, **Software Testing: A Craftsman’s Approach**, 4th ed. Boca Raton, FL, USA: CRC Press, 2013.