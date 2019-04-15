package com.cjburkey.cjsreactors;

import net.minecraftforge.common.config.Config;

/**
 * Created by CJ Burkey on 2019/04/14
 */
@SuppressWarnings("unused")
@Config(modid = ModInfo.MODID)
@Config.RequiresWorldRestart
public final class ModConfig {

    @Config.Comment("The maximum amount of energy that a reactor may hold in its buffer. If too small, energy may not be permitted to leave the reactor as quickly as it is generated. Excess energy is discarded.")
    @Config.RangeInt(min = 128, max = 16777216)
    @Config.LangKey("config.max_reactor_power_buffer")
    public static int maxReactorPowerBuffer = 512;

    @Config.Comment("The minimum width, height, and length of a reactor")
    @Config.RangeInt(min = 4, max = 8)
    @Config.LangKey("config.min_reactor_size")
    @Config.SlidingOption
    public static int minReactorSize = 4;

    @Config.Comment("The maximum width, height, and length of a reactor")
    @Config.RangeInt(min = 8, max = 64)
    @Config.LangKey("config.max_reactor_size")
    @Config.SlidingOption
    public static int maxReactorSize = 32;

}
