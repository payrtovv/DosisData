public class Medicamento {
    private String Nombre;
    private String dosis;
    private Frecuencia frecuencia;

    public Medicamento(String nombre, String dosis, Frecuencia frecuencia) {
        Nombre = nombre;
        this.dosis = dosis;
        this.frecuencia = frecuencia;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getDosis() {
        return dosis;
    }

    public void setDosis(String dosis) {
        this.dosis = dosis;
    }

    public Frecuencia getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(Frecuencia frecuencia) {
        this.frecuencia = frecuencia;
    }

    @Override
    public String toString() {
        return "Medicamento: " +'\n' +
                "Nombre= " + Nombre + '\n' +
                "Dosis= " + dosis + '\n' +
                "Frecuencia= " + " Cada " + frecuencia.getIntervalo() + " "+ frecuencia.getUnidadDeTiempo() +'\n';

    }
}
