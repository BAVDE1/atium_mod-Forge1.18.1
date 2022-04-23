package com.BAVDE.atium_mod.block.entity;

import com.BAVDE.atium_mod.screen.InfusingTableMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class InfusingTableBlockEntity extends BlockEntity implements MenuProvider {
    public final ItemStackHandler itemHandler = new ItemStackHandler(4) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    public InfusingTableBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModBlockEntities.INFUSING_TABLE_BLOCK_ENTITY.get(), pWorldPosition, pBlockState);
    }

    @Override
    public Component getDisplayName() {
        return new TextComponent("");
    }

    //calls the InfusingTableMenu
    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory, Player pPlayer) {
        return new InfusingTableMenu(pContainerId, pInventory, this, ContainerLevelAccess.NULL) {
            @Override
            protected boolean mayPickup(Player p_39798_, boolean p_39799_) {
                return true;
            }
            @Override
            protected void onTake(Player player, ItemStack itemStack) {
                itemStack.onCraftedBy(player.level, player, itemStack.getCount());
                InfusingTableBlockEntity.this.shrinkStacks();
                this.access.execute((level, blockPos) -> {
                    level.levelEvent(1044, blockPos, 0);
                });
            }
            @Override
            protected boolean isValidBlock(BlockState p_39788_) {
                return true;
            }
        };
    }

    public void shrinkStacks() {
        itemHandler.extractItem(0, 1, false);
        itemHandler.extractItem(1, 1, false);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @javax.annotation.Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return lazyItemHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    //saves what is in the inventory or slots
    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        tag.put("inventory", itemHandler.serializeNBT());
        super.saveAdditional(tag);
    }

    //loads what is saved in the inventory
    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
    }

    //gets what is in the inventory and drops it at the position of the block when it is destroyed
    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        inventory.setItem(0, itemHandler.getStackInSlot(0));
        inventory.setItem(1, itemHandler.getStackInSlot(1));
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    //clears the result slot (2) when placed
    public void clearResultSlot() {
        itemHandler.setStackInSlot(2, ItemStack.EMPTY);
    }

    //is called in InfusingTableBlock every tick
    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, InfusingTableBlockEntity pBlockEntity) {}
}
