# Informe de auditoría de liberación — ReservaLab

**Curso:** Aseguramiento de la Calidad de Software · Unidad 3 · Clase 16  
**Grupo:** 5  
**Fecha:** 23 de noviembre de 2026  
**Alcance:** Auditoría grupal de usabilidad, accesibilidad, confiabilidad y seguridad; integración del taller.  
**Referencia:** `Clase16_Fundamentos_Teoricos.pdf`, versión noviembre de 2026.

## 1. Resumen ejecutivo

**Veredicto: No-Go.** La liberación no debe autorizarse mientras existan hallazgos P1 abiertos. La evaluación identifica inyección SQL, acceso no autorizado a reservas y funciones administrativas, exposición de credenciales, barreras de accesibilidad, incumplimiento del SLO, recuperación ante desastres insuficiente y errores silenciosos en fechas. También se incumple el criterio de crecimiento de confiabilidad para la semana 12.

Las prioridades se asignan según T11; la decisión de liberación se fundamenta en R9. Los hallazgos de usabilidad y accesibilidad se incorporan a partir del informe del integrante responsable de A/B y del enunciado de integración. Las capturas originales no se han aportado a este documento. Las evidencias de seguridad se basan en las capturas de tráfico proporcionadas por el taller, no en un escaneo propio no realizado.

## 2. Hallazgos de auditoría

### 2.1. Usabilidad

Clasificación y recomendaciones aportadas por el integrante A/B. Las evidencias se describen según sus notas; no se adjuntan capturas originales.

| ID | Evidencia descrita | Criterio | Severidad | Prioridad | Recomendación | Ancla |
|---|---|---|---|---|---|---|
| UX-01 (O1) | Falta de retroalimentación al reservar; riesgo de clics y reservas duplicadas. | H1 · Visibilidad del estado del sistema | 3 (Mayor) | P2 | Deshabilitar el botón *Reservar* inmediatamente al hacer clic, mostrar un indicador de carga (*spinner*) hasta recibir confirmación del servidor e implementar control de idempotencia en el backend para evitar duplicados. | T1, T2, R1, T11 |
| UX-02 (O2) | Interpretación silenciosa de la fecha como mes/día sin aviso (F5). | H5 · Prevención de errores | 4 (Catástrofe) | **P1** | Reemplazar el campo de texto libre por un selector de calendario nativo (`date picker`) con formato localizado fijo (`DD/MM/AAAA`) y mostrar un resumen explícito con el día de la semana y mes en texto antes de confirmar la reserva. | T1, T2, R1, T11 |
| UX-03 (O3) | Mensaje de error técnico de base de datos. | H9 · Reconocer y recuperarse de errores | 3 (Mayor) | P2 | Ocultar excepciones técnicas de base de datos (`Error 0x45F`) y mostrar un mensaje claro en lenguaje natural (ej. *"El laboratorio seleccionado ya está ocupado en ese horario"*) junto a un enlace directo para ver horarios disponibles. | T1, T2, R1, T11 |
| UX-04 (O4) | Dependencia de un código consultado en un anexo PDF. | H6 · Reconocimiento antes que recuerdo | 3 (Mayor) | P2 | Sustituir el campo de texto libre del código por una lista desplegable con autocompletado que muestre código, nombre del laboratorio y ubicación, eliminando la dependencia del anexo PDF. | T1, T2, R1, T11 |
| UX-05 (O5) | Falta de opciones de volver o cancelar en el asistente. | H3 · Control y libertad del usuario | 3 (Mayor) | P2 | Agregar botones visibles de **Atrás** y **Cancelar** en todos los pasos del asistente de reserva. | T1, T2, R1, T11 |
| UX-06 (O6) | Inconsistencia de estilo, posición o iconos de acciones. | H4 · Consistencia y estándares | 2 (Menor) | P3 | Estandarizar el estilo y posición del botón primario, asignar iconos únicos a cada acción (*Guardar* vs *Enviar*) y diferenciar visualmente la acción destructiva *Eliminar* como botón secundario o de texto con confirmación previa. | T1, T2, R1, T11 |
| UX-07 (O7) | Horarios presentados sin una referencia local clara. | H2 · Correspondencia con el mundo real | 4 (Catástrofe) | **P1** | Mostrar y procesar todos los horarios en la zona horaria local del usuario indicando explícitamente `"Hora local (Ecuador, UTC-5)"` junto a cada franja horaria. | T1, T2, R1, T11 |
| UX-08 (O8) | Inicio saturado de avisos; acción principal poco visible (F9). | H8 · Diseño estético y minimalista | 2 (Menor) | P3 | Ubicar el botón primario `+ Nueva reserva` en la parte superior visible sin desplazamiento (*above the fold*) y agrupar avisos y carruseles secundarios en pestañas o secciones colapsables. | T1, T2, R1, T11 |

