package net.fabricmc.repeatrecipe.mixin;

import net.fabricmc.repeatrecipe.model.LastRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.CraftingResultSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CraftingResultSlot.class)
public class CraftingResultSlotMixin {

    @Inject(at = @At("HEAD"), method="onCrafted(Lnet/minecraft/item/ItemStack;)V")
    public void onCrafted(ItemStack itemStack, CallbackInfo callbackInfo) {
        LastRecipe.instance = LastRecipe.lastCheckedRecipe;
    }
}
