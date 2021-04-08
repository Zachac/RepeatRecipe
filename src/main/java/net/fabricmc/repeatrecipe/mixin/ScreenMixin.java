package net.fabricmc.repeatrecipe.mixin;

import net.fabricmc.repeatrecipe.RepeatRecipe;
import net.fabricmc.repeatrecipe.mixin.accessor.AbstractFurnaceScreenAccessor;
import net.fabricmc.repeatrecipe.mixin.accessor.CraftingScreenAccessor;
import net.fabricmc.repeatrecipe.mixin.accessor.InventoryScreenAccessor;
import net.fabricmc.repeatrecipe.mixin.accessor.RecipeBookWidgetAccessor;
import net.fabricmc.repeatrecipe.model.LastRecipe;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Screen.class)
public class ScreenMixin {

    private MinecraftClient client = MinecraftClient.getInstance();


    private static final Logger log = LogManager.getLogger("RepeatRecipe");

    @Inject(at = @At("HEAD"), method = "keyPressed(III)Z", cancellable = true)
    public void keyPressed(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> info) {

        // default r
        if (RepeatRecipe.keyBinding == null || !RepeatRecipe.keyBinding.matchesKey(keyCode, scanCode)) {
            return;
        }

        LastRecipe lastRecipe = LastRecipe.instance;
        if (lastRecipe == null) {
            return;
        }

        Screen screen = this.client.currentScreen;
        if (screen == null || !lastRecipe.screenClazz.isInstance(screen)) {
            return;
        }

        RecipeBookWidgetAccessor recipeBook = getRecipeBook(screen);
        if (recipeBook != null) {
            recipeBook.getGhostSlots().reset();
        }

        this.client.interactionManager.clickRecipe(this.client.player.currentScreenHandler.syncId, lastRecipe.recipe, Screen.hasShiftDown());

        info.setReturnValue(false);
        info.cancel();
    }

    @Nullable
    private RecipeBookWidgetAccessor getRecipeBook(Screen screen) {
        RecipeBookWidgetAccessor recipeBook;

        if (screen instanceof AbstractFurnaceScreenAccessor) {
            recipeBook = (RecipeBookWidgetAccessor) ((AbstractFurnaceScreenAccessor) screen).getRecipeBook();
        } else if (screen instanceof CraftingScreenAccessor) {
            recipeBook = (RecipeBookWidgetAccessor) ((CraftingScreenAccessor) screen).getRecipeBook();
        } else if (screen instanceof InventoryScreenAccessor) {
            recipeBook = (RecipeBookWidgetAccessor) ((InventoryScreenAccessor) screen).getRecipeBook();
        } else {
            log.warn("Unknown recipe book containing screen: {}", screen.getClass().getSimpleName());
            recipeBook = null;
        }

        return recipeBook;
    }
}
