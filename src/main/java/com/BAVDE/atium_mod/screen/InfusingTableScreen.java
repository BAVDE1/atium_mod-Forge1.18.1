package com.BAVDE.atium_mod.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.BAVDE.atium_mod.AtiumMod;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class InfusingTableScreen extends AbstractContainerScreen<InfusingTableMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(AtiumMod.MOD_ID, "textures/gui/infusing_table/infusing_table_gui.png");

    public InfusingTableScreen(InfusingTableMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.imageWidth = 176;
        this.imageHeight = 220;
        this.titleLabelX = 47;
        this.titleLabelY = 3;
        this.inventoryLabelX = 1000000;
        this.inventoryLabelY = 1000000;
    }

    @Override
    protected void init() {
        super.init();
    }

    //renders the inventory texture
    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTicks, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        //is displayed
        this.blit(pPoseStack, leftPos, topPos, 0, 0, imageWidth, imageHeight);

        if (!menu.isSlot0Empty()) {
            blit(pPoseStack, leftPos + 79, topPos + 23, 79, 57, 16, 16);
        }

        renderMetalDesc(pPoseStack);
        renderAtiumInfusionDesc(pPoseStack);
    }

    protected void renderMetalDesc(PoseStack poseStack) {
        final ResourceLocation METAL_DESC = new ResourceLocation(AtiumMod.MOD_ID, "textures/gui/infusing_table/metal_desc.png");
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, METAL_DESC);
        int Xpos = leftPos + 9;
        int Ypos = topPos + 24;
        int height = 70;
        int width = 57;

        switch (menu.hasMetal()) { //1=iron, 2=steel, 3=tin, 4=pewter, 5=brass, 6=zinc, 7=copper, 8=bronze, 9=gold, 10=aluminium
            case 1 -> blit(poseStack, Xpos, Ypos, 0, 0, width, height);
            case 2 -> blit(poseStack, Xpos, Ypos, 57, 0, width, height);
            case 3 -> blit(poseStack, Xpos, Ypos, 114, 0, width, height);
            case 4 -> blit(poseStack, Xpos, Ypos, 171, 0, width, height);
            case 5 -> blit(poseStack, Xpos, Ypos, 0, 70, width, height);
            case 6 -> blit(poseStack, Xpos, Ypos, 57, 70, width, height);
            case 7 -> blit(poseStack, Xpos, Ypos, 114, 70, width, height);
            case 8 -> blit(poseStack, Xpos, Ypos, 171, 70, width, height);
            case 9 -> blit(poseStack, Xpos, Ypos, 0, 140, width, height);
            case 10 -> blit(poseStack, Xpos, Ypos, 57, 140, width, height);
        }
    }

    protected void renderAtiumInfusionDesc(PoseStack poseStack) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        int Xpos = leftPos + 109;
        int Ypos = topPos + 24;
        int height = 70;
        int width = 57;

        //1=sword, 2=pick, 3=axe, 4=shovel, 5=hoe, 6=helmet, 7=chestplate, 8=leggings, 9=boots
        switch (menu.hasAtiumItem()) {
            case 1: //sword
                final ResourceLocation SWORD_DESC = new ResourceLocation(AtiumMod.MOD_ID, "textures/gui/infusing_table/atium/sword_desc.png");
                RenderSystem.setShaderTexture(0, SWORD_DESC);
                switch (menu.hasMetal()) {
                    case 1 -> blit(poseStack, Xpos, Ypos, 0, 0, width, height);
                    case 2 -> blit(poseStack, Xpos, Ypos, 56, 0, width, height);
                    case 3 -> blit(poseStack, Xpos, Ypos, 112, 0, width, height);
                    case 4 -> blit(poseStack, Xpos, Ypos, 168, 0, width, height);
                    case 5 -> blit(poseStack, Xpos, Ypos, 0, 70, width, height);
                    case 6 -> blit(poseStack, Xpos, Ypos, 56, 70, width, height);
                    case 7 -> blit(poseStack, Xpos, Ypos, 112, 70, width, height);
                    case 8 -> blit(poseStack, Xpos, Ypos, 168, 70, width, height);
                    case 9 -> blit(poseStack, Xpos, Ypos, 0, 140, width, height);
                    case 10 -> blit(poseStack, Xpos, Ypos, 56, 140, width, height);
                }
                break;
            case 2: //pickaxe
                break;
            case 3: //axe
                break;
            case 4: //shovel
                break;
            case 5: //hoe
                break;
            case 6: //helmet
                final ResourceLocation HELMET_DESC = new ResourceLocation(AtiumMod.MOD_ID, "textures/gui/infusing_table/atium/helmet_desc.png");
                RenderSystem.setShaderTexture(0, HELMET_DESC);
                switch (menu.hasMetal()) {
                    case 1 -> blit(poseStack, Xpos, Ypos, 0, 0, width, height);
                    case 2 -> blit(poseStack, Xpos, Ypos, 56, 0, width, height);
                    case 3 -> blit(poseStack, Xpos, Ypos, 112, 0, width, height);
                    case 4 -> blit(poseStack, Xpos, Ypos, 168, 0, width, height);
                    case 5 -> blit(poseStack, Xpos, Ypos, 0, 70, width, height);
                    case 6 -> blit(poseStack, Xpos, Ypos, 56, 70, width, height);
                    case 7 -> blit(poseStack, Xpos, Ypos, 112, 70, width, height);
                    case 8 -> blit(poseStack, Xpos, Ypos, 168, 70, width, height);
                    case 9 -> blit(poseStack, Xpos, Ypos, 0, 140, width, height);
                    case 10 -> blit(poseStack, Xpos, Ypos, 56, 140, width, height);
                }
                break;
            case 7: //chestplate
                final ResourceLocation CHESTPLATE_DESC = new ResourceLocation(AtiumMod.MOD_ID, "textures/gui/infusing_table/atium/chestplate_desc.png");
                RenderSystem.setShaderTexture(0, CHESTPLATE_DESC);
                switch (menu.hasMetal()) {
                    case 1 -> blit(poseStack, Xpos, Ypos, 0, 0, width, height);
                    case 2 -> blit(poseStack, Xpos, Ypos, 56, 0, width, height);
                    case 3 -> blit(poseStack, Xpos, Ypos, 112, 0, width, height);
                    case 4 -> blit(poseStack, Xpos, Ypos, 168, 0, width, height);
                    case 5 -> blit(poseStack, Xpos, Ypos, 0, 70, width, height);
                    case 6 -> blit(poseStack, Xpos, Ypos, 56, 70, width, height);
                    case 7 -> blit(poseStack, Xpos, Ypos, 112, 70, width, height);
                    case 8 -> blit(poseStack, Xpos, Ypos, 168, 70, width, height);
                    case 9 -> blit(poseStack, Xpos, Ypos, 0, 140, width, height);
                    case 10 -> blit(poseStack, Xpos, Ypos, 56, 140, width, height);
                }
                break;
            case 8: //leggings
                break;
            case 9: //boots
                break;
        }
    }

    @Override
    public void render(PoseStack pPoseStack, int mouseX, int mouseY, float delta) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, mouseX, mouseY, delta);
        RenderSystem.disableBlend();
        renderTooltip(pPoseStack, mouseX, mouseY);
    }
}