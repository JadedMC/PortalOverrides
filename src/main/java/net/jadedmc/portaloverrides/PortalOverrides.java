package net.jadedmc.portaloverrides;

import net.jadedmc.portaloverrides.listeners.PortalEnterListener;
import net.jadedmc.portaloverrides.portals.PortalManager;
import net.jadedmc.portaloverrides.runnables.PortalEnterChecker;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * This class is the main class of the plugin.
 * It links all parts together and registers them with the server.
 */
public final class PortalOverrides extends JavaPlugin {
    private PortalManager portalManager;
    private SettingsManager settingsManager;

    /**
     * Runs when the server is started.
     */
    @Override
    public void onEnable() {

        settingsManager = new SettingsManager(this);
        portalManager = new PortalManager(this);

        // Registers events.
        Bukkit.getPluginManager().registerEvents(new PortalEnterListener(this), this);

        // Registers runnables.
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new PortalEnterChecker(), 5, 5);
    }

    /**
     * Gets the current portal manager instance.
     * @return Portal manager.
     */
    public PortalManager getPortalManager() {
        return portalManager;
    }

    /**
     * Gets the current settings manager instance.
     * @return Settings Manager.
     */
    public SettingsManager getSettingsManager() {
        return settingsManager;
    }
}