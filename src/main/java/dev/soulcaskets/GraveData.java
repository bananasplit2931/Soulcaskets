package dev.soulcaskets;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

public class GraveData {

    private final UUID owner;
    private final Location location;
    private final List<ItemStack> items;
    private final int exp;
    private final long createdAt;

    public GraveData(UUID owner, Location location, List<ItemStack> items, int exp) {
        this.owner = owner;
        this.location = location;
        this.items = items;
        this.exp = exp;
        this.createdAt = System.currentTimeMillis();
    }

    public UUID getOwner() {
        return owner;
    }

    public Location getLocation() {
        return location;
    }

    public List<ItemStack> getItems() {
        return items;
    }

    public int getExp() {
        return exp;
    }

    public long getCreatedAt() {
        return createdAt;
    }
}
