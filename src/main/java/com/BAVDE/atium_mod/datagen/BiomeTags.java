package com.BAVDE.atium_mod.datagen;

import com.BAVDE.atium_mod.AtiumMod;
import com.BAVDE.atium_mod.worldgen.Registration;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.TagManager;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class BiomeTags extends TagsProvider<Biome> {

    public BiomeTags(DataGenerator generator, ExistingFileHelper helper) {
        super(generator, BuiltinRegistries.BIOME, AtiumMod.MOD_ID, helper, TagManager.getTagDir(BuiltinRegistries.BIOME.key()).substring(5));
    }

    @Override
    protected void addTags() {
        ForgeRegistries.BIOMES.getValues().forEach(biome -> {
            tag(Registration.HAS_ATIUM_GEODE_STRUCTURE).add(biome);
        });
    }

    @Override
    public String getName() {
        return "Atium Mod Tags";
    }
}
