import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class dosisGUI {
    private JTabbedPane tabbedPane1;
    private JButton agregarButton;
    private JButton agregarButton1;
    private JButton modificarButton1;
    private JButton mostrarButton1;
    private JButton borrarButton1;
    private JPanel pGeneral;
    private JPanel Inicio;
    private JButton agregarMedicamentoButton;
    private JTextField txtNombre;
    private JTextField txtDosis;
    private JComboBox comboBoxunidad;
    private JTextField txtIntervalo;
    private JButton modificarMedicamentoButton;
    private JButton listarMedicamentosButton;
    private JButton borrarMedicamentoButton;
    private JTextField textField4;
    private JTextField textField5;

    ArrayList<Medicamento> medicamentos = new ArrayList<>();

    public dosisGUI() {
        agregarMedicamentoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String nombre = txtNombre.getText();
                    String dosis = txtDosis.getText();

                    int intervalo = Integer.parseInt(txtIntervalo.getText());

                    String unidad = comboBoxunidad.getSelectedItem().toString();

                    Medicamento medicamento = new Medicamento(nombre, dosis, new Frecuencia(intervalo, unidad));

                    medicamentos.add(medicamento);

                    JOptionPane.showMessageDialog(null, "Agregado con exito");

                    txtDosis.setText("");
                    txtNombre.setText("");
                    txtIntervalo.setText("");
                    comboBoxunidad.setSelectedIndex(0);



                } catch (Exception X) {
                    JOptionPane.showMessageDialog(null, "Error");
                }
            }
        });
        listarMedicamentosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("dosisGUI");
        frame.setContentPane(new dosisGUI().pGeneral);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
