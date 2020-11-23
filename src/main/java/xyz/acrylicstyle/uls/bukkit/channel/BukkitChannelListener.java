package xyz.acrylicstyle.uls.bukkit.channel;

import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.acrylicstyle.uls.api.ChannelConstants;
import xyz.acrylicstyle.uls.api.UniversalLanguageSelectorAPIProvider;
import xyz.acrylicstyle.uls.bukkit.UniversalLanguageSelectorImpl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;

public class BukkitChannelListener implements PluginMessageListener {
    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        try {
            DataInputStream in = new DataInputStream(new ByteArrayInputStream(message));
            if (channel.equals(ChannelConstants.INVALIDATE_CACHE)) {
                UniversalLanguageSelectorAPIProvider.getAPI().invalidateCache(UUID.fromString(in.readUTF()));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void sendToBungeeCord(@NotNull org.bukkit.entity.Player p, @NotNull String tag, @Nullable String subchannel, @Nullable String message) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(stream);
        try {
            if (subchannel != null) out.writeUTF(subchannel);
            if (message != null) out.writeUTF(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        p.sendPluginMessage((UniversalLanguageSelectorImpl) UniversalLanguageSelectorAPIProvider.getAPI(), tag, stream.toByteArray());
    }
}
