package com.BAVDE.atium_mod.entity.projectile;

import com.BAVDE.atium_mod.entity.ModEntityTypes;
import com.BAVDE.atium_mod.item.ModItems;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class SteelCoinProjectile extends ThrowableItemProjectile {
    public SteelCoinProjectile(EntityType<? extends ThrowableItemProjectile> entityType, Level level) {
        super(entityType, level);
    }

    public SteelCoinProjectile(LivingEntity livingEntity, Level level) {
        super(ModEntityTypes.STEEL_COIN_PROJECTILE.get(), livingEntity, level);
    }

    public SteelCoinProjectile(double x, double y, double z, Level level) {
        super(ModEntityTypes.STEEL_COIN_PROJECTILE.get(), x, y, z, level);
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        if (!this.level.isClientSide) {
            if (pResult.getEntity() instanceof LivingEntity target) {
                if (this.getOwner() instanceof ServerPlayer owner) {
                    target.hurt(DamageSource.playerAttack(owner), 1.0F);
                }
            }
        }
        super.onHitEntity(pResult);
    }

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        if (!this.level.isClientSide) {
            if (this.getOwner() instanceof ServerPlayer owner) {

            }
        }
        super.onHitBlock(blockHitResult);
    }

    @Override
    protected void onHit(HitResult pResult) {
        super.onHit(pResult);
        //deletes entity when it hits anything
        this.discard();
    }

    //render texture basically
    @Override
    protected Item getDefaultItem() {
        return ModItems.COIN.get();
    }
}
