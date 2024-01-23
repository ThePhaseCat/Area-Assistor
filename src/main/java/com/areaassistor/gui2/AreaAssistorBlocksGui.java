package com.areaassistor.gui2;

import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.*;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.block.Block;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AreaAssistorBlocksGui extends LightweightGuiDescription {

    public AreaAssistorBlocksGui(ArrayList<Block> blocks)
    {
        WGridPanel root = new WGridPanel();
        root.setSize(400, 200);

        WPlainPanel panel = new WPlainPanel();
        panel.setSize(400, 200);

        String allBlocks = compileAllBlocks(blocks);
        WLabel label = new WLabel(Text.literal(allBlocks));
        //check if string allBlocks is empty
        if(allBlocks.length() == 0 || allBlocks == null)
        {
            allBlocks = "No blocks in area selected!";
            label = new WLabel(Text.literal(allBlocks));
        }
        if(allBlocks.length() < 75)
        {
            root.add(label, 1, 1);
        }
        else
        {
            for(int i = 0; i<allBlocks.length(); i+=70)
            {
                if(i+70 < allBlocks.length())
                {
                    label = new WLabel(Text.literal(allBlocks.substring(i, i+70)));
                }
                else
                {
                    label = new WLabel(Text.literal(allBlocks.substring(i)));
                }
                root.add(label, 1, i/70);
            }
        }

        WScrollPanel scrollPanel = new WScrollPanel(root);
        scrollPanel.setScrollingHorizontally(TriState.FALSE);
        scrollPanel.setScrollingVertically(TriState.TRUE);
        scrollPanel.setSize(400, 200);
        //setRootPanel(root);
        setRootPanel(scrollPanel);
        //root.add(scrollPanel, 0, 0);
    }

    public String getBlockName(Block block)
    {
        String blockString = block.getTranslationKey().toString();
        blockString = blockString.substring(16);
        return blockString;
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
}
