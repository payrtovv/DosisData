import javax.swing.*;

public class dosisGUI {
    private JTabbedPane tabbedPane1;
    private JButton agregarButton;
    private JButton modificarButton;
    private JButton mostrarButton;
    private JButton borrarButton;
    private JButton agregarButton1;
    private JButton modificarButton1;
    private JButton mostrarButton1;
    private JButton borrarButton1;
    private JPanel pGeneral;

    public dosisGUI() {
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("dosisGUI");
        frame.setContentPane(new dosisGUI().pGeneral);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
