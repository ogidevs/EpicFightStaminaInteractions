package com.tatunement.efstaminainteractions.handlers;

import com.tatunement.efstaminainteractions.EpicFightStaminaInteractionsMod;
import com.tatunement.efstaminainteractions.animations.StaminaAnimations;
import com.tatunement.efstaminainteractions.animations.StaminaMotions;
import com.tatunement.efstaminainteractions.config.EpicFightStaminaInteractionsConfig;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.ShieldBlockEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.animation.LivingMotion;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCategory;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = EpicFightStaminaInteractionsMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class StaminaHandler {

    private static Map<WeaponCategory, Float> weaponStaminaCosts = new HashMap<>();
    private static Map<String, Float> animationsStaminaCosts = new HashMap<>();
    private static final Set<UUID> INITIALIZED_PLAYERS = new HashSet<>();

    public static void setWeaponStaminaCosts(Map<WeaponCategory, Float> costs) {
        weaponStaminaCosts = costs;
    }

    public static void setAnimationsStaminaCosts(Map<String, Float> costs) {
        animationsStaminaCosts = costs;
    }

    @SubscribeEvent
    public static void onPlayerLogsOut(PlayerEvent.PlayerLoggedOutEvent event) {
        INITIALIZED_PLAYERS.remove(event.getEntity().getUUID());
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        Player player = event.player;
        PlayerPatch<Player> playerPatch = EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);

        if (playerPatch != null) {
            if (INITIALIZED_PLAYERS.add(player.getUUID())) {
                initializePlayer(playerPatch);
            }

            handleSprinting(player, playerPatch);
            handleJumping(player, playerPatch);
            handleRangedWeapons(player, playerPatch);
        }
    }

    private static void initializePlayer(PlayerPatch<Player> playerPatch) {

        playerPatch.getEventListener().addEventListener(PlayerEventListener.EventType.BASIC_ATTACK_EVENT, playerPatch.getOriginal().getUUID(), event -> {
            if (EpicFightStaminaInteractionsConfig.enableAttackStamina.get() && playerPatch.isEpicFightMode()) {
                if (playerPatch.getStamina() <= 0.0F) {
                    event.setCanceled(true);
                    return;
                }
                CapabilityItem weaponCapability = playerPatch.getHoldingItemCapability(InteractionHand.MAIN_HAND);
                if (weaponCapability != null) {
                    Player player = playerPatch.getOriginal();
                    double weaponDamage = EpicFightStaminaInteractionsConfig.enableDamageScalingCost.get() ? player.getAttributeValue(Attributes.ATTACK_DAMAGE) : 0.0D;
                    WeaponCategory weaponCategory = weaponCapability.getWeaponCategory();
                    float weaponStaminaCost = weaponStaminaCosts.getOrDefault(weaponCategory, 0.0f);
                    float attackStaminaCost = (float)(weaponDamage * 0.54D) + weaponStaminaCost;
                    float currentStamina = playerPatch.getStamina();
                    playerPatch.setStamina(Math.max(0.0F, currentStamina - attackStaminaCost));
                }
            }
        });

        playerPatch.getEventListener().addEventListener(PlayerEventListener.EventType.ANIMATION_END_EVENT, playerPatch.getOriginal().getUUID(), event -> {
            if (EpicFightStaminaInteractionsConfig.enableAnimationCosts.get()) {
                StaticAnimation animation = event.getAnimation().getRealAnimation().get();
                if (animation != null && animation.getLocation() != null) {
                    String animationPath = animation.getLocation().getPath();
                    float animationCost = animationsStaminaCosts.getOrDefault(animationPath, 0.0F);
                    if (animationCost > 0) {
                        float currentStamina = playerPatch.getStamina();
                        playerPatch.setStamina(Math.max(0.0F, currentStamina - animationCost));
                    }
                }
            }
        });

        playerPatch.getEventListener().addEventListener(PlayerEventListener.EventType.ANIMATION_BEGIN_EVENT, playerPatch.getOriginal().getUUID(), event -> {
            if (EpicFightStaminaInteractionsConfig.enableDebugMode.get() && playerPatch.isLogicalClient()) {
                StaticAnimation animation = event.getAnimation().getRealAnimation().get();
                if (animation != null && animation.getLocation() != null) {
                    playerPatch.getOriginal().sendSystemMessage(Component.literal("[DEBUG] Anim Begin: " + animation.getLocation()));
                }
            }
        });
    }

    private static void handleSprinting(Player player, PlayerPatch<Player> playerPatch) {
        if (EpicFightStaminaInteractionsConfig.enableSprintStamina.get() && player.isSprinting() && !player.isCreative()) {
            if (playerPatch.getStamina() <= 0.0F) {
                player.setSprinting(false);
                return;
            }
            float sprintCost = EpicFightStaminaInteractionsConfig.SPRINT_STAMINA_COST.get().floatValue();
            float currentStamina = playerPatch.getStamina();
            playerPatch.setStamina(Math.max(0.0F, currentStamina - sprintCost));
            playerPatch.resetActionTick();
        }
    }

    private static void handleJumping(Player player, PlayerPatch<Player> playerPatch) {
        if (playerPatch.getStamina() <= 0.0F) return;

        if (manageJumpingConditions(player) && player.getDeltaMovement().y > 0.05) {
            float jumpCost = EpicFightStaminaInteractionsConfig.JUMP_STAMINA_COST.get().floatValue();
            float currentStamina = playerPatch.getStamina();
            playerPatch.setStamina(Math.max(0.0F, currentStamina - jumpCost));
            playerPatch.resetActionTick();
        }
    }

    private static void handleRangedWeapons(Player player, PlayerPatch<Player> playerPatch) {
        if (EpicFightStaminaInteractionsConfig.enableAttackStamina.get() && !player.isCreative() && player.isUsingItem()) {
            var activeItem = player.getUseItem().getItem();
            if (activeItem == Items.BOW || activeItem == Items.CROSSBOW) {
                if (playerPatch.getStamina() <= 0.0F) {
                    player.stopUsingItem();
                } else {
                    float cost = EpicFightStaminaInteractionsConfig.CROSSBOW_STAMINA_COST.get().floatValue();
                    float currentStamina = playerPatch.getStamina();
                    playerPatch.setStamina(Math.max(0.0F, currentStamina - cost));
                    playerPatch.resetActionTick();
                }
            }
        }
    }

    @SubscribeEvent
    public static void onEntityShieldBlock(ShieldBlockEvent event) {
        if (EpicFightStaminaInteractionsConfig.enableShieldStamina.get() && event.getEntity() instanceof Player player) {
            PlayerPatch<Player> playerPatch = EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);
            if (playerPatch != null) {
                if (playerPatch.getStamina() <= 0.0F) {
                    player.getCooldowns().addCooldown(player.getUseItem().getItem(), 80);
                    player.stopUsingItem();
                } else {
                    float blockedDamage = event.getBlockedDamage();
                    float staminaCost = blockedDamage * EpicFightStaminaInteractionsConfig.SHIELD_STAMINA_MULTIPLIER.get().floatValue();
                    float currentStamina = playerPatch.getStamina();
                    playerPatch.setStamina(Math.max(0.0F, currentStamina - staminaCost));
                    playerPatch.resetActionTick();
                }
            }
        }
    }


    private static boolean manageJumpingConditions(Player player) {
        return EpicFightStaminaInteractionsConfig.enableJumpStamina.get() && !player.isCreative() && !player.onClimbable() && !player.isSwimming() && !player.isInWater() && !player.isSleeping();
    }
}