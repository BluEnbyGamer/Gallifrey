package com.timelordmod.gallifrey.item;

import com.terraformersmc.terraform.boat.api.item.TerraformBoatItemHelper;
import com.timelordmod.gallifrey.GallifreyMod;
import com.timelordmod.gallifrey.GallifreySounds;
import com.timelordmod.gallifrey.block.GallifreyModBlocks;
import com.timelordmod.gallifrey.entity.ModBoats;
import com.timelordmod.gallifrey.entity.GallifreyEntities;
import com.timelordmod.gallifrey.fluid.GallifreyFluids;
import com.timelordmod.gallifrey.item.custom.GeoHeadwearItem;
import com.timelordmod.gallifrey.item.custom.DiceTabletItem;
import com.timelordmod.gallifrey.item.custom.VortexManipulator;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import com.timelordmod.gallifrey.item.custom.SonicScrewdriver;
import com.timelordmod.gallifrey.item.custom.SonicShadesItem;
import com.timelordmod.gallifrey.item.custom.HeadwearItem;
import com.timelordmod.gallifrey.item.custom.PrehistoricArmorMaterial;
import com.timelordmod.gallifrey.item.custom.PrehistoricToolMaterial;
import com.timelordmod.gallifrey.item.custom.ModArmorMaterials;
import com.timelordmod.gallifrey.item.custom.ModToolMaterials;

public class GallifreyModItems {
    // Dedicated legacy item identities: these are real Gallifrey registry entries, not texture overrides.
    public static final Item CLASSIC_APPLE = new Item(new FabricItemSettings());
    public static final Item CLASSIC_APPLE_GOLDEN = new Item(new FabricItemSettings());
    public static final Item CLASSIC_CARROT_GOLDEN = new Item(new FabricItemSettings());

    // Classic Chainmail Armor
    public static final ArmorItem CLASSIC_CHAINMAIL_BOOTS = new ArmorItem(ArmorMaterials.CHAIN, ArmorItem.Type.BOOTS, new FabricItemSettings());
    public static final ArmorItem CLASSIC_CHAINMAIL_CHESTPLATE = new ArmorItem(ArmorMaterials.CHAIN, ArmorItem.Type.CHESTPLATE, new FabricItemSettings());
    public static final ArmorItem CLASSIC_CHAINMAIL_HELMET = new ArmorItem(ArmorMaterials.CHAIN, ArmorItem.Type.HELMET, new FabricItemSettings());
    public static final ArmorItem CLASSIC_CHAINMAIL_LEGGINGS = new ArmorItem(ArmorMaterials.CHAIN, ArmorItem.Type.LEGGINGS, new FabricItemSettings());

    public static final Item CLASSIC_COAL = new Item(new FabricItemSettings());
    public static final Item CLASSIC_DIAMOND = new Item(new FabricItemSettings());

    // Classic Diamond Tools & Armor
    public static final AxeItem CLASSIC_DIAMOND_AXE = new AxeItem(ModToolMaterials.CLASSIC_DIAMOND, 5.0F, -3.0F, new FabricItemSettings());
    public static final ArmorItem CLASSIC_DIAMOND_BOOTS = new ArmorItem(ArmorMaterials.DIAMOND, ArmorItem.Type.BOOTS, new FabricItemSettings());
    public static final ArmorItem CLASSIC_DIAMOND_CHESTPLATE = new ArmorItem(ArmorMaterials.DIAMOND, ArmorItem.Type.CHESTPLATE, new FabricItemSettings());
    public static final ArmorItem CLASSIC_DIAMOND_HELMET = new ArmorItem(ArmorMaterials.DIAMOND, ArmorItem.Type.HELMET, new FabricItemSettings());
    public static final HoeItem CLASSIC_DIAMOND_HOE = new HoeItem(ModToolMaterials.CLASSIC_DIAMOND, -1, -1.0F, new FabricItemSettings());
    public static final Item CLASSIC_DIAMOND_HORSE_ARMOR = new Item(new FabricItemSettings());
    public static final ArmorItem CLASSIC_DIAMOND_LEGGINGS = new ArmorItem(ArmorMaterials.DIAMOND, ArmorItem.Type.LEGGINGS, new FabricItemSettings());
    public static final PickaxeItem CLASSIC_DIAMOND_PICKAXE = new PickaxeItem(ModToolMaterials.CLASSIC_DIAMOND, 1, -2.8F, new FabricItemSettings());
    public static final ShovelItem CLASSIC_DIAMOND_SHOVEL = new ShovelItem(ModToolMaterials.CLASSIC_DIAMOND, 1.5F, -3.0F, new FabricItemSettings());
    public static final SwordItem CLASSIC_DIAMOND_SWORD = new SwordItem(ModToolMaterials.CLASSIC_DIAMOND, 3, -2.4F, new FabricItemSettings());

    public static final Item CLASSIC_DOOR_IRON = new Item(new FabricItemSettings());
    public static final Item CLASSIC_EMERALD = new Item(new FabricItemSettings());
    public static final Item CLASSIC_FLINT = new Item(new FabricItemSettings());
    public static final Item CLASSIC_FLINT_AND_STEEL = new Item(new FabricItemSettings());

