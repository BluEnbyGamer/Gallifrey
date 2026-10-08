package com.timelordmod.gallifrey.item.custom;

import com.timelordmod.gallifrey.item.GallifreyModItems;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

import java.util.function.Supplier;

/**
 * Tool tiers for the mod's metals.
 *
 * STEEL is a small step above iron while remaining well below diamond.
 * DALEKANIUM copies vanilla IRON exactly.
 * METALERTANIUM copies vanilla NETHERITE exactly.
 *
 * Order of the numbers: mining level, durability, mining speed, attack damage bonus, enchantability.
 */
public enum ModToolMaterials implements ToolMaterial {
    STEEL(2, 300, 6.5F, 2.25F, 14, () -> Ingredient.ofItems(GallifreyModItems.STEEL_INGOT)),
    DALEKANIUM(2, 250, 6.0F, 2.0F, 14, () -> Ingredient.ofItems(GallifreyModItems.DALEKANIUM_INGOT)),
    METALERTANIUM(4, 2031, 9.0F, 4.0F, 15, () -> Ingredient.ofItems(GallifreyModItems.METALERTANIUM_INGOT));

    private final int miningLevel;
    private final int durability;
    private final float miningSpeed;
    private final float attackDamage;
    private final int enchantability;
    // A supplier, so the ingot is only looked up when something is actually repaired
    // (the ingot items do not exist yet while this enum is being created).
    private final Supplier<Ingredient> repairIngredient;

    ModToolMaterials(int miningLevel, int durability, float miningSpeed, float attackDamage,
                     int enchantability, Supplier<Ingredient> repairIngredient) {
        this.miningLevel = miningLevel;
        this.durability = durability;
        this.miningSpeed = miningSpeed;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.repairIngredient = repairIngredient;
    }

    @Override public int getDurability() { return durability; }
    @Override public float getMiningSpeedMultiplier() { return miningSpeed; }
    @Override public float getAttackDamage() { return attackDamage; }
    @Override public int getMiningLevel() { return miningLevel; }
    @Override public int getEnchantability() { return enchantability; }
    @Override public Ingredient getRepairIngredient() { return repairIngredient.get(); }
}
