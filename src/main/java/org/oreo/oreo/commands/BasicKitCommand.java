package org.oreo.oreo.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BasicKitCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] strings) {

        if (sender instanceof Player) {
            Player player = (Player) sender;



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

            return true;
        }
        return false;
    }
}
