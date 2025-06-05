import javax.swing.*;
import java.util.ArrayList;

public class FrmMostrarTratamientos {
    private JPanel mainPanel;
    private JTextArea textArea1;

    public FrmMostrarTratamientos(ArrayList<Tratamiento> tratamientos) {
        StringBuilder texto = new StringBuilder();
        for (Tratamiento t : tratamientos) {
            // Si preferís usar el toString() de Tratamiento, bastaría hacer:
            texto.append(t.toString()).append("\n");

        }
        textArea1.setText(texto.toString());
        textArea1.setCaretPosition(0);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
