package dev.gimme.pickaxeprogression;

import net.neoforged.neoforge.common.ModConfigSpec;

public class NeoForgeCommonConfig extends CommonConfig {

    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.BooleanValue PICKAXE_REQUIREMENTS = BUILDER
            .comment("""
                If true, stone requires stone pickaxe to mine, iron requires iron pickaxe and diamond requires diamond pickaxe.
                Hint: You can still acquire blocks through explosions.""")
            .define("pickaxeRequirements", true);

    private static final ModConfigSpec.IntValue MAX_IRON_GOLEM_INGOT_DROPS = BUILDER
            .comment("""
                    Maximum number of iron ingots dropped by Iron Golems. The minimum becomes 2 less than this value.
                     Vanilla: set to -1 (3-5 ingots)""")
            .defineInRange("maxIronGolemIngotDrops", 3, -1, 5);

    public static final ModConfigSpec SPEC = BUILDER.build();

    @Override
    public boolean pickaxeRequirements() {
        return PICKAXE_REQUIREMENTS.get();
    }

    @Override
    public int getMaxIronGolemIngotDrops() {
        return MAX_IRON_GOLEM_INGOT_DROPS.get();
    }
}
