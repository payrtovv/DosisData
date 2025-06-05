import java.util.ArrayList;
import java.util.Date;

public class Tratamiento {
    private ArrayList<Medicamento> medicamentos;
    private String duracion;
    private boolean estado;
    private String nombre;

    public Tratamiento(String nombre, String duracion, boolean estado) {
        this.nombre = nombre;
        this.duracion = duracion;
        this.estado = estado;
        this.medicamentos = new ArrayList<>();
    }

    public void agregarMedicamento(Medicamento medicamento) {
        if (medicamento != null) {
            this.medicamentos.add(medicamento);
        }
    }

    public String getNombre() {
        return nombre;
    }


    public ArrayList<Medicamento> getMedicamentos() {
        return medicamentos;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Tratamiento:\n");
        sb.append("  Nombre   = ").append(nombre).append("\n");
        sb.append("  Duración = ").append(duracion).append("\n");
        sb.append("  Estado   = ").append(estado ? "Activo" : "Inactivo").append("\n");
        sb.append("  Medicamentos:\n");
        for (Medicamento m : medicamentos) {
            sb.append("    • ").append(m.toString().replaceAll("(?m)^", "      ")).append("\n");
        }
        return sb.toString();
    }
}
