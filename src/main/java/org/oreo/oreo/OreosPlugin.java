package org.oreo.oreo;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.oreo.oreo.commands.BasicKitCommand;
import org.oreo.oreo.commands.OpenGuiCommand;
import org.oreo.oreo.commands.SetPortCommand;
import org.oreo.oreo.commands.SuicideCommand;
import org.oreo.oreo.eventListeners.*;

import java.io.File;


public final class OreosPlugin extends JavaPlugin {

    private File customConfigFile;
    private FileConfiguration customConfig;

    public static ConfigReader AConfig;

    @Override
    public void onEnable() {

        // Plugin startup logic

        //Setting up custom config files for saving

//        AConfig = new ConfigReader(this,"settings/","AConfig.yml");
//        AConfig.saveDefaultConfig();
//
//        FileConfiguration aconfig = AConfig.getConfig();
//        aconfig.set("ports","yes");
//        ConfigReader.save(this.AConfig);

        //Setting up commands
        getLogger().info("onEnable is called!"); // Just to make sure
        getCommand("kit_basic").setExecutor(new BasicKitCommand(this));
        getCommand("suicide").setExecutor(new SuicideCommand());
        getCommand("set_port").setExecutor(new SetPortCommand(this));
        getCommand("open_gui").setExecutor(new OpenGuiCommand(this));

        saveDefaultConfig();
        //Setting up Listeners
        getServer().getPluginManager().registerEvents(new OnPlayerJoin(),this);
        getServer().getPluginManager().registerEvents(new OnFlagPlaced(this),this);
        getServer().getPluginManager().registerEvents(new OnFlagBroken(),this);
        //getServer().getPluginManager().registerEvents(new OnMobKilled(this),this); //This Spawns a copy of the mob that died where the mob died
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("onDisable is called!");
        ConfigReader.save(this.AConfig);
    }
}
