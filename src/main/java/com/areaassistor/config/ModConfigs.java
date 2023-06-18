package com.areaassistor.config;

import com.areaassistor.AreaAssistor;
import com.mojang.datafixers.util.Pair;
import net.minecraft.util.math.BlockPos;

public class ModConfigs {
    public static SimpleConfig CONFIG;
    public static BlockPos.Mutable[] blocks = new BlockPos.Mutable[]{
            // there's gotta be a better way!
            BlockPos.ORIGIN.mutableCopy(),
            BlockPos.ORIGIN.mutableCopy(),
            BlockPos.ORIGIN.mutableCopy()
    };
    private static ModConfigProvider configs;

    public static void registerConfigs() {
        configs = new ModConfigProvider();
        for (int i = 1; i <= 3; i++) {
            for (char axis : "XYZ".toCharArray()) {
                configs.addKeyValuePair(new Pair<>("Block" + i + axis, 0), "int");
            }
        }

        CONFIG = SimpleConfig.of(AreaAssistor.MOD_ID + "config").provider(configs).request();

        for (int i = 1; i <= 3; i++) {
            // this could be deduplicated but it's... worse lol
            blocks[i - 1].setX(CONFIG.getOrDefault("Block" + i + "X", 0));
            blocks[i - 1].setY(CONFIG.getOrDefault("Block" + i + "Y", 0));
            blocks[i - 1].setZ(CONFIG.getOrDefault("Block" + i + "Z", 0));
        }
        AreaAssistor.LOGGER.info("Config read successfuly");
    }
}
