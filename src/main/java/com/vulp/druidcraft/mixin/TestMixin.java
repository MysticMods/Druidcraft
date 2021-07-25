package com.vulp.druidcraft.mixin;

import net.minecraft.entity.item.ItemEntity;
import net.minecraft.util.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemEntity.class)
public class TestMixin {

    @Inject(at = @At("HEAD"), method = "attackEntityFrom(Lnet/minecraft/util/DamageSource;F)Z", cancellable = true)
    private void attackEntityFrom(DamageSource source, float amount, CallbackInfoReturnable<Boolean> callback) {
        callback.setReturnValue(false);
    }

}