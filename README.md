# ConvertTxtMdToHtml
A command-line tool can process input .txt or .md files into generated .html files.

## Installation
1. Download and Install Java Development Kit according to the Oracle [JDK Installation Guide](https://docs.oracle.com/en/java/javase/20/install/overview-jdk-installation.html#GUID-8677A77F-231A-40F7-98B9-1FD0B48C346A).
   
2. Clone this repository to your local machine
    
   `git clone git@github.com:WangGithub0/ConvertTxtMdToHtml.git`
   
   `cd ConvertTxtMdToHtml`


## Usage

To convert a `.txt` / `.md` file to `.html`:

`
java src/ConvertTxtMdToHtml.java <path-to-txt-file>
`



To specify a different output directory:

`
java src/ConvertTxtMdToHtml.java <path-to-txt-file> --output <path-to-output-directory>
`

By default, if no output directory is specified, the `.html` files will be saved inside the `ConvertTxtMdToHtml` directory.

## Command Options:

* `--help` or `-h`: Display help information.
* `--version` or `-v`: Display the tool's version.
* `path`: Specify the path to a `.txt` file or a directory containing multiple .txt files. If a directory is provided, `tml` will recursively process all `.txt` files within.
* `--output` or `-o`: Specify a custom output directory. The tool will create the directory if it does not exist.
* * `--lang` or `-l`: Specify the language for html, default: en-CA

## Features

* Converts single or multiple `.txt` / `.md` files to `.html`.
* Same directory structure in output directory.
* Automatically parse title from  `.txt` / `.md` file.
* Converts .md syntax links to .html syntax links.
* Converts .md `---` to `<hr>`.
* Parse code block and can show line number by adding "showLineNumber" after first triple backticks 

## Examples
`java src/ConvertTxtMdToHtml.java ./examples`


## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you'd like to change.

## License

[MIT](https://github.com/mnajibi/tml/blob/main/LICENSE)
