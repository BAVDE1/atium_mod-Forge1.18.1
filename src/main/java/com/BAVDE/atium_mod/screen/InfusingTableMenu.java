package com.BAVDE.atium_mod.screen;

import com.BAVDE.atium_mod.block.custom.InfusingTableBlock;
import com.BAVDE.atium_mod.item.ModItems;
import com.BAVDE.atium_mod.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class InfusingTableMenu extends AbstractInfusingMenu {
    public final Level level;
    protected final Player player;

    //slots
    protected final ResultContainer resultSlots = new ResultContainer();
    protected final Container inputSlots = new SimpleContainer(2) {
        //For tile entities, ensures the chunk containing the tile entity is saved to disk later - the game won't think it hasn't changed and skip it.
        public void setChanged() {
            super.setChanged();
            InfusingTableMenu.this.slotsChanged(this);
        }
    };

    public InfusingTableMenu(int window, Inventory inv) {
        this(window, inv, ContainerLevelAccess.NULL);
    }

    public InfusingTableMenu(int window, Inventory inv, ContainerLevelAccess access) {
        super(ModMenuTypes.INFUSING_TABLE_MENU.get(), window, inv, access);
        this.player = inv.player;
        this.level = inv.player.level;

        //slot determines position (in pixels) of slot in inventory
        //the coords start at 0 on the top right most pixel of inventory texture
        //+x -->  &  +y v
        //slot placement is top right corner of slot (not centre)
        //index should start at 0

        //metal slot
        this.addSlot(new Slot(this.inputSlots, 0, 79, 23));
        //gear slot
        this.addSlot(new Slot(this.inputSlots, 1, 79, 57));
        //output slot
        this.addSlot(new Slot(this.resultSlots, 2, 79, 98) {
            //Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
            public boolean mayPlace(ItemStack p_39818_) {
                return false;
            }
            //Return whether this slot's stack can be taken from this slot.
            public boolean mayPickup(Player p_39813_) {
                return InfusingTableMenu.this.mayPickup(p_39813_, this.hasItem());
            }
            public void onTake(Player p_150604_, ItemStack p_150605_) {
                InfusingTableMenu.this.onTake(p_150604_, p_150605_);
            }
        });

        //player inventory & hotbar (located in AbstractInfusingMenu)
        addPlayerInventory(inv, 139);
        addPlayerHotbar(inv, 196);
    }

    @Override
    protected boolean mayPickup(Player player, boolean bool) {
        return true;
    }

    @Override
    protected void onTake(Player player, ItemStack itemStack) {
        itemStack.onCraftedBy(player.level, player, itemStack.getCount());
        //on craft
        if (hasRecipe()) {
            this.shrinkStacksInSlot(0);
            this.shrinkStacksInSlot(1);
            this.playSound(player.blockPosition());
            //this isnt called vv
            this.access.execute((level, blockPos) -> {
                level.levelEvent(1044, blockPos, 0);
                InfusingTableBlock.createCraftParticles(0.05D, 1, player, blockPos);
            });
        }
    }

    //plays sound on craft
    private void playSound(BlockPos pos) {
        level.playSound((Player) null, pos, SoundEvents.SMITHING_TABLE_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
        level.playSound((Player) null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 0.6F, 0.8F + level.random.nextFloat() * 1.2F);
    }

    //shrinks the stacks in slot by one
    private void shrinkStacksInSlot(int slot) {
        ItemStack itemstack = this.inputSlots.getItem(slot);
        itemstack.shrink(1);
        this.inputSlots.setItem(slot, itemstack);
    }

    @Override
    protected boolean isValidBlock(BlockState blockState) {
        return true;
    }

    @Override
    public void createResult() {
        if (hasRecipe()) {
            ItemStack gearSlot = this.slots.get(1).getItem();

            //Normal metal infusion (if not infused already and metal is not copper, bronze or aluminium (this doesn't like 'or' statements))
            if (!hasMetalTag() && hasMetal() != 7) { //not copper
                if (hasMetal() != 8) { //not bronze
                    if (hasMetal() != 10) { //not aluminium
                        this.slots.get(2).set(gearSlot.copy());
                        //blockEntity.itemHandler.setStackInSlot(2, gearSlot.copy());
                        this.addMetalTag();
                        this.addCopperTag(0);
                    }
                }

                //Copper infusion (else if the infusing metal is copper does not have copper infused)
            } else if (hasMetalTag() && hasMetal() == 7 && getCopperTag() == 0) {
                this.slots.get(2).set(gearSlot.copy());
                //blockEntity.itemHandler.setStackInSlot(2, gearSlot.copy());
                this.addCopperTag(1);

                //Bronze infusion (else if the infusing metal is bronze and has copper cloud)
            } else if (hasMetalTag() && hasMetal() == 8 && getCopperTag() == 1) {
                this.slots.get(2).set(gearSlot.copy());
                //blockEntity.itemHandler.setStackInSlot(2, gearSlot.copy());
                this.addCopperTag(0);

                //Aluminium infusion (else if gear is infused and infusing metal is aluminium)
            } else if (hasMetalTag() && hasMetal() == 10) {
                this.slots.get(2).set(gearSlot.copy());
                //blockEntity.itemHandler.setStackInSlot(2, gearSlot.copy());
                this.removeMetalTags();
            }
        } else {
            this.slots.get(2).set(ItemStack.EMPTY);
            //blockEntity.itemHandler.setStackInSlot(2, ItemStack.EMPTY);
        }
    }

    //returns true if there is a valid metal in slot 0 and valid gear in slot 1
    private boolean hasRecipe() {
        return hasMetal() != 0 && hasAtiumItem() != 0;
    }

    //to check if metal slot is empty (used in screen)
    public boolean isSlot0Empty() {
        ItemStack itemStack = this.slots.get(0).getItem();
        return itemStack == ItemStack.EMPTY;
    }

    //returns metal number in slot 0 (returns 0 if not a valid metal)
    public int hasMetal() {
        //1=iron, 2=steel, 3=tin, 4=pewter, 5=brass, 6=zinc, 7=copper, 8=bronze, 9=gold
        Item item = this.slots.get(0).getItem().getItem();
        int metal = 0;
        if (item.getDefaultInstance().is(ModTags.Items.INFUSING_IRON)) {
            metal = 1;
        } else if (item.getDefaultInstance().is(ModTags.Items.INFUSING_STEEL)) {
            metal = 2;
        } else if (item.getDefaultInstance().is(ModTags.Items.INFUSING_TIN)) {
            metal = 3;
        } else if (item.getDefaultInstance().is(ModTags.Items.INFUSING_PEWTER)) {
            metal = 4;
        } else if (item.getDefaultInstance().is(ModTags.Items.INFUSING_BRASS)) {
            metal = 5;
        } else if (item.getDefaultInstance().is(ModTags.Items.INFUSING_ZINC)) {
            metal = 6;
        } else if (item.getDefaultInstance().is(ModTags.Items.INFUSING_COPPER)) {
            metal = 7;
        } else if (item.getDefaultInstance().is(ModTags.Items.INFUSING_BRONZE)) {
            metal = 8;
        } else if (item.getDefaultInstance().is(ModTags.Items.INFUSING_GOLD)) {
            metal = 9;
        } else if (item.getDefaultInstance().is(ModTags.Items.INFUSING_ALUMINIUM)) {
            metal = 10;
        }
        return metal;
    }

    //returns number if there is a valid atium item in slot 1 (returns 0 if not valid)
    public int hasAtiumItem() {
        //1=sword, 2=pick, 3=axe, 4=shovel, 5=hoe, 6=helmet, 7=chestplate, 8=leggings, 9=boots
        Item item = this.slots.get(1).getItem().getItem();
        int returnValue = 0;
        if (item == ModItems.ATIUM_SWORD.get()) {
            returnValue = 1;
        } else if (item == ModItems.ATIUM_PICKAXE.get()) {
            returnValue = 2;
        } else if (item == ModItems.ATIUM_AXE.get()) {
            returnValue = 3;
        } else if (item == ModItems.ATIUM_SHOVEL.get()) {
            returnValue = 4;
        } else if (item == ModItems.ATIUM_HOE.get()) {
            returnValue = 5;
        } else if (item == ModItems.ATIUM_HELMET.get()) {
            returnValue = 6;
        } else if (item == ModItems.ATIUM_CHESTPLATE.get()) {
            returnValue = 7;
        } else if (item == ModItems.ATIUM_LEGGINGS.get()) {
            returnValue = 8;
        } else if (item == ModItems.ATIUM_BOOTS.get()) {
            returnValue = 9;
        }
        return returnValue;
    }

    //returns whether item in slot 1 has metal tag
    private boolean hasMetalTag() {
        ItemStack itemStack = this.slots.get(1).getItem();
        if (itemStack.getTag().contains("atium_mod.metal")) {
            return itemStack.getTag().getInt("atium_mod.metal") != 0;
        }
        return false;
    }

    //gets copper tag of item in slot 1
    private int getCopperTag() {
        ItemStack gearItem = this.slots.get(1).getItem();
        if (gearItem.getTag().contains("atium_mod.copper_cloud")) {
            return gearItem.getTag().getInt("atium_mod.copper_cloud");
        } else {
            return 0;
        }
    }

    //adds metal tag to output item
    private void addMetalTag() {
        this.slots.get(2).getItem().getTag().putInt("atium_mod.metal", hasMetal());
    }

    //adds copper cloud tag to output item
    private void addCopperTag(int isCopper) {
        //0 = false, 1 = true
        ItemStack outputItem = this.slots.get(2).getItem();
        outputItem.getTag().putInt("atium_mod.copper_cloud", isCopper);
    }

    //for infusing aluminium, removes all infusion tags
    private void removeMetalTags() {
        ItemStack outputItem = this.slots.get(2).getItem();
        outputItem.getTag().remove("atium_mod.metal");
        outputItem.getTag().remove("atium_mod.copper_cloud");
    }

    //called when menu is closed (puts items from input slots back into players inventory)
    @Override
    public void removed(Player pPlayer) {
        super.removed(pPlayer);
        this.clearContainer(player, this.inputSlots);
    }

    //if still close enough to access inventory
    //e.g. if player is in mine-cart and moves too far away from block
    @Override
    public boolean stillValid(Player pPlayer) {
        return this.access.evaluate((p_39785_, p_39786_) -> {
            return this.isValidBlock(p_39785_.getBlockState(p_39786_)) && pPlayer.distanceToSqr((double) p_39786_.getX() + 0.5D, (double) p_39786_.getY() + 0.5D, (double) p_39786_.getZ() + 0.5D) <= 64.0D;
        }, true);
    }
}