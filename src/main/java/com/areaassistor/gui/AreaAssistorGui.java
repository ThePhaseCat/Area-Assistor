package com.areaassistor.gui;

import com.areaassistor.gui2.AreaAssistorBlocksGui;
import io.github.cottonmc.cotton.gui.client.CottonClientScreen;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.*;
import net.fabricmc.fabric.api.util.TriState;
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

import java.util.ArrayList;

public class AreaAssistorGui extends LightweightGuiDescription {

    public static BlockPos block1 = BlockPos.ORIGIN;
    public static BlockPos block2 = BlockPos.ORIGIN;
    public static BlockPos block3 = BlockPos.ORIGIN;

    public static Block block1info;
    public static Block block2info;
    public static Block block3info;
    public static int areaValue;

    public static Item toolInfo = getHeldItem();

    public static int eLevel = 0;

    public static BlockPos playerPos = getPlayerPosition();

    public AreaAssistorGui() {
        toolInfo = getHeldItem();
        //setValuesPanel
        WGridPanel setValuesPanel = new WGridPanel();
        setRootPanel(setValuesPanel);
        setValuesPanel.setSize(300, 200);

        //setValuesPanel Labels
        WLabel block1lab = new WLabel(Text.literal("Block 1: " + BlockCoords(block1)));
        WLabel block2lab = new WLabel(Text.literal("Block 2: " + BlockCoords(block2)));
        WLabel block3lab = new WLabel(Text.literal("Block 3: " + BlockCoords(block3)));


        //setValuesPanel buttons
        WButton block1coords = new WButton(Text.literal("Change Block 1 Coords"));
        WButton block2coords = new WButton(Text.literal("Change Block 2 Coords"));
        WButton block3coords = new WButton(Text.literal("Change Block 3 Coords"));
        WButton resetValues = new WButton(Text.literal("Reset Values"));

        //setValuesPanel button logic
        block1coords.setOnClick(() -> {
            block1 = getBlockPos();
            block1lab.setText(Text.literal("Block 1: " + BlockCoords(block1)));
            areaValue = getAreaValue();
            block1info = getBlockInfo();
            MinecraftClient.getInstance().setScreen(new CottonClientScreen(new AreaAssistorGui()));
        });
        block2coords.setOnClick(() -> {
            block2 = getBlockPos();
            block2lab.setText(Text.literal("Block 2: " + BlockCoords(block2)));
            areaValue = getAreaValue();
            block2info = getBlockInfo();
            MinecraftClient.getInstance().setScreen(new CottonClientScreen(new AreaAssistorGui()));
        });
        block3coords.setOnClick(() -> {
            block3 = getBlockPos();
            block3lab.setText(Text.literal("Block 3: " + BlockCoords(block3)));
            areaValue = getAreaValue();
            block3info = getBlockInfo();
            MinecraftClient.getInstance().setScreen(new CottonClientScreen(new AreaAssistorGui()));
        });
        resetValues.setOnClick(() -> {
            block1 = BlockPos.ORIGIN;
            block2 = BlockPos.ORIGIN;
            block3 = BlockPos.ORIGIN;
            areaValue = 0;
            block1info = null;
            block2info = null;
            block3info = null;
            MinecraftClient.getInstance().setScreen(new CottonClientScreen(new AreaAssistorGui()));
        });


        setValuesPanel.add(block1lab, 1, 1, 1, 1);
        setValuesPanel.add(block2lab, 1, 3, 1, 1);
        setValuesPanel.add(block3lab, 1, 5, 1, 1);
        setValuesPanel.add(resetValues, 1, 7, 4, 1);

        setValuesPanel.add(block1coords, 8, 1, 8, 1);
        setValuesPanel.add(block2coords, 8, 3, 8, 1);
        setValuesPanel.add(block3coords, 8, 5, 8, 1);

        //area calculation panel
        WGridPanel areaCalcPanel = new WGridPanel();
        areaCalcPanel.setSize(300, 200);

        WLabel enchantmentText = new WLabel(Text.literal("Unbreaking Level: " + eLevel));
        if(eLevel == 0){
            enchantmentText.setText(Text.literal("Unbreaking Level: " + "No Unbreaking"));
        }
        else if(eLevel == 1){
            enchantmentText.setText(Text.literal("Unbreaking Level: " + "Unbreaking 1"));
        }
        else if(eLevel == 2){
            enchantmentText.setText(Text.literal("Unbreaking Level: " + "Unbreaking 2"));
        }
        else if(eLevel == 3){
            enchantmentText.setText(Text.literal("Unbreaking Level: " + "Unbreaking 3"));
        }


        WButton noUnbreaking = new WButton(Text.literal("No Unbreaking"));
        WButton unbreaking1 = new WButton(Text.literal("Unbreaking 1"));
        WButton unbreaking2 = new WButton(Text.literal("Unbreaking 2"));
        WButton unbreaking3 = new WButton(Text.literal("Unbreaking 3"));

        noUnbreaking.setOnClick(() -> {
            eLevel = 0;
            MinecraftClient.getInstance().setScreen(new CottonClientScreen(new AreaAssistorGui()));
        });
        unbreaking1.setOnClick(() -> {
            eLevel = 1;
            MinecraftClient.getInstance().setScreen(new CottonClientScreen(new AreaAssistorGui()));
        });
        unbreaking2.setOnClick(() -> {
            eLevel = 2;
            MinecraftClient.getInstance().setScreen(new CottonClientScreen(new AreaAssistorGui()));
        });
        unbreaking3.setOnClick(() -> {
            eLevel = 3;
            MinecraftClient.getInstance().setScreen(new CottonClientScreen(new AreaAssistorGui()));
        });

        //area calculation panel labels
        String toolName;
        String toolStuff;
        if(toolInfo == null)
        {
            toolName = "No Tool in Hand!";
            toolStuff = "Equip a tool before using this service!";
        }
        else
        {
            toolName = toolInfo.getName().getString();
            toolStuff = "Durability: " + durabilityCalculation(toolInfo, eLevel);
        }

        WLabel toolLabel = new WLabel(Text.literal("Tool: " + toolName));
        WLabel toolInfoLabel = new WLabel(Text.literal(toolStuff));

        String areaInformation = null;
        if(howManyAmount() == 0.0 || toolInfo == null)
        {
            areaInformation = "No area defined or no tool in hand!";
        }
        else
        {
            String toolAmount = String.valueOf(howManyAmount()) + " ";
            //check if the tool is a shovel or pickaxe
            if(toolInfo == Items.WOODEN_SHOVEL || toolInfo == Items.STONE_SHOVEL || toolInfo == Items.IRON_SHOVEL || toolInfo == Items.GOLDEN_SHOVEL || toolInfo == Items.DIAMOND_SHOVEL || toolInfo == Items.NETHERITE_SHOVEL || toolInfo == Items.WOODEN_PICKAXE || toolInfo == Items.STONE_PICKAXE || toolInfo == Items.IRON_PICKAXE || toolInfo == Items.GOLDEN_PICKAXE || toolInfo == Items.DIAMOND_PICKAXE || toolInfo == Items.NETHERITE_PICKAXE)
            {
                areaInformation = "It will take " + toolAmount + toolName + "'s to clear " + areaValue + " blocks!";
            }
        }
        WLabel areaCaluclationInfo = new WLabel(Text.literal(areaInformation));

        //area calculation panel add
        areaCalcPanel.add(toolLabel, 1, 1, 1, 1);
        areaCalcPanel.add(enchantmentText, 1, 2, 1, 1);
        areaCalcPanel.add(noUnbreaking, 4, 3, 6, 1);
        areaCalcPanel.add(unbreaking1, 4, 4, 6, 1);
        areaCalcPanel.add(unbreaking2, 4, 5, 6, 1);
        areaCalcPanel.add(unbreaking3, 4, 6, 6, 1);
        areaCalcPanel.add(toolInfoLabel, 1, 8, 1, 1);
        areaCalcPanel.add(areaCaluclationInfo, 1, 9, 1, 1);

        //area panel
        WGridPanel areaPanel = new WGridPanel();
        areaPanel.setSize(300, 200);

        playerPos = getPlayerPosition();

        //area panel labels
        WLabel areaLabel = null;
        if(areaValue == 0)
        {
            areaLabel = new WLabel(Text.literal("Area: " + "No Area Defined"));
        }
        else
        {
            areaLabel = new WLabel(Text.literal("Area: " + areaValue));

        }

        WLabel inAreaLabel = null;
        if(inArea())
        {
            inAreaLabel = new WLabel(Text.literal("In Area Selected: True"));
        }
        else
        {
            inAreaLabel = new WLabel(Text.literal("In Area Selected: False"));
        }

        String block1name = null;
        String block2name = null;
        String block3name = null;
        if(block1info == null)
        {
            block1name = "No Block Selected";
        }
        else
        {
            block1name = block1info.getTranslationKey().toString();
            block1name = block1name.substring(16);
        }

        if(block2info == null)
        {
            block2name = "No Block Selected";
        }
        else
        {
            block2name = block2info.getTranslationKey().toString();
            block2name = block2name.substring(16);
        }

        if(block3info == null)
        {
            block3name = "No Block Selected";
        }
        else
        {
            block3name = block3info.getTranslationKey().toString();
            block3name = block3name.substring(16);
        }

        WLabel b1name = new WLabel(Text.literal("Block 1: " + block1name));
        WLabel b2name = new WLabel(Text.literal("Block 2: " + block2name));
        WLabel b3name = new WLabel(Text.literal("Block 3: " + block3name));

        WButton refreshButton = new WButton(Text.literal("Refresh Screen"));
        refreshButton.setOnClick(() -> {
            MinecraftClient.getInstance().setScreen(new CottonClientScreen(new AreaAssistorGui()));
        });

        WButton areaBlocks = new WButton(Text.literal("All blocks in Area Selected"));
        areaBlocks.setOnClick(() -> {
            ArrayList<Block> blockList = getBlockList();
            MinecraftClient.getInstance().setScreen(new CottonClientScreen(new AreaAssistorBlocksGui(blockList)));
        });

        areaPanel.add(b1name, 1, 1, 1, 1);
        areaPanel.add(b2name, 1, 2, 1, 1);
        areaPanel.add(b3name, 1, 3, 1, 1);
        areaPanel.add(inAreaLabel, 1, 5, 1, 1);
        areaPanel.add(areaLabel, 1, 6, 1, 1);
        areaPanel.add(areaBlocks, 1, 9, 8, 1);
        areaPanel.add(refreshButton, 1, 8, 5, 1);

        //tabs
        WTabPanel tabs = new WTabPanel();
        tabs.add(setValuesPanel, tab -> tab.title(Text.literal("Set Values")));
        tabs.add(areaCalcPanel, tab -> tab.title(Text.literal("Area Calculation")));
        tabs.add(areaPanel, tab -> tab.title(Text.literal("Area Information")));

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

    public Block getBlockInfo()
    {
        MinecraftClient client = MinecraftClient.getInstance();
        HitResult hit = client.crosshairTarget;

        BlockHitResult blockHit = (BlockHitResult) hit;
        Block blockInfo = client.world.getBlockState(blockHit.getBlockPos()).getBlock();

        if(hit.getType() == HitResult.Type.BLOCK)
        {
            return blockInfo;
        }
        else
        {
            return null;
        }
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

        //run new methods
        toolInfo = getHeldItem();
        return area;
    }


    public String BlockCoords(BlockPos blockGiven)
    {
        if (blockGiven == null)
        {
            return "No Block Selected!";
        }
        int blockX = blockGiven.getX();
        int blockY = blockGiven.getY();
        int blockZ = blockGiven.getZ();

        String blockCoords = blockX + ", " + blockY + ", " + blockZ;
        return blockCoords;
    }

    //method to get the tool that a player is holding
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

    public int durabilityCalculation(Item tool, int enchantLevel)
    {
        if(tool == null)
        {
            return 0;
        }
        //change tool to item stack
        ItemStack toolStack = new ItemStack(tool);
        int durability = tool.getMaxDamage();

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

    public static BlockPos getPlayerPosition()
    {
        MinecraftClient client = MinecraftClient.getInstance();
        PlayerEntity player = client.player;
        BlockPos playerPos = player.getBlockPos();
        return playerPos;
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
}
