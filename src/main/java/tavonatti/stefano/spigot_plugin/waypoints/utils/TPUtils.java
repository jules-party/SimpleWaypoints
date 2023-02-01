package tavonatti.stefano.spigot_plugin.waypoints.utils;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class TPUtils {

    /**
     * teleport the player (and the vehicle) to a location
     * @param player
     * @param location
     */
    public static void teleportPlayer(Player player, Location location){
        if(player.isInsideVehicle()){

            double x=location.getX();
            double y=location.getY();
            double z=location.getZ();

            /*if the player is inside a veichle, move the veichle*/
            Entity vehicle=player.getVehicle();
            location=new Location(player.getWorld(),x,y+1,z);

            vehicle.eject();//eject the player from the veichle

            //teleport the player and the veichle
            vehicle.teleport(location);
            player.teleport(location);

            //put the player on the veichle
            vehicle.addPassenger(player);
        }
        else {
            player.teleport(location);
        }
    }
}
