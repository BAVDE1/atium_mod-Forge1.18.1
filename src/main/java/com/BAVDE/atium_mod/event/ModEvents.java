package com.BAVDE.atium_mod.event;

import com.BAVDE.atium_mod.AtiumMod;
import com.BAVDE.atium_mod.item.ModItems;
import com.BAVDE.atium_mod.particle.ModParticles;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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

    //dash key used to make sure player releases key to perform dash
    static boolean leftDashKeyPressed = false;
    static boolean rightDashKeyPressed = false;

    /**
     * EVENTS 
     * (events NEED to be static or else they won't be called)
     **/

    @SubscribeEvent
    public static void onAttacked(LivingAttackEvent event) {
        LivingEntity player = event.getEntityLiving();
        Level level = player.getLevel();

        //atium chestplate
        if (isAtiumChestplate(getChestplateItem(player)) && hasMetalTag(getChestplateItem(player))) {
            switch (getMetalTag(getChestplateItem(player))) {
                case 6 -> chestplateZinc(0.2, event); //20% deflects projectiles
            }
        }
        //atium boots
        if (isAtiumBoots(getBootsItem(player)) && hasMetalTag(getBootsItem(player))) {
            switch (getMetalTag(getBootsItem(player))) {
                case 5 -> bootsBrass(event); //prevents all damage from magma blocks
                case 9 -> bootsGold(0.15, player, level); //15% creates healing cloud around player
            }
        }
    }

    @SubscribeEvent
    public static void onHurt(LivingHurtEvent event) {
        LivingEntity player = event.getEntityLiving();
        Level level = player.getLevel();

        //atium chestplate
        if (isAtiumChestplate(getChestplateItem(player)) && hasMetalTag(getChestplateItem(player))) {
            switch (getMetalTag(getChestplateItem(player))) {
                case 2 -> chestplateSteel(0.15, 6.0D, level, player); //15% chance to push nearby mobs away on damage
                case 5 -> chestplateBrass(0.15, 5, player, event); //chance to set attacker on fire
            }
        }
        //atium boots
        if (isAtiumBoots(getBootsItem(player)) && hasMetalTag(getBootsItem(player))) {
            switch (getMetalTag(getBootsItem(player))) {
                case 6 -> bootsZinc(0.1, 2F, event, player, level); //10% to take half damage and push away from damage source
            }
        }
    }

    @SubscribeEvent
    public static void onEquipmentChange(LivingEquipmentChangeEvent event) {
        LivingEntity player = event.getEntityLiving();
        ItemStack itemStackTo = event.getTo();
        ItemStack itemStackFrom = event.getFrom();

        if (event.getSlot() == EquipmentSlot.CHEST) {
            //ON
            if (isAtiumChestplate(itemStackTo) && hasMetalTag(itemStackTo)) {
                switch (getMetalTag(itemStackTo)) {
                    case 4 -> chestplatePewterOn(4.0D, player); //adds 4 hp (2 hearts)
                }
            }
            //OFF
            if (isAtiumChestplate(itemStackFrom) && hasMetalTag(itemStackFrom)) {
                switch (getMetalTag(itemStackFrom)) {
                    case 4 -> chestplatePewterOff(4.0D, player); //takes 4 hp (2 hearts)
                }
            }
        }
        if (event.getSlot() == EquipmentSlot.HEAD) {
            //ON
            //OFF
            if (isAtiumHelmet(itemStackFrom) && hasMetalTag(itemStackFrom)) {
                switch (getMetalTag(itemStackFrom)) {
                    case 3 -> helmetTinOff(player); //removes night vision if tin helmet removed or breaks (safety check)
                }
            }
        }
    }

    @SubscribeEvent
    public static void onJump(LivingEvent.LivingJumpEvent event) {
        LivingEntity player = event.getEntityLiving();
        Level level = player.getLevel();

        //atium boots
        if (isAtiumBoots(getBootsItem(player)) && hasMetalTag(getBootsItem(player))) {
            switch (getMetalTag(getBootsItem(player))) {
                case 4 -> bootsPewter(0.3D, (Player) player, level); //big jump when crouch jump
            }
        }
    }

    @SubscribeEvent
    public static void onMovementInput(MovementInputUpdateEvent event) {
        Player player = event.getPlayer();
        Level level = player.getLevel();

        //atium boots
        if (isAtiumBoots(getBootsItem(player)) && hasMetalTag(getBootsItem(player))) {
            switch (getMetalTag(getBootsItem(player))) {
                case 2 -> bootsSteel(6, 1, 20, event, player, getBootsItem(player)); //dash if player double taps left or right (doublePressTickSpeed is leeway in ticks for a double tap recognition)
            }
        }
    }

    /**
     * INFUSIONS FUNCTIONALITY
     **/

    //chance to push nearby mobs away on damage
    private static void chestplateSteel(double chance, double range, Level level, LivingEntity player) {
        if (Math.random() < chance) {
            //code explained in iron method, atium sword
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

    //dash if player double taps left or right
    private static void bootsSteel(int doubleTapTickSpeed, int dashStrength, int cooldownTicks, MovementInputUpdateEvent event, Player player, ItemStack itemStack) {
        boolean leftKey = event.getInput().left;
        boolean rightKey = event.getInput().right;

        //if item is not on cooldown
        if (!onCooldown(player, itemStack)) {
            //LEFT DASH
            if (leftKey) {
                if (hasLeftDashTag(itemStack) && !leftDashKeyPressed) {
                    dashParticlesAndSound(player);

                    //dash push direction
                    Vec3 looking = player.getLookAngle().multiply(dashStrength, 0, dashStrength).normalize();
                    player.push(looking.z, 0, -looking.x);

                    //remove tag
                    itemStack.getTag().remove("atium_mod.left_dash_ready");
                    //adds cooldown
                    player.getCooldowns().addCooldown(itemStack.getItem(), cooldownTicks);
                } else if (!leftDashKeyPressed) {
                    //else if item does not have dash tag, add the tag
                    itemStack.getTag().putInt("atium_mod.left_dash_ready", doubleTapTickSpeed);
                    leftDashKeyPressed = true;
                }
            } else {
                leftDashKeyPressed = false;
            }

            //RIGHT DASH
            if (rightKey) {
                if (hasRightDashTag(itemStack) && !rightDashKeyPressed) {
                    dashParticlesAndSound(player);

                    //dash push direction
                    Vec3 look = player.getLookAngle().multiply(dashStrength, 0, dashStrength).normalize();
                    player.push(-look.z, 0, look.x);

                    //remove tag
                    itemStack.getTag().remove("atium_mod.right_dash_ready");
                    //adds cooldown
                    player.getCooldowns().addCooldown(itemStack.getItem(), cooldownTicks);
                } else if (!rightDashKeyPressed) {
                    //else if item does not have dash tag, add the tag
                    itemStack.getTag().putInt("atium_mod.right_dash_ready", doubleTapTickSpeed);
                    rightDashKeyPressed = true;
                }
            } else {
                rightDashKeyPressed = false;
            }
        }
    }

    //removes night vision if tin helmet removed (safety check)
    private static void helmetTinOff(LivingEntity player) {
        if (player.hasEffect(MobEffects.NIGHT_VISION)) {
            player.removeEffect(MobEffects.NIGHT_VISION);
        }
    }

    //chance to set attacker on fire
    private static void chestplateBrass(double chance, int secondsOnFire, LivingEntity player, LivingHurtEvent event) {
        if (Math.random() < chance) { //15%
            int seconds = secondsOnFire;
            LivingEntity pAttacker = (LivingEntity) event.getSource().getEntity();
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
    private static void chestplatePewterOn(double hpAmount, LivingEntity player) {
        player.getAttribute(Attributes.MAX_HEALTH).setBaseValue(player.getAttributeValue(Attributes.MAX_HEALTH) + hpAmount);
    }

    //takes 4 hp (2 hearts)
    private static void chestplatePewterOff(double hpAmount, LivingEntity player) {
        if (player.getHealth() > player.getMaxHealth() - hpAmount) {
            player.setHealth(player.getMaxHealth() - (float) hpAmount);
        }
        player.getAttribute(Attributes.MAX_HEALTH).setBaseValue(player.getAttributeValue(Attributes.MAX_HEALTH) - hpAmount);
    }

    //big jump when crouch jump
    private static void bootsPewter(double jumpPower, Player player, Level level) {
        if (player.isCrouching() && !player.hasEffect(MobEffects.JUMP) && player.isOnGround()) {
            //jump indication (sound & particles)
            level.playLocalSound(player.getX(), player.getY(), player.getZ(), SoundEvents.ARMOR_EQUIP_ELYTRA, SoundSource.PLAYERS, 2, 1, false);
            if (level.isClientSide) {
                //particles
                for (int i = 0; i < 8; i++) {
                    level.addParticle(ModParticles.FALLING_SMOKE_PARTICLES.get(), player.getRandomX(1), player.getY(), player.getRandomZ(1), 0, 0, 0);
                }
            }

            //actual jump
            double d0 = (double) 0.42F * getBlockJumpFactor(player) + jumpPower; //extra jumpPower (e.g. 0.3D = 3 blocks)
            Vec3 vec3 = player.getDeltaMovement();
            player.setDeltaMovement(vec3.x, d0, vec3.z);
            player.hasImpulse = true;
        }
    }

    //prevents all damage from magma blocks
    private static void bootsBrass(LivingAttackEvent event) {
        if (event.getSource() == DamageSource.HOT_FLOOR) {
            event.setCanceled(true);
        }
    }

    //deflects projectiles
    private static void chestplateZinc(double chance, LivingAttackEvent event) {
        Entity damageSourceEntity = event.getSource().getDirectEntity();
        if (damageSourceEntity instanceof AbstractArrow || damageSourceEntity instanceof ThrowableItemProjectile) {
            if (Math.random() < chance) {
                if (event.isCancelable()) {
                    event.setCanceled(true);
                    event.getSource().getDirectEntity().playSound(SoundEvents.SHIELD_BLOCK, 4.0F, 1.0F);
                }
            }
        }
    }

    //take 50% damage and push away from damage source
    private static void bootsZinc(double chance, float damageDivision,  LivingHurtEvent event, LivingEntity player, Level level) {
        if (Math.random() < chance) {
            Entity attacker = event.getSource().getDirectEntity();
            if (attacker != null) {
                //half damage
                float originalAmount = event.getAmount();
                event.setAmount(originalAmount / damageDivision);
                if (level.isClientSide) {
                    //particles
                    for (int i = 0; i < 8; i++) {
                        level.addParticle(ModParticles.FALLING_SMOKE_PARTICLES.get(), player.getRandomX(1), player.getY(), player.getRandomZ(1), 0, 0, 0);
                    }
                }
                //knockback
                double pX = player.getX() - attacker.getX();
                double pZ;
                for (pZ = player.getZ() - attacker.getZ(); pX * pX + pZ * pZ < 1.0E-4D; pZ = (Math.random() - Math.random()) * 0.01D) {
                    pX = (Math.random() - Math.random()) * 0.01D;
                }
                int modifier = 4;
                player.push(pX * modifier, 0, pZ * modifier);
                //sound
                attacker.playSound(SoundEvents.ARMOR_EQUIP_ELYTRA, 2.0F, 1.0F);
            }
        }

    }

    //creates healing cloud around player
    private static void bootsGold(double chance, LivingEntity player, Level level) {
        if (Math.random() < chance) {
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
                    if (level.isClientSide) {
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
    }

    private static void dashParticlesAndSound(Player player) {
        //particles
        if (player.level.isClientSide) {
            for (int i = 0; i < 8; i++) {
                player.level.addParticle(ModParticles.FALLING_SMOKE_PARTICLES.get(), player.getRandomX(1), player.getY(), player.getRandomZ(1), 0, 0, 0);
            }
        }
        //sound
        player.playSound(SoundEvents.ARMOR_EQUIP_ELYTRA, 3.0F, 1.0F);
    }

    protected static float getBlockJumpFactor(Player player) {
        return player.level.getBlockState(player.blockPosition()).getBlock().getJumpFactor();
    }

    @SubscribeEvent
    public static void resetFreezeOnRespawn(ClientPlayerNetworkEvent.RespawnEvent event) {
        event.getNewPlayer().setTicksFrozen(0);
    }
    
    /**
    * ITEM CHECKS
    */

    //returns itemstack of what is in certain equipment slot (returns ItemStack)
    private static ItemStack getHelmetItem(LivingEntity player) {
        return player.getItemBySlot(EquipmentSlot.HEAD);
    }
    private static ItemStack getChestplateItem(LivingEntity player) {
        return player.getItemBySlot(EquipmentSlot.CHEST);
    }
    private static ItemStack getBootsItem(LivingEntity player) {
        return player.getItemBySlot(EquipmentSlot.FEET);
    }

    //checks if item is correct gear (returns boolean)
    private static boolean isAtiumHelmet(ItemStack itemStack) {
        return itemStack.getItem() == ModItems.ATIUM_HELMET.get();
    }
    private static boolean isAtiumChestplate(ItemStack itemStack) {
        return itemStack.getItem() == ModItems.ATIUM_CHESTPLATE.get();
    }
    private static boolean isAtiumBoots(ItemStack itemStack) {
        return itemStack.getItem() == ModItems.ATIUM_BOOTS.get();
    }

    //checks for steel dash tag (returns boolean)
    private static boolean hasLeftDashTag(ItemStack itemStack) {
        return itemStack.getTag().contains("atium_mod.left_dash_ready");
    }
    private static boolean hasRightDashTag(ItemStack itemStack) {
        return itemStack.getTag().contains("atium_mod.right_dash_ready");
    }

    //checks if item is on cooldown (returns boolean)
    private static boolean onCooldown(Player player, ItemStack itemStack) {
        return player.getCooldowns().isOnCooldown(itemStack.getItem());
    }

    //checks if itemstack has metal tag (returns boolean)
    private static boolean hasMetalTag(ItemStack itemStack) {
        return itemStack.getTag().contains("atium_mod.metal");
    }

    //returns the metal tag of itemStack (returns int)
    private static int getMetalTag(ItemStack itemStack) {
        if (hasMetalTag(itemStack)) {
            return itemStack.getTag().getInt("atium_mod.metal");
        }
        //returns 0 if doesn't have metal tag
        return 0;
    }
}