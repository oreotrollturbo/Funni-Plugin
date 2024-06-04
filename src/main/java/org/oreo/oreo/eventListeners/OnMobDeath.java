package org.oreo.oreo.eventListeners;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.oreo.oreo.OreosPlugin;

public class OnMobDeath implements Listener {

    private final OreosPlugin plugin;

    public OnMobDeath(OreosPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void OnMobKilled(EntityDeathEvent event) {

        LivingEntity entity = event.getEntity();
        EntityType type = event.getEntityType();
        Location location = entity.getLocation(); //Get the mob that died its location, its type etc.
        Player player = entity.getKiller();
        assert player != null;
        World w = event.getEntity().getWorld();

        w.spawnEntity(location,type); // Spawn the same entity exactly where it died
        // w.spawnEntity(location,type); //Uncommenting this will make the mobs "duplicate" on death
    }
}
