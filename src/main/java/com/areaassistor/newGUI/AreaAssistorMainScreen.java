package com.areaassistor.newGUI;

import com.areaassistor.AreaAssistorClient;
import io.wispforest.owo.ui.base.BaseOwoScreen;
import io.wispforest.owo.ui.component.Components;
import io.wispforest.owo.ui.container.Containers;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.core.*;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Flow;

public class AreaAssistorMainScreen extends BaseOwoScreen<FlowLayout> {

    public static BlockPos block1 = BlockPos.ORIGIN;
    public static BlockPos block2 = BlockPos.ORIGIN;
    public static BlockPos block3 = BlockPos.ORIGIN;

    public static int areaValue;

    public static Item toolInfo = getHeldItem();

    public static int eLevel = 0;

    public static BlockPos playerPos = getPlayerPosition();

    @Override
    protected @NotNull OwoUIAdapter<FlowLayout> createAdapter() {
        return OwoUIAdapter.create(this, Containers::verticalFlow);
    }

    @Override
    protected void build(FlowLayout rootComponent) {
        String block1Coords = "Block 1: " + blockCoords(block1);
        String block2Coords = "Block 2: " + blockCoords(block2);
        String block3Coords = "Block 3: " + blockCoords(block3);

        if(getAreaValue() != 0)
        {
            areaValue = getAreaValue();
        }

        String areaLabel = "";
        if(areaValue == 0)
        {
            areaLabel = "Area: No Area Selected!";
        }
        else
        {
            if(areaValue == 1)
            {
                areaLabel = "Area: " + areaValue + " Block";
            }
            else
            {
                areaLabel = "Area: " + areaValue + " Blocks";
            }
        }



                String isPlayerInArea = "";
        //we can't check that if it's 0 or 1, so show a different message...
        if(areaValue == 0 && areaValue == 1)
        {
            isPlayerInArea = "No Area Selected/defined!";
        }
        else {
            if (inArea()) {
                isPlayerInArea = "Inside Area Selected";
            } else {
                isPlayerInArea = "Outside Area Selected";
            }
        }

        //String blocksInArea = compileAllBlocks(getBlockList());

        rootComponent
                .surface(Surface.VANILLA_TRANSLUCENT)
                .horizontalAlignment(HorizontalAlignment.CENTER)
                .verticalAlignment(VerticalAlignment.CENTER);

        rootComponent.child(

                Containers.verticalFlow(Sizing.content() /**/, Sizing.content())
                        .child(Components.button(Text.literal("A Button"), button -> { System.out.println("click"); }))
                        .padding(Insets.of(10)) //
                        .surface(Surface.DARK_PANEL)
                        .verticalAlignment(VerticalAlignment.CENTER)
                        .horizontalAlignment(HorizontalAlignment.CENTER)
        );

        rootComponent.child(
                Containers.collapsible(Sizing.content(), Sizing.content(), Text.literal("Set Block Positions For Area Calculations"), true)
                        .child(Components.button(Text.literal("Set Block 1 Position"), button -> { setBlockPos(1); }))
                        .child(Components.button(Text.literal("Set Block 2 Position"), button -> { setBlockPos(2); }))
                        .child(Components.button(Text.literal("Set Block 3 Position"), button -> { setBlockPos(3); }))
                        .child(Components.button(Text.literal("Reset Area"), button -> { resetAreaInformation(); }))
                        .child(Components.label(Text.literal(block1Coords)))
                        .child(Components.label(Text.literal(block2Coords)))
                        .child(Components.label(Text.literal(block3Coords)))
                        .padding(Insets.of(10)) //
                        .surface(Surface.DARK_PANEL)
                        .verticalAlignment(VerticalAlignment.CENTER)
                        .horizontalAlignment(HorizontalAlignment.CENTER)
        );

        rootComponent.child(
                Containers.collapsible(Sizing.content(), Sizing.content(), Text.literal("Area Info"), false)
                        .child(Components.label(Text.literal(areaLabel)))
                        .child(Components.label(Text.literal(isPlayerInArea)))
                        .padding(Insets.of(10)) //
                        .surface(Surface.DARK_PANEL)
                        .verticalAlignment(VerticalAlignment.CENTER)
                        .horizontalAlignment(HorizontalAlignment.CENTER)
        );

        //blocks in area would probably be easier if it's in its own screen, will
        //do that later
        rootComponent.child(
                Containers.verticalFlow(Sizing.content(), Sizing.content())
                        .child(Components.button(Text.literal("Blocks in Area Screen"), button -> { MinecraftClient.getInstance().setScreen(new AreaAssistorBlocksScreen(getBlockList())); }))
                        .padding(Insets.of(10)) //
                        .surface(Surface.DARK_PANEL)
                        .verticalAlignment(VerticalAlignment.CENTER)
                        .horizontalAlignment(HorizontalAlignment.CENTER)
        );

    }

