package qa.citas;

/**
 * Reglas de negocio puras — equivalente a citasLogic.js.
 * Mismos predicados y mismos valores de V(G) = P + 1.
 */
public class CitasLogic {

    /** Resultado de las validaciones (edad y horario). */
    public static class ResultadoValidacion {
        public final boolean valida;
        public final String tipo;
        public final String motivo;

        public ResultadoValidacion(boolean valida, String tipo, String motivo) {
            this.valida = valida;
            this.tipo = tipo;
            this.motivo = motivo;
        }
    }

    // Regla 4: Penalización Tardía. P = 2 -> V(G) = 3
    public static double calcularPenalizacion(double horasAnticipacion, boolean esEmergencia, double monto) {
        if (horasAnticipacion < 24 && !esEmergencia) {
            return Math.round(monto * 0.10 * 100) / 100.0; // 10% del monto
        }
        return 0;
    }

    // Regla 1: Regla de Edad. P = 2 -> V(G) = 3
    public static ResultadoValidacion validarEdad(int edad, boolean tieneAcudiente) {
        if (edad < 18) {
            if (!tieneAcudiente) {
                return new ResultadoValidacion(false, "Pediátrico", "Menor de 18 años requiere acudiente legal");
            }
            return new ResultadoValidacion(true, "Pediátrico", null);
        }
        return new ResultadoValidacion(true, "Adulto", null);
    }

    // Regla 2: Restricción de Horarios. P = 3 -> V(G) = 4
    public static ResultadoValidacion validarHorario(boolean esFinDeSemana, int hora) {
        if (esFinDeSemana) {
            return new ResultadoValidacion(false, null, "La clínica no atiende el fin de semana");
        }
        if (hora < 8) {
            return new ResultadoValidacion(false, null, "Fuera de horario: la atención inicia a las 08:00");
        }
        if (hora >= 17) {
            return new ResultadoValidacion(false, null, "Fuera de horario: la atención termina a las 17:00");
        }
        return new ResultadoValidacion(true, null, null);
    }

    // Regla 3: Choque de Citas. P = 2 -> V(G) = 3
    public static boolean hayChoque(Cita existente, Cita nueva) {
        if (!existente.medicoId.equals(nueva.medicoId)) return false;
        if (!existente.fecha.equals(nueva.fecha)) return false;
        return existente.hora == nueva.hora;
    }
}