package org.oreo.oreo.eventListeners;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class OnMobKilled implements Listener {

    @EventHandler
    public void OnMobKilled(EntityDeathEvent event) {

        if (event.getEntity() instanceof Creeper) { //Checks if a creeper was killed
            LivingEntity creeper = event.getEntity();
            Player player = creeper.getKiller();//Alll this is setup stuff
            assert player != null;
            World w = player.getWorld();

            TNTPrimed tnt = (TNTPrimed)w.spawnEntity(creeper.getLocation(), EntityType.PRIMED_TNT);
            tnt.setFuseTicks(20);
            //w.createExplosion(creeper.getLocation(),4);//Creates an intsant explosion
            //w.spawnEntity(creeper.getLocation(), EntityType.PRIMED_TNT); //Spawns tnt on the dead creeper
        }
    }
}
