package com.BAVDE.atium_mod.block.custom;

import com.BAVDE.atium_mod.particle.ModParticles;
import com.BAVDE.atium_mod.screen.InfusingTableMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class InfusingTableBlock extends CraftingTableBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    private static final Component CONTAINER_TITLE = new TextComponent("");

    public InfusingTableBlock(Properties properties) {
        super(properties);
    }

    /**
     * Block Model
     */
    //stores the shape for the block                                                side     height   side
    private static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 10, 16);

    //creates the custom block shape
    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }
    @Override
    public BlockState rotate(BlockState pState, Rotation pRotation) {
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }
    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }
    //prevents model from being invisible
    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    /**
     * MENU
     */
    @Override
    public MenuProvider getMenuProvider(BlockState pState, Level pLevel, BlockPos pPos) {
        return new SimpleMenuProvider((ContainerID, inventory, player) -> {
            return new InfusingTableMenu(ContainerID, inventory, ContainerLevelAccess.NULL);
        }, CONTAINER_TITLE);
    }

    //when block is right-clicked, open the inventory / GUI
    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pLevel.isClientSide()) {
            return InteractionResult.SUCCESS;
        } else {
            pPlayer.openMenu(pState.getMenuProvider(pLevel, pPos));
            return InteractionResult.CONSUME;
        }
    }

    public static void createCraftParticles(double pSpeed, int pSize, LivingEntity player, BlockPos pos) {
        Level level = player.getLevel();
        Minecraft minecraft = Minecraft.getInstance();
        //gets middle of block
        double d0 = pos.getX() + 0.5;
        double d1 = pos.getY() + 0.5;
        double d2 = pos.getZ() + 0.5;
        for (int i = -pSize; i <= pSize; ++i) {
            for (int j = -pSize; j <= pSize; ++j) {
                for (int k = -pSize; k <= pSize; ++k) {
                    double d3 = (double) j + (level.random.nextDouble() - level.random.nextDouble()) * 0.5D;
                    double d4 = (double) i + (level.random.nextDouble() - level.random.nextDouble()) * 0.5D;
                    double d5 = (double) k + (level.random.nextDouble() - level.random.nextDouble()) * 0.5D;
                    double d6 = Math.sqrt(d3 * d3 + d4 * d4 + d5 * d5) / pSpeed + level.random.nextGaussian() * 0.05D;
                    minecraft.particleEngine.createParticle(ModParticles.INFUSION_CRAFT_PARTICLES.get(), d0, d1, d2, d3 / d6, d4 / d6, d5 / d6);
                    if (i != -pSize && i != pSize && j != -pSize && j != pSize) {
                        k += pSize * 2 - 1;
                    }
                }
            }
        }
    }
}