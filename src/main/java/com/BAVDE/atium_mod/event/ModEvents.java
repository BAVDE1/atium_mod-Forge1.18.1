package com.BAVDE.atium_mod.event;

import com.BAVDE.atium_mod.AtiumMod;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = AtiumMod.MOD_ID)
public class ModEvents {

    @SubscribeEvent
    public static void resetFreeze(ClientPlayerNetworkEvent.RespawnEvent respawnEvent) {
        respawnEvent.getNewPlayer().setTicksFrozen(0);
    }
}
