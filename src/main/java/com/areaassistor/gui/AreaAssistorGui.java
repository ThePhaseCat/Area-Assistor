package com.areaassistor.gui;

import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.*;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import org.apache.http.impl.cookie.RFC2965SpecFactory;

import java.util.Arrays;
import java.util.function.Consumer;

import static com.areaassistor.Util.biggestValue;
import static com.areaassistor.Util.smallestValue;
import static com.areaassistor.config.ModConfigs.blocks;
import static java.lang.Math.max;
import static java.lang.Math.min;

public class AreaAssistorGui extends LightweightGuiDescription {
    public static int areaValue;


    public static int eLevel = 0;


    public AreaAssistorGui() {
        //setValuesPanel
        WGridPanel setValuesPanel = new WGridPanel();
        setRootPanel(setValuesPanel);
        setValuesPanel.setSize(300, 200);

        //setValuesPanel Labels
        for (int i = 0; i < 3; i++) {
            WButton blockCoords = new WButton(Text.literal("Change Block " + (i-1) + " Coords"));
            WLabel blockLab = new WLabel(Text.literal("Block " + (i-1) + ": " + blockCoords(blocks[i])));

            int finalI = i;
            blockCoords.setOnClick(() -> {
                blocks[finalI -1] = getLookedAtBlockPos().mutableCopy();
                blockLab.setText(Text.literal("Block " + (finalI-1) + ": " + blockCoords(blocks[finalI])));
                areaValue = getAreaValue();
                MinecraftClient.getInstance().setScreen(new AreaAssistorScreen(new AreaAssistorGui()));
            });
            setValuesPanel.add(blockCoords, 8, 1 + (2 * i), 8, 1);
            setValuesPanel.add(blockLab, 1, 1 + (2 * i), 1, 1);
        }


        WButton resetValues = new WButton(Text.translatable("Reset Values"));


        resetValues.setOnClick(() -> {
            blocks = new BlockPos.Mutable[]{ // this feels bad
                    BlockPos.ORIGIN.mutableCopy(),
                    BlockPos.ORIGIN.mutableCopy(),
                    BlockPos.ORIGIN.mutableCopy()
            };
            areaValue = 0;
            MinecraftClient.getInstance().setScreen(new AreaAssistorScreen(new AreaAssistorGui()));
        });
        setValuesPanel.add(resetValues, 1, 7, 4, 1);


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

        noUnbreaking.setOnClick(() -> {
            eLevel = 0;
            MinecraftClient.getInstance().setScreen(new AreaAssistorScreen(new AreaAssistorGui()));
        });

        for (int i = 1; i <= 3; i++) {
            WButton unbreaking = new WButton(Text.literal("Unbreaking " + i));
            int finalI = i;
            unbreaking.setOnClick(() -> {
                eLevel = finalI;
                MinecraftClient.getInstance().setScreen(new AreaAssistorScreen(new AreaAssistorGui()));
            });
            areaCalcPanel.add(unbreaking, 4, 3 + i, 6, 1);
        }

        //area calculation panel labels
        String toolName;
        String toolStuff;
        var item = getHeldItem();
        if(item == null)
        {
            toolName = "No Tool in Hand!";
            toolStuff = "Equip a tool before using this service!";
        }
        else
        {
            toolName = item.getName().getString();
            toolStuff = "Durability: " + durabilityCalculation(item, eLevel);
        }

        WLabel toolLabel = new WLabel(Text.translatable("Tool: " + toolName));
        WLabel toolInfoLabel = new WLabel(Text.translatable(toolStuff));

        String areaInformation = null;
        if(howManyAmount() == 0.0 || item == null)
        {
            areaInformation = "No area defined or no tool in hand!";
        }
        else
        {
            String toolAmount = String.valueOf(howManyAmount()) + " ";
            //check if the tool is a shovel or pickaxe
            if(item == Items.WOODEN_SHOVEL ||
                    item == Items.STONE_SHOVEL ||
                    item == Items.IRON_SHOVEL ||
                    item== Items.GOLDEN_SHOVEL ||
                    item == Items.DIAMOND_SHOVEL ||
                    item == Items.NETHERITE_SHOVEL ||
                    item == Items.WOODEN_PICKAXE ||
                    item == Items.STONE_PICKAXE ||
                    item == Items.IRON_PICKAXE ||
                    item == Items.GOLDEN_PICKAXE ||
                    item == Items.DIAMOND_PICKAXE ||
                    item == Items.NETHERITE_PICKAXE
            )
            {
                areaInformation = "It will take " + toolAmount + toolName + "'s to clear " + areaValue + " blocks!";
            }
        }
        WLabel areaCaluclationInfo = new WLabel(Text.literal(areaInformation));

        //area calculation panel add
        areaCalcPanel.add(toolLabel, 1, 1, 1, 1);
        areaCalcPanel.add(enchantmentText, 1, 2, 1, 1);
        areaCalcPanel.add(noUnbreaking, 4, 3, 6, 1);
        areaCalcPanel.add(toolInfoLabel, 1, 8, 1, 1);
        areaCalcPanel.add(areaCaluclationInfo, 1, 9, 1, 1);

        //area panel
        WGridPanel areaPanel = new WGridPanel();
        areaPanel.setSize(300, 200);


        //area panel labels
        WLabel areaLabel = new WLabel(Text.literal("Area: " + (areaValue != 0 ? areaValue : "No Area Defined")));
        areaPanel.add(areaLabel, 1, 6, 1, 1);

        //Block[] blocks = new Block[] {block1info, block2info, block3info};
        ClientWorld world = MinecraftClient.getInstance().world;
        for(int b = 1; b <= blocks.length; b++) {
            BlockPos pos = blocks[b-1];
            Block block = world.getBlockState(pos).getBlock();
            String text;
            if (block == null) {
                text = "No Block Selected";
            } else {
                text = block.getTranslationKey().substring(16);
            }
            WLabel label = new WLabel(Text.literal(text));
            areaPanel.add(label, 1, b, 1, 1);
        }

        WButton refreshButton = new WButton(Text.literal("Refresh Screen"));
        refreshButton.setOnClick(() -> {
            MinecraftClient.getInstance().setScreen(new AreaAssistorScreen(new AreaAssistorGui()));
        });
        areaPanel.add(refreshButton, 1, 8, 3, 1);

        //tabs
        WTabPanel tabs = new WTabPanel();
        tabs.add(setValuesPanel, tab -> tab.title(Text.literal("Set Values")));
        tabs.add(areaCalcPanel, tab -> tab.title(Text.literal("Area Calculation")));
        tabs.add(areaPanel, tab -> tab.title(Text.literal("Area Information")));

        setRootPanel(tabs);
        tabs.setSelectedIndex(0);
    }

