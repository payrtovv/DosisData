import javax.swing.*;
import java.util.ArrayList;

public class FrmMostrarTratamientos {
    private JTextArea textArea1;
    private JPanel mainPanel;

    public FrmMostrarTratamientos(ArrayList<Tratamiento> tratamientos) {
        StringBuilder texto = new StringBuilder();
        for (Tratamiento t : tratamientos) {
            texto.append(t.toString2()).append("\n");

        }
        textArea1.setText(texto.toString());
        textArea1.setCaretPosition(0);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
