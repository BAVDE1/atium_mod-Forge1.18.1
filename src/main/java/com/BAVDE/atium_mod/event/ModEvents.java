package com.BAVDE.atium_mod.event;

import com.BAVDE.atium_mod.AtiumMod;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = AtiumMod.MOD_ID)
public class ModEvents {

    @SubscribeEvent
    public static void resetFreezeOnRespawn(ClientPlayerNetworkEvent.RespawnEvent respawnEvent) {
        respawnEvent.getNewPlayer().setTicksFrozen(0);
    }
}
