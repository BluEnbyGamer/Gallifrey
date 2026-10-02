package com.timelordmod.gallifrey.item.custom;

import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class PrehistoricToolMaterial implements ToolMaterial {
    public static final PrehistoricToolMaterial INSTANCE = new PrehistoricToolMaterial();
    private PrehistoricToolMaterial() {}
    @Override public int getDurability() { return 1800; }
    @Override public float getMiningSpeedMultiplier() { return 8.5F; }
    @Override public float getAttackDamage() { return 4.0F; }
    @Override public int getMiningLevel() { return 3; }
    @Override public int getEnchantability() { return 12; }
    @Override public Ingredient getRepairIngredient() { return Ingredient.ofItems(com.timelordmod.gallifrey.item.GallifreyModItems.PREHISTORIC_INGOT); }
}
