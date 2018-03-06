import java.awt.*;
import java.awt.event.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import javax.swing.*;
import net.miginfocom.swing.MigLayout;

/*
 * Created by JFormDesigner on Wed Feb 28 20:20:57 JST 2018
 */



/**
 * @author Naoto Ozaki
 */
public class GUI extends JFrame {
    public GUI() {
        initComponents();
        myInitializations();
    }

    private void myInitializations() {
        final ByteArrayOutputStream bytes = new ByteArrayOutputStream() {
            @Override
            public synchronized void flush() throws IOException {
                textArea1.setText(toString());
            }
        };

        PrintStream out = new PrintStream(bytes, true);

        System.setErr(out);
        System.setOut(out);
        this.setVisible(true);
    }

    private void StartButton(ActionEvent e) {
        System.out.println("EnemyStats process started!");
        this.startButton.setEnabled(false);
        this.serverDropDownMenu.setEnabled(false);
        this.nameField.setEnabled(false);
        Thread enemyStatsThread = new Thread(new EnemyStats());
        enemyStatsThread.start();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Naoto Ozaki
        label1 = new JLabel();
        serverDropDownMenu = new JComboBox<>();
        scrollPane1 = new JScrollPane();
        textArea1 = new JTextArea();
        label2 = new JLabel();
        nameField = new JTextField();
        startButton = new JButton();

        //======== this ========
        setTitle("StarCraft2 EnemyStats");
        Container contentPane = getContentPane();
        contentPane.setLayout(new MigLayout(
            "hidemode 3",
            // columns
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[396,fill]",
            // rows
            "[]" +
            "[]" +
            "[186]"));

        //---- label1 ----
        label1.setText("Server");
        contentPane.add(label1, "cell 0 0");

        //---- serverDropDownMenu ----
        serverDropDownMenu.setModel(new DefaultComboBoxModel<>(new String[] {
            "Americas",
            "Europe",
            "Asia"
        }));
        contentPane.add(serverDropDownMenu, "cell 1 0");

        //======== scrollPane1 ========
        {

            //---- textArea1 ----
            textArea1.setEditable(false);
            scrollPane1.setViewportView(textArea1);
        }
        contentPane.add(scrollPane1, "cell 3 0 2 3,hmin 300");

        //---- label2 ----
        label2.setText("Name");
        contentPane.add(label2, "cell 0 1");

        //---- nameField ----
        nameField.setText("Dyukusi");
        contentPane.add(nameField, "cell 1 1");

        //---- startButton ----
        startButton.setText("Start");
        startButton.addActionListener(e -> StartButton(e));
        contentPane.add(startButton, "cell 0 2 2 1,aligny top,growy 0");
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Naoto Ozaki
    private JLabel label1;
    private JComboBox<String> serverDropDownMenu;
    private JScrollPane scrollPane1;
    private JTextArea textArea1;
    private JLabel label2;
    private JTextField nameField;
    private JButton startButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    public String getPlayerName() {
        return this.nameField.getText();
    }

    public Region getRegion() {
        return Region.valueOf(this.serverDropDownMenu.getSelectedItem().toString());
    }
}
