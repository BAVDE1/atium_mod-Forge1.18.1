package com.BAVDE.atium_mod.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.stringtemplate.v4.ST;

import javax.annotation.Nullable;
import java.util.List;

public class AtiumChestplate extends ArmorItem {
    public AtiumChestplate(ArmorMaterial pMaterial, EquipmentSlot pSlot, Properties pProperties) {
        super(pMaterial, pSlot, pProperties);
    }

    @Override
    public boolean isFoil(ItemStack pStack) {
        return (pStack.getTag().contains("atium_mod.metal") || pStack.isEnchanted());
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if(pStack.getTag().contains("atium_mod.metal")) {
            String currentMetal = pStack.getTag().getString("atium_mod.metal");
            pTooltipComponents.add(new TextComponent("trol"));
        }
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
