package com.areaassistor;

import com.areaassistor.gui.AreaAssistorGui;
import io.github.cottonmc.cotton.gui.client.CottonClientScreen;
import io.wispforest.owo.client.screens.OwoScreenHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AreaAssistorClient implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("areaassistor");

    public static final String MOD_ID = "areaassistor";

    private static KeyBinding keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "areaassistor.keybind.open", // The translation key of the keybinding's name
            InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
            GLFW.GLFW_KEY_0, // The keycode of the key
            "areassistor.keybind.category" // The translation key of the keybinding's category.
    ));


    @Override
    public void onInitializeClient() {
        LOGGER.info("AreaAssistor successfully loaded!");

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (keyBinding.wasPressed())
            {
                System.out.println("hi");
                MinecraftClient.getInstance().setScreen(SpruceScreenFactory.createAreaAssistorTabbedScreen(null));
                //MinecraftClient.getInstance().setScreen(new CottonClientScreen(new AreaAssistorGui()));
            }
        });
    }
}
