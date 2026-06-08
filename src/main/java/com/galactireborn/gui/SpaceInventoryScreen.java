package com.galactireborn.gui;

import com.galactireborn.GalactiReborn;
import com.galactireborn.inventory.SpaceInventoryCapability;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class SpaceInventoryScreen extends Screen
{
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
        this.renderBackground(graphics);

        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        graphics.blit(TEXTURE, guiLeft, guiTop, 0, 0, GUI_WIDTH, GUI_HEIGHT);

        // Modelo del jugador
        var player = Minecraft.getInstance().player;
        if (player != null)
        {
            InventoryScreen.renderEntityInInventoryFollowsMouse(
                    graphics,
                    guiLeft + 31,
                    guiTop + 72,
                    30,
                    0.0625f,
                    0.0625f,
                    player);

            // Estado oxigeno y paracaidas (coordenadas Y diferentes)
            player.getCapability(SpaceInventoryCapability.SPACE_INVENTORY)
                    .ifPresent(inv -> {
                        String o2 = inv.hasOxygenEquipped() ? "O2: ON" : "O2: OFF";
                        int o2c = inv.hasOxygenEquipped() ? 0x55FF55 : 0xFF5555;
                        graphics.drawString(this.font, o2,
                                guiLeft + 100, guiTop + 64, o2c);

                        String para = inv.hasParachute() ? "Chute: YES" : "Chute: NO";
                        int pc = inv.hasParachute() ? 0x55FF55 : 0xFF5555;
                        graphics.drawString(this.font, para,
                                guiLeft + 100, guiTop + 74, pc);
                    });
        }

        // Pestaña de regreso al inventario vanilla
        graphics.fill(guiLeft - 20, guiTop,
                guiLeft, guiTop + 20,
                0xFFC6C6C6);
        graphics.hLine(guiLeft - 20, guiLeft, guiTop,      0xFF373737);
        graphics.hLine(guiLeft - 20, guiLeft, guiTop + 19, 0xFF373737);
        graphics.vLine(guiLeft - 20, guiTop,  guiTop + 20, 0xFF373737);
        graphics.drawCenteredString(this.font, "E",
                guiLeft - 10, guiTop + 6, 0xFF000000);

        super.render(graphics, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button)
    {
        if (mouseX >= guiLeft - 20 && mouseX <= guiLeft
                && mouseY >= guiTop && mouseY <= guiTop + 20)
        {
            Minecraft.getInstance().setScreen(new net.minecraft.client.gui.screens.inventory.InventoryScreen(Minecraft.getInstance().player));
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers)
    {
        if (keyCode == 256) { this.onClose(); return true; }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean isPauseScreen() { return false; }
}