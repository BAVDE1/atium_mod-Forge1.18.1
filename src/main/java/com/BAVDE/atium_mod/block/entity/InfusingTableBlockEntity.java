package com.BAVDE.atium_mod.block.entity;

import com.BAVDE.atium_mod.item.ModItems;
import com.BAVDE.atium_mod.recipe.InfusingTableRecipe;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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
import java.util.Optional;

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
        return new InfusingTableMenu(pContainerId, pInventory, this);
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
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    //is called in InfusingTableBlock every tick
    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, InfusingTableBlockEntity pBlockEntity) {
        if (hasRecipe(pBlockEntity)) {
            displayItem(pBlockEntity);
        }
    }

    public static void displayItem(InfusingTableBlockEntity entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }
        Optional<InfusingTableRecipe> match = level.getRecipeManager().getRecipeFor(InfusingTableRecipe.Type.INSTANCE, inventory, level);

        if (match.isPresent()) {
            entity.itemHandler.setStackInSlot(2, new ItemStack(match.get().getResultItem().getItem(), 1));
            //entity.itemHandler.getStackInSlot(2).addTagElement("metal", getMetal(entity));
        }
    }

    public static int getMetal(InfusingTableBlockEntity entity) {
        ItemStack itemStack = entity.itemHandler.getStackInSlot(0);
        int metal = 0;
        if (itemStack.getItem() == Items.IRON_INGOT) {
            metal = 1;
        } else if (itemStack.getItem() == ModItems.STEEL.get()) {
            metal = 2;
        } else if (itemStack.getItem() == ModItems.TIN.get()) {
            metal = 3;
        } else if (itemStack.getItem() == ModItems.PEWTER.get()) {
            metal = 4;
        } else if (itemStack.getItem() == ModItems.BRASS.get()) {
            metal = 5;
        } else if (itemStack.getItem() == ModItems.ZINC.get()) {
            metal = 6;
        } else if (itemStack.getItem() == Items.COPPER_INGOT) {
            metal = 7;
        } else if (itemStack.getItem() == ModItems.BRONZE.get()) {
            metal = 8;
        } else if (itemStack.getItem() == Items.IRON_INGOT) {
            metal = 9;
        }
        return metal;
    }

    private static void craftItem(InfusingTableBlockEntity entity) {
        //extracts item from slot 0
        entity.itemHandler.extractItem(0, 1, false);
        //extracts item from slot 1
        entity.itemHandler.extractItem(1, 1, false);

        //puts result item in slot 2
        entity.itemHandler.setStackInSlot(2, new ItemStack(ModItems.PURE_ATIUM.get(),
                entity.itemHandler.getStackInSlot(2).getCount() + 1));
    }

    //checks if correct items in inventory for the recipe
    private static boolean hasRecipe(InfusingTableBlockEntity entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }
        Optional<InfusingTableRecipe> match = level.getRecipeManager().getRecipeFor(InfusingTableRecipe.Type.INSTANCE, inventory, level);

        return match.isPresent() && canInsertAmountIntoOutputSlot(inventory) && canInsertItemIntoOutputSlot(inventory, match.get().getResultItem());
    }

    //if it is the same item in the output slot as what it is trying to craft
    private static boolean canInsertItemIntoOutputSlot(SimpleContainer inventory, ItemStack output) {
        return inventory.getItem(2).getItem() == output.getItem() || inventory.getItem(2).isEmpty();
    }

    //checks if the stack in result slot has reached its max stack size
    private static boolean canInsertAmountIntoOutputSlot(SimpleContainer inventory) {
        return inventory.getItem(2).getMaxStackSize() > inventory.getItem(2).getCount();
    }
}
