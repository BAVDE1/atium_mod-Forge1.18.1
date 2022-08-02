package com.BAVDE.atium_mod.client;

import com.BAVDE.atium_mod.item.ModItems;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class AtiumModClient {
    private static final ResourceLocation ARMOUR_ICONS = new ResourceLocation("atium_mod", "textures/gui/armour_icons/armour_icons.png");

    public static void renderAtiumModArmourBars(PoseStack stack, Gui gui) {
        Player player = Minecraft.getInstance().player;

        if (player != null) {
            Window window = Minecraft.getInstance().getWindow();
            stack.pushPose();
            RenderSystem.disableBlend();

            //set texture
            AbstractTexture texture = Minecraft.getInstance().getTextureManager().getTexture(ARMOUR_ICONS);
            RenderSystem.setShaderTexture(0, texture.getId());

            //armour items
            ItemStack helmetItem = player.getItemBySlot(EquipmentSlot.HEAD);
            ItemStack chestplateItem = player.getItemBySlot(EquipmentSlot.CHEST);
            ItemStack leggingsItem = player.getItemBySlot(EquipmentSlot.LEGS);
            ItemStack bootsItem = player.getItemBySlot(EquipmentSlot.FEET);

            if (helmetItem.getItem() == ModItems.ATIUM_HELMET.get()) {

            }

            if (chestplateItem.getItem() == ModItems.ATIUM_CHESTPLATE.get()) {

            }

            if (leggingsItem.getItem() == ModItems.ATIUM_LEGGINGS.get()) {

            }

            if (bootsItem.getItem() == ModItems.ATIUM_BOOTS.get()) {

            }
        }
    }
}
