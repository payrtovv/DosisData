import javax.swing.*;
import java.util.ArrayList;

public class FrmMostrarMedicamentosGUI {
    private JPanel mainPanel;
    private JTextArea textArea1;

    private ArrayList<Medicamento> medicamentos = new ArrayList<>();

    public FrmMostrarMedicamentosGUI(ArrayList<Medicamento> medicamentos) {
        StringBuilder texto = new StringBuilder();
        for (Medicamento medicamento : medicamentos) {
            texto.append(medicamento.toString()).append("\n");
        }
        textArea1.setText(texto.toString());
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
