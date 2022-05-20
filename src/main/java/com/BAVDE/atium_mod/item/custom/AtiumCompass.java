package com.BAVDE.atium_mod.item.custom;

import com.mojang.datafixers.util.Pair;
import net.minecraft.commands.arguments.ResourceOrTagLocationArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.gametest.framework.StructureUtils;
import net.minecraft.nbt.CompoundTag;
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
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class AtiumCompass extends Item {
    public AtiumCompass(Properties pProperties) {
        super(pProperties);
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);
        pPlayer.startUsingItem(pUsedHand);
        setUsing(itemstack, true);
        findStructure(pLevel, pPlayer, itemstack);
        //getAllowedStructuresRes(pPlayer);
        return InteractionResultHolder.consume(itemstack);
    }

    @Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity, int pTimeCharged) {
        setUsing(pStack, false);
        //deleteStructureLocation(pStack);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.BLOCK;
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 72000;
    }

    public static boolean isUsing(ItemStack itemStack) {
        CompoundTag compoundtag = itemStack.getTag();
        return compoundtag != null && compoundtag.getBoolean("Using");
    }

    public static void setUsing(ItemStack itemStack, boolean pIsUsing) {
        CompoundTag compoundtag = itemStack.getOrCreateTag();
        compoundtag.putBoolean("Using", pIsUsing);
    }

    private static void findStructure(Level level, Player player, ItemStack itemStack) {
        if (level instanceof ServerLevel) {
            ServerLevel serverlevel = (ServerLevel) level;

            @SuppressWarnings("deprecation")
            StructureFeature<?> structure = Registry.STRUCTURE_FEATURE.get(ResourceLocation.tryParse("atium_mod:atium_geode"));
            ResourceLocation resourceLocation = structure.getRegistryName();

            ChunkGenerator generator = serverlevel.getChunkSource().getGenerator();
            HolderSet<ConfiguredStructureFeature<?, ?>> holderSet = serverlevel
                    .registryAccess().registryOrThrow(Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY)
                    .getHolder(ResourceKey.create(Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY,
                            new ResourceLocation(resourceLocation.getNamespace(),
                                    resourceLocation.getPath().replace("endcity", "end_city"))
                    )).map(HolderSet::direct).orElseThrow();

            Pair<BlockPos, Holder<ConfiguredStructureFeature<?, ?>>> result = generator.findNearestMapFeature(
                    serverlevel, holderSet, player.blockPosition(), 100, false);

            BlockPos blockPos = new BlockPos(result.getFirst());
            player.sendMessage(new TranslatableComponent("structure pos: " + blockPos), player.getUUID());
        }
    }

    public static List<ResourceLocation> getAllowedStructuresRes(Player player) {
        final List<ResourceLocation> result = new ArrayList<>();
        for (StructureFeature<?> structureFeature : ForgeRegistries.STRUCTURE_FEATURES) {
            ResourceLocation res = structureFeature.getRegistryName();
            result.add(res);
        }
        player.sendMessage(new TranslatableComponent("structures Location: " + result), player.getUUID());
        return result;
    }

    private static void removeLocationTag(ItemStack itemStack) {
        itemStack.getTag().remove("Location");
    }
}