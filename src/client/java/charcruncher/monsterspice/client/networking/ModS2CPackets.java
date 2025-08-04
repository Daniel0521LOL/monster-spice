package charcruncher.monsterspice.client.networking;

import charcruncher.monsterspice.MonsterSpice;
import charcruncher.monsterspice.logic.IEntityDataReadWrite;
import charcruncher.monsterspice.networking.payloads.WarmthMeterSyncPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class ModS2CPackets {
    public static void registerS2CPacketReceivers() {
        // Register S2C packets here
        ClientPlayNetworking.registerGlobalReceiver(WarmthMeterSyncPayload.ID, (payload, context) ->         context.client().execute(() -> {
            int warmth = Math.max(payload.warmth(), 0);
            // Update the player's warmth level in the UI or logic
            // MonsterSpice.LOGGER.info("Received warmth sync: {}", warmth);
            // Here you would typically update the UI or internal state with the new warmth values
            ((IEntityDataReadWrite) context.client().player).monsterSpice$setWarmth(warmth);
        }));
    }
}
