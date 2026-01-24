package dev.gimme.pickaxeprogression.mixin;

import dev.gimme.pickaxeprogression.CommonConfig;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.ReloadableServerRegistries;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.golem.IronGolem;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Mixin to modify iron golem drops.
 */
@Mixin(LivingEntity.class)
public class IronGolemDropsMixin {

    /**
     * Redirects iron golem loot table to lower ingot drops.
     */
    @Redirect(method = "dropFromLootTable(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/damagesource/DamageSource;ZLnet/minecraft/resources/ResourceKey;Ljava/util/function/Consumer;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/ReloadableServerRegistries$Holder;getLootTable(Lnet/minecraft/resources/ResourceKey;)Lnet/minecraft/world/level/storage/loot/LootTable;"))
    private LootTable onDropFromLootTable(ReloadableServerRegistries.Holder instance, ResourceKey<LootTable> lootTableKey) {
        if ((LivingEntity) (Object) this instanceof IronGolem && CommonConfig.INSTANCE.getMaxIronGolemIngotDrops() >= 0) {
            int maxIngotDrops = CommonConfig.INSTANCE.getMaxIronGolemIngotDrops();
            int minIngotDrops = Math.max(0, maxIngotDrops - 2);

            return LootTable.lootTable()
                    .withPool(
                            LootPool.lootPool()
                                    .setRolls(ConstantValue.exactly(1.0F))
                                    .add(LootItem.lootTableItem(Blocks.POPPY).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F))))
                    )
                    .withPool(
                            LootPool.lootPool()
                                    .setRolls(ConstantValue.exactly(1.0F))
                                    .add(LootItem.lootTableItem(Items.IRON_INGOT).apply(SetItemCountFunction.setCount(UniformGenerator.between(minIngotDrops, maxIngotDrops))))
                    )
                    .build();
        }

        return instance.getLootTable(lootTableKey);
    }
}
