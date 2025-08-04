package charcruncher.monsterspice.networking;

import charcruncher.monsterspice.networking.payloads.SpecialMovePayload;
import charcruncher.monsterspice.networking.payloads.WarmthMeterSyncPayload;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class ModPackets {

    public static void registerModPayloadTypes() {
        PayloadTypeRegistry.playC2S().register(SpecialMovePayload.ID, SpecialMovePayload.CODEC);
        PayloadTypeRegistry.playS2C().register(WarmthMeterSyncPayload.ID, WarmthMeterSyncPayload.CODEC);
    }

    public static void registerC2SPacketReceivers() {
        // Register C2S packets here
        ServerPlayNetworking.registerGlobalReceiver(SpecialMovePayload.ID, SpecialMovePayload::receive);
    }
}
