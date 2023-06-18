package com.areaassistor;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.math.BlockPos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AreaAssistor implements ModInitializer {
    public static BlockPos.Mutable[] blocks = new BlockPos.Mutable[]{
            // there's gotta be a better way!
            BlockPos.ORIGIN.mutableCopy(),
            BlockPos.ORIGIN.mutableCopy(),
            BlockPos.ORIGIN.mutableCopy()
    };

    public static final Logger LOGGER = LoggerFactory.getLogger("areaassistor");

    public static final String MOD_ID = "areaassistor";

    @Override
    public void onInitialize() {
        LOGGER.info("AreaAssistor loaded!");
    }
}