package com.BAVDE.atium_mod.block.custom;

import com.BAVDE.atium_mod.block.entity.ModBlockEntities;
import com.BAVDE.atium_mod.particle.ModParticles;
import com.BAVDE.atium_mod.screen.InfusingTableMenu;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import com.BAVDE.atium_mod.block.entity.InfusingTableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class InfusingTableBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public InfusingTableBlock(Properties properties) {
        super(properties);
    }

    //stores the shape for the block                                                 side     height   side
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

    /* BLOCK ENTITY */
    //calls the drop method in the block entity to drop its inventory
    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof InfusingTableBlockEntity) {
                ((InfusingTableBlockEntity) blockEntity).drops();
            }
        }
        pLevel.updateNeighborsAt(pPos, this);
    }

    //when placed calls clearResultSlot in block entity
    @Override
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
        if (pState.getBlock() != pOldState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof InfusingTableBlockEntity) {
                ((InfusingTableBlockEntity) blockEntity).clearResultSlot();
            }
        }
        pLevel.updateNeighborsAt(pPos, this);
    }

    //when block is right-clicked, open the inventory / GUI
    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide()) {
            BlockEntity entity = pLevel.getBlockEntity(pPos);
            if (entity instanceof InfusingTableBlockEntity) {
                //network hooks basically synchronizes client and server side (i think)
                NetworkHooks.openGui(((ServerPlayer) pPlayer), (InfusingTableBlockEntity) entity, pPos);
            } else {
                throw new IllegalStateException("Our Container provider is missing!");
            }
        }
        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new InfusingTableBlockEntity(pPos, pState);
    }

    @Override
    public int getSignal(BlockState pState, BlockGetter pLevel, BlockPos pPos, Direction pDirection) {
        return 1;
    }

    @Override
    public boolean isSignalSource(BlockState pState) {
        return true;
    }

    //makes block tick, called in blockEntity
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, ModBlockEntities.INFUSING_TABLE_BLOCK_ENTITY.get(), InfusingTableBlockEntity::tick);
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, Random pRandom) {
        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
        if (blockEntity instanceof InfusingTableBlockEntity) {
            if (((InfusingTableBlockEntity) blockEntity).hasMetal()) {
                //pLevel.addParticle(ModParticles.INFUSION_FLAME_PARTICLES.get(), pPos.getX() + 0.5, pPos.getY() + 0.7, pPos.getZ() + 0.5, 0, 5.0E-5D, 0);
            }
        }
    }
    //tick doesn't work here lol
}