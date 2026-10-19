const express = require('express');
const path = require('path');
const Database = require('better-sqlite3');
const {
  calcularPenalizacion,
  validarEdad,
  validarHorario,
  hayChoque,
} = require('./citasLogic');

const app = express();
const db = new Database(':memory:'); // SQLite en memoria: efímero, ideal para el taller

db.exec(`
  CREATE TABLE citas (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    paciente TEXT NOT NULL,
    edad INTEGER NOT NULL,
    tieneAcudiente INTEGER NOT NULL DEFAULT 0,
    medicoId TEXT NOT NULL,
    fecha TEXT NOT NULL,
    hora INTEGER NOT NULL,
    monto REAL NOT NULL DEFAULT 0
  );
`);

app.use(express.json());
app.use(express.static(path.join(__dirname, 'public')));

// Listar citas
app.get('/api/citas', (req, res) => {
  res.json(db.prepare('SELECT * FROM citas ORDER BY fecha, hora').all());
});

// Agendar cita — aplica las 4 reglas de negocio
app.post('/api/citas', (req, res) => {
  const { paciente, edad, tieneAcudiente, medicoId, fecha, hora, monto } = req.body;

  if (!paciente || edad == null || !medicoId || !fecha || hora == null) {
    return res.status(400).json({ error: 'Faltan datos obligatorios' });
  }

  const fechaCita = new Date(`${fecha}T00:00:00`);
  const esFinDeSemana = [0, 6].includes(fechaCita.getDay());

  const horario = validarHorario(esFinDeSemana, hora);
  if (!horario.valida) return res.status(422).json({ error: horario.motivo });

  const edadRes = validarEdad(edad, Boolean(tieneAcudiente));
  if (!edadRes.valida) return res.status(422).json({ error: edadRes.motivo });

  const existentes = db
    .prepare('SELECT * FROM citas WHERE medicoId = ? AND fecha = ?')
    .all(medicoId, fecha);
  const choque = existentes.find((c) => hayChoque(c, { medicoId, fecha, hora }));
  if (choque) {
    return res.status(409).json({ error: 'El médico ya tiene una cita en ese bloque horario' });
  }

  const info = db
    .prepare(
      `INSERT INTO citas (paciente, edad, tieneAcudiente, medicoId, fecha, hora, monto)
       VALUES (?, ?, ?, ?, ?, ?, ?)`
    )
    .run(paciente, edad, tieneAcudiente ? 1 : 0, medicoId, fecha, hora, monto ?? 0);

  res.status(201).json({ id: info.lastInsertRowid, mensaje: 'Cita agendada' });
});

// Cancelar cita — aplica penalización si falta menos de 24 h
app.delete('/api/citas/:id', (req, res) => {
  const cita = db.prepare('SELECT * FROM citas WHERE id = ?').get(Number(req.params.id));
  if (!cita) return res.status(404).json({ error: 'Cita no encontrada' });

  const fechaHora = new Date(
    `${cita.fecha}T${String(cita.hora).padStart(2, '0')}:00:00`
  );
  const horasRestantes = (fechaHora - Date.now()) / 36e5;
  const penalizacion = calcularPenalizacion(horasRestantes, false, cita.monto);

  db.prepare('DELETE FROM citas WHERE id = ?').run(cita.id);
  res.json({ mensaje: 'Cita cancelada', penalizacion });
});

module.exports = app;