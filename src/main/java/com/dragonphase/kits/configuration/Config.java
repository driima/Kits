package com.dragonphase.kits.configuration;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Config extends YamlConfiguration {

    private JavaPlugin plugin;
    private String fileName;

    public Config(JavaPlugin plugin, String fileName) {
        this.plugin = plugin;
        this.fileName = fileName + (fileName.endsWith(".yml") ? "" : ".yml");

        createFiles();
    }

    public void createFiles() {
        try {
            File file = new File(plugin.getDataFolder(), fileName);
            if (!file.exists()) {
                if (plugin.getResource(fileName) != null) {
                    plugin.saveResource(fileName, false);
                } else {
                    save(file);
                }
            } else {
                load(file);
                try {
                    save(file);
                } catch (Exception ignored) {
                }
            }
        } catch (Exception ignored) {
        }
    }

    public void save() {
        try {
            save(new File(plugin.getDataFolder(), fileName));
        } catch (Exception ignored) {
        }
    }
}
