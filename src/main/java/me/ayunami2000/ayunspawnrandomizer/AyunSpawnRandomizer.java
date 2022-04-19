package me.ayunami2000.ayunspawnrandomizer;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

import java.util.Random;

public final class AyunSpawnRandomizer extends JavaPlugin implements Listener {
    private static final Random random = new Random();

    private static int minX,minZ,maxX,maxZ,y;
    private static World world;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        minX = getConfig().getInt("min.x");
        minZ = getConfig().getInt("min.z");
        maxX = getConfig().getInt("max.x");
        maxZ = getConfig().getInt("max.z");
        y = getConfig().getInt("y");
        world = getServer().getWorld(getConfig().getString("world"));
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {}

    @EventHandler
    public void onPlayerSpawn(PlayerSpawnLocationEvent event){
        event.setSpawnLocation(randomLocation(event.getSpawnLocation().getWorld()));
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event){
        event.setRespawnLocation(randomLocation(world));
    }

    private static Location randomLocation(World world){
        return new Location(world, random.nextInt(maxX - minX) + minX, y, random.nextInt(maxZ - minZ) + minZ);
    }
}
