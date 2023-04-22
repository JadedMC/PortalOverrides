package net.jadedmc.portaloverrides.events;

import net.jadedmc.portaloverrides.portals.PortalType;
import org.antlr.v4.runtime.misc.NotNull;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Called every time a player enters a portal.
 */
public class PortalEnterEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final Player player;
    private final PortalType type;

    /**
     * Creates the event.
     * @param player Player who entered the portal.
     * @param type Type of portal entered.
     */
    public PortalEnterEvent(Player player, PortalType type) {
        this.player = player;
        this.type = type;
    }

    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    /**
     * Gets the player who entered the portal.
     * @return Player that entered the portal.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Gets the type of portal entered.
     * @return Portal type.
     */
    public PortalType getType() {
        return type;
    }
}