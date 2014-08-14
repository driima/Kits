package com.dragonphase.kits;

import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

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

    static {
        ConfigurationSerialization.registerClass(Kit.class);
        ConfigurationSerialization.registerClass(DelayedPlayer.class);
    }

    @Override
    public void onDisable() {
        getCollectionManager().save();
    }

    @Override
    public void onEnable() {
        Message.setParent(this);

        kitManager = new KitManager(this);
        collectionManager = new CollectionManager();

        reload();

        getServer().getPluginManager().registerEvents(new EventListener(this), this);

        getCommand("kits").setExecutor(new KitsCommandExecutor(this));
        getCommand("kit").setExecutor(new KitCommandExecutor(this));

        instance = this;
    }

    public void reload() {
        getCollectionManager().reload(this);
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
