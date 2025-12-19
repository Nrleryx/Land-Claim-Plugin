package com.landclaim.managers;

import com.landclaim.LandClaimPlugin;
import com.landclaim.models.Claim;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ClaimManager {

    private final LandClaimPlugin plugin;
    private final Map<String, Claim> claims;

    public ClaimManager(LandClaimPlugin plugin) {
        this.plugin = plugin;
        this.claims = new HashMap<>();
    }

    public void loadClaims() {
        claims.clear();
        File file = new File(plugin.getDataFolder(), "claims.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().severe("Could not create claims.yml!");
            }
            return;
        }

        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        if (!config.contains("claims")) {
            return;
        }
        
        org.bukkit.configuration.ConfigurationSection section = config.getConfigurationSection("claims");
        if (section == null) {
            return;
        }
        
        for (String key : section.getKeys(false)) {
            String path = "claims." + key;
            UUID owner = UUID.fromString(config.getString(path + ".owner"));
            String world = config.getString(path + ".world");
            int minX = config.getInt(path + ".minX");
            int minZ = config.getInt(path + ".minZ");
            int maxX = config.getInt(path + ".maxX");
            int maxZ = config.getInt(path + ".maxZ");

            Claim claim = new Claim(owner, world, minX, minZ, maxX, maxZ);
            claims.put(key, claim);
        }
    }

    public void saveClaims() {
        File file = new File(plugin.getDataFolder(), "claims.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        
        config.set("claims", null);
        int i = 0;
        for (Claim claim : claims.values()) {
            String path = "claims." + i;
            config.set(path + ".owner", claim.getOwner().toString());
            config.set(path + ".world", claim.getWorld());
            config.set(path + ".minX", claim.getMinX());
            config.set(path + ".minZ", claim.getMinZ());
            config.set(path + ".maxX", claim.getMaxX());
            config.set(path + ".maxZ", claim.getMaxZ());
            i++;
        }

        try {
            config.save(file);
        } catch (IOException e) {
            plugin.getLogger().severe("Could not save claims.yml!");
        }
    }

    public boolean canClaim(Location loc1, Location loc2, UUID player) {
        int minX = Math.min(loc1.getBlockX(), loc2.getBlockX());
        int maxX = Math.max(loc1.getBlockX(), loc2.getBlockX());
        int minZ = Math.min(loc1.getBlockZ(), loc2.getBlockZ());
        int maxZ = Math.max(loc1.getBlockZ(), loc2.getBlockZ());
        String world = loc1.getWorld().getName();

        for (Claim claim : claims.values()) {
            if (!claim.getWorld().equals(world)) continue;
            if (claim.overlaps(minX, minZ, maxX, maxZ)) {
                return false;
            }
        }

        int max = plugin.getConfigManager().getMaxClaims();
        int count = getPlayerClaimCount(player);
        return count < max;
    }

    public boolean createClaim(Location loc1, Location loc2, UUID player) {
        if (!canClaim(loc1, loc2, player)) {
            return false;
        }

        int minX = Math.min(loc1.getBlockX(), loc2.getBlockX());
        int maxX = Math.max(loc1.getBlockX(), loc2.getBlockX());
        int minZ = Math.min(loc1.getBlockZ(), loc2.getBlockZ());
        int maxZ = Math.max(loc1.getBlockZ(), loc2.getBlockZ());
        String world = loc1.getWorld().getName();

        String id = UUID.randomUUID().toString();
        Claim claim = new Claim(player, world, minX, minZ, maxX, maxZ);
        claims.put(id, claim);
        saveClaims();
        return true;
    }

    public boolean removeClaim(Location location, UUID player) {
        Claim claim = getClaimAt(location);
        if (claim == null || !claim.getOwner().equals(player)) {
            return false;
        }

        claims.entrySet().removeIf(entry -> entry.getValue().equals(claim));
        saveClaims();
        return true;
    }

    public Claim getClaimAt(Location location) {
        int x = location.getBlockX();
        int z = location.getBlockZ();
        String world = location.getWorld().getName();

        for (Claim claim : claims.values()) {
            if (claim.getWorld().equals(world) && claim.contains(x, z)) {
                return claim;
            }
        }
        return null;
    }

    public boolean isClaimed(Location location) {
        return getClaimAt(location) != null;
    }

    public boolean canBuild(Location location, UUID player) {
        Claim claim = getClaimAt(location);
        if (claim == null) return true;
        return claim.getOwner().equals(player);
    }

    public int getPlayerClaimCount(UUID player) {
        int count = 0;
        for (Claim claim : claims.values()) {
            if (claim.getOwner().equals(player)) {
                count++;
            }
        }
        return count;
    }

    public List<Claim> getPlayerClaims(UUID player) {
        List<Claim> list = new ArrayList<>();
        for (Claim claim : claims.values()) {
            if (claim.getOwner().equals(player)) {
                list.add(claim);
            }
        }
        return list;
    }
}

