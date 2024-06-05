package org.oreo.oreo.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.oreo.oreo.eventListeners.OnFlagPlaced;

public class ToggleWarModeCommand implements CommandExecutor {

    public static boolean isWarModeOn = true;//This boolean is used in other classes to see if war is on
// Server restarts automatically turn on war mode

    private final OnFlagPlaced onFlagPlaced;

    public ToggleWarModeCommand(OnFlagPlaced onFlagPlaced){
        this.onFlagPlaced = onFlagPlaced;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] strings) {
        isWarModeOn = !isWarModeOn; // Invert the war mode

        if (isWarModeOn){ //send the message
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.sendMessage(ChatColor.DARK_RED + "§l War mode is enabled"); //Send a message to all players
            }
        }else { // send the message to all players

            onFlagPlaced.deleteAllFlags(); //Delete all the physical flags
            onFlagPlaced.getFlagLocations().clear(); //Clear the flag list
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.sendMessage(ChatColor.DARK_RED + "§l War mode is disabled");//Send a message to all players
            }
        }
        return true;
    }
}
