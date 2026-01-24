package dev.gimme.pickaxeprogression;

import net.neoforged.neoforge.common.ModConfigSpec;

public class NeoForgeCommonConfig extends CommonConfig {

    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.BooleanValue PICKAXE_REQUIREMENTS = BUILDER
            .comment("""
                If true, stone requires stone pickaxe to mine, iron requires iron pickaxe and diamond requires diamond pickaxe.
                Hint: You can still acquire blocks through explosions.""")
            .define("pickaxeRequirements", true);

    public static final ModConfigSpec SPEC = BUILDER.build();

    @Override
    public boolean pickaxeRequirements() {
        return PICKAXE_REQUIREMENTS.get();
    }
}
