package charcruncher.monsterspice.mixin;

import charcruncher.monsterspice.logic.IEntityDataReadWrite;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityNbtDataMixin implements IEntityDataReadWrite {
    @Unique
    private int warmth = 0;

    @Override
    public int monsterSpice$getWarmth() {
        return this.warmth;
    }

    @Override
    public void monsterSpice$setWarmth(int value) {
        this.warmth = value;
    }

    @Inject(method= "writeNbt", at = @At("HEAD"))
    public void injectWriteNbt(NbtCompound nbt, CallbackInfoReturnable<NbtCompound> info) {
        // Save warmth level to NBT
        nbt.putInt("warmth", this.warmth);
    }

    @Inject(method = "readNbt", at = @At("HEAD"))
    protected void injectReadNbt(NbtCompound nbt, CallbackInfo info) {
        // Load warmth level from NBT
        if (nbt.contains("warmth")) {
            this.warmth = nbt.getInt("warmth");
        } else {
            this.warmth = 0; // Default value if not present
        }
    }
}