### 2.2. Accesibilidad

#### Práctica en el sitio de demostración W3C BAD (B1)

Las siguientes observaciones corresponden al sitio de demostración, no a ReservaLab. No forman parte del veredicto del producto.

* **B1.1 (`1.4.3` — Nivel AA):** En `<td bgcolor="#A9B8BF"><font color="#41545D"><b>Free Penguins</b></font></td>` el contraste actual es de **3.88:1** (texto normal en negrita de 13 px). **Corrección:** Oscurecer el color del texto (ej. `#222C31`) o aclarar el fondo para alcanzar el mínimo de **4.5:1**.
* **B1.2 (`3.1.1` — Nivel A):** La etiqueta raíz `<html>` carece del atributo `lang`. **Corrección:** Agregar el idioma principal (`<html lang="en">`) para su correcta pronunciación en lectores de pantalla.
* **B1.3 (`4.1.2` — Nivel A):** El elemento `<select onchange="location.href = this.value;">` no tiene etiqueta ni nombre accesible. **Corrección:** Asociar un `<label for="...">` visible o agregar `aria-label` descriptivo (ej. `aria-label="Seleccionar sección"`).

#### Código de ReservaLab (B2)

Las prioridades de los criterios A distintos de S3 se asignan como P2 según T11, pues las notas no documentan un bloqueo del flujo crítico.

| ID | Evidencia descrita | Criterio | Severidad de origen | Prioridad | Recomendación | Ancla |
|---|---|---|---|---|---|---|
| A11Y-01 (S1) | Mapa sin alternativa textual | WCAG 1.1.1 | A | P2 | Agregar un atributo alt descriptivo al mapa de laboratorios. | T4, T11 |
| A11Y-02 (S2) | Texto de ayuda con contraste 3.45:1 (F7) | WCAG 1.4.3 | AA | P2 | Oscurecer el texto sobre blanco, por ejemplo #595959, hasta alcanzar al menos 4.5:1. | T4, T11, T5 |
| A11Y-03 (S3) | Botón Reservar implementado con div, inoperable mediante teclado (F3) | WCAG 2.1.1 | A | P1 | Reemplazar por un botón nativo y verificar el flujo con Tab, Enter y Espacio. | T4, T11 |
| A11Y-04 (S4) | Campo de correo sin etiqueta visible asociada | WCAG 3.3.2 | A | P2 | Vincular una etiqueta visible mediante for e id. | T4, T11 |
| A11Y-06 (S6) | Idioma español no declarado en el elemento raíz | WCAG 3.1.1 | A | P2 | Declarar el idioma con `<html lang="es">`. | T4, T11 |
| A11Y-08 (S8) | Área interactiva de botones inferior al mínimo indicado | WCAG 2.5.8 | AA | P2 | Aumentar a 24 × 24 px CSS como mínimo o aplicar espaciado equivalente. | T4, T11 |
| A11Y-10 (S10) | Control personalizado sin nombre, función o estado accesibles | WCAG 4.1.2 | A | P2 | Utilizar un botón con role="switch", nombre accesible y aria-checked actualizado, o un checkbox nativo. | T4, T11 |


### 2.3. Confiabilidad (SRE)

| ID | Título y evidencia | Criterio violado | Severidad de origen | Prioridad | Recomendación | Ancla |
|---|---|---|---|---|---|---|
| SRE-01 (F4) | Se consumió el 116.67 % del presupuesto de error mensual. | SLO de disponibilidad 99.5 %. | Rojo; SLO incumplido | P1 | Congelar funcionalidades nuevas, priorizar correcciones de confiabilidad y seguridad, efectuar postmortem, automatizar rollback y alertar sobre expiración de certificados. | T6, R5 |
| SRE-02 (F8) | Intensidad estimada de 2.98 defectos/semana en la semana 12. | Intensidad objetivo ≤ 1 defecto/semana. | Criterio de liberación incumplido | P2 | Continuar pruebas y correcciones hasta cumplir el umbral; reevaluar el modelo con datos actualizados. | F9, R6 |
| SRE-03 | Únicamente existe respaldo nocturno en el mismo servidor. | RPO ≤ 5 min y RTO ≤ 30 min; regla 3-2-1. | RPO/RTO sin estrategia actual que los cumpla | P1 | Implementar espera en caliente, replicación, copias fuera del sitio y simulacros de restauración; medir el RPO/RTO real. | §3.6, T7, R7, T11 |

#### Métricas del piloto

El período de observación es de 30 días (720 horas). Se excluye MNT-01, mantenimiento anunciado con al menos 48 horas de anticipación, sin reducir el período T (R4).

