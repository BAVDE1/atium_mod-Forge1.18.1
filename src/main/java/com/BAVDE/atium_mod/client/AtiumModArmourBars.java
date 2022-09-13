package com.BAVDE.atium_mod.client;

import com.BAVDE.atium_mod.AtiumMod;
import com.BAVDE.atium_mod.item.ModItems;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class AtiumModArmourBars {
    private static final ResourceLocation ARMOUR_ICONS = new ResourceLocation(AtiumMod.MOD_ID, "textures/gui/armour_icons/armour_icons.png");
    private static float helmetTimeStamp = 0F;
    private static float chestplateTimeStamp = 0F;
    private static float leggingsTimeStamp = 0F;
    private static float bootsTimeStamp = 0F;

    public static void renderArmourBars(PoseStack poseStack, Gui gui) {
        Player player = Minecraft.getInstance().player;

        if (player != null && !player.isSpectator()) {
            float currentTime = Util.getMillis();
            float shadowAlpha = 0.5F;
            Window window = Minecraft.getInstance().getWindow();
            poseStack.pushPose();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();

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
            int bgHelmetX = 11;
            int bgHelmetY = screenH - 40;
            int fgHelmetX = 12;
            int fgHelmetY = screenH - 39;
            int icHelmetX = 4;
            int icHelmetY = screenH - 41;

            int bgChestplateX = 11;
            int bgChestplateY = screenH - 30;
            int fgChestplateX = 12;
            int fgChestplateY = screenH - 29;
            int icChestplateX = 4;
            int icChestplateY = screenH - 32;

            int bgLeggingsX = 11;
            int bgLeggingsY = screenH - 19;
            int fgLeggingsX = 12;
            int fgLeggingsY = screenH - 18;
            int icLeggingsX = 5;
            int icLeggingsY = screenH - 21;

            int bgBootsX = 11;
            int bgBootsY = screenH - 9;
            int fgBootsX = 12;
            int fgBootsY = screenH - 8;
            int icBootsX = 4;
            int icBootsY = screenH - 10;

            //FOR REFERENCE: U = X(W), V = Y(H)

            if (hasValidPieceEquipped) {
                //helmet
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0f, hasValidHelmet ? 1.0F : shadowAlpha);
                float helmetCooldown = player.getCooldowns().getCooldownPercent(helmetItem.getItem(), Minecraft.getInstance().getFrameTime());

                gui.blit(poseStack, bgHelmetX, bgHelmetY, 0, 0, bgBarX, bgBarY); //background bar
                gui.blit(poseStack, fgHelmetX, fgHelmetY, fgBarOffsetX, fgBarOffsetY, hasValidHelmet ? (int) (fgBarX - (helmetCooldown * 10)) : fgBarX, fgBarY); //foreground bar cooldown
                if (helmetItem.getTag() != null && helmetItem.getTag().contains("atium_mod.green_tick")) {
                    helmetTimeStamp = currentTime;
                }
                float helmetCol = Math.max(0.0F, Math.min(1.0F, ((currentTime - helmetTimeStamp) / 1000)));
                RenderSystem.setShaderColor(helmetCol, 1.0F, helmetCol, hasValidHelmet ? 1.0F : shadowAlpha);
                gui.blit(poseStack, icHelmetX, icHelmetY, 0, helmetOffsetY, helmetX, helmetY); //icon

                //chestplate
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0f, hasValidChestplate ? 1.0F : shadowAlpha);
                float chestplateCooldown = player.getCooldowns().getCooldownPercent(chestplateItem.getItem(), Minecraft.getInstance().getFrameTime());

                gui.blit(poseStack, bgChestplateX, bgChestplateY, 0, 0, bgBarX, bgBarY); //background bar
                gui.blit(poseStack, fgChestplateX, fgChestplateY, fgBarOffsetX, fgBarOffsetY, hasValidChestplate ? (int) (fgBarX - (chestplateCooldown * 10)) : fgBarX, fgBarY); //foreground bar
                if (chestplateItem.getTag() != null && chestplateItem.getTag().contains("atium_mod.green_tick")) {
                    chestplateTimeStamp = currentTime;
                }
                float chestplateCol = Math.max(0.0F, Math.min(1.0F, ((currentTime - chestplateTimeStamp) / 1000)));
                RenderSystem.setShaderColor(chestplateCol, 1.0F, chestplateCol, hasValidChestplate ? 1.0F : shadowAlpha);
                gui.blit(poseStack, icChestplateX, icChestplateY, 0, chestplateOffsetY, chestplateX, chestplateY); //icon

                //leggings
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0f, hasValidLeggings ? 1.0F : shadowAlpha);
                float leggingsCooldown = player.getCooldowns().getCooldownPercent(leggingsItem.getItem(), Minecraft.getInstance().getFrameTime());

                gui.blit(poseStack, bgLeggingsX, bgLeggingsY, 0, 0, bgBarX, bgBarY); //background bar
                gui.blit(poseStack, fgLeggingsX, fgLeggingsY, fgBarOffsetX, fgBarOffsetY, hasValidLeggings ? (int) (fgBarX - (leggingsCooldown * 10)) : fgBarX, fgBarY); //foreground bar
                if (leggingsItem.getTag() != null && leggingsItem.getTag().contains("atium_mod.green_tick")) {
                    leggingsTimeStamp = currentTime;
                }
                float leggingsCol = Math.max(0.0F, Math.min(1.0F, ((currentTime - leggingsTimeStamp) / 1000)));
                RenderSystem.setShaderColor(leggingsCol, 1.0F, leggingsCol, hasValidLeggings ? 1.0F : shadowAlpha);
                gui.blit(poseStack, icLeggingsX, icLeggingsY, 0, leggingsOffsetY, leggingsX, leggingsY); //icon

                //boots
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0f, hasValidBoots ? 1.0F : shadowAlpha);
                float bootsCooldown = player.getCooldowns().getCooldownPercent(bootsItem.getItem(), Minecraft.getInstance().getFrameTime());

                gui.blit(poseStack, bgBootsX, bgBootsY, 0, 0, bgBarX, bgBarY); //background bar
                gui.blit(poseStack, fgBootsX, fgBootsY, fgBarOffsetX, fgBarOffsetY, hasValidBoots ? (int) (fgBarX - (bootsCooldown * 10)) : fgBarX, fgBarY); //foreground bar
                if (bootsItem.getTag() != null && bootsItem.getTag().contains("atium_mod.green_tick")) {
                    bootsTimeStamp = currentTime;
                }
                float bootsCol = Math.max(0.0F, Math.min(1.0F, ((currentTime - bootsTimeStamp) / 1000)));
                RenderSystem.setShaderColor(bootsCol, 1.0F, bootsCol, hasValidBoots ? 1.0F : shadowAlpha);
                gui.blit(poseStack, icBootsX, icBootsY, 0, bootsOffsetY, bootsX, bootsY); //icon
            }
            RenderSystem.disableBlend();
            poseStack.popPose();
        }
    }
}
