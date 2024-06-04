package org.oreo.oreo.eventListeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.oreo.oreo.commands.ToggleWarModeCommand;


public class OnBlockBroken implements Listener {

    private final OnFlagPlaced onFlagPlaced;

    public OnBlockBroken(OnFlagPlaced onFlagPlaced) {
        this.onFlagPlaced = onFlagPlaced;
    }

    @EventHandler
    public void onFlagBroken(BlockBreakEvent e) {

        Block block = e.getBlock(); //Get the block broken
        Location blockLocation = block.getLocation();

        int blockX = block.getX();
        int blockY = block.getY(); // Is always handy
        int blockZ = block.getZ();

        if (ToggleWarModeCommand.isWarModeOn){ //We don't care about flags if war mode is off

            if (block.getBlockData().getMaterial().equals(Material.OAK_FENCE)) { //If it's an oak fence

                Location deepslateLocation = new Location(block.getWorld(),blockX,blockY + 1,blockZ);

                if (onFlagPlaced.getFlagLocations().contains(deepslateLocation)){
                    e.setCancelled(true); //If the deepslate is a flag don't let the player break the flag
                }

            } else if (block.getBlockData().getMaterial().equals(Material.TORCH)){ // If it's a torch
                Location deepslateLocation = new Location(block.getWorld(),blockX,blockY - 1,blockZ); //Get the deepsalte location

                if (onFlagPlaced.getFlagLocations().contains(deepslateLocation) &&
                        deepslateLocation.getBlock().getBlockData().getMaterial().equals(Material.DEEPSLATE)){
                    e.setCancelled(true); //If the block above it is deepslate and is a flag don't let the torch break
                }
            } else if (block.getBlockData().getMaterial().equals(Material.DEEPSLATE) && onFlagPlaced.getFlagLocations().equals(blockLocation)){
                //If its deepslate and its in the list


                // Removing flag location from the flag list in OnFlagPlaced
                onFlagPlaced.getFlagLocations().remove(blockLocation);

                Location fenceLocation = new Location(block.getWorld(), blockX,blockY - 1,blockZ); // Get the fence and torch location to check
                Location torchLocation = new Location(block.getWorld(), blockX,blockY + 1,blockZ);


                int chunkX = block.getChunk().getX();
                int chunkZ = block.getChunk().getZ(); // Get the chunks to broadcast the message

                torchLocation.getBlock().setType(Material.AIR);
                fenceLocation.getBlock().setType(Material.AIR); //Delete the torch and fence

                Player eventPlayer = e.getPlayer();
                String playerName = eventPlayer.getName(); //Get the player and his name

                for (Player player : Bukkit.getOnlinePlayers()) { //Broadcast a message to everyone
                    player.sendMessage(ChatColor.DARK_RED + "[War] " + playerName + " defended chunk (" + chunkX + "," + chunkZ + ") against Naples!");
                }

            }
        }
    }
}
