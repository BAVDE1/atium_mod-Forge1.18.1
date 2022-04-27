package com.BAVDE.atium_mod.util;

import com.BAVDE.atium_mod.item.ModItems;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ModItemProperties {
    public static void addCustomItemProperties() {

        InfusableItem(ModItems.ATIUM_CHESTPLATE.get());
    }

    static void InfusableItem(Item item) {
        ItemProperties.register(item, new ResourceLocation("metal"), (itemStack, clientLevel, livingEntity, i) -> {
            Entity entity = (Entity)(livingEntity != null ? livingEntity : itemStack.getEntityRepresentation());
            int metal = 0;

            if (entity != null) {
                //boolean flag = livingEntity instanceof Player && ((Player)livingEntity).isLocalPlayer();
                metal = itemStack.getTag().getInt("atium_mod.metal");
            }
            return metal;
        });
    }
}