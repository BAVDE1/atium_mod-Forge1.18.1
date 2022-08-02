package com.BAVDE.atium_mod.entity.projectile;

import com.BAVDE.atium_mod.entity.ModEntityTypes;
import com.BAVDE.atium_mod.item.ModItems;
import com.BAVDE.atium_mod.sound.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.world.NoteBlockEvent;

public class IronCoinProjectile extends ThrowableItemProjectile {
    public IronCoinProjectile(EntityType<? extends IronCoinProjectile> entityType, Level level) {
        super(entityType, level);
    }

    //spawning of projectile super
    public IronCoinProjectile(LivingEntity livingEntity, Level level) {
        super(ModEntityTypes.IRON_COIN_PROJECTILE.get(), livingEntity, level);
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        if (pResult.getEntity() instanceof LivingEntity target) {
            if (getOwner() instanceof Player owner) {
                target.hurt(DamageSource.playerAttack(owner), 0.0F);
                //pull
                Position targetPos = target.position();
                Position ownerPos = owner.position();

                double v = 6;

                if (!owner.isCrouching()) {
                    double pX = targetPos.x() - ownerPos.x();
                    double pY = targetPos.y() - ownerPos.y();
                    double pZ = targetPos.z() - ownerPos.z();

                    owner.push(pX / v, pY / v, pZ / v);
                }
                if (!(target instanceof ArmorStand)) {
                    if (!target.isCrouching()) {
                        double pX = ownerPos.x() - targetPos.x();
                        double pY = ownerPos.y() - targetPos.y();
                        double pZ = ownerPos.z() - targetPos.z();

                        target.push(pX / v, pY / v, pZ / v);
                    }
                }
            }
        }
        super.onHitEntity(pResult);
    }

    @Override
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        //deletes entity when it hits anything
        this.discard();
    }

    //render texture basically
    @Override
    protected Item getDefaultItem() {
        return Items.IRON_NUGGET;
    }
}
