package qa.citas;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Acceso a SQLite en memoria — equivalente a la BD de server.js.
 */
public class CitasRepository implements AutoCloseable {

    private final Connection conn;

    public CitasRepository(String jdbcUrl) throws SQLException {
        this.conn = DriverManager.getConnection(jdbcUrl);
        try (Statement st = conn.createStatement()) {
            st.execute(
                "CREATE TABLE IF NOT EXISTS citas (" +
                " id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " paciente TEXT NOT NULL," +
                " edad INTEGER NOT NULL," +
                " tieneAcudiente INTEGER NOT NULL DEFAULT 0," +
                " medicoId TEXT NOT NULL," +
                " fecha TEXT NOT NULL," +
                " hora INTEGER NOT NULL," +
                " monto REAL NOT NULL DEFAULT 0)"
            );
        }
    }

    public synchronized List<Cita> listar() throws SQLException {
        List<Cita> out = new ArrayList<>();
        try (ResultSet rs = conn.createStatement()
                .executeQuery("SELECT * FROM citas ORDER BY fecha, hora")) {
            while (rs.next()) out.add(mapRow(rs));
        }
        return out;
    }

    public synchronized List<Cita> buscarPorMedicoYFecha(String medicoId, String fecha) throws SQLException {
        List<Cita> out = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(
                "SELECT * FROM citas WHERE medicoId = ? AND fecha = ?")) {
            ps.setString(1, medicoId);
            ps.setString(2, fecha);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) out.add(mapRow(rs));
            }
        }
        return out;
    }

    public synchronized Cita obtenerPorId(int id) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM citas WHERE id = ?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapRow(rs) : null;
            }
        }
    }

    public synchronized int insertar(Cita c) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO citas (paciente, edad, tieneAcudiente, medicoId, fecha, hora, monto) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, c.paciente);
            ps.setInt(2, c.edad);
            ps.setInt(3, c.tieneAcudiente ? 1 : 0);
            ps.setString(4, c.medicoId);
            ps.setString(5, c.fecha);
            ps.setInt(6, c.hora);
            ps.setDouble(7, c.monto == null ? 0 : c.monto);
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                return keys.next() ? keys.getInt(1) : -1;
            }
        }
    }

    public synchronized boolean eliminar(int id) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM citas WHERE id = ?")) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private static Cita mapRow(ResultSet rs) throws SQLException {
        Cita c = new Cita();
        c.id = rs.getInt("id");
        c.paciente = rs.getString("paciente");
        c.edad = rs.getInt("edad");
        c.tieneAcudiente = rs.getInt("tieneAcudiente") == 1;
        c.medicoId = rs.getString("medicoId");
        c.fecha = rs.getString("fecha");
        c.hora = rs.getInt("hora");
        c.monto = rs.getDouble("monto");
        return c;
    }

    @Override
    public void close() throws SQLException {
        conn.close();
    }
}