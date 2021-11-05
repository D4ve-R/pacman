package demo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileEditor extends JPanel implements ActionListener {
    private final JFileChooser fc = new JFileChooser();
    private JButton openBtn = new JButton("Open File");
    private JButton saveBtn = new JButton("Save");
    private JTextArea textArea = new JTextArea();
    private JScrollPane scrollPane = new JScrollPane(textArea);
    private File file;
    private Scanner sc;

    public FileEditor() {
        initialize();
        setLayout(new BorderLayout());

        JPanel btns = new JPanel();
        btns.add(openBtn);
        btns.add(saveBtn);
        openBtn.addActionListener(this);
        saveBtn.addActionListener(this);

        add(btns, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void initialize() {
        try{
            file = new File("./ressources/levels/data.txt");
            sc = new Scanner(file);
            fc.setCurrentDirectory(file);
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }

        while(sc.hasNextLine()){
            textArea.append(sc.nextLine());
            textArea.append("\n");
        }
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == openBtn){
            int returnVal = fc.showOpenDialog(FileEditor.this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                file = fc.getSelectedFile();
                textArea.setText("");
                try{
                    sc = new Scanner(file);
                }catch(FileNotFoundException ex){
                    ex.printStackTrace();
                }

                while(sc.hasNextLine()){
                    textArea.append(sc.nextLine());
                    textArea.append("\n");
                }
            }
        }
        else if(e.getSource() == saveBtn){
            String s = textArea.getText();
            try {
                FileWriter myWriter = new FileWriter(file);
                myWriter.write(s);
                myWriter.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}