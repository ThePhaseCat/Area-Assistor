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
import net.minecraft.item.ItemStack;
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

    public static boolean isSetBlocksOpen = false;

    public static boolean isEnchantmentOpen = false;

    public AreaAssistorMainScreen() {
        super();
    }

    public AreaAssistorMainScreen(boolean isBlocksOpen, boolean isEnchantmentOpen) {
        super();
        isSetBlocksOpen = isBlocksOpen;
        isEnchantmentOpen = isEnchantmentOpen;
    }

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

        String toolName;
        String toolStuff;

        if(toolInfo == null)
        {
            toolName = "No Tool in Hand!";
            toolStuff = "Equip a tool before trying to use this feature!";
        }
        else
        {
            toolName = toolInfo.getName().getString();
            toolStuff = "Durability: " + durabilityCalculation(toolInfo, eLevel);
        }

        String areaInformation = null;

        if(howManyAmount() == 0.0 || toolInfo == null)
        {
            areaInformation = "No area defined or no tool in hand!";
        }
        else
        {
            String toolAmount = howManyAmount() + " ";
            //check if the tool is a shovel or pickaxe
            if(toolInfo == Items.WOODEN_SHOVEL || toolInfo == Items.STONE_SHOVEL || toolInfo == Items.IRON_SHOVEL || toolInfo == Items.GOLDEN_SHOVEL || toolInfo == Items.DIAMOND_SHOVEL || toolInfo == Items.NETHERITE_SHOVEL || toolInfo == Items.WOODEN_PICKAXE || toolInfo == Items.STONE_PICKAXE || toolInfo == Items.IRON_PICKAXE || toolInfo == Items.GOLDEN_PICKAXE || toolInfo == Items.DIAMOND_PICKAXE || toolInfo == Items.NETHERITE_PICKAXE)
            {
                areaInformation = "It will take " + toolAmount + toolName + "'s to clear " + areaValue + " blocks!";
            }
        }

        String unbreakingLevel = null;
        if(eLevel == 0)
        {
            unbreakingLevel = "No Unbreaking";
        }
        else if(eLevel == 1)
        {
            unbreakingLevel = "Unbreaking I";
        }
        else if(eLevel == 2)
        {
            unbreakingLevel = "Unbreaking II";
        }
        else if(eLevel == 3)
        {
            unbreakingLevel = "Unbreaking III";
        }

        rootComponent
                .surface(Surface.VANILLA_TRANSLUCENT)
                .horizontalAlignment(HorizontalAlignment.CENTER)
                .verticalAlignment(VerticalAlignment.CENTER);

        rootComponent.child(
                Containers.collapsible(Sizing.content(), Sizing.content(), Text.literal("Set Block Positions For Area Calculations"), isSetBlocksOpen)
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
                Containers.collapsible(Sizing.content(), Sizing.content(), Text.literal("Enchant/Area Info"), false)
                        .child(Components.label(Text.literal(toolName)))
                        .child(Components.label(Text.literal(toolStuff)))
                        .child(Components.label(Text.literal("Unbreaking Level: " + unbreakingLevel)))
                        .child(Components.button(Text.literal("No Unbreaking"), button -> { setELevel(0); }))
                        .child(Components.button(Text.literal("Unbreaking I"), button -> { setELevel(1); }))
                        .child(Components.button(Text.literal("Unbreaking II"), button -> { setELevel(2); }))
                        .child(Components.button(Text.literal("Unbreaking III"), button -> { setELevel(3); }))
                        .child(Components.label(Text.literal(areaLabel)))
                        .child(Components.label(Text.literal(isPlayerInArea)))
                        .child(Components.label(Text.literal(areaInformation)))
                        .padding(Insets.of(10)) //
                        .surface(Surface.DARK_PANEL)
                        .verticalAlignment(VerticalAlignment.CENTER)
                        .horizontalAlignment(HorizontalAlignment.CENTER)
        );

        rootComponent.child(
                Containers.verticalFlow(Sizing.content(), Sizing.content())
                        .child(Components.button(Text.literal("View Blocks in Area"), button -> { switchToBlocksScreen();}))
                        .padding(Insets.of(10)) //
                        .surface(Surface.DARK_PANEL)
                        .verticalAlignment(VerticalAlignment.CENTER)
                        .horizontalAlignment(HorizontalAlignment.CENTER)
        );

        isSetBlocksOpen = false;
        isEnchantmentOpen = false;
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
                    MinecraftClient.getInstance().setScreen(new AreaAssistorMainScreen(true, false));
                    break;
                case 2:
                    block2 = blockPos;
                    MinecraftClient.getInstance().setScreen(new AreaAssistorMainScreen(true, false));
                    break;
                case 3:
                    block3 = blockPos;
                    MinecraftClient.getInstance().setScreen(new AreaAssistorMainScreen(true, false));
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
        //reset/update player position
        playerPos = getPlayerPosition();

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

        boolean isPlayerInArea = false;

        //AreaAssistorClient.LOGGER.info("Player Pos: " + playerX + ", " + playerY + ", " + playerZ);
        //AreaAssistorClient.LOGGER.info("Start Pos: " + startX + ", " + startY + ", " + startZ);
        //AreaAssistorClient.LOGGER.info("Final Pos: " + finalX + ", " + finalY + ", " + finalZ);

        for(int i = startX; i <= finalX; i++)
        {
            for(int j = startY; j <= finalY; j++)
            {
                for(int k = startZ; k <= finalZ; k++)
                {
                    //AreaAssistorClient.LOGGER.info("Block Pos Checking: " + i + ", " + j + ", " + k);
                    if(playerX == i && playerY == j && playerZ == k)
                    {
                        //AreaAssistorClient.LOGGER.info("Player is in the area!");
                        isPlayerInArea = true;
                    }
                }
            }
        }
        if(isPlayerInArea)
        {
            return true;
        }
        else
        {
            return false;
        }
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

    public void switchToBlocksScreen()
    {
        ArrayList<Block> blocks = getBlockList();
        MinecraftClient.getInstance().setScreen(new AreaAssistorBlocksScreen(blocks));
    }

    public void setELevel(int level)
    {
        eLevel = level;
        MinecraftClient.getInstance().setScreen(new AreaAssistorMainScreen(false, true));
    }

    public int durabilityCalculation(Item tool, int enchantLevel)
    {
        if(tool == null)
        {
            return 0;
        }
        //change tool to item stack
        ItemStack toolStack = new ItemStack(tool);
        int durability = 0;

        //check what item the tool is and then get the durability
        if(tool == Items.WOODEN_PICKAXE || tool == Items.WOODEN_SHOVEL)
        {
            durability = 60;
        }
        if(tool == Items.STONE_PICKAXE || tool == Items.STONE_SHOVEL)
        {
            durability = 132;
        }
        if(tool == Items.IRON_PICKAXE || tool == Items.IRON_SHOVEL)
        {
            durability = 251;
        }
        if(tool == Items.GOLDEN_PICKAXE || tool == Items.GOLDEN_SHOVEL)
        {
            durability = 33;
        }
        if(tool == Items.DIAMOND_PICKAXE || tool == Items.DIAMOND_SHOVEL)
        {
            durability = 1562;
        }
        if(tool == Items.NETHERITE_PICKAXE || tool == Items.NETHERITE_SHOVEL)
        {
            durability = 2032;
        }


        //get enchant of unbreaking
        int unbreaking = enchantLevel;

        //calculate durability
        if (unbreaking == 1)
        {
            durability = durability * 2;
        }
        else if (unbreaking == 2)
        {
            durability = durability * 3;
        }
        else if (unbreaking == 3)
        {
            durability = durability * 4;
        }
        else
        {
            durability = durability;
        }
        return durability;
    }

    public double howManyAmount()
    {
        //calculate how many pickaxes or shovels are required to clear out an area
        int area = getAreaValue();
        if(area == 0)
        {
            return 0.0;
        }
        else
        {
            double durability = durabilityCalculation(toolInfo, eLevel);
            if(durability == 0)
            {
                return 0.0;
            }
            double howMany = area / durability;
            //trim howMany to 2 decimal places
            howMany = Math.round(howMany * 100.0) / 100.0;
            return howMany;
        }
    }
}
