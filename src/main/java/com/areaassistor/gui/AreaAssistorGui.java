package com.areaassistor.gui;

import com.areaassistor.config.ModConfigs;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;

public class AreaAssistorGui extends LightweightGuiDescription {

    public static BlockPos block1 = assignValues(0);
    public static BlockPos block2 = assignValues(1);
    public static BlockPos block3 = assignValues(2);
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
        WLabel block1lab = new WLabel(Text.translatable("Block 1: " + BlockCoords(block1)));
        WLabel block2lab = new WLabel(Text.translatable("Block 2: " + BlockCoords(block2)));
        WLabel block3lab = new WLabel(Text.translatable("Block 3: " + BlockCoords(block3)));

        //setValuesPanel buttons
        WButton block1coords = new WButton(Text.translatable("Change Block 1 Coords"));
        WButton block2coords = new WButton(Text.translatable("Change Block 2 Coords"));
        WButton block3coords = new WButton(Text.translatable("Change Block 3 Coords"));
        WButton resetValues = new WButton(Text.translatable("Reset Values"));

        //setValuesPanel button logic
        block1coords.setOnClick(() -> {
            block1 = getBlockPos();
            block1lab.setText(Text.translatable("Block 1: " + BlockCoords(block1)));
            areaValue = getAreaValue();
            MinecraftClient.getInstance().setScreen(new AreaAssistorScreen(new AreaAssistorGui()));
        });
        block2coords.setOnClick(() -> {
            block2 = getBlockPos();
            block2lab.setText(Text.translatable("Block 2: " + BlockCoords(block2)));
            areaValue = getAreaValue();
            MinecraftClient.getInstance().setScreen(new AreaAssistorScreen(new AreaAssistorGui()));
        });
        block3coords.setOnClick(() -> {
            block3 = getBlockPos();
            block3lab.setText(Text.translatable("Block 3: " + BlockCoords(block3)));
            areaValue = getAreaValue();
            MinecraftClient.getInstance().setScreen(new AreaAssistorScreen(new AreaAssistorGui()));
        });
        resetValues.setOnClick(() -> {
            block1 = BlockPos.ORIGIN;
            block2 = BlockPos.ORIGIN;
            block3 = BlockPos.ORIGIN;
            areaValue = 0;
            MinecraftClient.getInstance().setScreen(new AreaAssistorScreen(new AreaAssistorGui()));
        });


        setValuesPanel.add(block1lab, 1, 1, 1, 1);
        setValuesPanel.add(block2lab, 1, 3, 1, 1);
        setValuesPanel.add(block3lab, 1, 5, 1, 1);
        setValuesPanel.add(resetValues, 1, 7, 4, 1);

        setValuesPanel.add(block1coords, 8, 1, 8, 1);
        setValuesPanel.add(block2coords, 8, 3, 8, 1);
        setValuesPanel.add(block3coords, 8, 5, 8, 1);

        //area panel
        WGridPanel areaPanel = new WGridPanel();
        //setRootPanel(areaPanel);
        areaPanel.setSize(300, 200);

        //area panel labels
        if(areaValue == 0)
        {
            WLabel areaLabel = new WLabel(Text.translatable("Area: " + "No Area Defined"));
            areaPanel.add(areaLabel, 1, 1, 1, 1);
        }
        else
        {
            WLabel areaLabel = new WLabel(Text.translatable("Area: " + areaValue));
            areaPanel.add(areaLabel, 1, 1, 1, 1);
        }



        //area calculation panel
        WGridPanel areaCalcPanel = new WGridPanel();
        areaCalcPanel.setSize(300, 200);

        WLabel enchantmentText = new WLabel(Text.translatable("Unbreaking Level: " + eLevel));
        if(eLevel == 0){
            enchantmentText.setText(Text.translatable("Unbreaking Level: " + "No Unbreaking"));
        }
        else if(eLevel == 1){
            enchantmentText.setText(Text.translatable("Unbreaking Level: " + "Unbreaking 1"));
        }
        else if(eLevel == 2){
            enchantmentText.setText(Text.translatable("Unbreaking Level: " + "Unbreaking 2"));
        }
        else if(eLevel == 3){
            enchantmentText.setText(Text.translatable("Unbreaking Level: " + "Unbreaking 3"));
        }