    // Classic Gold Tools & Armor
    public static final AxeItem CLASSIC_GOLD_AXE = new AxeItem(ModToolMaterials.CLASSIC_GOLD, 6.0F, -3.0F, new FabricItemSettings());
    public static final ArmorItem CLASSIC_GOLD_BOOTS = new ArmorItem(ArmorMaterials.GOLD, ArmorItem.Type.BOOTS, new FabricItemSettings());
    public static final ArmorItem CLASSIC_GOLD_CHESTPLATE = new ArmorItem(ArmorMaterials.GOLD, ArmorItem.Type.CHESTPLATE, new FabricItemSettings());
    public static final ArmorItem CLASSIC_GOLD_HELMET = new ArmorItem(ArmorMaterials.GOLD, ArmorItem.Type.HELMET, new FabricItemSettings());
    public static final HoeItem CLASSIC_GOLD_HOE = new HoeItem(ModToolMaterials.CLASSIC_GOLD, 0, -1.0F, new FabricItemSettings());
    public static final Item CLASSIC_GOLD_HORSE_ARMOR = new Item(new FabricItemSettings());
    public static final Item CLASSIC_GOLD_INGOT = new Item(new FabricItemSettings());
    public static final ArmorItem CLASSIC_GOLD_LEGGINGS = new ArmorItem(ArmorMaterials.GOLD, ArmorItem.Type.LEGGINGS, new FabricItemSettings());
    public static final Item CLASSIC_GOLD_NUGGET = new Item(new FabricItemSettings());
    public static final PickaxeItem CLASSIC_GOLD_PICKAXE = new PickaxeItem(ModToolMaterials.CLASSIC_GOLD, 1, -2.8F, new FabricItemSettings());
    public static final ShovelItem CLASSIC_GOLD_SHOVEL = new ShovelItem(ModToolMaterials.CLASSIC_GOLD, 1.5F, -3.0F, new FabricItemSettings());
    public static final SwordItem CLASSIC_GOLD_SWORD = new SwordItem(ModToolMaterials.CLASSIC_GOLD, 3, -2.4F, new FabricItemSettings());

    // Classic Iron Tools & Armor
    public static final AxeItem CLASSIC_IRON_AXE = new AxeItem(ModToolMaterials.CLASSIC_IRON, 6.0F, -3.1F, new FabricItemSettings());
    public static final ArmorItem CLASSIC_IRON_BOOTS = new ArmorItem(ArmorMaterials.IRON, ArmorItem.Type.BOOTS, new FabricItemSettings());
    public static final ArmorItem CLASSIC_IRON_CHESTPLATE = new ArmorItem(ArmorMaterials.IRON, ArmorItem.Type.CHESTPLATE, new FabricItemSettings());
    public static final ArmorItem CLASSIC_IRON_HELMET = new ArmorItem(ArmorMaterials.IRON, ArmorItem.Type.HELMET, new FabricItemSettings());
    public static final HoeItem CLASSIC_IRON_HOE = new HoeItem(ModToolMaterials.CLASSIC_IRON, -2, -1.0F, new FabricItemSettings());
    public static final Item CLASSIC_IRON_HORSE_ARMOR = new Item(new FabricItemSettings());
    public static final Item CLASSIC_IRON_INGOT = new Item(new FabricItemSettings());
    public static final ArmorItem CLASSIC_IRON_LEGGINGS = new ArmorItem(ArmorMaterials.IRON, ArmorItem.Type.LEGGINGS, new FabricItemSettings());
    public static final PickaxeItem CLASSIC_IRON_PICKAXE = new PickaxeItem(ModToolMaterials.CLASSIC_IRON, 1, -2.8F, new FabricItemSettings());
    public static final ShovelItem CLASSIC_IRON_SHOVEL = new ShovelItem(ModToolMaterials.CLASSIC_IRON, 1.5F, -3.0F, new FabricItemSettings());
    public static final SwordItem CLASSIC_IRON_SWORD = new SwordItem(ModToolMaterials.CLASSIC_IRON, 3, -2.4F, new FabricItemSettings());

    // Classic Leather Armor
    public static final Item CLASSIC_LEATHER = new Item(new FabricItemSettings());
    public static final ArmorItem CLASSIC_LEATHER_BOOTS = new ArmorItem(ArmorMaterials.LEATHER, ArmorItem.Type.BOOTS, new FabricItemSettings());
    public static final ArmorItem CLASSIC_LEATHER_CHESTPLATE = new ArmorItem(ArmorMaterials.LEATHER, ArmorItem.Type.CHESTPLATE, new FabricItemSettings());
    public static final ArmorItem CLASSIC_LEATHER_HELMET = new ArmorItem(ArmorMaterials.LEATHER, ArmorItem.Type.HELMET, new FabricItemSettings());
    public static final ArmorItem CLASSIC_LEATHER_LEGGINGS = new ArmorItem(ArmorMaterials.LEATHER, ArmorItem.Type.LEGGINGS, new FabricItemSettings());

    public static final Item CLASSIC_OAK_BOAT = new Item(new FabricItemSettings());
    public static final Item CLASSIC_STICK = new Item(new FabricItemSettings());

