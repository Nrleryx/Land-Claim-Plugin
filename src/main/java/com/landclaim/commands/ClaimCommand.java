package com.landclaim.commands;

import com.landclaim.LandClaimPlugin;
import com.landclaim.managers.ClaimManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClaimCommand implements CommandExecutor {

    private final LandClaimPlugin plugin;
    private final ClaimManager claimManager;

    public ClaimCommand(LandClaimPlugin plugin) {
        this.plugin = plugin;
        this.claimManager = plugin.getClaimManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be used by players!");
            return true;
        }

        Player player = (Player) sender;

        if (label.equalsIgnoreCase("claim")) {
            if (args.length > 0 && args[0].equalsIgnoreCase("info")) {
                Location loc = player.getLocation();
                if (claimManager.isClaimed(loc)) {
                    player.sendMessage(ChatColor.GREEN + "This area is claimed!");
                } else {
                    player.sendMessage(ChatColor.YELLOW + "This area is not claimed.");
                }
                return true;
            }

            Location loc = player.getLocation();
            int size = plugin.getConfigManager().getMinClaimSize();

            Location loc1 = loc.clone().add(-size/2, 0, -size/2);
            Location loc2 = loc.clone().add(size/2, 0, size/2);

            if (claimManager.canClaim(loc1, loc2, player.getUniqueId())) {
                if (claimManager.createClaim(loc1, loc2, player.getUniqueId())) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', 
                        plugin.getConfigManager().getClaimMessage()));
                } else {
                    player.sendMessage(ChatColor.RED + "Failed to create claim!");
                }
            } else {
                if (claimManager.isClaimed(loc)) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        plugin.getConfigManager().getAlreadyClaimedMessage()));
                } else {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        plugin.getConfigManager().getMaxClaimsMessage()));
                }
            }
            return true;
        }

        if (label.equalsIgnoreCase("landclaim")) {
            if (args.length == 0) {
                player.sendMessage(ChatColor.GOLD + "=== LandClaim Plugin ===");
                player.sendMessage(ChatColor.YELLOW + "/claim - Claim the area you're standing in");
                player.sendMessage(ChatColor.YELLOW + "/unclaim - Remove your claim");
                player.sendMessage(ChatColor.YELLOW + "/landclaim info - View claim information");
                player.sendMessage(ChatColor.YELLOW + "/landclaim list - List your claims");
                return true;
            }

            if (args[0].equalsIgnoreCase("info")) {
                Location loc = player.getLocation();
                if (claimManager.isClaimed(loc)) {
                    player.sendMessage(ChatColor.GREEN + "This area is claimed!");
                } else {
                    player.sendMessage(ChatColor.YELLOW + "This area is not claimed.");
                }
                return true;
            }

            if (args[0].equalsIgnoreCase("list")) {
                int count = claimManager.getPlayerClaimCount(player.getUniqueId());
                player.sendMessage(ChatColor.GOLD + "Your Claims: " + ChatColor.YELLOW + count + "/" + 
                    plugin.getConfigManager().getMaxClaims());
                return true;
            }

            if (args[0].equalsIgnoreCase("claim")) {
                Location loc = player.getLocation();
                int size = plugin.getConfigManager().getMinClaimSize();

                Location loc1 = loc.clone().add(-size/2, 0, -size/2);
                Location loc2 = loc.clone().add(size/2, 0, size/2);

                if (claimManager.canClaim(loc1, loc2, player.getUniqueId())) {
                    if (claimManager.createClaim(loc1, loc2, player.getUniqueId())) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', 
                            plugin.getConfigManager().getClaimMessage()));
                    } else {
                        player.sendMessage(ChatColor.RED + "Failed to create claim!");
                    }
                } else {
                    if (claimManager.isClaimed(loc)) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            plugin.getConfigManager().getAlreadyClaimedMessage()));
                    } else {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            plugin.getConfigManager().getMaxClaimsMessage()));
                    }
                }
                return true;
            }
        }

        return false;
    }
}

