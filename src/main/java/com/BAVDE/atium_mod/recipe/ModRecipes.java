package com.BAVDE.atium_mod.recipe;

import com.BAVDE.atium_mod.AtiumMod;
import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, AtiumMod.MOD_ID);

    public static final RegistryObject<RecipeSerializer<InfusingTableRecipe>> INFUSING_TABLE_SERIALIZER =
            SERIALIZERS.register("infusing", () -> InfusingTableRecipe.Serializer.INSTANCE);

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
    }
}