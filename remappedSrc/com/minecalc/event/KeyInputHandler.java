package com.minecalc.event;

import com.minecalc.gui.MineCalcScreen;
import com.minecalc.gui.MineCalcGui;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {

    public static final String CATEGORY = "key.category.minecalc.minecalc_category";
    public static final String KEYBIND = "key.minecalc.open_minecalc";

    public static KeyBinding open_minecalc_key;

    public static void registerKeyInputs()
    {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (open_minecalc_key.wasPressed())
            {
                MinecraftClient.getInstance().setScreen(new MineCalcScreen(new MineCalcGui()));
            }
        });
    }

    public static void register()
    {
        open_minecalc_key = KeyBindingHelper.registerKeyBinding(
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
