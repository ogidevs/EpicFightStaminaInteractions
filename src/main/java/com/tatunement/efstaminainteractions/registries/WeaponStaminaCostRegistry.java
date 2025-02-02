package com.tatunement.efstaminainteractions.registries;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import com.tatunement.efstaminainteractions.EpicFightStaminaInteractionsMod;
import com.tatunement.efstaminainteractions.config.EpicFightStaminaInteractionsConfig;
import com.tatunement.efstaminainteractions.events.RegisterWeaponStaminaCostEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCategory;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = EpicFightStaminaInteractionsMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class WeaponStaminaCostRegistry {
    private static final Logger LOGGER = LogUtils.getLogger();

    private static final Map<WeaponCategory, Float> WEAPON_CATEGORIES_FLOAT_MAP = new HashMap<>();

    static {
        Object[][] weaponData = {
                {CapabilityItem.WeaponCategories.AXE, EpicFightStaminaInteractionsConfig.AXE_STAMINA_COST.get()},
                {CapabilityItem.WeaponCategories.SWORD, EpicFightStaminaInteractionsConfig.SWORD_STAMINA_COST.get()},
                {CapabilityItem.WeaponCategories.SPEAR, EpicFightStaminaInteractionsConfig.SPEAR_STAMINA_COST.get()},
                {CapabilityItem.WeaponCategories.GREATSWORD, EpicFightStaminaInteractionsConfig.GREATSWORD_STAMINA_COST.get()},
                {CapabilityItem.WeaponCategories.DAGGER, EpicFightStaminaInteractionsConfig.DAGGER_STAMINA_COST.get()},
                {CapabilityItem.WeaponCategories.TRIDENT, EpicFightStaminaInteractionsConfig.TRIDENT_STAMINA_COST.get()},
                {CapabilityItem.WeaponCategories.UCHIGATANA, EpicFightStaminaInteractionsConfig.UCHIGATANA_STAMINA_COST.get()},
                {CapabilityItem.WeaponCategories.TACHI, EpicFightStaminaInteractionsConfig.TACHI_STAMINA_COST.get()},
                {CapabilityItem.WeaponCategories.LONGSWORD, EpicFightStaminaInteractionsConfig.LONGSWORD_STAMINA_COST.get()},
                {CapabilityItem.WeaponCategories.HOE, EpicFightStaminaInteractionsConfig.HOE_STAMINA_COST.get()},
                {CapabilityItem.WeaponCategories.PICKAXE, EpicFightStaminaInteractionsConfig.PICKAXE_STAMINA_COST.get()},
                {CapabilityItem.WeaponCategories.SHOVEL, EpicFightStaminaInteractionsConfig.SHOVEL_STAMINA_COST.get()},
        };


        for(Object[] weapon : weaponData) {
            CapabilityItem.WeaponCategories category = (CapabilityItem.WeaponCategories) weapon[0];
            Double staminaCost = (Double) weapon[1];
            WEAPON_CATEGORIES_FLOAT_MAP.put(category, staminaCost.floatValue());
        }
    }

    public static void registerWeaponStamina() {
        RegisterWeaponStaminaCostEvent event = new RegisterWeaponStaminaCostEvent(WEAPON_CATEGORIES_FLOAT_MAP);
        MinecraftForge.EVENT_BUS.post(event);
    }

    public static Map<WeaponCategory, Float> getWeaponStaminaCosts() {
        return WEAPON_CATEGORIES_FLOAT_MAP;
    }

    public static void addWeaponStamina(WeaponCategory weaponCategory, Float staminaCost) {
        WEAPON_CATEGORIES_FLOAT_MAP.put(weaponCategory, staminaCost);
    }

    @SubscribeEvent
    public static void onDatapackReload(AddReloadListenerEvent event) {
        event.addListener(new SimpleJsonResourceReloadListener(new Gson(), "weapon_stamina") {
            @Override
            protected void apply(@NotNull Map<ResourceLocation, JsonElement> data, @NotNull ResourceManager resourceManager, @NotNull ProfilerFiller profiler) {
                for (Map.Entry<ResourceLocation, JsonElement> entry : data.entrySet()) {
                    JsonObject json = entry.getValue().getAsJsonObject();
                    for (Map.Entry<String, JsonElement> weaponEntry : json.entrySet()) {
                        try {
                            WeaponCategory category = WeaponCategory.ENUM_MANAGER.get(weaponEntry.getKey().toUpperCase());
                            if (category != null) {
                                Float cost = weaponEntry.getValue().getAsFloat();
                                addWeaponStamina(category, cost);
                            } else {
                                LOGGER.error("Cant find weapon category: " + weaponEntry.getKey());
                            }
                        } catch (NumberFormatException e) {
                            LOGGER.error("Invalid stamina cost for " + weaponEntry.getKey() + ": " + weaponEntry.getValue(), e);
                        } catch (Exception e){
                            LOGGER.error("Error processing weapon category: " + weaponEntry.getKey(), e);
                        }
                    }
                }
            }
        });
    }
}
