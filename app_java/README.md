# Migración a Java — Sistema de Citas Médicas (app_java)

Segunda etapa del proyecto: migrar el backend de Node.js/Express a **Java 17**
manteniendo el **mismo contrato de API**, de modo que la interfaz existente en
`app/public` funcione sin cambios.

## Requisitos

- Java 17 (JDK)
- Maven 3.8+

Verificar en el Codespace:

```bash
java -version
mvn -version
```

Si falta algo:

```bash
sudo apt-get update && sudo apt-get install -y openjdk-17-jdk maven
```

## Estructura

```text
app_java/
├── pom.xml
├── README.md
└── src
    ├── main/java/qa/citas
    │   ├── Main.java            # rutas Spark + arranque (equivale a server.js/index.js)
    │   ├── Cita.java            # modelo de cita
    │   ├── CitasLogic.java      # reglas de negocio puras (equivale a citasLogic.js)
    │   └── CitasRepository.java # SQLite en memoria (jdbc)
    └── test/java/qa/citas
        └── CitasLogicTest.java  # 13 tests, uno por camino de V(G)
```

## Plan de migración

1. La versión Node en `app/` se conserva como referencia; `app_java` es un proyecto Maven independiente.
2. El contrato de la API se mantiene idéntico: `GET /api/citas`, `POST /api/citas`, `DELETE /api/citas/:id`, con los mismos códigos de estado (400, 409, 404, 422, 201).
3. Las reglas de negocio se trasladan de `citasLogic.js` a `CitasLogic.java`, con los mismos predicados y los mismos valores de McCabe: V(G) = 3 + 4 + 3 + 3 = 13 caminos.
4. SQLite en memoria se conserva (jdbc), con la misma tabla `citas`.
5. `Main.java` (Spark Java) sirve también el frontend desde `../app/public`.

## Plan de pruebas

- **Unitarias (JUnit 5):** 13 tests en `CitasLogicTest`, uno por camino independiente de cada regla.
- **Integración/manual:** con el servidor levantado, agendar, listar y cancelar desde la interfaz; verificar errores de horario, edad, choque y penalización.
- **Paridad:** enviar los mismos casos a la versión Node y a la Java y comparar respuestas y códigos de estado.

## Cómo ejecutar

```bash
cd app_java
mvn test          # pruebas unitarias (13 caminos)
mvn exec:java     # servidor en http://localhost:3000 (sirve ../app/public)
```

Abre el puerto 3000 en la pestaña **Ports** del Codespace: verás el mismo frontend de siempre, pero atendido por el backend Java.

Notas de paridad:

- Detén el servidor Node (`npm start` en `app/`) antes de levantar el de Java, porque ambos usan el puerto 3000.
- La carpeta del frontend se resuelve como `../app/public` relativa a donde ejecutes Maven.