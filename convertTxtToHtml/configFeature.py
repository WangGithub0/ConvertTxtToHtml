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

