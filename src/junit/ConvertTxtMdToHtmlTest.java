package junit;


import org.junit.*;
import application.ConvertTxtMdToHtml;
import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


public class ConvertTxtMdToHtmlTest {
  @Test
  public void testVersionOption() {
    final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    ConvertTxtMdToHtml.main(new String[] {"-v"});

    assertEquals("Version command is triggered", "convertTxtToHtml version 0.1",
        outContent.toString().trim());
  }

  @Test
  public void testHelpOptionShort() {
    final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    final String expectedMessage = """
            Usage: convertTxtToHtml [options] <input>
            Options:
              --help, -h           Print this help message
              --version, -v        Print version information
              --output <dir>, -o   Specify the output directory (default: convertTxtToHtml)
              --lang, -l           Specify the language (default: en-CA)""";

    System.setOut(new PrintStream(outContent));

    ConvertTxtMdToHtml.main(new String[] {"-h"});

    assertEquals(expectedMessage,
            outContent.toString().trim().replaceAll("\r\n", "\n"));
  }

  @Test
  public void testHelpOptionVerbose() {
    final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    final String expectedMessage = """
            Usage: convertTxtToHtml [options] <input>
            Options:
              --help, -h           Print this help message
              --version, -v        Print version information
              --output <dir>, -o   Specify the output directory (default: convertTxtToHtml)
              --lang, -l           Specify the language (default: en-CA)""";

    System.setOut(new PrintStream(outContent));

    ConvertTxtMdToHtml.main(new String[] {"--help"});

    assertEquals(expectedMessage,
            outContent.toString().trim().replaceAll("\r\n", "\n"));
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

  @Test
    public void testConvertLinks() {
        try {
            // Create an instance of ConvertTxtMdToHtml
            ConvertTxtMdToHtml converter = new ConvertTxtMdToHtml();

            // Use reflection to access the private convertLinks method
            Method convertLinksMethod = ConvertTxtMdToHtml.class.getDeclaredMethod("convertLinks", String.class);
            convertLinksMethod.setAccessible(true);

            // Test cases
            String input1 = "This is a [link](https://example.com).";
            String expectedOutput1 = "This is a <a href=\"https://example.com\">link</a>.";
            assertEquals(expectedOutput1, convertLinksMethod.invoke(converter, input1));

            // Add more test cases as needed

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            fail("Exception thrown during test: " + e.getMessage());
        }
    }

}
