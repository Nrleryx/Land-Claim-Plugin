package com.landclaim.commands;

import com.landclaim.LandClaimPlugin;
import com.landclaim.managers.ClaimManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UnclaimCommand implements CommandExecutor {

    private final LandClaimPlugin plugin;
    private final ClaimManager claimManager;

    public UnclaimCommand(LandClaimPlugin plugin) {
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
        Location loc = player.getLocation();

        if (claimManager.removeClaim(loc, player.getUniqueId())) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                plugin.getConfigManager().getUnclaimMessage()));
        } else {
            if (!claimManager.isClaimed(loc)) {
                player.sendMessage(ChatColor.RED + "This area is not claimed!");
            } else {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfigManager().getNoPermissionMessage()));
            }
        }

        return true;
    }
}

