package com.timelordmod.gallifrey.block;

import com.terraformersmc.terraform.sign.block.TerraformHangingSignBlock;
import com.terraformersmc.terraform.sign.block.TerraformSignBlock;
import com.terraformersmc.terraform.sign.block.TerraformWallHangingSignBlock;
import com.terraformersmc.terraform.sign.block.TerraformWallSignBlock;
import com.timelordmod.gallifrey.GallifreyMod;
import com.timelordmod.gallifrey.world.tree.TardisSaplingGenerator;
import com.timelordmod.gallifrey.world.tree.UlandaSaplingGenerator;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.minecraft.block.*;
import net.minecraft.data.family.BlockFamilies;
import net.minecraft.data.family.BlockFamily;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;


/**
 * All the blocks for the mod get registered here.
 *
 * Adding a new one? do this:
 *  1. Make the block class
 *  2. Register it below
 *  3. Block model json in assets/regeneration/models/block/
 *  4. Texture in assets/regeneration/textures/block/
 *  5. Item model json in assets/regeneration/models/item/
 *  6. Add it to the creative tab
 */


public class GallifreyModBlocks {

    //misc blocks
    public static final Block REINFORCED_STEEL_BLOCK = registerBlock("reinforced_steel_block",
            new Block(FabricBlockSettings.copyOf(Blocks.OBSIDIAN).sounds(BlockSoundGroup.METAL)));


    public static final Block TARDIS_LEAVES = registerBlock("tardis_leaves",
            new LeavesBlock(FabricBlockSettings.copyOf(Blocks.OAK_LEAVES)));

    public static final Block TARDIS_WOOD = registerBlock("tardis_wood",
            new PillarBlock(FabricBlockSettings.copyOf(Blocks.OAK_WOOD)));

    public static final Block TARDIS_LOG = registerBlock("tardis_log",
            new PillarBlock(FabricBlockSettings.copyOf(Blocks.OAK_LOG)));

    public static final Block   STRIP_TARDIS_LOG = registerBlock("stripped_tardis_log",
            new PillarBlock(FabricBlockSettings.copyOf(Blocks.OAK_LOG)));

    public static final Block STRIP_TARDIS_WOOD = registerBlock("stripped_tardis_wood",
            new PillarBlock(FabricBlockSettings.copyOf(Blocks.OAK_WOOD)));

    public static final Block TARDIS_PLANKS = registerBlock("tardis_planks",
            new Block(FabricBlockSettings.copyOf(Blocks.OAK_WOOD)));

    public static final Block TARDIS_SAPLING = registerBlock("tardis_sapling",
            new SaplingBlock(new TardisSaplingGenerator(), FabricBlockSettings.copyOf(Blocks.OAK_SAPLING)));

    public static final Block POTTED_TARDIS_SAPLING = registerBlock("potted_tardis_sapling",
            new FlowerPotBlock(GallifreyModBlocks.TARDIS_SAPLING, FabricBlockSettings.copyOf(Blocks.POTTED_OAK_SAPLING)));

    public static final Block TARDIS_STAIRS = registerBlock("tardis_stairs",
            new StairsBlock(GallifreyModBlocks.TARDIS_PLANKS.getDefaultState() ,FabricBlockSettings.copyOf(Blocks.OAK_WOOD)));

    public static final Block TARDIS_SLAB = registerBlock("tardis_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.OAK_WOOD)));

    public static final Block TARDIS_BUTTON = registerBlock("tardis_button",
            new ButtonBlock(FabricBlockSettings.copyOf(Blocks.OAK_WOOD), BlockSetType.OAK,15, true));

    public static final Block TARDIS_PRESSURE_PLATE = registerBlock("tardis_pressure_plate",
            new PressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, FabricBlockSettings.copyOf(Blocks.OAK_WOOD), BlockSetType.OAK));

    public static final Block TARDIS_FENCE = registerBlock("tardis_fence",
            new FenceBlock(FabricBlockSettings.copyOf(Blocks.OAK_WOOD)));

    public static final Block TARDIS_FENCE_GATE = registerBlock("tardis_fence_gate",
            new FenceGateBlock(FabricBlockSettings.copyOf(Blocks.OAK_WOOD), WoodType.OAK));

    public static final Block TARDIS_WOOD_DOOR = registerBlock("tardis_wood_door",
            new DoorBlock(FabricBlockSettings.copyOf(Blocks.OAK_DOOR), BlockSetType.OAK));

