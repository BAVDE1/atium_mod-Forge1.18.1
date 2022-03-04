package com.BAVDE.atium_mod.block.custom;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class AtiumOreOvergrownRecharging extends Block {
    public static final BooleanProperty RECHARGING = BlockStateProperties.LIT;

    public AtiumOreOvergrownRecharging(BlockBehaviour.Properties p_49795_) {
        super(p_49795_);
        this.registerDefaultState(this.stateDefinition.any().setValue(RECHARGING, Boolean.valueOf(true)));
    }


}
