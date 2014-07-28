package com.dragonphase.Kits.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import com.dragonphase.Kits.Kits;
import com.dragonphase.Kits.Util.Collections;
import com.dragonphase.Kits.Util.Kit;
import com.dragonphase.Kits.Util.Message;
import com.dragonphase.Kits.Util.Utils;
import com.dragonphase.Kits.Util.Message.MessageType;

public class EventListener implements Listener{
    public Kits plugin;

    public EventListener(Kits instance){
        plugin = instance;
    }
    
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event){
    	if (event.getInventory().getName().toLowerCase().startsWith("new kit: "))
    		CreateKit((Player) event.getPlayer(), event.getInventory());
    	
    	if (event.getInventory().getName().toLowerCase().startsWith("edit kit: "))
    		EditKit((Player) event.getPlayer(), event.getInventory());
    }
    
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
    	//Signs
    }
    
    //Helper methods
    
    public void CreateKit(Player player, Inventory inventory){
    	String inventoryName = inventory.getName().toLowerCase().replace("new kit: ", "");
    	
    	String name = Utils.Capitalize(inventoryName);
    	
    	Kit kit = new Kit(name, inventory.getContents(), 0, true, true);
    	
    	player.sendMessage(Message.Show("Kit " + kit.GetName() + " created.", MessageType.INFO));
    	
    }
    
    public void EditKit(Player player, Inventory inventory){
    	String inventoryName = inventory.getName().toLowerCase().replace("edit kit: ", "");
    	
    	String name = Utils.Capitalize(inventoryName);
    	
    	Collections.GetKit(name).SetItems(inventory.getContents());
    	
    	player.sendMessage(Message.Show("Kit " + name + " edited.", MessageType.INFO));
    	
    }
}
