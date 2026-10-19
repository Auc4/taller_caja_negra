package qa.citas;

/**
 * Modelo de cita. Campos públicos para que Gson
 * los mapee directamente desde el JSON de la petición.
 */
public class Cita {
    public int id;
    public String paciente;
    public Integer edad;
    public boolean tieneAcudiente;
    public String medicoId;
    public String fecha;   // formato yyyy-MM-dd
    public Integer hora;   // 0-23
    public Double monto;
}