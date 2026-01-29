package dev.gimme.pickaxeprogression;

import com.electronwill.nightconfig.core.Config;
import com.electronwill.nightconfig.core.UnmodifiableConfig;
import com.electronwill.nightconfig.toml.TomlFormat;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;

public class NeoForgeCommonConfig extends CommonConfig {

    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.ConfigValue<Config> INCORRECT_TOOLS = BUILDER
            .comment("""
                    Restricts tools from mining specific blocks. This makes the tools less efficient at breaking the blocks and makes them drop nothing.
                    This uses regex patterns. For example, `"stone_pickaxe|copper_pickaxe": ".*iron.*"` makes stone and copper pickaxes incorrect tools
                    for any block with "iron" in its name.
                    Note: Blocks can still be acquired through explosions.""")
            .define(
                    "incorrectTools",
                    () -> {
                        Config defaultConfig = TomlFormat.newConfig();
                        defaultConfig.set("wooden_pickaxe", "stone|deepslate|cobblestone|cobbled_deepslate|blackstone|andesite|calcite|diorite|granite|tuff");
                        defaultConfig.set("stone_pickaxe|copper_pickaxe", "*.iron_ore|*.iron_block");
                        defaultConfig.set("iron_pickaxe", "*.diamond_ore|diamond_block");
                        return defaultConfig;
                    },
                    NeoForgeCommonConfig::validateRegexMap
            );

    private static final ModConfigSpec.IntValue MAX_IRON_GOLEM_INGOT_DROPS = BUILDER
            .comment("""
                    Maximum number of iron ingots dropped by Iron Golems. The minimum becomes 2 less than this value.
                     Vanilla: set to -1 (3-5 ingots)""")
            .defineInRange("maxIronGolemIngotDrops", 3, -1, 5);

    /**
     * Validates that the given object is a map of regexes to regexes.
     */
    private static boolean validateRegexMap(Object o) {
        if (!(o instanceof Config cfg)) return false;

        for (Config.Entry e : cfg.entrySet()) {
            if (!isValidRegex(e.getKey())) return false;

            if (!(e.getValue() instanceof String s)) return false;
            if (!isValidRegex(s)) return false;
        }
        return true;
    }

    /**
     * Checks if the given string is a valid regex pattern.
     */
    private static boolean isValidRegex(@NotNull String regex) {
        try {
            Pattern.compile(regex);
        } catch (PatternSyntaxException ex) {
            return false;
        }
        return true;
    }

    public static final ModConfigSpec SPEC = BUILDER.build();

    @Override
    public Map<String, String> getIncorrectTools() {
        return INCORRECT_TOOLS.get().entrySet().stream().collect(Collectors.toMap(UnmodifiableConfig.Entry::getKey, UnmodifiableConfig.Entry::getValue));
    }

    @Override
    public int getMaxIronGolemIngotDrops() {
        return MAX_IRON_GOLEM_INGOT_DROPS.get();
    }
}
