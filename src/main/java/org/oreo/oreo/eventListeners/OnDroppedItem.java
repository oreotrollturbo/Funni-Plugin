package org.oreo.oreo.eventListeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;


public class OnDroppedItem implements Listener {  //This isn't used I just think its funni

    @EventHandler
    public void onDropedItem(PlayerDropItemEvent e) {
        e.setCancelled(true); // Oh so you wanna drop an item ? Yeah no .
    }
}
