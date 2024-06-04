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

        int blockX = block.getX();
        int blockY = block.getY(); // Is always handy
        int blockZ = block.getZ();

        if (ToggleWarModeCommand.isWarModeOn){ //We dont care about flags if war mode is off

            if (block.getBlockData().getMaterial().equals(Material.OAK_FENCE)) { //If its an oak fence
                Location deepslateLocation = new Location(block.getWorld(),blockX,blockY + 1,blockZ);

                if (deepslateLocation.getBlock().getBlockData().getMaterial().equals(Material.DEEPSLATE)){
                    e.setCancelled(true); //If theres deepsalte above it then it cant break
                } //TODO make it detect if its in the flag list

            } else if (block.getBlockData().getMaterial().equals(Material.TORCH)){ // If its a torch
                Location deepslateLocation = new Location(block.getWorld(),blockX,blockY - 1,blockZ); //Get the deepsalte location

                if (deepslateLocation.getBlock().getBlockData().getMaterial().equals(Material.DEEPSLATE)){
                    e.setCancelled(true); //If there is deepslate bellow it we assume its a flag and dont let the player break the torch
                }
            } else if (block.getBlockData().getMaterial().equals(Material.DEEPSLATE)){ //If its deepslate

                Location blockLocation = block.getLocation(); //Get its location

                // Removing flag location from the flag list in OnFlagPlaced
                onFlagPlaced.getFlagLocations().remove(blockLocation);

                Location fenceLocation = new Location(block.getWorld(), blockX,blockY - 1,blockZ); // Get the fence and torch location to check
                Location torchLocation = new Location(block.getWorld(), blockX,blockY + 1,blockZ);

                Material torchMaterial = torchLocation.getBlock().getType();
                Material fenceMaterial = fenceLocation.getBlock().getType(); //Get their type to check if they are the correct ones

                if (torchMaterial.equals(Material.TORCH) && fenceMaterial.equals(Material.OAK_FENCE)){ //If its a flag (has the correct blocks)
                    int chunkX = block.getChunk().getX();
                    int chunkZ = block.getChunk().getZ();

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
}
