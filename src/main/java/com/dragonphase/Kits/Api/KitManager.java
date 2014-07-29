package com.dragonphase.Kits.Api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import net.minecraft.util.org.apache.commons.lang3.ArrayUtils;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.dragonphase.Kits.Permissions.Permissions;
import com.dragonphase.Kits.Util.Collections;
import com.dragonphase.Kits.Util.Kit;
import com.dragonphase.Kits.Util.Message;
import com.dragonphase.Kits.Util.Utils;
import com.dragonphase.Kits.Util.Message.MessageType;

public class KitManager {
    /**
     * Creates a new kit using default values - 
     *  delay = 0;
     *  overwrite = true;
     *  announce = true.
     * @param kitName The name of the kit to create.
     * @param contents The contents of the kit.
     * @return The created Kit.
     */
    public static Kit createKit(String kitName, ItemStack[] contents){
        return createKit(kitName, contents, 0, true, true);
    }
    
    /**
     * Creates a new Kit.
     * @param kitName The name of the Kit to create.
     * @param contents The contents of the Kit.
     * @param delay The default delay value of the Kit.
     * @param overwrite The default overwrite value of the Kit.
     * @param announce The default announce value of the Kit.
     * @return The created Kit.
     */
    public static Kit createKit(String kitName, ItemStack[] contents, long delay, boolean overwrite, boolean announce){
        String name = Utils.Capitalize(kitName);
        Kit kit = new Kit(name, contents, delay, overwrite, announce);
        Kit.AddKit(kit);
        return kit;
    }
    
    /**
     * Gets the Kit with the specified name.
     * @param kitName The name of the Kit to retrieve.
     * @return The Kit value or null if the Kit does not exist.
     */
    public static Kit getKit(String kitName){
        return Collections.GetKit(Utils.Capitalize(kitName.toLowerCase()));
    }
    
    /**
     * Gets whether the Kit with the specified name exists.
     * @param kitName The Kit to find.
     * @return true if the Kit exists, otherwise false.
     */
    public static boolean kitExists(String kitName){
        return getKit(kitName) != null;
    }
    
    /**
     * Removes the Kit with the specified name.
     * @param kitName The name of the Kit to remove.
     */
    public static void removeKit(String kitName){
        if (kitExists(kitName)) Collections.KitList.remove(getKit(kitName));
    }
    
    
    public static void spawnKit(Player player, String kitName, String flags){
        if (!kitExists(kitName)) return;
        
        spawnKit(player, getKit(kitName), flags);
    }

    public static void spawnKit(Player player, String kitName, String...flags){
        if (!kitExists(kitName)) return;
        
        spawnKit(player, getKit(kitName), flags);
    }
    
    public static void spawnKit(Player player, String kitName, HashMap<String, Boolean> flags){
        if (!kitExists(kitName)) return;
        
        spawnKit(player, getKit(kitName), flags);
    }
    
    public static void spawnKit(Player player, Kit kit, String flags){
        spawnKit(player, kit, flags.split(" "));
    }

    public static void spawnKit(Player player, Kit kit, String...flags){
        List<String> flagList = Arrays.asList(flags);
        HashMap<String, Boolean> Flags = new HashMap<String, Boolean>();
        
        for (String flag : flagList){
            if (flag.isEmpty() || flag.length() < 2) continue;
            Flags.put(flag.replace("+", "").replace("-", ""), flag.startsWith("-") ? false : true);
        }
        
        spawnKit(player, kit, Flags);
    }
    
    public static void spawnKit(Player player, Kit kit, HashMap<String, Boolean> flags){
        long delay = Permissions.CheckPermission(player, Permissions.KITS_DELAY, kit.GetName()) ? kit.GetDelay() : 0;
        boolean overwrite = kit.GetOverwrite();
        boolean announce = kit.GetAnnounce();
        
        for (String flag : flags.keySet()){
            switch (flag){
                case "overwrite":
                    overwrite = flags.get(flag);
                    break;
                case "announce":
                    announce = flags.get(flag);
                    break;
                case "delay":
                    delay = flags.get(flag) ? delay : 0;
                    break;
            }
        }
        
        spawnKit(player, kit, delay, overwrite, announce);
    }
    
    public static void spawnKit(Player player, Kit kit, long delay, boolean overwrite, boolean announce){

        if (Collections.GetDelayedPlayer(player).PlayerDelayed(kit) && kit.GetDelay() == delay && delay > 0){
            String message = "You are currently delayed for kit " + kit.GetName() + "." + (player.hasPermission(Permissions.KITS_FLAGS + ".delay") ? " Use the -delay flag to override this." : "");
            player.sendMessage(Message.Show(message, MessageType.WARNING));
            return;
        }
        
        List<ItemStack> items = new ArrayList<ItemStack>(Arrays.asList(kit.GetItems()));
        java.util.Collections.replaceAll(items, null, new ItemStack(Material.AIR));

        ItemStack[] armor = new ItemStack[]{items.remove(0), items.remove(0), items.remove(0), items.remove(0)};
        ArrayUtils.reverse(armor);
        
        for (int i = 0; i < 5; i ++)
            items.remove(0);
        
        if (overwrite){
            player.getInventory().clear();
            
            for (int i = 0; i < items.size(); i ++){
                player.getInventory().setItem(i + 9 < 36 ? i + 9 : i - 27, items.get(i));
            }
        }
        else player.getInventory().addItem(items.toArray(new ItemStack[items.size()]));
        
        player.getInventory().setArmorContents(armor);
        
        if (delay > 0)
            Collections.GetDelayedPlayer(player).AddKit(kit);
        
        if (announce) player.sendMessage(Message.Show("Kit " + kit.GetName() + " spawned.", MessageType.INFO));
        
    }
}
