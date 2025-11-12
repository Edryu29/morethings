package com.edryu.morethings.registry;

import com.edryu.morethings.MoreThingsMain;
import com.edryu.morethings.screen.PulleyScreenHandler;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ScreenRegistry {
    public static final ScreenHandlerType<PulleyScreenHandler> PULLEY_SCREEN_HANDLER = Registry.register(Registries.SCREEN_HANDLER, Identifier.of(MoreThingsMain.MOD_ID, "pulley"), new ScreenHandlerType<>(PulleyScreenHandler::new, FeatureSet.empty()));

    public static void initialize() {}
}