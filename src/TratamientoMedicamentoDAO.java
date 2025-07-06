// src/TratamientoMedicamentoDAO.java
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TratamientoMedicamentoDAO {
    public void addMedicamentoToTratamiento(int tratamientoId, int medicamentoId) throws SQLException {
        String sql = "INSERT INTO tratamiento_medicamento (tratamiento_id, medicamento_id) VALUES (?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, tratamientoId);
            stmt.setInt(2, medicamentoId);
            stmt.executeUpdate();
        }
    }
}