    // Classic Wooden Tools
    public static final AxeItem CLASSIC_WOOD_AXE = new AxeItem(ModToolMaterials.CLASSIC_WOOD, 6.0F, -3.2F, new FabricItemSettings());
    public static final HoeItem CLASSIC_WOOD_HOE = new HoeItem(ModToolMaterials.CLASSIC_WOOD, 0, -1.0F, new FabricItemSettings());
    public static final PickaxeItem CLASSIC_WOOD_PICKAXE = new PickaxeItem(ModToolMaterials.CLASSIC_WOOD, 1, -2.8F, new FabricItemSettings());
    public static final ShovelItem CLASSIC_WOOD_SHOVEL = new ShovelItem(ModToolMaterials.CLASSIC_WOOD, 1.5F, -3.0F, new FabricItemSettings());
    public static final SwordItem CLASSIC_WOOD_SWORD = new SwordItem(ModToolMaterials.CLASSIC_WOOD, 3, -2.4F, new FabricItemSettings());


    public static final WhitePointStarItem WHITE_POINT_STAR = new WhitePointStarItem(
            new FabricItemSettings()
    );

    public static final SiliconeItem SILICONE = new SiliconeItem(
            new FabricItemSettings()
    );

    public static final RawSonicCrystalItem RAW_SONIC_CRYSTAL = new RawSonicCrystalItem(
            new FabricItemSettings()
    );

    public static final RefinedSonicCrystalItem REFINED_SONIC_CRYSTAL = new RefinedSonicCrystalItem(
            new FabricItemSettings()
    );

    public static final VortexManipulator VORTEX_MANIPULATOR = new VortexManipulator(
            new FabricItemSettings()
    );

    public static final DalekIngotItem DALEKANIUM_INGOT = new DalekIngotItem(
            new FabricItemSettings()
    );

    public static final HuonParticleItem HUON_PARTICLE = new HuonParticleItem(
            new FabricItemSettings()
    );

    public static final SteelIngotItem STEEL_INGOT = new SteelIngotItem(
            new FabricItemSettings()
    );

    public static final RawSteelItem RAW_STEEL = new RawSteelItem(
            new FabricItemSettings()
    );

    public static final BlankCircuitItem BLANK_CIRCUIT = new BlankCircuitItem(
            new FabricItemSettings()
    );

    public static final LocationCircuitItem LOCATION_CIRCUIT = new LocationCircuitItem(
            new FabricItemSettings()
    );

    public static final InterfaceCircuitItem INTERFACE_CIRCUIT = new InterfaceCircuitItem(
            new FabricItemSettings()
    );

    public static final DimensionCircuitItem DIMENSION_CIRCUIT = new DimensionCircuitItem(
            new FabricItemSettings()
    );

    public static final GeoHeadwearItem OMEGA_HELMET = new GeoHeadwearItem(
            "omega_helmet",
            1.15F,
            ArmorMaterials.NETHERITE,
            new FabricItemSettings().fireproof()
    );

    public static final SpawnEggItem SKARO_CITY_DALEK_SPAWN_EGG = new SpawnEggItem(
            GallifreyEntities.SKARO_CITY_DALEK,
            0x6D737A,
            0x3D7DFF,
            new FabricItemSettings()
    );

    public static final Item TARDIS_SIGN = registerItem("tardis_sign",
            new SignItem(new FabricItemSettings().maxCount(16), GallifreyModBlocks.STANDING_TARDIS_SIGN, GallifreyModBlocks.WALL_TARDIS_SIGN));
    public static final Item HANGING_TARDIS_SIGN = registerItem("tardis_hanging_sign",
            new HangingSignItem(GallifreyModBlocks.HANGING_TARDIS_SIGN, GallifreyModBlocks.WALL_HANGING_TARDIS_SIGN, new FabricItemSettings().maxCount(16)));

    public static final Item TARDIS_BOAT = TerraformBoatItemHelper.registerBoatItem(ModBoats.TARDIS_BOAT_ID, ModBoats.TARDIS_BOAT_KEY, false);
    public static final Item TARDIS_CHEST_BOAT = TerraformBoatItemHelper.registerBoatItem(ModBoats.TARDIS_CHEST_BOAT_ID, ModBoats.TARDIS_BOAT_KEY, true);

    public static final Item ULANDA_SIGN = registerItem("ulanda_sign",
            new SignItem(new FabricItemSettings().maxCount(16), GallifreyModBlocks.STANDING_ULANDA_SIGN, GallifreyModBlocks.WALL_ULANDA_SIGN));
    public static final Item HANGING_ULANDA_SIGN = registerItem("ulanda_hanging_sign",
            new HangingSignItem(GallifreyModBlocks.HANGING_ULANDA_SIGN, GallifreyModBlocks.WALL_HANGING_ULANDA_SIGN, new FabricItemSettings().maxCount(16)));

    public static final Item ULANDA_BOAT = TerraformBoatItemHelper.registerBoatItem(ModBoats.ULANDA_BOAT_ID, ModBoats.ULANDA_BOAT_KEY, false);
    public static final Item ULANDA_CHEST_BOAT = TerraformBoatItemHelper.registerBoatItem(ModBoats.ULANDA_CHEST_BOAT_ID, ModBoats.ULANDA_BOAT_KEY, true);

