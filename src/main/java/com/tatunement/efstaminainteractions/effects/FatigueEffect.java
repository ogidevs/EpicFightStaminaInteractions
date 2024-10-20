package com.tatunement.efstaminainteractions.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.UUID;

public class FatigueEffect extends MobEffect {
    private static final UUID ATTACK_SPEED_MODIFIER_UUID = UUID.fromString("d24fefea-36bb-4ac5-9517-df2e9d7f4907");
    private static final UUID STAMINA_REGEN_MODIFIER_UUID = UUID.fromString("12f3c2ef-58d2-4a23-9e30-53fd7bb2e2f3");

    public FatigueEffect() {
        super(MobEffectCategory.HARMFUL, 8421504);
    }



    @Override
    @ParametersAreNonnullByDefault
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if(entity instanceof Player player) {
            PlayerPatch<Player> playerPatch = EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);

            if(playerPatch != null) {
                player.setSprinting(false);

                AttributeInstance attackSpeedAttr = player.getAttribute(Attributes.ATTACK_SPEED);
                if(attackSpeedAttr != null) {
                    AttributeModifier attackSpeedModifier = new AttributeModifier(ATTACK_SPEED_MODIFIER_UUID, "Fatigue attack speed reduction", -0.8D, AttributeModifier.Operation.MULTIPLY_TOTAL);
                    if(attackSpeedAttr.getModifier(ATTACK_SPEED_MODIFIER_UUID) == null) {
                        attackSpeedAttr.addTransientModifier(attackSpeedModifier);
                    }
                }

                AttributeInstance staminaRegenAttr = player.getAttribute(EpicFightAttributes.STAMINA_REGEN.get());
                if(staminaRegenAttr != null) {
                    AttributeModifier staminaRegenModifier = new AttributeModifier(STAMINA_REGEN_MODIFIER_UUID, "Fatigue stamina regen reduction", -0.5D, AttributeModifier.Operation.MULTIPLY_TOTAL);
                    if(staminaRegenAttr.getModifier(STAMINA_REGEN_MODIFIER_UUID) == null) {
                        staminaRegenAttr.addTransientModifier(staminaRegenModifier);
                    }
                }
            }
        }
    }

    @Override
    @ParametersAreNonnullByDefault
    public void removeAttributeModifiers(LivingEntity entity, AttributeMap attributeMap, int amplifier) {
        super.removeAttributeModifiers(entity, attributeMap, amplifier);

        if(entity instanceof Player player) {
            if(player.getAttribute(Attributes.ATTACK_SPEED) != null) {
                player.getAttribute(Attributes.ATTACK_SPEED).removeModifier(ATTACK_SPEED_MODIFIER_UUID);
            }

            if(player.getAttribute(EpicFightAttributes.STAMINA_REGEN.get()) != null) {
                player.getAttribute(EpicFightAttributes.STAMINA_REGEN.get()).removeModifier(STAMINA_REGEN_MODIFIER_UUID);
            }
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        if(this instanceof FatigueEffect) {
            return true;
        }
        return super.isDurationEffectTick(duration, amplifier);
    }
}