    public static final Block TARDIS_TRAPDOOR = registerBlock("tardis_trapdoor",
            new TrapdoorBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS), BlockSetType.OAK));

    public static final Identifier TARDIS_SIGN_TEXTURE = new Identifier(GallifreyMod.MOD_ID, "entity/signs/tardis");
    public static final Identifier TARDIS_HANGING_SIGN_TEXTURE = new Identifier(GallifreyMod.MOD_ID, "entity/signs/hanging/tardis");
    public static final Identifier TARDIS_HANGING_GUI_SIGN_TEXTURE = new Identifier(GallifreyMod.MOD_ID, "textures/gui/hanging_signs/tardis");

    public static final Block STANDING_TARDIS_SIGN = Registry.register(Registries.BLOCK, new Identifier(GallifreyMod.MOD_ID, "tardis_standing_sign"),
            new TerraformSignBlock(TARDIS_SIGN_TEXTURE, FabricBlockSettings.copyOf(Blocks.OAK_SIGN)));
    public static final Block WALL_TARDIS_SIGN = Registry.register(Registries.BLOCK, new Identifier(GallifreyMod.MOD_ID, "tardis_wall_sign"),
            new TerraformWallSignBlock(TARDIS_SIGN_TEXTURE, FabricBlockSettings.copyOf(Blocks.OAK_WALL_SIGN)));
    public static final Block HANGING_TARDIS_SIGN = Registry.register(Registries.BLOCK, new Identifier(GallifreyMod.MOD_ID, "tardis_hanging_sign"),
            new TerraformHangingSignBlock(TARDIS_HANGING_SIGN_TEXTURE, TARDIS_HANGING_GUI_SIGN_TEXTURE, FabricBlockSettings.copyOf(Blocks.OAK_HANGING_SIGN)));
    public static final Block WALL_HANGING_TARDIS_SIGN = Registry.register(Registries.BLOCK, new Identifier(GallifreyMod.MOD_ID, "tardis_wall_hanging_sign"),
            new TerraformWallHangingSignBlock(TARDIS_HANGING_SIGN_TEXTURE, TARDIS_HANGING_GUI_SIGN_TEXTURE, FabricBlockSettings.copyOf(Blocks.OAK_WALL_HANGING_SIGN)));

    public static final BlockFamily TARDIS_FAMILY = BlockFamilies.register(GallifreyModBlocks.TARDIS_PLANKS)
            .sign(GallifreyModBlocks.STANDING_TARDIS_SIGN, GallifreyModBlocks.WALL_TARDIS_SIGN)
            .group("wooden").unlockCriterionName("has_planks").build();



    //ULANDA WOOD SET BLOCK REGISTRY
    public static final Block ULANDA_LEAVES = registerBlock("ulanda_leaves",
            new LeavesBlock(FabricBlockSettings.copyOf(Blocks.OAK_LEAVES)));

    public static final Block ULANDA_WOOD = registerBlock("ulanda_wood",
            new PillarBlock(FabricBlockSettings.copyOf(Blocks.OAK_WOOD)));

    public static final Block ULANDA_LOG = registerBlock("ulanda_log",
            new PillarBlock(FabricBlockSettings.copyOf(Blocks.OAK_LOG)));

    public static final Block   STRIP_ULANDA_LOG = registerBlock("stripped_ulanda_log",
            new PillarBlock(FabricBlockSettings.copyOf(Blocks.OAK_LOG)));

    public static final Block STRIP_ULANDA_WOOD = registerBlock("stripped_ulanda_wood",
            new PillarBlock(FabricBlockSettings.copyOf(Blocks.OAK_WOOD)));

    public static final Block ULANDA_PLANKS = registerBlock("ulanda_planks",
            new Block(FabricBlockSettings.copyOf(Blocks.OAK_WOOD)));

    public static final Block ULANDA_SAPLING = registerBlock("ulanda_sapling",
            new SaplingBlock(new UlandaSaplingGenerator(), FabricBlockSettings.copyOf(Blocks.OAK_SAPLING)));

    public static final Block POTTED_ULANDA_SAPLING = registerBlock("potted_ulanda_sapling",
            new FlowerPotBlock(GallifreyModBlocks.ULANDA_SAPLING, FabricBlockSettings.copyOf(Blocks.POTTED_OAK_SAPLING)));

    public static final Block ULANDA_STAIRS = registerBlock("ulanda_stairs",
            new StairsBlock(GallifreyModBlocks.ULANDA_PLANKS.getDefaultState() ,FabricBlockSettings.copyOf(Blocks.OAK_WOOD)));

    public static final Block ULANDA_SLAB = registerBlock("ulanda_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.OAK_WOOD)));

    public static final Block ULANDA_BUTTON = registerBlock("ulanda_button",
            new ButtonBlock(FabricBlockSettings.copyOf(Blocks.OAK_WOOD), BlockSetType.OAK,15, true));

    public static final Block ULANDA_PRESSURE_PLATE = registerBlock("ulanda_pressure_plate",
            new PressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, FabricBlockSettings.copyOf(Blocks.OAK_WOOD), BlockSetType.OAK));

    public static final Block ULANDA_FENCE = registerBlock("ulanda_fence",
            new FenceBlock(FabricBlockSettings.copyOf(Blocks.OAK_WOOD)));

    public static final Block ULANDA_FENCE_GATE = registerBlock("ulanda_fence_gate",
            new FenceGateBlock(FabricBlockSettings.copyOf(Blocks.OAK_WOOD), WoodType.OAK));


    public static final Block ULANDA_DOOR = registerBlock("ulanda_door",
            new DoorBlock(FabricBlockSettings.copyOf(Blocks.ACACIA_DOOR), BlockSetType.ACACIA));

    public static final Block ULANDA_TRAPDOOR = registerBlock("ulanda_trapdoor",
            new TrapdoorBlock(FabricBlockSettings.copyOf(Blocks.ACACIA_DOOR), BlockSetType.ACACIA));

    public static final Identifier ULANDA_SIGN_TEXTURE = new Identifier(GallifreyMod.MOD_ID, "entity/signs/ulanda");
    public static final Identifier ULANDA_HANGING_SIGN_TEXTURE = new Identifier(GallifreyMod.MOD_ID, "entity/signs/hanging/ulanda");
    public static final Identifier ULANDA_HANGING_GUI_SIGN_TEXTURE = new Identifier(GallifreyMod.MOD_ID, "textures/gui/hanging_signs/ulanda");

    public static final Block STANDING_ULANDA_SIGN = Registry.register(Registries.BLOCK, new Identifier(GallifreyMod.MOD_ID, "ulanda_standing_sign"),
            new TerraformSignBlock(ULANDA_SIGN_TEXTURE, FabricBlockSettings.copyOf(Blocks.ACACIA_SIGN)));
    public static final Block WALL_ULANDA_SIGN = Registry.register(Registries.BLOCK, new Identifier(GallifreyMod.MOD_ID, "ulanda_wall_sign"),
            new TerraformWallSignBlock(ULANDA_SIGN_TEXTURE, FabricBlockSettings.copyOf(Blocks.ACACIA_WALL_SIGN)));
    public static final Block HANGING_ULANDA_SIGN = Registry.register(Registries.BLOCK, new Identifier(GallifreyMod.MOD_ID, "ulanda_hanging_sign"),
            new TerraformHangingSignBlock(ULANDA_HANGING_SIGN_TEXTURE, ULANDA_HANGING_GUI_SIGN_TEXTURE, FabricBlockSettings.copyOf(Blocks.ACACIA_HANGING_SIGN)));
    public static final Block WALL_HANGING_ULANDA_SIGN = Registry.register(Registries.BLOCK, new Identifier(GallifreyMod.MOD_ID, "ulanda_wall_hanging_sign"),
            new TerraformWallHangingSignBlock(ULANDA_HANGING_SIGN_TEXTURE, ULANDA_HANGING_GUI_SIGN_TEXTURE, FabricBlockSettings.copyOf(Blocks.ACACIA_WALL_HANGING_SIGN)));

    public static final BlockFamily ULANDA_FAMILY = BlockFamilies.register(GallifreyModBlocks.ULANDA_PLANKS)
            .sign(GallifreyModBlocks.STANDING_ULANDA_SIGN, GallifreyModBlocks.WALL_ULANDA_SIGN)
            .group("wooden").unlockCriterionName("has_planks").build();

    //TREE-BORG WOOD SET BLOCK REGISTRY


    //ASH WOOD SET BLOCK REGISTRY


    //MAPLE WOOD SET BLOCK REGISTRY


    //WILLOW WOOD SET BLOCK REGISTRY


    //MOON-PINE WOOD SET BLOCK REGISTRY


    // MISC BLOCKS REGISTRY


    private static Block registerBlock(String name, Block block) {
        registerBlockitem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(GallifreyMod.MOD_ID, name), block);
    }
    private static Item registerBlockitem(String name, Block block) {
        return Registry.register(Registries.ITEM, new Identifier(GallifreyMod.MOD_ID, name),
                new BlockItem(block,new FabricItemSettings()));
    }

    public static void register(){
        GallifreyMod.LOGGER.info("Registering ModBlocks for " + GallifreyMod.MOD_ID );
        registerFlammable();
    }

    private static void registerFlammable() {
        FlammableBlockRegistry flammable = FlammableBlockRegistry.getDefaultInstance();

        //misc flammables


        //tardis woood set
        flammable.add(TARDIS_LOG, 5, 5);
        flammable.add(STRIP_TARDIS_LOG, 5, 5);
        flammable.add(TARDIS_WOOD, 5, 5);
        flammable.add(STRIP_TARDIS_WOOD, 5, 5);
        flammable.add(TARDIS_PLANKS, 5, 20);
        flammable.add(TARDIS_LEAVES, 30, 60);
        flammable.add(TARDIS_STAIRS, 5, 20);
        flammable.add(TARDIS_SLAB, 5, 20);

        //Ulanda wood set
        flammable.add(ULANDA_LOG, 5, 5);
        flammable.add(STRIP_ULANDA_LOG, 5, 5);
        flammable.add(ULANDA_WOOD, 5, 5);
        flammable.add(STRIP_ULANDA_WOOD, 5, 5);
        flammable.add(ULANDA_PLANKS, 5, 20);
        flammable.add(ULANDA_LEAVES, 30, 60);
        flammable.add(ULANDA_STAIRS, 5, 20);
        flammable.add(ULANDA_SLAB, 5, 20);


        //Tree-borg wood set
        //flammable.add(TREEBORRG_LOG, 5, 5);
        //flammable.add(STRIP_TREEBORRG_LOG, 5, 5);
        //flammable.add(TREEBORRG_WOOD, 5, 5);
        //flammable.add(STRIP_TREEBORRG_WOOD, 5, 5);
        //flammable.add(TREEBORRG_PLANKS, 5, 20);
        //flammable.add(TREEBORRG_LEAVES, 30, 60);

        //Ash wood set
        //flammable.add(ASH_LOG, 5, 5);
        //flammable.add(STRIP_ASH_LOG, 5, 5);
        //flammable.add(ASH_WOOD, 5, 5);
        //flammable.add(STRIP_ASH_WOOD, 5, 5);
        //flammable.add(ASH_PLANKS, 5, 20);
        //flammable.add(ASH_LEAVES, 30, 60);

        //Maple wood set
        //flammable.add(MAPLE_LOG, 5, 5);
        //flammable.add(STRIP_MAPLE_LOG, 5, 5);
        //flammable.add(MAPLE_WOOD, 5, 5);
        //flammable.add(STRIP_MAPLE_WOOD, 5, 5);
        //flammable.add(MAPLE_PLANKS, 5, 20);
        //flammable.add(MAPLE_LEAVES, 30, 60);

        //Willow wood set
        //flammable.add(WILLOW_LOG, 5, 5);
        //flammable.add(STRIP_WILLOW_LOG, 5, 5);
        //flammable.add(WILLOW_WOOD, 5, 5);
        //flammable.add(STRIP_WILLOW_WOOD, 5, 5);
        //flammable.add(WILLOW_PLANKS, 5, 20);
        //flammable.add(WILLOW_LEAVES, 30, 60);

        //Moon-pine wood set
        //flammable.add(MOONPINE_LOG, 5, 5);
        //flammable.add(STRIP_MOONPINE_LOG, 5, 5);
        //flammable.add(MOONPINE_WOOD, 5, 5);
        //flammable.add(STRIP_MOONPINE_WOOD, 5, 5);
        //flammable.add(MOONPINE_PLANKS, 5, 20);
        //flammable.add(MOONPINE_LEAVES, 30, 60);

        //Misc flammables

    }
}