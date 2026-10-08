package com.timelordmod.gallifrey.sonic;

public enum SonicCasing {

    THIRD_DOCTOR(
            "third_doctor",
            "Third Doctor"
    ),

    FOURTH_DOCTOR(
            "fourth_doctor",
            "Fourth Doctor"
    ),

    FIFTH_DOCTOR(
            "fifth_doctor",
            "Fifth Doctor"
    ),

    EIGHTH_DOCTOR(
            "eighth_doctor",
            "Eighth Doctor"
    ),

    WAR_DOCTOR(
            "war_doctor",
            "War Doctor"
    ),

    WAR_MASTER(
            "war_master",
            "War Master"
    ),

    ELEVENTH_CANE(
            "eleventh_cane",
            "Copper Cane"
    ),

    BLUE_SONIC(
            "blue_sonic",
            "Blue's Sonic"
    ),

    RICKS_PORTAL_GUN(
            "ricks_portal_gun",
            "Rick Portal Gun"
    ),

    VALVUE_GUN(
            "valvue_gun",
            "Aperture Gun"
    ),

    DEOS_PORTAL_GUN(
            "deos_portal_gun",
            "D0 Gun"
    ),

    DEOS_HAMMER(
            "deos_hammer",
            "Deo's Hammer"
    ),

    MISSYS_UMBRELLA(
            "missys_umbrella",
            "Missy Umbrella"
    ),

    GAMBLERS_SONIC(
            "gamblers_sonic",
            "Glückspiel Sonic"
    ),

    BLUNT_SONIC(
            "blunt_sonic",
            "Blunt"
    ),

    ALASTOR_STAFF(
            "alastor_staff",
            "Alastor's Staff"
    );

    private final String id;
    private final String displayName;

    SonicCasing(
            String id,
            String displayName
    ) {
        this.id = id;
        this.displayName = displayName;
    }

    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static SonicCasing fromId(String id) {

        for (SonicCasing casing : values()) {

            if (casing.id.equals(id)) {
                return casing;
            }
        }

        // Default Sonic
        return THIRD_DOCTOR;
    }
}
