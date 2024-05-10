package com.areaassistor.newGUI;

import com.areaassistor.AreaAssistorClient;
import io.wispforest.owo.ui.base.BaseOwoScreen;
import io.wispforest.owo.ui.component.Components;
import io.wispforest.owo.ui.container.Containers;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.core.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;

public class AreaAssistorMainScreen extends BaseOwoScreen<FlowLayout> {

    public static BlockPos block1Pos = BlockPos.ORIGIN;
    public static BlockPos block2Pos = BlockPos.ORIGIN;
    public static BlockPos block3Pos = BlockPos.ORIGIN;

    @Override
    protected @NotNull OwoUIAdapter<FlowLayout> createAdapter() {
        return OwoUIAdapter.create(this, Containers::verticalFlow);
    }

    @Override
    protected void build(FlowLayout rootComponent) {
        String block1Coords = "Block 1: " + blockCoords(block1Pos);
        String block2Coords = "Block 2: " + blockCoords(block2Pos);
        String block3Coords = "Block 3: " + blockCoords(block3Pos);

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
                Containers.collapsible(Sizing.content(), Sizing.content(), Text.literal("Set Area"), true)
                        .child(Components.button(Text.literal("Set Block 1 Position"), button -> { setBlockPos(1); }))
                        .child(Components.button(Text.literal("Set Block 2 Position"), button -> { setBlockPos(2); }))
                        .child(Components.button(Text.literal("Set Block 3 Position"), button -> { setBlockPos(3); }))
                        .child(Components.label(Text.literal(block1Coords)))
                        .child(Components.label(Text.literal(block2Coords)))
                        .child(Components.label(Text.literal(block3Coords)))
                        .padding(Insets.of(10)) //
                        .surface(Surface.DARK_PANEL)
                        .verticalAlignment(VerticalAlignment.CENTER)
                        .horizontalAlignment(HorizontalAlignment.CENTER)
        );
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
                    block1Pos = blockPos;
                    MinecraftClient.getInstance().setScreen(new AreaAssistorMainScreen());
                    break;
                case 2:
                    block2Pos = blockPos;
                    MinecraftClient.getInstance().setScreen(new AreaAssistorMainScreen());
                    break;
                case 3:
                    block3Pos = blockPos;
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
}
