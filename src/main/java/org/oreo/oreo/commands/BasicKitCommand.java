package org.oreo.oreo.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.oreo.oreo.OreosPlugin;

import java.util.HashMap;
import java.util.UUID;

public class BasicKitCommand implements CommandExecutor {

    //Key = UUID of the player
    //long = when epoch time of when they ran the command
    private final HashMap<UUID, Long> cooldown;

    private final OreosPlugin plugin; //Getting the plugin instance


    public BasicKitCommand(OreosPlugin plugin) {
        this.cooldown = new HashMap<>();
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] strings) {
        ConfigurationSection section = this.plugin.getConfig().getConfigurationSection("kit-commands");


        if (sender instanceof Player) { //Make sure a player sent the command
            Player player = (Player) sender;

            if (!section.getBoolean("command-cooldown-on")){ //if the command cooldown is off then simply give the items
                giveItems(player); //Gives the items
            }else if(!this.cooldown.containsKey(player.getUniqueId())){ //if its on add new players to the hash map
                this.cooldown.put(player.getUniqueId(),System.currentTimeMillis());
                giveItems(player);
            }else {// if player is in the hashmap see if the cooldown is over or not

                //Tim difference in mils
                long timeElapsed = System.currentTimeMillis() - cooldown.get(player.getUniqueId());

                //10 secs
                if (timeElapsed >= section.getInt("command-cooldown")){

                    this.cooldown.put(player.getUniqueId(),System.currentTimeMillis());
                    giveItems(player);

                    return true;
                }else {
                    player.sendMessage(ChatColor.RED + section.getString("command-cooldown-fail-message"));
                }
            }
        }
        return false;
    }

    private void giveItems(Player player){

        ItemStack diamond = new ItemStack(Material.DIAMOND_SWORD);

        ItemStack stone = new ItemStack(Material.STONE);

        ItemStack food = new ItemStack(Material.COOKED_BEEF);

        ItemStack helm = new ItemStack(Material.IRON_HELMET);
        ItemStack chest = new ItemStack(Material.IRON_CHESTPLATE);
        ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS);
        ItemStack boots = new ItemStack(Material.IRON_BOOTS);

        ItemStack shield = new ItemStack(Material.SHIELD);

        food.setAmount(64);
        stone.setAmount(64);

        player.getInventory().addItem(diamond);
        player.getInventory().addItem(food);
        player.getInventory().addItem(shield);
        player.getInventory().addItem(leggings);
        player.getInventory().addItem(chest);
        player.getInventory().addItem(boots);
        player.getInventory().addItem(helm);
        player.getInventory().addItem(stone);

    }
}
