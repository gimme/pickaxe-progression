package dev.gimme.pickaxeprogression;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public class PickaxeTierHelper {

    /**
     * Checks if the given tool item is blocked by this mod from mining the specified block.
     */
    public static boolean isToolBlockedFromMining(Item tool, Block block) {
        var blocksByIncorrectTools = CommonConfig.INSTANCE.getIncorrectTools();
        return blocksByIncorrectTools.entrySet().stream().anyMatch(entry -> {
            var incorrectToolRegex = entry.getKey();
            var blockRegex = entry.getValue();
            return matchesRegex(tool, incorrectToolRegex) && matchesRegex(block.asItem(), blockRegex);
        });
    }

    /**
     * Checks if the given item matches the specified regex.
     */
    private static boolean matchesRegex(@NotNull Item item, @NotNull String regex) {
        String itemId = item.toString();

        if (!regex.contains(":") && itemId.contains(":")) {
            return itemId.split(":", 2)[1].matches(regex);
        } else {
            return itemId.matches(regex);
        }
    }
}
