package com.BAVDE.atium_mod.screen;

import com.BAVDE.atium_mod.block.entity.InfusingTableBlockEntity;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public abstract class AbstractInfusingMenu extends AbstractContainerMenu {
    private final InfusingTableBlockEntity blockEntity;
    protected final Level level;
    protected final Player player;

    protected abstract boolean mayPickup(Player p_39798_, boolean p_39799_);

    protected abstract void onTake(Player p_150601_, ItemStack p_150602_);

    protected abstract boolean isValidBlock(BlockState p_39788_);

    public AbstractInfusingMenu(@Nullable MenuType<?> menuType, int window, Inventory inv, BlockEntity entity) {
        super(menuType, window);
        blockEntity = ((InfusingTableBlockEntity) entity);
        this.player = inv.player;
        this.level = inv.player.level;
    }

    public abstract void createResult();

    public void slotsChanged(Container pInventory) {
        super.slotsChanged(pInventory);
        this.createResult();
    }

    /* QUICK MOVE */
    protected boolean shouldQuickMoveToAdditionalSlot(ItemStack p_39787_) {
        return false;
    }

    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(pIndex);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (pIndex == 2) {
                if (!this.moveItemStackTo(itemstack1, 3, 39, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(itemstack1, itemstack);
            } else if (pIndex != 0 && pIndex != 1) {
                if (pIndex >= 3 && pIndex < 39) {
                    int i = this.shouldQuickMoveToAdditionalSlot(itemstack) ? 1 : 0;
                    if (!this.moveItemStackTo(itemstack1, i, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (!this.moveItemStackTo(itemstack1, 3, 39, false)) {
                return ItemStack.EMPTY;
            }
            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTake(pPlayer, itemstack1);
        }
        return itemstack;
    }

    /* INVENTORY */
    public void addPlayerInventory(Inventory playerInventory, int Ypos) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 7 + l * 18, Ypos + i * 18));
            }
        }
    }

    public void addPlayerHotbar(Inventory playerInventory, int Ypos) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 7 + i * 18, Ypos));
        }
    }
}