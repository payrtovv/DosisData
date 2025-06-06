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



    @Override
    public String toString() {
        return Nombre;
    }

    public String toString2() {
        return "Medicamento: " +'\n' +
                "Nombre= " + Nombre + '\n' +
                "Dosis= " + dosis + '\n' +
                "Frecuencia= " + " Cada " + frecuencia.getIntervalo() + " "+ frecuencia.getUnidadDeTiempo() +'\n';

    }
}
