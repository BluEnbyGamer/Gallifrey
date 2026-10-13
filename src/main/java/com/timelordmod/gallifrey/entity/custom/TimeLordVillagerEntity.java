package com.timelordmod.gallifrey.entity.custom;

import com.timelordmod.gallifrey.item.GallifreyModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOfferList;
import net.minecraft.world.World;

/** A separate modern-model Time Lord villager with persistent custom trades. */
public class TimeLordVillagerEntity extends VillagerEntity {
    public TimeLordVillagerEntity(EntityType<? extends VillagerEntity> type, World world) {
        super(type, world);
    }

    @Override
    public TradeOfferList getOffers() {
        // Use the inherited merchant offer list rather than a separate transient list.
        // The vanilla merchant screen synchronizes this list, and the villager saves it.
        TradeOfferList offers = super.getOffers();
        if (offers.isEmpty()) {
            offers.add(new TradeOffer(new ItemStack(Items.EMERALD, 4), new ItemStack(GallifreyModItems.BLANK_CIRCUIT, 1), 12, 2, 0.05F));
            offers.add(new TradeOffer(new ItemStack(Items.EMERALD, 3), new ItemStack(GallifreyModItems.REFINED_SONIC_CRYSTAL, 1), 12, 3, 0.05F));
            offers.add(new TradeOffer(new ItemStack(GallifreyModItems.CLASSIC_EMERALD, 4), new ItemStack(GallifreyModItems.BLANK_CIRCUIT, 1), 12, 2, 0.05F));
            offers.add(new TradeOffer(new ItemStack(GallifreyModItems.CLASSIC_EMERALD, 3), new ItemStack(GallifreyModItems.REFINED_SONIC_CRYSTAL, 1), 12, 3, 0.05F));
            offers.add(new TradeOffer(new ItemStack(Items.EMERALD, 6), new ItemStack(GallifreyModItems.FEZ, 1), 4, 2, 0.05F));
            offers.add(new TradeOffer(new ItemStack(GallifreyModItems.CLASSIC_EMERALD, 6), new ItemStack(GallifreyModItems.FEZ, 1), 4, 2, 0.05F));
            // Roll once when the villager's offer list is first created; the result is then saved.
            if (this.random.nextFloat() < 0.01F) {
                offers.add(new TradeOffer(new ItemStack(Items.EMERALD, 20), new ItemStack(GallifreyModItems.OMEGA_HELMET, 1), 1, 10, 0.0F));
                offers.add(new TradeOffer(new ItemStack(GallifreyModItems.CLASSIC_EMERALD, 20), new ItemStack(GallifreyModItems.OMEGA_HELMET, 1), 1, 10, 0.0F));
            }
        }
        return offers;
    }
}
