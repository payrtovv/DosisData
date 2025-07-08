// src/MedicamentoDAO.java
import java.sql.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class MedicamentoDAO {
    public void addMedicamento(Medicamento m) throws SQLException {
        String sql = "INSERT INTO medicamentos (nombre, dosis, intervalo, unidad, proxima_dosis, inicio_dosis) VALUES (?, ?, ?, ?, ?, ?)";

        LocalDateTime inicioDosis = LocalDateTime.now();
        LocalDateTime proximaDosis = calcularProximaDosisDesdeInicio(inicioDosis, m.getFrecuencia());

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, m.getNombre());
            ps.setString(2, m.getDosis());
            ps.setInt(3, m.getFrecuencia().getIntervalo());
            ps.setString(4, m.getFrecuencia().getUnidad());
            ps.setTimestamp(5, Timestamp.valueOf(proximaDosis));
            ps.setTimestamp(6, Timestamp.valueOf(inicioDosis));

            ps.executeUpdate();
        }
    }

    public List<Medicamento> getMedicamentosConRecordatorio() throws SQLException {
        List<Medicamento> list = new ArrayList<>();
        String sql = "SELECT * FROM medicamentos WHERE hora_recordatorio IS NOT NULL";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String dosis = rs.getString("dosis");
                int intervalo = rs.getInt("intervalo");
                String unidad = rs.getString("unidad");
                Time hora = rs.getTime("hora_recordatorio");

                Medicamento m = new Medicamento(nombre, dosis, new Frecuencia(intervalo, unidad));
                m.setId(id);
                m.setHoraRecordatorio(hora.toLocalTime());
                list.add(m);
            }
        }
        return list;
    }

    public void updateMedicamento(Medicamento m) throws SQLException {
        String sql = "UPDATE medicamentos SET nombre = ?, dosis = ?, intervalo = ?, unidad = ? WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, m.getNombre());
            ps.setString(2, m.getDosis());
            ps.setInt(3, m.getFrecuencia().getIntervalo());
            ps.setString(4, m.getFrecuencia().getUnidad());
            ps.setInt(5, m.getId());
            ps.executeUpdate();
        }
    }





    private LocalDateTime calcularProximaDosisDesdeInicio(LocalDateTime inicio, Frecuencia frecuencia) {
        int intervalo = frecuencia.getIntervalo();
        String unidad = frecuencia.getUnidad();

        switch (unidad) {
            case "Minutos":
                return inicio.plusMinutes(intervalo);
            case "Horas":
                return inicio.plusHours(intervalo);
            case "días":
            case "dias":
                return inicio.plusDays(intervalo);
            default:
                throw new IllegalArgumentException("Unidad de frecuencia no soportada: " + unidad);
        }
    }



    public List<Medicamento> getAllMedicamentos() throws SQLException {
        List<Medicamento> list = new ArrayList<>();
        String sql = "SELECT * FROM medicamentos";
        try (Connection conn = DBUtil.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String dosis = rs.getString("dosis");
                int intervalo = rs.getInt("intervalo");
                String unidad = rs.getString("unidad");
                list.add(new Medicamento(id, nombre, dosis, new Frecuencia(intervalo, unidad)));
            }
        }
        return list;
    }

    public void deleteMedicamento(String nombre) throws SQLException {
        String sql = "DELETE FROM medicamentos WHERE nombre = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.executeUpdate();
        }
    }

    public void actualizarHoraRecordatorio(int medicamentoId, LocalTime hora) throws SQLException {
        String sql = "UPDATE medicamentos SET hora_recordatorio = ? WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setTime(1, Time.valueOf(hora));
            ps.setInt(2, medicamentoId);
            ps.executeUpdate();
        }
    }

}