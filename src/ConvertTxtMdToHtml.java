import java.io.*;
import java.nio.file.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConvertTxtToHtml {

    private static String defaultLang = "en-CA"; 

    public static void main(String[] args) {
        // parse arguments
        if (args.length == 0 || args[0].equals("-h") || args[0].equals("--help")) {
            printHelp();
            return;
        } else if (args[0].equals("-v") || args[0].equals("--version")) {
            printVersion();
            return;
        }

        String inputPath = args[0];
        String outputPath = "convertTxtToHtml";
        String outputArg = "convertTxtToHtml";
        String lang = defaultLang;

        // Check for the -l or --lang flag and set the language accordingly
        for (int i = 1; i < args.length; i++) {
            if (args[i].equals("--output") || args[i].equals("-o")) {
                if (i + 1 < args.length) {
                    outputArg = args[i + 1];
                    i++;
                } else {
                    System.err.println("Output path must be specified after -o flag.");
                    printHelp();
                    return; // Exit the program
                }
            } else if (args[i].equals("--lang") || args[i].equals("-l")) {
                if (i + 1 < args.length) {
                    lang = args[i + 1];
                    i++;
                } else {
                    System.err.println("Language must be specified after -l flag.");
                    printHelp();
                    return; // Exit the program
                }
            }
        }

        // Check if the specified output is a directory
        File outputDir = new File(outputArg);
        if (outputDir.isDirectory()) {
            outputPath = outputArg;
            deleteContents(outputDir);
        } else {
            // If it's a file, throw an error
            System.err.println("Output path must be a directory, not a file.");
            printHelp();
            return; // Exit the program
        }

        try {
            // Delete the output directory if it exists
            Files.deleteIfExists(Paths.get(outputPath));

            // Create the output directory
            Files.createDirectories(Paths.get(outputPath));

            File inputFile = new File(inputPath);

            if (inputFile.isDirectory()) {
                // Process all .txt files and .md files in the input directory
                File[] files = inputFile.listFiles((dir, name) -> (name.endsWith(".txt") || name.endsWith(".md")));
                if (files.length > 0) {
                    for (File file : files) {
                        processFile(file, outputPath, lang);
                    }
                } else {
                    System.err.println("No .txt or .md files found in the input directory.");
                }
            } else if (inputFile.isFile() && (inputPath.endsWith(".txt") || inputPath.endsWith(".md"))) {
                processFile(inputFile, outputPath, lang);
            } else {
                System.err.println("Invalid input file or directory.");
                printHelp();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void processFile(File inputFile, String outputPath, String lang) throws IOException {
        // Read the input .txt file
        List<String> lines = Files.readAllLines(inputFile.toPath());

        String fileName = inputFile.getName();
        String title = fileName.substring(0, fileName.lastIndexOf('.'));
        // Parse title (optional)

        Boolean hasTitle = false;
        if (lines.size() >= 3 && lines.get(1).isEmpty() && lines.get(2).isEmpty()) {
            title = lines.get(0);
            lines = lines.subList(3, lines.size());
            hasTitle = true;
        }

        // Create the HTML content
        StringBuilder htmlContent = new StringBuilder();
        htmlContent.append("<!doctype html>\n<html lang=\"").append(lang).append("\">\n<head>\n");
        htmlContent.append("<meta charset=\"utf-8\">\n");
        htmlContent.append("<title>").append(title != null ? title : "Untitled").append("</title>\n");
        htmlContent.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n");
        htmlContent.append("</head>\n<body>\n");

        if (hasTitle) {
            if (fileName.endsWith(".md"))
                title = convertLinks(title); // convert links in MD file
            htmlContent.append("<h1>").append(title).append("</h1>\n");
        }
        for (String line : lines) {
            if (line.isEmpty()) {
                htmlContent.append("<p></p>\n"); // Create a new paragraph
            } else {
                if (fileName.endsWith(".md"))
                    line = convertHorizontal(line); // convert horizontal in MD file
                line = convertLinks(line);   // convert links in MD file
                htmlContent.append("<p>").append(line).append("</p>\n");
            }
        }

        htmlContent.append("</body>\n</html>");

        // Write the HTML content to the output file
        String outputFileName = outputPath + File.separator + getFilenameNoExt(inputFile.getName()) + ".html";
        try (PrintWriter writer = new PrintWriter(outputFileName)) {
            writer.println(htmlContent.toString());
        }

        System.out.println("Processed: " + inputFile.getName() + " -> " + outputFileName);
    }

    private static void printHelp() {
        System.out.println("Usage: convertTxtToHtml [options] <input>");
        System.out.println("Options:");
        System.out.println("  --help, -h           Print this help message");
        System.out.println("  --version, -v        Print version information");
        System.out.println("  --output <dir>, -o   Specify the output directory (default: convertTxtToHtml)");
        System.out.println("  --lang, -l           Specify the language (default: en-CA)");
    }

    private static void printVersion() {
        System.out.println("convertTxtToHtml version 0.1");
    }

    private static void deleteContents(File directory) {
        File[] allContents = directory.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                if (file.isDirectory()) {
                    deleteContents(file);
                }
                file.delete();
            }
        }
    }

    // Returns the filename with the extension removed
    private static String getFilenameNoExt(String filename) {
        String newFilename = filename;
        int dotIndex = filename.lastIndexOf('.');
        if (dotIndex > 0)
            newFilename = filename.substring(0, dotIndex);
        return newFilename;
    }

    // Convert any .md syntax links in string to .html syntax links
    private static String convertLinks(String str) {
        String newStr = str;
        String linkText = "";
        String linkUrl = "";
        String mdLink = "";
        String htmlLink = "";
        Pattern linkTextPattern;
        Matcher linkTextMatcher;
        Pattern linkUrlPattern;
        Matcher linkUrlMatcher;

        // using regex to find links in .md files in the form of [linkText](linkUrl)
        Pattern pattern = Pattern.compile("\\[([^\\[\\]]*?)\\]\\((.*?)\\)");
        Matcher matcher = pattern.matcher(newStr);

        // loop through each md link and replace it with an html link
        while (matcher.find()) {
            mdLink = matcher.group(); // the md link

            // using regex to extract the link text from the md link
            linkTextPattern = Pattern.compile("\\[(.*?)\\]");
            linkTextMatcher = linkTextPattern.matcher(mdLink);
            if (linkTextMatcher.find()) {
                linkText = linkTextMatcher.group(1);
            } else {
                System.err.println("Unexpected error in convertLinks(). Link text not found.");
                return "";
            }

            // using regex to extract the link url from the md link
            linkUrlPattern = Pattern.compile("\\((.*?)\\)");
            linkUrlMatcher = linkUrlPattern.matcher(mdLink);
            if (linkUrlMatcher.find()) {
                linkUrl = linkUrlMatcher.group(1);
            } else {
                System.err.println("Unexpected error in convertLinks(). Link url not found.");
                return "";
            }

            // replace the md link with the html link
            htmlLink = "<a href=\"" + linkUrl + "\">" + linkText + "</a>";
            newStr = newStr.replace(mdLink, htmlLink);
        }

        return newStr;
    }

    // Convert horizontal in string to <hr>
    private static String convertHorizontal(String line) {
        String newLine = line.replaceAll("---", "<hr>");
        return newLine;
    }
}
