package demo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileEditor extends JPanel implements ActionListener {
    private final JFileChooser fc = new JFileChooser();
    private JFrame f;
    private JButton openBtn = new JButton("Open File");
    private JButton saveBtn = new JButton("Save");
    private JButton backBtn = new JButton("zurück");
    private JTextArea textArea = new JTextArea(20, 30);
    private JScrollPane scrollPane = new JScrollPane(textArea);
    private File file;
    private Scanner sc;

    public FileEditor(JFrame f) {
        this.f = f;
        initialize();
        setLayout(new BorderLayout());

        JPanel btns = new JPanel();
        btns.add(openBtn);
        btns.add(saveBtn);
        backBtn.addActionListener(this);
        openBtn.addActionListener(this);
        saveBtn.addActionListener(this);

        add(btns, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(backBtn, BorderLayout.PAGE_END);
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

    @Override
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
        else if(e.getSource() == backBtn){
            MainMenu mM = new MainMenu(f);
            f.setContentPane(mM);
            f.revalidate();
        }
    }
}