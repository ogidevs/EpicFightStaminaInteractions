package com.tatunement.efstaminainteractions.monitors;

import com.tatunement.efstaminainteractions.EpicFightStaminaInteractionsMod;
import com.tatunement.efstaminainteractions.animations.StaminaAnimations;
import com.tatunement.efstaminainteractions.animations.StaminaMotions;
import com.tatunement.efstaminainteractions.effects.ModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.client.forgeevent.UpdatePlayerMotionEvent;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

@Mod.EventBusSubscriber(modid = EpicFightStaminaInteractionsMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class StaminaMonitor {
    @SubscribeEvent
    public static void onPlayerTick(LivingEvent.LivingTickEvent event) {
        LivingEntity livingEntity = event.getEntity();
        if (livingEntity instanceof Player player) {
            ServerPlayerPatch playerPatch = EpicFightCapabilities.getEntityPatch(player, ServerPlayerPatch.class);
            if (playerPatch != null) {
                float currentStamina = playerPatch.getStamina();
                if (currentStamina <= 0.0F) {
                    playerPatch.setStamina(0.1F);
                    if (player.isSprinting()) {
                        player.setSprinting(false);
                    }
                    if (!player.hasEffect(ModEffects.FATIGUE.get())) {
                        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 110, 3, true, false, false));
                        player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 110, 3, true, false, false));
                        player.addEffect(new MobEffectInstance(ModEffects.FATIGUE.get(), 110, 1, true, false, false));
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void OnLivingMotionUpdate(UpdatePlayerMotionEvent event) {
        if(event.getMotion().isSame(StaminaMotions.TIRED_WALK)) {
            event.getPlayerPatch().playAnimationSynchronized(StaminaAnimations.WALKING_TIRED_BIPED_ANIMATION, 0);
        }
        if(event.getMotion().isSame(StaminaMotions.TIRED_IDLE)) {
            event.getPlayerPatch().playAnimationSynchronized(StaminaAnimations.TIRED_LOOPING_BIPED_ANIMATION, 0);
        }
    }
}
