package com.dragonphase.kits.configuration;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.org.apache.commons.lang3.ArrayUtils;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.dragonphase.kits.Kits;
import com.dragonphase.kits.api.Kit;
import com.dragonphase.kits.util.DelayedPlayer;

public class Collections {
	//TODO: Remove static nature of this file
    
	public static Config KitConfig;
	public static List<Kit> KitList;
	
	public static Config PlayerConfig;
	public static List<DelayedPlayer> PlayerList;
	
	public static void save(){
		KitConfig.set("kits", KitList);
		KitConfig.save();
		
		for (DelayedPlayer player : PlayerList)
			player.sortKits();
		
		PlayerConfig.set("players", PlayerList);
		PlayerConfig.save();
	}
	
	@SuppressWarnings("unchecked")
	public static void load(Kits plugin){
		KitConfig = new Config(plugin, "kits");
		migrateOldKitsFile();
		KitList = KitConfig.get("kits") == null ? new ArrayList<Kit>() : (List<Kit>) KitConfig.get("kits");
		
		PlayerConfig = new Config(plugin, "players");
		PlayerList = PlayerConfig.get("players") == null ? new ArrayList<DelayedPlayer>() : (List<DelayedPlayer>) PlayerConfig.getList("players");
	}
	
	@SuppressWarnings("unchecked")
    private static void migrateOldKitsFile(){
	    if (KitConfig.contains("kits")) return;
	    
	    List<Kit> newKits = new ArrayList<Kit>();
	    
	    for (String key : KitConfig.getKeys(false)){
	        ItemStack[] items = ((ArrayList<ItemStack>)KitConfig.get(key + ".kit")).toArray(new ItemStack[((ArrayList<ItemStack>)KitConfig.get(key + ".kit")).size()]);
	        ArrayUtils.reverse(items);
	        newKits.add(new Kit(key, items, KitConfig.getLong(key + ".delay"), KitConfig.getBoolean(key + ".overwrite"), true));
	    }
	    
	    for (Kit kit : newKits)
	        KitConfig.set(kit.getName(), null);
	    
	    KitConfig.set("kits", newKits);
	}
	
	public static void reload(Kits plugin){
		if (KitConfig != null && PlayerConfig != null) save();
		load(plugin);
	}
	
	public static Kit getKit(String name){
		for (Kit kit : KitList){
			if (kit.getName().equalsIgnoreCase(name)) return kit;
		}
		return null;
	}
	
	public static DelayedPlayer getDelayedPlayer(Player player){
		for (DelayedPlayer delayedPlayer : PlayerList){
		    try{
	            if (delayedPlayer.getPlayer().getUniqueId().equals(player.getUniqueId())) return delayedPlayer;
		    }catch (Exception ex){ }
		}
		DelayedPlayer delayedPlayer = new DelayedPlayer(player);
		PlayerList.add(delayedPlayer);
		return delayedPlayer;
	}
}
