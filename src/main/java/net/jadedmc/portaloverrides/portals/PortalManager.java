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
package net.jadedmc.portaloverrides.portals;

import net.jadedmc.portaloverrides.PortalOverridesPlugin;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Manages all overridden portals.
 */
public class PortalManager {
    private final PortalOverridesPlugin plugin;
    private final Collection<Portal> portals = new ArrayList<>();


    /**
     * Creates the portal manager.
     * @param plugin Instance of the plugin.
     */
    public PortalManager(final PortalOverridesPlugin plugin) {
        this.plugin = plugin;
        reloadPortals();
    }

    public void reloadPortals() {
        // Clears previous portals
        portals.clear();

        final ConfigurationSection section = plugin.getConfigManager().getConfig().getConfigurationSection("Portals");

        // Makes sure there is a Portals section in the config.
        if(section == null) {
            return;
        }

        // Loops through each portal and registers it.
        for(final String id : section.getKeys(false)) {
            portals.add(new Portal(plugin, id));
        }
    }

    /**
     * Gets all currently registered portals.
     * @return All online portals.
     */
    public Collection<Portal> getPortals() {
        return portals;
    }
}
