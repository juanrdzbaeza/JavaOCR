package org.juanrdzbaeza;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JavaOCR extends JFrame {

    private static final Logger LOGGER = Logger.getLogger(JavaOCR.class.getName());

    private final JTextField filePathTextField;
    private final JTextArea textArea;
    private final JButton browseButton;
    private final JButton pasteButton;

    public JavaOCR() {
        super("OCR App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Cargar icono de forma segura
        URL iconUrl = getClass().getResource("/img/JavaOCR-icon.jpg");
        if (iconUrl != null) {
            setIconImage(new ImageIcon(iconUrl).getImage());
        } else {
            LOGGER.log(Level.WARNING, "Icono no encontrado en /img/JavaOCR-icon.jpg");
        }

        // Componentes
        JLabel filePathLabel = new JLabel("Archivo de imagen:");
        filePathTextField = new JTextField(40);
        browseButton = new JButton("Examinar");
        pasteButton = new JButton("Pegar imagen");

        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(filePathLabel);
        topPanel.add(filePathTextField);
        topPanel.add(browseButton);
        topPanel.add(pasteButton);
        add(topPanel, BorderLayout.NORTH);

        add(new JScrollPane(textArea), BorderLayout.CENTER);

        browseButton.addActionListener(e -> openFileAndProcess());
        pasteButton.addActionListener(e -> {
            try {
                pasteFileAndProcess();
            } catch (IOException | UnsupportedFlavorException ex) {
                throw new RuntimeException(ex);
            }
        });

        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void openFileAndProcess() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(JavaOCR.this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            filePathTextField.setText(selectedFile.getAbsolutePath());
            runOcrInBackground(selectedFile);
        }
    }

    private void pasteFileAndProcess() throws IOException, UnsupportedFlavorException {
        Image image = (Image) Toolkit.getDefaultToolkit()
                .getSystemClipboard().getContents(null)
                .getTransferData(DataFlavor.imageFlavor);
        try {
            File tempFile = File.createTempFile("pasted_image", ".png");
            javax.imageio.ImageIO.write((BufferedImage) image, "png", tempFile);
            filePathTextField.setText(tempFile.getAbsolutePath());
            runOcrInBackground(tempFile);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error al pegar la imagen desde el portapapeles", ex);
            textArea.setText("Error al pegar la imagen desde el portapapeles.");
        }
    }

    private void runOcrInBackground(File imageFile) {
        browseButton.setEnabled(false);
        Cursor oldCursor = getCursor();
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        textArea.setText("Procesando...");

        SwingWorker<String, Void> worker = new SwingWorker<>() {
            @Override
            protected String doInBackground() {
                return getImgText(imageFile.getAbsolutePath());
            }

            @Override
            protected void done() {
                try {
                    String result = get();
                    textArea.setText(result);
                } catch (Exception ex) {
                    LOGGER.log(Level.SEVERE, "Error en OCR background task", ex);
                    textArea.setText("Error al procesar la imagen.");
                } finally {
                    browseButton.setEnabled(true);
                    setCursor(oldCursor);
                }
            }
        };
        worker.execute();
    }

    // java
    public String getImgText(String imagePath) {
        ITesseract iTesseract = new Tesseract();

        // Preferir la variable de entorno TESSDATA_PREFIX, si no existe usar el directorio tessdata del proyecto
        String tessdata = System.getenv("TESSDATA_PREFIX");
        if (tessdata == null || tessdata.isBlank()) {
            tessdata = System.getProperty("user.dir") + File.separator + "tessdata";
        }
        iTesseract.setDatapath(tessdata);

        try {
            iTesseract.setLanguage("spa");
            return iTesseract.doOCR(new File(imagePath));
        } catch (TesseractException e) {
            LOGGER.log(Level.SEVERE, "Error al procesar la imagen (TesseractException). Comprueba `tessdata` en: " + tessdata, e);
            return "Error al procesar la imagen: " + e.getMessage();
        } catch (Error e) {
            // Captura errores nativos (p. ej. Invalid memory access)
            LOGGER.log(Level.SEVERE, "Error nativo durante OCR. Verifica instalación/arquitectura de Tesseract y que las DLLs estén en `PATH`.", e);
            return "Error nativo: verifica que las librerías nativas de Tesseract estén instaladas y coincidan con la arquitectura de la JVM.";
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(JavaOCR::new);
    }
}