package com.BAVDE.atium_mod.block.entity;

import com.BAVDE.atium_mod.AtiumMod;
import com.BAVDE.atium_mod.block.ModBlocks;
import com.BAVDE.atium_mod.block.entity.custom.InfusingTableBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, AtiumMod.MOD_ID);

    public static final RegistryObject<BlockEntityType<InfusingTableBlockEntity>> INFUSING_TABLE_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("infusing_table_block_entity", () ->
                    BlockEntityType.Builder.of(InfusingTableBlockEntity::new,
                            ModBlocks.INFUSING_TABLE.get()).build(null));


    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
