import java.util.ArrayList;
import java.util.Date;

public class Tratamiento {
    private ArrayList<Medicamento> medicamentos;
    private int duracion;
    private String UnidadDeTimepo;
    private boolean estado;
    private String nombre;

    public Tratamiento(ArrayList<Medicamento> medicamentos, int duracion, String unidadDeTimepo, String nombre) {
        this.medicamentos = medicamentos;
        this.duracion = duracion;
        UnidadDeTimepo = unidadDeTimepo;
        this.nombre = nombre;
        this.estado = true;
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


    public String toString2() {
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

    @Override
    public String toString() {
        return nombre; // Solo el nombre se verá en el combo
    }
}
