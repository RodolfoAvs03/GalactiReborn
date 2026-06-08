package com.galactireborn.client;

import com.galactireborn.GalactiReborn;
import com.galactireborn.gui.SpaceInventoryScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = GalactiReborn.MODID, value = Dist.CLIENT)
public class InventoryTabHandler
{
    // Posicion de la pestaña espacial
    private static final int TAB_X = 56;
    private static final int TAB_W = 20;
    private static final int TAB_H = 20;

    // Dibujar la pestaña encima del inventario vanilla
    @SubscribeEvent
    public static void onScreenRender(ScreenEvent.Render.Post event)
    {
        if (!(event.getScreen() instanceof InventoryScreen screen)) return;

        int left = screen.getGuiLeft();
        int top  = screen.getGuiTop();

        GuiGraphics graphics = event.getGuiGraphics();

        // Fondo de la pestaña (gris oscuro)
        // Fondo de la pestaña (gris igual al inventario)
        graphics.fill(left + TAB_X, top - TAB_H,
                left + TAB_X + TAB_W, top,
                0xFFC6C6C6);

// Borde oscuro
        graphics.hLine(left + TAB_X,          left + TAB_X + TAB_W, top - TAB_H, 0xFF373737);
        graphics.hLine(left + TAB_X,          left + TAB_X + TAB_W, top - 1,     0xFFC6C6C6);
        graphics.vLine(left + TAB_X,          top - TAB_H, top, 0xFF373737);
        graphics.vLine(left + TAB_X + TAB_W,  top - TAB_H, top, 0xFF373737);

        // Icono de cohete en la pestaña
        graphics.drawCenteredString(
                Minecraft.getInstance().font,
                "\uD83D\uDE80",
                left + TAB_X + TAB_W / 2,
                top - TAB_H + 6,
                0xFF000000);
    }

    // Detectar click en la pestaña
    @SubscribeEvent
    public static void onScreenClick(ScreenEvent.MouseButtonPressed.Post event)
    {
        if (!(event.getScreen() instanceof InventoryScreen screen)) return;

        int left = screen.getGuiLeft();
        int top  = screen.getGuiTop();

        double mx = event.getMouseX();
        double my = event.getMouseY();

        // Verificar si el click fue en la pestaña espacial
        if (mx >= left + TAB_X && mx <= left + TAB_X + TAB_W
                && my >= top - TAB_H  && my <= top)
        {
            Minecraft.getInstance().setScreen(new SpaceInventoryScreen());
        }
    }
}