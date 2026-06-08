package dev.soulcaskets;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class GraveListener implements Listener {

    private final SoulCaskets plugin;
    private final GraveManager graveManager;
    private final MiniMessage mm = MiniMessage.miniMessage();

    public GraveListener(SoulCaskets plugin, GraveManager graveManager) {
        this.plugin = plugin;
        this.graveManager = graveManager;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        List<ItemStack> items = new ArrayList<>(event.getDrops());
        int exp = event.getDroppedExp();

        event.getDrops().clear();
        event.setDroppedExp(0);

        graveManager.createGrave(player, items, exp);

        Location loc = player.getLocation();
        String prefix = plugin.getConfig().getString("message-prefix", "<dark_gray>[<light_purple>SoulCaskets</light_purple>]</dark_gray> ");
        String coords = "<white>" + loc.getBlockX() + ", " + loc.getBlockY() + ", " + loc.getBlockZ() + "</white>";
        String world = "<gray>" + loc.getWorld().getName() + "</gray>";

        player.sendMessage(mm.deserialize(prefix + "Your grave was placed at " + coords + " in " + world + "."));
    }

    @EventHandler
    public void onGraveInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (event.getClickedBlock() == null) return;

        Location loc = event.getClickedBlock().getLocation();
        GraveData data = graveManager.getGrave(loc);
        if (data == null) return;

        event.setCancelled(true);

        Player player = event.getPlayer();
        String prefix = plugin.getConfig().getString("message-prefix", "<dark_gray>[<light_purple>SoulCaskets</light_purple>]</dark_gray> ");

        if (!data.getOwner().equals(player.getUniqueId())) {
            player.sendMessage(mm.deserialize(prefix + "<red>This grave doesn't belong to you."));
            return;
        }

        graveManager.collectGrave(player, loc);
        player.sendMessage(mm.deserialize(prefix + "<green>You retrieved your items and experience."));
    }
}
