package com.tatunement.efstaminainteractions.monitors;

import com.tatunement.efstaminainteractions.EpicFightStaminaInteractionsMod;
import com.tatunement.efstaminainteractions.effects.ModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

@Mod.EventBusSubscriber(modid = EpicFightStaminaInteractionsMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class StaminaMonitor {
    @SubscribeEvent
    public static void onPlayerTick(LivingEvent.LivingTickEvent event) {
        LivingEntity livingEntity = event.getEntity();
        if(livingEntity instanceof Player player) {
            PlayerPatch<Player> playerPatch = EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);
            if (playerPatch != null) {
                float currentStamina = playerPatch.getStamina();
                if (currentStamina <= 0.0F) {
                    player.setSprinting(false);
                    if(!player.hasEffect(ModEffects.FATIGUE.get())) {
                        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 110, 3, true, false, false));
                        player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 110, 3, true, false, false));
                        player.addEffect(new MobEffectInstance(ModEffects.FATIGUE.get(), 110, 1, true, false, false));
                    }
                    playerPatch.setStamina(0.0F);
                }
            }
        }
    }
}
