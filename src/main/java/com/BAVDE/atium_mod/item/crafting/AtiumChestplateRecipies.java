package com.BAVDE.atium_mod.item.crafting;

import com.BAVDE.atium_mod.item.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class AtiumChestplateRecipies extends CustomRecipe {
    private static final Ingredient CHESTPLATE_INGREDIENT = Ingredient.of(ModItems.ATIUM_CHESTPLATE.get());
    private static final Ingredient GOLD_INGREDIENT = Ingredient.of(Items.GOLD_INGOT);

    public AtiumChestplateRecipies(ResourceLocation pId) {super(pId);}

    @Override
    public boolean matches(CraftingContainer pContainer, Level pLevel) {
        boolean flag = false;
        boolean flag2 = false;

        for(int j = 0; j < pContainer.getContainerSize(); ++j) {
            ItemStack itemstack = pContainer.getItem(j);
            if (!itemstack.isEmpty()) {
                if (CHESTPLATE_INGREDIENT.test(itemstack)) {
                    if (flag) {
                        return false;
                    }

                    flag = true;
                } else if (GOLD_INGREDIENT.test(itemstack)) {
                    if (flag2) {
                        return false;
                    }
                    
                    flag2 = true;
                }
            }
        }

        return flag && flag2;
    }

    @Override
    public ItemStack assemble(CraftingContainer pContainer) {
        ItemStack itemstack = new ItemStack(ModItems.INFUSED_ATIUM_CHESTPLATE.get(), 1);
        CompoundTag compoundtag = itemstack.getOrCreateTagElement("");
        ListTag listtag = new ListTag();
        int i = 0;

        for(int j = 0; j < pContainer.getContainerSize(); ++j) {
            ItemStack itemstack1 = pContainer.getItem(j);
            if (!itemstack1.isEmpty()) {
                if (CHESTPLATE_INGREDIENT.test(itemstack1)) {
                    ++i;
                } else if (GOLD_INGREDIENT.test(itemstack1)) {
                    CompoundTag compoundtag1 = itemstack1.getTagElement("Gold");
                    if (compoundtag1 != null) {
                        listtag.add(compoundtag1);
                    }
                }
            }
        }

        if (!listtag.isEmpty()) {
            compoundtag.put("Metal", listtag);
        }

        return itemstack;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return pWidth * pHeight >= 2;
    }

    @Override
    public ItemStack getResultItem() {
        return new ItemStack(ModItems.INFUSED_ATIUM_CHESTPLATE.get());
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return null;
    }
}
