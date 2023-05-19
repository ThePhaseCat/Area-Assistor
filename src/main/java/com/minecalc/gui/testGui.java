package com.minecalc.gui;

import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;

public class testGui extends LightweightGuiDescription {

    public static BlockPos block1;
    public static BlockPos block2;
    public static BlockPos block3;
    public static int areaValue;

    public static Item toolInfo = getHeldItem();

    public testGui() {
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

        //area calculation panel
        WGridPanel areaCalcPanel = new WGridPanel();
        areaCalcPanel.setSize(300, 200);

        //area calculation panel labels
        String toolName;
        String toolStuff;
        if(toolInfo == null)
        {
            toolName = "No Tool in Hand!";
            toolStuff = "Please have a tool in your hand before using this service!";
        }
        else
        {
            toolName = toolInfo.getName().getString();
            toolStuff = "Durability: " + durabilityCalculation(toolInfo);
        }

        WLabel toolLabel = new WLabel(Text.translatable("Tool: " + toolName));
        WLabel toolInfoLabel = new WLabel(Text.translatable(toolStuff));

        String areaInformation;
        if(howManyAmount() == 0.0 || toolInfo == null)
        {
            areaInformation = "No area defined or no tool in hand!";
        }
        else
        {
            //check if the tool is a shovel or pickaxe
            if(toolInfo == Items.WOODEN_SHOVEL || toolInfo == Items.STONE_SHOVEL || toolInfo == Items.IRON_SHOVEL || toolInfo == Items.GOLDEN_SHOVEL || toolInfo == Items.DIAMOND_SHOVEL || toolInfo == Items.NETHERITE_SHOVEL)
            {
                areaInformation = "It will take " + howManyAmount() + toolName + "'s to clear the area of " + areaValue + " blocks!";
            }
            else
            {
                areaInformation = "It will take " + howManyAmount() + toolName + "'s to clear the area of " + areaValue + " blocks!";
            }
        }
        WLabel areaCaluclationInfo = new WLabel(Text.translatable(areaInformation));

        //area calculation panel add
        areaCalcPanel.add(toolLabel, 1, 1, 1, 1);
        areaCalcPanel.add(toolInfoLabel, 1, 3, 1, 1);
        areaCalcPanel.add(areaCaluclationInfo, 1, 5, 1, 1);

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

        //run new methods
        toolInfo = getHeldItem();
        return area;
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

    public int durabilityCalculation(Item tool)
    {
        if(tool == null)
        {
            return 0;
        }
        //change tool to item stack
        ItemStack toolStack = new ItemStack(tool);
        int durability = tool.getMaxDamage();
        System.out.println(durability);

        //check what item the tool is and then get the durability
        if(tool == Items.WOODEN_PICKAXE || tool == Items.WOODEN_SHOVEL)
        {
            durability = 60;
        }
        if(tool == Items.WOODEN_PICKAXE || tool == Items.WOODEN_SHOVEL)
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
        int unbreaking = EnchantmentHelper.getLevel(Enchantments.UNBREAKING, toolStack);
        System.out.println(unbreaking);

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
        System.out.println(durability);
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
            double durability = durabilityCalculation(toolInfo);
            if(durability == 0)
            {
                return 0.0;
            }
            double howMany = area / durability;
            return howMany;
        }
    }
}
