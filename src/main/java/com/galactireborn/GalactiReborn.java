package com.galactireborn;

import com.galactireborn.inventory.SpaceInventoryCapability;
import com.galactireborn.items.OxygenTank;
import com.mojang.logging.LogUtils;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraft.world.item.Item;
import org.slf4j.Logger;

@Mod(GalactiReborn.MODID)
public class GalactiReborn
{
    public static final String MODID = "galactireborn";
    private static final Logger LOGGER = LogUtils.getLogger();

    // Registro de items
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

    // Tanques de oxigeno
    public static final RegistryObject<Item> OXYGEN_TANK_SMALL =
            ITEMS.register("oxygen_tank_small", () -> new OxygenTank(300));
    public static final RegistryObject<Item> OXYGEN_TANK_MEDIUM =
            ITEMS.register("oxygen_tank_medium", () -> new OxygenTank(600));
    public static final RegistryObject<Item> OXYGEN_TANK_LARGE =
            ITEMS.register("oxygen_tank_large", () -> new OxygenTank(900));

    // Tab creativo
    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public static final RegistryObject<CreativeModeTab> GALACTIREBORN_TAB =
            TABS.register("galactireborn_tab", () -> CreativeModeTab.builder()
                    .withTabsBefore(CreativeModeTabs.COMBAT)
                    .icon(() -> OXYGEN_TANK_SMALL.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        output.accept(OXYGEN_TANK_SMALL.get());
                        output.accept(OXYGEN_TANK_MEDIUM.get());
                        output.accept(OXYGEN_TANK_LARGE.get());
                    }).build());

    public GalactiReborn()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);

        ITEMS.register(modEventBus);
        TABS.register(modEventBus);

        // Registrar eventos del mundo (capability, teclas, etc)
        MinecraftForge.EVENT_BUS.register(this);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, GalactiRebornConfig.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        LOGGER.info("GalactiReborn - Iniciando sistema espacial...");
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        LOGGER.info("GalactiReborn - Servidor listo");
    }
}