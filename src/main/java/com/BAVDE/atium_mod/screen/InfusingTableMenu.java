package com.BAVDE.atium_mod.screen;

import com.BAVDE.atium_mod.block.ModBlocks;
import com.BAVDE.atium_mod.block.entity.InfusingTableBlockEntity;
import com.BAVDE.atium_mod.item.ModItems;
import com.BAVDE.atium_mod.recipe.InfusingTableRecipe;
import com.BAVDE.atium_mod.screen.slot.ModInputSlot;
import com.BAVDE.atium_mod.screen.slot.ModResultSlot;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.CapabilityItemHandler;

import java.util.List;
import java.util.Optional;

public class InfusingTableMenu extends AbstractInfusingMenu {
    private final InfusingTableBlockEntity blockEntity;
    private final List<InfusingTableRecipe> recipes;
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
        this.recipes = this.level.getRecipeManager().getAllRecipesFor(InfusingTableRecipe.Type.INSTANCE);

        this.blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(handler -> {
            //slotItemHandler determines position (in pixels) of slot in inventory
            //the coords start at 0 on the top right most pixel of inventory texture
            //+x -->  &  +y v
            //slot placement is top right corner of slot (not centre)
            //index should start at 0
            this.addSlot(new ModInputSlot(handler, 0, 79, 23) {
                public void setChanged() {
                    super.setChanged();
                    InfusingTableMenu.this.slotsChanged(this.container);
                }
            });
            this.addSlot(new ModInputSlot(handler, 1, 79, 57) {
                public void setChanged() {
                    super.setChanged();
                    InfusingTableMenu.this.slotsChanged(this.container);
                }
            });
            this.addSlot(new ModResultSlot(handler, 2, 79, 98) {
                public boolean mayPlace(ItemStack itemStack) {
                    return false;
                }

                public void onTake(Player player, ItemStack itemStack) {
                    InfusingTableMenu.this.onTake(player, itemStack);
                }
            });
        });
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
        BlockEntity blockEntity1 = level.getBlockEntity(pos);
        itemStack.onCraftedBy(player.level, player, itemStack.getCount());
        if (hasRecipe(blockEntity)) {
            this.shrinkStacks();
            if (blockEntity1 instanceof InfusingTableBlockEntity) {
                InfusingTableBlockEntity.createCraftParticles(0.05D, 1, player, pos);
            }
            this.playSound(pos);
            this.access.execute((level, blockPos) -> {
                level.levelEvent(1044, blockPos, 0);
            });
        }
    }

    private void playSound(BlockPos pos) {
        level.playSound((Player) null, pos, SoundEvents.SMITHING_TABLE_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
        level.playSound((Player) null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 0.6F, 0.8F + level.random.nextFloat() * 1.2F);
    }

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
        if (hasRecipe(blockEntity)) {
            Level level = blockEntity.getLevel();
            SimpleContainer inventory = new SimpleContainer(blockEntity.itemHandler.getSlots());
            for (int i = 0; i < blockEntity.itemHandler.getSlots(); i++) {
                inventory.setItem(i, blockEntity.itemHandler.getStackInSlot(i));
            }
            Optional<InfusingTableRecipe> match = level.getRecipeManager().getRecipeFor(InfusingTableRecipe.Type.INSTANCE, inventory, level);

            if (match.isPresent()) {
                if (!hasMetalTag()) {
                    blockEntity.itemHandler.setStackInSlot(2, new ItemStack(match.get().getResultItem().getItem(), 1));
                    this.copyTag();
                    this.addMetalTag();
                }
            }
        } else {
            blockEntity.itemHandler.setStackInSlot(2, ItemStack.EMPTY);
        }
    }

    private static boolean hasRecipe(InfusingTableBlockEntity entity) {
        Level level = entity.getLevel();
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }
        Optional<InfusingTableRecipe> match = level.getRecipeManager().getRecipeFor(InfusingTableRecipe.Type.INSTANCE, inventory, level);

        return match.isPresent();
    }

    public boolean isSlot0Empty() {
        ItemStack itemStack = this.slots.get(0).getItem();
        return itemStack == ItemStack.EMPTY;
    }

    public int hasMetal() {
        //1=iron, 2=steel, 3=tin, 4=pewter, 5=brass, 6=zinc, 7=copper, 8=bronze, 9=gold
        Item item = this.slots.get(0).getItem().getItem();
        int metal = 0;
        if (item == ModItems.REFINED_IRON.get()) {
            metal = 1;
        } else if (item == ModItems.STEEL.get()) {
            metal = 2;
        } else if (item == ModItems.TIN.get()) {
            metal = 3;
        } else if (item == ModItems.PEWTER.get()) {
            metal = 4;
        } else if (item == ModItems.BRASS.get()) {
            metal = 5;
        } else if (item == ModItems.ZINC.get()) {
            metal = 6;
        } else if (item == Items.COPPER_INGOT) {
            metal = 7;
        } else if (item == ModItems.BRONZE.get()) {
            metal = 8;
        } else if (item == ModItems.PURIFIED_GOLD.get()) {
            metal = 9;
        }
        return metal;
    }

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

    private void copyTag() {
        CompoundTag copyCompoundTag = blockEntity.itemHandler.getStackInSlot(1).getTag();
        blockEntity.itemHandler.getStackInSlot(2).setTag(copyCompoundTag.copy());
    }

    private boolean hasMetalTag() {
        return blockEntity.itemHandler.getStackInSlot(1).getTag().contains("atium_mod.metal");
    }

    private void addMetalTag() {
        blockEntity.itemHandler.getStackInSlot(2).getTag().putInt("atium_mod.metal", hasMetal());
    }

    //if still close enough to access inventory
    //e.g. if player is in mine-cart and moves too far away from block
    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), pPlayer, ModBlocks.INFUSING_TABLE.get());
    }
}