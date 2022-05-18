package com.BAVDE.atium_mod.util;

import com.BAVDE.atium_mod.item.ModItems;
import com.BAVDE.atium_mod.item.ModTiers;
import com.BAVDE.atium_mod.item.custom.AtiumCompass;
import com.google.common.collect.Maps;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CompassItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class ModItemProperties {
    public static void addCustomItemProperties() {

        InfusableItem(ModItems.ATIUM_HELMET.get());
        InfusableItem(ModItems.ATIUM_CHESTPLATE.get());
        InfusableItem(ModItems.ATIUM_LEGGINGS.get());
        InfusableItem(ModItems.ATIUM_BOOTS.get());

        InfusableItem(ModItems.ATIUM_SWORD.get());
        InfusableItem(ModItems.ATIUM_PICKAXE.get());
        InfusableItem(ModItems.ATIUM_AXE.get());
        InfusableItem(ModItems.ATIUM_SHOVEL.get());
        InfusableItem(ModItems.ATIUM_HOE.get());

        ModCompassItemUse(ModItems.ATIUM_COMPASS.get());
        ModCompassItemWobble(ModItems.ATIUM_COMPASS.get());
    }

    static void InfusableItem(Item item) {
        ItemProperties.register(item, new ResourceLocation("metal"), (itemStack, clientLevel, livingEntity, i) -> {
            Entity entity = (Entity) (livingEntity != null ? livingEntity : itemStack.getEntityRepresentation());
            int metal = 0;

            if (entity != null) {
                metal = itemStack.getTag().getInt("atium_mod.metal");
            }
            return metal;
        });
    }

    static void ModCompassItemUse(Item item) {
        ItemProperties.register(item, new ResourceLocation("using"), (itemStack, clientLevel, livingEntity, i) -> {
            return livingEntity != null && AtiumCompass.isUsing(itemStack) ? 1.0F : 0.0F;
        });
    }

    static void ModCompassItemWobble(Item item) {
        ItemProperties.register(item, new ResourceLocation("angle"), new ClampedItemPropertyFunction() {
            private final ModItemProperties.CompassWobble wobble = new ModItemProperties.CompassWobble();
            private final ModItemProperties.CompassWobble wobbleRandom = new ModItemProperties.CompassWobble();

            @Override
            public float unclampedCall(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int pSeed) {
                Entity entity = (livingEntity != null ? livingEntity : itemStack.getEntityRepresentation());
                if (entity == null) {
                    return 0.0F;
                } else {
                    if (clientLevel == null && entity.level instanceof ClientLevel) {
                        clientLevel = (ClientLevel) entity.level;
                    }

                    BlockPos blockpos = this.getSpawnPosition(clientLevel);
                    long gameTime = clientLevel.getGameTime();

                    if (blockpos != null && !(entity.position().distanceToSqr((double) blockpos.getX() + 0.5D, entity.position().y(), (double) blockpos.getZ() + 0.5D) < (double) 1.0E-5F)) {
                        //direction wobble
                        double d1;
                        d1 = livingEntity.getYRot();
                        d1 = Mth.positiveModulo(d1 / 360.0D, 1.0D);

                        double d2 = this.getAngleTo(Vec3.atCenterOf(blockpos), entity) / (double) ((float) Math.PI * 2F);
                        double d3;

                        boolean flag = ((Player) livingEntity).isLocalPlayer();
                        if (flag) {
                            if (this.wobble.shouldUpdate(gameTime)) {
                                this.wobble.update(gameTime, 0.5D - (d1 - 0.25D));
                            }
                            d3 = d2 + this.wobble.rotation;
                        } else {
                            d3 = 0.5D - (d1 - 0.25D - d2);
                        }
                        return Mth.positiveModulo((float) d3, 1.0F);
                    } else {
                        //random wobble
                        if (this.wobbleRandom.shouldUpdate(gameTime)) {
                            this.wobbleRandom.update(gameTime, Math.random());
                        }
                        double d0 = this.wobbleRandom.rotation + (double) ((float) this.hash(pSeed) / 2.14748365E9F);
                        return Mth.positiveModulo((float) d0, 1.0F);
                    }
                }
            }

            private int hash(int i) {
                return i * 1327217883;
            }

            @javax.annotation.Nullable
            private BlockPos getSpawnPosition(ClientLevel clientLevel) {
                return clientLevel.dimensionType().natural() ? clientLevel.getSharedSpawnPos() : null;
            }

            private double getAngleTo(Vec3 vec3, Entity entity) {
                return Math.atan2(vec3.z() - entity.getZ(), vec3.x() - entity.getX());
            }

            @Nullable
            private BlockPos getStructurePosition(ClientLevel clientLevel) {
                if (item instanceof AtiumCompass) {
                    //locate structure here
                    return clientLevel.dimensionType().natural() ? clientLevel.getSharedSpawnPos() : null;
                } else {
                    return null;
                }
            }
        });
    }

    @OnlyIn(Dist.CLIENT)
    static class CompassWobble {
        double rotation;
        private double deltaRotation;
        private long lastUpdateTick;

        boolean shouldUpdate(long pGameTime) {
            return this.lastUpdateTick != pGameTime;
        }

        void update(long pGameTime, double pWobbleAmount) {
            this.lastUpdateTick = pGameTime;
            double d0 = pWobbleAmount - this.rotation;
            d0 = Mth.positiveModulo(d0 + 0.5D, 1.0D) - 0.5D;
            this.deltaRotation += d0 * 0.1D;
            this.deltaRotation *= 0.8D;
            this.rotation = Mth.positiveModulo(this.rotation + this.deltaRotation, 1.0D);
        }
    }
}