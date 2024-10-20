package com.tatunement.efstaminainteractions.effects;

import com.tatunement.efstaminainteractions.EpicFightStaminaInteractionsMod;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = EpicFightStaminaInteractionsMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, EpicFightStaminaInteractionsMod.MODID);

    public static final RegistryObject<MobEffect> FATIGUE = MOB_EFFECTS.register("fatigue", FatigueEffect::new);

    public static final RegistryObject<MobEffect> VIGOR = MOB_EFFECTS.register("vigor", VigorEffect::new);

    public static final RegistryObject<MobEffect> STRENGHT_SURGE = MOB_EFFECTS.register("strength_surge", StrenghtSurgeEffect::new);
}
