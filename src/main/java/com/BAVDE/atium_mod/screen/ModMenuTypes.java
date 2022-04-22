package com.BAVDE.atium_mod.screen;

import com.BAVDE.atium_mod.AtiumMod;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.CONTAINERS, AtiumMod.MOD_ID);

    //registered menus
    //public static final RegistryObject<MenuType<InfusingTableMenu>> INFUSING_TABLE_MENU = registerMenuType(InfusingTableMenu::new, "infusing_table_menu");

    public static final RegistryObject<MenuType<InfusingTableMenu>> INFUSING_TABLE_MENU = registerMenuType(InfusingTableMenu::new, "infusing_table_menu");


    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerMenuType(IContainerFactory<T> factory, String name) {
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }
    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
