package xyz.acrylicstyle.uls.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.acrylicstyle.uls.api.Language;

/**
 * Fired when player changed their language.
 */
public class LanguageChangeEvent extends PlayerEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    @Nullable private final Language language;
    private boolean cancelled = false;
    @NotNull private final Cause cause;

    public LanguageChangeEvent(@NotNull Player who, @Nullable Language language, @NotNull Cause cause) {
        super(who);
        this.language = language;
        this.cause = cause;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() { return handlers; }

    @NotNull
    public static HandlerList getHandlerList() { return handlers; }

    @Nullable
    public Language getLanguage() { return language; }

    @NotNull
    public Cause getCause() { return cause; }

    public boolean isCancelled() { return cancelled; }

    public void setCancelled(boolean cancel) { this.cancelled = cancel; }

    public enum Cause {
        GUI,
        PLUGIN,
    }
}
