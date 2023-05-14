package com.minecalc.gui;

import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;

public class testGui extends LightweightGuiDescription {

    public testGui() {
        WGridPanel root = new WGridPanel();
        setRootPanel(root);
        root.setSize(300, 200);
    }
}
