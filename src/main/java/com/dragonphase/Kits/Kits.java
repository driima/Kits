package com.dragonphase.Kits;

import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

import com.dragonphase.Kits.Commands.KitCommandExecutor;
import com.dragonphase.Kits.Commands.KitsCommandExecutor;
import com.dragonphase.Kits.Listeners.EventListener;
import com.dragonphase.Kits.Util.Collections;
import com.dragonphase.Kits.Util.DelayedPlayer;
import com.dragonphase.Kits.Util.Kit;
import com.dragonphase.Kits.Util.Message;

public class Kits extends JavaPlugin{
    
    static{
        ConfigurationSerialization.registerClass(Kit.class);
        ConfigurationSerialization.registerClass(DelayedPlayer.class);
    }
    
    @Override
    public void onDisable(){
        Collections.Save();
    }
    
    @Override
    public void onEnable(){
        Message.setParent(this);
        
        reload();
        
        getServer().getPluginManager().registerEvents(new EventListener(this), this);
        
        getCommand("kits").setExecutor(new KitsCommandExecutor(this));
        getCommand("kit").setExecutor(new KitCommandExecutor(this));
    }
    
    public void reload(){
    	Collections.Reload(this);
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
}
