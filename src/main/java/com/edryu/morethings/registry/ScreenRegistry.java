package com.edryu.morethings.registry;

import com.edryu.morethings.MoreThingsMain;
import com.edryu.morethings.screen.PulleyScreenHandler;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.MenuType;

public class ScreenRegistry {
    public static final MenuType<PulleyScreenHandler> PULLEY_SCREEN_HANDLER = Registry.register(BuiltInRegistries.MENU, ResourceLocation.fromNamespaceAndPath(MoreThingsMain.MOD_ID, "pulley"), new MenuType<>(PulleyScreenHandler::new, FeatureFlagSet.of()));

    public static void initialize() {}
}