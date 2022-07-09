package com.BAVDE.atium_mod.event;

import com.BAVDE.atium_mod.AtiumMod;
import com.BAVDE.atium_mod.item.ModItems;
import com.BAVDE.atium_mod.particle.ModParticles;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.MovementInputUpdateEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
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

    /**
     * EVENTS
     **/

    @SubscribeEvent
    public static void onAttacked(LivingAttackEvent livingAttackEvent) {
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
                case 5 -> bootsBrass(livingAttackEvent);
                //case 6 -> bootsZinc(livingAttackEvent, player);
                case 9 -> bootsGold(player, level);
            }
        }
    }

    @SubscribeEvent
    public static void onHurt(LivingHurtEvent livingHurtEvent) {
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
        //atium boots
        ItemStack bootsItem = player.getItemBySlot(EquipmentSlot.FEET);
        if (bootsItem.getItem() == ModItems.ATIUM_BOOTS.get() && bootsItem.getTag().contains("atium_mod.metal")) {
            int currentMetal = bootsItem.getTag().getInt("atium_mod.metal");
            switch (currentMetal) {
                case 6 -> bootsZinc(livingHurtEvent, player);
            }
        }
    }

    @SubscribeEvent
    public static void onEquipmentChange(LivingEquipmentChangeEvent livingEquipmentChangeEvent) {
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

        //atium boots
        ItemStack bootsItem = player.getItemBySlot(EquipmentSlot.FEET);
        if (bootsItem.getItem() == ModItems.ATIUM_BOOTS.get() && bootsItem.getTag().contains("atium_mod.metal")) {
            int currentMetal = bootsItem.getTag().getInt("atium_mod.metal");
            switch (currentMetal) {
            }
        }
    }

    @SubscribeEvent
    public static void onJump(LivingEvent.LivingJumpEvent livingJumpEvent) {
        LivingEntity player = livingJumpEvent.getEntityLiving();
        Level level = player.getLevel();

        //atium boots
        ItemStack bootsItem = player.getItemBySlot(EquipmentSlot.FEET);
        if (bootsItem.getItem() == ModItems.ATIUM_BOOTS.get() && bootsItem.getTag().contains("atium_mod.metal")) {
            int currentMetal = bootsItem.getTag().getInt("atium_mod.metal");
            switch (currentMetal) {
                case 4 -> bootsPewter((Player) player, level);
            }
        }
    }

    /**
     * INFUSIONS FUNCTIONALITY
     **/

    //chance to push nearby mobs away on damage
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

    //removes night vision if tin helmet removed (safety check)
    private static void helmetTinOff(LivingEntity player) {
        if (player.hasEffect(MobEffects.NIGHT_VISION)) {
            player.removeEffect(MobEffects.NIGHT_VISION);
        }
    }

    //chance to set attacker on fire
    private static void chestplateBrass(LivingEntity player, LivingHurtEvent livingHurtEvent) {
        if (Math.random() < 0.15) { //15%
            int seconds = 5;
            LivingEntity pAttacker = (LivingEntity) livingHurtEvent.getSource().getEntity();
            if (!pAttacker.isOnFire()) {
                pAttacker.setSecondsOnFire(seconds);
            } else {
                int fireTicks = pAttacker.getRemainingFireTicks();
                pAttacker.setRemainingFireTicks(fireTicks + (seconds * 20));
            }
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

    //adds 4 hp (2 hearts)
    private static void chestplatePewterOn(LivingEntity player) {
        player.getAttribute(Attributes.MAX_HEALTH).setBaseValue(player.getAttributeValue(Attributes.MAX_HEALTH) + 4.0D);
    }

    //takes 4 hp (2 hearts)
    private static void chestplatePewterOff(LivingEntity player) {
        if (player.getHealth() > player.getMaxHealth() - 4) {
            player.setHealth(player.getMaxHealth() - 4);
        }
        player.getAttribute(Attributes.MAX_HEALTH).setBaseValue(player.getAttributeValue(Attributes.MAX_HEALTH) - 4.0D);
    }

    //big jump when crouch jump
    private static void bootsPewter(Player player, Level level) {
        if (player.isCrouching() && !player.hasEffect(MobEffects.JUMP) && player.isOnGround()) {
            //jump indication (sound & particles)
            level.playLocalSound(player.getX(), player.getY(), player.getZ(), SoundEvents.ARMOR_EQUIP_ELYTRA, SoundSource.PLAYERS, 2, 1, false);
            for (int i = 0; i < 8; i++) {
                level.addParticle(ModParticles.FALLING_SMOKE_PARTICLES.get(), player.getRandomX(1), player.getY(), player.getRandomZ(1), 0, 0, 0);
            }
            //actual jump
            double jumpPower = 0.3D; /*extra jump power (e.g. 0.3D = 3 blocks)*/

            double d0 = (double) 0.42F * getBlockJumpFactor(player) + jumpPower;
            Vec3 vec3 = player.getDeltaMovement();
            player.setDeltaMovement(vec3.x, d0, vec3.z);
            player.hasImpulse = true;
        }
    }

    //prevents damage from magma blocks
    private static void bootsBrass(LivingAttackEvent livingAttackEvent) {
        if (livingAttackEvent.getSource() == DamageSource.HOT_FLOOR) {
            livingAttackEvent.setCanceled(true);
        }
    }

    //deflects projectiles
    private static void chestplateZinc(LivingAttackEvent livingAttackEvent) {
        Entity damageSourceEntity = livingAttackEvent.getSource().getDirectEntity();
        if (damageSourceEntity instanceof AbstractArrow || damageSourceEntity instanceof ThrowableItemProjectile) {
            if (Math.random() < 0.2) { //20%
                if (livingAttackEvent.isCancelable()) {
                    livingAttackEvent.setCanceled(true);
                    livingAttackEvent.getSource().getDirectEntity().playSound(SoundEvents.SHIELD_BLOCK, 4.0F, 1.0F);
                }
            }
        }
    }

    private static void bootsZinc(LivingHurtEvent event, LivingEntity player) {
        if (Math.random() < 0.5) {
            //half damage
            float originalAmount = event.getAmount();
            event.setAmount((float)originalAmount / 2);

            Entity attacker = event.getSource().getDirectEntity();
            //knockback
            double pX = attacker.getX() - player.getX();
            double pZ;
            for (pZ = attacker.getZ() - player.getZ(); pX * pX + pZ * pZ < 1.0E-4D; pZ = (Math.random() - Math.random()) * 0.01D) {
                pX = (Math.random() - Math.random()) * 0.01D;
            }
            int modifier = 10;
            player.push(pX * modifier, 0, pZ * modifier);

            event.getEntityLiving().playSound(SoundEvents.ARMOR_EQUIP_ELYTRA, 2.0F, 1.0F);
            //player.level.playSound((Player) player, player.getX(), player.getY(), player.getZ(), SoundEvents.ARMOR_EQUIP_ELYTRA, SoundSource.PLAYERS, 2, 1);
            //player.level.playLocalSound(player.getX(), player.getY(), player.getZ(), SoundEvents.ARMOR_EQUIP_ELYTRA, SoundSource.PLAYERS, 2, 1, false);
        }
    }

    private static void bootsGold(LivingEntity player, Level level) {
        if (Math.random() < 0.15) {
            //cloud properties
            AreaEffectCloud areaeffectcloud = new AreaEffectCloud(level, player.getX(), player.getY(), player.getZ());
            areaeffectcloud.setOwner(player);
            areaeffectcloud.setRadius(3.0F);
            areaeffectcloud.setRadiusOnUse(-0.3F);
            areaeffectcloud.setWaitTime(4);
            areaeffectcloud.setRadiusPerTick(-areaeffectcloud.getRadius() / (float) areaeffectcloud.getDuration());
            areaeffectcloud.setPotion(Potions.HEALING);
            areaeffectcloud.addEffect(new MobEffectInstance(MobEffects.HEAL));

            //add cloud to world
            level.addFreshEntity(areaeffectcloud);
        }
    }

    /**
     * UTILS
     **/

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

    protected static float getBlockJumpFactor(Player player) {
        return player.level.getBlockState(player.blockPosition()).getBlock().getJumpFactor();
    }

    @SubscribeEvent
    public static void resetFreezeOnRespawn(ClientPlayerNetworkEvent.RespawnEvent respawnEvent) {
        respawnEvent.getNewPlayer().setTicksFrozen(0);
    }
}