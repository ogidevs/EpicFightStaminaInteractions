package com.tatunement.efstaminainteractions.handlers;

import com.tatunement.efstaminainteractions.EpicFightStaminaInteractionsMod;
import com.tatunement.efstaminainteractions.config.EpicFightStaminaInteractionsConfig;
import com.tatunement.efstaminainteractions.effects.ModEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = EpicFightStaminaInteractionsMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CampfireEffectHandler {
    private static final Map<Player, Integer> campfireTimeMap = new HashMap<>();
    private static final int REQUIRED_TICKS = 1200;
    private static final int EFFECT_DURATION = 6000;

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if(event.phase == TickEvent.Phase.START) {
            if (EpicFightStaminaInteractionsConfig.enableCampfireBuffs.get()) {
                Player player = event.player;

                if(player.isCrouching()) {
                    boolean nearCampfire = false;
                    boolean nearSoulCampfire = false;

                    BlockPos playerPos = player.blockPosition();
                    for(int x = -4; x <= 4; x++) {
                        for(int y = -4; y <= 4; y++) {
                            for(int z = -4; z <= 4; z++) {
                                BlockPos checkPos = playerPos.offset(x, y, z);
                                BlockState blockState = player.level().getBlockState(checkPos);

                                if(blockState.is(Blocks.CAMPFIRE)) {
                                    nearCampfire = true;
                                }
                                if (blockState.is(Blocks.SOUL_CAMPFIRE)) {
                                    nearSoulCampfire = true;
                                }
                            }
                        }
                    }
                    if(nearCampfire || nearSoulCampfire) {
                        int timeNearCampfire = campfireTimeMap.getOrDefault(player, 0) + 1;
                        campfireTimeMap.put(player, timeNearCampfire);

                        if (timeNearCampfire >= REQUIRED_TICKS) {
                            if(nearCampfire) {
                                player.addEffect(new MobEffectInstance(ModEffects.STRENGHT_SURGE.get(), EFFECT_DURATION, 0, true, false, false));
                                player.addEffect(new MobEffectInstance(ModEffects.VIGOR.get(), EFFECT_DURATION, 0, true, false, false));
                            }
                            if(nearSoulCampfire){
                                player.addEffect(new MobEffectInstance(MobEffects.HUNGER, 3000, 0, true, false, false));
                                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 3000, 1, true, false, false));
                            }

                            campfireTimeMap.put(player, 0);
                        }
                    } else {
                        campfireTimeMap.remove(player);
                    }
                } else {
                    campfireTimeMap.remove(player);
                }
            }
        }
    }
}
