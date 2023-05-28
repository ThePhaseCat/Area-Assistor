package com.minecalc;

import com.minecalc.config.ModConfigs;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MineCalc implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("minecalc");

    public static final String MOD_ID = "minecalc";

    @Override
    public void onInitialize() {
        LOGGER.info("MineCalc loaded!");
        ModConfigs.registerConfigs();
    }
}