import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class Tratamiento {
    private int id;
    private ArrayList<Medicamento> medicamentos;
    private int duracion;
    private String UnidadDeTimepo;
    private boolean estado;
    private String nombre;

    public Tratamiento(int id, ArrayList<Medicamento> medicamentos, int duracion, String unidadDeTimepo, String nombre) {
        this.id = id;
        this.medicamentos = medicamentos;
        this.duracion = duracion;
        this.UnidadDeTimepo = unidadDeTimepo;
        this.nombre = nombre;
        this.estado = true;
    }

    // Overload constructor if needed (without id)
    public Tratamiento(ArrayList<Medicamento> medicamentos, int duracion, String unidadDeTimepo, String nombre) {
        this.medicamentos = medicamentos;
        this.duracion = duracion;
        this.UnidadDeTimepo = unidadDeTimepo;
        this.nombre = nombre;
        this.estado = true;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public String getUnidadDeTimepo() {
        return UnidadDeTimepo;
    }

    public void setUnidadDeTimepo(String unidadDeTimepo) {
        UnidadDeTimepo = unidadDeTimepo;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}


