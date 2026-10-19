package qa.citas;

import org.junit.jupiter.api.Test;
import qa.citas.CitasLogic.ResultadoValidacion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Suite auditada: un test por cada camino independiente.
 * Total V(G) = 13 (3 + 4 + 3 + 3), igual que la versión Node.
 */
class CitasLogicTest {

    @Test
    void camino1_anticipacionMayorOIgual24h_sinPenalizacion() {
        assertEquals(0, CitasLogic.calcularPenalizacion(48, false, 100));
    }

    @Test
    void camino2_tardiaPeroEmergencia_sinPenalizacion() {
        assertEquals(0, CitasLogic.calcularPenalizacion(5, true, 100));
    }

    @Test
    void camino3_tardiaSinEmergencia_penalizacionDel10Porciento() {
        assertEquals(10, CitasLogic.calcularPenalizacion(5, false, 100));
    }

    @Test
    void camino1_adulto_citaValida() {
        ResultadoValidacion r = CitasLogic.validarEdad(35, false);
        assertTrue(r.valida);
        assertEquals("Adulto", r.tipo);
    }

    @Test
    void camino2_menorConAcudiente_citaValida() {
        ResultadoValidacion r = CitasLogic.validarEdad(10, true);
        assertTrue(r.valida);
        assertEquals("Pediátrico", r.tipo);
    }

    @Test
    void camino3_menorSinAcudiente_citaRechazada() {
        ResultadoValidacion r = CitasLogic.validarEdad(10, false);
        assertFalse(r.valida);
        assertEquals("Menor de 18 años requiere acudiente legal", r.motivo);
    }

    @Test
    void camino1_finDeSemana_franjaBloqueada() {
        ResultadoValidacion r = CitasLogic.validarHorario(true, 10);
        assertFalse(r.valida);
        assertTrue(r.motivo.toLowerCase().contains("fin de semana"));
    }

    @Test
    void camino2_antesDeLas8_franjaBloqueada() {
        ResultadoValidacion r = CitasLogic.validarHorario(false, 7);
        assertFalse(r.valida);
        assertTrue(r.motivo.contains("08:00"));
    }

    @Test
    void camino3_aLas17OHoraPosterior_franjaBloqueada() {
        ResultadoValidacion r = CitasLogic.validarHorario(false, 17);
        assertFalse(r.valida);
        assertTrue(r.motivo.contains("17:00"));
    }

    @Test
    void camino4_diaLaboralDentroDeFranja_citaValida() {
        assertTrue(CitasLogic.validarHorario(false, 10).valida);
    }

    @Test
    void camino1_medicoDistinto_sinChoque() {
        Cita existente = cita(1, "2026-11-10", 10);
        assertFalse(CitasLogic.hayChoque(existente, cita(2, "2026-11-10", 10)));
    }

    @Test
    void camino2_mismoMedicoDistintaFechaOHora_sinChoque() {
        Cita existente = cita(1, "2026-11-10", 10);
        assertFalse(CitasLogic.hayChoque(existente, cita(1, "2026-11-11", 10)));
        assertFalse(CitasLogic.hayChoque(existente, cita(1, "2026-11-10", 11)));
    }

    @Test
    void camino3_mismoMedicoFechaYHora_choqueDetectado() {
        Cita existente = cita(1, "2026-11-10", 10);
        assertTrue(CitasLogic.hayChoque(existente, cita(1, "2026-11-10", 10)));
    }

    private static Cita cita(int medicoId, String fecha, int hora) {
        Cita c = new Cita();
        c.medicoId = "Dr. " + medicoId;
        c.fecha = fecha;
        c.hora = hora;
        return c;
    }
}