package com.edryu.morethings.registry;

import com.edryu.morethings.MoreThingsMain;
import com.edryu.morethings.util.SimpleScreenHandler;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ScreenRegistry {
    public static final ScreenHandlerType<SimpleScreenHandler> SACK_SCREEN_HANDLER = Registry.register(Registries.SCREEN_HANDLER, Identifier.of(MoreThingsMain.MOD_ID, "sack"), new ScreenHandlerType<>(SimpleScreenHandler::new, FeatureSet.empty()));
    public static final ScreenHandlerType<SimpleScreenHandler> SAFE_SCREEN_HANDLER = Registry.register(Registries.SCREEN_HANDLER, Identifier.of(MoreThingsMain.MOD_ID, "safe"), new ScreenHandlerType<>(SimpleScreenHandler::new, FeatureSet.empty()));

    public static void initialize() {}
}