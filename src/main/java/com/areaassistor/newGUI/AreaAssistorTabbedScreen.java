package com.areaassistor.newGUI;

import dev.lambdaurora.spruceui.screen.SpruceScreen;
import dev.lambdaurora.spruceui.widget.container.tabbed.SpruceTabbedWidget;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

public class AreaAssistorTabbedScreen extends SpruceScreen
{

    private final Screen parent;

    private SpruceTabbedWidget tabbedWidget;
    protected AreaAssistorTabbedScreen(@Nullable Screen parent) {
        super(Text.literal("Area Assistor Main Screen"));
        this.parent = parent;
    }
}
