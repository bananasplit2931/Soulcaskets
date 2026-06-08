package dev.soulcaskets;

import org.bukkit.plugin.java.JavaPlugin;

public class SoulCaskets extends JavaPlugin {

    private GraveManager graveManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        graveManager = new GraveManager(this);
        getServer().getPluginManager().registerEvents(new GraveListener(this, graveManager), this);
    }

    @Override
    public void onDisable() {
        graveManager.dropAllGraves();
    }

    public GraveManager getGraveManager() {
        return graveManager;
    }
}
