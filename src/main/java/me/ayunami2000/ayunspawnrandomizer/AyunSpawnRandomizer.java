package me.ayunami2000.ayunspawnrandomizer;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

import java.util.*;

public final class AyunSpawnRandomizer extends JavaPlugin implements Listener {
    private static final Random random = new Random();

    private static int minX,minZ,maxX,maxZ,y,loaded;
    private static World world;
    //private static Set<Chunk> safeLoadedChunks = new HashSet<>();
    //private static Set<Chunk> checkedChunks = new HashSet<>();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        minX = getConfig().getInt("min.x");
        minZ = getConfig().getInt("min.z");
        maxX = getConfig().getInt("max.x");
        maxZ = getConfig().getInt("max.z");
        y = getConfig().getInt("y");
        loaded = getConfig().getInt("loaded");
        world = getServer().getWorld(getConfig().getString("world"));
        getServer().getPluginManager().registerEvents(this, this);
        /*
        int radius = getConfig().getInt("radius");
        int limit = getConfig().getInt("limit");
        getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
            Chunk[] loadedChunks = world.getLoadedChunks();
            if (loadedChunks.length < loaded) {
                safeLoadedChunks.clear();
                safeLoadedChunks = new HashSet<>(Arrays.asList(loadedChunks));
            } else {
                Set<Chunk> newSafeLoadedChunks = new HashSet<>();
                for (int i = 0; i < Math.min(limit == 0 ? loadedChunks.length : limit, loadedChunks.length); i++) {
                    Chunk currChunk = loadedChunks[i];
                    if (nearChunksAreLoaded(currChunk, radius)) {
                        newSafeLoadedChunks.add(currChunk);
                    }
                }
                safeLoadedChunks.clear();
                safeLoadedChunks = newSafeLoadedChunks;
            }
        }, 0, getConfig().getInt("often"));
        */
    }

    @Override
    public void onDisable() {
        //getServer().getScheduler().cancelTasks(this);
    }

    @EventHandler
    public void onPlayerSpawn(PlayerSpawnLocationEvent event){
        event.setSpawnLocation(randomLoadedLocation(event.getSpawnLocation().getWorld()));
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event){
        event.setRespawnLocation(randomLoadedLocation(world));
    }

    private static Location randomLoadedLocation(World world){
        Chunk[] loadedChunks = world.getLoadedChunks();
        if (loadedChunks.length < loaded) {
            return randomLocation(world);
        } else {
            /*
            int index = random.nextInt(safeLoadedChunks.size());
            Iterator<Chunk> iter = safeLoadedChunks.iterator();
            for (int i = 0; i < index; i++) iter.next();
            Chunk chosenChunk = iter.next();
            */
            Chunk chosenChunk = loadedChunks[random.nextInt(loadedChunks.length)];
            return new Location(world, chosenChunk.getX() * 16, y, chosenChunk.getZ() * 16);
        }
    }

    /*
    private static boolean nearChunksAreLoaded(Chunk chunk, int radius){
        try {
            for (int ix = -1; ix <= 1; ix++) {
                for (int iy = -1; iy <= 1; iy++) {
                    /*
                    int fix = ix;
                    int fiy = iy;
                    Chunk theChunk = checkedChunks.stream().filter(ch -> ch.getX() == fix && ch.getZ() == fiy).findFirst().orElse(null);
                    if (theChunk == null) {
                        theChunk = chunk.getWorld().getChunkAt(chunk.getX() + ix, chunk.getZ() + iy);
                    }
                    if (!theChunk.isLoaded()) return false;
                    *//*
                    if (!chunk.getWorld().getChunkAt(chunk.getX() + ix, chunk.getZ() + iy).isLoaded()) return false;
                }
            }
            return true;
        } catch (Exception e) { // anticipated edge of world chunks
            return false;
        }
    }
    */

    private static Location randomLocation(World world){
        return new Location(world, random.nextInt(maxX - minX) + minX, y, random.nextInt(maxZ - minZ) + minZ);
    }
}
