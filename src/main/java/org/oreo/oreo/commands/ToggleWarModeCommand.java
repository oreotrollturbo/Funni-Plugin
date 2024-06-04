package org.oreo.oreo.commands;

import net.md_5.bungee.chat.SelectorComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ToggleWarModeCommand implements CommandExecutor {

    public static boolean isWarModeOn = false;//This boolean is used in other classes to see if war is on
// Server restarts automatically disable war mode
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] strings) {
        isWarModeOn = !isWarModeOn;

        if (isWarModeOn){ //If the war mode is on turn it off
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.sendMessage(ChatColor.DARK_RED + "§l War mode is enabled"); //Send a message to all players
            }
        }else { // If the war mode is off turn it on
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.sendMessage(ChatColor.DARK_RED + "§l War mode is disabled");//Send a message to all players
            }
        }
        return true;
    }
}
