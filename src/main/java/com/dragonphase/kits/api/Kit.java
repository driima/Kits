package com.dragonphase.kits.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;

public class Kit implements ConfigurationSerializable {

    private String name;
    private long delay;
    private boolean overwrite, announce;
    private ItemStack[] items;

    public Kit(String name, ItemStack[] items, long delay, boolean overwrite, boolean announce) {
        this.name = name;
        this.delay = delay;
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
        Map<String, Object> result = new HashMap<>();

        result.put("name", name);
        result.put("delay", delay);
        result.put("overwrite", overwrite);
        result.put("announce", announce);
        result.put("items", items);

        return result;
    }

    @SuppressWarnings("unchecked")
    public static Kit deserialize(Map<String, Object> args) {
        return new Kit((String) args.get("name"), ((ArrayList<ItemStack>) args.get("items")).toArray(new ItemStack[((ArrayList<ItemStack>) args.get("items")).size()]), (Integer) args.get("delay"), (Boolean) args.get("overwrite"), (Boolean) args.get("announce"));
    }
}
