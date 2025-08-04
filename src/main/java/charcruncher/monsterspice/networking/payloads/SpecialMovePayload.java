package charcruncher.monsterspice.networking.payloads;

import charcruncher.monsterspice.MonsterSpice;
import charcruncher.monsterspice.item.EmberbloomShieldItem;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public record SpecialMovePayload(BlockPos pos) implements CustomPayload {
    public static final CustomPayload.Id<SpecialMovePayload> ID = new CustomPayload.Id<>(Identifier.of(MonsterSpice.MOD_ID, "special_move"));
    public static final PacketCodec<RegistryByteBuf, SpecialMovePayload> CODEC = PacketCodec.tuple(BlockPos.PACKET_CODEC, SpecialMovePayload::pos, SpecialMovePayload::new);

    @Override
    public CustomPayload.Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static void receive(SpecialMovePayload payload, ServerPlayNetworking.Context context) {
        context.server().execute(() -> {
            MonsterSpice.LOGGER.info("Successfully received SpecialMovePayload from client.");
            // Execute doSporebustion from EmberbloomShieldItem if it is held in hand and player is blocking
            if (context.player().getActiveItem().getItem() instanceof EmberbloomShieldItem shieldItem && context.player().isBlocking()) {
                shieldItem.doSporebustion(context.player().getActiveItem(), context.player().getWorld(), context.player());
            }
        });
    }
}
