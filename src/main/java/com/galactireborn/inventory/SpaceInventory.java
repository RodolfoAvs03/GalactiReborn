package com.galactireborn.inventory;

import com.galactireborn.GalactiReborn;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class SpaceInventory
{
    // Indices de cada slot
    public static final int SLOT_OXYGEN_MASK   = 0;
    public static final int SLOT_OXYGEN_GEAR   = 1;
    public static final int SLOT_OXYGEN_TANK_1 = 2;
    public static final int SLOT_OXYGEN_TANK_2 = 3;
    public static final int SLOT_PARACHUTE     = 4;
    public static final int SLOT_FREQ_MODULE   = 5;
    public static final int SLOT_COUNT         = 6;

    // Almacenamiento de los slots
    private final ItemStackHandler handler = new ItemStackHandler(SLOT_COUNT)
    {
        @Override
        protected void onContentsChanged(int slot)
        {
            // Aqui notificaremos cambios mas adelante
        }
    };

    // Obtener item en un slot
    public ItemStack getStack(int slot)
    {
        return handler.getStackInSlot(slot);
    }

    // Poner item en un slot
    public void setStack(int slot, ItemStack stack)
    {
        handler.setStackInSlot(slot, stack);
    }

    // El jugador tiene oxigeno equipado?
    public boolean hasOxygenEquipped()
    {
        boolean hasMask  = !getStack(SLOT_OXYGEN_MASK).isEmpty();
        boolean hasGear  = !getStack(SLOT_OXYGEN_GEAR).isEmpty();
        boolean hasTank  = !getStack(SLOT_OXYGEN_TANK_1).isEmpty()
                || !getStack(SLOT_OXYGEN_TANK_2).isEmpty();
        return hasMask && hasGear && hasTank;
    }

    // El jugador tiene paracaidas equipado?
    public boolean hasParachute()
    {
        return !getStack(SLOT_PARACHUTE).isEmpty();
    }

    public ItemStackHandler getHandler()
    {
        return handler;
    }
}
