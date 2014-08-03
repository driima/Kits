package com.dragonphase.Kits.Commands;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.dragonphase.Kits.Kits;
import com.dragonphase.Kits.Permissions.Permissions;
import com.dragonphase.Kits.Util.Collections;
import com.dragonphase.Kits.Util.CommandDescription;
import com.dragonphase.Kits.Util.Kit;
import com.dragonphase.Kits.Util.Message;
import com.dragonphase.Kits.Util.Message.MessageType;
import com.dragonphase.Kits.Util.Utils;

public class KitsCommandExecutor implements CommandExecutor{
    private Kits plugin;
    
    public KitsCommandExecutor(Kits instance){
        plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String command, String[] args) {

    	if (args.length < 1){
    		handleBaseCommand(sender);
    		return false;
    	}
    	
    	if (args[0].equalsIgnoreCase("reload")) handleReload(sender);
    	
        return false;
    }
    
    private void handleBaseCommand(CommandSender sender){
		if (sender instanceof Player && !Permissions.checkPermission((Player) sender, Permissions.KITS_LIST)) return;
		
		if (Collections.KitList.size() < 1){
			sender.sendMessage(Message.show("There are no available kits.", MessageType.WARNING));
			return;
		}
		
		List<CommandDescription> commands = new ArrayList<CommandDescription>();
		
		for (Kit kit : Collections.KitList){
			List<String> lines = new ArrayList<String>();
			List<String> items = new ArrayList<String>();
			for (ItemStack item : kit.getItems()){
				if (item == null) continue;
				items.add(item.getAmount() + " x " + (item.hasItemMeta() ? item.getItemMeta().getDisplayName() : Utils.capitalize(item.getType().name())));
			}
			lines.add("Number of items: " + items.size());
			lines.add("Delay: " + kit.getDelay() + "ms");
			lines.add("Overwrite: " + kit.getOverwrite());
			lines.add("Announce: " + kit.getAnnounce());
			commands.add(new CommandDescription(ChatColor.DARK_AQUA + kit.getName(), "/kit " + kit.getName(), lines.toArray(new String[lines.size()])));
		}

    	if (!(sender instanceof Player)){
    		String message = "Available kits: ";
    		for (CommandDescription command : commands){
    			message += ChatColor.GRAY + ", " + command.getTitle();
    		}
    		sender.sendMessage(message.replaceFirst(Pattern.quote(", "), ""));
    		return;
    	}
    	
		Message.showCommand((Player)sender, "Available kits: ", commands.toArray(new CommandDescription[commands.size()]));

        return;
    }
    
    private void handleReload(CommandSender sender){
    	if (sender instanceof Player){
    		if (Permissions.checkPermission((Player)sender, Permissions.KITS_ADMIN)) plugin.reload();
    	}
    	else plugin.reload();
    }
}
