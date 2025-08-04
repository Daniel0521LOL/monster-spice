package charcruncher.monsterspice.logic;

import charcruncher.monsterspice.MonsterSpice;
import charcruncher.monsterspice.networking.payloads.WarmthMeterSyncPayload;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;

public class WarmthManager {
    public static final int MAX_WARMTH = 200;

    public static int getWarmth(PlayerEntity player) {
        return ((IEntityDataReadWrite) player).monsterSpice$getWarmth();
    }

    public static void addWarmth(PlayerEntity player, int amount) {
        IEntityDataReadWrite data = (IEntityDataReadWrite) player;
        int warmth = data.monsterSpice$getWarmth();
        warmth = Math.min(warmth + amount, MAX_WARMTH);
        data.monsterSpice$setWarmth(warmth);
        syncWarmth(player);
        // MonsterSpice.LOGGER.info("Added {} warmth to player. New warmth level: {}", amount, warmth);
    }

    public static void removeWarmth(PlayerEntity player, int amount) {
        IEntityDataReadWrite data = (IEntityDataReadWrite) player;
        int warmth = data.monsterSpice$getWarmth();
        warmth = Math.max(warmth - amount, 0);
        data.monsterSpice$setWarmth(warmth);
        syncWarmth(player);
        // MonsterSpice.LOGGER.info("Removed {} warmth from player. New warmth level: {}", amount, warmth);
    }

    public static void syncWarmth(PlayerEntity player) {
        int warmth = getWarmth(player);
        ServerPlayNetworking.send((ServerPlayerEntity) player, new WarmthMeterSyncPayload(warmth));
    }

}
