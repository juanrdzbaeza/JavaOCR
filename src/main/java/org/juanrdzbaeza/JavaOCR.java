package org.juanrdzbaeza;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;

public class JavaOCR {

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

    }
}
