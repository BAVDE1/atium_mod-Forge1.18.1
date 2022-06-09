package com.BAVDE.atium_mod.event;

import com.BAVDE.atium_mod.AtiumMod;
import com.BAVDE.atium_mod.item.ModItems;
import com.BAVDE.atium_mod.particle.ModParticles;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.MovementInputUpdateEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Random;

@Mod.EventBusSubscriber(modid = AtiumMod.MOD_ID)
public class ModEvents {
    public final Random random = new Random();
    //1=iron, 2=steel, 3=tin, 4=pewter, 5=brass, 6=zinc, 7=copper, 8=bronze, 9=gold
    //attack event order: 1.LivingAttackEvent 2.LivingHurtEvent 3.LivingDamageEvent 4.LivingDeathEvent 5.Global Loot Modifiers

    /**** EVENTS ****/

    @SubscribeEvent
    public static void entityAttackEvent(LivingAttackEvent livingAttackEvent) {
        LivingEntity player = livingAttackEvent.getEntityLiving();
        Level level = player.getLevel();

        //atium chestplate
        ItemStack chestplateItem = player.getItemBySlot(EquipmentSlot.CHEST);
        if (chestplateItem.getItem() == ModItems.ATIUM_CHESTPLATE.get() && chestplateItem.getTag().contains("atium_mod.metal")) {
            int currentMetal = chestplateItem.getTag().getInt("atium_mod.metal");
            switch (currentMetal) {
                case 6 -> chestplateZinc(livingAttackEvent);
            }
        }
        //atium boots
        ItemStack bootsItem = player.getItemBySlot(EquipmentSlot.FEET);
        if (bootsItem.getItem() == ModItems.ATIUM_BOOTS.get() && bootsItem.getTag().contains("atium_mod.metal")) {
            int currentMetal = bootsItem.getTag().getInt("atium_mod.metal");
            switch (currentMetal) {
                case 6 -> bootsZinc(livingAttackEvent);
            }
        }
    }

    @SubscribeEvent
    public static void entityHurtEvent(LivingHurtEvent livingHurtEvent) {
        LivingEntity player = livingHurtEvent.getEntityLiving();
        Level level = player.getLevel();

        //atium chestplate
        ItemStack chestplateItem = player.getItemBySlot(EquipmentSlot.CHEST);
        if (chestplateItem.getItem() == ModItems.ATIUM_CHESTPLATE.get() && chestplateItem.getTag().contains("atium_mod.metal")) {
            int currentMetal = chestplateItem.getTag().getInt("atium_mod.metal");
            switch (currentMetal) {
                case 2 -> chestplateSteel(level, player);
                case 5 -> chestplateBrass(player, livingHurtEvent);
            }
        }
    }

