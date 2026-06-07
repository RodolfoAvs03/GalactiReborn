package com.galactireborn.inventory;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import com.galactireborn.GalactiReborn;

@Mod.EventBusSubscriber(modid = GalactiReborn.MODID)
public class SpaceInventoryProvider
{
    // Registrar la capability en Forge
    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event)
    {
        event.register(SpaceInventory.class);
    }

    // Adjuntar el inventario espacial a cada jugador
    @SubscribeEvent
    public static void onAttachCapabilities(AttachCapabilitiesEvent<net.minecraft.world.entity.Entity> event)
    {
        if (event.getObject() instanceof Player)
        {
            SpaceInventoryCapability cap = new SpaceInventoryCapability();
            event.addCapability(
                    new ResourceLocation(GalactiReborn.MODID, "space_inventory"), cap);
            event.addListener(cap::invalidate);
        }
    }

    // Copiar inventario al morir/respawnear
    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event)
    {
        event.getOriginal().reviveCaps();
        event.getOriginal().getCapability(SpaceInventoryCapability.SPACE_INVENTORY)
                .ifPresent(oldInv ->
                        event.getEntity().getCapability(SpaceInventoryCapability.SPACE_INVENTORY)
                                .ifPresent(newInv -> {
                                    for (int i = 0; i < SpaceInventory.SLOT_COUNT; i++)
                                        newInv.setStack(i, oldInv.getStack(i));
                                })
                );
        event.getOriginal().invalidateCaps();
    }
}