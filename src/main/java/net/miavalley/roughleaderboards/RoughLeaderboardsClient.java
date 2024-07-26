package net.miavalley.roughleaderboards;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.text.Text; // For text in minecraft
public class RoughLeaderboardsClient {

    public void onInitializeClient(){
        ClientPlayNetworking.registerGlobalReceiver(RoughLeaderboards.DIRT_BROKEN,(client, handler, buf, responseSender)->{
            int totalDirtBlocksBroken = buf.readInt();
            client.execute(()->{
                assert client.player != null;
                client.player.sendMessage(Text.literal("Total dirt blocks broken: "+totalDirtBlocksBroken), false);
            });
        });
    }
}
