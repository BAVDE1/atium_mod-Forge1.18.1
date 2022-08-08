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

        if (player != null && !player.isSpectator()) {
            Window window = Minecraft.getInstance().getWindow();
            poseStack.pushPose();
            RenderSystem.disableBlend();

            //set texture
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, ARMOUR_ICONS);

            //armour items
            ItemStack helmetItem = player.getItemBySlot(EquipmentSlot.HEAD);
            boolean hasValidHelmet = helmetItem.getItem() == ModItems.ATIUM_HELMET.get();
            ItemStack chestplateItem = player.getItemBySlot(EquipmentSlot.CHEST);
            boolean hasValidChestplate = chestplateItem.getItem() == ModItems.ATIUM_CHESTPLATE.get();
            ItemStack leggingsItem = player.getItemBySlot(EquipmentSlot.LEGS);
            boolean hasValidLeggings = leggingsItem.getItem() == ModItems.ATIUM_LEGGINGS.get();
            ItemStack bootsItem = player.getItemBySlot(EquipmentSlot.FEET);
            boolean hasValidBoots = bootsItem.getItem() == ModItems.ATIUM_BOOTS.get();

            boolean hasValidPieceEquipped = hasValidHelmet || hasValidChestplate || hasValidLeggings || hasValidBoots;

            int screenW = window.getGuiScaledWidth();
            int screenH = window.getGuiScaledHeight();

            //permanent
            int bgBarX = 12;
            int bgBarY = 5;
            int fgBarX = 10;
            int fgBarY = 3;
            int fgBarOffsetY = 5;
            int fgBarOffsetX = 1;

            int helmetX = 8;
            int helmetY = 7;
            int helmetOffsetY = 11;

            int chestplateX = 9;
            int chestplateY = 9;
            int chestplateOffsetY = 19;

            int leggingsX = 7;
            int leggingsY = 9;
            int leggingsOffsetY = 29;

            int bootsX = 9;
            int bootsY = 7;
            int bootsOffsetY = 39;

            //changeable
            int bgHelmetX = screenW / 2 - 108;
            int bgHelmetY = screenH - 19;
            int fgHelmetX = screenW / 2 - 107;
            int fgHelmetY = screenH - 18;
            int icHelmetX = screenW / 2 - 115;
            int icHelmetY = screenH - 20;

            int bgChestplateX = screenW / 2 - 108;
            int bgChestplateY = screenH - 9;
            int fgChestplateX = screenW / 2 - 107;
            int fgChestplateY = screenH - 8;
            int icChestplateX = screenW / 2 - 115;
            int icChestplateY = screenH - 11;

            int bgLeggingsX = screenW / 2 + 96;
            int bgLeggingsY = screenH - 19;
            int fgLeggingsX = screenW / 2 + 97;
            int fgLeggingsY = screenH - 18;
            int icLeggingsX = screenW / 2 + 107;
            int icLeggingsY = screenH - 21;

            int bgBootsX = screenW / 2 + 96;
            int bgBootsY = screenH - 9;
            int fgBootsX = screenW / 2 + 97;
            int fgBootsY = screenH - 8;
            int icBootsX = screenW / 2 + 106;
            int icBootsY = screenH - 10;

            //put config position changes here

            if ("lol" == "lol") {
                bgHelmetX = screenW / 2 - 108;
                bgHelmetY = screenH - 19;
                fgHelmetX = screenW / 2 - 107;
                fgHelmetY = screenH - 18;
                icHelmetX = screenW / 2 - 115;
                icHelmetY = screenH - 20;

                bgChestplateX = screenW / 2 - 108;
                bgChestplateY = screenH - 9;
                fgChestplateX = screenW / 2 - 107;
                fgChestplateY = screenH - 8;
                icChestplateX = screenW / 2 - 115;
                icChestplateY = screenH - 11;

                bgLeggingsX = screenW / 2 + 96;
                bgLeggingsY = screenH - 19;
                fgLeggingsX = screenW / 2 + 97;
                fgLeggingsY = screenH - 18;
                icLeggingsX = screenW / 2 + 107;
                icLeggingsY = screenH - 21;

                bgBootsX = screenW / 2 + 96;
                bgBootsY = screenH - 9;
                fgBootsX = screenW / 2 + 97;
                fgBootsY = screenH - 8;
                icBootsX = screenW / 2 + 106;
                icBootsY = screenH - 10;
            }

            //FOR REFERENCE: U = X(W), V = Y(H)

            if (hasValidHelmet) {
                float cooldown = player.getCooldowns().getCooldownPercent(helmetItem.getItem(), Minecraft.getInstance().getFrameTime());

                gui.blit(poseStack, bgHelmetX, bgHelmetY, 0, 0, bgBarX, bgBarY); //background bar
                gui.blit(poseStack, fgHelmetX, fgHelmetY, fgBarOffsetX, fgBarOffsetY, (int) (fgBarX - (cooldown * 10)), fgBarY); //foreground bar cooldown
                gui.blit(poseStack, icHelmetX, icHelmetY, 0, helmetOffsetY, helmetX, helmetY); //icon
            } else if (hasValidPieceEquipped) {
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 0.5F);
                gui.blit(poseStack, bgHelmetX, bgHelmetY, 0, 0, bgBarX, bgBarY); //shadow background bar
                gui.blit(poseStack, fgHelmetX, fgHelmetY, fgBarOffsetX, fgBarOffsetY, fgBarX, fgBarY); //shadow foreground bar cooldown
                gui.blit(poseStack, icHelmetX, icHelmetY, 0, helmetOffsetY, helmetX, helmetY); //shadow icon
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

            }

            if (hasValidChestplate) {
                gui.blit(poseStack, bgChestplateX, bgChestplateY, 0, 0, bgBarX, bgBarY); //background bar
                gui.blit(poseStack, fgChestplateX, fgChestplateY, fgBarOffsetX, fgBarOffsetY, fgBarX, fgBarY); //foreground bar
                gui.blit(poseStack, icChestplateX, icChestplateY, 0, chestplateOffsetY, chestplateX, chestplateY); //icon
            } else if (hasValidPieceEquipped) {
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 0.5F);
                gui.blit(poseStack, bgChestplateX, bgChestplateY, 0, 0, bgBarX, bgBarY); //shadow background bar
                gui.blit(poseStack, fgChestplateX, fgChestplateY, fgBarOffsetX, fgBarOffsetY, fgBarX, fgBarY); //shadow foreground bar
                gui.blit(poseStack, icChestplateX, icChestplateY, 0, chestplateOffsetY, chestplateX, chestplateY); //shadow icon
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            }

            if (hasValidLeggings) {
                gui.blit(poseStack, bgLeggingsX, bgLeggingsY, 0, 0, bgBarX, bgBarY); //background bar
                gui.blit(poseStack, fgLeggingsX, fgLeggingsY, fgBarOffsetX, fgBarOffsetY, fgBarX, fgBarY); //foreground bar
                gui.blit(poseStack, icLeggingsX, icLeggingsY, 0, leggingsOffsetY, leggingsX, leggingsY); //icon
            } else if (hasValidPieceEquipped) {
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 0.5F);
                gui.blit(poseStack, bgLeggingsX, bgLeggingsY, 0, 0, bgBarX, bgBarY); //shadow background bar
                gui.blit(poseStack, fgLeggingsX, fgLeggingsY, fgBarOffsetX, fgBarOffsetY, fgBarX, fgBarY); //shadow foreground bar
                gui.blit(poseStack, icLeggingsX, icLeggingsY, 0, leggingsOffsetY, leggingsX, leggingsY); //shadow icon
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            }

            if (hasValidBoots) {
                gui.blit(poseStack, bgBootsX, bgBootsY, 0, 0, bgBarX, bgBarY); //background bar
                gui.blit(poseStack, fgBootsX, fgBootsY, fgBarOffsetX, fgBarOffsetY, fgBarX, fgBarY); //foreground bar
                gui.blit(poseStack, icBootsX, icBootsY, 0, bootsOffsetY, bootsX, bootsY); //icon
            } else if (hasValidPieceEquipped) {
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 0.5F);
                gui.blit(poseStack, bgBootsX, bgBootsY, 0, 0, bgBarX, bgBarY); //shadow background bar
                gui.blit(poseStack, fgBootsX, fgBootsY, fgBarOffsetX, fgBarOffsetY, fgBarX, fgBarY); //shadow foreground bar
                gui.blit(poseStack, icBootsX, icBootsY, 0, bootsOffsetY, bootsX, bootsY); //shadow icon
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            }
            poseStack.popPose();
        }
    }
}
