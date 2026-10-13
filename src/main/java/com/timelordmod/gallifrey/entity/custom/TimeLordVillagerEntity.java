package com.timelordmod.gallifrey.entity.custom;

import com.timelordmod.gallifrey.item.GallifreyModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOfferList;
import net.minecraft.world.World;

/**
 * Gallifrey's separate modern-model Time Lord villager with custom trade offers.
 */
public class TimeLordVillagerEntity extends VillagerEntity {
    private boolean omegaOfferRolled = false;

    public TimeLordVillagerEntity(EntityType<? extends VillagerEntity> type, World world) {
        super(type, world);
    }

    @Override
    protected void fillRecipes() {
        TradeOfferList offers = this.getOffers();

        // Core Gallifreyan goods, paid for with both forms of currency.
        offers.add(new TradeOffer(new ItemStack(Items.EMERALD, 4),
                new ItemStack(GallifreyModItems.BLANK_CIRCUIT, 1), 12, 2, 0.05F));
        offers.add(new TradeOffer(new ItemStack(Items.EMERALD, 3),
                new ItemStack(GallifreyModItems.REFINED_SONIC_CRYSTAL, 1), 12, 3, 0.05F));
        offers.add(new TradeOffer(new ItemStack(GallifreyModItems.CLASSIC_EMERALD, 4),
                new ItemStack(GallifreyModItems.BLANK_CIRCUIT, 1), 12, 2, 0.05F));
        offers.add(new TradeOffer(new ItemStack(GallifreyModItems.CLASSIC_EMERALD, 3),
                new ItemStack(GallifreyModItems.REFINED_SONIC_CRYSTAL, 1), 12, 3, 0.05F));

        // Sell a fez for either currency.
        offers.add(new TradeOffer(new ItemStack(Items.EMERALD, 6),
                new ItemStack(GallifreyModItems.FEZ, 1), 4, 2, 0.05F));
        offers.add(new TradeOffer(new ItemStack(GallifreyModItems.CLASSIC_EMERALD, 6),
                new ItemStack(GallifreyModItems.FEZ, 1), 4, 2, 0.05F));

        // The rare helmet offer is rolled once per villager, with either payment option.
        if (!omegaOfferRolled) {
            omegaOfferRolled = true;
            if (this.random.nextFloat() < 0.01F) {
                offers.add(new TradeOffer(new ItemStack(Items.EMERALD, 20),
                        new ItemStack(GallifreyModItems.OMEGA_HELMET, 1), 1, 10, 0.0F));
                offers.add(new TradeOffer(new ItemStack(GallifreyModItems.CLASSIC_EMERALD, 20),
                        new ItemStack(GallifreyModItems.OMEGA_HELMET, 1), 1, 10, 0.0F));
            }
        }
    }
}
