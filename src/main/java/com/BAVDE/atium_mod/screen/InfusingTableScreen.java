package com.BAVDE.atium_mod.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.BAVDE.atium_mod.AtiumMod;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
public class InfusingTableScreen extends AbstractContainerScreen<InfusingTableMenu2> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(AtiumMod.MOD_ID, "textures/gui/infusing_table/infusing_table_layout.png");

    public InfusingTableScreen(InfusingTableMenu2 pMenu, Inventory pPlayerInventory, Component pTitle) {
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

        renderMetalDesc(pPoseStack);
    }

    protected void renderMetalDesc(PoseStack pPoseStack){
        final ResourceLocation METAL_DESC = new ResourceLocation(AtiumMod.MOD_ID, "textures/gui/infusing_table/metal_desc.png");
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, METAL_DESC);
        int descPosX = leftPos + 9;
        int descPosY = topPos + 24;

        switch (menu.hasMetal()) { //1=iron, 2=steel, 3=tin, 4=pewter, 5=brass, 6=zinc, 7=copper, 8=bronze, 9=gold
            case 1 -> blit(pPoseStack, descPosX, descPosY, 0,   0,   57, 70);
            case 2 -> blit(pPoseStack, descPosX, descPosY, 57,  0,   57, 70);
            case 3 -> blit(pPoseStack, descPosX, descPosY, 114, 0,   57, 70);
            case 4 -> blit(pPoseStack, descPosX, descPosY, 171, 0,   57, 70);
            case 5 -> blit(pPoseStack, descPosX, descPosY, 0,   70,  57, 70);
            case 6 -> blit(pPoseStack, descPosX, descPosY, 57,  70,  57, 70);
            case 7 -> blit(pPoseStack, descPosX, descPosY, 114, 70,  57, 70);
            case 8 -> blit(pPoseStack, descPosX, descPosY, 171, 70,  57, 70);
            case 9 -> blit(pPoseStack, descPosX, descPosY, 0,   140, 57, 70);
        }
    }

    @Override
    public void render(PoseStack pPoseStack, int mouseX, int mouseY, float delta) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, mouseX, mouseY, delta);
        renderTooltip(pPoseStack, mouseX, mouseY);
    }
}