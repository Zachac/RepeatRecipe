package net.fabricmc.example.mixin;

import net.fabricmc.example.LastRecipe;
import net.fabricmc.example.mixin.accessors.AbstractFurnaceScreenAccessor;
import net.fabricmc.example.mixin.accessors.CraftingScreenAccessor;
import net.fabricmc.example.mixin.accessors.RecipeBookWidgetAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

        // space
        if (keyCode != 32) {
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

        RecipeBookWidgetAccessor recipeBook;

        if (screen instanceof AbstractFurnaceScreenAccessor) {
            recipeBook = (RecipeBookWidgetAccessor) ((AbstractFurnaceScreenAccessor) screen).getRecipeBook();
        } else if (screen instanceof CraftingScreenAccessor) {
            recipeBook = (RecipeBookWidgetAccessor) ((CraftingScreenAccessor) screen).getRecipeBook();
        } else {
            log.warn("Unknown recipe book containing screen: {}", screen.getClass().getSimpleName());
            return;
        }

        recipeBook.getGhostSlots().reset();
        this.client.interactionManager.clickRecipe(this.client.player.currentScreenHandler.syncId, lastRecipe.recipe, Screen.hasShiftDown());

        info.setReturnValue(false);
        info.cancel();
    }
}
