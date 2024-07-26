package net.miavalley.roughleaderboards;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.block.Blocks;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class RoughLeaderboards implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("roughleaderboards");
	public static final String MOD_ID = "roughleaderboards";

	public static final Identifier DIRT_BROKEN = new Identifier(MOD_ID, "dirt_broken");

//	private Integer totalDirtBlocksBroken = 0;
	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, entity) -> {
			if(state.getBlock() == Blocks.GRASS_BLOCK || state.getBlock() == Blocks.DIRT){
				StateSaverAndLoader serverState = StateSaverAndLoader.getServerState(world.getServer());
				serverState.totalDirtBlocksBroken +=1;

				MinecraftServer server = world.getServer();
				PacketByteBuf data = PacketByteBufs.create();
				data.writeInt(serverState.totalDirtBlocksBroken);

                assert server != null;
                ServerPlayerEntity playerEntity = server.getPlayerManager().getPlayer(player.getUuid());
				server.execute(()->{
                    assert playerEntity != null;
                    ServerPlayNetworking.send(playerEntity, DIRT_BROKEN, data);
				});
			}
		});
	}
}