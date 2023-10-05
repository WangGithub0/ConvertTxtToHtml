import argparse
import toml

def parse_command_line_args():
    parser = argparse.ArgumentParser(description="CLI Application with TOML Config File Support")
    
    # Add command-line arguments
    parser.add_argument("-c", "--config", help="Path to the TOML configuration file")

    args = parser.parse_args()
    return args

