package com.BAVDE.atium_mod.client;

import net.minecraftforge.client.gui.IIngameOverlay;

public class ModOverlays {

    public static final IIngameOverlay HUD_ARMOUR_BARS = ((gui, poseStack, partialTick, width, height) -> {
        gui.setupOverlayRenderState(true, false);
        AtiumModArmourBars.renderArmourBars(poseStack, gui);
    });
}
