# ConvertTxtToHtml
A command-line tool can process input .txt files into generated .html files.

## Installation
1. Download and Install Java Development Kit according to the Oracle [JDK Installation Guide](https://docs.oracle.com/en/java/javase/20/install/overview-jdk-installation.html#GUID-8677A77F-231A-40F7-98B9-1FD0B48C346A).
   
2. Clone this repository to your local machine
    
   `git clone git@github.com:WangGithub0/ConvertTxtToHtml.git`
   
   `cd ConvertTxtToHtml`


## Usage

To convert a `.txt` file to `.html`:

`
java src/ConvertTxtToHtml.java <path-to-txt-file>
`



To specify a different output directory:

`
java src/ConvertTxtToHtml.java <path-to-txt-file> --output <path-to-output-directory>
`

By default, if no output directory is specified, the `.html` files will be saved inside the `ConvertTxtToHtml` directory.

## Command Options:

* `--help` or `-h`: Display help information.
* `--version` or `-v`: Display the tool's version.
* `path`: Specify the path to a `.txt` file or a directory containing multiple .txt files. If a directory is provided, `tml` will recursively process all `.txt` files within.
* `--output` or `-o`: Specify a custom output directory. The tool will create the directory if it does not exist.

## Features

* Converts single or multiple `.txt` files to `.html`.
* Same directory structure in output directory.
* Automatically parse title from  `.txt` file.

## Examples
`java src/ConvertTxtToHtml.java ./examples`


## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you'd like to change.

## License

[MIT](https://github.com/mnajibi/tml/blob/main/LICENSE)
