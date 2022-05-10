package com.BAVDE.atium_mod.event;

import com.BAVDE.atium_mod.AtiumMod;
import com.BAVDE.atium_mod.item.ModItems;
import com.BAVDE.atium_mod.particle.ModParticles;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Random;

@Mod.EventBusSubscriber(modid = AtiumMod.MOD_ID)
public class ModEvents {
    public final Random random = new Random();

    @SubscribeEvent
    public static void entityHurtEvent(LivingHurtEvent livingHurtEvent) {
        LivingEntity livingEntity = livingHurtEvent.getEntityLiving();
        ItemStack chestplateItem = livingEntity.getItemBySlot(EquipmentSlot.CHEST);
        Level level = livingEntity.getLevel();
        if (chestplateItem.getItem() == ModItems.ATIUM_CHESTPLATE.get() && chestplateItem.getTag().contains("atium_mod.metal")) {
            int currentMetal = chestplateItem.getTag().getInt("atium_mod.metal");
            switch (currentMetal) {
                case 2 -> chestplateSteel(level, livingEntity);
            }
        }
    }

    private static void chestplateSteel(Level level, LivingEntity livingEntity) {
        var chance = Math.random();
        if (chance < 1) { //20%
            //code explained in iron method, atium sword class
            AABB aabb = livingEntity.getBoundingBox().inflate(5.0D, 5.0D, 5.0D);
            List<LivingEntity> entityList = level.getNearbyEntities(LivingEntity.class, TargetingConditions.DEFAULT, livingEntity, aabb);
            for (int i = 0; i < entityList.size(); i++) {
                LivingEntity entity = entityList.get(i);
                double pX = livingEntity.getX() - entity.getX();
                double pZ;
                for (pZ = livingEntity.getZ() - entity.getZ(); pX * pX + pZ * pZ < 1.0E-4D; pZ = (Math.random() - Math.random()) * 0.01D) {
                    pX = (Math.random() - Math.random()) * 0.01D;
                }
                livingEntity.playSound(SoundEvents.EVOKER_CAST_SPELL, 4.0F, 1.0F);
                entity.knockback(1.0F, pX, pZ);
            }
        }
    }

    @SubscribeEvent
    public static void resetFreezeOnRespawn(ClientPlayerNetworkEvent.RespawnEvent respawnEvent) {
        respawnEvent.getNewPlayer().setTicksFrozen(0);
    }
}
