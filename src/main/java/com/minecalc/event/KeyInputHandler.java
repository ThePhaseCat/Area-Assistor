package com.minecalc.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {

    public static final String CATEGORY = "key.categories.minecalc";
    public static final String KEYBIND = "key.minecalc.test";

    public static KeyBinding testGuiKey;

    public static void registerKeyInputs()
    {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (testGuiKey.wasPressed())
            {
                client.player.sendChatMessage("pressed", Text.of("test"));
            }
        });
    }

    public static void register()
    {
        testGuiKey = KeyBindingHelper.registerKeyBinding(
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
