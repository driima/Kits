package com.dragonphase.Kits.Permissions;

import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;

import com.dragonphase.Kits.Util.Message;
import com.dragonphase.Kits.Util.Message.MessageType;

public class Permissions {
	
	public static final String KITS_BASE			= "kits";
	
	public static final String KITS_LIST			= KITS_BASE + ".list";
	
	public static final String KITS_SPAWN			= KITS_BASE + ".spawn";
	public static final String KITS_SPAWN_OTHERS	= KITS_SPAWN + ".others";
	
	public static final String KITS_FLAGS			= KITS_BASE + ".flags";
	
	public static final String KITS_DELAY			= KITS_BASE + ".delay";
	
	public static final String KITS_ADMIN			= KITS_BASE + ".admin";
    
    public static boolean CheckPermission(Player player, String permission){
    	if (!player.hasPermission(permission)){
    		Message.ShowMessage(player, Message.Show("Warning", "You do not have permission to perform that action.", MessageType.WARNING), permission);
    		//player.sendMessage(Message.Show("Warning", "You do not have permission to perform that action.", MessageType.WARNING));
    		return false;
    	}
    	return true;
    }
    
    public static boolean CheckPermission(Player player, String permission, String...subPerms){
    	return CheckPermission(player, permission + "." + StringUtils.join(subPerms, "."));
    }
}
