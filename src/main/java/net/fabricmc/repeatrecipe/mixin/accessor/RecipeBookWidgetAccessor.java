package net.fabricmc.repeatrecipe.mixin.accessor;

import net.minecraft.client.gui.screen.recipebook.RecipeBookGhostSlots;
import net.minecraft.client.gui.screen.recipebook.RecipeBookResults;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(RecipeBookWidget.class)
public interface RecipeBookWidgetAccessor {


    @Accessor
    public RecipeBookResults getRecipesArea();

    @Accessor
    public RecipeBookGhostSlots getGhostSlots();

}
