package org.oreo.oreo.eventListeners;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.oreo.oreo.OreosPlugin;

public class OnMobKilled implements Listener {

    private final OreosPlugin plugin;

    public OnMobKilled(OreosPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void OnMobKilled(EntityDeathEvent event) {

        if (event.getEntity() instanceof Creeper) { //Checks if a creeper was killed
            LivingEntity creeper = event.getEntity();
            Player player = creeper.getKiller();//Alll this is setup stuff
            assert player != null;
            World w = event.getEntity().getWorld();

            ConfigurationSection section = this.plugin.getConfig().getConfigurationSection("on-creeper-death");
            //So it gets the right section and then the values

            assert section != null;
            if (section.getBoolean("creeper-explode-on-death")){ //If its enabled in the config
                w.createExplosion(creeper.getLocation(),4);//Creates an intsant explosion
            }else if (section.getBoolean("creeper-spawn-tnt-on-death")){
                TNTPrimed tnt = (TNTPrimed)w.spawnEntity(creeper.getLocation(), EntityType.PRIMED_TNT);
                tnt.setFuseTicks(20);
            }
        }

        LivingEntity entity = event.getEntity();
        EntityType type = event.getEntityType();
        Location location = entity.getLocation();
        Player player = entity.getKiller();
        assert player != null;
        World w = event.getEntity().getWorld();

        w.spawnEntity(location,type);
    }
}
