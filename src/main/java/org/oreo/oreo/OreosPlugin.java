package org.oreo.oreo;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;
import org.oreo.oreo.commands.*;
import org.oreo.oreo.eventListeners.*;

public final class OreosPlugin extends JavaPlugin implements Listener  {

    private Scoreboard scoreboard;
    private Team redTeam;
    private Team bluTeam;

    @Override
    public void onEnable() {// Plugin startup logic

        // Set up the scoreboard manager and main scoreboard
        ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
        assert scoreboardManager != null;
        scoreboard = scoreboardManager.getMainScoreboard();

        // Create or get the red team
        redTeam = scoreboard.getTeam("Red");
        if (redTeam == null) {
            redTeam = scoreboard.registerNewTeam("Red");
        }
        redTeam.setPrefix("[Red] ");
        redTeam.setColor(ChatColor.RED);

        // Create or get the blu team
        bluTeam = scoreboard.getTeam("Blu");
        if (bluTeam == null) {
            bluTeam = scoreboard.registerNewTeam("Blu");
        }
        bluTeam.setPrefix("[Blu] ");
        bluTeam.setColor(ChatColor.BLUE);


        getLogger().info("onEnable is called!"); // Just to make sure

        getCommand("suicide").setExecutor(new SuicideCommand());
        getCommand("open_gui").setExecutor(new KitGuiCommand(this)); //Setting up the commands
        getCommand("team_gui").setExecutor(new TeamGuiCommand(this));

        OnFlagPlaced syncFlags = new OnFlagPlaced(this); // This is to synchronise the flagLocation List
        getServer().getPluginManager().registerEvents(syncFlags, this);
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new OnFlagBroken(syncFlags), this);
        // getServer().getPluginManager().registerEvents(new OnMobKilled(this), this); // This Spawns a copy of the mob that died where the mob died

        saveDefaultConfig();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) { //Handle player joining
        Player player = event.getPlayer();
        String playername = player.getName();

        if (playername.contains("oreotrollturbo")) {
            player.sendMessage(ChatColor.DARK_RED + "Welcome back me :)");
        } else {
            event.setJoinMessage("Welcome to Oreo's plugin " + playername);
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) { //Remove players from teams
        Player player = event.getPlayer();
        String playername = player.getName();

        removePlayerFromTeam(playername);
    }

    public void addPlayerToRedTeam(Player player){
        redTeam.addEntry(player.getName());
        player.setScoreboard(scoreboard);
        player.sendMessage(ChatColor.GREEN + "Added you to the red team successfully !");
    }

    public void addPlayerToBluTeam(Player player){
        bluTeam.addEntry(player.getName());
        player.setScoreboard(scoreboard);
        player.sendMessage(ChatColor.GREEN + "Added you to the blu team successfully !");
    }

    public void removePlayerFromTeam(String name){

        if (redTeam.hasEntry(name)){
            redTeam.removeEntry(name);
        } else if (bluTeam.hasEntry(name)) {
            bluTeam.removeEntry(name);
        }
    }
}