    public void resetAreaInformation()
    {
        block1 = null;
        block2 = null;
        block3 = null;
        areaValue = 0;
    }

    public void setBlockPos(int blockNum)
    {
        BlockPos blockPos = getBlockPos();
        if(blockPos != null)
        {
            //AreaAssistorClient.LOGGER.info("Block " + blockNum + " Pos: " + blockPos);
            switch(blockNum)
            {
                case 1:
                    block1 = blockPos;
                    MinecraftClient.getInstance().setScreen(new AreaAssistorMainScreen());
                    break;
                case 2:
                    block2 = blockPos;
                    MinecraftClient.getInstance().setScreen(new AreaAssistorMainScreen());
                    break;
                case 3:
                    block3 = blockPos;
                    MinecraftClient.getInstance().setScreen(new AreaAssistorMainScreen());
                    break;
            }
        }
    }

    //returns the block that the player is looking at
    public BlockPos getBlockPos()
    {
        MinecraftClient client = MinecraftClient.getInstance();
        HitResult hit = client.crosshairTarget;

        BlockHitResult blockHit = (BlockHitResult) hit;
        BlockPos blockPos = blockHit.getBlockPos();

        if(hit.getType() == HitResult.Type.BLOCK)
        {
            //AreaAssistorClient.LOGGER.info("Block Pos: " + blockPos);
            return blockPos;
        }
        else
        {
            return null;
        }
    }

    public String blockCoords(BlockPos blockGiven)
    {
        if (blockGiven == null)
        {
            return "No Block Selected!";
        }
        else
        {
            String finalResult = blockGiven.getX() + ", " + blockGiven.getY() + ", " + blockGiven.getZ();
            return finalResult;
        }
    }

    public int getAreaValue()
    {
        //check if blocks are null
        if(block1 == null || block2 == null || block3 == null)
        {
            return 0;
        }

        int b1x = block1.getX();
        int b1y = block1.getY();
        int b1z = block1.getZ();

        int b2x = block2.getX();
        int b2y = block2.getY();
        int b2z = block2.getZ();

        int b3x = block3.getX();
        int b3y = block3.getY();
        int b3z = block3.getZ();

        int startX = smallestValue(b1x, b2x, b3x);
        int finalX = biggestValue(b1x, b2x, b3x);

        int startY = smallestValue(b1y, b2y, b3y);
        int finalY = biggestValue(b1y, b2y, b3y);

        int startZ = smallestValue(b1z, b2z, b3z);
        int finalZ = biggestValue(b1z, b2z, b3z);

        int length = finalX - startX;
        int width = finalZ - startZ;
        int height = finalY - startY;

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

        //add that in once that method is ported
        toolInfo = getHeldItem();
        return area;
    }

