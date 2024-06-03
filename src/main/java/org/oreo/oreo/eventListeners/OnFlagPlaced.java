package org.oreo.oreo.eventListeners;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.oreo.oreo.OreosPlugin;

import java.util.ArrayList;
import java.util.List;

public class OnFlagPlaced implements Listener {

    private final List<Location> flagLocations;

    private final OreosPlugin plugin;
    public OnFlagPlaced(OreosPlugin plugin) {
        this.plugin = plugin;
        this.flagLocations = new ArrayList<>();
    }

    @EventHandler
    public void onFlagPlaced(BlockPlaceEvent e) {

        Block block = e.getBlockPlaced();
        World world = block.getWorld();
        Player player = e.getPlayer();

        if (block.getBlockData().getMaterial().equals(Material.OAK_FENCE)){

            boolean cancelled = false;
            int blockX = block.getX();
            int blockY = block.getY();
            int blockZ = block.getZ();

            Location deepslateLocation = new Location(world, blockX,blockY + 1,blockZ);
            Location torchLocation = new Location(world, blockX,blockY + 2,blockZ);

            for (Location location : flagLocations) {
                if (location.getChunk().equals(deepslateLocation.getChunk())){
                    e.setCancelled(true);
                    cancelled = true;
                    player.sendMessage(ChatColor.RED + "Theres a flag within this chunk already");
                    break;
                }
            }

            if (!canSeeSky(world,blockX,blockY,blockZ)){
                e.setCancelled(true);
                cancelled = true;
                player.sendMessage(ChatColor.RED + "Flag cannot see the sky");
            }

            if (!cancelled){
                flagLocations.add(deepslateLocation);

                deepslateLocation.getBlock().setType(Material.DEEPSLATE);
                torchLocation.getBlock().setType(Material.TORCH);

                Player eventPlayer = e.getPlayer();
                String playerName = eventPlayer.getName();

                for (Player playerM : Bukkit.getOnlinePlayers()) {
                    playerM.sendMessage(ChatColor.DARK_RED + "[War] " + playerName + " is attacking London at (" + blockX + "," + blockY + "," + blockZ + ")");
                }

                plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                    public void run() {

                        if (block.getType().equals(Material.OAK_FENCE)){

                            block.setType(Material.AIR);
                            torchLocation.getBlock().setType(Material.AIR);
                            deepslateLocation.getBlock().setType(Material.AIR);

                            for (Player player : Bukkit.getOnlinePlayers()) {
                                int chunkX = block.getChunk().getX();
                                int chunkZ = block.getChunk().getZ();
                                player.sendMessage(ChatColor.DARK_RED + "[War] " + playerName + " captured chunk (" + chunkX + "," + chunkZ + ") from London");
                            }
                            // Remove the flag location from the list after it's captured
                            flagLocations.remove(deepslateLocation);
                        }
                    }
                }, 200);
            }
        }
    }

    // Getter method for the flagLocations list
    public List<Location> getFlagLocations() {
        return flagLocations;
    }

    private boolean canSeeSky(World world ,int x ,int y ,int z){
        Block highestBlock = world.getHighestBlockAt(x,z);
        return !(highestBlock.getY() > y);
    }
}