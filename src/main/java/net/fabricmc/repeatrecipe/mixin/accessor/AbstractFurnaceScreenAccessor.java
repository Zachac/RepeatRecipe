package net.fabricmc.repeatrecipe.mixin.accessor;

import net.minecraft.client.gui.screen.ingame.AbstractFurnaceScreen;
import net.minecraft.client.gui.screen.recipebook.AbstractFurnaceRecipeBookScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AbstractFurnaceScreen.class)
public interface AbstractFurnaceScreenAccessor {

    @Accessor
    public AbstractFurnaceRecipeBookScreen getRecipeBook();

}
