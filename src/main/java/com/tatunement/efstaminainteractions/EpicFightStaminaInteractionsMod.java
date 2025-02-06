package com.tatunement.efstaminainteractions;

import com.mojang.logging.LogUtils;
import com.tatunement.efstaminainteractions.config.EpicFightStaminaInteractionsConfig;
import com.tatunement.efstaminainteractions.effects.ModEffects;
import com.tatunement.efstaminainteractions.registries.AnimationStaminaCostRegistry;
import com.tatunement.efstaminainteractions.registries.WeaponStaminaCostRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.asm.service.MixinService;

@Mod(EpicFightStaminaInteractionsMod.MODID)
public class EpicFightStaminaInteractionsMod {
    public static final String MODID = "efstaminainteractions";

    private static final Logger LOGGER = LogUtils.getLogger();

    public EpicFightStaminaInteractionsMod(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);

        LOGGER.info("Reading Configuration File");
        context.registerConfig(ModConfig.Type.COMMON, EpicFightStaminaInteractionsConfig.CONFIG_SPEC);

        LOGGER.info("Loading stamina consumption for each weapon category...");
        WeaponStaminaCostRegistry.registerWeaponStamina();
        LOGGER.info("Loading customized stamina cost for animations if set...");
        AnimationStaminaCostRegistry.registerAnimationStamina();
        LOGGER.info("Loading Mixins configuration");
        MixinBootstrap.init();
        Mixins.addConfiguration("mixins.efstaminainteractions.json");
        LOGGER.info(Mixins.getConfigs().toString());

        ModEffects.MOB_EFFECTS.register(context.getModEventBus());
    }


    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("Loading EpicFight Stamina Interactions...");
    }
}
