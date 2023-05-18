package com.minecalc.gui;

import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.*;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.function.BiConsumer;

public class testGui extends LightweightGuiDescription {

    public static BlockPos block1;
    public static BlockPos block2;
    public static BlockPos block3;

    public static int areaValue;

    public static ArrayList<String> blockNames = new ArrayList<String>();

    public static String stringBlockNames = sendBlockNames();

    public testGui() {
        //setValuesPanel
        WGridPanel setValuesPanel = new WGridPanel();
        setRootPanel(setValuesPanel);
        setValuesPanel.setSize(300, 200);

        //setValuesPanel Labels
        WLabel block1lab = new WLabel(Text.translatable("Block 1: " + BlockCoords(block1)));
        WLabel block2lab = new WLabel(Text.translatable("Block 2: " + BlockCoords(block2)));
        WLabel block3lab = new WLabel(Text.translatable("Block 3: " + BlockCoords(block3)));

        //setValuesPanel buttons
        WButton block1coords = new WButton(Text.translatable("Change Block 1 Coords"));
        WButton block2coords = new WButton(Text.translatable("Change Block 2 Coords"));
        WButton block3coords = new WButton(Text.translatable("Change Block 3 Coords"));

        //setValuesPanel button logic
        block1coords.setOnClick(() -> {
            System.out.println("block 1 button pressed");
            block1 = getBlockPos();
            block1lab.setText(Text.translatable("Block 1: " + BlockCoords(block1)));
            areaValue = getAreaValue();
            MinecraftClient.getInstance().setScreen(new testScreen(new testGui()));
        });
        block2coords.setOnClick(() -> {
            System.out.println("block 2 button pressed");
            block2 = getBlockPos();
            block2lab.setText(Text.translatable("Block 2: " + BlockCoords(block2)));
            areaValue = getAreaValue();
            MinecraftClient.getInstance().setScreen(new testScreen(new testGui()));
        });
        block3coords.setOnClick(() -> {
            System.out.println("block 3 button pressed");
            block3 = getBlockPos();
            block3lab.setText(Text.translatable("Block 3: " + BlockCoords(block3)));
            areaValue = getAreaValue();
            MinecraftClient.getInstance().setScreen(new testScreen(new testGui()));
        });


        setValuesPanel.add(block1lab, 1, 1, 1, 1);
        setValuesPanel.add(block2lab, 1, 3, 1, 1);
        setValuesPanel.add(block3lab, 1, 5, 1, 1);

        setValuesPanel.add(block1coords, 7, 1, 10, 1);
        setValuesPanel.add(block2coords, 7, 3, 10, 1);
        setValuesPanel.add(block3coords, 7, 5, 10, 1);

        //area panel
        WGridPanel areaPanel = new WGridPanel();
        //setRootPanel(areaPanel);
        areaPanel.setSize(300, 200);

        //area panel labels
        WLabel areaLabel = new WLabel(Text.translatable("Area: " + areaValue));
        areaPanel.add(areaLabel, 1, 1, 1, 1);

        //block names panel
        WGridPanel blockNamesPanel = new WGridPanel();
        blockNamesPanel.setSize(300, 200);

        //block names panel labels
        WLabel blockNamesLabel = new WLabel(Text.translatable("Blocks in Area: " + stringBlockNames));
        blockNamesPanel.add(blockNamesLabel, 1, 1, 1, 1);


        //tabs
        WTabPanel tabs = new WTabPanel();
        tabs.add(setValuesPanel, tab -> tab.title(Text.literal("Set Values")));
        tabs.add(areaPanel, tab -> tab.title(Text.literal("Area Value")));
        tabs.add(blockNamesPanel, tab -> tab.title(Text.literal("Blocks in Area")));

        setRootPanel(tabs);
        tabs.setSelectedIndex(0);


    }

    public BlockPos getBlockPos()
    {
        //get the block that the player is looking at
        MinecraftClient client = MinecraftClient.getInstance();
        HitResult hit = client.crosshairTarget;

        BlockHitResult blockHit = (BlockHitResult) hit;
        BlockPos blockPos = blockHit.getBlockPos();

        if(hit.getType() == HitResult.Type.BLOCK)
        {
            return blockPos;
        }
        else
        {
            return null;
        }
    }

    public int getAreaValue()
    {
        //check if blocks are null
        if(block1 == null || block2 == null || block3 == null)
        {
            return 0;
        }

        //length
        int block2x = block2.getX();
        int block1x = block1.getX();

        //width
        int block2z = block2.getZ();
        int block1z = block1.getZ();

        //height
        int block3y = block3.getY();
        int block2y = block2.getY();

        if (block2x < 0){
            block2x = block2x * -1;
        }
        if (block1x < 0){
            block1x = block1x * -1;
        }
        if (block2z < 0){
            block2z = block2z * -1;
        }
        if (block1z < 0){
            block1z = block1z * -1;
        }
        if (block3y < 0){
            block3y = block3y * -1;
        }
        if (block2y < 0){
            block2y = block2y * -1;
        }

        int length = block2x - block1x;
        int width = block2z - block1z;
        int height = block3y - block2y;

        if(length < 0){
            length = length * -1;
        }
        if(width < 0){
            width = width * -1;
        }
        if(height < 0){
            height = height * -1;
        }

        length = length + 1;
        width = width + 1;
        height = height + 1;

        int area = length * width * height;
        if (area < 0)
        {
            area = area * -1;
        }
        clearBlockNames();
        addBlockNames();
        System.out.println(sendBlockNames());
        stringBlockNames = sendBlockNames();
        System.out.println(stringBlockNames);
        return area;
    }

    public void addBlockNames()
    {
        System.out.println("addBlockNames called");
        if (block1 == null || block2 == null || block3 == null)
        {
            return;
        }
        int block1x = block1.getX();
        int block1y = block1.getY();
        int block1z = block1.getZ();

        int block2x = block2.getX();
        int block2y = block2.getY();
        int block2z = block2.getZ();

        int block3x = block3.getX();
        int block3y = block3.getY();
        int block3z = block3.getZ();

        for(int y = block1y; y<=block3y; y++)
        {
            for(int x = block1x; x<=block2x; x++)
            {
                for(int z = block1z; z<=block2z; z++)
                {
                    System.out.println(x + " " + y + " " + z);
                }
            }
        }
        for(int i = 0; i < blockNames.size(); i++)
        {
            System.out.println(blockNames.get(i));
        }
        System.out.println("addBlockNames finished");
    }

    public void clearBlockNames()
    {
        blockNames.clear();
        System.out.println("blockNames cleared");
    }

    public static String sendBlockNames()
    {
        System.out.println("sendBlockNames called");
        if(blockNames.size() == 0)
        {
            return "null";
        }
        String blockNamesString = "";
        for (int i = 0; i < blockNames.size(); i++)
        {
            blockNamesString = blockNamesString + blockNames.get(i) + ", ";
        }
        //print everything in the arraylist
        return blockNamesString;
    }

    public String BlockCoords(BlockPos blockGiven)
    {
        if (blockGiven == null)
        {
            return "null";
        }
        int blockX = blockGiven.getX();
        int blockY = blockGiven.getY();
        int blockZ = blockGiven.getZ();

        String blockCoords = blockX + ", " + blockY + ", " + blockZ;
        return blockCoords;
    }
}
