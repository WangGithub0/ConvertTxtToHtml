package com.converttxtmdtohtml;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.util.*;


public class ConvertTxtMdToHtmlTest {
    @Test
    public void testVersionOption() {
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        ConvertTxtMdToHtml.main(new String[]{"-v"});

        assertEquals("convertTxtToHtml version 1.0",
                outContent.toString().trim());
    }

    @Test
    public void testConversionWithSimpleText() {
        try {
            // Prepare a simple text and write it to a regular file
            String simpleText = "This is a simple line of text.";
            String filePath = "testFile.txt";
            File regularFile = new File(filePath);

            FileWriter fileWriter = new FileWriter(regularFile);
            fileWriter.write(simpleText);
            fileWriter.close();

            // Create the output directory
            String outputPath = "testOutput";
            File outputDir = new File(outputPath);
            outputDir.mkdir();

            // Convert the regular file to HTML
            ConvertTxtMdToHtml.processFile(regularFile, outputPath, "en-US");

            String fileName = "testFile.html";
            File outputFile = new File(outputDir, fileName); // Ensure using the output directory
            List<String> lines = Files.readAllLines(outputFile.toPath());

            // Assert the content of the output HTML
            assertEquals("<!doctype html>", lines.get(0).trim());
            assertEquals("<html lang=\"en-US\">", lines.get(1).trim());
            assertEquals("<head>", lines.get(2).trim());
            // ... and so on for other expected content in the HTML file

            // Clean up: delete the regular file
            regularFile.delete();

            // Clean up: delete generated HTML file
            outputFile.delete();

            // Clean up: delete the output directory
            outputDir.delete();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
