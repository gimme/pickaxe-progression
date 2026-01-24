package dev.gimme.pickaxeprogression.mixin;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static dev.gimme.pickaxeprogression.PickaxeTierHelper.isBlockedByPickaxeTier;

/**
 * Mixin to add pickaxe tier requirements for mining certain blocks.
 */
@Mixin(Item.class)
public abstract class PickaxeTierMixin {

    /**
     * Adds pickaxe tier requirements for mining certain blocks.
     */
    @Inject(method = "isCorrectToolForDrops", at = @At("HEAD"), cancellable = true)
    private void onIsCorrectForDrops(ItemStack stack, BlockState state, CallbackInfoReturnable<Boolean> cir) {
        var instance = (Item) (Object) this;

        if (isBlockedByPickaxeTier(instance, state.getBlock())) {
            cir.setReturnValue(false);
        }
    }
}
