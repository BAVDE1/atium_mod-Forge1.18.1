package com.BAVDE.atium_mod.util.handlers;


import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class AMEventHandler {
    @SubscribeEvent
    public void onEvent(LivingDropsEvent event) {}
}
