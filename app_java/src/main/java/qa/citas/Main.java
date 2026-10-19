package qa.citas;

import com.google.gson.Gson;
import spark.Spark;

import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.staticFiles;

/**
 * Punto de entrada — equivalente a server.js + index.js.
 * Rutas idénticas a la versión Node para reutilizar app/public.
 */
public class Main {

    public static void main(String[] args) throws SQLException {
        port(3000);

        // Sirve el frontend existente (carpeta app/public del proyecto Node)
        staticFiles.externalLocation("../app/public");

        Gson gson = new Gson();
        CitasRepository repo = new CitasRepository("jdbc:sqlite::memory:");

        get("/api/citas", (req, res) -> {
            res.type("application/json");
            return gson.toJson(repo.listar());
        });

        post("/api/citas", (req, res) -> {
            res.type("application/json");
            Cita nueva = gson.fromJson(req.body(), Cita.class);

            if (nueva.paciente == null || nueva.edad == null || nueva.medicoId == null
                    || nueva.fecha == null || nueva.hora == null) {
                res.status(400);
                return gson.toJson(java.util.Map.of("error", "Faltan datos obligatorios"));
            }

            LocalDate fechaCita = LocalDate.parse(nueva.fecha);
            boolean esFinDeSemana = fechaCita.getDayOfWeek() == DayOfWeek.SATURDAY
                    || fechaCita.getDayOfWeek() == DayOfWeek.SUNDAY;

            CitasLogic.ResultadoValidacion horario = CitasLogic.validarHorario(esFinDeSemana, nueva.hora);
            if (!horario.valida) {
                res.status(422);
                return gson.toJson(java.util.Map.of("error", horario.motivo));
            }

            CitasLogic.ResultadoValidacion edadRes =
                    CitasLogic.validarEdad(nueva.edad, nueva.tieneAcudiente);
            if (!edadRes.valida) {
                res.status(422);
                return gson.toJson(java.util.Map.of("error", edadRes.motivo));
            }

            List<Cita> existentes = repo.buscarPorMedicoYFecha(nueva.medicoId, nueva.fecha);
            boolean choque = existentes.stream().anyMatch(c -> CitasLogic.hayChoque(c, nueva));
            if (choque) {
                res.status(409);
                return gson.toJson(java.util.Map.of(
                        "error", "El médico ya tiene una cita en ese bloque horario"));
            }

            int id = repo.insertar(nueva);
            res.status(201);
            return gson.toJson(java.util.Map.of("id", id, "mensaje", "Cita agendada"));
        });

        delete("/api/citas/:id", (req, res) -> {
            res.type("application/json");
            Cita cita = repo.obtenerPorId(Integer.parseInt(req.params(":id")));
            if (cita == null) {
                res.status(404);
                return gson.toJson(java.util.Map.of("error", "Cita no encontrada"));
            }

            LocalDateTime fechaHora = LocalDateTime.of(
                    LocalDate.parse(cita.fecha), LocalTime.of(cita.hora, 0));
            double horasRestantes =
                    Duration.between(LocalDateTime.now(), fechaHora).toMillis() / 3600000.0;
            double penalizacion = CitasLogic.calcularPenalizacion(
                    horasRestantes, false, cita.monto == null ? 0 : cita.monto);

            repo.eliminar(cita.id);
            return gson.toJson(java.util.Map.of(
                    "mensaje", "Cita cancelada", "penalizacion", penalizacion));
        });
    }
}