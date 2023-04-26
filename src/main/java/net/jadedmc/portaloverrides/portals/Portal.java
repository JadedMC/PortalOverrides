package net.jadedmc.portaloverrides.portals;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import net.jadedmc.portaloverrides.PortalOverrides;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an overridden portal, and it's settings.
 */
public class Portal {
    private final PortalType type;
    private final String region;
    private final String world;
    private final List<String> commands = new ArrayList<>();
    private final Location teleportLocation;
    private final String permission;

    /**
     * Creates a portal object.
     * @param plugin Instance of the plugin.
     * @param id Portal id as set in the config.
     */
    public Portal(PortalOverrides plugin, String id) {
        FileConfiguration settings = plugin.getSettingsManager().getConfig();
        String path = "Portals." + id + ".";

        type = PortalType.valueOf(settings.getString(path + "type"));

        // Get the WorldGuard region the portal should apply to.
        if(settings.isSet(path + "requirements.region")) {
            region = settings.getString(path + "requirements.region");
        }
        else {
            region = "";
        }

        // Get the world the portal should apply to.
        if(settings.isSet(path + "requirements.world")) {
            world = settings.getString(path + "requirements.world");
        }
        else {
            world = "";
        }

        // Get the permission required to use the portal.
        if(settings.isSet(path + "requirements.permission")) {
            permission = settings.getString(path + "requirements.permission");
        }
        else {
            permission = "";
        }

        // Load commands
        if(settings.isSet(path + "actions.commands")) {
            commands.addAll(settings.getStringList(path + "actions.commands"));
        }

        // Load teleport location.
        if(settings.isSet(path + "actions.teleport.world")) {
            World world = Bukkit.getWorld(settings.getString(path + "actions.teleport.world"));
            double x = settings.getDouble(path + "actions.teleport.x");
            double y = settings.getDouble(path + "actions.teleport.y");
            double z = settings.getDouble(path + "actions.teleport.z");
            float yaw = (float) settings.getDouble(path + "actions.teleport.yaw");
            float pitch = (float) settings.getDouble(path + "actions.teleport.pitch");

            teleportLocation = new Location(world, x, y, z, yaw, pitch);
        }
        else {
            teleportLocation = null;
        }
    }

    /**
     * Get the WorldGuard region id that the portal should apply to.
     * @return RegionID.
     */
    public String getRegion() {
        return region;
    }

    /**
     * Get the type of portal being used.
     * @return Portal Type.
     */
    public PortalType getType() {
        return type;
    }

    /**
     * Get the world the portal should work in.
     * @return World the portal should work in. Returns null if all worlds should work.
     */
    public World getWorld() {

        // Makes sure the world isn't empty.
        if(this.world.isEmpty()) {
            return null;
        }

        return Bukkit.getWorld(world);
    }

    /**
     * Makes a player use the portal.
     * @param player Player using the portal.
     */
    public boolean usePortal(Player player) {

        // Checks permission requirements
        if(!permission.isEmpty() && !player.hasPermission(permission)) {
            return false;
        }

        // Checks world requirements.
        if(getWorld() != null) {
            if(player.getLocation().getWorld() != getWorld()) {
                return false;
            }
        }

        // Checks region requirements.
        if(!region.isEmpty()) {
            LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(player);
            RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
            ApplicableRegionSet regions = container.get(localPlayer.getWorld()).getApplicableRegions(localPlayer.getLocation().toVector().toBlockPoint());

            boolean flag = false;
            // Loops through each region the player is in to check if it matches.
            for(ProtectedRegion wgRegion : regions) {
                if(wgRegion.getId().equalsIgnoreCase(this.region)) {
                    flag = true;
                    break;
                }
            }

            // If there is none, cancels the portal.
            if(!flag) {
                return false;
            }
        }

        // Processes commands.
        for(String command : commands) {
            Bukkit.dispatchCommand(player, command);
        }

        // Teleport player.
        if(teleportLocation != null) {
            player.teleport(teleportLocation);
        }

        return true;
    }
}