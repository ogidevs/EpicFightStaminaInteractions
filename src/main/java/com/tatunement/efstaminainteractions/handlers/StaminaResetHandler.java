package com.tatunement.efstaminainteractions.handlers;

import com.tatunement.efstaminainteractions.EpicFightStaminaInteractionsMod;
import com.tatunement.efstaminainteractions.config.EpicFightStaminaInteractionsConfig;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;

@Mod.EventBusSubscriber(modid = EpicFightStaminaInteractionsMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class StaminaResetHandler {

    @SubscribeEvent
    public static void onPlayerLoad(PlayerEvent.LoadFromFile event) { resetPlayerStamina(event.getEntity());}

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        resetPlayerStamina(event.getEntity());
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        resetPlayerStamina(event.getEntity());
    }

    private static void resetPlayerStamina(Player player) {
        PlayerPatch<Player> playerPatch = EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);

        if(playerPatch != null) {
            playerPatch.setStamina(EpicFightStaminaInteractionsConfig.MAX_START_STAMINA.get().floatValue());

            AttributeInstance maxStaminaAttribute = player.getAttribute(EpicFightAttributes.MAX_STAMINA.get());
            if (maxStaminaAttribute != null) {
                maxStaminaAttribute.setBaseValue(EpicFightStaminaInteractionsConfig.MAX_START_STAMINA.get());
            }
        }
    }
}
