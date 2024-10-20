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

public class VigorEffect extends MobEffect {
    private static final UUID STAMINA_REGEN_BOOST_UUID = UUID.fromString("7d68acb3-635b-4b48-86a6-999f3123e79b");

    public VigorEffect() {
        super(MobEffectCategory.BENEFICIAL, 65280);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if(entity instanceof Player player) {
            if(player.getAttribute(EpicFightAttributes.STAMINA_REGEN.get()) != null) {
                AttributeModifier regenBoost = new AttributeModifier(STAMINA_REGEN_BOOST_UUID, "Vigor stamina regen boost", 0.2D * (amplifier+1), AttributeModifier.Operation.MULTIPLY_TOTAL);
                if (player.getAttribute(EpicFightAttributes.STAMINA_REGEN.get()).getModifier(STAMINA_REGEN_BOOST_UUID) == null) {
                    player.getAttribute(EpicFightAttributes.STAMINA_REGEN.get()).addTransientModifier(regenBoost);
                }
            }
        }
    }

    @Override
    @ParametersAreNonnullByDefault
    public void removeAttributeModifiers(LivingEntity entity, AttributeMap attributeMap, int amplifier) {
        super.removeAttributeModifiers(entity, attributeMap, amplifier);

        if(entity instanceof Player player) {
            if(player.getAttribute(EpicFightAttributes.STAMINA_REGEN.get()) != null) {
                player.getAttribute(EpicFightAttributes.STAMINA_REGEN.get()).removeModifier(STAMINA_REGEN_BOOST_UUID);
            }
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        if(this instanceof VigorEffect) {
            return true;
        }
        return super.isDurationEffectTick(duration, amplifier);
    }
}
