package net.jadedmc.portaloverrides.listeners;

import net.jadedmc.portaloverrides.PortalOverrides;
import net.jadedmc.portaloverrides.events.PortalEnterEvent;
import net.jadedmc.portaloverrides.portals.Portal;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * This listens to the PortalEnterEvent event, which is called every time a player enters a portal.
 * We use this to process portal actions of overridden portals when the player enters a portal.
 */
public class PortalEnterListener implements Listener {
    private final PortalOverrides plugin;

    /**
     * To be able to access the configuration files, we need to pass an instance of the plugin to our listener.
     * @param plugin Instance of the plugin.
     */
    public PortalEnterListener(PortalOverrides plugin) {
        this.plugin = plugin;
    }

    /**
     * Runs when the event is called.
     * @param event PortalEnterEvent.
     */
    @EventHandler
    public void onPortalEnter(PortalEnterEvent event) {

        // Loops through each registered portal to find one that matches.
        for(Portal portal : plugin.getPortalManager().getPortals()) {
            // Makes sure the portal has the right type.
            if(portal.getType() != event.getType()) {
                continue;
            }

            // Attempts to use the portal.
            boolean successful = portal.usePortal(event.getPlayer());

            // If it finds a successfully used portal, exits the loop.
            if(successful) {
                break;
            }
        }
    }
}