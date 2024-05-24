package org.oreo.oreo.eventListeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnPlayerJoin implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent join) {

        Player player = join.getPlayer();

        String playername = player.getName();

        if (playername.contains("oreotrollturbo")){
            player.sendMessage(ChatColor.DARK_RED + "Welcome back me :)");
        }else {
            join.setJoinMessage("Welcome to Oreo's plugin " + playername);
        }
    }
}
