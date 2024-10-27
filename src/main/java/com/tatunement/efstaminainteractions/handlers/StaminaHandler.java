package com.tatunement.efstaminainteractions.handlers;

import com.tatunement.efstaminainteractions.EpicFightStaminaInteractionsMod;
import com.tatunement.efstaminainteractions.config.EpicFightStaminaInteractionsConfig;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.ShieldBlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCategory;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = EpicFightStaminaInteractionsMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class StaminaHandler {

    private static final Map<CapabilityItem.WeaponCategories, Float> weaponStaminaCosts = new HashMap<>();

    private static final Map<String, Double> animationsStaminaCosts = AnimationCostConfigHelper.parseAnimationCosts();

    public StaminaHandler() {
        Object[][] weaponData = {
                {CapabilityItem.WeaponCategories.AXE, EpicFightStaminaInteractionsConfig.AXE_STAMINA_COST},
                {CapabilityItem.WeaponCategories.SWORD, EpicFightStaminaInteractionsConfig.SWORD_STAMINA_COST},
                {CapabilityItem.WeaponCategories.SPEAR, EpicFightStaminaInteractionsConfig.SPEAR_STAMINA_COST},
                {CapabilityItem.WeaponCategories.GREATSWORD, EpicFightStaminaInteractionsConfig.GREATSWORD_STAMINA_COST},
                {CapabilityItem.WeaponCategories.DAGGER, EpicFightStaminaInteractionsConfig.DAGGER_STAMINA_COST},
                {CapabilityItem.WeaponCategories.TRIDENT, EpicFightStaminaInteractionsConfig.TRIDENT_STAMINA_COST},
                {CapabilityItem.WeaponCategories.UCHIGATANA, EpicFightStaminaInteractionsConfig.UCHIGATANA_STAMINA_COST},
                {CapabilityItem.WeaponCategories.TACHI, EpicFightStaminaInteractionsConfig.TACHI_STAMINA_COST},
                {CapabilityItem.WeaponCategories.LONGSWORD, EpicFightStaminaInteractionsConfig.LONGSWORD_STAMINA_COST},
                {CapabilityItem.WeaponCategories.HOE, EpicFightStaminaInteractionsConfig.HOE_STAMINA_COST},
                {CapabilityItem.WeaponCategories.PICKAXE, EpicFightStaminaInteractionsConfig.PICKAXE_STAMINA_COST},
                {CapabilityItem.WeaponCategories.SHOVEL, EpicFightStaminaInteractionsConfig.SHOVEL_STAMINA_COST},
        };

        for(Object[] weapon : weaponData) {
            CapabilityItem.WeaponCategories category = (CapabilityItem.WeaponCategories) weapon[0];
            Double staminaCost = (Double) weapon[1];
            weaponStaminaCosts.put(category, staminaCost.floatValue());
        }
    }

    private static final float BOW_STAMINA_COST = EpicFightStaminaInteractionsConfig.BOW_STAMINA_COST.get().floatValue();
    private static final float CROSSBOW_STAMINA_COST = EpicFightStaminaInteractionsConfig.CROSSBOW_STAMINA_COST.get().floatValue();
    private static final float JUMP_STAMINA_COST = EpicFightStaminaInteractionsConfig.JUMP_STAMINA_COST.get().floatValue();
    private static final boolean isJumpCostEnabled = EpicFightStaminaInteractionsConfig.enableJumpStamina.get();
    private static final boolean isSprintCostEnabled = EpicFightStaminaInteractionsConfig.enableSprintStamina.get();

    @SubscribeEvent
    public static void onPlayerTick(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity instanceof Player player) {
            PlayerPatch<Player> playerPatch = EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);

            if (playerPatch != null) {
                float currentStamina = playerPatch.getStamina();

                if(player.isSprinting() && !player.isCreative() && isSprintCostEnabled) {
                    float sprintStaminaCost = EpicFightStaminaInteractionsConfig.SPRINT_STAMINA_COST.get().floatValue();
                    playerPatch.setStamina(Math.max(0.0F, currentStamina - sprintStaminaCost));
                    playerPatch.resetActionTick();
                }

                if(!player.isCreative() && isJumpCostEnabled && !player.onClimbable() && !player.isSwimming()) {
                   if (player.getDeltaMovement().y > 0.0F) {
                       playerPatch.setStamina(Math.max(0.0F, currentStamina - JUMP_STAMINA_COST));
                       playerPatch.resetActionTick();
                   }
                }

                if(!player.isCreative() && EpicFightStaminaInteractionsConfig.enableAttackStamina.get()) {
                    Item activeItem = player.getMainHandItem().getItem();
                    if(playerPatch.getStamina() <= 0.0F) {
                        if(activeItem == Items.BOW && player.isUsingItem()) {
                            player.stopUsingItem();
                        } else if (activeItem == Items.CROSSBOW && player.isUsingItem()) {
                            player.stopUsingItem();
                        }
                    } else {
                        if(activeItem == Items.BOW && player.isUsingItem()) {
                            playerPatch.setStamina(Math.max(0.0F, currentStamina - BOW_STAMINA_COST));
                            playerPatch.resetActionTick();
                        } else if (activeItem == Items.CROSSBOW && player.isUsingItem()) {
                            playerPatch.setStamina(Math.max(0.0F, currentStamina - CROSSBOW_STAMINA_COST));
                            playerPatch.resetActionTick();
                        }
                    }

                    playerPatch.getEventListener().addEventListener(PlayerEventListener.EventType.BASIC_ATTACK_EVENT, playerPatch.getOriginal().getUUID(), basicAttackEvent -> {
                        if (!player.isCreative() && EpicFightStaminaInteractionsConfig.enableAttackStamina.get() && playerPatch.isBattleMode()) {
                            CapabilityItem weaponCapability = playerPatch.getHoldingItemCapability(InteractionHand.MAIN_HAND);
                            if (weaponCapability != null) {
                                double weaponDamage = player.getAttribute(Attributes.ATTACK_DAMAGE).getValue();
                                WeaponCategory weaponCategory = weaponCapability.getWeaponCategory();
                                if(weaponCategory instanceof CapabilityItem.WeaponCategories weaponType) {
                                    float weaponStaminaCost = weaponStaminaCosts.getOrDefault(weaponType, 1.0F);
                                    float attackStaminaCost = (float)(weaponDamage * 0.54D + weaponStaminaCost);
                                    float newStamina = Math.max(0.0F, currentStamina - attackStaminaCost);
                                    playerPatch.setStamina(newStamina);
                                }
                            }
                        }
                    });



                    playerPatch.getEventListener().addEventListener(PlayerEventListener.EventType.ANIMATION_BEGIN_EVENT, playerPatch.getOriginal().getUUID(), animationBeginEvent ->  {
                        if(playerPatch.isBattleMode() && EpicFightStaminaInteractionsConfig.enableDebugMode.get() && Minecraft.getInstance().isSingleplayer()) {
                            String animationName = animationBeginEvent.getAnimation().getLocation().getPath();
                            PlayerChatMessage chatMessage = PlayerChatMessage.unsigned(player.getUUID(), animationName);
                            player.createCommandSourceStack().sendChatMessage(new OutgoingChatMessage.Player(chatMessage), false, ChatType.bind(ChatType.CHAT, player));
                        }
                    });
                }
            }
        }
    }

    @SubscribeEvent
    public static void onEntityShieldBlock(ShieldBlockEvent event) {
        if(EpicFightStaminaInteractionsConfig.enableShieldStamina.get()) {
            LivingEntity livingEntity = event.getEntity();
            if (livingEntity instanceof Player player) {
                PlayerPatch<Player> playerPatch = EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);

                if (playerPatch != null) {
                    float currentStamina = playerPatch.getStamina();
                    if(currentStamina <= 0.0F) {
                        player.stopUsingItem();
                    } else {
                        float blockedDamage = event.getBlockedDamage();
                        float staminaCost = blockedDamage * EpicFightStaminaInteractionsConfig.SHIELD_STAMINA_MULTIPLIER.get().floatValue();
                        float newStamina = Math.max(0.0F, playerPatch.getStamina() - staminaCost);
                        playerPatch.setStamina(newStamina);
                        playerPatch.resetActionTick();
                    }
                }
            }
        }
    }
}
