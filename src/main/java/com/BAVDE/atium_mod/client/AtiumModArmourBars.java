package com.BAVDE.atium_mod.client;

import com.BAVDE.atium_mod.AtiumMod;
import com.BAVDE.atium_mod.item.ModItems;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class AtiumModArmourBars {
    private static final ResourceLocation ARMOUR_ICONS = new ResourceLocation(AtiumMod.MOD_ID, "textures/gui/armour_icons/armour_icons.png");

    public static void renderArmourBars(PoseStack poseStack, Gui gui) {
        Player player = Minecraft.getInstance().player;

        if (player != null) {
            Window window = Minecraft.getInstance().getWindow();
            poseStack.pushPose();
            RenderSystem.disableBlend();

            //set texture
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, ARMOUR_ICONS);

            //armour items
            ItemStack helmetItem = player.getItemBySlot(EquipmentSlot.HEAD);
            ItemStack chestplateItem = player.getItemBySlot(EquipmentSlot.CHEST);
            ItemStack leggingsItem = player.getItemBySlot(EquipmentSlot.LEGS);
            ItemStack bootsItem = player.getItemBySlot(EquipmentSlot.FEET);

            int screenW = window.getGuiScaledWidth() / 2;
            int screenH = window.getGuiScaledHeight();

            int bgBarW = 12;
            int bgBarH = 5;

            gui.blit(poseStack, 10, 10, 0, 0, bgBarW, bgBarH); //background bar

            gui.getFont().draw(poseStack, "hihIhi", screenW, 10, 0xffffffff);





            int fgBarW = 10; //doesn't use foreground outline
            int fgBarH = 3;
            int fgBarOffsetY = 6;
            int fgBarOffsetX = 1;

            int helmetW = 8;
            int helmetH = 7;
            int helmetOffsetY = 12;

            int chestplateW = 9;
            int chestplateH = 9;
            int chestplateOffsetY = 20;

            int leggingsW = 7;
            int leggingsH = 9;
            int leggingsOffsetY = 30;

            int bootsW = 9;
            int bootsH = 7;
            int bootsOffsetY = 40;

            //U = X(W), V = Y(H)


            if (helmetItem.getItem() == ModItems.ATIUM_HELMET.get()) {
                //gui.blit(stack, midScreenW - 100, screenHeight - 22, fgBarOffsetX, fgBarOffsetY, fgBarW, fgBarH); //foreground bar
                //gui.blit(stack); //icon
            }

            if (chestplateItem.getItem() == ModItems.ATIUM_CHESTPLATE.get()) {

            }

            if (leggingsItem.getItem() == ModItems.ATIUM_LEGGINGS.get()) {

            }

            if (bootsItem.getItem() == ModItems.ATIUM_BOOTS.get()) {

            }
            poseStack.popPose();
        }
    }
}
