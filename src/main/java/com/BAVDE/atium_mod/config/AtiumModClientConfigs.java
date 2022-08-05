package com.BAVDE.atium_mod.config;

import net.minecraftforge.common.ForgeConfigSpec;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class AtiumModClientConfigs {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    //public static final ForgeConfigSpec.ConfigValue<String> ARMOUR_BAR_PLACEMENT;

    static {
        BUILDER.push("Configs for the Atium Mod");

       //ARMOUR_BAR_PLACEMENT = BUILDER.comment("Specify where the armour bars are located on screen").defineList);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
