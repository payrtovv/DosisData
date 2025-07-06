// src/MedicamentoDAO.java
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicamentoDAO {
    public void addMedicamento(Medicamento m) throws SQLException {
        String sql = "INSERT INTO medicamentos (nombre, dosis, intervalo, unidad) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, m.getNombre());
            ps.setString(2, m.getDosis());
            ps.setInt(3, m.getFrecuencia().getIntervalo());
            ps.setString(4, m.getFrecuencia().getUnidad());
            ps.executeUpdate();
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
}