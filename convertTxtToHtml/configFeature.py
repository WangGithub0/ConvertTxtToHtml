import argparse
import toml

def parse_command_line_args():
    parser = argparse.ArgumentParser(description="CLI Application with TOML Config File Support")
    
    # Add command-line arguments
    parser.add_argument("-c", "--config", help="Path to the TOML configuration file")

    args = parser.parse_args()
    return args

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
    # Parse command-line arguments
    args = parse_command_line_args()

    # If a config file is provided, load and use it
    if args.config:
        config_data = load_config_file(args.config)
    else:
        # Default values if config file not provided
        config_data = {
            "output": "./build",
            "stylesheet": "https://cdn.jsdelivr.net/npm/water.css@2/out/water.css",
            "lang": "en",  # Default language
        }

    # Access config values
    output_dir = config_data.get("output")
    stylesheet_url = config_data.get("stylesheet")
    lang = config_data.get("lang")

    # Example: Using the config values in your application
    print(f"Output Directory: {output_dir}")
    print(f"Stylesheet URL: {stylesheet_url}")
    print(f"Language: {lang}")

    # Your code here: Use the config values in your application

if __name__ == "__main__":
    main()