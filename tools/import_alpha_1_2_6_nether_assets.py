#!/usr/bin/env python3
"""Import Alpha 1.2.6 Nether assets extracted by extract_alpha_1_2_nether.py.

Run from the Gallifrey project root after extracting the official Alpha client:
    python tools/extract_alpha_1_2_nether.py
    python tools/import_alpha_1_2_6_nether_assets.py path/to/output/a1.2.6

The old terrain atlas stores the three Alpha Nether block textures at:
0x67 netherrack, 0x68 soul sand, 0x69 glowstone (16x16 tiles).
"""
from pathlib import Path
from PIL import Image
import shutil, sys

ROOT = Path(__file__).resolve().parents[1]
RES = ROOT / "src/main/resources/assets/gallifrey/textures"

src = Path(sys.argv[1]) if len(sys.argv) > 1 else ROOT / "alpha_assets/a1.2.6"
terrain = src / "terrain.png"
if not terrain.exists():
    raise SystemExit(f"Missing {terrain}. Run the extractor first.")

out = RES / "block/alpha_nether"
out.mkdir(parents=True, exist_ok=True)
img = Image.open(terrain).convert("RGBA")
if img.width < 160 or img.height < 112:
    raise SystemExit(f"Unexpected terrain.png size: {img.size}")

# Atlas indices 0x67/0x68/0x69 are the Alpha Nether textures.
for name, index in (("netherrack", 0x67), ("soul_sand", 0x68), ("glowstone", 0x69)):
    x = (index % 16) * 16
    y = (index // 16) * 16
    img.crop((x, y, x + 16, y + 16)).save(out / f"{name}.png")

mob_src = src / "mob"
entity_out = RES / "entity"
entity_out.mkdir(parents=True, exist_ok=True)
for source, target in (("ghast.png", "alpha_ghast.png"), ("pigzombie.png", "alpha_pigman.png")):
    path = mob_src / source
    if path.exists():
        shutil.copy2(path, entity_out / target)
    else:
        print(f"warning: {path} not found")

# Write a tiny marker so it is obvious which resource set is installed.
(ROOT / "alpha_assets" / "README_IMPORTED.txt").parent.mkdir(parents=True, exist_ok=True)
(ROOT / "alpha_assets" / "README_IMPORTED.txt").write_text(
    "Alpha 1.2.6 Nether textures imported from the locally extracted client.\n",
    encoding="utf-8"
)
print("Imported Alpha 1.2.6 Nether textures into the Gallifrey resources.")
