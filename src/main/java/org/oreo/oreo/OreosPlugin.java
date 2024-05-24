package org.oreo.oreo;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.oreo.oreo.commands.BasicKitCommand;
import org.oreo.oreo.commands.SuicideCommand;
import org.oreo.oreo.eventListeners.OnMobKilled;
import org.oreo.oreo.eventListeners.OnPlayerJoin;

public final class OreosPlugin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("onEnable is called!"); // Just to make sure
        this.getCommand("kit_basic").setExecutor(new BasicKitCommand());
        this.getCommand("suicide").setExecutor(new SuicideCommand());

        getServer().getPluginManager().registerEvents(new OnPlayerJoin(),this);
        getServer().getPluginManager().registerEvents(new OnMobKilled(),this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("onDisable is called!");
    }
}
