package com.landclaim.listeners;

import com.landclaim.LandClaimPlugin;
import com.landclaim.managers.ClaimManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class ClaimListener implements Listener {

    private final LandClaimPlugin plugin;
    private final ClaimManager claimManager;

    public ClaimListener(LandClaimPlugin plugin) {
        this.plugin = plugin;
        this.claimManager = plugin.getClaimManager();
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (!plugin.getConfigManager().isProtectBreak()) {
            return;
        }

        Location loc = event.getBlock().getLocation();
        if (claimManager.isClaimed(loc)) {
            if (!claimManager.canBuild(loc, event.getPlayer().getUniqueId())) {
                event.setCancelled(true);
                event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getNoPermissionMessage()));
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (!plugin.getConfigManager().isProtectBuild()) {
            return;
        }

        Location loc = event.getBlock().getLocation();
        if (claimManager.isClaimed(loc)) {
            if (!claimManager.canBuild(loc, event.getPlayer().getUniqueId())) {
                event.setCancelled(true);
                event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getNoPermissionMessage()));
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (!plugin.getConfigManager().isProtectInteract()) {
            return;
        }
        if (event.getClickedBlock() == null) {
            return;
        }

        Location loc = event.getClickedBlock().getLocation();
        if (claimManager.isClaimed(loc)) {
            if (!claimManager.canBuild(loc, event.getPlayer().getUniqueId())) {
                event.setCancelled(true);
                event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getNoPermissionMessage()));
            }
        }
    }
}

