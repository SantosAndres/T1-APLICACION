import javax.swing.SwingUtilities;


public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            NaturalGUI ventana = new NaturalGUI();
            ventana.setVisible(true);
        });
    }
}
