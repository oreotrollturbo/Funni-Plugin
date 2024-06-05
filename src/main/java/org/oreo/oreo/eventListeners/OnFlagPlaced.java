package org.oreo.oreo.eventListeners;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.oreo.oreo.OreosPlugin;
import org.oreo.oreo.commands.ToggleWarModeCommand;

import java.util.ArrayList;
import java.util.List;

public class OnFlagPlaced implements Listener {

    private final List<Location> flagLocations; //This is the list with all the flags

    private final OreosPlugin plugin; // Getting the plugin and creating the list with flags
    public OnFlagPlaced(OreosPlugin plugin) {
        this.plugin = plugin;
        this.flagLocations = new ArrayList<>();
    }

    @EventHandler
    public void onFlagPlaced(BlockPlaceEvent e) {

        Block block = e.getBlockPlaced();

        if (ToggleWarModeCommand.isWarModeOn && block.getBlockData().getMaterial().equals(Material.OAK_FENCE)) {
            //If the player placed a fence and war mode is on

            World world = block.getWorld(); //Get the world player and block for later
            Player player = e.getPlayer();

            int blockX = block.getX();
            int blockY = block.getY(); //Get the block coordinates
            int blockZ = block.getZ();

            Location deepslateLocation = new Location(world, blockX,blockY + 1,blockZ);
            Location torchLocation = new Location(world, blockX,blockY + 2,blockZ);// Get the torch and deepslate location to create them

            for (Location location : flagLocations) { //Loop through the existing flags locations
                if (location.getChunk().equals(deepslateLocation.getChunk())){
                    e.setCancelled(true);
                    player.sendMessage(ChatColor.RED + "Theres a flag within this chunk already");
                    return; //If there is a flag in the same chunk cancel the event and don't do anything
                }
            }

            if (!canSeeSky(world,blockX,blockY,blockZ)){ //Check if the flag can see the sky
                e.setCancelled(true);
                player.sendMessage(ChatColor.RED + "Flag cannot see the sky"); //Send feedback
                return; //If the flag doesn't see the sky just stop
            }

            deepslateLocation.getBlock().setType(Material.DEEPSLATE);
            torchLocation.getBlock().setType(Material.TORCH); //Create the deepslate and the torch

            flagLocations.add(deepslateLocation); //Add the flag to the list

            Player eventPlayer = e.getPlayer();
            String playerName = eventPlayer.getName(); //Get the player

            for (Player playerM : Bukkit.getOnlinePlayers()) { //Broadcast the attack message to all players
                playerM.sendMessage(ChatColor.DARK_RED + "[War] " + playerName + " is attacking London at (" + blockX + "," + blockY + "," + blockZ + ")");
            }

            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() { //basically creates a new thread
                public void run() { //This delays the code bellow

                    if (block.getType().equals(Material.OAK_FENCE)) {

                        block.setType(Material.AIR);
                        torchLocation.getBlock().setType(Material.AIR); // Delete the flag
                        deepslateLocation.getBlock().setType(Material.AIR);

                        for (Player player : Bukkit.getOnlinePlayers()) { //Broadcast the attack message
                            int chunkX = block.getChunk().getX();
                            int chunkZ = block.getChunk().getZ(); // Get the flag chunk coords to show them in chat
                            player.sendMessage(ChatColor.DARK_RED + "[War] " + playerName + " captured chunk (" + chunkX + "," + chunkZ + ") from London");
                        }
                        // Remove the flag location from the list after it's captured
                        flagLocations.remove(deepslateLocation);
                    }
                }
            }, 400); //This is how long the code is delayed before being executed (in ticks)
            // 20 secs
        }

    }

    // Getter method for the flagLocations list
    public List<Location> getFlagLocations() {
        return flagLocations;
    }

    private boolean canSeeSky(World world ,int x ,int y ,int z){
        Block highestBlock = world.getHighestBlockAt(x,z); //Gets the xyz coords and checks if that block can "see the sky"
        return !(highestBlock.getY() > y);
    }

    public void deleteAllFlags() { //Deletes all flags

        for (Location flag : flagLocations){ //Loops through and deletes all flags

            World world = flag.getWorld();
            int x = flag.getBlockX();
            int y = flag.getBlockY(); // Get the flags xyz
            int z = flag.getBlockZ();

            Location torchLocation = new Location(world,x,y + 1,z); //Get the flags locations
            Location fenceLocation = new Location(world,x,y - 1,z);

            torchLocation.getBlock().setType(Material.AIR);  //Delete the torch first or else it will drop as an item
            flag.getBlock().setType(Material.AIR); //Delete the flag
            fenceLocation.getBlock().setType(Material.AIR);
        }
        flagLocations.clear(); //Clear all flags
    }
}