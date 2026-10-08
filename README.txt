# Gallifrey

Gallifrey is a Minecraft 1.20.1 Fabric mod that brings the world of Doctor Who to Minecraft.

Explore Gallifrey, home of the Time Lords, and use the Vortex Manipulator inspired by the one seen in the show.

✨ Features

🪐 Gallifrey

Adds Gallifrey-inspired content to Minecraft.
Explore the homeworld of the Time Lords.

📦 Roundels

Adds a selection of decorative roundels.

⌚ Vortex Manipulator

Adds a Vortex Manipulator inspired by Doctor Who.
Use it as part of your adventures across Minecraft.

🧩 GeckoLib Support

Requires GeckoLib for animations and mod functionality.
📦 Requirements
Minecraft: 1.20.1
Mod Loader: Fabric
Required Dependency: GeckoLib

Make sure you have the correct version of GeckoLib for Minecraft 1.20.1 installed.

🛠️ Installation
Install Fabric Loader for Minecraft 1.20.1.
Install Fabric API if required by the mod.
Download and install GeckoLib for Minecraft 1.20.1.
Download the latest version of Gallifrey.
Place the .jar files into your Minecraft mods folder.
Launch Minecraft using your Fabric installation.

🌌 About

Gallifrey is a fan-made Minecraft mod inspired by the Doctor Who universe.

The mod aims to bring elements of Gallifrey and Time Lord technology into Minecraft while fitting naturally into the game's world.

📜 License

This project is licensed under CC0 1.0 Universal.

You are free to copy, modify, distribute, and use the project's contents without restriction, subject to the terms of the CC0 dedication.

⚠️ Disclaimer

Gallifrey is a fan-made project and is not affiliated with, endorsed by, or sponsored by the BBC or the creators and rights holders of Doctor Who.

Doctor Who and its related names, characters, designs, and concepts are trademarks and/or intellectual property of their respective owners.

Gallifrey TARDIS v15 notes
- Every newly placed TARDIS creates its own isolated persistent pocket dimension: gallifrey:tardis_<tardis uuid without dashes>.
- The old gallifrey:tardis dimension is retained for legacy saves.
- The Hartnell console has a dedicated 32x32 inventory/held-item PNG.
- The TARDIS console UI is reorganized into Navigation / Exterior / Interior / Systems tabs, inspired by the Vortex Manipulator's compact navigation/isomorphic-control layout.
- Flight, exterior shell selection, interior selection/application, security, refuelling, and Doctor Who Vale playback are available from the console UI.


TARDIS systems in this build
----------------------------
- /tardis idfinder (and /tardis IdFinder) lists registered TARDIS UUIDs.
- /tardis exterior <style> changes the owner's exterior shell.
- /tardis antigrav toggles antigravity; with it disabled, the physical TARDIS falls.
- /tardis selfdestruct arms a 10-second TARDIS self-destruct; /tardis selfdestruct cancel cancels it.
- TARDIS flight requires artron power and antigravity and performs a visible real-world takeoff before rematerialisation.
- Occupied landing coordinates are resolved to the highest available supported two-block space.
- The exterior is physically two blocks tall and uses an open-then-enter door interaction.
- Console controls and TARDIS commands are server-side owner-gated.
- Exterior/emission lighting and interior lighting shut down when artron power reaches zero.
