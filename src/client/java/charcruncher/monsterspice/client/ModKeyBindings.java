package charcruncher.monsterspice.client;

import charcruncher.monsterspice.networking.payloads.SpecialMovePayload;
import charcruncher.monsterspice.item.EmberbloomShieldItem;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.glfw.GLFW;

public class ModKeyBindings {
    public static KeyBinding specialMoveBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.monster-spice.special_move",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_G,
            "category.monster-spice.abilities"
    ));

    public static void registerKeyBindings() {
        ClientTickEvents.END_CLIENT_TICK.register(minecraftClient -> {
            if (specialMoveBinding.wasPressed() && minecraftClient.player != null) {
                if (minecraftClient.player.getMainHandStack().getItem() instanceof EmberbloomShieldItem ||
                        minecraftClient.player.getOffHandStack().getItem() instanceof EmberbloomShieldItem) {
                    ClientPlayNetworking.send(new SpecialMovePayload(BlockPos.ORIGIN));

                }
            }

        });

    }
}
