package com.galactireborn.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;
import java.util.List;

public class OxygenTank extends Item
{
    private final int maxOxygen;

    public OxygenTank(int maxOxygen)
    {
        super(new Item.Properties()
                .stacksTo(1)
                .durability(maxOxygen));
        this.maxOxygen = maxOxygen;
    }

    // Cuanto oxigeno le queda al tanque
    public int getOxygenLevel(ItemStack stack)
    {
        return stack.getMaxDamage() - stack.getDamageValue();
    }

    // El tanque esta vacio?
    public boolean isEmpty(ItemStack stack)
    {
        return getOxygenLevel(stack) <= 0;
    }

    // Muestra el nivel de oxigeno cuando pasas el mouse por encima
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level,
                                List<Component> tooltip, TooltipFlag flag)
    {
        tooltip.add(Component.literal("Oxigeno: " + getOxygenLevel(stack) + " / " + maxOxygen));
    }
}