import javax.swing.*;

import java.time.LocalTime;


public class Medicamento {
         private int id;
        private String Nombre;
        private String dosis;
        private Frecuencia frecuencia;
        private LocalTime horaRecordatorio;

    public LocalTime getHoraRecordatorio() {
        return horaRecordatorio;
    }

    public void setHoraRecordatorio(LocalTime horaRecordatorio) {
        this.horaRecordatorio = horaRecordatorio;
    }


    public Medicamento(int id, String nombre, String dosis, Frecuencia frecuencia) {
            this.id = id;
            this.Nombre = nombre;
            this.dosis = dosis;
            this.frecuencia = frecuencia;
        }

        // Overload constructor if needed (without id)
        public Medicamento(String nombre, String dosis, Frecuencia frecuencia) {
            this.Nombre = nombre;
            this.dosis = dosis;
            this.frecuencia = frecuencia;
        }

        public int getId() {
            return id;
        }
        public void setId(int id) {
            this.id = id;
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
        return Nombre;
    }

    public String toString2() {
        return "Medicamento: " +'\n' +
                "Nombre= " + Nombre + '\n' +
                "Dosis= " + dosis + '\n' +
                "Frecuencia= " + " Cada " + frecuencia.getIntervalo() + " "+ frecuencia.getUnidadDeTiempo() +'\n';

    }





}
