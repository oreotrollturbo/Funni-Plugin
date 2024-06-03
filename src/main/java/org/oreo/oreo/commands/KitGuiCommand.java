package org.oreo.oreo.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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

public class KitGuiCommand implements CommandExecutor , Listener { //The listener part is required to listen for players trying to drag the item out of the inventory

    private String invName = "Choose your kit"; //The name of the gui (The text displayed above the inventory spaces)

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] strings) { //When the command is executed

        if (sender instanceof Player){//making sure whoever sent this was in fact a player to avoid console shenanigans

            Player player = (Player) sender; //Convert the sender object into a player object since we know its one (for utility)
            openInventory(player); // Open the custom inventory for the player

            return true; //Command successful
        }
        return false; //If it isn't a player do nothing
    }

    private final Inventory inv; // Declare the inventory to be used in various methods

    public KitGuiCommand(OreosPlugin plugin){

        Bukkit.getPluginManager().registerEvents(this,plugin);

        // Create a new inventory, with no owner (as this isn't a real inventory), a size of nine, called example
        inv = Bukkit.createInventory(null, 9, invName);

        // Put the items into the inventory
        initializeItems();
    }

    // Method used to put all of the items in the custom inventory
    public void initializeItems() {
        inv.setItem(3,createGuiItem(Material.DIAMOND_SWORD, "Basic Kit", ""));
        inv.setItem(4,createGuiItem(Material.STONE, "Builder Kit", ""));  //The items that will correspond to kits
        inv.setItem(5,createGuiItem(Material.BOW, "Ranger Kit", ""));
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
        if (!e.getView().getTitle().equals(invName)) return; //If it isn't our custom inventory don't do anything

        e.setCancelled(true); //Stop people from taking items out of the inventory via clicking

        final ItemStack clickedItem = e.getCurrentItem();

        // verify current item is not null
        if (clickedItem == null || clickedItem.getType().isAir()) return;

        final Player p = (Player) e.getWhoClicked();

        // Each number corresponds to a slot ( 0 is top left )

        switch (e.getRawSlot()){
            case 3:
                p.getInventory().clear();
                giveBasicKit(p);
                p.closeInventory();
                p.sendMessage(ChatColor.GREEN + "Kit selected successfully");
                break;
            case 4:
                p.getInventory().clear(); //We always clear the players inventory before giving them a new kit to avoid kit combo shenanigans
                giveBuilderKit(p);
                p.closeInventory();
                p.sendMessage(ChatColor.GREEN + "Kit selected successfully");
                break;
            case 5:
                p.getInventory().clear();
                giveRangerKit(p);
                p.closeInventory();
                p.sendMessage(ChatColor.GREEN + "Kit selected successfully");
                break;
        }

    }

    // Cancel dragging in our inventory
    @EventHandler
    public void onInventoryClick(final InventoryDragEvent e) {
        if (e.getView().getTitle().equals(invName)) {
            e.setCancelled(true); //Stop people from taking items out of the inventory via dragging
        }
    }


    private void giveBasicKit(Player player){ //The basic kit and all of its items
        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
        ItemStack pick = new ItemStack(Material.STONE_PICKAXE);
        ItemStack food = new ItemStack(Material.COOKED_BEEF);

        ItemStack helm = new ItemStack(Material.IRON_HELMET);
        ItemStack chest = new ItemStack(Material.IRON_CHESTPLATE);
        ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS);
        ItemStack boots = new ItemStack(Material.IRON_BOOTS);
        ItemStack shield = new ItemStack(Material.SHIELD);

        food.setAmount(64); // Make the kit give you 64 of the food item

        player.getInventory().addItem(sword);
        player.getInventory().addItem(food);
        player.getInventory().addItem(pick);

        player.getInventory().setItemInOffHand(shield);//Adding the shield directly to the offhand

        player.getInventory().setBoots(boots);
        player.getInventory().setLeggings(leggings);
        player.getInventory().setChestplate(chest);//Adding the armor directly to the armor slots
        player.getInventory().setHelmet(helm);
    }

    private void giveBuilderKit(Player player){ //Gives the builder kit
        ItemStack sword = new ItemStack(Material.IRON_SWORD);
        ItemStack pick = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemStack food = new ItemStack(Material.COOKED_BEEF);
        ItemStack blocks = new ItemStack(Material.STONE);

        ItemStack helm = new ItemStack(Material.IRON_HELMET);
        ItemStack chest = new ItemStack(Material.IRON_CHESTPLATE);
        ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS);
        ItemStack boots = new ItemStack(Material.IRON_BOOTS);

        food.setAmount(64);
        blocks.setAmount(64);

        player.getInventory().addItem(sword);
        player.getInventory().addItem(food);
        player.getInventory().addItem(pick);

        player.getInventory().setItemInOffHand(blocks); //The blocks are in the offhand

        player.getInventory().setBoots(boots);
        player.getInventory().setLeggings(leggings);//Adding the armor directly to the armor slots
        player.getInventory().setChestplate(chest);
        player.getInventory().setHelmet(helm);
    }

    private void giveRangerKit(Player player){ //Gives the ranger kit
        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
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

        player.getInventory().addItem(sword);
        player.getInventory().addItem(food);
        player.getInventory().addItem(bow);
        player.getInventory().addItem(pick);

        player.getInventory().addItem(arrows);

        player.getInventory().setBoots(boots);
        player.getInventory().setLeggings(leggings);
        player.getInventory().setChestplate(chest); //Adding the armor directly to the armor slots
        player.getInventory().setHelmet(helm);
    }
}
