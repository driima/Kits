package com.dragonphase.Kits.Util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;

public class DelayedPlayer implements ConfigurationSerializable{

    private OfflinePlayer player;
	private HashMap<String, Long> kits;
	
	public DelayedPlayer(Player player){
		this.player = player;
		this.kits = new HashMap<String, Long>();
	}
	
	public DelayedPlayer(UUID player, HashMap<String, Long> kits){
		this.player = Bukkit.getPlayer(player);
		this.kits = kits;
	}
	
	public Player GetPlayer(){
		return player.isOnline() ? player.getPlayer() : null;
	}
	
	public void AddKit(Kit kit){
		kits.put(kit.GetName(), System.currentTimeMillis());
	}
	
	public boolean PlayerDelayed(Kit kit){
		boolean delayed = false;
		if (kits.containsKey(kit.GetName())){
			delayed = System.currentTimeMillis() - kits.get(kit.GetName()) < kit.GetDelay();
			if (!delayed) kits.remove(kit.GetName());
		}
		return delayed;
	}
	
	public void SortKits(){
		Iterator<Entry<String,Long>> iter = kits.entrySet().iterator();
		while (iter.hasNext()) {
		    Entry<String,Long> entry = iter.next();
		    if (System.currentTimeMillis() - entry.getValue() >= Collections.GetKit(entry.getKey()).GetDelay()){
		        iter.remove();
		    }
		}
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> result = new HashMap<String, Object>();

		result.put("player", GetPlayer().getUniqueId().toString());
		result.put("kits", kits);
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public static DelayedPlayer deserialize(Map<String, Object> args){
		return new DelayedPlayer(UUID.fromString((String)args.get("player")), (HashMap<String, Long>)args.get("kits"));
	}

}
