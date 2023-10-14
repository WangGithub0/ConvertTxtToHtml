const arg = require("arg");
const fs = require("fs").promises;
const toml = require("toml");

function parseCommandArgs() {
  const args = arg({
    "--config": String,
    "-c": "--config",
  });
  return args["--config"];
}

async function loadConfigFile(configFilePath) {
  try {
    const configData = await fs.readFile(configFilePath, "utf8");
    return toml.parse(configData);
  } catch (error) {
    if (error.code === "ENOENT") {
      console.error("Config file ${configFilePath} not found.");
    } else {
      console.error("Error parsing TOML in ${configFilePath}. Make sure it's valid TOML.");
    }
    process.exit(1);
  }
}

function main() {
  const configFilePath = parseCommandArgs();
  const configData = configFilePath ? loadConfigFile(configFilePath) : {};

  const defaultConfig = {
    output: "./build",
    stylesheet: "https://cdn.jsdelivr.net/npm/water.css@2/out/water.css",
    lang: "en",
  };

  const config = { ...defaultConfig, ...configData };

  const outputDir = config.output;
  const stylesheetURL = config.stylesheet;
  const lang = config.lang;

  console.log("Output Directory: ${outputDir}");
  console.log("Stylesheet URL: ${stylesheetURL}");
  console.log("Language: ${lang}");
}

main();
