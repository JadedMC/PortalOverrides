/*
 * This file is part of PortalOverrides, licensed under the MIT License.
 *
 *  Copyright (c) JadedMC
 *  Copyright (c) contributors
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */
package net.jadedmc.portaloverrides.listeners;

import net.jadedmc.portaloverrides.PortalOverridesPlugin;
import net.jadedmc.portaloverrides.portals.Portal;
import net.jadedmc.portaloverrides.portals.PortalType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class PlayerPortalListener implements Listener {
    private final PortalOverridesPlugin plugin;

    /**
     * To be able to access the configuration files, we need to pass an instance of the plugin to our listener.
     * @param plugin Instance of the plugin.
     */
    public PlayerPortalListener(final PortalOverridesPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPortalTeleport(PlayerPortalEvent event) {

        if(event.getCause() != PlayerTeleportEvent.TeleportCause.END_PORTAL) {
            return;
        }

        // Loops through each registered portal to find one that matches.
        for(final Portal portal : plugin.getPortalManager().getPortals()) {
            if(portal.getType() != PortalType.END_PORTAL) {
                continue;
            }

            // Attempts to use the portal.
            final boolean successful = portal.usePortal(event.getPlayer());

            // If it finds a successfully used portal, exits the loop.
            if(successful) {
                event.setCancelled(true);
                break;
            }
        }

        // Disable vanilla portals if that's configured.
        if(plugin.getConfigManager().getConfig().getBoolean("DisableVanillaPortals")) {
            event.setCancelled(true);
        }
    }
}
