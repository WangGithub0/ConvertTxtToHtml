import java.io.*;
import java.nio.file.*;
import java.util.List;

public class ConvertTxtToHtml {

	public static void main(String[] args) {
		// parse arguments
		if(args.length==0||args[0].equals("-h")||args[0].equals("--help")) {
			printHelp();
			return;
		}else if(args[0].equals("-v")||args[0].equals("--version")) {
			printVersion();
			return;
		}
		
		String inputPath=args[0];
		String outputPath="convertTxtToHtml";
		
		if (args.length >= 3 && (args[1].equals("--output") || args[1].equals("-o"))) {
            outputPath = args[2];
        }

        try {
            // Delete the output directory if it exists
            Files.deleteIfExists(Paths.get(outputPath));

            // Create the output directory
            Files.createDirectories(Paths.get(outputPath));

            File inputFile = new File(inputPath);

            if (inputFile.isDirectory()) {
                // Process all .txt files in the input directory
                File[] files = inputFile.listFiles((dir, name) -> name.endsWith(".txt"));
                if (files != null) {
                    for (File file : files) {
                        processFile(file, outputPath);
                    }
                }
            } else if (inputFile.isFile() && inputPath.endsWith(".txt")) {
                processFile(inputFile, outputPath);
            } else {
                System.err.println("Invalid input file or directory.");
                printHelp();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

	}
	
	
	private static void processFile(File inputFile, String outputPath) throws IOException {
        // Read the input .txt file
        List<String> lines = Files.readAllLines(inputFile.toPath());

        String fileName = inputFile.getName();
        String title = fileName.substring(0, fileName.lastIndexOf('.'));
        // Parse title (optional)

        Boolean hasTitle=false;
        if (lines.size() >= 3 && lines.get(1).isEmpty() && lines.get(2).isEmpty()) {
            title = lines.get(0);
            lines = lines.subList(3, lines.size());
            hasTitle=true;
        }

        // Create the HTML content
        StringBuilder htmlContent = new StringBuilder();
        htmlContent.append("<!doctype html>\n<html lang=\"en\">\n<head>\n");
        htmlContent.append("<meta charset=\"utf-8\">\n");
        htmlContent.append("<title>").append(title != null ? title : "Untitled").append("</title>\n");
        htmlContent.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n");
        htmlContent.append("</head>\n<body>\n");

        if(hasTitle){
            htmlContent.append("<h1>").append(title).append("</h1>\n");
        }
        for (String line : lines) {
            if (line.isEmpty()) {
                htmlContent.append("<p></p>\n"); // Create a new paragraph
            } else {
                htmlContent.append("<p>").append(line).append("</p>\n");
            }
        }

        htmlContent.append("</body>\n</html>");

        // Write the HTML content to the output file
        String outputFileName = outputPath + File.separator + inputFile.getName().replace(".txt", ".html");
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
	}
	
	private static void printVersion() {
		System.out.println("convertTxtToHtml version 0.1");
	}
	
}