    public static final Item TREEBORG_BOAT = TerraformBoatItemHelper.registerBoatItem(ModBoats.TREEBORG_BOAT_ID, ModBoats.TREEBORG_BOAT_KEY, false);
    public static final Item TREEBORG_CHEST_BOAT = TerraformBoatItemHelper.registerBoatItem(ModBoats.TREEBORG_CHEST_BOAT_ID, ModBoats.TREEBORG_BOAT_KEY, true);

    public static final Item TREEBORG_SIGN = registerItem("treeborg_sign",
            new SignItem(new FabricItemSettings().maxCount(16), GallifreyModBlocks.STANDING_TREEBORG_SIGN, GallifreyModBlocks.WALL_TREEBORG_SIGN));
    public static final Item HANGING_TREEBORG_SIGN = registerItem("treeborg_hanging_sign",
            new HangingSignItem(GallifreyModBlocks.HANGING_TREEBORG_SIGN, GallifreyModBlocks.WALL_HANGING_TREEBORG_SIGN, new FabricItemSettings().maxCount(16)));

    public static final Item ASH_SIGN = registerItem("ash_sign",
            new SignItem(new FabricItemSettings().maxCount(16), GallifreyModBlocks.STANDING_ASH_SIGN, GallifreyModBlocks.WALL_ASH_SIGN));
    public static final Item HANGING_ASH_SIGN = registerItem("ash_hanging_sign",
            new HangingSignItem(GallifreyModBlocks.HANGING_ASH_SIGN, GallifreyModBlocks.WALL_HANGING_ASH_SIGN, new FabricItemSettings().maxCount(16)));
    public static final Item ASH_BOAT = TerraformBoatItemHelper.registerBoatItem(ModBoats.ASH_BOAT_ID, ModBoats.ASH_BOAT_KEY, false);
    public static final Item ASH_CHEST_BOAT = TerraformBoatItemHelper.registerBoatItem(ModBoats.ASH_CHEST_BOAT_ID, ModBoats.ASH_BOAT_KEY, true);

    public static final Item MAPLE_SIGN = registerItem("maple_sign",
            new SignItem(new FabricItemSettings().maxCount(16), GallifreyModBlocks.STANDING_MAPLE_SIGN, GallifreyModBlocks.WALL_MAPLE_SIGN));
    public static final Item HANGING_MAPLE_SIGN = registerItem("maple_hanging_sign",
            new HangingSignItem(GallifreyModBlocks.HANGING_MAPLE_SIGN, GallifreyModBlocks.WALL_HANGING_MAPLE_SIGN, new FabricItemSettings().maxCount(16)));
    public static final Item MAPLE_BOAT = TerraformBoatItemHelper.registerBoatItem(ModBoats.MAPLE_BOAT_ID, ModBoats.MAPLE_BOAT_KEY, false);
    public static final Item MAPLE_CHEST_BOAT = TerraformBoatItemHelper.registerBoatItem(ModBoats.MAPLE_CHEST_BOAT_ID, ModBoats.MAPLE_BOAT_KEY, true);

    public static final Item MOONPINE_SIGN = registerItem("moonpine_sign",
            new SignItem(new FabricItemSettings().maxCount(16), GallifreyModBlocks.STANDING_MOONPINE_SIGN, GallifreyModBlocks.WALL_MOONPINE_SIGN));
    public static final Item HANGING_MOONPINE_SIGN = registerItem("moonpine_hanging_sign",
            new HangingSignItem(GallifreyModBlocks.HANGING_MOONPINE_SIGN, GallifreyModBlocks.WALL_HANGING_MOONPINE_SIGN, new FabricItemSettings().maxCount(16)));
    public static final Item MOONPINE_BOAT = TerraformBoatItemHelper.registerBoatItem(ModBoats.MOONPINE_BOAT_ID, ModBoats.MOONPINE_BOAT_KEY, false);
    public static final Item MOONPINE_CHEST_BOAT = TerraformBoatItemHelper.registerBoatItem(ModBoats.MOONPINE_CHEST_BOAT_ID, ModBoats.MOONPINE_BOAT_KEY, true);



    // PREHISTORIC MATERIALS AND GEAR
    public static final Item PREHISTORIC_INGOT = new Item(new FabricItemSettings());
    public static final Item PREHISTORIC_UPGRADE_SMITHING_TEMPLATE = new Item(new FabricItemSettings());

    public static final SwordItem PREHISTORIC_SWORD = new SwordItem(PrehistoricToolMaterial.INSTANCE, 3, -2.4F, new FabricItemSettings());
    public static final PickaxeItem PREHISTORIC_PICKAXE = new PickaxeItem(PrehistoricToolMaterial.INSTANCE, 1, -2.8F, new FabricItemSettings());
    public static final AxeItem PREHISTORIC_AXE = new AxeItem(PrehistoricToolMaterial.INSTANCE, 5.5F, -3.0F, new FabricItemSettings());
    public static final ShovelItem PREHISTORIC_SHOVEL = new ShovelItem(PrehistoricToolMaterial.INSTANCE, 1.5F, -3.0F, new FabricItemSettings());
    public static final HoeItem PREHISTORIC_HOE = new HoeItem(PrehistoricToolMaterial.INSTANCE, -2, -1.0F, new FabricItemSettings());

