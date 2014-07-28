package com.dragonphase.Kits.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;

public class Kit implements ConfigurationSerializable{
	
	private String name;
	private long delay;
	private boolean overwrite, announce;
	private ItemStack[] items;
	
	public Kit(String name, ItemStack[] items, long delay, boolean overwrite, boolean announce){
		this.name = name;
		this.delay = delay;
		this.overwrite = overwrite;
		this.announce = announce;
		this.items = items;
		AddKit(this);
	}
	
	public String GetName(){
		return name;
	}
	
	public void SetName(String name){
		this.name = name;
	}
	
	public long GetDelay(){
		return delay;
	}
	
	public void SetDelay(long delay){
		this.delay = delay;
	}
	
	public boolean GetOverwrite(){
		return overwrite;
	}
	
	public void SetOverwrite(boolean overwrite){
		this.overwrite = overwrite;
	}
	
	public boolean GetAnnounce(){
		return announce;
	}
	
	public void SetAnnounce(boolean announce){
		this.announce = announce;
	}
	
	public ItemStack[] GetItems(){
		return items;
	}
	
	public void SetItems(ItemStack[] items){
		this.items = items;
	}
	
	public static void AddKit(Kit kit){
		if (Collections.KitList == null) Collections.KitList = new ArrayList<Kit>();
		Collections.KitList.add(kit);
	}
	
	public static void RemoveKit(Kit kit){
		Collections.KitList.remove(kit);
	}
	
	public static Kit GetKit(String name){
		for (Kit kit : Collections.KitList){
			if (kit.GetName().equalsIgnoreCase(name))
				return kit;
		}
		return null;
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> result = new HashMap<String, Object>();
		
		result.put("name", name);
		result.put("delay", delay);
		result.put("overwrite", overwrite);
		result.put("announce", announce);
		result.put("items", items);
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public static Kit deserialize(Map<String, Object> args){
		return new Kit((String)args.get("name"), ((ArrayList<ItemStack>)args.get("items")).toArray(new ItemStack[((ArrayList<ItemStack>)args.get("items")).size()]), (Integer)args.get("delay"), (Boolean)args.get("overwrite"), (Boolean) args.get("announce"));
	}
}
