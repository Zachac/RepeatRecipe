package net.fabricmc.repeatrecipe.mixin.accessor;

import net.minecraft.client.gui.screen.ingame.CraftingScreen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(CraftingScreen.class)
public interface CraftingScreenAccessor {

    @Accessor
    public RecipeBookWidget getRecipeBook();
}
