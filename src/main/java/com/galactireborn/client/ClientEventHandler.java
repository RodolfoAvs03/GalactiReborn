package com.galactireborn.client;

import com.galactireborn.GalactiReborn;
import com.galactireborn.gui.SpaceInventoryScreen;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = GalactiReborn.MODID, value = Dist.CLIENT)
public class ClientEventHandler
{
    // Tecla R para abrir el inventario espacial
    public static final KeyMapping OPEN_SPACE_INVENTORY = new KeyMapping(
            "key.galactireborn.space_inventory",
            GLFW.GLFW_KEY_R,
            "key.categories.galactireborn"
    );

    // Registrar la tecla en Forge
    @Mod.EventBusSubscriber(modid = GalactiReborn.MODID,
            bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ModEvents
    {
        @SubscribeEvent
        public static void onRegisterKeyMappings(RegisterKeyMappingsEvent event)
        {
            event.register(OPEN_SPACE_INVENTORY);
        }
    }

    // Detectar cuando se presiona la tecla
    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event)
    {
        Minecraft mc = Minecraft.getInstance();
        if (OPEN_SPACE_INVENTORY.consumeClick() && mc.player != null)
        {
            mc.setScreen(new SpaceInventoryScreen());
        }
    }
}
