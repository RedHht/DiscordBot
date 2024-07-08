package ar.com.thiagoianuzzi.utils;

import ar.com.thiagoianuzzi.DiscordBot;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.Map;

public class ConfigUtils {

    public static Map<String, String> getConfig() {
        String configPath = "config/config.yml";

        try {
            FileInputStream config = new FileInputStream(configPath);

            return new Yaml().loadAs(config, Map.class);
        } catch (FileNotFoundException e) {
            saveConfigToDisk();

            return getConfig();
        }
    }

    private static void saveConfigToDisk() {

        File config = new File("config/config.yml");

        config.getParentFile().mkdirs();

        try (InputStream inputStream = DiscordBot.class.getClassLoader().getResourceAsStream("config.yml");
             OutputStream outputStream = new FileOutputStream(config)) {

            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            return;
        }

    }

}
