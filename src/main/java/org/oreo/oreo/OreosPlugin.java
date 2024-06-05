package org.oreo.oreo;

import com.google.common.cache.LoadingCache;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
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

        OnFlagPlaced syncFlags = new OnFlagPlaced(this); // This is to synchronise the flagLocation List

        getCommand("suicide").setExecutor(new SuicideCommand());
        getCommand("toggle_building").setExecutor(new ToggleWarModeCommand(syncFlags));
        getCommand("open_gui").setExecutor(new KitGuiCommand(this)); //Setting up the commands
        getCommand("team_gui").setExecutor(new TeamGuiCommand(this));


        getServer().getPluginManager().registerEvents(new OnDroppedItem(),this);
        getServer().getPluginManager().registerEvents(syncFlags, this);
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new OnBlockBroken(syncFlags), this);
        getServer().getPluginManager().registerEvents(new OnCreeperDeath(this), this); // This makes creepers explode on death when killed
        //getServer().getPluginManager().registerEvents(new OnMobDeath(this), this); // This spawns a copy of the mob where it died
        getServer().getPluginManager().registerEvents(new FireBowEvents(), this);

        saveDefaultConfig();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) { //Handle player joining
        Player player = event.getPlayer();
        String playername = player.getName();

        if (playername.contains("oreotrollturbo")) { // If its me send a funni message
            player.sendMessage(ChatColor.DARK_RED + "Welcome back me :)");
        } else {
            event.setJoinMessage(ChatColor.GREEN + "Welcome to Oreo's plugin " + playername); // Else welcome the player
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) { //Remove the player who left from his team
        Player player = event.getPlayer();
        String playername = player.getName();

        removePlayerFromTeam(playername);
    }

    public void addPlayerToRedTeam(Player player){ // A simple function to add a player to a team from any class
        redTeam.addEntry(player.getName()); //Add them to the team object
        Location spawn = new Location(player.getWorld(),0,100,0);
        player.setScoreboard(scoreboard); //Give them a fresh scoreboard
        player.sendMessage(ChatColor.GREEN + "Added you to the red team successfully !");
    }

    public void addPlayerToBluTeam(Player player){ // A simple function to add a player to the blue team from any class
        bluTeam.addEntry(player.getName()); //Add them to the team object
        player.setScoreboard(scoreboard); //Give them a fresh scoreboard
        player.sendMessage(ChatColor.GREEN + "Added you to the blu team successfully !");
    }

    public void removePlayerFromTeam(String name){ //A simple function to remove players from their team

    //Having this hard-coded might not be the best way to go about it but for only two teams it will do ok
        if (redTeam.hasEntry(name)){
            redTeam.removeEntry(name);
        } else if (bluTeam.hasEntry(name)) {
            bluTeam.removeEntry(name);
        }
    }
}
