package com.BAVDE.atium_mod.item.custom;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class AtiumAxe extends AxeItem {
    public AtiumAxe(Tier pTier, float pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    public Rarity getRarity(ItemStack pStack) {
        if (pStack.getTag().contains("atium_mod.metal")) {
            return Rarity.UNCOMMON;
        } else {
            return super.getRarity(pStack);
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if (pStack.getTag().contains("atium_mod.metal")) {
            int currentMetal = pStack.getTag().getInt("atium_mod.metal");
            if (Screen.hasControlDown()) {
                switch (currentMetal) { //1=iron, 2=steel, 3=tin, 4=pewter, 5=brass, 6=zinc, 7=copper, 8=bronze, 9=gold
                    case 1 -> pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.atium_axe.tooltip.iron.ctrl"));
                    case 2 -> pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.atium_axe.tooltip.steel.ctrl"));
                    case 3 -> pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.atium_axe.tooltip.tin.ctrl"));
                    case 4 -> pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.atium_axe.tooltip.pewter.ctrl"));
                    case 5 -> pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.atium_axe.tooltip.brass.ctrl"));
                    case 6 -> pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.atium_axe.tooltip.zinc.ctrl"));
                    case 9 -> pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.atium_axe.tooltip.gold.ctrl"));
                }
            } else {
                switch (currentMetal) { //1=iron, 2=steel, 3=tin, 4=pewter, 5=brass, 6=zinc, 7=copper, 8=bronze, 9=gold
                    case 1 -> pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.tooltip.iron"));
                    case 2 -> pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.tooltip.steel"));
                    case 3 -> pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.tooltip.tin"));
                    case 4 -> pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.tooltip.pewter"));
                    case 5 -> pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.tooltip.brass"));
                    case 6 -> pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.tooltip.zinc"));
                    case 9 -> pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.tooltip.gold"));
                }
            }
        }
        if (pStack.getTag().contains("atium_mod.copper_cloud")) {
            if (pStack.getTag().getInt("atium_mod.copper_cloud") == 1) {
                pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.has_copper_cloud"));
            }
        }
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
