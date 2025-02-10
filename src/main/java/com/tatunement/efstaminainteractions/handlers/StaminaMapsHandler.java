package com.tatunement.efstaminainteractions.handlers;

import com.mojang.logging.LogUtils;
import com.tatunement.efstaminainteractions.EpicFightStaminaInteractionsMod;
import com.tatunement.efstaminainteractions.events.RegisterAnimationStaminaCostEvent;
import com.tatunement.efstaminainteractions.events.RegisterWeaponStaminaCostEvent;
import com.tatunement.efstaminainteractions.registries.AnimationStaminaCostRegistry;
import com.tatunement.efstaminainteractions.registries.WeaponStaminaCostRegistry;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = EpicFightStaminaInteractionsMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class StaminaMapsHandler {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onRegisteredWeaponStaminaCost(RegisterWeaponStaminaCostEvent event) {
        StaminaHandler.setWeaponStaminaCosts(WeaponStaminaCostRegistry.getWeaponStaminaCosts());
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onRegisteredAnimationStaminaCost(RegisterAnimationStaminaCostEvent event) {
        StaminaHandler.setAnimationsStaminaCosts(AnimationStaminaCostRegistry.getAnimationCostMap());
    }
}
