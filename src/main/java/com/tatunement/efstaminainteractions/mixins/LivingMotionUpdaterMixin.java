package com.tatunement.efstaminainteractions.mixins;

import com.mojang.logging.LogUtils;
import com.tatunement.efstaminainteractions.animations.StaminaMotions;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.client.world.capabilites.entitypatch.player.AbstractClientPlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

@Mixin(value = AbstractClientPlayerPatch.class)
public abstract class LivingMotionUpdaterMixin {

    @Shadow protected abstract boolean isMoving();

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraftforge/eventbus/api/IEventBus;post(Lnet/minecraftforge/eventbus/api/Event;)Z", shift = At.Shift.BEFORE, ordinal = 0), method = "updateMotion(Z)V")
    public void updateMotionPostInject(boolean considerInaction, CallbackInfo ci) {
        PlayerPatch<?> patch = ((PlayerPatch<?>) (Object) this);

        if (patch.getStamina() <= 0.0F && !patch.currentLivingMotion.isSame(LivingMotions.SWIM)) {
            if (isMoving()) {
                patch.currentLivingMotion = StaminaMotions.TIRED_WALK;
            } else {
                patch.currentLivingMotion = StaminaMotions.TIRED_IDLE;
            }
        }
    }
}
