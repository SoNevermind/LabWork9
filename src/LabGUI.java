import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.*;
import java.io.PrintWriter;
import java.io.*;
import java.util.Scanner;

class JMenuTest extends JFrame{
    public static final long serialVersionUID = 1L;
    JTextField textField, textField2;
    private JLabel label;

    public JMenuTest(){
        super("Лабораторная номер 9");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        label = new JLabel("Ввод:\n");

        textField = new JTextField(25);
        textField2 = new JTextField(25);
        textField.setToolTipText(" ");
        textField2.setToolTipText(" ");
        textField.setFont(new Font("Dialog", Font.PLAIN, 14));
        textField2.setFont(new Font("Dialog", Font.PLAIN, 14));
        textField.setHorizontalAlignment(JTextField.LEFT);
        textField2.setHorizontalAlignment(JTextField.LEFT);

        JPanel contents = new JPanel(new FlowLayout(FlowLayout.CENTER));
        contents.add(label);
        contents.add(textField);
        contents.add(textField2);
        setContentPane(contents);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createFileMenu());
        menuBar.add(createViewMenu());
        setJMenuBar(menuBar);
        setSize(500, 500);
        setVisible(true);
    }

    private JMenu createFileMenu(){
        JMenu file = new JMenu("Файл");
        JMenuItem open = new JMenuItem("Открыть");
        JMenuItem open2 = new JMenuItem("Сохранить как");
        JMenuItem exit = new JMenuItem(new ExitAction());
        file.add(open);
        file.addSeparator();
        file.add(open2);
        file.addSeparator();
        file.add(exit);

        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                System.out.println("ActionListener.actionPerformed: open");

                JFileChooser fileChooser = new JFileChooser(".");
                int userChoice = fileChooser.showOpenDialog(JMenuTest.this);
                if(userChoice == JFileChooser.APPROVE_OPTION){
                    String filename = fileChooser.getSelectedFile().getAbsolutePath();
                    JOptionPane.showMessageDialog(JMenuTest.this, filename, "Файл, который вы выбрали", JOptionPane.PLAIN_MESSAGE);
                }
            }
        });

        open2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                try(FileWriter writer = new FileWriter("text.txt", false)) {
                    PrintWriter wr = new PrintWriter(writer);

                    wr.println(textField.getText().getBytes());
                    wr.println(textField2.getText().getBytes());

                    wr.close();
                } catch (IOException ex){
                    System.out.println(ex.getMessage());
                }
            }
        });

        return file;
    }

    private JMenu createViewMenu(){
        JMenu viewMenu = new JMenu("Справка");
        JMenuItem about = new JMenuItem("О программе");
        viewMenu.add(about);

        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = "";
                message += about.getText() + ": Бабкин Данил, Лабораторная 9";
                JOptionPane.showMessageDialog(null, message, "О программе", JOptionPane.PLAIN_MESSAGE);
            }
        });

        return viewMenu;
    }

    class ExitAction extends AbstractAction{
        private static final long serialVersionUID = 1L;
        ExitAction(){
            putValue(NAME, "Выход");
        }

        public void actionPerformed(ActionEvent e){
            System.exit(0);
        }
    }
}