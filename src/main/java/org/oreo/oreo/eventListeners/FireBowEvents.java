package org.oreo.oreo.eventListeners;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.oreo.oreo.OreosPlugin;

import java.util.ArrayList;
import java.util.List;

public class FireBowEvents implements Listener {

    private final OreosPlugin plugin;
    private final List<Player> fireArrowPlayer;
    private final List<Arrow> fireArrow;

    public FireBowEvents(OreosPlugin plugin){
        this.plugin = plugin;
        fireArrowPlayer = new ArrayList<Player>();
        fireArrow = new ArrayList<Arrow>();
    }

    @EventHandler
    public void BowLight(PlayerInteractEvent e) {
        Player player = e.getPlayer(); //Get the player
        Block block = e.getClickedBlock(); //Get the block
        World world = player.getWorld();

        if (block != null && isHoldingBow(player) && isCorrectBlock(block) && !fireArrowPlayer.contains(player)){ // Make sure the block isn't null to avoid errors
            fireArrowPlayer.add(player);
            world.playSound(player.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 1f, 1f);
        }
    }

    private boolean isHoldingBow(Player player){ //What item the player needs to be holding a bow
        return player.getInventory().getItemInMainHand().getType().equals(Material.BOW) || player.getInventory().getItemInMainHand().getType().equals(Material.CROSSBOW);
    }
    private boolean isCorrectBlock(Block block){ //Player needs to be interacting with a magma block fire or a campfire
        return block.getBlockData().getMaterial().equals(Material.FIRE) ||
                block.getBlockData().getMaterial().equals(Material.CAMPFIRE) || block.getBlockData().getMaterial().equals(Material.MAGMA_BLOCK);
    }

    @EventHandler
    public void BowShoot(ProjectileLaunchEvent e){ //When someone shoots a bow

        Entity entity = e.getEntity();
        System.out.println("Detected");

        if (entity instanceof Arrow){ //Make sure its an arrow
            Arrow arrow = (Arrow) entity; //See the entity as an arrow object

            if (arrow.getShooter() instanceof Player){ //if the arrow is shot by a player get the player Who shot it
                Player player = ((Player) arrow.getShooter()).getPlayer();

                if (fireArrowPlayer.contains(player)){//If the shooter had a "lit arrow" make the arrow deal fire damage
                    fireArrowPlayer.remove(player);
                    fireArrow.add(arrow);
                    arrow.setVisualFire(true); // 300 ticks = 15 secs
                    // The fire ticks account for the time the arrow has traveled as well
                    //Rain extinguishes the fire arrows
                }
            }
        }
    }

    @EventHandler
    public void ArrowHit(EntityDamageByEntityEvent e){
        Entity entity = e.getDamager(); //Get what entity dealt the damage

        int config = this.plugin.getConfig().getInt("arrow-time-burning");
        //Getting the config's value

        if (entity instanceof Arrow){ //If what dealt damage is an arrow
            Arrow arrow = (Arrow) entity;
            if (fireArrow.contains(arrow)){ //If the arrow is on the list
                fireArrow.remove(arrow); //Remove the arrow from the list
                e.getEntity().setFireTicks(config); //Set for how long the entity hit burns in ticks
                // 200 ticks by default (10 seconds)
            }
        }
    }
}