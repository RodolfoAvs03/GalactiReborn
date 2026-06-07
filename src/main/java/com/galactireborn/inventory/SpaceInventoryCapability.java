package com.galactireborn.inventory;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SpaceInventoryCapability implements ICapabilitySerializable<CompoundTag>
{
    // Registro de la capability
    public static final Capability<SpaceInventory> SPACE_INVENTORY =
            CapabilityManager.get(new CapabilityToken<>(){});

    private final SpaceInventory inventory = new SpaceInventory();
    private final LazyOptional<SpaceInventory> optional = LazyOptional.of(() -> inventory);

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(
            @NotNull Capability<T> cap, @Nullable Direction side)
    {
        return cap == SPACE_INVENTORY ? optional.cast() : LazyOptional.empty();
    }

    // Guardar en NBT (para que persista al cerrar el mundo)
    @Override
    public CompoundTag serializeNBT()
    {
        CompoundTag tag = new CompoundTag();
        tag.put("SpaceInventory", inventory.getHandler().serializeNBT());
        return tag;
    }

    // Cargar desde NBT
    @Override
    public void deserializeNBT(CompoundTag tag)
    {
        inventory.getHandler().deserializeNBT(tag.getCompound("SpaceInventory"));
    }

    public void invalidate()
    {
        optional.invalidate();
    }
}