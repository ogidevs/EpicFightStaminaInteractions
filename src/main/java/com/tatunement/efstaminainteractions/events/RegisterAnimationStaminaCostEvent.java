package com.tatunement.efstaminainteractions.events;

import net.minecraftforge.eventbus.api.Event;

import java.util.Map;

public class RegisterAnimationStaminaCostEvent extends Event {
    private final Map<String, Float> animationStaminaCost;

    public RegisterAnimationStaminaCostEvent(Map<String, Float> animationStaminaCost) {
        this.animationStaminaCost = animationStaminaCost;
    }

    public Map<String, Float> getAnimationStaminaCost() {
        return animationStaminaCost;
    }
}

