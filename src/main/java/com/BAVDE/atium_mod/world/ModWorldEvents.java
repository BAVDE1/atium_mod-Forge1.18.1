package com.BAVDE.atium_mod.world;

import com.BAVDE.atium_mod.AtiumMod;
import com.BAVDE.atium_mod.world.gen.ModTreeGeneration;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = AtiumMod.MOD_ID)
public class ModWorldEvents {
    /*@SubscribeEvent
    public static void biomeLoadingEvent(final BiomeLoadingEvent event) {
        ModTreeGeneration.generateTrees(event);
    }*/
}