    @SubscribeEvent
    public static void equipmentChangeEvent(LivingEquipmentChangeEvent livingEquipmentChangeEvent) {
        LivingEntity player = livingEquipmentChangeEvent.getEntityLiving();
        ItemStack itemStackTo = livingEquipmentChangeEvent.getTo();
        ItemStack itemStackFrom = livingEquipmentChangeEvent.getFrom();

        //atium chestplate
        if (livingEquipmentChangeEvent.getSlot() == EquipmentSlot.CHEST) {
            //ON
            if (itemStackTo.getItem() == ModItems.ATIUM_CHESTPLATE.get() && itemStackTo.getTag().contains("atium_mod.metal")) {
                int currentMetal = itemStackTo.getTag().getInt("atium_mod.metal");
                switch (currentMetal) {
                    case 4 -> chestplatePewterOn(player);
                }
            }
            //OFF
            if (itemStackFrom.getItem() == ModItems.ATIUM_HELMET.get() && itemStackFrom.getTag().contains("atium_mod.metal")) {
                int currentMetal = itemStackFrom.getTag().getInt("atium_mod.metal");
                switch (currentMetal) {
                    case 3 -> helmetTinOff(player);
                }
            }
            if (itemStackFrom.getItem() == ModItems.ATIUM_CHESTPLATE.get() && itemStackFrom.getTag().contains("atium_mod.metal")) {
                int currentMetal = itemStackFrom.getTag().getInt("atium_mod.metal");
                switch (currentMetal) {
                    case 4 -> chestplatePewterOff(player);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onMove(MovementInputUpdateEvent movementInputUpdateEvent) {
        LivingEntity player = movementInputUpdateEvent.getEntityLiving();
        Level level = player.getLevel();

        //atium chestplate
        ItemStack bootsItem = player.getItemBySlot(EquipmentSlot.FEET);
        if (bootsItem.getItem() == ModItems.ATIUM_BOOTS.get() && bootsItem.getTag().contains("atium_mod.metal")) {
            int currentMetal = bootsItem.getTag().getInt("atium_mod.metal");
            switch (currentMetal) {
            }
        }

    }

    /**** INFUSIONS FUNCTIONALITY ****/

    private static void chestplateSteel(Level level, LivingEntity player) {
        if (Math.random() < 0.15) { //15%
            //code explained in iron method, atium sword
            var range = 6.0D;
            AABB aabb = player.getBoundingBox().inflate(range, range, range);
            List<LivingEntity> entityList = level.getNearbyEntities(LivingEntity.class, TargetingConditions.DEFAULT, player, aabb);
            for (LivingEntity entity : entityList) {
                double pX = player.getX() - entity.getX();
                double pZ;
                for (pZ = player.getZ() - entity.getZ(); pX * pX + pZ * pZ < 1.0E-4D; pZ = (Math.random() - Math.random()) * 0.01D) {
                    pX = (Math.random() - Math.random()) * 0.01D;
                }
                entity.knockback(1.0F, pX, pZ);
            }
            //sound not working atm
            level.playSound((Player) player, player, SoundEvents.EVOKER_CAST_SPELL, SoundSource.PLAYERS, 4.0F, 1.0F);
            createForceFieldParticles(0.7D, 4, player);
        }
    }

    private static void helmetTinOff(LivingEntity player) {
        if (player.hasEffect(MobEffects.NIGHT_VISION)) {
            player.removeEffect(MobEffects.NIGHT_VISION);
        }
    }

    private static void chestplateBrass(LivingEntity player, LivingHurtEvent livingHurtEvent) {
        if (Math.random() < 0.15) { //15%
            LivingEntity pAttacker = (LivingEntity) livingHurtEvent.getSource().getEntity();
            pAttacker.setSecondsOnFire(5);
            pAttacker.playSound(SoundEvents.FIRECHARGE_USE, 4.0F, 1.0F);
            //knockback
            double pX = player.getX() - pAttacker.getX();
            double pZ;
            for (pZ = player.getZ() - pAttacker.getZ(); pX * pX + pZ * pZ < 1.0E-4D; pZ = (Math.random() - Math.random()) * 0.01D) {
                pX = (Math.random() - Math.random()) * 0.01D;
            }
            pAttacker.knockback(0.3F, pX, pZ);
        }
    }

    private static void chestplatePewterOn(LivingEntity player) {
        player.getAttribute(Attributes.MAX_HEALTH).setBaseValue(player.getAttributeValue(Attributes.MAX_HEALTH) + 4.0D);
    }

    private static void chestplatePewterOff(LivingEntity player) {
        if (player.getHealth() > player.getMaxHealth() - 4) {
            player.setHealth(player.getMaxHealth() - 4);
        }
        player.getAttribute(Attributes.MAX_HEALTH).setBaseValue(player.getAttributeValue(Attributes.MAX_HEALTH) - 4.0D);
    }

    private static void bootsZinc(LivingAttackEvent livingAttackEvent) {
        if (livingAttackEvent.getSource() == DamageSource.HOT_FLOOR) {
            livingAttackEvent.setCanceled(true);
        }
    }

    private static void chestplateZinc(LivingAttackEvent livingAttackEvent) {
        if (livingAttackEvent.getSource().getDirectEntity() instanceof AbstractArrow) {
            if (Math.random() < 0.2) { //15%
                if (livingAttackEvent.isCancelable()) {
                    livingAttackEvent.setCanceled(true);
                    livingAttackEvent.getSource().getDirectEntity().playSound(SoundEvents.SHIELD_BLOCK, 4.0F, 1.0F);
                }
            }
        }
    }

    private static void createForceFieldParticles(double pSpeed, int pSize, LivingEntity player) {
        Level level = player.getLevel();
        Minecraft minecraft = Minecraft.getInstance();
        double d0 = player.getX();
        double d1 = player.getY();
        double d2 = player.getZ();
        for (int i = -pSize; i <= pSize; ++i) {
            for (int j = -pSize; j <= pSize; ++j) {
                for (int k = -pSize; k <= pSize; ++k) {
                    double d3 = (double) j + (level.random.nextDouble() - level.random.nextDouble()) * 0.5D;
                    double d4 = (double) i + (level.random.nextDouble() - level.random.nextDouble()) * 0.5D;
                    double d5 = (double) k + (level.random.nextDouble() - level.random.nextDouble()) * 0.5D;
                    double d6 = Math.sqrt(d3 * d3 + d4 * d4 + d5 * d5) / pSpeed + level.random.nextGaussian() * 0.05D;
                    minecraft.particleEngine.createParticle(ModParticles.FORCE_FIELD_PARTICLES.get(), d0, d1, d2, d3 / d6, d4 / d6, d5 / d6);
                    if (i != -pSize && i != pSize && j != -pSize && j != pSize) {
                        k += pSize * 2 - 1;
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void resetFreezeOnRespawn(ClientPlayerNetworkEvent.RespawnEvent respawnEvent) {
        respawnEvent.getNewPlayer().setTicksFrozen(0);
    }
}
