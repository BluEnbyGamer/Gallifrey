package com.timelordmod.gallifrey.worldgen;

import com.timelordmod.gallifrey.mixin.FoliagePlacerTypeInvoker;
import com.timelordmod.gallifrey.mixin.TrunkPlacerTypeInvoker;
import com.timelordmod.gallifrey.worldgen.tree.TardisTreeFoliagePlacer;
import com.timelordmod.gallifrey.worldgen.tree.TardisTreeTrunkPlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

public class GallifreyWorldgen {

    public static final TrunkPlacerType<TardisTreeTrunkPlacer> TARDIS_TREE_TRUNK_PLACER =
            TrunkPlacerTypeInvoker.callRegister("gallifrey:tardis_tree_trunk_placer", TardisTreeTrunkPlacer.CODEC);

    public static final FoliagePlacerType<TardisTreeFoliagePlacer> TARDIS_TREE_FOLIAGE_PLACER =
            FoliagePlacerTypeInvoker.callRegister("gallifrey:tardis_tree_foliage_placer", TardisTreeFoliagePlacer.CODEC);

    // Static fields only run their initializers once this class actually loads, and Java
    // doesn't load a class until something touches it. Calling this (empty) method from
    // GallifreyMod.onInitialize() is what makes that happen, at a predictable point during
    // startup — without it, both fields above stay unregistered until whatever moment a
    // tree first tries to generate with them, which is too late and would crash.
    public static void init() {
    }
}