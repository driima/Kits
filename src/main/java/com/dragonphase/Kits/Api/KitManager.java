package com.dragonphase.Kits.Api;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.dragonphase.Kits.Util.Collections;
import com.dragonphase.Kits.Util.Kit;
import com.dragonphase.Kits.Util.Utils;

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
        
        return new Kit(name, contents, delay, overwrite, announce);
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
}
