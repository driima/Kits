package com.dragonphase.kits;

import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;
import org.mcstats.Metrics.Plotter;

import com.dragonphase.kits.api.Kit;
import com.dragonphase.kits.api.KitManager;
import com.dragonphase.kits.commands.KitCommandExecutor;
import com.dragonphase.kits.commands.KitsCommandExecutor;
import com.dragonphase.kits.configuration.CollectionManager;
import com.dragonphase.kits.listeners.EventListener;
import com.dragonphase.kits.util.DelayedPlayer;
import com.dragonphase.kits.util.Message;

public class Kits extends JavaPlugin {

    private static Kits instance;

    private KitManager kitManager;
    private CollectionManager collectionManager;

    @Override
    public void onDisable() {
        getCollectionManager().save();
    }

    @Override
    public void onEnable() {
        Message.setParent(this);

        kitManager = new KitManager(this);
        collectionManager = new CollectionManager(this);

        reload();

        registerEvents();
        registerCommands();
        registerConfigurationSerializables();
        
        registerMetrics();

        instance = this;
    }
    
    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new EventListener(this), this);
    }
    
    private void registerCommands() {
        getCommand("kits").setExecutor(new KitsCommandExecutor(this));
        getCommand("kit").setExecutor(new KitCommandExecutor(this));
    }
    
    private void registerConfigurationSerializables() {
        ConfigurationSerialization.registerClass(Kit.class);
        ConfigurationSerialization.registerClass(DelayedPlayer.class);
    }
    
    private void registerMetrics() {
        try {
            Metrics metrics = new Metrics(this);
            
            metrics.createGraph("Kits").addPlotter(new Plotter("Number of kits created") {
                public int getValue() {
                    return getCollectionManager().getKits().size();
                }
            });
            
            metrics.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reload() {
        getCollectionManager().reload();
    }

    public String getPluginDetails() {
        return getPluginName() + " " + getPluginVersion();
    }

    public String getPluginName() {
        return getDescription().getName();
    }

    public String getPluginVersion() {
        return getDescription().getVersion();
    }

    public KitManager getKitManager() {
        return kitManager;
    }

    public CollectionManager getCollectionManager() {
        return collectionManager;
    }

    public static Kits getInstance() {
        return instance;
    }
}
