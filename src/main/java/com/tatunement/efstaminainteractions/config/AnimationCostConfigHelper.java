package com.tatunement.efstaminainteractions.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnimationCostConfigHelper {
    public static Map<String, Double> parseAnimationCosts() {
        List<? extends String> configList = EpicFightStaminaInteractionsConfig.customAnimationCosts.get();
        Map<String,Double> animationCostMap = new HashMap<>();

        for(String config : configList) {
            String[] parts = config.split(":");
            if (parts.length == 2) {
                animationCostMap.put(parts[0], Double.parseDouble(parts[1]));
            }
        }
        return animationCostMap;
    }
}
