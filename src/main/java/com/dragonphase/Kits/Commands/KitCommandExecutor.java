package com.dragonphase.Kits.Commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import net.minecraft.util.org.apache.commons.lang3.ArrayUtils;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.dragonphase.Kits.Permissions.Permissions;
import com.dragonphase.Kits.Util.Collections;
import com.dragonphase.Kits.Util.Kit;
import com.dragonphase.Kits.Util.Message;
import com.dragonphase.Kits.Util.Time;
import com.dragonphase.Kits.Util.Utils;
import com.dragonphase.Kits.Util.Message.MessageType;

public class KitCommandExecutor implements CommandExecutor{
    
    public KitCommandExecutor(){}

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String command, String[] args) {

    	if (args.length < 1){
    		HandleBaseCommand(sender);
    		return false;
    	}
    	
    	if (args[0].equalsIgnoreCase("create"))			CreateKit	(sender, Utils.Trim(args));
    	else if (args[0].equalsIgnoreCase("edit"))		EditKit		(sender, Utils.Trim(args));
    	else if (args[0].equalsIgnoreCase("remove"))	RemoveKit	(sender, Utils.Trim(args));
    	else											SpawnKit	(sender, args);
    	
        return false;
    }
    
    private void HandleBaseCommand(CommandSender sender){
    	if (sender instanceof Player){
            Message.ShowMessage((Player)sender, Message.Show("Usage", "/kit create " + ChatColor.ITALIC + "kitname", MessageType.INFO), "Create a new kit with the specific name.");
            Message.ShowMessage((Player)sender, Message.Show("Usage", "/kit edit " + ChatColor.ITALIC + "kitname", MessageType.INFO), "Edit an existing kit with the specific name.");
            Message.ShowMessage((Player)sender, Message.Show("Usage", "/kit edit " + ChatColor.ITALIC + "kitname [flagname] <flagvalue>", MessageType.INFO), "Edit an existing kit's flags with the specific names.");
            Message.ShowMessage((Player)sender, Message.Show("Usage", "/kit remove " + ChatColor.ITALIC + "kitname", MessageType.INFO), "Remove an existing kit with the specific name.");
            Message.ShowMessage((Player)sender, Message.Show("Usage", "/kit " + ChatColor.ITALIC + "kitname", MessageType.INFO), "Spawn the kit with the specified name.");
            Message.ShowMessage((Player)sender, Message.Show("Usage", "/kit " + ChatColor.ITALIC + "kitname [flags]", MessageType.INFO), "Spawn the kit with the specified name.", "With the specified flags. Example:", "/kit basic -announce");
            Message.ShowMessage((Player)sender, Message.Show("Usage", "/kit " + ChatColor.ITALIC + "kitname playername", MessageType.INFO), "Spawn the kit with the specified name.", "For the player with the specified name.");
            Message.ShowMessage((Player)sender, Message.Show("Usage", "/kit " + ChatColor.ITALIC + "kitname playername [flags]", MessageType.INFO), "Spawn the kit with the specified name.", "For the player with the specified name.", "With the specified flags. Example:", "/kit basic bob -announce");
    	}
    	else{
            sender.sendMessage(Message.Show("Usage", "/kit create " + ChatColor.ITALIC + "kitname", MessageType.INFO));
            sender.sendMessage(Message.Show("Usage", "/kit edit " + ChatColor.ITALIC + "kitname", MessageType.INFO));
            sender.sendMessage(Message.Show("Usage", "/kit edit " + ChatColor.ITALIC + "kitname [flagname] <flagvalue>", MessageType.INFO));
            sender.sendMessage(Message.Show("Usage", "/kit remove " + ChatColor.ITALIC + "kitname", MessageType.INFO));
            sender.sendMessage(Message.Show("Usage", "/kit " + ChatColor.ITALIC + "kitname", MessageType.INFO));
            sender.sendMessage(Message.Show("Usage", "/kit " + ChatColor.ITALIC + "kitname [flags]", MessageType.INFO));
            sender.sendMessage(Message.Show("Usage", "/kit " + ChatColor.ITALIC + "kitname playername", MessageType.INFO));
            sender.sendMessage(Message.Show("Usage", "/kit " + ChatColor.ITALIC + "kitname playername [flags]", MessageType.INFO));
    	}

        return;
    }
    
    //Create Kit
    
    private void CreateKit(CommandSender sender, String[] args){
    	if (!IsPlayer(sender)) return;
    	
    	Player player = (Player) sender;
    	
    	if (!Permissions.CheckPermission(player, Permissions.KITS_ADMIN)) return;
    	
    	if (args.length < 1){
    		Message.ShowMessage(player, Message.Show("Usage", "/kit create " + ChatColor.ITALIC + "kitname", MessageType.INFO), "Create a new kit with the specific name.");
    		return;
    	}
    	
    	if (args.length > 1){
    		player.sendMessage(Message.Show("Kit names cannot contain spaces.", MessageType.WARNING));
    		return;
    	}
    	
    	String name = Utils.Capitalize(args[0].toLowerCase());
    	
    	if (Collections.GetKit(name) != null){
    		player.sendMessage(Message.Show("Kit " + name + " already exists.", MessageType.WARNING));
    		return;
    	}
    	
    	if (name.length() > 22){
    		player.sendMessage(Message.Show("Kit name cannot exceed 22 characters.", MessageType.WARNING));
    		return;
    	}

        Inventory inventory = Bukkit.createInventory(player, 45, "New kit: " + name);
        player.openInventory(inventory);
    }
    
    //Edit Kit
    
    private void EditKit(CommandSender sender, String[] args){
    	if (!IsPlayer(sender)) return;
    	
    	Player player = (Player) sender;
    	
    	if (!Permissions.CheckPermission(player, Permissions.KITS_ADMIN)) return;
    	
    	if (args.length < 1){
    		Message.ShowMessage(player, Message.Show("Usage", "/kit edit " + ChatColor.ITALIC + "kitname", MessageType.INFO), "Edit an existing kit with the specific name.");
    		Message.ShowMessage(player, Message.Show("Usage", "/kit edit " + ChatColor.ITALIC + "kitname flagname", MessageType.INFO), "Edit an existing kit's flags with the specific names.");
    		return;
    	}
    	
    	String name = Utils.Capitalize(args[0].toLowerCase());
    	
    	if (Collections.GetKit(name) == null){
    		player.sendMessage(Message.Show("Kit " + name + " does not exist.", MessageType.WARNING));
    		return;
    	}
    	
    	if (args.length > 1){
    		EditKitFlags(player, Collections.GetKit(name), Utils.Trim(args));
    		return;
    	}

        Inventory inventory = Bukkit.createInventory(player, 45, "Edit kit: " + name);
        
        inventory.setContents(Collections.GetKit(name).GetItems());
        
        player.openInventory(inventory);
    }
    
    public void EditKitFlags(Player player, Kit kit, String[] args){
    	if (args[0].equalsIgnoreCase("overwrite")){
    		EditKitOverwrite(player, kit, Utils.Trim(args));
    		return;
    	}
    	
    	if (args[0].equalsIgnoreCase("announce")){
    		EditKitAnnounce(player, kit, Utils.Trim(args));
    		return;
    	}
    	
    	if (args[0].equalsIgnoreCase("delay")){
    		EditKitDelay(player, kit, Utils.Trim(args));
    		return;
    	}
    }
    
    public void EditKitOverwrite(Player player, Kit kit, String[] args){
    	if (args.length < 1){
    		Message.ShowMessage(player, Message.Show("Usage", "/kit edit " + ChatColor.ITALIC + kit.GetName() + " overwrite [true|false]", MessageType.INFO), "Change the overwrite flag of " + kit.GetName() + " to true or false.", "Current overwrite value: " + kit.GetOverwrite() + ".");
    		return;
    	}
    	
    	boolean value = args[0].equalsIgnoreCase("true") ? true : false;
    	kit.SetOverwrite(value);
		player.sendMessage(Message.Show("Overwrite for kit " + kit.GetName() + " set to " + value, MessageType.INFO));
    }
    
    public void EditKitAnnounce(Player player, Kit kit, String[] args){
    	if (args.length < 1){
    		Message.ShowMessage(player, Message.Show("Usage", "/kit edit " + ChatColor.ITALIC + kit.GetName() + " announce [true|false]", MessageType.INFO), "Change the announce flag of " + kit.GetName() + " to true or false.", "Current announce value: " + kit.GetAnnounce() + ".");
    		return;
    	}
    	
    	boolean value = args[0].equalsIgnoreCase("true") ? true : false;
    	kit.SetAnnounce(value);
		player.sendMessage(Message.Show("Announce for kit " + kit.GetName() + " set to " + value, MessageType.INFO));
    }
    
    public void EditKitDelay(Player player, Kit kit, String[] args){
    	if (args.length < 1){
    		Message.ShowMessage(player, Message.Show("Usage", "/kit edit " + ChatColor.ITALIC + kit.GetName() + " delay [delay]", MessageType.INFO), "Change the delay flag of " + kit.GetName() + ".", "Example: 1h30m for 1 hour 30 minute delay.");
    		return;
    	}
    	
    	try{
        	Time value = new Time(args[0]);
        	kit.SetDelay(value.getMilliseconds());
    		player.sendMessage(Message.Show("Delay for kit " + kit.GetName() + " set to " + args[0], MessageType.INFO));
    	}catch(Exception ex){
    		player.sendMessage(Message.Show("Incorrect delay format. Example: 1h30m for 1 hour 30 minute delay.", MessageType.WARNING));
    	}
    }
    
    //Remove Kit
    
    private void RemoveKit(CommandSender sender, String[] args){
    	if (!IsPlayer(sender)) return;
    	
    	Player player = (Player) sender;
    	
    	if (!Permissions.CheckPermission(player, Permissions.KITS_ADMIN)) return;
    	
    	if (args.length < 1){
    		Message.ShowMessage(player, Message.Show("Usage", "/kit remove " + ChatColor.ITALIC + "kitname", MessageType.INFO), "Remove an existing kit with the specific name.");
    		return;
    	}
    	
    	String name = Utils.Capitalize(args[0].toLowerCase());
    	
    	if (Collections.GetKit(name) == null){
    		player.sendMessage(Message.Show("Kit " + name + " does not exist.", MessageType.WARNING));
    		return;
    	}
    	
    	Collections.KitList.remove(Collections.GetKit(name));
		player.sendMessage(Message.Show("Kit " + name + " removed.", MessageType.INFO));
    }
    
    //Spawn Kit
    
    public void SpawnKit(CommandSender sender, String[] args){
    	if (sender instanceof Player){
    		SpawnKit((Player)sender, args);
    		return;
    	}
    	
    	if (args.length < 2){
    		sender.sendMessage(Message.Show("Usage",  "kit " + ChatColor.ITALIC + "kitname playername", MessageType.WARNING));
    		return;
    	}
    	
    	if (SpawnKit(sender, args[0], args[1], StringUtils.join(Utils.Trim(Utils.Trim(args)), " ")))
    	    sender.sendMessage(Message.Show("Kit " + args[0] + " spawned for " + args[1] + ".", MessageType.INFO));
    }
    
    private void SpawnKit(Player player, String[] args){
    	if (args.length == 0){
    		Message.ShowMessage(player, Message.Show("Usage", "/kit " + ChatColor.ITALIC + "kitname", MessageType.WARNING), "Spawn the specified kit.");
    		Message.ShowMessage(player, Message.Show("Usage", "/kit " + ChatColor.ITALIC + "kitname playername", MessageType.WARNING), "Spawn the specified kit for the specified player.");
    		return;
    	}
    	
    	if (!Permissions.CheckPermission(player, Permissions.KITS_SPAWN + "." + args[0].toLowerCase())) return;
    	
    	if (args.length > 1 && (!args[1].startsWith("+") && !args[1].startsWith("-"))){
    		if (!Permissions.CheckPermission(player, Permissions.KITS_SPAWN_OTHERS + "." + args[0].toLowerCase())) return;
        	
        	if (SpawnKit(player, args[0], args[1], StringUtils.join(Utils.Trim(Utils.Trim(args)), " ")))
        		player.sendMessage(Message.Show("Kit " + args[0] + " spawned for " + args[1] + ".", MessageType.INFO));
        	
        	return;
    	}
    	
    	SpawnKit(player, args[0], player.getName(), StringUtils.join(Utils.Trim(args), " "));
    }
    
    private boolean SpawnKit(CommandSender sender, String kitName, String playerName, String flags){
    	Player player = GetPlayer(playerName);
    	
    	if (player == null){
    		sender.sendMessage(Message.Show(playerName + " is not online. Make sure the name is typed correctly.", MessageType.WARNING));
    		return false;
    	}
    	
    	Kit kit = Collections.GetKit(kitName);
    	
    	if (kit == null){
    		sender.sendMessage(Message.Show("Kit " + kitName + " does not exist. Make sure the name is typed correctly.", MessageType.WARNING));
    		return false;
    	}
    	
    	return SpawnKit(sender, player, kit, flags);
    }
    
    private boolean SpawnKit(CommandSender sender, Player player, Kit kit, String flags){
    	List<String> flagList = Arrays.asList(flags.split(" "));
    	HashMap<String, Boolean> Flags = new HashMap<String, Boolean>();
    	
    	if (sender.hasPermission(Permissions.KITS_FLAGS)){
        	for (String flag : flagList){
        		if (flag.isEmpty() || flag.length() < 2) continue;
        		if (sender instanceof Player && !Permissions.CheckPermission((Player)sender, Permissions.KITS_FLAGS, flag.replace("+", "").replace("-", ""))){
            		sender.sendMessage(Message.Show("You do not have permission to use the " + flag.replace("+", "").replace("-", "") + " flag.", MessageType.WARNING));
            		continue;
        		}
        		Flags.put(flag.replace("+", "").replace("-", ""), flag.startsWith("-") ? false : true);
        	}
    	}else{
    		sender.sendMessage(Message.Show("You do not have permission to use flags.", MessageType.WARNING));
    	}
    	
    	return SpawnKit(sender, player, kit, Flags);
    }
    
    private boolean SpawnKit(CommandSender sender, Player player, Kit kit, HashMap<String, Boolean> flags){
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
    	
    	return SpawnKit(sender, player, kit, delay, overwrite, announce);
    }
    
    private boolean SpawnKit(CommandSender sender, Player player, Kit kit, long delay, boolean overwrite, boolean announce){
    	
		if (Collections.GetDelayedPlayer(player).PlayerDelayed(kit) && kit.GetDelay() == delay && delay > 0){
			String message = (sender instanceof Player && ((Player)sender).getName().equalsIgnoreCase(player.getName()) ? "You are " : player.getName() + " is ") + "currently delayed for kit " + kit.GetName() + "." + (sender.hasPermission(Permissions.KITS_FLAGS + ".delay") ? " Use the -delay flag to override this." : "");
	    	sender.sendMessage(Message.Show(message, MessageType.WARNING));
	    	return false;
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
		
		return true;
    }
    
    //Helper methods
    
    private boolean IsPlayer(CommandSender sender){
    	if (!(sender instanceof Player)){
    		sender.sendMessage(Message.Show("Command must be issued ingame.", MessageType.WARNING));
    		return false;
    	}
    	
    	return true;
    }
    
	private Player GetPlayer(String name){
    	for (Player player : Bukkit.getOnlinePlayers()){
    		if (player.getName().equalsIgnoreCase(name)) return player;
    	}
    	return null;
    }
}
