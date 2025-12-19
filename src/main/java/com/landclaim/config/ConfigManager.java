package com.landclaim.config;

import com.landclaim.LandClaimPlugin;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {

    private final LandClaimPlugin plugin;
    private FileConfiguration config;

    public ConfigManager(LandClaimPlugin plugin) {
        this.plugin = plugin;
    }

    public void loadConfig() {
        plugin.saveDefaultConfig();
        config = plugin.getConfig();
    }

    public void reloadConfig() {
        plugin.reloadConfig();
        config = plugin.getConfig();
    }

    public int getMaxClaims() {
        return config.getInt("max-claims", 3);
    }

    public int getMinClaimSize() {
        return config.getInt("min-claim-size", 10);
    }

    public int getMaxClaimSize() {
        return config.getInt("max-claim-size", 1000);
    }

    public boolean isProtectBuild() {
        return config.getBoolean("protect-build", true);
    }

    public boolean isProtectBreak() {
        return config.getBoolean("protect-break", true);
    }

    public boolean isProtectInteract() {
        return config.getBoolean("protect-interact", true);
    }

    public String getClaimMessage() {
        return config.getString("messages.claim-success", "&aLand successfully claimed!");
    }

    public String getUnclaimMessage() {
        return config.getString("messages.unclaim-success", "&aLand unclaimed successfully!");
    }

    public String getNoPermissionMessage() {
        return config.getString("messages.no-permission", "&cYou don't have permission to interact in this area!");
    }

    public String getAlreadyClaimedMessage() {
        return config.getString("messages.already-claimed", "&cThis area is already claimed!");
    }

    public String getMaxClaimsMessage() {
        return config.getString("messages.max-claims", "&cYou have reached the maximum number of claims!");
    }
}

