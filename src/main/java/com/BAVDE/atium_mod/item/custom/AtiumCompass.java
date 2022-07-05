package com.BAVDE.atium_mod.item.custom;

import com.mojang.datafixers.util.Pair;
import net.minecraft.Util;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AtiumCompass extends Item {
    public AtiumCompass(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);
        pPlayer.startUsingItem(pUsedHand);
        removeLocation(itemstack);
        setUsing(itemstack, true);
        findStructure(pLevel, pPlayer, itemstack);
        return InteractionResultHolder.consume(itemstack);
    }

    @Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity, int pTimeCharged) {
        setUsing(pStack, false);
        removeLocation(pStack);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.BLOCK;
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 72000;
    }

    //checks if item is being used
    public static boolean isUsing(ItemStack itemStack) {
        CompoundTag compoundtag = itemStack.getTag();
        return compoundtag != null && compoundtag.getBoolean("Using");
    }

    //sets tag stating is items is in use
    public static void setUsing(ItemStack itemStack, boolean pIsUsing) {
        CompoundTag compoundtag = itemStack.getOrCreateTag();
        compoundtag.putBoolean("Using", pIsUsing);
    }

    //finds the nearest structure in world
    private static void findStructure(Level level, Player player, ItemStack itemStack) {
        //server side
        if (level instanceof ServerLevel serverLevel) {
            //configured structure registry
            Registry<ConfiguredStructureFeature<?, ?>> registry = serverLevel.registryAccess().registryOrThrow(Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY);
            //resource location of structure
            ResourceKey<ConfiguredStructureFeature<?, ?>> structureKey = ResourceKey.create(Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY, ResourceLocation.tryParse("atium_mod:atium_geode"));

            //holder set of structureKey
            HolderSet<ConfiguredStructureFeature<?, ?>> featureHolderSet = registry.getHolder(structureKey).map((holders) -> {
                return HolderSet.direct(holders);
            }).orElse(null);

            //gets location of the nearest structure
            Pair<BlockPos, Holder<ConfiguredStructureFeature<?, ?>>> pair =
                    serverLevel.getChunkSource().getGenerator().findNearestMapFeature(
                            serverLevel, featureHolderSet, player.blockPosition(), 100, false);

            //if structure is present in dimension get block pos
            BlockPos structurePos = pair != null ? pair.getFirst() : null;
            if (structurePos != null) {
                addLocation(itemStack, structurePos);
            }
        }
    }

    //adds tag storing structure location
    private static void addLocation(ItemStack itemStack, BlockPos blockPos) {
        CompoundTag compoundtag = itemStack.getOrCreateTag();
        compoundtag.put("Location", NbtUtils.writeBlockPos(blockPos));
    }

    //removes tag storing structure location
    private static void removeLocation(ItemStack itemStack) {
        CompoundTag compoundtag = itemStack.getTag();
        if (compoundtag != null) {
            if (compoundtag.contains("Location")) {
                itemStack.getTag().remove("Location");
            }
        }
    }

    //gets tag storing structure location
    public static Tag getLocation(ItemStack itemStack) {
        CompoundTag compoundtag = itemStack.getTag();
        if (compoundtag != null) {
            if (compoundtag.contains("Location")) {
                return compoundtag.get("Location");
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if (Screen.hasControlDown()) {
            //item lore
            pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.atium_compass.tooltip.ctrl1"));
            //line break
            pTooltipComponents.add(new TextComponent(" "));
            //item desc
            pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.atium_compass.tooltip.ctrl2"));
        } else {
            pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.atium_compass.tooltip"));
        }
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}