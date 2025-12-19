package com.landclaim;

import com.landclaim.commands.ClaimCommand;
import com.landclaim.commands.UnclaimCommand;
import com.landclaim.config.ConfigManager;
import com.landclaim.listeners.ClaimListener;
import com.landclaim.managers.ClaimManager;
import org.bukkit.plugin.java.JavaPlugin;

public class LandClaimPlugin extends JavaPlugin {

    private static LandClaimPlugin instance;
    private ConfigManager configManager;
    private ClaimManager claimManager;

    @Override
    public void onEnable() {
        instance = this;
        
        configManager = new ConfigManager(this);
        claimManager = new ClaimManager(this);
        
        configManager.loadConfig();
        claimManager.loadClaims();
        
        getCommand("claim").setExecutor(new ClaimCommand(this));
        getCommand("unclaim").setExecutor(new UnclaimCommand(this));
        getCommand("landclaim").setExecutor(new ClaimCommand(this));
        
        getServer().getPluginManager().registerEvents(new ClaimListener(this), this);
        
        getLogger().info("LandClaim plugin has been enabled!");
        getLogger().info("Players can now claim and protect their land!");
    }

    @Override
    public void onDisable() {
        claimManager.saveClaims();
        getLogger().info("LandClaim plugin has been disabled!");
    }

    public static LandClaimPlugin getInstance() {
        return instance;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public ClaimManager getClaimManager() {
        return claimManager;
    }
}

