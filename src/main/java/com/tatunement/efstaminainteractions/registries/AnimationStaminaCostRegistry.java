package com.tatunement.efstaminainteractions.registries;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import com.tatunement.efstaminainteractions.EpicFightStaminaInteractionsMod;
import com.tatunement.efstaminainteractions.config.EpicFightStaminaInteractionsConfig;
import com.tatunement.efstaminainteractions.events.RegisterAnimationStaminaCostEvent;
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
import yesman.epicfight.world.capabilities.item.WeaponCategory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(modid = EpicFightStaminaInteractionsMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class AnimationStaminaCostRegistry {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Map<String, Float> ANIMATION_COST_MAP = new HashMap<>();

    static{
        ANIMATION_COST_MAP.putAll(parseAnimationCosts());
    }

    public static void registerAnimationStamina() {
        RegisterAnimationStaminaCostEvent event = new RegisterAnimationStaminaCostEvent(ANIMATION_COST_MAP);
        MinecraftForge.EVENT_BUS.post(event);
    }

    public static Map<String, Float> getAnimationCostMap() {
        return ANIMATION_COST_MAP;
    }

    public static void addAnimationCost(String animationPath, float animationCost) {
        ANIMATION_COST_MAP.put(animationPath, animationCost);
    }

    public static Map<String, Float> parseAnimationCosts() {
        List<? extends String> configList = EpicFightStaminaInteractionsConfig.customAnimationCosts.get();
        if(configList != null && !configList.isEmpty()) {
            Map<String,Float> animationCostMap = new HashMap<>();

            for(String config : configList) {
                String[] parts = config.split(":");
                if (parts.length == 2) {
                    animationCostMap.put(parts[0], Float.parseFloat(parts[1]));
                }
            }
            return animationCostMap;
        }
        return new HashMap<>();
    }

    @SubscribeEvent
    public static void onDatapackReload(AddReloadListenerEvent event) {
        event.addListener(new SimpleJsonResourceReloadListener(new Gson(), "animation_stamina") {
            @Override
            protected void apply(@NotNull Map<ResourceLocation, JsonElement> data, @NotNull ResourceManager resourceManager, @NotNull ProfilerFiller profiler) {
                for (Map.Entry<ResourceLocation, JsonElement> entry : data.entrySet()) {
                    JsonObject json = entry.getValue().getAsJsonObject();
                    for (Map.Entry<String, JsonElement> animationEntry : json.entrySet()) {
                        try {
                                String animationPath = animationEntry.getKey();
                                Float cost = animationEntry.getValue().getAsFloat();
                                addAnimationCost(animationPath, cost);
                        } catch (NumberFormatException e) {
                            LOGGER.error("Invalid stamina cost for " + animationEntry.getKey() + ": " + animationEntry.getValue(), e);
                        } catch (Exception e){
                            LOGGER.error("Error processing weapon category: " + animationEntry.getKey(), e);
                        }
                    }
                }
            }
        });
    }
}
