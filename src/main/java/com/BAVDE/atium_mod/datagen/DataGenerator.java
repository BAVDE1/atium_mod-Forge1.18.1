package com.BAVDE.atium_mod.datagen;

import com.BAVDE.atium_mod.AtiumMod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = AtiumMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerator {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        net.minecraft.data.DataGenerator generator = event.getGenerator();
        if (event.includeServer()) {
            generator.addProvider(new BiomeTags(generator, event.getExistingFileHelper()));
        }
    }
}
