package com.dragonphase.kits.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;

public class Kit implements ConfigurationSerializable {

    private String name;
    private long delay;
    private boolean clear, overwrite, announce;
    private ItemStack[] items;

    public Kit(String name, ItemStack[] items, long delay, boolean clear, boolean overwrite, boolean announce) {
        this.name = name;
        this.delay = delay;
        this.clear = clear;
        this.overwrite = overwrite;
        this.announce = announce;
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }
    
    public boolean getClear() {
        return clear;
    }
    
    public void setClear(boolean clear) {
        this.clear = clear;
    }

    public boolean getOverwrite() {
        return overwrite;
    }

    public void setOverwrite(boolean overwrite) {
        this.overwrite = overwrite;
    }

    public boolean getAnnounce() {
        return announce;
    }

    public void setAnnounce(boolean announce) {
        this.announce = announce;
    }

    public ItemStack[] getItems() {
        return items;
    }

    public void setItems(ItemStack[] items) {
        this.items = items;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> result = new HashMap<String, Object>();

        result.put("name", getName());
        result.put("delay", getDelay());
        result.put("clear", getClear());
        result.put("overwrite", getOverwrite());
        result.put("announce", getAnnounce());
        result.put("items", getItems());

        return result;
    }

    @SuppressWarnings("unchecked")
    public static Kit deserialize(Map<String, Object> args) {
        String name = (String) args.get("name");
        ItemStack[] items = ((ArrayList<ItemStack>) args.get("items")).toArray(new ItemStack[((ArrayList<ItemStack>) args.get("items")).size()]);
        boolean clear = (Boolean) (args.containsKey("clear") ? args.get("clear") : true);
        boolean overwrite = (Boolean) (args.containsKey("overwrite") ? args.get("overwrite") : true);
        boolean announce = (Boolean) (args.containsKey("announce") ? args.get("announce") : true);
        
        try{
            long delay = (Long) (args.containsKey("delay") ? args.get("delay") : 0);
            return new Kit(name, items, delay, clear, overwrite, announce);
        }catch (Exception ex){
            int delay = (Integer) (args.containsKey("delay") ? args.get("delay") : 0);
            return new Kit(name, items, delay, clear, overwrite, announce);
        }
        
    }
}
