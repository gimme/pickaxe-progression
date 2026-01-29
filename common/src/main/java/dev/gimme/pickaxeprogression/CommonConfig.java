package dev.gimme.pickaxeprogression;

import java.util.Map;

public abstract class CommonConfig {

    public static CommonConfig INSTANCE;

    public abstract Map<String, String> getIncorrectTools();

    public abstract int getMaxIronGolemIngotDrops();
}
