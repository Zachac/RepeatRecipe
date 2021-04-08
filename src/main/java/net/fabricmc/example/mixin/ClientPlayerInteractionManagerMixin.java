package net.fabricmc.example.mixin;

import net.fabricmc.example.LastRecipe;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.recipe.Recipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin {

    private MinecraftClient client = MinecraftClient.getInstance();
    public Recipe<?> lastClickedRecipe;

    @Inject(at = @At("TAIL"), method = "clickRecipe(ILnet/minecraft/recipe/Recipe;Z)V")
    public void clickRecipe(int syncId, Recipe<?> recipe, boolean craftAll, CallbackInfo info) {
        LastRecipe.instance = new LastRecipe(recipe, this.client.currentScreen.getClass());
    }
}
