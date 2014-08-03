package com.dragonphase.Kits;

import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

import com.dragonphase.Kits.Api.KitManager;
import com.dragonphase.Kits.Commands.KitCommandExecutor;
import com.dragonphase.Kits.Commands.KitsCommandExecutor;
import com.dragonphase.Kits.Listeners.EventListener;
import com.dragonphase.Kits.Util.Collections;
import com.dragonphase.Kits.Util.DelayedPlayer;
import com.dragonphase.Kits.Util.Kit;
import com.dragonphase.Kits.Util.Message;

public class Kits extends JavaPlugin{
    
    private KitManager kitManager;
    
    static{
        ConfigurationSerialization.registerClass(Kit.class);
        ConfigurationSerialization.registerClass(DelayedPlayer.class);
    }
    
    @Override
    public void onDisable(){
        Collections.save();
    }
    
    @Override
    public void onEnable(){
        Message.setParent(this);
        
        kitManager = new KitManager();
        
        reload();
        
        getServer().getPluginManager().registerEvents(new EventListener(this), this);
        
        getCommand("kits").setExecutor(new KitsCommandExecutor(this));
        getCommand("kit").setExecutor(new KitCommandExecutor(this));
    }
    
    public void reload(){
    	Collections.reload(this);
    }
    
    public String getPluginDetails(){
        return getPluginName() + " " + getPluginVersion();
    }
    
    public String getPluginName(){
        return getDescription().getName();
    }
    
    public String getPluginVersion(){
        return getDescription().getVersion();
    }
    
    public KitManager getKitManager(){
        return kitManager;
    }
}
