package com.BAVDE.atium_mod;

import com.BAVDE.atium_mod.block.ModBlocks;
import com.BAVDE.atium_mod.block.entity.ModBlockEntities;
import com.BAVDE.atium_mod.item.ModItems;
import com.BAVDE.atium_mod.painting.ModPaintings;
import com.BAVDE.atium_mod.recipie.ModRecipes;
import com.BAVDE.atium_mod.screen.InfusingTableScreen;
import com.BAVDE.atium_mod.screen.ModMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(AtiumMod.MOD_ID)
public class AtiumMod {

    public static final String MOD_ID = "atium_mod";

    // Directly reference a log4j logger
    public static final Logger LOGGER = LogManager.getLogger();

    public AtiumMod() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(eventBus);

        ModPaintings.register(eventBus);

        ModBlocks.register(eventBus);
        ModBlockEntities.register(eventBus);
        ModRecipes.register(eventBus);

        ModMenuTypes.register(eventBus);

        eventBus.addListener(this::setup);
        eventBus.addListener(this::setupClient);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void setupClient(final FMLClientSetupEvent event) {
        //cutout textures
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.SMALL_CRYSTALLIZED_ATIUM_BUD.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.MEDIUM_CRYSTALLIZED_ATIUM_BUD.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.LARGE_CRYSTALLIZED_ATIUM_BUD.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.CRYSTALLIZED_ATIUM_CLUSTER.get(), RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(ModBlocks.CRYSTALLINE_LEAVES.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.BUDDING_CRYSTALLINE_LEAVES.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.CRYSTALLINE_SAPLING.get(), RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(ModBlocks.CRYSTALLINE_DOOR.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.CRYSTALLINE_TRAPDOOR.get(), RenderType.cutout());

        //mod gui
        MenuScreens.register(ModMenuTypes.INFUSING_TABLE_MENU.get(), InfusingTableScreen::new);
    }

    private void setup(final FMLCommonSetupEvent event) {
        // some preinit code
        LOGGER.info("HELLO FROM PRE INIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }
}