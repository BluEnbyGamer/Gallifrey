#!/usr/bin/env python3
"""
Download Mojang's official Alpha 1.2.x client JARs and extract the legacy
Nether-related texture assets into ./output/.

This does NOT redistribute Minecraft assets. It downloads the official client
files directly from Mojang's launcher metadata, then extracts them locally.

Python 3.9+; standard library only.
"""

from pathlib import Path
from urllib.request import Request, urlopen
from urllib.parse import urlparse
import hashlib
import json
import re
import sys
import zipfile

OUT = Path("output")
CACHE = Path("cache")
MANIFEST_URL = "https://piston-meta.mojang.com/mc/game/version_manifest_v2.json"

# These are the old resource names used by the Alpha-era client.
# terrain.png contains the block atlas, including the early Nether blocks and
# the portal texture. Mob files are individual legacy textures.
MOB_FILES = {
    "mob/ghast.png",
    "mob/ghast_fire.png",
    "mob/pigzombie.png",
    # Historical/unused Pigman texture: keep it if a particular JAR contains it.
    "mob/pigman.png",
}

# Useful context files that can contain Nether visuals/effects in this era.
OPTIONAL_FILES = {
    "particles.png",
    "terrain.png",
    "gui/items.png",
    "gui/allitems.png",
}

def get_json(url):
    req = Request(url, headers={"User-Agent": "Alpha-1.2-Nether-Asset-Extractor/1.0"})
    with urlopen(req, timeout=60) as r:
        return json.load(r)

def download(url, dest, expected_sha1=None):
    if dest.exists() and expected_sha1:
        h = hashlib.sha1(dest.read_bytes()).hexdigest()
        if h == expected_sha1:
            return
    dest.parent.mkdir(parents=True, exist_ok=True)
    req = Request(url, headers={"User-Agent": "Alpha-1.2-Nether-Asset-Extractor/1.0"})
    with urlopen(req, timeout=120) as r, dest.open("wb") as f:
        while True:
            chunk = r.read(1024 * 1024)
            if not chunk:
                break
            f.write(chunk)
    if expected_sha1:
        h = hashlib.sha1(dest.read_bytes()).hexdigest()
        if h != expected_sha1:
            raise RuntimeError(f"SHA-1 mismatch for {dest}")

def main():
    CACHE.mkdir(exist_ok=True)
    OUT.mkdir(exist_ok=True)

    print("Reading Mojang version manifest...")
    manifest = get_json(MANIFEST_URL)

    versions = []
    for v in manifest["versions"]:
        vid = v["id"]
        # Alpha 1.2.x includes 1.2.0 through 1.2.6 and lettered releases
        # such as a1.2.2a.
        if re.fullmatch(r"a1\.2\.[0-9]+[a-z]?", vid):
            versions.append((vid, v["url"]))

    # Oldest -> newest.
    def sort_key(x):
        m = re.match(r"a1\.2\.(\d+)([a-z]?)$", x[0])
        return (int(m.group(1)), m.group(2) or "")
    versions.sort(key=sort_key)

    if not versions:
        raise RuntimeError("No a1.2.x versions were found in Mojang's manifest.")

    inventory = []

    for vid, meta_url in versions:
        print(f"\n[{vid}] reading version metadata...")
        meta = get_json(meta_url)
        client = meta.get("downloads", {}).get("client")
        if not client:
            print("  No client download listed; skipping.")
            continue

        jar = CACHE / f"{vid}.jar"
        download(client["url"], jar, client.get("sha1"))

        dest = OUT / vid
        dest.mkdir(parents=True, exist_ok=True)

        wanted = set(MOB_FILES) | set(OPTIONAL_FILES)
        found = []

        with zipfile.ZipFile(jar) as z:
            names = set(z.namelist())
            for name in sorted(wanted):
                if name in names:
                    target = dest / name
                    target.parent.mkdir(parents=True, exist_ok=True)
                    target.write_bytes(z.read(name))
                    found.append(name)

            # Keep a complete list of legacy mob textures so you can inspect
            # any Nether-adjacent/unused material present in a given JAR.
            mob_names = sorted(n for n in names if n.startswith("mob/") and n.endswith(".png"))
            (dest / "mob_file_inventory.txt").write_text(
                "\n".join(mob_names) + "\n", encoding="utf-8"
            )

        inventory.append({
            "version": vid,
            "client_sha1": client.get("sha1"),
            "client_url": client["url"],
            "extracted": found,
            "legacy_mob_inventory_count": len(mob_names),
        })
        print("  extracted:", ", ".join(found) if found else "(none)")

    (OUT / "MANIFEST.json").write_text(
        json.dumps(inventory, indent=2), encoding="utf-8"
    )
    print("\nDone.")
    print(f"Assets are in: {OUT.resolve()}")
    print("terrain.png is the original 16x16 legacy atlas; Nether block textures")
    print("such as netherrack, soul sand, glowstone and the portal are tiles in it.")
    print("The old mob textures include ghast, ghast_fire and pigzombie when present.")

if __name__ == "__main__":
    main()
