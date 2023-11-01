# ConvertTxtMdToHtml
A command-line tool can process input .txt or .md files into generated .html files.



## Usage

To convert a `.txt` / `.md` file to `.html`:

`
java src/application/ConvertTxtMdToHtml.java <path-to-txt-file>
`



To specify a different output directory:

`
java src/application/ConvertTxtMdToHtml.java <path-to-txt-file> --output <path-to-output-directory>
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
`java src//application/ConvertTxtMdToHtml.java ./examples`


## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you'd like to change.
Please see the [contributors guide](https://github.com/WangGithub0/ConvertTxtToHtml/blob/main/CONTRIBUTING.md) for details.

## License

[MIT](https://github.com/mnajibi/tml/blob/main/LICENSE)
