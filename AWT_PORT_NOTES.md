# AWT content port

This Gallifrey working tree now includes a dependency-free port of selected Adventures With TARDISes content:

- Skaro and Mondas dimensions, with their own dimension types, biomes and noise settings.
- Skaro's second-sun client rendering and Mondas snow/fog effects.
- Fez variants, Trustable Hat and Dalek Eye Stalk as wearable vanilla head-slot equipment.
- AWT clothing textures/models and crafting recipes adapted to the `gallifrey` namespace.
- Skaro/Mondas portal frames use blocks and the White Point Star already provided by Gallifrey.

No new Gradle/Minecraft mod dependencies were added. The clothing implementation deliberately does **not** use Trinkets; it uses the vanilla `EquipmentSlot.HEAD` and a small client-side feature renderer instead.
