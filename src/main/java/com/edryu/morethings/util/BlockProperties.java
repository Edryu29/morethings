package com.edryu.morethings.util;

import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.StringIdentifiable;

public class BlockProperties {
    
    public static final EnumProperty<Winding> WINDING = EnumProperty.of("winding", Winding.class);
    public static final EnumProperty<Color> COLOR = EnumProperty.of("color", Color.class);
    public static final EnumProperty<VerticalConnectingType> VERTICAL_CONNECTING_TYPE = EnumProperty.of("type", VerticalConnectingType.class);

    public enum Winding implements StringIdentifiable {
        NONE("none"),
        CHAIN("chain"),
        ROPE("rope");

        private final String name;

        Winding(String name) {
            this.name = name;
        }

        @Override
        public String asString() {
            return this.name;
        }
    }

    public enum Color implements StringIdentifiable {
        BLACK("black"),
        BLUE("blue"),
        BROWN("brown"),
        CYAN("cyan"),
        GRAY("gray"),
        GREEN("green"),
        LIGHT_BLUE("light_blue"),
        LIGHT_GRAY("light_gray"),
        LIME("lime"),
        MAGENTA("magenta"),
        ORANGE("orange"),
        PINK("pink"),
        PURPLE("purple"),
        RED("red"),
        WHITE("white"),
        YELLOW("yellow");

        private final String name;

        Color(String name) {
            this.name = name;
        }

        @Override
        public String asString() {
            return this.name;
        }
    }

    public enum VerticalConnectingType implements StringIdentifiable {
        TOP("top"),
        MIDDLE("middle"),
        BOTTOM("bottom"),
        NONE("none");

        private final String name;

        VerticalConnectingType(String name) {
            this.name = name;
        }

        @Override
        public String asString() {
            return this.name;
        }
    }

}

