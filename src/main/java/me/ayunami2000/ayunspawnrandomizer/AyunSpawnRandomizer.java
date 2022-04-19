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

    @Override
    public void onEnable() {
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
        event.setRespawnLocation(randomLocation(event.getRespawnLocation().getWorld()));
    }

    private static Location randomLocation(World world){
        return new Location(world, random.nextInt(1000000) - 500000, 100, random.nextInt(1000000) - 500000);
    }
}
