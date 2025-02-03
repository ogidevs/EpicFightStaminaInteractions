package com.tatunement.efstaminainteractions.events;

import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.event.IModBusEvent;
import yesman.epicfight.world.capabilities.item.WeaponCategory;

import java.util.Map;

public class RegisterWeaponStaminaCostEvent extends Event implements IModBusEvent {
    private final Map<WeaponCategory, Float> weaponStaminaCostMap;

    public RegisterWeaponStaminaCostEvent(Map<WeaponCategory, Float> weaponStaminaCostMap) {
        this.weaponStaminaCostMap = weaponStaminaCostMap;
    }

    public Map<WeaponCategory, Float> getWeaponStaminaCostMap() {
        return weaponStaminaCostMap;
    }
}
