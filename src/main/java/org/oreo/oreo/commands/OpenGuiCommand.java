package org.oreo.oreo.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.oreo.oreo.OreosPlugin;


import java.util.Arrays;

public class OpenGuiCommand implements CommandExecutor , Listener {

    private String invName = "oreo Gui";

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] strings) {

        if (sender instanceof Player){//making sure whoever sent this was in fact a player to avoid console shenanigans
            Player player = (Player) sender;

            openInventory(player);

            return true;
        }

        return false;
    }

    private final Inventory inv;

    public OpenGuiCommand(OreosPlugin plugin){

        Bukkit.getPluginManager().registerEvents(this,plugin);

        // Create a new inventory, with no owner (as this isn't a real inventory), a size of nine, called example
        inv = Bukkit.createInventory(null, 9, invName);

        // Put the items into the inventory
        initializeItems();
    }

    // You can call this whenever you want to put the items in
    public void initializeItems() {
        inv.addItem(createGuiItem(Material.DIAMOND_SWORD, "Basic Kit", ""));
        inv.addItem(createGuiItem(Material.STONE, "Builder Kit", ""));
        inv.addItem(createGuiItem(Material.BOW, "Ranger Kit", ""));
    }

    // Nice little method to create a gui item with a custom name, and description
    protected ItemStack createGuiItem(final Material material, final String name, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        // Set the name of the item
        assert meta != null;
        meta.setDisplayName(name);

        // Set the lore of the item
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }

    // You can open the inventory with this
    public void openInventory(final HumanEntity ent) {
        ent.openInventory(inv);
    }

    // Check for clicks on items
    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e) {
        if (!e.getView().getTitle().equals(invName)) return;

        e.setCancelled(true);

        final ItemStack clickedItem = e.getCurrentItem();

        // verify current item is not null
        if (clickedItem == null || clickedItem.getType().isAir()) return;

        final Player p = (Player) e.getWhoClicked();

        // Using slots click is a best option for your inventory click's

        switch (e.getRawSlot()){
            case 0:
                giveBasicKit(p);
                break;
            case 1:
                giveBuilderKit(p);
                break;
            case 2:
                giveRangerKit(p);
                break;
            case 3:
                p.sendMessage("You clicked at the third slot");
                break;
            case 4:
                p.sendMessage("You clicked at the fourth slot");
                break;
            case 5:
                p.sendMessage("You clicked at the fifth slot");
                break;
            case 6:
                p.sendMessage("You clicked at the sixth slot");
                break;
            case 7:
                p.sendMessage("You clicked at the seventh slot");
                break;
            case 8:
                p.sendMessage("You clicked at the eighth slot");
                break;
        }

    }

    // Cancel dragging in our inventory
    @EventHandler
    public void onInventoryClick(final InventoryDragEvent e) {
        if (e.getView().getTitle().equals(invName)) {
            e.setCancelled(true);
        }
    }


    private void giveBasicKit(Player player){
        ItemStack diamond = new ItemStack(Material.DIAMOND_SWORD);
        ItemStack pick = new ItemStack(Material.STONE_PICKAXE);
        ItemStack food = new ItemStack(Material.COOKED_BEEF);

        ItemStack helm = new ItemStack(Material.IRON_HELMET);
        ItemStack chest = new ItemStack(Material.IRON_CHESTPLATE);
        ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS);
        ItemStack boots = new ItemStack(Material.IRON_BOOTS);
        ItemStack shield = new ItemStack(Material.SHIELD);

        food.setAmount(64);

        player.getInventory().addItem(diamond);
        player.getInventory().addItem(pick);
        player.getInventory().addItem(food);
        player.getInventory().addItem(shield);
        player.getInventory().addItem(leggings);
        player.getInventory().addItem(chest);
        player.getInventory().addItem(boots);
        player.getInventory().addItem(helm);
    }

    private void giveBuilderKit(Player player){
        ItemStack diamond = new ItemStack(Material.IRON_SWORD);
        ItemStack pick = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemStack food = new ItemStack(Material.COOKED_BEEF);
        ItemStack blocks = new ItemStack(Material.STONE);

        ItemStack helm = new ItemStack(Material.IRON_HELMET);
        ItemStack chest = new ItemStack(Material.IRON_CHESTPLATE);
        ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS);
        ItemStack boots = new ItemStack(Material.IRON_BOOTS);

        food.setAmount(64);
        blocks.setAmount(64);

        player.getInventory().addItem(diamond);
        player.getInventory().addItem(pick);
        player.getInventory().addItem(food);
        player.getInventory().addItem(blocks);
        player.getInventory().addItem(leggings);
        player.getInventory().addItem(chest);
        player.getInventory().addItem(boots);
        player.getInventory().addItem(helm);
    }

    private void giveRangerKit(Player player){
        ItemStack diamond = new ItemStack(Material.DIAMOND_SWORD);
        ItemStack bow = new ItemStack(Material.BOW);
        ItemStack pick = new ItemStack(Material.STONE_PICKAXE);
        ItemStack food = new ItemStack(Material.COOKED_BEEF);
        ItemStack arrows = new ItemStack(Material.ARROW);

        ItemStack helm = new ItemStack(Material.LEATHER_HELMET);
        ItemStack chest = new ItemStack(Material.LEATHER_CHESTPLATE);
        ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);

        food.setAmount(64);
        arrows.setAmount(64);

        player.getInventory().addItem(diamond);
        player.getInventory().addItem(bow);
        player.getInventory().addItem(pick);
        player.getInventory().addItem(food);
        player.getInventory().addItem(arrows);
        player.getInventory().addItem(leggings);
        player.getInventory().addItem(chest);
        player.getInventory().addItem(boots);
        player.getInventory().addItem(helm);
    }
}
