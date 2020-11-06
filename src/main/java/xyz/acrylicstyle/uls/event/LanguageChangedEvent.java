package xyz.acrylicstyle.uls.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

/**
 * Fired when player changed their language.
 */
public class LanguageChangedEvent extends PlayerEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    @NotNull private final Locale locale;
    private boolean cancelled = false;
    @NotNull private final Cause cause;

    public LanguageChangedEvent(@NotNull Player who, @NotNull Locale locale, @NotNull Cause cause) {
        super(who);
        this.locale = locale;
        this.cause = cause;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() { return handlers; }

    @NotNull
    public static HandlerList getHandlerList() { return handlers; }

    @NotNull
    public Locale getLocale() { return locale; }

    @NotNull
    public Cause getCause() { return cause; }

    public boolean isCancelled() { return cancelled; }

    public void setCancelled(boolean cancel) { this.cancelled = cancel; }

    public enum Cause {
        GUI,
        PLUGIN,
    }
}
