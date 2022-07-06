package com.BAVDE.atium_mod.block.entity;

import com.BAVDE.atium_mod.item.ModItems;
import com.BAVDE.atium_mod.particle.ModParticles;
import com.BAVDE.atium_mod.screen.InfusingTableMenu;
import com.BAVDE.atium_mod.util.ModTags;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.Set;

public class InfusingTableBlockEntity extends BaseContainerBlockEntity implements MenuProvider {
    protected NonNullList<ItemStack> items = NonNullList.withSize(3, ItemStack.EMPTY);
    public final ItemStackHandler itemHandler = new ItemStackHandler(3) {
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
    protected Component getDefaultName() {
        return new TextComponent("");
    }

    @Override
    protected AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory) {
        return new InfusingTableMenu(pContainerId, pInventory, this, ContainerLevelAccess.NULL);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
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

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @javax.annotation.Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return lazyItemHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    //gets what is in the inventory and drops it at the position of the block when it is destroyed
    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        inventory.setItem(0, itemHandler.getStackInSlot(0));
        inventory.setItem(1, itemHandler.getStackInSlot(1));
        //if is not valid craft drop item that is in result slot
        //this.checkResult(inventory);
        this.clearResultSlot();
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    private void checkResult(SimpleContainer inventory) {
        //Level level = this.getLevel();
        //Optional<InfusingTableRecipe> match = level.getRecipeManager().getRecipeFor(InfusingTableRecipe.Type.INSTANCE, inventory, level);

        //if recipe is not valid
        if (!(!hasMetal() && hasAnyOf(Set.of(ModItems.ATIUM_HELMET.get(), ModItems.ATIUM_CHESTPLATE.get(), ModItems.ATIUM_LEGGINGS.get(), ModItems.ATIUM_BOOTS.get(), ModItems.ATIUM_SWORD.get(), ModItems.ATIUM_PICKAXE.get(), ModItems.ATIUM_AXE.get(), ModItems.ATIUM_SHOVEL.get(), ModItems.ATIUM_HOE.get())))){
            inventory.setItem(2, itemHandler.getStackInSlot(2));
        } else {
            inventory.setItem(2, ItemStack.EMPTY);
        }

        /*if (match.isPresent()) {
            if (itemHandler.getStackInSlot(2).getItem() == new ItemStack(match.get().getResultItem().getItem(), 1).getItem()) {
                inventory.setItem(2, ItemStack.EMPTY);
            }
        } else {
            inventory.setItem(2, itemHandler.getStackInSlot(2));
        }*/
    }

    //clears the result slot (2) when placed & broken - just to be sure
    public void clearResultSlot() {
        itemHandler.setStackInSlot(2, ItemStack.EMPTY);
    }

    //doesn't work with itemHandler (see furnace for example?)
    @Override
    public int getContainerSize() {
        return 2;
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemstack : this.items) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getItem(int pIndex) {
        return this.items.get(pIndex);
    }

    @Override
    public ItemStack removeItem(int pIndex, int pCount) {
        return ContainerHelper.removeItem(this.items, pIndex, pCount);
    }

    @Override
    public ItemStack removeItemNoUpdate(int pIndex) {
        return ContainerHelper.takeItem(this.items, pIndex);
    }

    @Override
    public void setItem(int pIndex, ItemStack pStack) {
        this.items.set(pIndex, pStack);
        if (pStack.getCount() > this.getMaxStackSize()) {
            pStack.setCount(this.getMaxStackSize());
        }
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        if (this.level.getBlockEntity(this.worldPosition) != this) {
            return false;
        } else {
            return pPlayer.distanceToSqr((double) this.worldPosition.getX() + 0.5D, (double) this.worldPosition.getY() + 0.5D, (double) this.worldPosition.getZ() + 0.5D) <= 64.0D;
        }
    }

    @Override
    public void clearContent() {
        this.items.clear();
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, InfusingTableBlockEntity pBlockEntity) {
        //particle effect for when valid metal is in metal slot (0)
        if (pBlockEntity.hasMetal() && Math.random() < 0.6) {
            //pLevel.addParticle(ModParticles.INFUSION_FLAME_PARTICLES.get(), pPos.getX() + 0.5, pPos.getY() + 0.95, pPos.getZ() + 0.5, 0, 0, 0);
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

    //returns true if item in slot 0 is a valid infusing metal
    public boolean hasMetal() {
        Item item = itemHandler.getStackInSlot(0).getItem();
        return item.getDefaultInstance().is(ModTags.Items.INFUSING_IRON) ||
                item.getDefaultInstance().is(ModTags.Items.INFUSING_STEEL) ||
                item.getDefaultInstance().is(ModTags.Items.INFUSING_TIN) ||
                item.getDefaultInstance().is(ModTags.Items.INFUSING_PEWTER) ||
                item.getDefaultInstance().is(ModTags.Items.INFUSING_BRASS) ||
                item.getDefaultInstance().is(ModTags.Items.INFUSING_ZINC) ||
                item.getDefaultInstance().is(ModTags.Items.INFUSING_COPPER) ||
                item.getDefaultInstance().is(ModTags.Items.INFUSING_BRONZE) ||
                item.getDefaultInstance().is(ModTags.Items.INFUSING_GOLD);
    }
}