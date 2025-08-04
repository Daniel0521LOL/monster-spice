package charcruncher.monsterspice.networking.payloads;

import charcruncher.monsterspice.MonsterSpice;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record WarmthMeterSyncPayload(int warmth) implements CustomPayload {
    public static final Id<WarmthMeterSyncPayload> ID = new Id<>(Identifier.of(MonsterSpice.MOD_ID, "warmth_meter_sync"));
    public static final PacketCodec<RegistryByteBuf, WarmthMeterSyncPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER,
            WarmthMeterSyncPayload::warmth,
            WarmthMeterSyncPayload::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
