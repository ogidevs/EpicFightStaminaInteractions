package com.tatunement.efstaminainteractions.mixins;

import com.tatunement.efstaminainteractions.animations.StaminaAnimations;
import com.tatunement.efstaminainteractions.animations.StaminaMotions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.api.animation.Animator;
import yesman.epicfight.api.client.animation.ClientAnimator;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

@Mixin(value = PlayerPatch.class, remap = false)
public class PlayerPatchMixin {

    @Inject(at = @At(value="TAIL"), method = "initAnimator(Lyesman/epicfight/api/animation/Animator;)V")
    private void injectInit(Animator animator, CallbackInfo ci) {
        animator.addLivingAnimation(StaminaMotions.TIRED_IDLE, StaminaAnimations.TIRED_LOOPING_BIPED_ANIMATION);
        animator.addLivingAnimation(StaminaMotions.TIRED_WALK, StaminaAnimations.WALKING_TIRED_BIPED_ANIMATION);
    }
}