    public static final ArmorItem PREHISTORIC_HELMET = new ArmorItem(PrehistoricArmorMaterial.INSTANCE, ArmorItem.Type.HELMET, new FabricItemSettings());
    public static final ArmorItem PREHISTORIC_CHESTPLATE = new ArmorItem(PrehistoricArmorMaterial.INSTANCE, ArmorItem.Type.CHESTPLATE, new FabricItemSettings());
    public static final ArmorItem PREHISTORIC_LEGGINGS = new ArmorItem(PrehistoricArmorMaterial.INSTANCE, ArmorItem.Type.LEGGINGS, new FabricItemSettings());
    public static final ArmorItem PREHISTORIC_BOOTS = new ArmorItem(PrehistoricArmorMaterial.INSTANCE, ArmorItem.Type.BOOTS, new FabricItemSettings());

    // STEEL GEAR
    public static final Item STEEL_TEMPLATE = new Item(new FabricItemSettings());
    public static final SwordItem STEEL_SWORD = new SwordItem(ModToolMaterials.STEEL, 3, -2.4F, new FabricItemSettings());
    public static final PickaxeItem STEEL_PICKAXE = new PickaxeItem(ModToolMaterials.STEEL, 1, -2.8F, new FabricItemSettings());
    public static final AxeItem STEEL_AXE = new AxeItem(ModToolMaterials.STEEL, 6.0F, -3.1F, new FabricItemSettings());
    public static final ShovelItem STEEL_SHOVEL = new ShovelItem(ModToolMaterials.STEEL, 1.5F, -3.0F, new FabricItemSettings());
    public static final HoeItem STEEL_HOE = new HoeItem(ModToolMaterials.STEEL, -2, -1.0F, new FabricItemSettings());
    public static final ArmorItem STEEL_HELMET = new ArmorItem(ModArmorMaterials.STEEL, ArmorItem.Type.HELMET, new FabricItemSettings());
    public static final ArmorItem STEEL_CHESTPLATE = new ArmorItem(ModArmorMaterials.STEEL, ArmorItem.Type.CHESTPLATE, new FabricItemSettings());
    public static final ArmorItem STEEL_LEGGINGS = new ArmorItem(ModArmorMaterials.STEEL, ArmorItem.Type.LEGGINGS, new FabricItemSettings());
    public static final ArmorItem STEEL_BOOTS = new ArmorItem(ModArmorMaterials.STEEL, ArmorItem.Type.BOOTS, new FabricItemSettings());

    // DALEKANIUM GEAR
    public static final Item LIQUID_DALEKANIUM = new Item(new FabricItemSettings());
    public static final SwordItem DALEKANIUM_SWORD = new SwordItem(ModToolMaterials.DALEKANIUM, 3, -2.4F, new FabricItemSettings());
    public static final PickaxeItem DALEKANIUM_PICKAXE = new PickaxeItem(ModToolMaterials.DALEKANIUM, 1, -2.8F, new FabricItemSettings());
    public static final AxeItem DALEKANIUM_AXE = new AxeItem(ModToolMaterials.DALEKANIUM, 6.0F, -3.1F, new FabricItemSettings());
    public static final ShovelItem DALEKANIUM_SHOVEL = new ShovelItem(ModToolMaterials.DALEKANIUM, 1.5F, -3.0F, new FabricItemSettings());
    public static final HoeItem DALEKANIUM_HOE = new HoeItem(ModToolMaterials.DALEKANIUM, -2, -1.0F, new FabricItemSettings());
    public static final ArmorItem DALEKANIUM_HELMET = new ArmorItem(ModArmorMaterials.DALEKANIUM, ArmorItem.Type.HELMET, new FabricItemSettings());
    public static final ArmorItem DALEKANIUM_CHESTPLATE = new ArmorItem(ModArmorMaterials.DALEKANIUM, ArmorItem.Type.CHESTPLATE, new FabricItemSettings());
    public static final ArmorItem DALEKANIUM_LEGGINGS = new ArmorItem(ModArmorMaterials.DALEKANIUM, ArmorItem.Type.LEGGINGS, new FabricItemSettings());
    public static final ArmorItem DALEKANIUM_BOOTS = new ArmorItem(ModArmorMaterials.DALEKANIUM, ArmorItem.Type.BOOTS, new FabricItemSettings());

