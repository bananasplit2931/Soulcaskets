package dev.soulcaskets;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class GraveManager {

    private final SoulCaskets plugin;
    private final Map<Location, GraveData> graves = new HashMap<>();

    public GraveManager(SoulCaskets plugin) {
        this.plugin = plugin;
        if (plugin.getConfig().getBoolean("timer-enabled", false)) {
            startTimerTask();
        }
    }

    public void createGrave(Player player, List<ItemStack> items, int exp) {
        Location loc = player.getLocation().getBlock().getLocation();

        if (loc.getBlockY() <= loc.getWorld().getMinHeight()) {
            loc = loc.getWorld().getHighestBlockAt(loc).getLocation();
        }

        Material graveBlock = Material.matchMaterial(
                plugin.getConfig().getString("grave-block", "SOUL_SAND")
        );
        if (graveBlock == null) graveBlock = Material.SOUL_SAND;

        loc.getBlock().setType(graveBlock);

        GraveData data = new GraveData(player.getUniqueId(), loc, items, exp);
        graves.put(loc, data);
    }

    public GraveData getGrave(Location loc) {
        return graves.get(loc);
    }

    public void removeGrave(Location loc, boolean dropItems) {
        GraveData data = graves.remove(loc);
        if (data == null) return;

        loc.getBlock().setType(Material.AIR);

        if (dropItems) {
            for (ItemStack item : data.getItems()) {
                if (item != null) {
                    loc.getWorld().dropItemNaturally(loc, item);
                }
            }
        }
    }

    public void collectGrave(Player player, Location loc) {
        GraveData data = graves.get(loc);
        if (data == null) return;

        loc.getBlock().setType(Material.AIR);
        graves.remove(loc);

        for (ItemStack item : data.getItems()) {
            if (item != null) {
                Map<Integer, ItemStack> leftover = player.getInventory().addItem(item);
                leftover.values().forEach(i -> loc.getWorld().dropItemNaturally(loc, i));
            }
        }

        player.giveExp(data.getExp());
    }

    public void dropAllGraves() {
        for (Location loc : new HashSet<>(graves.keySet())) {
            removeGrave(loc, true);
        }
    }

    private void startTimerTask() {
        long durationMillis = plugin.getConfig().getLong("timer-duration", 3600) * 1000L;

        new BukkitRunnable() {
            @Override
            public void run() {
                long now = System.currentTimeMillis();
                for (Location loc : new HashSet<>(graves.keySet())) {
                    GraveData data = graves.get(loc);
                    if (data == null) continue;
                    if (now - data.getCreatedAt() >= durationMillis) {
                        removeGrave(loc, true);
                    }
                }
            }
        }.runTaskTimer(plugin, 20L * 30, 20L * 30);
    }
}
