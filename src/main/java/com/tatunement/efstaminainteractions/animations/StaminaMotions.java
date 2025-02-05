package com.tatunement.efstaminainteractions.animations;

import yesman.epicfight.api.animation.LivingMotion;
import yesman.epicfight.api.animation.LivingMotions;

public enum StaminaMotions implements LivingMotion {

    TIRED_WALK,
    TIRED_IDLE;

    final int id;

    StaminaMotions() {
        this.id = LivingMotion.ENUM_MANAGER.assign(this);
    }

    @Override
    public int universalOrdinal() {
        return id;
    }
}
