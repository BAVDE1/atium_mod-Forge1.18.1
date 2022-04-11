package com.BAVDE.atium_mod.item.custom;

import com.BAVDE.atium_mod.item.ModArmourMaterials;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class AtiumChestplate extends ArmorItem {
    public static final BooleanProperty GOLD = BooleanProperty.create("gold");

    public AtiumChestplate(ArmorMaterial material, EquipmentSlot slot, Properties settings) {
        super(material, slot, settings);
    }

    @Override
    public void onArmorTick(ItemStack stack, Level world, Player player) {
        if(!world.isClientSide()) {
            if(hasArmorOn(player)) {
                if (hasCorrectArmorOn(ModArmourMaterials.ATIUM, player)) {
                    addStatusEffectForMaterial(player, new MobEffectInstance(MobEffects.REGENERATION, 20, 1));
                }
            }
        }
    }

    private void addStatusEffectForMaterial(Player player, MobEffectInstance mapStatusEffect) {
        boolean hasPlayerEffect = player.hasEffect(mapStatusEffect.getEffect());

        if(hasCorrectArmorOn(ModArmourMaterials.ATIUM, player) && !hasPlayerEffect) {
            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 20, 1));
        }
    }

    private boolean hasArmorOn(Player player) {
        ItemStack breastplate = player.getInventory().getArmor(2);
        return !breastplate.isEmpty();
    }

    private boolean hasCorrectArmorOn(ArmorMaterial material, Player player) {
        ArmorItem breastplate = ((ArmorItem)player.getInventory().getArmor(2).getItem());
        return breastplate.getMaterial() == material;
    }
}
