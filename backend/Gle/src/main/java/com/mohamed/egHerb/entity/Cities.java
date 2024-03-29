package com.mohamed.egHerb.entity;

public enum Cities {
        CAIRO("CAIRO"),
        ALEXANDRIA("ALEXANDRIA"),
        GIZA("GIZA"),
        LUXOR("LUXOR"),
        ASWAN("ASWAN"),
        SHARM_EL_SHEIKH("SHARM EL-SHEIKH"),
        HURGHADA("HURGHADA"),
        PORT_SAID("PORT SAID"),
        SUEZ("SUEZ"),
        ISMAILIA("ISMAILIA"),
        MANSOURA("MANSOURA"),
        TANTA("TANTA"),
        ASSIUT("ASSIUT"),
        SOHAG("SOHAG"),
        ZAGAZIG("ZAGAZIG"),
        DAMIETTA("DAMIETTA"),
        MINYA("MINYA"),
        BENI_SUEF("BENI SUEF"),
        QENA("QENA"),
        BANHA("BANHA"),
        KAFR_EL_SHEIKH("KAFR EL-SHEIKH");
        private final String value;

    Cities(String value) {
            this.value = value.toUpperCase();
        }

    public String getValue() {
            return value;
        }

        public static Cities fromString(String input) {
            try {
                return Cities.valueOf(input.toUpperCase());
            } catch (IllegalArgumentException e) {
                return CAIRO;
            }

    }
}
