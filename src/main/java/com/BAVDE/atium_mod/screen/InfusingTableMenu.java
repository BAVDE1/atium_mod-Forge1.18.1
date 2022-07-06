package com.BAVDE.atium_mod.screen;

import com.BAVDE.atium_mod.block.ModBlocks;
import com.BAVDE.atium_mod.block.entity.InfusingTableBlockEntity;
import com.BAVDE.atium_mod.item.ModItems;
import com.BAVDE.atium_mod.screen.slot.ModInputSlot;
import com.BAVDE.atium_mod.screen.slot.ModResultSlot;
import com.BAVDE.atium_mod.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.CapabilityItemHandler;

public class InfusingTableMenu extends AbstractInfusingMenu {
    private final InfusingTableBlockEntity blockEntity;
    public final Level level;
    protected final Player player;
    protected final ContainerLevelAccess access;


    public InfusingTableMenu(int window, Inventory inv, FriendlyByteBuf friendlyByteBuf) {
        this(window, inv, inv.player.level.getBlockEntity(friendlyByteBuf.readBlockPos()), ContainerLevelAccess.NULL);
    }

    public InfusingTableMenu(int window, Inventory inv, BlockEntity entity, ContainerLevelAccess access) {
        super(ModMenuTypes.INFUSING_TABLE_MENU.get(), window, inv, entity);
        blockEntity = ((InfusingTableBlockEntity) entity);
        this.access = access;
        this.player = inv.player;
        this.level = inv.player.level;

        this.blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(handler -> {
            //slotItemHandler determines position (in pixels) of slot in inventory
            //the coords start at 0 on the top right most pixel of inventory texture
            //+x -->  &  +y v
            //slot placement is top right corner of slot (not centre)
            //index should start at 0

            //metal slot
            this.addSlot(new ModInputSlot(handler, 0, 79, 23) {
                public void setChanged() {
                    super.setChanged();
                    InfusingTableMenu.this.slotsChanged(this.container);
                }
            });

            //gear slot
            this.addSlot(new ModInputSlot(handler, 1, 79, 57) {
                public void setChanged() {
                    super.setChanged();
                    InfusingTableMenu.this.slotsChanged(this.container);
                }
            });

            //output slot
            this.addSlot(new ModResultSlot(handler, 2, 79, 98) {
                public boolean mayPlace(ItemStack itemStack) {
                    return false;
                }

                public void onTake(Player player, ItemStack itemStack) {
                    InfusingTableMenu.this.onTake(player, itemStack);
                }
            });
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
        BlockPos pos = blockEntity.getBlockPos();
        BlockEntity blockEntity = level.getBlockEntity(pos);
        itemStack.onCraftedBy(player.level, player, itemStack.getCount());
        //on craft
        if (hasRecipe()) {
            this.shrinkStacks();
            if (blockEntity instanceof InfusingTableBlockEntity) {
                InfusingTableBlockEntity.createCraftParticles(0.05D, 1, player, pos);
            }
            this.playSound(pos);
            this.access.execute((level, blockPos) -> {
                level.levelEvent(1044, blockPos, 0);
            });
        }
    }

    //plays sound on craft
    private void playSound(BlockPos pos) {
        level.playSound((Player) null, pos, SoundEvents.SMITHING_TABLE_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
        level.playSound((Player) null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 0.6F, 0.8F + level.random.nextFloat() * 1.2F);
    }

    //shrinks stacks in slot 0 & 1 by one on craft
    private void shrinkStacks() {
        blockEntity.itemHandler.extractItem(0, 1, false);
        blockEntity.itemHandler.extractItem(1, 1, false);
    }

    @Override
    protected boolean isValidBlock(BlockState blockState) {
        return true;
    }

    @Override
    public void createResult() {
        if (hasRecipe()) {
            ItemStack gearSlot = blockEntity.itemHandler.getStackInSlot(1);

            //Normal metal infusion (if not infused already and metal is not copper, bronze or aluminium (this doesn't like 'or' statements))
            if (!hasMetalTag() && hasMetal() != 7) { //not copper
                if (hasMetal() != 8) { //not bronze
                    if (hasMetal() != 10) { //not aluminium
                        blockEntity.itemHandler.setStackInSlot(2, gearSlot.copy());
                        this.addMetalTag();
                        this.addCopperTag(0);
                    }
                }

                //Copper infusion (else if the infusing metal is copper does not have copper infused)
            } else if (hasMetalTag() && hasMetal() == 7 && getCopperTag() == 0) {
                blockEntity.itemHandler.setStackInSlot(2, gearSlot.copy());
                this.addCopperTag(1);

                //Bronze infusion (else if the infusing metal is bronze and has copper cloud)
            } else if (hasMetalTag() && hasMetal() == 8 && getCopperTag() == 1) {
                blockEntity.itemHandler.setStackInSlot(2, gearSlot.copy());
                this.addCopperTag(0);

                //Aluminium infusion (else if gear is infused and infusing metal is aluminium)
            } else if (hasMetalTag() && hasMetal() == 10) {
                blockEntity.itemHandler.setStackInSlot(2, gearSlot.copy());
                this.removeMetalTags();
            }
        } else {
            blockEntity.itemHandler.setStackInSlot(2, ItemStack.EMPTY);
        }
    }

    //returns true if there is a valid metal in slot 0 and valid gear in slot 1
    private boolean hasRecipe() {
        return hasMetal() != 0 && hasAtiumItem() != 0;
    }

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

    //if still close enough to access inventory
    //e.g. if player is in mine-cart and moves too far away from block
    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), pPlayer, ModBlocks.INFUSING_TABLE.get());
    }
}