package com.BAVDE.atium_mod.item.custom;

import com.BAVDE.atium_mod.entity.projectile.IronCoinProjectile;
import com.BAVDE.atium_mod.item.ModItems;
import com.BAVDE.atium_mod.sound.ModSounds;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class CoinPouch extends Item {
    public CoinPouch(Properties pProperties) {
        super(pProperties);
    }

    /**
     * Pouch Projectile
     */

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player player, InteractionHand pUsedHand) {
        ItemStack pouchItem = player.getItemInHand(pUsedHand);
        ItemStack leggings = getPantsItem(player);

        if (!player.getCooldowns().isOnCooldown(pouchItem.getItem())) {
            if (pouchItem.getTag().contains("atium_mod.coins")) {
                if (leggings.getItem() == ModItems.ATIUM_LEGGINGS.get()) {
                    if (hasMetalTag(leggings)) {
                        //iron or steel pants
                        if (getMetalTag(leggings) == 1 || getMetalTag(leggings) == 2) {
                            player.startUsingItem(pUsedHand);
                            return InteractionResultHolder.consume(pouchItem);
                        }
                    }
                }
            }
        }
        return super.use(pLevel, player, pUsedHand);
    }

    @Override
    public void releaseUsing(ItemStack pouchItem, Level level, LivingEntity player, int pTimeCharged) {
        ItemStack leggings = getPantsItem((Player) player);
        int cooldownTicks = 20;
        int charge = getUseDuration(pouchItem) - pTimeCharged;

        //if player has used item for over 5 ticks
        if ((getUseDuration(pouchItem) - pTimeCharged) > 5) {
            if (leggings.getItem() == ModItems.ATIUM_LEGGINGS.get()) {
                if (hasMetalTag(leggings)) {
                    //iron
                    if (getMetalTag(leggings) == 1) {
                        //spawn projectile
                        ironProjectileSpawn(level, (Player) player, pTimeCharged);

                        playRemoveOneSound(player);
                        decreaseCoins(pouchItem, (Player) player);
                        ((Player) player).getCooldowns().addCooldown(pouchItem.getItem(), cooldownTicks);
                    }
                }
                //steel
                if (getMetalTag(leggings) == 2) {
                    //push
                    steelPush((Player) player, charge);

                    playPushSound(player);
                    playRemoveOneSound(player);
                    decreaseCoins(pouchItem, (Player) player);
                    ((Player) player).getCooldowns().addCooldown(pouchItem.getItem(), cooldownTicks);
                }
            }
        }

        super.releaseUsing(pouchItem, level, player, pTimeCharged);
    }

    private void ironProjectileSpawn(Level level, Player player, int pTimeCharged) {
        if (!level.isClientSide) {
            IronCoinProjectile ironCoinProjectile = new IronCoinProjectile(player, level);
            ironCoinProjectile.setItem(Items.IRON_NUGGET.getDefaultInstance());
            ironCoinProjectile.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
            level.addFreshEntity(ironCoinProjectile);
        }
    }

    private void steelPush(Player player, int charge) {
        double max = 2;
        double str = Math.min(charge / 8D, max);
        Vec3 look = player.getLookAngle().normalize();

        player.push(-look.x * str, -look.y * str, -look.z * str);

        //debugging purposes
        if (player.level.isClientSide) {
            System.out.println("===============================");
            System.out.println("str: " + str);
        }
    }

    /**
     * Pouch Storage
     */

    @Override
    public boolean overrideStackedOnOther(ItemStack pouchItem, Slot pSlot, ClickAction pAction, Player pPlayer) {
        if (pAction != ClickAction.SECONDARY) {
            return false;
        } else {
            ItemStack pOther = pSlot.getItem();
            if (!pOther.isEmpty()) {
                if (pOther.getItem() == Items.IRON_NUGGET) {
                    addCoinStack(pouchItem, pOther, pPlayer);
                    return true;
                } else return false;
            } else return false;
        }
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack pouchItem, ItemStack pOther, Slot pSlot, ClickAction pAction, Player pPlayer, SlotAccess pAccess) {
        if (pAction == ClickAction.SECONDARY && pSlot.allowModification(pPlayer)) {
            if (pOther.isEmpty()) {
                if (pouchItem.getTag().contains("atium_mod.coins")) {
                    //removes all items from pouch
                    removeAllCoins(pouchItem, pPlayer).ifPresent(pAccess::set);
                } else return false;
            } else if (pOther.getItem() == Items.IRON_NUGGET) {
                //adds items into pouch
                addCoinStack(pouchItem, pOther, pPlayer);
            } else return false;

            return true;
        } else return false;
    }

    @Override
    public boolean isBarVisible(ItemStack pStack) {
        return true;
    }

    //uses durability bar to show how many coins item contains
    @Override
    public int getBarWidth(ItemStack pStack) {
        if (pStack.getTag().contains("atium_mod.coins")) {
            int currentCoins = pStack.getTag().getInt("atium_mod.coins");
            return Math.min(1 + (12 * currentCoins / pStack.getMaxDamage()), 13);
        } else {
            return 0;
        }
    }

    //add item to pouch
    private void addCoinStack(ItemStack pouchStack, ItemStack additionStack, Player player) {
        int spaceLeft = pouchStack.getMaxDamage();
        int stackSize = additionStack.getCount();

        if (pouchStack.getTag().contains("atium_mod.coins")) {
            int currentCoins = pouchStack.getTag().getInt("atium_mod.coins");
            spaceLeft = pouchStack.getMaxDamage() - currentCoins;
            int addAmountAllowed = Math.min(spaceLeft, stackSize);

            //if there is space in the pouch
            if (!(currentCoins >= pouchStack.getMaxDamage())) {
                additionStack.shrink(addAmountAllowed);
                pouchStack.getTag().putInt("atium_mod.coins", currentCoins + addAmountAllowed);
                this.playInsertSound(player);
                this.playCoinsClinkSound(player);
            }
        } else {
            int addAmountAllowed = Math.min(spaceLeft, stackSize);

            additionStack.shrink(addAmountAllowed);
            pouchStack.getTag().putInt("atium_mod.coins", addAmountAllowed);
            this.playInsertSound(player);
            if (stackSize != 1) {
                this.playCoinsClinkSound(player);
            }
        }
    }

    //removes all items from pouch
    private Optional<ItemStack> removeAllCoins(ItemStack pouchItem, Player player) {
        int currentCoins = pouchItem.getTag().getInt("atium_mod.coins");
        ItemStack returnItemStack = Items.IRON_NUGGET.getDefaultInstance();

        //remove all items & clear tag
        pouchItem.getTag().remove("atium_mod.coins");
        returnItemStack.setCount(currentCoins);

        this.playRemoveOneSound(player);
        return Optional.of(returnItemStack);
    }

    /**
     * Utils
     */

    private void playRemoveOneSound(Entity entity) {
        entity.playSound(SoundEvents.BUNDLE_REMOVE_ONE, 0.8F, 0.8F + entity.getLevel().getRandom().nextFloat() * 0.4F);
    }

    private void playInsertSound(Entity entity) {
        entity.playSound(SoundEvents.BUNDLE_INSERT, 0.8F, 0.8F + entity.getLevel().getRandom().nextFloat() * 0.4F);
    }

    //run debug to find good volume & pitch
    private void playCoinsClinkSound(Entity entity) {
        entity.playSound(ModSounds.COINS_CLANK.get(), 0.7F, 0.9F + entity.getLevel().getRandom().nextFloat() * 0.4F);
    }

    //plays push sound for steel push
    private void playPushSound(Entity entity) {
        entity.playSound(SoundEvents.ARMOR_EQUIP_ELYTRA, 2.0F, 1.0F);
    }

    //returns the item in players pants slot
    private ItemStack getPantsItem(Player player) {
        return player.getItemBySlot(EquipmentSlot.LEGS);
    }

    //checks if itemstack has metal tag (returns boolean)
    private static boolean hasMetalTag(ItemStack itemStack) {
        return itemStack.getTag().contains("atium_mod.metal");
    }

    //returns the metal tag of itemStack (returns int)
    private static int getMetalTag(ItemStack itemStack) {
        if (hasMetalTag(itemStack)) {
            return itemStack.getTag().getInt("atium_mod.metal");
        }
        //returns 0 if doesn't have metal tag
        return 0;
    }

    //sets use duration of item (when item starts being used counts down from getUseDuration every tick)
    @Override
    public int getUseDuration(ItemStack pStack) {
        return 72000;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.BOW;
    }

    //decrease amount of coins in pouch by 1 (used on release of item)
    private void decreaseCoins(ItemStack itemStack, Player player) {
        //if not in creative
        if (!player.getAbilities().instabuild) {
            if (itemStack.getTag().contains("atium_mod.coins")) {
                int coins = itemStack.getTag().getInt("atium_mod.coins");

                if (!(coins <= 1)) {
                    itemStack.getTag().putInt("atium_mod.coins", --coins);
                } else {
                    itemStack.getTag().remove("atium_mod.coins");
                }
            }
        }
    }

    //displays number of coins in pouch
    //add: More Info [CTRL]
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        int count = 0;
        if (pStack.getTag().contains("atium_mod.coins")) {
            int currentCoins = pStack.getTag().getInt("atium_mod.coins");
            count = currentCoins;
        }

        if (Screen.hasControlDown()) {
            //item chances
            pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.coin_pouch1.tooltip.consume_chance"));
            pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.coin_pouch1.tooltip.strength_multiplier"));
            //line break
            pTooltipComponents.add(new TextComponent(" "));
            //item desc
            pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.coin_pouch.tooltip.ctrl"));
        } else {
            pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.tooltip.more_info_ctrl"));
        }
        //line break
        pTooltipComponents.add(new TextComponent(" "));
        //iron nugget count
        pTooltipComponents.add(new TextComponent("Iron nuggets: " + count + "/" + pStack.getMaxDamage()));
    }
}