package com.vulp.druidcraft.config;

import net.minecraftforge.common.ForgeConfigSpec;

public abstract class AbstractConfig implements IBaseConfig {
    public AbstractConfig() {
        Configuration.CONFIGS.add(this);
    }

    public abstract void apply(ForgeConfigSpec.Builder builder);

    public abstract void reset();
}
