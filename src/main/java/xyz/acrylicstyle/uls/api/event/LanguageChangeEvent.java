package xyz.acrylicstyle.uls.api.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.acrylicstyle.uls.api.Language;

import java.util.UUID;

/**
 * Fired when player changed their language. It's no use on bungee, so use only on bukkit.
 */
public class LanguageChangeEvent extends PlayerEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    @Nullable private final Language language;
    private boolean cancelled = false;
    @NotNull private final Cause cause;
    private final UUID uniqueId;

    public LanguageChangeEvent(@Nullable Player who, @NotNull UUID uniqueId, @Nullable Language language, @NotNull Cause cause) {
        super(who);
        this.uniqueId = uniqueId;
        this.language = language;
        this.cause = cause;
    }

    @NotNull
    public UUID getUniqueId() { return uniqueId; }

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
