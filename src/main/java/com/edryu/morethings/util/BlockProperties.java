package com.edryu.morethings.util;

import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.StringIdentifiable;

public class BlockProperties {
    
    public static final EnumProperty<Winding> WINDING = EnumProperty.of("winding", Winding.class);

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

}

