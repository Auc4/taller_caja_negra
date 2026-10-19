# Sistema de Citas Médico Familiar — Auditoría QA (Caja Blanca)

API RESTful con interfaz reactiva para la gestión de citas médicas. Toda la
lógica de negocio está desacoplada en `citasLogic.js` y auditada
matemáticamente con la **Complejidad Ciclomática de McCabe V(G)** y un
**100% de Cobertura de Ramas**.

## Reglas de negocio auditadas

| # | Regla | Función | Predicados (P) | V(G) = P + 1 | Tests |
| --- | --- | --- | --- | --- | --- |
| 1 | Regla de Edad (pediátrico vs. adulto) | `validarEdad` | 2 | 3 | 3 |
| 2 | Restricción de Horarios (Lun–Vie, 08:00–17:00) | `validarHorario` | 3 | 4 | 4 |
| 3 | Choque de Citas (doble reserva) | `hayChoque` | 2 | 3 | 3 |
| 4 | Penalización Tardía (menos de 24 h, salvo emergencia) | `calcularPenalizacion` | 2 | 3 | 3 |

## Framework del QA Aumentado

1. La IA genera la lógica base.
2. QA extrae el CFG y calcula V(G) de cada función.
3. QA exige exactamente V(G) tests (uno por camino independiente).
4. Jest + cobertura v8 validan el 100% de ramas (`coverageThreshold` hace fallar la suite si la cobertura baja).
5. Un test “zombi” (sin `expect`) puede pasar en verde sin validar la lógica. Cada oráculo debe comprobar el resultado esperado, no solo la ejecución.

## Instalación y uso

```bash
npm install express better-sqlite3
npm install --save-dev jest c8

npm test             # Ejecuta Jest con cobertura v8
npm run coverage:c8  # Alternativa con c8
npm start            # Inicia el servidor en http://localhost:3000
```

