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
package net.jadedmc.portaloverrides;

import net.jadedmc.portaloverrides.commands.PortalOverridesCMD;
import net.jadedmc.portaloverrides.listeners.PlayerPortalListener;
import net.jadedmc.portaloverrides.listeners.PortalEnterListener;
import net.jadedmc.portaloverrides.listeners.ReloadListener;
import net.jadedmc.portaloverrides.portals.PortalManager;
import net.jadedmc.portaloverrides.runnables.PortalEnterChecker;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * This class is the main class of the plugin.
 * It links all parts together and registers them with the server.
 */
public final class PortalOverridesPlugin extends JavaPlugin {
    private HookManager hookManager;
    private PortalManager portalManager;
    private ConfigManager configManager;

    /**
     * Runs when the server is started.
     */
    @Override
    public void onEnable() {
        configManager = new ConfigManager(this);
        hookManager = new HookManager(this);
        portalManager = new PortalManager(this);

        // Registers commands.
        getCommand("portaloverrides").setExecutor(new PortalOverridesCMD(this));

        // Registers events.
        Bukkit.getPluginManager().registerEvents(new PortalEnterListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerPortalListener(this), this);

        // Supports BetterReload if installed.
        if(this.hookManager.useBetterReload()) getServer().getPluginManager().registerEvents(new ReloadListener(this), this);

        // Registers runnables.
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new PortalEnterChecker(), 5, 5);

        // Enables bStats statistics tracking.
        new Metrics(this, 18282);
    }

    /**
     * Gets the current portal manager instance.
     * @return Portal manager.
     */
    public PortalManager getPortalManager() {
        return portalManager;
    }

    /**
     * Gets the current config manager instance.
     * @return Config Manager.
     */
    public ConfigManager getConfigManager() {
        return configManager;
    }
}