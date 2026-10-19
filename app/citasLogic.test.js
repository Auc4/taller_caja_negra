const {
  calcularPenalizacion,
  validarEdad,
  validarHorario,
  hayChoque,
} = require('./citasLogic');

// ============================================================
// AUDITORÍA DE CAJA BLANCA — Framework del QA Aumentado
// Cada describe cubre EXACTAMENTE los V(G) = P + 1 caminos
// linealmente independientes (Cobertura de Ramas).
// ============================================================

describe('calcularPenalizacion — Penalización Tardía [V(G) = 3]', () => {
  test('Camino 1: anticipación >= 24h -> sin penalización', () => {
    expect(calcularPenalizacion(48, false, 100)).toBe(0);
  });

  test('Camino 2: anticipación < 24h pero es emergencia -> sin penalización', () => {
    expect(calcularPenalizacion(5, true, 100)).toBe(0);
  });

  test('Camino 3: anticipación < 24h y no es emergencia -> penalización del 10%', () => {
    expect(calcularPenalizacion(5, false, 100)).toBe(10);
  });
});

describe('validarEdad — Regla de Edad [V(G) = 3]', () => {
  test('Camino 1: paciente adulto -> cita válida', () => {
    expect(validarEdad(35, false)).toEqual({ valida: true, tipo: 'Adulto', motivo: null });
  });

  test('Camino 2: menor CON acudiente legal -> cita válida', () => {
    expect(validarEdad(10, true)).toEqual({ valida: true, tipo: 'Pediátrico', motivo: null });
  });

  test('Camino 3: menor SIN acudiente legal -> cita rechazada', () => {
    expect(validarEdad(10, false)).toEqual({
      valida: false,
      tipo: 'Pediátrico',
      motivo: 'Menor de 18 años requiere acudiente legal',
    });
  });
});

describe('validarHorario — Restricción de Horarios [V(G) = 4]', () => {
  test('Camino 1: fin de semana -> franja bloqueada', () => {
    expect(validarHorario(true, 10).valida).toBe(false);
    expect(validarHorario(true, 10).motivo).toMatch(/fin de semana/i);
  });

  test('Camino 2: antes de las 08:00 -> franja bloqueada', () => {
    expect(validarHorario(false, 7).valida).toBe(false);
    expect(validarHorario(false, 7).motivo).toMatch(/08:00/i);
  });

  test('Camino 3: a las 17:00 o después -> franja bloqueada', () => {
    expect(validarHorario(false, 17).valida).toBe(false);
    expect(validarHorario(false, 17).motivo).toMatch(/17:00/i);
  });

  test('Camino 4: día laboral dentro de la franja -> cita válida', () => {
    expect(validarHorario(false, 10)).toEqual({ valida: true, motivo: null });
  });
});

describe('hayChoque — Choque de Citas [V(G) = 4]', () => {
  const existente = { medicoId: 'Dr. Gómez', fecha: '2026-11-10', hora: 10 };

  test('Camino 1: médico distinto -> sin choque', () => {
    expect(hayChoque(existente, { medicoId: 'Dra. Ruiz', fecha: '2026-11-10', hora: 10 })).toBe(false);
  });

  test('Camino 2: mismo médico, distinta fecha -> sin choque', () => {
    expect(hayChoque(existente, { medicoId: 'Dr. Gómez', fecha: '2026-11-11', hora: 10 })).toBe(false);
  });

  test('Camino 3: mismo médico y fecha, distinta hora -> sin choque', () => {
    expect(hayChoque(existente, { medicoId: 'Dr. Gómez', fecha: '2026-11-10', hora: 11 })).toBe(false);
  });

  test('Camino 4: mismo médico, fecha y hora -> CHOQUE detectado', () => {
    expect(hayChoque(existente, { medicoId: 'Dr. Gómez', fecha: '2026-11-10', hora: 10 })).toBe(true);
  });
});

// ------------------------------------------------------------
// PROTECCIÓN CONTRA TESTS ZOMBIS (Modelo PIE):
// Un test como el de abajo recorre las líneas (Execution) pero
// no verifica el oráculo (Detection), y la herramienta lo marca
// en verde igual. NUNCA dejes tests sin expect en la suite:
//
//   test('zombi', () => { calcularPenalizacion(5, false, 100); });
// ------------------------------------------------------------