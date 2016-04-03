package com.dragonphase.kits.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;

import com.dragonphase.kits.api.Kit;
import com.dragonphase.kits.configuration.CollectionManager;

public class DelayedPlayer implements ConfigurationSerializable {

    private UUID playerUniqueId;
    private HashMap<String, Long> kits;

    public DelayedPlayer(Player player) {
        playerUniqueId = player.getUniqueId();
        this.kits = new HashMap<>();
    }

    public DelayedPlayer(UUID uuid, HashMap<String, Long> kits) {
        this.playerUniqueId = uuid;
        this.kits = new HashMap<>(kits);
    }

    @SuppressWarnings("unchecked")
    public DelayedPlayer(Map<String, Object> args) {
        this(UUID.fromString((String) args.get("player")), (HashMap<String, Long>) args.get("kits"));
    }

    public UUID getUniqueId() {
        return playerUniqueId;
    }

    public HashMap<String, Long> getKits() {
        return kits;
    }

    public void addKit(Kit kit, long delay) {
        getKits().put(kit.getName(), System.currentTimeMillis() + delay);
    }

    public boolean playerDelayed(Kit kit) {
        boolean delayed = false;
        if (getKits().containsKey(kit.getName())) {
            delayed = System.currentTimeMillis() - getKits().get(kit.getName()) < kit.getDelay();
            if (!delayed) getKits().remove(kit.getName());
        }
        return delayed;
    }

    public String getRemainingTime(Kit kit) {
        if (!playerDelayed(kit)) return "";

        return Time.fromMilliseconds(kit.getDelay() - (System.currentTimeMillis() - getKits().get(kit.getName()))).toReadableFormat(false);
    }

    public void sortKits(CollectionManager manager) {
        Iterator<Entry<String, Long>> iter = getKits().entrySet().iterator();
        while (iter.hasNext()) {
            Entry<String, Long> entry = iter.next();
            if (manager.getKit(entry.getKey()) == null || (System.currentTimeMillis() - entry.getValue() >= manager.getKit(entry.getKey()).getDelay())) {
                iter.remove();
            }
        }
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> result = new HashMap<>();

        result.put("player", getUniqueId().toString());
        result.put("kits", getKits());

        return result;
    }
}
