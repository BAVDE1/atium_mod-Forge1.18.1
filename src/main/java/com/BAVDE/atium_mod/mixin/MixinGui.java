package com.BAVDE.atium_mod.mixin;

import com.BAVDE.atium_mod.client.AtiumModClient;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Gui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class MixinGui {

    @Inject(at = @At("RETURN"), method = "renderCrosshair")
    private void afterRenderCrosshair(PoseStack poseStack, CallbackInfo ci) {
        AtiumModClient.renderArmourBars(poseStack, (Gui) (Object) this);
    }
}