    // METALERTANIUM GEAR
    public static final Item LIQUID_METALERTANIUM = new Item(new FabricItemSettings().fireproof());
    public static final Item METALERTANIUM_INGOT = new Item(new FabricItemSettings().fireproof());
    public static final SwordItem METALERTANIUM_SWORD = new SwordItem(ModToolMaterials.METALERTANIUM, 3, -2.4F, new FabricItemSettings().fireproof());
    public static final PickaxeItem METALERTANIUM_PICKAXE = new PickaxeItem(ModToolMaterials.METALERTANIUM, 1, -2.8F, new FabricItemSettings().fireproof());
    public static final AxeItem METALERTANIUM_AXE = new AxeItem(ModToolMaterials.METALERTANIUM, 5.0F, -3.0F, new FabricItemSettings().fireproof());
    public static final ShovelItem METALERTANIUM_SHOVEL = new ShovelItem(ModToolMaterials.METALERTANIUM, 1.5F, -3.0F, new FabricItemSettings().fireproof());
    public static final HoeItem METALERTANIUM_HOE = new HoeItem(ModToolMaterials.METALERTANIUM, -4, 0.0F, new FabricItemSettings().fireproof());
    public static final ArmorItem METALERTANIUM_HELMET = new ArmorItem(ModArmorMaterials.METALERTANIUM, ArmorItem.Type.HELMET, new FabricItemSettings().fireproof());
    public static final ArmorItem METALERTANIUM_CHESTPLATE = new ArmorItem(ModArmorMaterials.METALERTANIUM, ArmorItem.Type.CHESTPLATE, new FabricItemSettings().fireproof());
    public static final ArmorItem METALERTANIUM_LEGGINGS = new ArmorItem(ModArmorMaterials.METALERTANIUM, ArmorItem.Type.LEGGINGS, new FabricItemSettings().fireproof());
    public static final ArmorItem METALERTANIUM_BOOTS = new ArmorItem(ModArmorMaterials.METALERTANIUM, ArmorItem.Type.BOOTS, new FabricItemSettings().fireproof());

    // FOOD ITEMS
    public static final Item MAPLE_SYRUP = registerItem("maple_syrup", new Item(new FabricItemSettings().food(GallifreyFoodComponents.MAPLE_SYRUP)));
    public static final Item TREEBORG_PASTE = registerItem("treeborg_paste", new Item(new FabricItemSettings().food(GallifreyFoodComponents.TREEBORG_PASTE)));

    //music disks
    public static final Item DW_XIV_MUSIC_DISC = registerItem("music_disk_a",
            new MusicDiscItem(7, GallifreySounds.DWXIV, new FabricItemSettings().maxCount(1), 151));
    public static final Item GALLIFREY_MUSIC_DISC = registerItem("music_disk_b",
            new MusicDiscItem(7, GallifreySounds.GALLIFREY, new FabricItemSettings().maxCount(1), 198));

    public static final Item RADIATION_BUCKET = registerItem("radiation_bucket",
            new BucketItem(GallifreyFluids.STILL_RADIATION, new FabricItemSettings().recipeRemainder(Items.BUCKET).maxCount(1)));

    public static final Item CLASSIC_WATER_BUCKET = registerItem("classic_water_bucket",
            new BucketItem(GallifreyFluids.STILL_CLASSIC_WATER, new FabricItemSettings().recipeRemainder(Items.BUCKET).maxCount(1)));
    public static final Item CLASSIC_LAVA_BUCKET = registerItem("classic_lava_bucket",
            new BucketItem(GallifreyFluids.STILL_CLASSIC_LAVA, new FabricItemSettings().recipeRemainder(Items.BUCKET).maxCount(1)));

    // Clothing
    public static final HeadwearItem FEZ = new HeadwearItem(new FabricItemSettings());
    public static final HeadwearItem FANCYFEZ = new HeadwearItem(new FabricItemSettings());
    public static final HeadwearItem PURPLEFEZ = new HeadwearItem(new FabricItemSettings());
    public static final HeadwearItem GREENFEZ = new HeadwearItem(new FabricItemSettings());
    public static final HeadwearItem ORANGEFEZ = new HeadwearItem(new FabricItemSettings());
    public static final HeadwearItem BLUEFEZ = new HeadwearItem(new FabricItemSettings());
    public static final HeadwearItem DARKBLUEFEZ = new HeadwearItem(new FabricItemSettings());
    public static final HeadwearItem PINKFEZ = new HeadwearItem(new FabricItemSettings());
    public static final HeadwearItem GREYFEZ = new HeadwearItem(new FabricItemSettings());
    public static final HeadwearItem YELLOWFEZ = new HeadwearItem(new FabricItemSettings());
    public static final HeadwearItem TRUSTABLE_HAT = new HeadwearItem(new FabricItemSettings());
    public static final HeadwearItem EYESTALK = new HeadwearItem(new FabricItemSettings());


    public static final SonicScrewdriver SONIC_SCREWDRIVER =
            new SonicScrewdriver(
                    new FabricItemSettings()
                            .maxCount(1)
                            .maxDamage(100)
            );

    public static final SonicShadesItem SONIC_SHADES =
            new SonicShadesItem(
                    new FabricItemSettings()
                            .maxDamage(100)
            );

    // Atrium materials
    public static final Item ATRIUM = new Item(new FabricItemSettings());
    public static final Item ATRIUM_FUEL = new Item(new FabricItemSettings());
    public static final Item ENERGIZED_ATRIUM = new Item(new FabricItemSettings());
    public static final Item ATRIUM_CORE = new Item(new FabricItemSettings());

    //Dice Tablet :3
    public static final Item DICE_TABLET = new DiceTabletItem(new FabricItemSettings().maxCount(1));





