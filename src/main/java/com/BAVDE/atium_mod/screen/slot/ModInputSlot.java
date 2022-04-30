package com.BAVDE.atium_mod.screen.slot;

import com.BAVDE.atium_mod.screen.InfusingTableMenu;
import com.BAVDE.atium_mod.screen.ModMenuTypes;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class ModInputSlot extends SlotItemHandler {
    public ModInputSlot(IItemHandler itemHandler, int index, int x, int y) {
        super(itemHandler, index, x, y);
    }
}
