package com.BAVDE.atium_mod.client;

import com.BAVDE.atium_mod.block.ModBlocks;
import com.BAVDE.atium_mod.screen.InfusingTableScreen;
import com.BAVDE.atium_mod.screen.ModMenuTypes;
import com.BAVDE.atium_mod.util.ModItemProperties;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.client.gui.OverlayRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static net.minecraftforge.client.gui.ForgeIngameGui.HOTBAR_ELEMENT;

public class ClientSetup {

    public static void renderTextureCutouts(final FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.SMALL_CRYSTALLIZED_ATIUM_BUD.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.MEDIUM_CRYSTALLIZED_ATIUM_BUD.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.LARGE_CRYSTALLIZED_ATIUM_BUD.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.CRYSTALLIZED_ATIUM_CLUSTER.get(), RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(ModBlocks.CRYSTALLINE_LEAVES.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.BUDDING_CRYSTALLINE_LEAVES.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.CRYSTALLINE_SAPLING.get(), RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(ModBlocks.CRYSTALLINE_DOOR.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.CRYSTALLINE_TRAPDOOR.get(), RenderType.cutout());
    }

    public static void renderHudOverlays(final FMLClientSetupEvent event) {
        OverlayRegistry.registerOverlayAbove(HOTBAR_ELEMENT, "Armour Bars", ModOverlays.HUD_ARMOUR_BARS);
    }

    public static void registerModGui(final FMLClientSetupEvent event) {
        MenuScreens.register(ModMenuTypes.INFUSING_TABLE_MENU.get(), InfusingTableScreen::new);
    }

    public static void registerItemProperties(final FMLClientSetupEvent event) {
        ModItemProperties.addCustomItemProperties();
    }
}
