package mv.roughleaderboards.mixin;

import mv.roughleaderboards.RoughLeaderboards;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;

public class StateSaverAndLoader extends PersistentState {
    public Integer totalDirtBlocksBroken = 0;
    @Override
    public NbtCompound writeNbt(NbtCompound nbt){
        nbt.putInt("totalDirtBlocksBroken", totalDirtBlocksBroken);
        return nbt;
    }

    public static StateSaverAndLoader createFromNbt(NbtCompound tag){
        StateSaverAndLoader state = new StateSaverAndLoader();
        state.totalDirtBlocksBroken = tag.getInt("totalDirtBlocksBroken");
        return state;
    }
    public static T<StateSaverAndLoader> type = new T<>(
            StateSaverAndLoader::new,
            StateSaverAndLoader::createFromNbt,
            null
    );

    public static StateSaverAndLoader getServerState(MinecraftServer server){
        PersistentStateManager persistentStateManager = server.getWorld(World.OVERWORLD).getPersistentStateManager();
        StateSaverAndLoader state = persistentStateManager.getOrCreate(type, type, RoughLeaderboards.MOD_ID);
        state.markDirty();
        return state;
    }

}
