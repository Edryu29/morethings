package com.edryu.morethings.util;

import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public class BlockProperties {
    
    public static final EnumProperty<Winding> WINDING = EnumProperty.create("winding", Winding.class);
    public static final EnumProperty<DyeColor> COLOR = EnumProperty.create("color", DyeColor.class);
    public static final EnumProperty<VerticalConnectingType> VERTICAL_CONNECTING_TYPE = EnumProperty.create("type", VerticalConnectingType.class);

    public enum Winding implements StringRepresentable {
        NONE("none"),
        CHAIN("chain"),
        ROPE("rope");

        private final String name;

        Winding(String name) {
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }
    }

    public enum VerticalConnectingType implements StringRepresentable {
        TOP("top"),
        MIDDLE("middle"),
        BOTTOM("bottom"),
        NONE("none");

        private final String name;

        VerticalConnectingType(String name) {
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }
    }

}

