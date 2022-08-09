package com.BAVDE.atium_mod;

import com.BAVDE.atium_mod.block.ModBlocks;
import com.BAVDE.atium_mod.block.entity.ModBlockEntities;
import com.BAVDE.atium_mod.client.ClientSetup;
import com.BAVDE.atium_mod.config.AtiumModClientConfigs;
import com.BAVDE.atium_mod.effect.ModMobEffects;
import com.BAVDE.atium_mod.entity.ModEntityTypes;
import com.BAVDE.atium_mod.item.ModItems;
import com.BAVDE.atium_mod.painting.ModPaintings;
import com.BAVDE.atium_mod.particle.ModParticles;
import com.BAVDE.atium_mod.screen.ModMenuTypes;
import com.BAVDE.atium_mod.sound.ModSounds;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
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

        //registry
        ModItems.register(eventBus);
        ModPaintings.register(eventBus);
        ModBlocks.register(eventBus);
        ModBlockEntities.register(eventBus);
        ModEntityTypes.register(eventBus);
        ModMenuTypes.register(eventBus);
        //ModTreeDecoratorType.register(eventBus);
        ModParticles.register(eventBus);
        ModMobEffects.register(eventBus);
        ModSounds.register(eventBus);

        //ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, AtiumModClientConfigs.SPEC, "atium_mod-client.toml");

        eventBus.addListener(this::setup);
        eventBus.addListener(this::FMLClientSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void FMLClientSetup(final FMLClientSetupEvent event) {
        ClientSetup.renderTextureCutouts(event);
        ClientSetup.renderHudOverlays(event);
        ClientSetup.registerModGui(event);
        ClientSetup.registerItemProperties(event);
    }

    private void setup(final FMLCommonSetupEvent event) {
        // some preinit code
        LOGGER.info("Hi");
        LOGGER.info("Pure Atium >> {}", ModItems.PURE_ATIUM.get().getRegistryName());
    }
}