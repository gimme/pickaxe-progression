package dev.gimme.pickaxeprogression;

public abstract class CommonConfig {

    public static CommonConfig INSTANCE;

    public abstract boolean pickaxeRequirements();
    public abstract int getMaxIronGolemIngotDrops();
}
