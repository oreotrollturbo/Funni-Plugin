package org.oreo.oreo.eventListeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class OnFlagBroken implements Listener {

    @EventHandler
    public void onFlagBroken(BlockBreakEvent e) {

        Block block = (Block) e.getBlock();

        if (block.getBlockData().getMaterial().equals(Material.OAK_FENCE)) {
            Player eventPlayer = e.getPlayer();
            String playerName = eventPlayer.getName();


            int chunkX = block.getChunk().getX();
            int chunkZ = block.getChunk().getZ();

            for (Player player : Bukkit.getOnlinePlayers()) {
                player.sendMessage(ChatColor.DARK_RED + "[War] " + playerName + " defended chunk (" + chunkX + "," + chunkZ + ") against Naples!");
            }
        }
    }
}
