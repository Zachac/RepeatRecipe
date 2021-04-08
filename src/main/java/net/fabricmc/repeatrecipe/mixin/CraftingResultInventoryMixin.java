package net.fabricmc.repeatrecipe.mixin;

import net.fabricmc.repeatrecipe.model.LastRecipe;
import net.minecraft.client.MinecraftClient;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CraftingResultInventory.class)
public class CraftingResultInventoryMixin {

    private MinecraftClient client = MinecraftClient.getInstance();

    @Inject(at = @At("TAIL"), method = "setLastRecipe(Lnet/minecraft/recipe/Recipe;)V")
    public void matches(Recipe<? super CraftingInventory> recipe, CallbackInfo callbackInfoReturnable) {
        if (recipe != null) {
            LastRecipe.lastCheckedRecipe = new LastRecipe(recipe, client.currentScreen.getClass());
        }
    }
}
