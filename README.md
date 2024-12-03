# JavaOCR

JavaOCR is a simple Java application that utilizes the Tesseract OCR engine to perform Optical Character Recognition (OCR) on images.

### Prerequisites

Before running JavaOCR, make sure you have the following prerequisites installed:

1. Java Development Kit (JDK) 8 or later
2. Tesseract OCR engine (version 4.1.1 or later)
3. Tesseract language data files for the languages you want to recognize

### Installation

1. Download the latest version of JavaOCR from the GitHub repository: https://github.com/juanrdzbaeza/JavaOCR
2. Extract the downloaded ZIP file to your desired location.

### Configuration

1. Download the Spanish language data file from the Tesseract GitHub repository: https://github.com/tesseract-ocr/tessdata/raw/main/spa.traineddata
2. Create a directory named `tessdata` in your project's root directory (e.g., `/Users/juan/IdeaProjects/JavaOCR/tessdata`).
3. Move the downloaded `spa.traineddata` file into the `tessdata` directory.
4. Set the `TESSDATA_PREFIX` environment variable to the path of the `tessdata` directory.

For macOS, you can set the environment variable using the following command in your terminal:

```bash
export TESSDATA_PREFIX=/Users/juan/IdeaProjects/JavaOCR/tessdata
```

After setting the environment variable, you can run your Java application again. The Tesseract library should now be able to load the Spanish language data file and perform OCR on images.

If you're using an IDE like IntelliJ IDEA, you can also set the environment variable in the IDE's run configuration. Follow these steps:

1. Open the Run Configuration for your Java application (e.g., JavaOCR).
2. Go to the "Environment Variables" tab.
3. Click the "+" button to add a new environment variable.
4. Set the name as `TESSDATA_PREFIX` and the value as the path to your `tessdata` directory (`/Users/juan/IdeaProjects/JavaOCR/tessdata`).
5. Save the changes and run your application.

### Usage

To use JavaOCR, follow these steps:

1. Run the JavaOCR application.
2. Click on the "Examinar" button to select an image file for OCR.
3. The recognized text will be displayed in the text area below the image.

### Troubleshooting

If you encounter any issues with JavaOCR, please check the following:

1. Ensure that you have installed the required prerequisites.
2. Verify that the `TESSDATA_PREFIX` environment variable is set correctly.
3. Check the logs for any error messages related to Tesseract.

If you still encounter problems, feel free to open an issue on the GitHub repository: https://github.com/juanrdzbaeza/JavaOCR/issues

### Contributing

Contributions to JavaOCR are welcome! If you find any bugs or have suggestions for improvements, please submit a pull request or open an issue on the GitHub repository.

### License

JavaOCR is licensed under the MIT License. See the `LICENSE` file for more information.

### Acknowledgments

JavaOCR uses the Tesseract OCR engine, which is developed by Google. The Tesseract language data files are provided by the Tesseract OCR project on GitHub.