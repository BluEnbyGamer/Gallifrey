package com.timelordmod.gallifrey.entity.client;

import com.timelordmod.gallifrey.GallifreyMod;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.VillagerEntityRenderer;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.util.Identifier;

/** Modern villager model and rendering, with the custom Time Lord villager texture. */
public class TimeLordVillagerRenderer extends VillagerEntityRenderer {
    private static final Identifier TEXTURE = new Identifier(GallifreyMod.MOD_ID,
            "textures/entity/timelord_villager.png");

    public TimeLordVillagerRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(VillagerEntity entity) {
        return TEXTURE;
    }
}