    public static void register() {
        registerItem("white_point_star", WHITE_POINT_STAR);
        registerItem("huon_particle", HUON_PARTICLE);
        registerItem("silicone", SILICONE);
        registerItem("vortex_manipulator", VORTEX_MANIPULATOR);
        registerItem("blank_circuit", BLANK_CIRCUIT);
        registerItem("location_circuit",LOCATION_CIRCUIT);
        registerItem("interface_circuit", INTERFACE_CIRCUIT);
        registerItem("dimension_circuit",DIMENSION_CIRCUIT);
        registerItem("raw_sonic_crystal",RAW_SONIC_CRYSTAL);
        registerItem("refined_sonic_crystal",REFINED_SONIC_CRYSTAL);
        registerItem("steel_ingot", STEEL_INGOT);
        registerItem("raw_steel", RAW_STEEL);
        registerItem("dalekanium_ingot", DALEKANIUM_INGOT);
        registerItem("prehistoric_ingot", PREHISTORIC_INGOT);
        registerItem("prehistoric_upgrade_smithing_template", PREHISTORIC_UPGRADE_SMITHING_TEMPLATE);
        registerItem("prehistoric_sword", PREHISTORIC_SWORD);
        registerItem("prehistoric_pickaxe", PREHISTORIC_PICKAXE);
        registerItem("prehistoric_axe", PREHISTORIC_AXE);
        registerItem("prehistoric_shovel", PREHISTORIC_SHOVEL);
        registerItem("prehistoric_hoe", PREHISTORIC_HOE);
        registerItem("prehistoric_helmet", PREHISTORIC_HELMET);
        registerItem("prehistoric_chestplate", PREHISTORIC_CHESTPLATE);
        registerItem("prehistoric_leggings", PREHISTORIC_LEGGINGS);
        registerItem("prehistoric_boots", PREHISTORIC_BOOTS);

        registerItem("steel_template", STEEL_TEMPLATE);
        registerItem("steel_sword", STEEL_SWORD);
        registerItem("steel_pickaxe", STEEL_PICKAXE);
        registerItem("steel_axe", STEEL_AXE);
        registerItem("steel_shovel", STEEL_SHOVEL);
        registerItem("steel_hoe", STEEL_HOE);
        registerItem("steel_helmet", STEEL_HELMET);
        registerItem("steel_chestplate", STEEL_CHESTPLATE);
        registerItem("steel_leggings", STEEL_LEGGINGS);
        registerItem("steel_boots", STEEL_BOOTS);

        registerItem("liquid_dalekanium", LIQUID_DALEKANIUM);
        registerItem("dalekanium_sword", DALEKANIUM_SWORD);
        registerItem("dalekanium_pickaxe", DALEKANIUM_PICKAXE);
        registerItem("dalekanium_axe", DALEKANIUM_AXE);
        registerItem("dalekanium_shovel", DALEKANIUM_SHOVEL);
        registerItem("dalekanium_hoe", DALEKANIUM_HOE);
        registerItem("dalekanium_helmet", DALEKANIUM_HELMET);
        registerItem("dalekanium_chestplate", DALEKANIUM_CHESTPLATE);
        registerItem("dalekanium_leggings", DALEKANIUM_LEGGINGS);
        registerItem("dalekanium_boots", DALEKANIUM_BOOTS);

        registerItem("liquid_metalertanium", LIQUID_METALERTANIUM);
        registerItem("metalertanium_ingot", METALERTANIUM_INGOT);
        registerItem("metalertanium_sword", METALERTANIUM_SWORD);
        registerItem("metalertanium_pickaxe", METALERTANIUM_PICKAXE);
        registerItem("metalertanium_axe", METALERTANIUM_AXE);
        registerItem("metalertanium_shovel", METALERTANIUM_SHOVEL);
        registerItem("metalertanium_hoe", METALERTANIUM_HOE);
        registerItem("metalertanium_helmet", METALERTANIUM_HELMET);
        registerItem("metalertanium_chestplate", METALERTANIUM_CHESTPLATE);
        registerItem("metalertanium_leggings", METALERTANIUM_LEGGINGS);
        registerItem("metalertanium_boots", METALERTANIUM_BOOTS);

        registerItem("sonic_screwdriver", SONIC_SCREWDRIVER);
        registerItem("sonic_shades", SONIC_SHADES);

        registerItem("atrium", ATRIUM);
        registerItem("atrium_fuel", ATRIUM_FUEL);
        registerItem("energized_atrium", ENERGIZED_ATRIUM);
        registerItem("atrium_core", ATRIUM_CORE);
        registerItem("fez", FEZ);
        registerItem("fancyfez", FANCYFEZ);
        registerItem("purplefez", PURPLEFEZ);
        registerItem("greenfez", GREENFEZ);
        registerItem("orangefez", ORANGEFEZ);
        registerItem("bluefez", BLUEFEZ);
        registerItem("darkbluefez", DARKBLUEFEZ);
        registerItem("pinkfez", PINKFEZ);
        registerItem("greyfez", GREYFEZ);
        registerItem("yellowfez", YELLOWFEZ);
        registerItem("trustable_hat", TRUSTABLE_HAT);
        registerItem("eyestalk", EYESTALK);
        registerItem("omega_helmet", OMEGA_HELMET);

        registerItem("dice_tablet", DICE_TABLET);

        registerItem("skaro_city_dalek_spawn_egg", SKARO_CITY_DALEK_SPAWN_EGG);

        // Classic-era items are separately registered under gallifrey:classic_* ids.
        registerItem("classic_apple", CLASSIC_APPLE);
        registerItem("classic_apple_golden", CLASSIC_APPLE_GOLDEN);
        registerItem("classic_carrot_golden", CLASSIC_CARROT_GOLDEN);
        registerItem("classic_chainmail_boots", CLASSIC_CHAINMAIL_BOOTS);
        registerItem("classic_chainmail_chestplate", CLASSIC_CHAINMAIL_CHESTPLATE);
        registerItem("classic_chainmail_helmet", CLASSIC_CHAINMAIL_HELMET);
        registerItem("classic_chainmail_leggings", CLASSIC_CHAINMAIL_LEGGINGS);
        registerItem("classic_coal", CLASSIC_COAL);
        registerItem("classic_diamond", CLASSIC_DIAMOND);
        registerItem("classic_diamond_axe", CLASSIC_DIAMOND_AXE);
        registerItem("classic_diamond_boots", CLASSIC_DIAMOND_BOOTS);
        registerItem("classic_diamond_chestplate", CLASSIC_DIAMOND_CHESTPLATE);
        registerItem("classic_diamond_helmet", CLASSIC_DIAMOND_HELMET);
        registerItem("classic_diamond_hoe", CLASSIC_DIAMOND_HOE);
        registerItem("classic_diamond_horse_armor", CLASSIC_DIAMOND_HORSE_ARMOR);
        registerItem("classic_diamond_leggings", CLASSIC_DIAMOND_LEGGINGS);
        registerItem("classic_diamond_pickaxe", CLASSIC_DIAMOND_PICKAXE);
        registerItem("classic_diamond_shovel", CLASSIC_DIAMOND_SHOVEL);
        registerItem("classic_diamond_sword", CLASSIC_DIAMOND_SWORD);
        registerItem("classic_door_iron", CLASSIC_DOOR_IRON);
        registerItem("classic_emerald", CLASSIC_EMERALD);
        registerItem("classic_flint", CLASSIC_FLINT);
        registerItem("classic_flint_and_steel", CLASSIC_FLINT_AND_STEEL);
        registerItem("classic_gold_axe", CLASSIC_GOLD_AXE);
        registerItem("classic_gold_boots", CLASSIC_GOLD_BOOTS);
        registerItem("classic_gold_chestplate", CLASSIC_GOLD_CHESTPLATE);
        registerItem("classic_gold_helmet", CLASSIC_GOLD_HELMET);
        registerItem("classic_gold_hoe", CLASSIC_GOLD_HOE);
        registerItem("classic_gold_horse_armor", CLASSIC_GOLD_HORSE_ARMOR);
        registerItem("classic_gold_ingot", CLASSIC_GOLD_INGOT);
        registerItem("classic_gold_leggings", CLASSIC_GOLD_LEGGINGS);
        registerItem("classic_gold_nugget", CLASSIC_GOLD_NUGGET);
        registerItem("classic_gold_pickaxe", CLASSIC_GOLD_PICKAXE);
        registerItem("classic_gold_shovel", CLASSIC_GOLD_SHOVEL);
        registerItem("classic_gold_sword", CLASSIC_GOLD_SWORD);
        registerItem("classic_iron_axe", CLASSIC_IRON_AXE);
        registerItem("classic_iron_boots", CLASSIC_IRON_BOOTS);
        registerItem("classic_iron_chestplate", CLASSIC_IRON_CHESTPLATE);
        registerItem("classic_iron_helmet", CLASSIC_IRON_HELMET);
        registerItem("classic_iron_hoe", CLASSIC_IRON_HOE);
        registerItem("classic_iron_horse_armor", CLASSIC_IRON_HORSE_ARMOR);
        registerItem("classic_iron_ingot", CLASSIC_IRON_INGOT);
        registerItem("classic_iron_leggings", CLASSIC_IRON_LEGGINGS);
        registerItem("classic_iron_pickaxe", CLASSIC_IRON_PICKAXE);
        registerItem("classic_iron_shovel", CLASSIC_IRON_SHOVEL);
        registerItem("classic_iron_sword", CLASSIC_IRON_SWORD);
        registerItem("classic_leather", CLASSIC_LEATHER);
        registerItem("classic_leather_boots", CLASSIC_LEATHER_BOOTS);
        registerItem("classic_leather_chestplate", CLASSIC_LEATHER_CHESTPLATE);
        registerItem("classic_leather_helmet", CLASSIC_LEATHER_HELMET);
        registerItem("classic_leather_leggings", CLASSIC_LEATHER_LEGGINGS);
        registerItem("classic_oak_boat", CLASSIC_OAK_BOAT);
        registerItem("classic_stick", CLASSIC_STICK);
        registerItem("classic_wood_axe", CLASSIC_WOOD_AXE);
        registerItem("classic_wood_hoe", CLASSIC_WOOD_HOE);
        registerItem("classic_wood_pickaxe", CLASSIC_WOOD_PICKAXE);
        registerItem("classic_wood_shovel", CLASSIC_WOOD_SHOVEL);
        registerItem("classic_wood_sword", CLASSIC_WOOD_SWORD);

        GallifreyMod.LOGGER.debug("[Gallifrey] Items registered.");
    }

    private static <T extends Item> T registerItem(String name, T item) {
        return Registry.register(Registries.ITEM, new Identifier(GallifreyMod.MOD_ID, name), item);
    }
}