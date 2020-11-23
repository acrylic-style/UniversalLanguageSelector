package xyz.acrylicstyle.uls.bungee.channel;

import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import org.jetbrains.annotations.Nullable;
import xyz.acrylicstyle.uls.api.ChannelConstants;
import xyz.acrylicstyle.uls.api.UniversalLanguageSelectorAPIProvider;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;

public class BungeeChannelListener implements Listener {
    @EventHandler
    public void onPluginMessage(PluginMessageEvent e) {
        try {
            DataInputStream in = new DataInputStream(new ByteArrayInputStream(e.getData()));
            if (e.getTag().equals(ChannelConstants.INVALIDATE_CACHE)) {
                UniversalLanguageSelectorAPIProvider.getAPI().invalidateCache(UUID.fromString(in.readUTF()));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void sendToBukkit(String tag, @Nullable String subchannel, @Nullable String message, ServerInfo server) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(stream);
        try {
            if (subchannel != null) out.writeUTF(subchannel);
            if (message != null) out.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
        server.sendData(tag, stream.toByteArray()); // channel = tag
    }
}
