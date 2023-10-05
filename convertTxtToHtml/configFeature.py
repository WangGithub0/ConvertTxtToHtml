import argparse
import toml

def parse_command_line_args():
    parser = argparse.ArgumentParser(description="CLI Application with TOML Config File Support")
    parser.add_argument("-c", "--config", help="Path to the TOML configuration file")
    return parser.parse_args()

def load_config_file(config_file_path):
    try:
        with open(config_file_path, "r") as file:
            config_data = toml.load(file)
        return config_data
    except FileNotFoundError:
        print(f"Config file '{config_file_path}' not found.")
        exit(1)
    except toml.TomlDecodeError:
        print(f"Error parsing TOML in '{config_file_path}'. Make sure it's valid TOML.")
        exit(1)

def main():
    args = parse_command_line_args()
    config_data = load_config_file(args.config) if args.config else {}

    default_config = {
        "output": "./build",
        "stylesheet": "https://cdn.jsdelivr.net/npm/water.css@2/out/water.css",
        "lang": "en",
    }

    config = {**default_config, **config_data}

    output_dir = config["output"]
    stylesheet_url = config["stylesheet"]
    lang = config["lang"]

    print(f"Output Directory: {output_dir}")
    print(f"Stylesheet URL: {stylesheet_url}")
    print(f"Language: {lang}")

if __name__ == "__main__":
    main()
