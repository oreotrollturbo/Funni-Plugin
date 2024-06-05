package org.oreo.oreo.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.oreo.oreo.OreosPlugin;

public class SpawnCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] strings) {

        if (sender instanceof Player){//making sure whoever sent this was a player to avoid console shenanigans
            Player player = (Player) sender;
            World world = player.getWorld();
            String playerName = player.getName(); //Get the player his name and the team hes in
            String teamName = OreosPlugin.getTeamName(playerName);

            Location redSpawn = new Location(world, 0,100 ,0); //TODO make a command to set red and blue spawns to the config
            Location bluSpawn = new Location(world, 100,100 ,100);

            if (teamName == null){ //Make sure the player has a team
                player.sendMessage(ChatColor.RED + "You are not in a team");
            }else if (teamName.equals("Red")){
                player.teleport(redSpawn);
                return true;
            }else if (teamName.equals("Blu")){
                player.teleport(bluSpawn);
                return true;
            }
        }

        return false; // Command unsuccessful
    }
}
