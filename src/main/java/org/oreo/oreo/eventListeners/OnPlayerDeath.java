package org.oreo.oreo.eventListeners;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.oreo.oreo.OreosPlugin;

public class OnPlayerDeath implements Listener {

    private final OreosPlugin plugin;

    public OnPlayerDeath(OreosPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void WhenPlayerDies(PlayerRespawnEvent event) {
        World world = event.getPlayer().getWorld();
        Location spawnLocation = new Location(world,236,149,210);
        event.setRespawnLocation(spawnLocation);
    }
}
