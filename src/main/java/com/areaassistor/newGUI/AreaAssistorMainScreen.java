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

    @Override
    protected @NotNull OwoUIAdapter<FlowLayout> createAdapter() {
        return OwoUIAdapter.create(this, Containers::verticalFlow);
    }

    @Override
    protected void build(FlowLayout rootComponent) {
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
                Containers.collapsible(Sizing.content(), Sizing.content(), Text.literal("Set Area"), false)
                        .child(Components.button(Text.literal("Set Block 1 Position"), button -> { getBlockPos(); }))
                        .padding(Insets.of(10)) //
                        .surface(Surface.DARK_PANEL)
                        .verticalAlignment(VerticalAlignment.CENTER)
                        .horizontalAlignment(HorizontalAlignment.CENTER)
        );
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
            AreaAssistorClient.LOGGER.info("Block Pos: " + blockPos);
            return blockPos;
        }
        else
        {
            return null;
        }
    }
}
