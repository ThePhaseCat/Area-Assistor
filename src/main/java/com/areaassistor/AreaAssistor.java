package com.areaassistor;

import com.areaassistor.config.ModConfigs;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AreaAssistor implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("areaassistor");

    public static final String MOD_ID = "areaassistor";

    @Override
    public void onInitialize() {
        LOGGER.info("AreaAssistor loaded!");
        ModConfigs.registerConfigs();
    }
}