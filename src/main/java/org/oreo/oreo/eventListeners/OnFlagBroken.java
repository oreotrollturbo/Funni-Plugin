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


public class OnFlagBroken implements Listener {

    private final OnFlagPlaced onFlagPlaced;

    public OnFlagBroken(OnFlagPlaced onFlagPlaced) {
        this.onFlagPlaced = onFlagPlaced;
    }

    @EventHandler
    public void onFlagBroken(BlockBreakEvent e) {

        Block block = e.getBlock();

        int blockX = block.getX();
        int blockY = block.getY();
        int blockZ = block.getZ();

        if (block.getBlockData().getMaterial().equals(Material.OAK_FENCE)) {
            Location deepslateLocation = new Location(block.getWorld(),blockX,blockY + 1,blockZ);

            if (deepslateLocation.getBlock().getBlockData().getMaterial().equals(Material.DEEPSLATE)){
                e.setCancelled(true);
            }

        } else if (block.getBlockData().getMaterial().equals(Material.TORCH)){
            Location deepslateLocation = new Location(block.getWorld(),blockX,blockY - 1,blockZ);

            if (deepslateLocation.getBlock().getBlockData().getMaterial().equals(Material.DEEPSLATE)){
                e.setCancelled(true);
            }
        } else if (block.getBlockData().getMaterial().equals(Material.DEEPSLATE)){

            Location blockLocation = block.getLocation();

            // Removing flag location from the list in OnFlagPlaced
            onFlagPlaced.getFlagLocations().remove(blockLocation);

            Player eventPlayer = e.getPlayer();
            String playerName = eventPlayer.getName();

            Location fenceLocation = new Location(block.getWorld(), blockX,blockY - 1,blockZ);
            Location torchLocation = new Location(block.getWorld(), blockX,blockY + 1,blockZ);

            Material torchMaterial = torchLocation.getBlock().getType();
            Material fenceMaterial = fenceLocation.getBlock().getType();

            if (torchMaterial.equals(Material.TORCH) && fenceMaterial.equals(Material.OAK_FENCE)){
                int chunkX = block.getChunk().getX();
                int chunkZ = block.getChunk().getZ();

                torchLocation.getBlock().setType(Material.AIR);
                fenceLocation.getBlock().setType(Material.AIR);

                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.sendMessage(ChatColor.DARK_RED + "[War] " + playerName + " defended chunk (" + chunkX + "," + chunkZ + ") against Naples!");
                }
            }
        }
    }
}