    public BlockPos getLookedAtBlockPos()
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
        int[] x = new int[] {blocks[0].getX(), blocks[1].getX(), blocks[2].getX()};
        int[] y = new int[] {blocks[0].getY(), blocks[1].getY(), blocks[2].getY()};
        int[] z = new int[] {blocks[0].getZ(), blocks[1].getZ(), blocks[2].getZ()};

        // streams: it's like kotlin but cringer
        // val minCoord = BlockPos(x.min(), y.min(), z.min()) would work iirc
        BlockPos minCoord = new BlockPos(Arrays.stream(x).min().getAsInt(), Arrays.stream(y).min().getAsInt(), Arrays.stream(z).min().getAsInt());
        BlockPos maxCoord = new BlockPos(Arrays.stream(x).max().getAsInt(), Arrays.stream(y).max().getAsInt(), Arrays.stream(z).max().getAsInt());

        BlockPos f = maxCoord.subtract(minCoord);

        return f.getX() * f.getY() * f.getZ();
    }


    public String blockCoords(BlockPos blockGiven)
    {
        if (blockGiven == null)
        {
            return "No Block Selected!";
        }
        return "%d, %d, %d".formatted(blockGiven.getX(), blockGiven.getY(), blockGiven.getZ());
    }

    //method to get the tool that a player is holding
    public static Item getHeldItem()
    {
        Item heldItem =  MinecraftClient.getInstance().player.getMainHandStack().getItem();
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
        int durability = new ItemStack(tool).getMaxDamage();

        //check what item the tool is and then get the durability
        return durability * enchantLevel;
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
            double durability = durabilityCalculation(getHeldItem(), eLevel);
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
}
