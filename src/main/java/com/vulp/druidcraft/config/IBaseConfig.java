package com.vulp.druidcraft.config;

import net.minecraftforge.common.ForgeConfigSpec;

public interface IBaseConfig {
    void apply(ForgeConfigSpec.Builder builder);

    void reset ();
}
