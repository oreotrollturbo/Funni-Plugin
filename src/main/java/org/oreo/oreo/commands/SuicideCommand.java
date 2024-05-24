package org.oreo.oreo.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SuicideCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] strings) {

        if (sender instanceof Player){//making sure whoever sent this was a player to avoid console shenanigans
            Player player = (Player) sender;
            player.setHealth(0); //Sets the players health to 0 thus killing them with the message ("player" died)
            return true;
        }

        return false;
    }
}
