package org.juanrdzbaeza;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;

public class Main {

    public String getImgText(String imagePath) {
        ITesseract iTesseract = new Tesseract();
        try {
            iTesseract.setLanguage("spa");
            String imgText = iTesseract.doOCR(new File(imagePath));
            return imgText;
        } catch (TesseractException e) {
            e.getMessage();
            return "Error al procesar la imagen.";
        }
    }


    public static void main(String[] args) {
        Main app = new Main();
        System.out.println(app.getImgText("src/main/resources/images-tests/2024-10-17_02-02.png"));
    }
}