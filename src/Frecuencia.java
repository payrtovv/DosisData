public class Frecuencia {
    private int intervalo;
    private String unidadDeTiempo; // "horas", "días", "semanas"

    public Frecuencia(int intervalo, String unidadDeTiempo) {
        this.intervalo = intervalo;
        this.unidadDeTiempo = unidadDeTiempo;
    }

    public int getIntervalo() {
        return intervalo;
    }

    public String getUnidadDeTiempo() {
        return unidadDeTiempo;
    }
}
