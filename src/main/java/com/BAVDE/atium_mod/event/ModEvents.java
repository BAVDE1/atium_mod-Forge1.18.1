package com.BAVDE.atium_mod.event;

import com.BAVDE.atium_mod.AtiumMod;
import com.BAVDE.atium_mod.item.ModItems;
import com.BAVDE.atium_mod.particle.ModParticles;
import net.minecraft.advancements.critereon.PlayerHurtEntityTrigger;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.lang.annotation.Target;
import java.util.Random;

@Mod.EventBusSubscriber(modid = AtiumMod.MOD_ID)
public class ModEvents {
    public final Random random = new Random();

    @SubscribeEvent
    public static void resetFreezeOnRespawn(ClientPlayerNetworkEvent.RespawnEvent respawnEvent) {
        respawnEvent.getNewPlayer().setTicksFrozen(0);
    }
}
