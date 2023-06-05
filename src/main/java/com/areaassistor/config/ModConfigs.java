package com.areaassistor.config;

import com.mojang.datafixers.util.Pair;
import com.areaassistor.AreaAssistor;

public class ModConfigs {
    public static SimpleConfig CONFIG;
    private static ModConfigProvider configs;

    public static int Block1X;
    public static int Block1Y;
    public static int Block1Z;
    public static int Block2X;
    public static int Block2Y;
    public static int Block2Z;
    public static int Block3X;
    public static int Block3Y;
    public static int Block3Z;

    public static void registerConfigs() {
        configs = new ModConfigProvider();
        createConfigs();


        CONFIG = SimpleConfig.of(AreaAssistor.MOD_ID + "config").provider(configs).request();

        assignConfigs();
    }

    private static void createConfigs() {
        configs.addKeyValuePair(new Pair<>("Block1X", 0), "int");
        configs.addKeyValuePair(new Pair<>("Block1Y", 0), "int");
        configs.addKeyValuePair(new Pair<>("Block1Z", 0), "int");
        configs.addKeyValuePair(new Pair<>("Block2X", 0), "int");
        configs.addKeyValuePair(new Pair<>("Block2Y", 0), "int");
        configs.addKeyValuePair(new Pair<>("Block2Z", 0), "int");
        configs.addKeyValuePair(new Pair<>("Block3X", 0), "int");
        configs.addKeyValuePair(new Pair<>("Block3Y", 0), "int");
        configs.addKeyValuePair(new Pair<>("Block3Z", 0), "int");
    }

    private static void assignConfigs() {
        Block1X = CONFIG.getOrDefault("Block1X", 0);
        Block1Y = CONFIG.getOrDefault("Block1Y", 0);
        Block1Z = CONFIG.getOrDefault("Block1Z", 0);
        Block2X = CONFIG.getOrDefault("Block2X", 0);
        Block2Y = CONFIG.getOrDefault("Block2Y", 0);
        Block2Z = CONFIG.getOrDefault("Block2Z", 0);
        Block3X = CONFIG.getOrDefault("Block3X", 0);
        Block3Y = CONFIG.getOrDefault("Block3Y", 0);
        Block3Z = CONFIG.getOrDefault("Block3Z", 0);
        System.out.println("All " + configs.getConfigsList().size() + " have been set properly");
    }
}
