package net.jadedmc.portaloverrides.portals;

import net.jadedmc.portaloverrides.PortalOverrides;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Manages all overridden portals.
 */
public class PortalManager {
    private final Collection<Portal> portals = new ArrayList<>();


    /**
     * Creates the portal manager.
     * @param plugin Instance of the plugin.
     */
    public PortalManager(PortalOverrides plugin) {
        ConfigurationSection section = plugin.getSettingsManager().getConfig().getConfigurationSection("Portals");

        // Makes sure there is a Portals section in the config.
        if(section == null) {
            return;
        }

        // Loops through each portal and registers it.
        for(String id : section.getKeys(false)) {
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
