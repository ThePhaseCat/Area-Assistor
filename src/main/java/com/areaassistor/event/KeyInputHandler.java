package com.areaassistor.event;

import com.areaassistor.gui.AreaAssistorGui;
import com.areaassistor.gui.AreaAssistorScreen;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {

    public static KeyBinding open_minecalc_key;

    public static void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (open_minecalc_key.wasPressed()) {
                MinecraftClient.getInstance().setScreen(new AreaAssistorScreen(new AreaAssistorGui()));
            }
        });
    }

    public static void register() {
        open_minecalc_key = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "areaassistor.keybinds.open",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_0,
                "areaassistor.keybinds.category"
        ));

        registerKeyInputs();
    }

}
