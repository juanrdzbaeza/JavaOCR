package org.juanrdzbaeza;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JavaOCR extends JFrame {

    private final JTextField filePathTextField;
    private final JTextArea textArea;

    public JavaOCR() {
        setTitle("OCR App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Load the icon image
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/JavaOCR-icon.jpg")));
        setIconImage(icon.getImage());

        // Create components
        JLabel filePathLabel = new JLabel("Archivo de imagen:");
        filePathTextField = new JTextField(40);
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
        browseButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(JavaOCR.this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                filePathTextField.setText(selectedFile.getAbsolutePath());
                String imgText = getImgText(selectedFile.getAbsolutePath());
                textArea.setText(imgText);
                textArea.setLineWrap(true);
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
            return iTesseract.doOCR(new File(imagePath));
        } catch (TesseractException e) {
            Logger.getLogger(JavaOCR.class.getName()).log(Level.SEVERE, "Error al procesar la imagen.", e);
            return "Error al procesar la imagen.";
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(JavaOCR::new);
    }
}
