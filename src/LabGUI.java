import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.*;
import java.io.PrintWriter;
import java.io.*;
import java.util.regex.*;
import java.io.BufferedReader;

class JMenuTest extends JFrame{
    public static final long serialVersionUID = 1L;
    JTextField textField, textField2;
    private JLabel label;

    public JMenuTest(){
        super("Лабораторная номер 9");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        textField = new JTextField(25);
        textField2 = new JTextField(25);
        textField.setToolTipText(" ");
        textField2.setToolTipText(" ");
        textField.setFont(new Font("Dialog", Font.PLAIN, 14));
        textField2.setFont(new Font("Dialog", Font.PLAIN, 14));
        textField.setHorizontalAlignment(JTextField.LEFT);
        textField2.setHorizontalAlignment(JTextField.LEFT);

        JPanel contents = new JPanel(new FlowLayout(FlowLayout.CENTER));
        contents.add(textField);
        contents.add(textField2);
        setContentPane(contents);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createFileMenu());
        menuBar.add(createViewMenu());
        setJMenuBar(menuBar);
        setSize(800, 500);
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

                JFileChooser fileChooser = new JFileChooser();
                int returnVal = fileChooser.showOpenDialog(null);
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                    System.out.println("You chose to open this file: " + fileChooser.getSelectedFile().getName());

                    File file = new File("input.txt");
                    String txt = new String();
                    try {
                        FileReader fr = new FileReader(file);
                        BufferedReader br = new BufferedReader(fr);
                        String str;
                        while ((str = br.readLine()) != null) {
                            txt += str;
                        }
                    } catch (IOException e1) {
                        JOptionPane.showMessageDialog(null, "Ну не получилось :(");
                    }

                    textField.setText(txt);
                    textField2.setText(variant1(txt));
                }
            }
        });

        open2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                try(FileWriter writer = new FileWriter("text.txt", false)) {
                    PrintWriter wr = new PrintWriter(writer);

                    wr.println(textField2.getText());

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
                message += about.getText() + ": Бабкин Данил, Лабораторная работа №9. Создание графического интерфейса пользователя. Регулярные выражения";
                JOptionPane.showMessageDialog(null, message, "О программе", JOptionPane.PLAIN_MESSAGE);
            }
        });

        return viewMenu;
    }

    public static String variant1(String text){
        String regex = "^\\{?\\p{XDigit}{8}-(?:\\p{XDigit}{4}-){3}\\p{XDigit}{12}}?$";
        String s = ("e02fd0e4-00fd-090A-ca30-0d00a0038ba0");
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(s);
        while(matcher.find()) {
            System.out.println(matcher.group());
        }

        return s;
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