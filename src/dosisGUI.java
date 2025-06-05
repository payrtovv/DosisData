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
    private JPanel pGeneral;
    private JPanel Inicio;
    private JButton agregarMedicamentoButton;
    private JTextField txtNombre;
    private JTextField txtDosis;
    private JComboBox<String> comboBoxunidad;
    private JTextField txtIntervalo;
    private JLabel imagenGestion;
    private JButton borrarTratamientoButton;
    private JLabel imagenTratamientos;
    private JTextField textFieldNombreTratamiento2;
    private JButton crearButton;
    private JComboBox<Tratamiento> comboBox1;    // “combo box 1” para tratamientos
    private JComboBox<Medicamento> comboBox2;    // “combo box 2” para medicamentos
    private JLabel imagenTrat;
    private JButton borrarMedicamento;
    private JButton modificarButton;
    private JComboBox<Tratamiento> tratamientoBorrarCombo;   // combo para seleccionar tratamiento a borrar
    private JTextField txtBorrarMedicamento;
    private JTextField textFieldDuracionTratamiento3;
    private JTextField textFieldEstado4;
    private JLabel imagenCrearTrat;
    private JButton listarMedicamentosButton;

    private ArrayList<Medicamento> medicamentos = new ArrayList<>();
    private ArrayList<Tratamiento> listaTratamientos = new ArrayList<>();

    public dosisGUI() {
        // Cargar imagenes (igual que antes)
        try {
            ImageIcon icono = new ImageIcon(getClass().getResource("/Imagenes/Imagen_gestionarMeds.png"));
            imagenGestion.setIcon(icono);
            ImageIcon icono3 = new ImageIcon(getClass().getResource("/Imagenes/Crear_Tratamiento.png"));
            imagenTrat.setIcon(icono3);
        } catch (Exception e) {
            System.out.println("No se pudo cargar la imagen: " + e.getMessage());
        }

        // Agregar Medicamento
        agregarMedicamentoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String nombre = txtNombre.getText().trim();
                    String dosis = txtDosis.getText().trim();
                    int intervalo = Integer.parseInt(txtIntervalo.getText().trim());
                    String unidad = comboBoxunidad.getSelectedItem().toString();

                    Medicamento medicamento = new Medicamento(nombre, dosis, new Frecuencia(intervalo, unidad));
                    medicamentos.add(medicamento);
                    comboBox2.addItem(medicamento);
                    JOptionPane.showMessageDialog(null, "Medicamento agregado con éxito.");

                    txtDosis.setText("");
                    txtNombre.setText("");
                    txtIntervalo.setText("");
                    comboBoxunidad.setSelectedIndex(0);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al agregar: " + ex.getMessage());
                }
            }
        });

        // Crear Tratamiento
        crearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String nombreTratamiento = textFieldNombreTratamiento2.getText().trim();
                    String duracion = textFieldDuracionTratamiento3.getText().trim();
                    String estado = textFieldEstado4.getText().trim();

                    if (nombreTratamiento.isEmpty() || duracion.isEmpty() || estado.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Todos los campos del tratamiento son obligatorios.");
                        return;
                    }

                    boolean estadoBool;
                    if (estado.equalsIgnoreCase("activo")) {
                        estadoBool = true;
                    } else if (estado.equalsIgnoreCase("inactivo")) {
                        estadoBool = false;
                    } else {
                        JOptionPane.showMessageDialog(null, "El estado del tratamiento debe ser 'activo' o 'inactivo'.");
                        return;
                    }

                    for (Tratamiento t : listaTratamientos) {
                        if (t.getNombre().equalsIgnoreCase(nombreTratamiento)) {
                            JOptionPane.showMessageDialog(pGeneral,
                                    "Ya existe un tratamiento con el nombre: " + nombreTratamiento,
                                    "Duplicado",
                                    JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                    }

                    Tratamiento nuevoTratamiento = new Tratamiento(nombreTratamiento, duracion, estadoBool);
                    listaTratamientos.add(nuevoTratamiento);
                    comboBox1.addItem(nuevoTratamiento);
                    tratamientoBorrarCombo.addItem(nuevoTratamiento);

                    JOptionPane.showMessageDialog(null,
                            "Tratamiento '" + nombreTratamiento + "' creado con éxito.");

                    textFieldNombreTratamiento2.setText("");
                    textFieldDuracionTratamiento3.setText("");
                    textFieldEstado4.setText("");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al crear tratamiento: " + ex.getMessage());
                }
            }
        });

        // Agregar Medicamento a Tratamiento
        agregarButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Tratamiento tratamientoSeleccionado = comboBox1.getItemAt(comboBox1.getSelectedIndex());
                    Medicamento medicamentoSeleccionado = comboBox2.getItemAt(comboBox2.getSelectedIndex());

                    if (tratamientoSeleccionado == null) {
                        JOptionPane.showMessageDialog(null, "Por favor, seleccione un tratamiento de la lista.");
                        return;
                    }
                    if (medicamentoSeleccionado == null) {
                        JOptionPane.showMessageDialog(null, "Por favor, seleccione un medicamento de la lista.");
                        return;
                    }

                    if (tratamientoSeleccionado.getMedicamentos().contains(medicamentoSeleccionado)) {
                        JOptionPane.showMessageDialog(pGeneral,
                                "El medicamento '" + medicamentoSeleccionado.getNombre() +
                                        "' ya ha sido agregado al tratamiento.");
                        return;
                    }

                    tratamientoSeleccionado.agregarMedicamento(medicamentoSeleccionado);
                    JOptionPane.showMessageDialog(pGeneral,
                            "Medicamento '" + medicamentoSeleccionado.getNombre() +
                                    "' agregado al tratamiento '" + tratamientoSeleccionado.getNombre() +
                                    "' con éxito.",
                            "Éxito",
                            JOptionPane.INFORMATION_MESSAGE);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null,
                            "Error al agregar medicamento al tratamiento: " + ex.getMessage());
                }
            }
        });

        // Borrar Medicamento
        borrarMedicamento.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombreABorrar = txtBorrarMedicamento.getText().trim();
                if (nombreABorrar.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Ingrese el nombre del medicamento a borrar.");
                    return;
                }

                Medicamento medicamentoEncontrado = null;
                for (Medicamento m : medicamentos) {
                    if (m.getNombre().equalsIgnoreCase(nombreABorrar)) {
                        medicamentoEncontrado = m;
                        break;
                    }
                }

                if (medicamentoEncontrado == null) {
                    JOptionPane.showMessageDialog(null,
                            "No se encontró ningún medicamento con el nombre: " + nombreABorrar);
                    return;
                }

                int confirm = JOptionPane.showConfirmDialog(null,
                        "¿Está seguro de que desea eliminar el medicamento '" +
                                medicamentoEncontrado.getNombre() + "'?",
                        "Confirmar eliminación",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    medicamentos.remove(medicamentoEncontrado);
                    comboBox2.removeItem(medicamentoEncontrado);
                    JOptionPane.showMessageDialog(null,
                            "Medicamento '" + medicamentoEncontrado.getNombre() + "' eliminado con éxito.");
                    txtBorrarMedicamento.setText("");
                }
            }
        });

        // Borrar Tratamiento
        borrarTratamientoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Tratamiento tratamientoSeleccionado = tratamientoBorrarCombo.getItemAt(tratamientoBorrarCombo.getSelectedIndex());
                if (tratamientoSeleccionado == null) {
                    JOptionPane.showMessageDialog(null, "Seleccione un tratamiento para eliminar.");
                    return;
                }

                int confirm = JOptionPane.showConfirmDialog(null,
                        "¿Está seguro de que desea eliminar el tratamiento '" +
                                tratamientoSeleccionado.getNombre() + "'?",
                        "Confirmar eliminación",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    listaTratamientos.remove(tratamientoSeleccionado);
                    comboBox1.removeItem(tratamientoSeleccionado);
                    tratamientoBorrarCombo.removeItem(tratamientoSeleccionado);
                    JOptionPane.showMessageDialog(null,
                            "Tratamiento '" + tratamientoSeleccionado.getNombre() + "' eliminado con éxito.");
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                JFrame frame = new JFrame("Gestor de Medicamentos");
                dosisGUI gui = new dosisGUI();
                frame.setContentPane(gui.pGeneral);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
