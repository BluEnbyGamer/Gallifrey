package com.timelordmod.gallifrey.mixin.client;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(HandledScreen.class)
public interface HandledScreenAccessor {

    /** Left edge of the GUI background. */
    @Accessor("x")
    int gallifrey$getX();

    /** Top edge of the GUI background. */
    @Accessor("y")
    int gallifrey$getY();

    /** Height of the GUI background (136 for the creative inventory). */
    @Accessor("backgroundHeight")
    int gallifrey$getBackgroundHeight();
}
