# Diamond Minecart and Classic Bedrock fixes

## Diamond Minecart
- Registered `gallifrey:diamond_minecart` as a tracked minecart entity type.
- Registered the custom `DiamondMinecartItem` as `gallifrey:diamond_minecart` so the crafting recipe produces a real item.
- Registered the custom minecart renderer on the client.
- Added the item's English display name.

## Classic Bedrock
- The server now enforces Classic Bedrock at every X/Z position at Y=1 when Classic dimension chunks generate and whenever existing chunks load.
- Replaces vanilla bedrock at Y=0 with Classic Bedrock as well, so the bottom of Classic terrain does not keep vanilla bedrock.

## Build verification
A full Gradle build could not be run in this environment because the wrapper must download Gradle and network access is unavailable. Open the project with Java 17 and run `gradlew build` in an environment with network access. Test the minecart in-game by crafting it and using it on a rail; test bedrock by generating/loading chunks in `gallifrey:classic`.
