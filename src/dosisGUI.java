// src/dosisGUI.java
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.List;
import java.awt.GridLayout;


public class dosisGUI {
    private JTabbedPane tabbedPane1;
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
    private JTextField textFieldNombreTratamiento2;
    private JButton crearButton;
    private JComboBox<Tratamiento> comboBox1;
    private JLabel imagenTrat;
    private JButton borrarMedicamento;
    private JButton modificarButton;
    private JComboBox<Tratamiento> tratamientoBorrarCombo;
    private JButton Mostrarmedicamentos;
    private JTextField NombreTratamiento;
    private JButton mostrarTratamiento;
    private JComboBox UnidadDetiempotratamiento;
    private JTextField TxtDuracion;
    private JComboBox<Medicamento> comboBox2;
    private JComboBox comboBoxBorrarMedicamento;
    private JComboBox ComboMedicamentoRecordatorios;
    private JComboBox comboBoxhora;
    private JComboBox comboBoxminuto;
    private JButton agregarRecordatorioButton;
    private JTable tabltablaRecordatorios;
    private JComboBox comboBoxModificarMedicamento;

    public dosisGUI() {
        // Load images
        try {
            ImageIcon icono = new ImageIcon(getClass().getResource("/Imagenes/Imagen_gestionarMeds.png"));
            imagenGestion.setIcon(icono);
            ImageIcon icono3 = new ImageIcon(getClass().getResource("/Imagenes/Crear_Tratamiento.png"));
            imagenTrat.setIcon(icono3);
        } catch (Exception e) {
            System.out.println("No se pudo cargar la imagen: " + e.getMessage());
        }





        refreshMedicamentosCombo();
        refreshTratamientosCombos();
        refreshComboBoxBorrarMedicamento();
        refreshComboBoxseleccionarMedicamento();
        cargarRecordatoriosPendientes();
        refreshComboBoxModificarMedicamento();

        agregarMedicamentoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String nombre = txtNombre.getText().trim();
                    String dosis = txtDosis.getText().trim();
                    int intervalo = Integer.parseInt(txtIntervalo.getText().trim());
                    if (intervalo <= 0) {
                        JOptionPane.showMessageDialog(null, "El intervalo debe ser mayor que cero.");
                        return;
                    }
                    String unidad = comboBoxunidad.getSelectedItem().toString();

                    Medicamento medicamento = new Medicamento(nombre, dosis, new Frecuencia(intervalo, unidad));
                    new MedicamentoDAO().addMedicamento(medicamento);

                    refreshMedicamentosCombo();
                    JOptionPane.showMessageDialog(null, "Medicamento agregado con éxito.");


                    txtDosis.setText("");
                    txtNombre.setText("");
                    txtIntervalo.setText("");
                    comboBoxunidad.setSelectedIndex(0);

                    refreshComboBoxBorrarMedicamento();




                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al agregar: " + ex.getMessage());
                }
            }
        });

        crearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String nombreTratamiento = NombreTratamiento.getText().trim();
                    int duracion = Integer.parseInt(TxtDuracion.getText());
                    String unidad = UnidadDetiempotratamiento.getSelectedItem().toString();

                    for (Tratamiento t : new TratamientoDAO().getAllTratamientos()) {
                        if (t.getNombre().equalsIgnoreCase(nombreTratamiento)) {
                            JOptionPane.showMessageDialog(pGeneral,
                                    "Ya existe un tratamiento con el nombre: " + nombreTratamiento,
                                    "Duplicado",
                                    JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                    }

                    Tratamiento nuevoTratamiento = new Tratamiento(null, duracion, unidad, nombreTratamiento); // medicamentos=null
                    new TratamientoDAO().addTratamiento(nuevoTratamiento);

                    refreshTratamientosCombos();

                    JOptionPane.showMessageDialog(null,
                            "Tratamiento '" + nombreTratamiento + "' creado con éxito.");

                    NombreTratamiento.setText("");
                    TxtDuracion.setText("");
                    UnidadDetiempotratamiento.setSelectedIndex(0);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al crear tratamiento: " + ex.getMessage());
                }
            }
        });

        agregarButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Tratamiento tratamientoSeleccionado = (Tratamiento) comboBox1.getSelectedItem();
                Medicamento medicamentoSeleccionado = (Medicamento) comboBox2.getSelectedItem();

                if (tratamientoSeleccionado == null || medicamentoSeleccionado == null) {
                    JOptionPane.showMessageDialog(null, "Seleccione un tratamiento y un medicamento.");
                    return;
                }

                try {
                    new TratamientoMedicamentoDAO().addMedicamentoToTratamiento(tratamientoSeleccionado.getId(), medicamentoSeleccionado.getId());
                    JOptionPane.showMessageDialog(null, "Medicamento agregado al tratamiento con éxito.");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al agregar medicamento al tratamiento: " + ex.getMessage());
                }
            }
        });



        borrarMedicamento.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Medicamento seleccionado = (Medicamento) comboBoxBorrarMedicamento.getSelectedItem();
                if (seleccionado == null) {
                    JOptionPane.showMessageDialog(null, "Seleccione un medicamento para borrar.");
                    return;
                }
                int confirm = JOptionPane.showConfirmDialog(null,
                        "¿Está seguro de que desea eliminar el medicamento '" +
                                seleccionado.getNombre() + "'?",
                        "Confirmar eliminación",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        new MedicamentoDAO().deleteMedicamento(seleccionado.getNombre());
                        refreshMedicamentosCombo();
                        refreshComboBoxBorrarMedicamento();
                        JOptionPane.showMessageDialog(null, "Medicamento eliminado con éxito.");
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Error al eliminar: " + ex.getMessage());
                    }
                }
            }
        });

        mostrarTratamiento.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    List<Tratamiento> tratamientos = new TratamientoDAO().getAllTratamientos();
                    if (tratamientos.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "No existen tratamientos");
                    } else {
                        FrmMostrarTratamientos frm = new FrmMostrarTratamientos(new java.util.ArrayList<>(tratamientos));
                        JFrame frame = new JFrame("Listar tratamientos");
                        frame.setContentPane(frm.getMainPanel());
                        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        frame.pack();
                        frame.setLocationRelativeTo(null);
                        frame.setVisible(true);
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al mostrar tratamientos: " + ex.getMessage());
                }
            }
        });

        Mostrarmedicamentos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    List<Medicamento> medicamentos = new MedicamentoDAO().getAllMedicamentos();
                    if (medicamentos.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "No existen medicamentos");
                    } else {
                        FrmMostrarMedicamentosGUI frm = new FrmMostrarMedicamentosGUI(new java.util.ArrayList<>(medicamentos));
                        JFrame frame = new JFrame("Listar Medicamentos");
                        frame.setContentPane(frm.getMainPanel());
                        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        frame.pack();
                        frame.setLocationRelativeTo(null);
                        frame.setVisible(true);
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al mostrar medicamentos: " + ex.getMessage());
                }
            }
        });

        borrarTratamientoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Tratamiento seleccionado = (Tratamiento) tratamientoBorrarCombo.getSelectedItem();
                if (seleccionado == null) {
                    JOptionPane.showMessageDialog(null, "Seleccione un tratamiento para borrar.");
                    return;
                }
                int confirm = JOptionPane.showConfirmDialog(null,
                        "¿Está seguro de que desea eliminar el tratamiento '" +
                                seleccionado.getNombre() + "'?",
                        "Confirmar eliminación",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        new TratamientoDAO().deleteTratamiento(seleccionado.getId());
                        refreshTratamientosCombos();
                        JOptionPane.showMessageDialog(null, "Tratamiento eliminado con éxito.");
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Error al eliminar tratamiento: " + ex.getMessage());
                    }
                }
            }
        });


        agregarRecordatorioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Medicamento medicamento = (Medicamento) ComboMedicamentoRecordatorios.getSelectedItem();
                if (medicamento == null) {
                    JOptionPane.showMessageDialog(null, "Seleccione un medicamento.");
                    return;
                }

                String horaStr = (String) comboBoxhora.getSelectedItem();
                String minutoStr = (String) comboBoxminuto.getSelectedItem();

                LocalTime hora = LocalTime.of(
                        Integer.parseInt(horaStr),
                        Integer.parseInt(minutoStr)
                );

                try {
                    new MedicamentoDAO().actualizarHoraRecordatorio(medicamento.getId(), hora);
                    JOptionPane.showMessageDialog(null, "Recordatorio guardado para las " + hora);
                    Recordatorio.programarRecordatorioFijo(medicamento, hora);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al guardar el recordatorio: " + ex.getMessage());
                }

            }
        });


        modificarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Medicamento seleccionado = (Medicamento) comboBoxModificarMedicamento.getSelectedItem();
                if (seleccionado == null) {
                    JOptionPane.showMessageDialog(pGeneral, "Por favor, seleccione un medicamento para modificar.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                showModificarMedicamentoDialog(seleccionado);
            }
        });




    }

    private void refreshMedicamentosCombo() {
        try {
            comboBox2.removeAllItems();
            for (Medicamento m : new MedicamentoDAO().getAllMedicamentos()) {
                comboBox2.addItem(m);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar medicamentos: " + e.getMessage());
        }
    }

    private void refreshTratamientosCombos() {
        try {
            comboBox1.removeAllItems();
            tratamientoBorrarCombo.removeAllItems();
            for (Tratamiento t : new TratamientoDAO().getAllTratamientos()) {
                comboBox1.addItem(t);
                tratamientoBorrarCombo.addItem(t);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar tratamientos: " + e.getMessage());
        }
    }

    public JPanel getMainPanel() {
        return pGeneral;
    }

    private void refreshComboBoxBorrarMedicamento() {
        try {
            comboBoxBorrarMedicamento.removeAllItems();
            for (Medicamento m : new MedicamentoDAO().getAllMedicamentos()) {
                comboBoxBorrarMedicamento.addItem(m);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar medicamentos: " + e.getMessage());
        }
    }

    private void refreshComboBoxModificarMedicamento() {
        try {
            comboBoxModificarMedicamento.removeAllItems();
            for (Medicamento m : new MedicamentoDAO().getAllMedicamentos()) {
                comboBoxModificarMedicamento.addItem(m);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar medicamentos: " + e.getMessage());
        }
    }

    private void refreshComboBoxseleccionarMedicamento() {
        try {
            ComboMedicamentoRecordatorios.removeAllItems();
            for (Medicamento m : new MedicamentoDAO().getAllMedicamentos()) {
                ComboMedicamentoRecordatorios.addItem(m);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar medicamentos: " + e.getMessage());
        }
    }

    private void cargarRecordatoriosPendientes() {
        String[] columnas = {"Nombre", "Dosis", "Intervalo", "Unidad", "Hora de Recordatorio"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0);

        model.addRow(new Object[]{"Nombre", "Dosis", "Intervalo", "Unidad", "Hora de Recordatorio"});

        try {
            List<Medicamento> lista = new MedicamentoDAO().getMedicamentosConRecordatorio();
            for (Medicamento m : lista) {
                Object[] fila = {
                        m.getNombre(),
                        m.getDosis(),
                        m.getFrecuencia().getIntervalo(),
                        m.getFrecuencia().getUnidad(),
                        m.getHoraRecordatorio().toString()
                };
                model.addRow(fila);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar recordatorios: " + e.getMessage());
        }

        tabltablaRecordatorios.setModel(model);
    }

    private void showModificarMedicamentoDialog(Medicamento medicamento) {
        JTextField nombreField = new JTextField(medicamento.getNombre(), 20);
        JTextField dosisField = new JTextField(medicamento.getDosis(), 20);
        JTextField intervaloField = new JTextField(String.valueOf(medicamento.getFrecuencia().getIntervalo()), 5);
        JComboBox<String> unidadCombo = new JComboBox<>(new String[]{"Horas", "Días", "Semanas"});
        unidadCombo.setSelectedItem(medicamento.getFrecuencia().getUnidadDeTiempo());

        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.add(new JLabel("Nombre:"));
        panel.add(nombreField);
        panel.add(new JLabel("Dosis:"));
        panel.add(dosisField);
        panel.add(new JLabel("Intervalo:"));
        panel.add(intervaloField);
        panel.add(new JLabel("Unidad de Tiempo:"));
        panel.add(unidadCombo);

        int result = JOptionPane.showConfirmDialog(pGeneral, panel, "Modificar Medicamento",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                medicamento.setNombre(nombreField.getText().trim());
                medicamento.setDosis(dosisField.getText().trim());
                int intervalo = Integer.parseInt(intervaloField.getText().trim());
                String unidad = (String) unidadCombo.getSelectedItem();
                medicamento.setFrecuencia(new Frecuencia(intervalo, unidad));

                new MedicamentoDAO().updateMedicamento(medicamento);
                refreshMedicamentosCombo();
                refreshComboBoxBorrarMedicamento();

                JOptionPane.showMessageDialog(pGeneral, "Medicamento actualizado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(pGeneral, "El intervalo debe ser un número válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(pGeneral, "Error al actualizar en la base de datos: " + ex.getMessage(), "Error de BD", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                JFrame frame = new JFrame("Gestor de Medicamentos");
                dosisGUI gui = new dosisGUI();
                frame.setContentPane(gui.getMainPanel());
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