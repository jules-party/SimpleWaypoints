package tavonatti.stefano.spigot_plugin.waypoints.commands;

import com.sun.tools.jdeprscan.scan.Scan;
import jdk.vm.ci.meta.Local;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import tavonatti.stefano.spigot_plugin.waypoints.utils.Permissions;
import tavonatti.stefano.spigot_plugin.waypoints.utils.TPUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Properties;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class CommandWTP implements CommandExecutor {
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;

            if(!player.hasPermission(Permissions.WAYPOINTS.permission)){
                player.sendMessage(""+ ChatColor.RED+"You don't have the permission to do this!");
                return true;
            }

            if (strings.length < 1) {
                player.sendMessage("use /wtp <name>");
                return true;
            }

            Properties properties = new Properties();

            for(World world : Bukkit.getWorlds()) {
                System.out.println("waypoints/" + player.getName() + "-" +
                        world.getName() + ".properties");

                //load file
                File waypointFile = new File("waypoints/" + player.getName() + "-" +
                        world.getName() + ".properties");

                if (CommandWSave.loadWaypointFile(waypointFile, properties)) return true;
            }

            if(properties.getProperty(strings[0])==null){
                player.sendMessage(ChatColor.RED+"Waypoint does not exist!");
                return true;
            }

            String waypointName = strings[0];
            String[] waypointString = properties.getProperty(strings[0]).split(" ");

            double x,y,z;

            x=Double.parseDouble(waypointString[0]);
            y=Double.parseDouble(waypointString[1]);
            z=Double.parseDouble(waypointString[2]);

            Location location = null;
            World waypointWorld = null;
            for (World world : Bukkit.getWorlds()) {
                File waypointFile = new File("waypoints/"+player.getName()+"-"+world.getName()+".properties");
                if(waypointFile.exists()) {
                    try {
                        Scanner scanner = new Scanner(waypointFile);
                        while(scanner.hasNextLine()) {
                            String lineFromFile = scanner.nextLine();
                            if(lineFromFile.contains(strings[0])) {
                                waypointWorld = world;
                                break;
                            }
                        }
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            if(waypointWorld != null) {
                location = new Location(waypointWorld,x,y,z);
                TPUtils.teleportPlayer(player,location);
                player.sendMessage("["+ChatColor.GREEN+"SimpleWP"+ChatColor.RESET+"] Teleported to: "+waypointName);
            }
        }

        return true;
    }


}
