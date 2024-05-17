package com.areaassistor.newGUI;

import io.wispforest.owo.ui.base.BaseUIModelScreen;
import io.wispforest.owo.ui.component.ButtonComponent;
import io.wispforest.owo.ui.container.CollapsibleContainer;
import io.wispforest.owo.ui.container.FlowLayout;
import net.minecraft.util.Identifier;

public class AreaAssistorMainXML extends BaseUIModelScreen<FlowLayout> {

    public AreaAssistorMainXML() {
        super(FlowLayout.class, DataSource.asset(new Identifier("areaassistor", "area_ui_model")));
    }
    @Override
    protected void build(FlowLayout rootComponent) {
        rootComponent.childById(ButtonComponent.class, "the-button").onPress(button -> {
            System.out.println("click");
        });
        rootComponent.childById(ButtonComponent.class, "button_2").onPress(button -> {
            System.out.println("click2");
        });
    }
}
