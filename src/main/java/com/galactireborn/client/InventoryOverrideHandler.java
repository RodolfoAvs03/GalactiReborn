package com.galactireborn.client;

import com.galactireborn.GalactiReborn;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = GalactiReborn.MODID, value = Dist.CLIENT)
public class InventoryOverrideHandler
{
    // El inventario vanilla se abre normal con E
    // El inventario espacial se abre desde la pestaña dentro del inventario
}