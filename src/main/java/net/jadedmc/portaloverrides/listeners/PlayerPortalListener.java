package net.jadedmc.portaloverrides.listeners;

import net.jadedmc.portaloverrides.PortalOverrides;
import net.jadedmc.portaloverrides.portals.Portal;
import net.jadedmc.portaloverrides.portals.PortalType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class PlayerPortalListener implements Listener {
    private final PortalOverrides plugin;

    /**
     * To be able to access the configuration files, we need to pass an instance of the plugin to our listener.
     * @param plugin Instance of the plugin.
     */
    public PlayerPortalListener(PortalOverrides plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPortalTeleport(PlayerPortalEvent event) {

        if(event.getCause() != PlayerTeleportEvent.TeleportCause.END_PORTAL) {
            return;
        }

        // Loops through each registered portal to find one that matches.
        for(Portal portal : plugin.getPortalManager().getPortals()) {
            if(portal.getType() != PortalType.END_PORTAL) {
                continue;
            }

            // Attempts to use the portal.
            boolean successful = portal.usePortal(event.getPlayer());

            // If it finds a successfully used portal, exits the loop.
            if(successful) {
                event.setCancelled(true);
                break;
            }
        }
    }
}
