package com.BAVDE.atium_mod.screen;

import com.BAVDE.atium_mod.block.ModBlocks;
import com.BAVDE.atium_mod.block.entity.InfusingTableBlockEntity;
import com.BAVDE.atium_mod.item.ModItems;
import com.BAVDE.atium_mod.recipe.InfusingTableRecipe;
import com.BAVDE.atium_mod.screen.slot.ModResultSlot;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

import java.util.Optional;

public class InfusingTableMenu extends AbstractContainerMenu {
    private final InfusingTableBlockEntity blockEntity;
    private final Level level;

    //constructor called in block entity
    public InfusingTableMenu(int windowId, Inventory inv, FriendlyByteBuf extraData) {
        this(windowId, inv, inv.player.level.getBlockEntity(extraData.readBlockPos()));
    }

    //constructor called in the ModMenuTypes
    public InfusingTableMenu(int windowId, Inventory inv, BlockEntity entity) {
        super(ModMenuTypes.INFUSING_TABLE_MENU.get(), windowId);
        checkContainerSize(inv, 3);
        blockEntity = ((InfusingTableBlockEntity) entity);
        this.level = inv.player.level;

        //calls methods to include players inv and hot bar at bottom
        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        //the slots in the inventory
        this.blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(handler -> {
            //slotItemHandler determines position (in pixels) of slot in inventory
            //the coords start at 0 on the top right most pixel of inventory texture
            //+x -->  &  +y v
            //slot placement is top right corner of slot (not centre)
            //index should start at 0
            this.addSlot(new SlotItemHandler(handler, 0, 79, 23));
            this.addSlot(new SlotItemHandler(handler, 1, 79, 57));
            this.addSlot(new ModResultSlot(handler, 2, 79, 98));
        });
    }

    public int hasMetal(){
        /*
        1 = Iron
        2 = Steel
        3 = Tin
        4 = Pewter
        5 = Brass
        6 = Zinc
        7 = Copper
        8 = Bronze
        9 = Gold */
        ItemStack itemStack = blockEntity.itemHandler.getStackInSlot(0);
        int metal = 0;
        if (itemStack.getItem() == Items.IRON_INGOT)            {metal = 1;}
        else if (itemStack.getItem() == ModItems.STEEL.get())   {metal = 2;}
        else if (itemStack.getItem() == ModItems.TIN.get())     {metal = 3;}
        else if (itemStack.getItem() == ModItems.PEWTER.get())  {metal = 4;}
        else if (itemStack.getItem() == ModItems.BRASS.get())   {metal = 5;}
        else if (itemStack.getItem() == ModItems.ZINC.get())    {metal = 6;}
        else if (itemStack.getItem() == Items.COPPER_INGOT)     {metal = 7;}
        else if (itemStack.getItem() == ModItems.BRONZE.get())  {metal = 8;}
        else if (itemStack.getItem() == Items.GOLD_INGOT)       {metal = 9;}
        return metal;
    }

    /* QUICK MOVE CODE (SHIFT CLICK ITEMSTACK) */
    // CREDIT GOES TO: diesieben07 | https://github.com/diesieben07/SevenCommons
    // must assign a slot number to each of the slots used by the GUI.
    // For this container, we can see both the tile inventory's slots as well as the player inventory slots and the hotbar.
    // Each time we add a Slot to the container, it automatically increases the slotIndex, which means
    //  0 - 8 = hotbar slots (which will map to the InventoryPlayer slot numbers 0 - 8)
    //  9 - 35 = player inventory slots (which map to the InventoryPlayer slot numbers 9 - 35)
    //  36 - 44 = TileInventory slots, which map to our TileEntity slot numbers 0 - 8)
    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;
    //must be defined!
    private static final int TE_INVENTORY_SLOT_COUNT = 3;  // must be the number of slots you have!
    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        Slot sourceSlot = slots.get(index);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // Check if the slot clicked is one of the vanilla container slots
        if (index < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX
                    + TE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;  // EMPTY_ITEM
            }
        } else if (index < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            // This is a TE slot so merge the stack into the players inventory
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("Invalid slotIndex:" + index);
            return ItemStack.EMPTY;
        }
        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(playerIn, sourceStack);
        return copyOfSourceStack;
    }

    //if still close enough to access inventory
    //e.g. if player is in mine-cart and moves too far away from block
    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), pPlayer, ModBlocks.INFUSING_TABLE.get());
    }

    /* PLAYER INVENTORY */
    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 7 + l * 18, 139 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 7 + i * 18, 196));
        }
    }
}