| Métrica | Cálculo | Resultado | Ancla |
|---|---|---|---|
| Fallas no planificadas | INC-01 a INC-06 | 6 | R4 |
| Indisponibilidad D | 42 + 18 + 95 + 12 + 30 + 55 | 252 min (4.2 h) | R4 |
| Tiempo en funcionamiento U | 720 − 4.2 | 715.8 h | §3.2 |
| MTTF | 715.8 / 6 | 119.3 h | F2 |
| MTTR | 252 / 6 | 42 min | F3 |
| MTBF | 720 / 6 | 120.0 h | F4 |
| Disponibilidad | 715.8 / 720 × 100 | 99.42 % | F5 |
| Presupuesto de error | 43 200 × 0.005 | 216 min | T6 |
| Consumo | 252 / 216 × 100 | 116.67 % (rojo) | R5 |

El modelo Goel–Okumoto usa a = 120 y b = 0.15/semana. La intensidad en la semana 12 es λ(12) = 18 × exp(−1.8) = 2.98 defectos/semana. El umbral de 1 defecto/semana se alcanzaría en t = ln(18)/0.15 ≈ 19.27 semanas; la primera semana completa que cumple es la 20 (F9, R6). Es una estimación, no una garantía de liberación.

Para recuperación ante desastres se recomienda **espera en caliente** (RPO típico ≤ 1 min; RTO 10–30 min). Luz piloto, la opción inmediatamente más barata, tiene RPO típico ≤ 15 min y RTO de 1–4 h; no satisface los límites del negocio en el peor caso (T7, R7).

### 2.4. Seguridad

Los identificadores personales, credenciales, tokens, hashes y demás datos sensibles se omiten deliberadamente. Los códigos OWASP se expresan en la edición 2021 y, entre paréntesis, su equivalente 2025.

| ID | Título y evidencia saneada | Criterio violado | Severidad | Prioridad | Recomendación | Ancla |
|---|---|---|---|---|---|---|
| SEC-01 (F1/E4) | Inyección SQL en `/api/v1/labs`: una entrada manipulada produce HTTP 500 con error SQL; otra devuelve 58 registros frente a 41 e incluye registros ocultos. | A03:2021 Injection (A05:2025); CWE-89. | Crítica, según el enunciado de integración | P1 | Usar consultas parametrizadas, validación de entradas, privilegio mínimo de BD y errores genéricos. Verificar que las entradas no modifiquen la estructura de la consulta ni expongan registros ocultos. | §4.4, T9, T10 |
| SEC-02 (F2/E1) | Una cuenta de estudiante obtiene HTTP 200 al consultar una reserva perteneciente a otra cuenta y recibe sus datos. | A01:2021 Broken Access Control (A01:2025); CWE-639 (IDOR/BOLA). | Alta | P1 | Verificar propiedad y permisos del recurso en el servidor; devolver 403 o 404 si el usuario no está autorizado. | §4.2, T9, T10 |
| SEC-03 (E3) | Login por HTTP, cookie sin Secure ni HttpOnly y respuesta con hash de contraseña compatible con MD5. | A02:2021 Cryptographic Failures (A04:2025); CWE-319 (principal). | Alta | P1 | Exigir HTTPS/TLS, habilitar HSTS, configurar Secure y HttpOnly, migrar a Argon2id con sal y eliminar hashes de las respuestas. | §4.3, T9, T10 |
| SEC-04 (E2) | Una cuenta de estudiante obtiene HTTP 200 en `/api/v1/admin/usuarios` con información administrativa. | A01:2021 Broken Access Control (A01:2025); CWE-285 (BFLA). | Alta | P1 | Aplicar autorización por rol en el servidor, denegar por defecto y verificar que las cuentas sin privilegios reciban 403 o 404. | §4.2, T9, T10 |
| SEC-05 (F6/E5) | Resumen pasivo ZAP: CSP y X-Content-Type-Options ausentes, con 12 instancias de cada alerta. | A05:2021 Security Misconfiguration (A02:2025); CWE-693. | Media, según integración | P2 | Configurar cabeceras adecuadas y verificar su presencia y eficacia en las respuestas HTTP. | T9, T10 |
| SEC-06 (F10/E5) | El encabezado Server revela la versión de Apache. | A05:2021 Security Misconfiguration (A02:2025); exposición de información. | Baja | P3 | Reducir la información de versión expuesta y mantener el servidor actualizado. | T9, T10 |
| SEC-07 (E5/E4) | ZAP informa Application Error Disclosure; la API devuelve detalles internos SQL. | A05:2021 Security Misconfiguration (A02:2025); CWE-209. | Media | P2 | Centralizar el manejo de errores, devolver mensajes genéricos y conservar detalles técnicos en registros internos protegidos. | §4.5, T9, T10 |

Los hallazgos SEC-04 y SEC-07 se incorporan como observaciones complementarias de las evidencias suministradas, aunque no pertenecían al núcleo evaluado de la estación D. SEC-07 representa la categoría secundaria de la evidencia E4; no sustituye la corrección de SQLi.

