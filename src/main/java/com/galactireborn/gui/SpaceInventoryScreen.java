package com.galactireborn.gui;

import com.galactireborn.GalactiReborn;
import com.galactireborn.inventory.SpaceInventory;
import com.galactireborn.inventory.SpaceInventoryCapability;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class SpaceInventoryScreen extends Screen
{
    // Textura de fondo de la GUI
    private static final ResourceLocation TEXTURE = new ResourceLocation(
            GalactiReborn.MODID, "textures/gui/space_inventory.png");

    private static final int GUI_WIDTH  = 176;
    private static final int GUI_HEIGHT = 166;

    private int guiLeft;
    private int guiTop;

    public SpaceInventoryScreen()
    {
        super(Component.translatable("screen.galactireborn.space_inventory"));
    }

    @Override
    protected void init()
    {
        guiLeft = (this.width  - GUI_WIDTH)  / 2;
        guiTop  = (this.height - GUI_HEIGHT) / 2;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
    {
        // Fondo oscuro detras de la GUI
        this.renderBackground(graphics);

        // Dibujar la textura de fondo
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        graphics.blit(TEXTURE, guiLeft, guiTop, 0, 0, GUI_WIDTH, GUI_HEIGHT);

        // Titulo de la pantalla
        graphics.drawCenteredString(
                this.font,
                Component.translatable("screen.galactireborn.space_inventory"),
                guiLeft + GUI_WIDTH / 2,
                guiTop + 6,
                0xFFFFFF);

        // Mostrar estado de oxigeno del jugador
        var player = Minecraft.getInstance().player;
        if (player != null)
        {
            player.getCapability(SpaceInventoryCapability.SPACE_INVENTORY)
                    .ifPresent(inv -> {
                        String status = inv.hasOxygenEquipped()
                                ? "Oxigeno: ACTIVO"
                                : "Oxigeno: SIN EQUIPO";
                        int color = inv.hasOxygenEquipped() ? 0x55FF55 : 0xFF5555;
                        graphics.drawString(this.font, status,
                                guiLeft + 8, guiTop + 20, color);

                        String parachute = inv.hasParachute()
                                ? "Paracaidas: SI"
                                : "Paracaidas: NO";
                        int pColor = inv.hasParachute() ? 0x55FF55 : 0xFF5555;
                        graphics.drawString(this.font, parachute,
                                guiLeft + 8, guiTop + 32, pColor);
                    });
        }

        super.render(graphics, mouseX, mouseY, partialTick);
    }

    // Cerrar con Escape o con R
    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers)
    {
        if (keyCode == 256) // Escape
        {
            this.onClose();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    // No pausa el juego
    @Override
    public boolean isPauseScreen()
    {
        return false;
    }
}