package com.tatunement.efstaminainteractions.animations;

import com.tatunement.efstaminainteractions.EpicFightStaminaInteractionsMod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.animation.types.MovementAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.forgeevent.AnimationRegistryEvent;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.model.armature.HumanoidArmature;

@Mod.EventBusSubscriber(modid = EpicFightStaminaInteractionsMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class StaminaAnimations {
    public static StaticAnimation TIRED_OPENING_BIPED_ANIMATION;
    public static StaticAnimation TIRED_LOOPING_BIPED_ANIMATION;
    public static StaticAnimation WALKING_TIRED_BIPED_ANIMATION;

    @SubscribeEvent
    public static void registerAnimations(AnimationRegistryEvent event) {
        event.getRegistryMap().put(EpicFightStaminaInteractionsMod.MODID, StaminaAnimations::buildAnimations);
    }

    private static void buildAnimations() {
        HumanoidArmature biped = Armatures.BIPED;

        TIRED_OPENING_BIPED_ANIMATION = new StaticAnimation(false, "biped/tired/tired_idle", biped);
        WALKING_TIRED_BIPED_ANIMATION = new MovementAnimation(true, "biped/tired/tired_walk", biped);
        TIRED_LOOPING_BIPED_ANIMATION = new StaticAnimation(true, "biped/tired/tired_loop", biped);
    }
}