#### Verificación de regresión de autorización

Con dos cuentas de prueba autorizadas, comprobar que una cuenta no puede leer recursos de otra. La prueba de Postman espera 403 o 404 en lugar del 200 observado:

```javascript
pm.test("No se permite leer reservas ajenas", () => {
  pm.expect(pm.response.code).to.be.oneOf([403, 404]);
});
```

## 3. Integración y prioridad unificada

Se aplica la matriz T11 a los diez hallazgos de integración. El desempate de §5.1 prioriza seguridad, accesibilidad de nivel A, confiabilidad y usabilidad. Entre los dos hallazgos de seguridad de esta lista se coloca primero el crítico.

| Orden | ID de integración | ID del informe | Hallazgo | Prioridad |
|---:|---|---|---|---|
| 1 | F1 | SEC-01 | Inyección SQL | P1 |
| 2 | F2 | SEC-02 | IDOR | P1 |
| 3 | F3 | A11Y-03 | Botón no operable con teclado | P1 |
| 4 | F4 | SRE-01 | SLO incumplido | P1 |
| 5 | F5 | UX-02 | Interpretación silenciosa incorrecta de fecha | P1 |
| 6 | F6 | SEC-05 | Cabeceras de seguridad ausentes | P2 |
| 7 | F7 | A11Y-02 | Contraste insuficiente | P2 |
| 8 | F8 | SRE-02 | Criterio de crecimiento incumplido | P2 |
| 9 | F10 | SEC-06 | Versión de Apache expuesta | P3 |
| 10 | F9 | UX-08 | Inicio saturado | P3 |

Los P1 complementarios SEC-03, SEC-04, SRE-03 y UX-07 también deben resolverse antes de liberar; el Top 5 anterior corresponde específicamente al ejercicio de integración.

## 4. Veredicto y plan de corrección

**No-Go (R9).** Los hallazgos F1, F2, F3, F4 y F5 son bloqueantes. Basta un P1 abierto para impedir la liberación. Además, los hallazgos complementarios P1 refuerzan la decisión. No se debe autorizar el paso a producción hasta corregir y verificar todos los P1.

| Acción | Responsable propuesto | Criterio de cierre |
|---|---|---|
| Corregir SQLi, IDOR, BFLA y exposición de credenciales | Equipo backend y seguridad | Pruebas de regresión de autorización, consultas seguras y transporte cifrado aprobadas. |
| Corregir teclado, contraste, fechas y horarios | Equipo frontend y QA | Flujo completo operable con teclado, contraste conforme y fechas y horarios locales correctos verificados. |
| Recuperar SLO y mejorar recuperación | Equipo DevOps/SRE | Postmortem y correcciones ejecutados; presupuesto recuperado y simulacro de DR dentro de los objetivos. |
| Continuar pruebas de crecimiento y resolver hallazgos restantes | QA y desarrollo | Intensidad ≤ 1 defecto/semana; P2 corregidos o asignados a responsables con fecha de máximo dos sprints, según R9. |

Los responsables indicados son propuestas por función, no asignaciones confirmadas. La reevaluación debe documentar resultados reales antes de emitir un nuevo veredicto.

## 5. Evidencias y limitaciones

Las evidencias textuales provienen del caso ReservaLab, sus incidentes y el ejercicio de integración. Las siguientes capturas deben añadirse en `clase16/evidencias/` si fueron obtenidas realmente:

| Archivo sugerido | Contenido | Estado |
|---|---|---|
| `evidencias/axe.png` | Resultado de axe DevTools del compañero de rol A/B. | Pendiente de incorporar. |
| `evidencias/lighthouse.png` | Resultado de Lighthouse Accessibility del compañero de rol A/B. | Pendiente de incorporar. |
| `evidencias/zap.png` | Alertas del escaneo autorizado, si se realizó. | No se dispone de una captura de escaneo propio. |
| `evidencias/postman.png` | Evidencia HTTP de prueba autorizada, debidamente saneada. | Pendiente si está disponible. |

No se afirma haber ejecutado herramientas ni obtenido capturas que no se han aportado. Si el docente no habilitó Juice Shop, indicar expresamente que se omitió el escaneo en vivo; no fabricar evidencias. El único objetivo de escaneo permitido es el entorno autorizado por el docente o Juice Shop local (R8). Antes de subir imágenes, ocultar todos los datos personales, secretos, credenciales, tokens, hashes e identificadores sensibles.

## 6. Referencias

- Clase 16, *Fundamentos teóricos: usabilidad, accesibilidad, confiabilidad y seguridad web*, noviembre de 2026: §1–§5, T1–T11, F2–F10 y R1–R9 según cada hallazgo.
- Taller *Auditoría de liberación de ReservaLab*: incidentes del piloto, evidencias E1–E5 y hallazgos F1–F10 de integración.
