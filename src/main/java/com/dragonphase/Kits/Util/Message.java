package com.dragonphase.Kits.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mkremins.fanciful.FancyMessage;
import net.minecraft.server.v1_7_R3.ChatSerializer;
import net.minecraft.server.v1_7_R3.PacketPlayOutChat;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_7_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.dragonphase.Kits.Kits;

public class Message {
	
	public enum MessageType{
		MESSAGE,
		INFO,
		WARNING
	}
	
    private static Kits plugin;
    
    public static void setParent(Kits instance){
        plugin = instance;
    }
    
    public static String Show(String message, MessageType type){
    	return Show(plugin.getName(), message, type);
    }
    
    public static String Show(String prefix, String message, MessageType type){
    	return ChatColor.DARK_GRAY + prefix + ": " + (type == MessageType.MESSAGE ? ChatColor.GRAY : type == MessageType.INFO ? ChatColor.DARK_AQUA : ChatColor.RED) + message;
    }
    
    //FancyMessage commands
    
    public static void ShowMessage(Player player, String title, String...args){
    	if (args.length < 1){
    		player.sendMessage(title);
    		return;
    	}
    	
    	FancyMessage message = new FancyMessage("").then(title).itemTooltip(GetMessage(args));
    	
    	SendJSONMessage(player, message);
    }
    
    public static void ShowCommand(Player player, CommandDescription command){
    	if (command.getArgs().length < 1){
    		player.sendMessage(command.getTitle());
    		return;
    	}
    	
    	FancyMessage message = new FancyMessage("").then(command.getTitle()).itemTooltip(GetMessage(command.getArgs())).command(command.getCommand());

    	SendJSONMessage(player, message);
    }
    
    public static void ShowCommand(Player player, String prefix, CommandDescription...commands){
    	FancyMessage message = new FancyMessage(prefix).color(ChatColor.DARK_GRAY);

    	List<CommandDescription> commandList = new ArrayList<CommandDescription>(Arrays.asList(commands));
    	
    	for (CommandDescription command : commandList){
        	if (command.getArgs().length < 1){
        		player.sendMessage(command.getTitle());
        		return;
        	}
        	
        	message = message.then(command.getTitle())
        				.itemTooltip(GetMessage(command.getArgs()))
        				.command(command.getCommand());
        	
        	if (commandList.get(commandList.size()-1) != command)
        		message = message.then(", ").color(ChatColor.GRAY);
        	
    	}
    	
    	SendJSONMessage(player, message);
    }
    
    public static ItemStack GetMessage(String...args){
    	ItemStack item = new ItemStack(Material.STONE);
    	ItemMeta meta = item.getItemMeta();
    	meta.setDisplayName(ChatColor.RESET + args[0]);
    	
    	List<String> lore = new ArrayList<String>(Arrays.asList(Utils.Trim(args)));
    	for (String line : lore)
    		lore.set(lore.indexOf(line), ChatColor.RESET + line);
    	
    	meta.setLore(lore);
    	item.setItemMeta(meta);
    	
    	return item;
    }
    
	public static void SendJSONMessage(Player player, FancyMessage message){
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutChat(ChatSerializer.a(message.toJSONString()), true));
	}
}
