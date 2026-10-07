# Classic rework

The supplied `New folder.zip` is treated as the source of truth for the new Classic-era asset set.

## Integrated Classic dimension assets
- Existing Gallifrey Classic blocks now use the supplied oak/grass/dirt/cobblestone/gold/iron textures where those files were provided.
- Existing Classic block sounds now use the supplied stone/grass/wood/gravel step recordings.
- Classic hurt sound now uses the supplied `damage/hurt.ogg`.
- Classic Nether worldgen remains separate from modern Nether blocks and keeps its custom Classic Nether blocks/fluid.
- Classic and Classic Nether keep their dedicated worldgen, dimensions, loot tables, recipes and Vortex destinations.

## Full supplied asset pack
A complete optional resource pack is included at `resourcepacks/classic_assets/`. It contains the supplied vanilla-style textures, mob textures, armor layers and sound recordings mapped into Minecraft's resource-pack layout.

The full pack is intentionally optional because its `assets/minecraft` overrides affect vanilla assets globally while enabled; the integrated Classic blocks above do not.
