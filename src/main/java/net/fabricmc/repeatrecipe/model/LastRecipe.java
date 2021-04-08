package net.fabricmc.repeatrecipe.model;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.recipe.Recipe;

import java.util.Objects;

public class LastRecipe {

    public static LastRecipe instance;

    public final Recipe<?> recipe;
    public final Class<? extends Screen> screenClazz;

    public LastRecipe(Recipe<?> recipe, Class<? extends Screen> screenClazz) {
        this.recipe = Objects.requireNonNull(recipe);
        this.screenClazz = Objects.requireNonNull(screenClazz);
    }
}
