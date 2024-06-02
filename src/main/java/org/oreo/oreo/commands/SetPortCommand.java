package org.oreo.oreo.commands;

import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.oreo.oreo.ConfigReader;
import org.oreo.oreo.OreosPlugin;

import java.io.File;
import java.util.List;

public class SetPortCommand implements CommandExecutor {



    public SetPortCommand(OreosPlugin plugin) {
    }

//    public static ConfigReader AConfig;
//    FileConfiguration aconfig = AConfig.getConfig();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] strings) {


        if (sender instanceof Player){//making sure whoever sent this was a player to avoid console shenanigans
            Player player = (Player) sender;

            Chunk chunk = player.getLocation().getChunk();

//            aconfig.set("Port","element");
//            ConfigReader.save(this.AConfig);

            return true;
        }

        return false;
    }
}
