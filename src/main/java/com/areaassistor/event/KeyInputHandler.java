package com.areaassistor.event;

import com.areaassistor.gui.AreaAssistorGui;
import io.github.cottonmc.cotton.gui.client.CottonClientScreen;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {

    public static final String CATEGORY = "areassistor.keybind.category";
    public static final String KEYBIND = "areaassistor.keybind.open";

    public static KeyBinding open_areaassistor_key;

    public static void registerKeyInputs()
    {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (open_areaassistor_key.wasPressed())
            {
                MinecraftClient.getInstance().setScreen(new CottonClientScreen(new AreaAssistorGui()));
            }
        });
    }

    public static void register()
    {
        open_areaassistor_key = KeyBindingHelper.registerKeyBinding(
                new KeyBinding(
                        KEYBIND,
                        InputUtil.Type.KEYSYM,
                        GLFW.GLFW_KEY_0,
                        CATEGORY
                )
        );

        registerKeyInputs();
    }

}
