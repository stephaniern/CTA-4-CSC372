import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class MenubarAppGUI extends JFrame {
    private JTextArea textArea;
    private JMenuItem colorItem;
    private int greenHue; 

    public MenubarAppGUI() {
        setTitle("User Interface");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Random rand = new Random();
        greenHue = rand.nextInt(61) + 90; 

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        menuBar.add(menu);

        JMenuItem timeItem = new JMenuItem("Print Date and Time");
        JMenuItem logItem = new JMenuItem("Save to log.txt");
        colorItem = new JMenuItem("Change Color (Hue: " + greenHue + ")"); 
        JMenuItem exitItem = new JMenuItem("Exit");

        menu.add(timeItem);
        menu.add(logItem);
        menu.add(colorItem);
        menu.addSeparator();
        menu.add(exitItem);

        setJMenuBar(menuBar);

        textArea = new JTextArea();
        textArea.setEditable(false);
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        timeItem.addActionListener(_ -> printTime());
        logItem.addActionListener(_ -> saveLog());
        colorItem.addActionListener(_ -> changeColor());
        exitItem.addActionListener(_ -> exitApp());
    }

    private void printTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        textArea.append(dtf.format(now) + "\n");
    }

    private void saveLog() {
        try (FileWriter writer = new FileWriter("log.txt", true)) {
            writer.write(textArea.getText());
            textArea.setText(""); // Clear text area after saving
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving log file!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void changeColor() {
        Color newColor = Color.getHSBColor(greenHue / 360f, 0.9f, 0.8f);
        getContentPane().setBackground(newColor); 
        ((JComponent) getContentPane()).setOpaque(true);
        
        JOptionPane.showMessageDialog(this, "Background changed to Green Hue: " + greenHue,
                "Color Changed", JOptionPane.INFORMATION_MESSAGE);
    }

    private void exitApp() {
        System.exit(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BankAppGUI app = new BankAppGUI();
            app.setVisible(true);
        });
    }
}