package com.BAVDE.atium_mod.handler;

import com.BAVDE.atium_mod.item.ModItems;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public final class ModDropHandler {

    @SubscribeEvent
    public void onLivingDrops(LivingDropsEvent event) {
        var entity = event.getEntityLiving();
        var world = entity.getCommandSenderWorld();

        if (!world.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT))
            return;

        var drops = event.getDrops();
        var attacker = event.getSource().getEntity();

        if (entity instanceof Zombie) {
            drops.add(new ItemEntity(world, entity.getX(), entity.getY(), entity.getZ(), new ItemStack(Items.LEATHER)));
        }
    }
}


/*if (attacker instanceof Player player) {
                var item = player.getMainHandItem().getItem();
                if (item == ModItems.WOOD_KNIFE.get()){*/
//}
//}