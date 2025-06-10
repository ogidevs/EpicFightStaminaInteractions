package com.tatunement.efstaminainteractions.mixins;

import com.tatunement.efstaminainteractions.animations.StaminaMotions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.client.world.capabilites.entitypatch.player.AbstractClientPlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

@Mixin(value = AbstractClientPlayerPatch.class, remap = false)
public abstract class LivingMotionUpdaterMixin {

    @Inject(method = "updateMotion(Z)V", at = @At("TAIL"))
    public void updateMotionTailInject(boolean considerInaction, CallbackInfo ci) {
        PlayerPatch<?> patch = (PlayerPatch<?>) (Object) this;

        if (patch.getStamina() <= 0.0F && !patch.getOriginal().isSwimming()) {
            if (!patch.getOriginal().walkAnimation.isMoving()) {
                patch.currentLivingMotion = StaminaMotions.TIRED_IDLE;
            } else {
                patch.currentLivingMotion = StaminaMotions.TIRED_WALK;
            }
        }
    }
}
