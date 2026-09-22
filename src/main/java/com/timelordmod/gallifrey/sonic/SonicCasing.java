package com.timelordmod.gallifrey.sonic;

public enum SonicCasing {

    THIRD_DOCTOR(
            "third_doctor",
            "Third Doctor Sonic"
    ),

    FOURTH_DOCTOR(
            "fourth_doctor",
            "Fourth Doctor Sonic"
    ),

    FIFTH_DOCTOR(
            "fifth_doctor",
            "Fifth Doctor Sonic"
    ),

    EIGHTH_DOCTOR(
            "eighth_doctor",
            "Eighth Doctor Sonic"
    ),

    WAR_DOCTOR(
            "war_doctor",
            "War Doctor Sonic"
    ),

    WAR_MASTER(
            "war_master",
            "War Master Sonic"
    ),

    ELEVENTH_CANE(
            "eleventh_cane",
            "Eleventh Doctor's Cane"
    ),

    BLUE_SONIC(
            "blue_sonic",
            "Blue Sonic"
    ),

    RICKS_PORTAL_GUN(
            "ricks_portal_gun",
            "Rick's Portal Gun"
    ),

    VALVUE_GUN(
            "valvue_gun",
            "ValVue Gun"
    ),

    ALASTOR_STAFF(
            "alastor_staff",
            "Alastor's Staff"
    ),

    DEOS_HAMMER(
            "deos_hammer",
            "Deo's Hammer Sonic"
    ),

    MISSYS_UMBRELLA(
            "missys_umbrella",
            "Missy's Umbrella Sonic"
    ),

    GAMBLERS_SONIC(
            "gamblers_sonic",
            "The Gambler's Sonic"
    ),

    BLUNT_SONIC(
            "blunt_sonic",
            "Blunt Sonic"
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
        return BLUE_SONIC;
    }
}


