/**
 * Lógica de negocio pura, desacoplada de la infraestructura,
 * para poder extraer el CFG y calcular V(G) = P + 1 (McCabe).
 */

// ------------------------------------------------------------------
// Regla 4: Penalización Tardía (< 24 h, salvo emergencia médica)
// P = 2 (horas < 24 ; !esEmergencia)  ->  V(G) = 3 caminos
// ------------------------------------------------------------------
function calcularPenalizacion(horasAnticipacion, esEmergencia, monto) {
  if (horasAnticipacion < 24 && !esEmergencia) {
    return Math.round(monto * 0.10 * 100) / 100; // 10% del monto
  }
  return 0;
}

// ------------------------------------------------------------------
// Regla 1: Regla de Edad (pediátrico vs adulto)
// P = 2 (edad < 18 ; !tieneAcudiente)  ->  V(G) = 3 caminos
// ------------------------------------------------------------------
function validarEdad(edad, tieneAcudiente) {
  if (edad < 18) {
    if (!tieneAcudiente) {
      return { valida: false, tipo: 'Pediátrico', motivo: 'Menor de 18 años requiere acudiente legal' };
    }
    return { valida: true, tipo: 'Pediátrico', motivo: null };
  }
  return { valida: true, tipo: 'Adulto', motivo: null };
}

// ------------------------------------------------------------------
// Regla 2: Restricción de Horarios (Lun-Vie, 08:00 a 17:00)
// P = 3  ->  V(G) = 4 caminos
// ------------------------------------------------------------------
function validarHorario(esFinDeSemana, hora) {
  if (esFinDeSemana) {
    return { valida: false, motivo: 'La clínica no atiende el fin de semana' };
  }
  if (hora < 8) {
    return { valida: false, motivo: 'Fuera de horario: la atención inicia a las 08:00' };
  }
  if (hora >= 17) {
    return { valida: false, motivo: 'Fuera de horario: la atención termina a las 17:00' };
  }
  return { valida: true, motivo: null };
}

// ------------------------------------------------------------------
// Regla 3: Choque de Citas (doble reserva del mismo médico)
// P = 3  ->  V(G) = 4 caminos
// ------------------------------------------------------------------
function hayChoque(citaExistente, nuevaCita) {
  if (citaExistente.medicoId !== nuevaCita.medicoId) return false;
  if (citaExistente.fecha !== nuevaCita.fecha) return false;
  return citaExistente.hora === nuevaCita.hora;
}

module.exports = {
  calcularPenalizacion,
  validarEdad,
  validarHorario,
  hayChoque,
};