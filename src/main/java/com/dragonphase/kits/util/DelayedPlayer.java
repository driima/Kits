package com.dragonphase.kits.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;

import com.dragonphase.kits.api.Kit;
import com.dragonphase.kits.configuration.CollectionManager;

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
	
	public OfflinePlayer getPlayer(){
		return player.isOnline() ? (Player)player.getPlayer() : player;
	}
	
	public void addKit(Kit kit, long delay){
		kits.put(kit.getName(), System.currentTimeMillis() + delay);
	}
	
	public boolean playerDelayed(Kit kit){
		boolean delayed = false;
		if (kits.containsKey(kit.getName())){
			delayed = System.currentTimeMillis() - kits.get(kit.getName()) < kit.getDelay();
			if (!delayed) kits.remove(kit.getName());
		}
		return delayed;
	}
	
	public void sortKits(CollectionManager manager){
		Iterator<Entry<String,Long>> iter = kits.entrySet().iterator();
		while (iter.hasNext()) {
		    Entry<String,Long> entry = iter.next();
		    if (manager.getKit(entry.getKey()) == null || (System.currentTimeMillis() - entry.getValue() >= manager.getKit(entry.getKey()).getDelay())){
		        iter.remove();
		    }
		}
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> result = new HashMap<String, Object>();

		result.put("player", getPlayer().getUniqueId().toString());
		result.put("kits", kits);
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public static DelayedPlayer deserialize(Map<String, Object> args){
		return new DelayedPlayer(UUID.fromString((String)args.get("player")), (HashMap<String, Long>)args.get("kits"));
	}

}
