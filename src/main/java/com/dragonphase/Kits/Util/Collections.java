package com.dragonphase.Kits.Util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import com.dragonphase.Kits.Kits;

public class Collections {
	
	public static Config KitConfig;
	public static List<Kit> KitList;
	
	public static Config PlayerConfig;
	public static List<DelayedPlayer> PlayerList;
	
	public static void Save(){
		KitConfig.set("kits", KitList);
		KitConfig.save();
		
		for (DelayedPlayer player : PlayerList)
			player.SortKits();
		
		PlayerConfig.set("players", PlayerList);
		PlayerConfig.save();
	}
	
	@SuppressWarnings("unchecked")
	public static void Load(Kits plugin){
		KitConfig = new Config(plugin, "kits");
		KitList = KitConfig.get("kits") == null ? new ArrayList<Kit>() : (List<Kit>) KitConfig.get("kits");
		
		PlayerConfig = new Config(plugin, "players");
		PlayerList = PlayerConfig.get("players") == null ? new ArrayList<DelayedPlayer>() : (List<DelayedPlayer>) PlayerConfig.getList("players");
	}
	
	public static void Reload(Kits plugin){
		if (KitConfig != null && PlayerConfig != null) Save();
		Load(plugin);
	}
	
	public static Kit GetKit(String name){
		for (Kit kit : KitList){
			if (kit.GetName().equalsIgnoreCase(name)) return kit;
		}
		return null;
	}
	
	public static DelayedPlayer GetDelayedPlayer(Player player){
		for (DelayedPlayer delayedPlayer : PlayerList){
			if (delayedPlayer.GetPlayer().getUniqueId().equals(player.getUniqueId())) return delayedPlayer;
		}
		DelayedPlayer delayedPlayer = new DelayedPlayer(player);
		PlayerList.add(delayedPlayer);
		return delayedPlayer;
	}
}
