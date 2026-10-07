# Classic Nether — Alpha 1.2.6 style

This adds `gallifrey:classic_nether`, a dedicated 128-block-high Nether-style
world intended to reproduce the **Alpha 1.2.6** Nether rather than the modern
1.20.1 Nether.

## Access

There is deliberately **no portal connection** to this dimension. It can be
selected from the Vortex Manipulator as **Classic Nether (Alpha 1.2.6)** or
entered with the normal `/execute in gallifrey:classic_nether` / teleport tools.

## Terrain/features

The dimension uses a 128-high Nether noise generator, bedrock floor and roof,
Netherrack as the main terrain, lava as the default fluid, and only the old
style features: soul-sand pockets, glowstone blobs, exposed fire and lava
springs. Modern Nether biomes, Nether fortresses, bastions, quartz, ancient
debris, fungi, hoglins, piglins, striders, etc. are not part of the biome.

This is an Alpha-style recreation inside the 1.20.1 world-generation engine;
it is **not byte-for-byte identical to the original Alpha generator**, because
Minecraft 1.20.1 uses a different noise engine and chunk format.

## Mobs

Only two Nether mob types are registered in the Classic Nether:

- **Alpha Ghast** — uses the normal classic ghast floating/fireball behaviour.
- **Alpha Zombie Pigman** — neutral until damaged, then attacks the attacker;
  nearby pigmen join the anger, matching the old group-aggro style. Pigmen
  spawn with a golden sword and do not actively hunt players while neutral.

No modern Nether mob spawns here.

## Alpha textures

The uploaded extractor is a downloader for the official Alpha client. It does
not contain the copyrighted textures itself. On a machine with Internet access:

    python tools/extract_alpha_1_2_nether.py
    python tools/import_alpha_1_2_6_nether_assets.py output/a1.2.6

The importer copies the old `ghast.png` and `pigzombie.png` textures and crops
Netherrack, Soul Sand and Glowstone from the Alpha `terrain.png` atlas. Until
the importer is run, the mob renderers fall back to the normal 1.20.1 textures
instead of showing missing-texture boxes.
