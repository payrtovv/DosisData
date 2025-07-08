// src/TratamientoDAO.java
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TratamientoDAO {

    public void addTratamiento(Tratamiento t) throws SQLException {
        String sql = "INSERT INTO tratamientos (nombre, duracion, unidad_de_tiempo, estado) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, t.getNombre());
            ps.setInt(2, t.getDuracion());
            ps.setString(3, t.getUnidadDeTimepo());
            ps.setBoolean(4, t.isEstado());
            ps.executeUpdate();
        }
    }

    public void updateTratamiento(Tratamiento t) throws SQLException {
        String sql = "UPDATE tratamientos SET nombre = ?, duracion = ?, unidad_de_tiempo = ?, estado = ? WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, t.getNombre());
            ps.setInt(2, t.getDuracion());
            ps.setString(3, t.getUnidadDeTimepo());
            ps.setBoolean(4, t.isEstado());
            ps.setInt(5, t.getId());
            ps.executeUpdate();
        }
    }

    public List<Medicamento> getMedicamentosByTratamiento(int tratamientoId) throws SQLException {
        List<Medicamento> medicamentos = new ArrayList<>();
        String sql = "SELECT m.* FROM medicamentos m " +
                "JOIN tratamiento_medicamento tm ON m.id = tm.medicamento_id " +
                "WHERE tm.tratamiento_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, tratamientoId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String nombre = rs.getString("nombre");
                    String dosis = rs.getString("dosis");
                    int intervalo = rs.getInt("intervalo");
                    String unidad = rs.getString("unidad");

                    Medicamento medicamento = new Medicamento(id, nombre, dosis, new Frecuencia(intervalo, unidad));
                    medicamentos.add(medicamento);
                }

            }
        }
        return medicamentos;
    }

    public void removeMedicamentoFromTratamiento(int tratamientoId, int medicamentoId) throws SQLException {
        String sql = "DELETE FROM tratamiento_medicamento WHERE tratamiento_id = ? AND medicamento_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, tratamientoId);
            ps.setInt(2, medicamentoId);
            ps.executeUpdate();
        }
    }

    public List<Tratamiento> getAllTratamientos() throws SQLException {
        List<Tratamiento> list = new ArrayList<>();
        String sql = "SELECT * FROM tratamientos";

        try (Connection conn = DBUtil.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                int duracion = rs.getInt("duracion");
                String unidad = rs.getString("unidad_de_tiempo");
                boolean estado = rs.getBoolean("estado");

                // Use the correct method and convert to ArrayList
                ArrayList<Medicamento> medicamentos = new ArrayList<>(getMedicamentosByTratamiento(id));

                Tratamiento tratamiento = new Tratamiento(medicamentos, duracion, unidad, nombre);
                tratamiento.setId(id);
                tratamiento.setEstado(estado);

                list.add(tratamiento);
            }
        }

        return list;
    }


    public void deleteTratamiento(int id) throws SQLException {
        String sql = "DELETE FROM tratamientos WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }


}