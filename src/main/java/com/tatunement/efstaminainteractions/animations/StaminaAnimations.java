
package com.tatunement.efstaminainteractions.animations;

import com.tatunement.efstaminainteractions.EpicFightStaminaInteractionsMod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.types.MovementAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.animation.AnimationManager.AnimationRegistryEvent;
import yesman.epicfight.gameasset.Armatures;

@Mod.EventBusSubscriber(modid = EpicFightStaminaInteractionsMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class StaminaAnimations {
    public static AnimationManager.AnimationAccessor<StaticAnimation> TIRED_OPENING_BIPED_ANIMATION;
    public static AnimationManager.AnimationAccessor<StaticAnimation> TIRED_LOOPING_BIPED_ANIMATION;
    public static AnimationManager.AnimationAccessor<MovementAnimation> WALKING_TIRED_BIPED_ANIMATION;

    @SubscribeEvent
    public static void registerAnimations(AnimationRegistryEvent event) {
        event.newBuilder(EpicFightStaminaInteractionsMod.MODID, StaminaAnimations::buildAnimations);
    }

    private static void buildAnimations(AnimationManager.AnimationBuilder builder) {

        TIRED_OPENING_BIPED_ANIMATION = builder.nextAccessor("biped/tired/tired_idle", (accessor) -> new StaticAnimation(true, accessor, Armatures.BIPED));
        WALKING_TIRED_BIPED_ANIMATION = builder.nextAccessor("biped/tired/tired_walk", (accessor) -> new MovementAnimation(true, accessor, Armatures.BIPED));
        TIRED_LOOPING_BIPED_ANIMATION = builder.nextAccessor("biped/tired/tired_loop", (accessor) -> new StaticAnimation(true, accessor, Armatures.BIPED));
    }
}
