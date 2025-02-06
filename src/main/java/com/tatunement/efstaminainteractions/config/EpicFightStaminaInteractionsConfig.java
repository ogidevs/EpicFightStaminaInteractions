package com.tatunement.efstaminainteractions.config;


import com.tatunement.efstaminainteractions.EpicFightStaminaInteractionsMod;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.compress.utils.Lists;

import java.util.List;

@Mod.EventBusSubscriber(modid = EpicFightStaminaInteractionsMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EpicFightStaminaInteractionsConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec.DoubleValue AXE_STAMINA_COST = BUILDER
            .comment("Stamina cost for using an axe (Default: 3.5)")
            .defineInRange("axeStaminaCost", 3.5D, 0.1D, 10.0D);
    public static final ForgeConfigSpec.DoubleValue SWORD_STAMINA_COST = BUILDER
            .comment("Stamina cost for using a sword (Default: 2.5)")
            .defineInRange("swordStaminaCost", 2.5D, 0.1D, 10.0D);
    public static final ForgeConfigSpec.DoubleValue SPEAR_STAMINA_COST = BUILDER
            .comment("Stamina cost for using a spear (Default: 3.0)")
            .defineInRange("spearStaminaCost", 3.0D, 0.1D, 10.0D);
    public static final ForgeConfigSpec.DoubleValue GREATSWORD_STAMINA_COST = BUILDER
            .comment("Stamina cost for using a greatsword (Default: 4.0)")
            .defineInRange("greatswordStaminaCost",4.0D, 0.1D, 10.0D);
    public static final ForgeConfigSpec.DoubleValue DAGGER_STAMINA_COST = BUILDER
            .comment("Stamina cost for using a dagger (Default: 1.2)")
            .defineInRange("daggerStaminaCost", 1.2D, 0.1D, 10.0D);
    public static final ForgeConfigSpec.DoubleValue BOW_STAMINA_COST = BUILDER
            .comment("Stamina cost for using a bow (Default: 0.5)")
            .defineInRange("bowStaminaCost", 0.5D, 0.1D, 10.0D);
    public static final ForgeConfigSpec.DoubleValue CROSSBOW_STAMINA_COST = BUILDER
            .comment("Stamina cost for using a crossbow (Default: 0.3)")
            .defineInRange("crossbowStaminaCost", 0.3D, 0.1D, 10.0D);
    public static final ForgeConfigSpec.DoubleValue TRIDENT_STAMINA_COST = BUILDER
            .comment("Stamina cost for using a trident (Default: 3.5)")
            .defineInRange("tridentStaminaCost", 3.5D, 0.1D, 10.0D);
    public static final ForgeConfigSpec.DoubleValue UCHIGATANA_STAMINA_COST = BUILDER
            .comment("Stamina cost for using an uchigatana (Default: 3.2)")
            .defineInRange("uchigatanaStaminaCost", 3.2D, 0.1D, 10.0D);
    public static final ForgeConfigSpec.DoubleValue TACHI_STAMINA_COST = BUILDER
            .comment("Stamina cost for using a tachi (Default: 3.5)")
            .defineInRange("tachiStaminaCost", 3.5D, 0.1D, 10.0D);
    public static final ForgeConfigSpec.DoubleValue LONGSWORD_STAMINA_COST = BUILDER
            .comment("Stamina cost for using a longsword (Default: 3.0)")
            .defineInRange("longswordStaminaCost", 3.0D, 0.1D, 10.0D);
    public static final ForgeConfigSpec.DoubleValue HOE_STAMINA_COST = BUILDER
            .comment("Stamina cost for using a hoe (Default: 1.5)")
            .defineInRange("hoeStaminaCost", 1.5D, 0.1D, 10.0D);
    public static final ForgeConfigSpec.DoubleValue SHOVEL_STAMINA_COST = BUILDER
            .comment("Stamina cost for using a shovel (Default: 1.5)")
            .defineInRange("shovelStaminaCost", 1.5D, 0.1D, 10.0D);
    public static final ForgeConfigSpec.DoubleValue PICKAXE_STAMINA_COST = BUILDER
            .comment("Stamina cost for using a pickaxe (Default: 1.8)")
            .defineInRange("pickaxeStaminaCost", 1.8D, 0.1D, 10.0D);
    public static final ForgeConfigSpec.DoubleValue SPRINT_STAMINA_COST = BUILDER
            .comment("Stamina cost per tick while sprinting (Default: 0.1)")
            .defineInRange("sprintStaminaCost", 0.3D, 0.1D, 10.0D);
    public static final ForgeConfigSpec.DoubleValue SHIELD_STAMINA_MULTIPLIER = BUILDER
            .comment("Multiplier for stamina cost based on blocked damage (Default: 1.2)")
            .defineInRange("shieldStaminaMultiplier", 1.2D, 0.1D, 10.0D);
    public static final ForgeConfigSpec.DoubleValue MAX_START_STAMINA = BUILDER
            .comment("Base stamina in start(Default: 100.0)")
            .defineInRange("maxStartStamina", 100.0D, 1.0D, 1024.0D);
    public static final ForgeConfigSpec.DoubleValue JUMP_STAMINA_COST = BUILDER
            .comment("Stamina cost for jumping (Default: 0.5)")
            .defineInRange("jumpStaminaCost",0.5D, 0.1D, 10.0D);
    public static final ForgeConfigSpec.BooleanValue enableSprintStamina = BUILDER
            .comment("Enable or disable stamina cost for sprinting (Default: true)")
            .define("enableSprintStamina", true);
    public static final ForgeConfigSpec.BooleanValue enableAttackStamina = BUILDER
            .comment("Enable or disable stamina cost for attacks (Default: true)")
            .define("enableAttackStamina", true);
    public static final ForgeConfigSpec.BooleanValue enableCampfireBuffs = BUILDER
            .comment("Enable or disable buffs for resting near campfires (Default: true)")
            .define("enableCampfireBuffs", true);
    public static final ForgeConfigSpec.BooleanValue enableShieldStamina = BUILDER
            .comment("Enable or disable stamina cost for blocking with shield (Default: true)")
            .define("enableShieldStamina", true);
    public static final ForgeConfigSpec.BooleanValue enableJumpStamina = BUILDER
            .comment("Enable or disable stamina cost for jump (Default: true)")
            .define("enableJumpStamina", true);
    public static final ForgeConfigSpec.BooleanValue enableAnimationCosts = BUILDER
            .comment("Enable or disable stamina multiplier for different animations (Default: false)")
            .define("enableAnimationCost", false);
    public static final ForgeConfigSpec.BooleanValue enableDebugMode = BUILDER
            .comment("Enable or disable Debug Mode (writes in chat the path of every animation when in battle mode. Default: false)")
            .define("enableDebugMode", false);
    public static final ForgeConfigSpec.BooleanValue enableDamageScalingCost = BUILDER
            .comment("Enable or disable stamina multiplier based on weapon damage (Every attack will now have the cost of the stamina set in this file or in datapacks. Default: true)")
            .define("enableDamageScalingCost", true);
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> customAnimationCosts = BUILDER
            .comment("You can add your own stamina by writing the path of the attack animation and the cost.")
            .comment("Example: customAnimationCosts = [\"animmodels/animations/biped/living/greatsword_airslash.json:2.5\", \"animmodels/animations/biped/living/spear_dash.json:3.5\"]")
            .comment("Keep in mind that the cost is added to the attack cost!")
            .defineList("customAnimationCosts", Lists.newArrayList(), (element)-> element instanceof String);
    public static final ForgeConfigSpec CONFIG_SPEC = BUILDER.build();
}


