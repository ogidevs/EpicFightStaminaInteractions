package com.tatunement.efstaminainteractions;

import com.mojang.logging.LogUtils;
import com.tatunement.efstaminainteractions.config.EpicFightStaminaInteractionsConfig;
import com.tatunement.efstaminainteractions.effects.ModEffects;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(EpicFightStaminaInteractionsMod.MODID)
public class EpicFightStaminaInteractionsMod {
    public static final String MODID = "efstaminainteractions";

    private static final Logger LOGGER = LogUtils.getLogger();

    public EpicFightStaminaInteractionsMod(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);

        ModEffects.MOB_EFFECTS.register(context.getModEventBus());


        context.registerConfig(ModConfig.Type.COMMON, EpicFightStaminaInteractionsConfig.CONFIG_SPEC);
    }


    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("Loading EpicFight Stamina Interactions...");
    }


}
