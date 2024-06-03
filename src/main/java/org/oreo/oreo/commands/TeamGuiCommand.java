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

public class TeamGuiCommand implements CommandExecutor , Listener {

    private String invName = "team_picker";

    private final OreosPlugin plugin;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] strings) {

        System.out.println("Ran");

        if (sender instanceof Player){//making sure whoever sent this was in fact a player to avoid console shenanigans
            Player player = (Player) sender;

            openInventory(player);

            return true;
        }
        return false;
    }

    private final Inventory inv;

    public TeamGuiCommand(OreosPlugin plugin){
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this,plugin);

        // Create a new inventory, with no owner (as this isn't a real inventory), a size of 2, called example
        inv = Bukkit.createInventory(null, 9, invName);

        // Put the items into the inventory
        initializeItems();
    }

    // You can call this whenever you want to put the items in
    public void initializeItems() {
        inv.addItem(createGuiItem(Material.GRAY_STAINED_GLASS_PANE, "Blank", "   "));
        inv.addItem(createGuiItem(Material.GRAY_STAINED_GLASS_PANE, "Blank", "  "));
        inv.addItem(createGuiItem(Material.GRAY_STAINED_GLASS_PANE, "Blank", " "));

        inv.addItem(createGuiItem(Material.RED_WOOL, "Red Team", ""));
        inv.addItem(createGuiItem(Material.BARRIER , "Close", ""));
        inv.addItem(createGuiItem(Material.BLUE_WOOL, "Blu Team", ""));

        inv.addItem(createGuiItem(Material.GRAY_STAINED_GLASS_PANE, "Blank", "    "));
        inv.addItem(createGuiItem(Material.GRAY_STAINED_GLASS_PANE, "Blank", "     "));
        inv.addItem(createGuiItem(Material.GRAY_STAINED_GLASS_PANE, "Blank", "      "));
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

    // You can close the inventory with this
    public void closeInventory(final HumanEntity ent) {
        ent.closeInventory();
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
            case 3:
                plugin.addPlayerToRedTeam(p);
                closeInventory(p);
                break;
            case 4:
                closeInventory(p);
                break;
            case 5:
                plugin.addPlayerToBluTeam(p);
                closeInventory(p);
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
}
