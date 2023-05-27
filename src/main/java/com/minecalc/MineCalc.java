package com.minecalc;

import com.minecalc.config.ModConfigs;
import com.minecalc.registry.ModItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.*;
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