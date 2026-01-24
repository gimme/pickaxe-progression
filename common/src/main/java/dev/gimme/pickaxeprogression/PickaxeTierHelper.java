package dev.gimme.pickaxeprogression;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.Set;

public class PickaxeTierHelper {

    private static final Set<Block> STONE_BLOCKS = Set.of(
            Blocks.STONE,
            Blocks.DEEPSLATE,
            Blocks.COBBLESTONE,
            Blocks.COBBLED_DEEPSLATE,
            Blocks.BLACKSTONE,
            Blocks.ANDESITE,
            Blocks.CALCITE,
            Blocks.DIORITE,
            Blocks.GRANITE,
            Blocks.TUFF
    );

    private static final Set<Block> IRON_BLOCKS = Set.of(
            Blocks.IRON_ORE,
            Blocks.DEEPSLATE_IRON_ORE,
            Blocks.IRON_BLOCK
    );

    private static final Set<Block> DIAMOND_BLOCKS = Set.of(
            Blocks.DIAMOND_ORE,
            Blocks.DEEPSLATE_DIAMOND_ORE,
            Blocks.DIAMOND_BLOCK
    );

    /**
     * Checks if the given pickaxe item is blocked by this mod from mining the specified block.
     */
    public static boolean isBlockedByPickaxeTier(Item item, Block block) {
        if (!CommonConfig.INSTANCE.pickaxeRequirements()) return false;

        if (isWoodenTier(item)) return STONE_BLOCKS.contains(block);
        if (isStoneTier(item)) return IRON_BLOCKS.contains(block);
        if (isIronTier(item)) return DIAMOND_BLOCKS.contains(block);
        return false;
    }

    private static boolean isWoodenTier(Item item) {
        return item == Items.WOODEN_PICKAXE;
    }

    private static boolean isStoneTier(Item item) {
        return item == Items.STONE_PICKAXE || item == Items.COPPER_PICKAXE;
    }

    private static boolean isIronTier(Item item) {
        return item == Items.IRON_PICKAXE || item == Items.GOLDEN_PICKAXE;
    }
}
