package com.areaassistor;

import com.areaassistor.event.KeyInputHandler;
import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AreaAssistorClient implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("areaassistor");

    public static final String MOD_ID = "areaassistor";

    @Override
    public void onInitializeClient() {
        LOGGER.info("AreaAssistor loaded!");
        KeyInputHandler.register();
    }
}
