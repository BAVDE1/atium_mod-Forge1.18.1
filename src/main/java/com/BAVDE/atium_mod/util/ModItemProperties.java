package com.BAVDE.atium_mod.util;

import com.BAVDE.atium_mod.item.ModItems;
import com.BAVDE.atium_mod.item.ModTiers;
import com.BAVDE.atium_mod.item.custom.AtiumCompass;
import com.google.common.collect.Maps;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Map;

public class ModItemProperties {
    public static void addCustomItemProperties() {

        InfusableItem(ModItems.ATIUM_HELMET.get());
        InfusableItem(ModItems.ATIUM_CHESTPLATE.get());
        InfusableItem(ModItems.ATIUM_LEGGINGS.get());
        InfusableItem(ModItems.ATIUM_BOOTS.get());

        InfusableItem(ModItems.ATIUM_SWORD.get());
        InfusableItem(ModItems.ATIUM_PICKAXE.get());
        InfusableItem(ModItems.ATIUM_AXE.get());
        InfusableItem(ModItems.ATIUM_SHOVEL.get());
        InfusableItem(ModItems.ATIUM_HOE.get());

        ModCompassItem(ModItems.ATIUM_COMPASS.get());
    }

    static void InfusableItem(Item item) {
        ItemProperties.register(item, new ResourceLocation("metal"), (itemStack, clientLevel, livingEntity, i) -> {
            Entity entity = (Entity)(livingEntity != null ? livingEntity : itemStack.getEntityRepresentation());
            int metal = 0;

            if (entity != null) {
                metal = itemStack.getTag().getInt("atium_mod.metal");
            }
            return metal;
        });
    }

    static void ModCompassItem(Item item) {
        ItemProperties.register(item, new ResourceLocation("using"), (itemStack, clientLevel, livingEntity, i) -> {
            return livingEntity != null && AtiumCompass.isUsing(itemStack) ? 1.0F : 0.0F;
        });
    }
}