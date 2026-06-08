package com.galactireborn.inventory;

import com.galactireborn.GalactiReborn;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SpaceEquipmentMenu extends AbstractContainerMenu
{
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, GalactiReborn.MODID);

    public static final RegistryObject<MenuType<SpaceEquipmentMenu>> SPACE_EQUIPMENT_MENU =
            MENUS.register("space_equipment_menu",
                    () -> net.minecraftforge.common.extensions.IForgeMenuType.create(
                            (id, inv, buf) -> new SpaceEquipmentMenu(id, inv)));
    private final SpaceInventory spaceInv;


    public SpaceEquipmentMenu(int windowId, Inventory playerInv)
    {
        super(SPACE_EQUIPMENT_MENU.get(), windowId);

        // Obtener el inventario espacial del jugador
        spaceInv = playerInv.player
                .getCapability(SpaceInventoryCapability.SPACE_INVENTORY)
                .orElse(new SpaceInventory());

        // Slots espaciales (columna izquierda)
        // Mascara de oxigeno
        this.addSlot(new SlotItemHandler(spaceInv.getHandler(),
                SpaceInventory.SLOT_OXYGEN_MASK, 8, 8));
        // Oxygen Gear
        this.addSlot(new SlotItemHandler(spaceInv.getHandler(),
                SpaceInventory.SLOT_OXYGEN_GEAR, 8, 26));
        // Tanque 1
        this.addSlot(new SlotItemHandler(spaceInv.getHandler(),
                SpaceInventory.SLOT_OXYGEN_TANK_1, 8, 44));
        // Tanque 2
        this.addSlot(new SlotItemHandler(spaceInv.getHandler(),
                SpaceInventory.SLOT_OXYGEN_TANK_2, 8, 62));
        // Paracaidas
        this.addSlot(new SlotItemHandler(spaceInv.getHandler(),
                SpaceInventory.SLOT_PARACHUTE, 8, 80));
        // Frequency Module
        this.addSlot(new SlotItemHandler(spaceInv.getHandler(),
                SpaceInventory.SLOT_FREQ_MODULE, 8, 98));

        // Inventario del jugador (3 filas)
        for (int row = 0; row < 3; row++)
            for (int col = 0; col < 9; col++)
                this.addSlot(new Slot(playerInv,
                        col + row * 9 + 9,
                        30 + col * 18, 8 + row * 18));

        // Hotbar
        for (int col = 0; col < 9; col++)
            this.addSlot(new Slot(playerInv, col, 30 + col * 18, 74));
    }

    @Override
    public boolean stillValid(Player player)
    {
        return true;
    }

    // Shift+click para mover items entre slots
    @Override
    public ItemStack quickMoveStack(Player player, int index)
    {
        return ItemStack.EMPTY;
    }
}