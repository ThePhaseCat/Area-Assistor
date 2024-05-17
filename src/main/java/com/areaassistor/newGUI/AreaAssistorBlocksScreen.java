package com.areaassistor.newGUI;

import io.wispforest.owo.ui.base.BaseOwoScreen;
import io.wispforest.owo.ui.component.Components;
import io.wispforest.owo.ui.container.Containers;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.core.Component;
import io.wispforest.owo.ui.core.*;
import net.minecraft.block.Block;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AreaAssistorBlocksScreen extends BaseOwoScreen<FlowLayout> {

    public static ArrayList<Block> blocks;

    public AreaAssistorBlocksScreen(ArrayList<Block> blocks) {
        super();
        AreaAssistorBlocksScreen.blocks = blocks;
    }

    @Override
    protected @NotNull OwoUIAdapter<FlowLayout> createAdapter() {
        return OwoUIAdapter.create(this, Containers::verticalFlow);
    }

    @Override
    protected void build(FlowLayout rootComponent)
    {
        String blocksInArea = compileAllBlocks(blocks);

        Component componet = whatIsThis(Components.label(Text.literal(blocksInArea)));

        rootComponent
                .surface(Surface.VANILLA_TRANSLUCENT)
                .horizontalAlignment(HorizontalAlignment.CENTER)
                .verticalAlignment(VerticalAlignment.CENTER);

        rootComponent.child(
                Containers.verticalScroll(Sizing.fill(75), Sizing.fill(75), componet)
                        .child(Components.label(Text.literal(blocksInArea)).maxWidth(300))
                        .padding(Insets.of(10))
                        .surface(Surface.DARK_PANEL)
                        .verticalAlignment(VerticalAlignment.CENTER)
                        .horizontalAlignment(HorizontalAlignment.CENTER)
        );
    }

    public String compileAllBlocks(ArrayList<Block> blocks)
    {
        if(blocks.size() == 0)
        {
            return "No blocks in area";
        }
        String allBlocks = "Blocks in Area: ";

        // build hash table with counting for duplicates
        HashMap<Block, Integer> blockCount = new HashMap<Block, Integer>();
        for (Block block : blocks) {
            Integer count = blockCount.get(block);
            if (count == null) {
                blockCount.put(block, 1);
            } else {
                blockCount.put(block, count + 1);
            }
        }

        // build output string
        for (Map.Entry<Block, Integer> entry : blockCount.entrySet()) {
            String blockName = getBlockName(entry.getKey());
            allBlocks = allBlocks + blockName + " x" + entry.getValue() + ", ";
        }

        allBlocks = allBlocks.substring(0, allBlocks.length() - 2);


        return allBlocks;
    }

    public String getBlockName(Block block)
    {
        String blockString = block.getTranslationKey().toString();
        blockString = blockString.substring(16);
        return blockString;
    }

    public static <C extends Component> C whatIsThis(C component)
    {
        return component;
    }
}
