package com.BAVDE.atium_mod.item.custom;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CoinPouchDiamond extends PouchItem {
    public CoinPouchDiamond(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public double getMaxStr() {
        return 2.2;
    }

    @Override
    protected double getChargeDivision() {
        return 7.8;
    }

    @Override
    protected double getConsumeChance() {
        return 0.8;
    }

    @Override
    protected int getCooldownTicks() {
        return 18;
    }

    @Override
    public Rarity getRarity(ItemStack pStack) {
        return Rarity.UNCOMMON;
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
            //item desc
            pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.coin_pouch_generic.tooltip.ctrl"));
            //line break
            pTooltipComponents.add(new TextComponent(" "));
            //item chances
            pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.coin_pouch_diamond.tooltip.consume_chance"));
            pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.coin_pouch_diamond.tooltip.strength_multiplier"));
            pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.coin_pouch_diamond.tooltip.cooldown_ticks"));
        } else {
            pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.tooltip.more_info_ctrl"));
        }
        //line break
        pTooltipComponents.add(new TextComponent(" "));
        //iron nugget count
        pTooltipComponents.add(new TextComponent("Iron nuggets: " + count + "/" + pStack.getMaxDamage()));
    }
}