package net.jadedmc.portaloverrides.runnables;

import net.jadedmc.portaloverrides.events.PortalEnterEvent;
import net.jadedmc.portaloverrides.portals.PortalType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Checks for all players currently standing in portals.
 * When it finds one, calls the PortalEnterEvent.
 */
public class PortalEnterChecker implements Runnable {
    @Override
    public void run() {
        // Stores a copy of all online players.
        Collection<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());

        // Check each player to see if they're in a portal.
        for(Player player : players) {
            // Makes sure the player is still online.
            if(player == null) {
                continue;
            }

            // Get the block the player is standing in.
            Block block = player.getWorld().getBlockAt(player.getLocation().getBlockX(), player.getLocation().getBlockY() + 1, player.getLocation().getBlockZ());

            // If it's a nether portal, calls the PortalEnterEvent for a nether portal.
            if(block.getType() == Material.NETHER_PORTAL) {
                Bukkit.getPluginManager().callEvent(new PortalEnterEvent(player, PortalType.NETHER_PORTAL));
                return;
            }

            // If it's an end portal, calls the PortalEnterEvent for an end portal.
            if(block.getType() == Material.END_PORTAL) {
                Bukkit.getPluginManager().callEvent(new PortalEnterEvent(player, PortalType.END_PORTAL));
                return;
            }
        }
    }
}