    public static Item getHeldItem()
    {
        MinecraftClient client = MinecraftClient.getInstance();
        PlayerEntity player = client.player;
        Item heldItem = player.getMainHandStack().getItem();
        //check if item is a pickaxe or shovel
        if (heldItem == Items.WOODEN_PICKAXE || heldItem == Items.STONE_PICKAXE || heldItem == Items.IRON_PICKAXE || heldItem == Items.GOLDEN_PICKAXE || heldItem == Items.DIAMOND_PICKAXE || heldItem == Items.NETHERITE_PICKAXE || heldItem == Items.WOODEN_SHOVEL || heldItem == Items.STONE_SHOVEL || heldItem == Items.IRON_SHOVEL || heldItem == Items.GOLDEN_SHOVEL || heldItem == Items.DIAMOND_SHOVEL || heldItem == Items.NETHERITE_SHOVEL) {
            return heldItem;
        } else {
            return null;
        }
    }

    public static BlockPos getPlayerPosition()
    {
        MinecraftClient client = MinecraftClient.getInstance();
        PlayerEntity player = client.player;
        BlockPos playerPos = player.getBlockPos();
        return playerPos;
    }

    public int biggestValue(int b1, int b2, int b3)
    {
        if(b1 >= b2 && b1 >= b3)
        {
            return b1;
        }
        else if(b2 >= b1 && b2 >= b3)
        {
            return b2;
        }
        else{
            return b3;
        }
    }

    public int smallestValue(int b1, int b2, int b3)
    {
        if(b1 <= b2 && b1 <= b3)
        {
            return b1;
        }
        else if(b2 <= b1 && b2 <= b3)
        {
            return b2;
        }
        else{
            return b3;
        }
    }

    public boolean inArea()
    {
        int playerX = playerPos.getX();
        int playerY = playerPos.getY();
        int playerZ = playerPos.getZ();

        int b1x = block1.getX();
        int b1y = block1.getY();
        int b1z = block1.getZ();

        int b2x = block2.getX();
        int b2y = block2.getY();
        int b2z = block2.getZ();

        int b3x = block3.getX();
        int b3y = block3.getY();
        int b3z = block3.getZ();

        int startX = smallestValue(b1x, b2x, b3x);
        int finalX = biggestValue(b1x, b2x, b3x);

        int startY = smallestValue(b1y, b2y, b3y);
        int finalY = biggestValue(b1y, b2y, b3y);

        int startZ = smallestValue(b1z, b2z, b3z);
        int finalZ = biggestValue(b1z, b2z, b3z);

        for(int i = startX; i <= finalX; i++)
        {
            for(int j = startY; j <= finalY; j++)
            {
                for(int k = startZ; k <= finalZ; k++)
                {
                    if(playerX == i && playerY == j && playerZ == k)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public ArrayList<Block> getBlockList() {
        ArrayList<Block> blocks = new ArrayList<Block>();
        int b1x = block1.getX();
        int b1y = block1.getY();
        int b1z = block1.getZ();

        int b2x = block2.getX();
        int b2y = block2.getY();
        int b2z = block2.getZ();

        int b3x = block3.getX();
        int b3y = block3.getY();
        int b3z = block3.getZ();

        int startX = smallestValue(b1x, b2x, b3x);
        int finalX = biggestValue(b1x, b2x, b3x);

        int startY = smallestValue(b1y, b2y, b3y);
        int finalY = biggestValue(b1y, b2y, b3y);

        int startZ = smallestValue(b1z, b2z, b3z);
        int finalZ = biggestValue(b1z, b2z, b3z);

        for (int i = startX; i <= finalX; i++) {
            for (int j = startY; j <= finalY; j++) {
                for (int k = startZ; k <= finalZ; k++) {
                    Block block = getBlockInfo(i, j, k);
                    if (block != Blocks.AIR) {
                        blocks.add(block);
                    }
                }
            }
        }
        return blocks;
    }

    public Block getBlockInfo(int x, int y, int z)
    {
        MinecraftClient client = MinecraftClient.getInstance();
        Block blockInfo = client.world.getBlockState(new BlockPos(x, y, z)).getBlock();

        if(blockInfo == null)
        {
            return null;
        }
        else
        {
            return blockInfo;
        }
    }


}
