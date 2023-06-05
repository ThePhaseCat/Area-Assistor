package com.areaassistor;

import com.areaassistor.event.KeyInputHandler;
import net.fabricmc.api.ClientModInitializer;

public class AreaAssistorClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        KeyInputHandler.register();
    }
}
