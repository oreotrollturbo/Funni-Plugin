package org.oreo.oreo.eventListeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.oreo.oreo.commands.ToggleWarModeCommand;


public class OnDroppedItem implements Listener {  //This isn't used I just think its funni

    @EventHandler
    public void onDropedItem(PlayerDropItemEvent e) {
        if (ToggleWarModeCommand.isWarModeOn){ //Dont let players drop items when war mode is on to avoid kit combination
            e.setCancelled(true); // Oh so you wanna drop an item ? Yeah no .
        }
    }
}
