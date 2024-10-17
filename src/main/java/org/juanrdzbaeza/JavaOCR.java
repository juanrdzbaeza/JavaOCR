package org.juanrdzbaeza;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class JavaOCR extends JFrame {

    private JTextField filePathTextField;
    private JTextArea textArea;

    public JavaOCR() {
        setTitle("OCR App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create components
        JLabel filePathLabel = new JLabel("Archivo de imagen:");
        filePathTextField = new JTextField(50);
        JButton browseButton = new JButton("Examinar");
        textArea = new JTextArea();

        // Add components to the frame
        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(filePathLabel);
        topPanel.add(filePathTextField);
        topPanel.add(browseButton);
        add(topPanel, BorderLayout.NORTH);

        add(new JScrollPane(textArea), BorderLayout.CENTER);

        // Add action listener to the browse button
        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(JavaOCR.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    filePathTextField.setText(selectedFile.getAbsolutePath());
                    String imgText = getImgText(selectedFile.getAbsolutePath());
                    textArea.setText(imgText);
                }
            }
        });

//        pack();
        setSize(800, 600);
        setVisible(true);
    }

    public String getImgText(String imagePath) {
        ITesseract iTesseract = new Tesseract();
        try {
            iTesseract.setLanguage("spa");
            String imgText = iTesseract.doOCR(new File(imagePath));
            return imgText;
        } catch (TesseractException e) {
            e.printStackTrace();
            return "Error al procesar la imagen.";
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new JavaOCR();
            }
        });
    }
}
