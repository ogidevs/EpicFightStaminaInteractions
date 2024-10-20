package com.tatunement.efstaminainteractions.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.UUID;

public class StrenghtSurgeEffect extends MobEffect {
    private static final UUID STAMINA_INCREASE_UUID = UUID.fromString("82f3a6c7-7525-4f4f-8fa1-7b2b72246d93");

    public StrenghtSurgeEffect() {
        super(MobEffectCategory.BENEFICIAL, 255);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if(entity instanceof Player player) {
            if(player.getAttribute(EpicFightAttributes.MAX_STAMINA.get()) != null) {
                AttributeModifier staminaIncrease = new AttributeModifier(STAMINA_INCREASE_UUID, "Strenght Surge stamina increase", 10*(amplifier+1), AttributeModifier.Operation.ADDITION);
                if(player.getAttribute(EpicFightAttributes.MAX_STAMINA.get()).getModifier(STAMINA_INCREASE_UUID) == null) {
                    player.getAttribute(EpicFightAttributes.MAX_STAMINA.get()).addTransientModifier(staminaIncrease);
                }
            }
        }
    }

    @Override
    @ParametersAreNonnullByDefault
    public void removeAttributeModifiers(LivingEntity entity, AttributeMap attributeMap, int amplifier) {
        super.removeAttributeModifiers(entity, attributeMap, amplifier);

        if(entity instanceof Player player) {
            if(player.getAttribute(EpicFightAttributes.MAX_STAMINA.get()) != null) {
                player.getAttribute(EpicFightAttributes.MAX_STAMINA.get()).removeModifier(STAMINA_INCREASE_UUID);
            }
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        if(this instanceof StrenghtSurgeEffect) {
            return true;
        }
        return super.isDurationEffectTick(duration, amplifier);
    }
}