        WButton noUnbreaking = new WButton(Text.translatable("No Unbreaking"));
        WButton unbreaking1 = new WButton(Text.translatable("Unbreaking 1"));
        WButton unbreaking2 = new WButton(Text.translatable("Unbreaking 2"));
        WButton unbreaking3 = new WButton(Text.translatable("Unbreaking 3"));

        noUnbreaking.setOnClick(() -> {
            eLevel = 0;
            MinecraftClient.getInstance().setScreen(new AreaAssistorScreen(new AreaAssistorGui()));
        });
        unbreaking1.setOnClick(() -> {
            eLevel = 1;
            MinecraftClient.getInstance().setScreen(new AreaAssistorScreen(new AreaAssistorGui()));
        });
        unbreaking2.setOnClick(() -> {
            eLevel = 2;
            MinecraftClient.getInstance().setScreen(new AreaAssistorScreen(new AreaAssistorGui()));
        });
        unbreaking3.setOnClick(() -> {
            eLevel = 3;
            MinecraftClient.getInstance().setScreen(new AreaAssistorScreen(new AreaAssistorGui()));
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

        WLabel toolLabel = new WLabel(Text.translatable("Tool: " + toolName));
        WLabel toolInfoLabel = new WLabel(Text.translatable(toolStuff));

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
        WLabel areaCaluclationInfo = new WLabel(Text.translatable(areaInformation));

        //area calculation panel add
        areaCalcPanel.add(toolLabel, 1, 1, 1, 1);
        areaCalcPanel.add(enchantmentText, 1, 2, 1, 1);
        areaCalcPanel.add(noUnbreaking, 4, 3, 6, 1);
        areaCalcPanel.add(unbreaking1, 4, 4, 6, 1);
        areaCalcPanel.add(unbreaking2, 4, 5, 6, 1);
        areaCalcPanel.add(unbreaking3, 4, 6, 6, 1);
        areaCalcPanel.add(toolInfoLabel, 1, 8, 1, 1);
        areaCalcPanel.add(areaCaluclationInfo, 1, 9, 1, 1);

        //other panel
        playerPos = getPlayerPosition();
        System.out.println(playerPos);

        System.out.println(inArea());

        WGridPanel otherPanel = new WGridPanel();


        //tabs
        WTabPanel tabs = new WTabPanel();
        tabs.add(setValuesPanel, tab -> tab.title(Text.literal("Set Values")));
        tabs.add(areaPanel, tab -> tab.title(Text.literal("Area Value")));
        tabs.add(areaCalcPanel, tab -> tab.title(Text.literal("Area Calculation")));

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

    public static BlockPos assignValues(int blockNum)
    {
        int x;
        int y;
        int z;
        if(blockNum == 0)
        {
            x = ModConfigs.Block1X;
            y = ModConfigs.Block1Y;
            z = ModConfigs.Block1Z;
        }
        if(blockNum == 1)
        {
            x = ModConfigs.Block2X;
            y = ModConfigs.Block2Y;
            z = ModConfigs.Block2Z;
        }
        if(blockNum == 2)
        {
            x = ModConfigs.Block3X;
            y = ModConfigs.Block3Y;
            z = ModConfigs.Block3Z;
        }
        else
        {
            x = 0;
            y = 0;
            z = 0;
        }
        BlockPos blockPos = new BlockPos(x, y, z);
        return blockPos;
    }
    public static void changeConfigValues()
    {
        ModConfigs.Block1X = block1.getX();
        ModConfigs.Block1Y = block1.getY();
        ModConfigs.Block1Z = block1.getZ();
        ModConfigs.Block2X = block2.getX();
        ModConfigs.Block2Y = block2.getY();
        ModConfigs.Block2Z = block2.getZ();
        ModConfigs.Block3X = block3.getX();
        ModConfigs.Block3Y = block3.getY();
        ModConfigs.Block3Z = block3.getZ();

        System.out.println(ModConfigs.Block1X);
        System.out.println("Values should be saved");
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
}
