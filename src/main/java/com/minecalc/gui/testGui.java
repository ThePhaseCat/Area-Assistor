package com.minecalc.gui;

import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.event.BlockPositionSource;
import net.minecraft.world.event.EntityPositionSource;

public class testGui extends LightweightGuiDescription {

    public testGui() {
        //actual panel
        WGridPanel root = new WGridPanel();
        setRootPanel(root);
        root.setSize(300, 200);

        //lables
        WLabel block1lab = new WLabel(Text.translatable("Block 1 + coords"));
        WLabel block2lab = new WLabel(Text.translatable("Block 2 + coords"));
        WLabel block3lab = new WLabel(Text.translatable("Block 3 + coords"));
        WLabel area = new WLabel(Text.translatable("Area: + areaValue "));

        //buttons
        WButton block1coords = new WButton(Text.translatable("Change Block 1 Coordinates"));
        WButton block2coords = new WButton(Text.translatable("Change Block 2 Coordinates"));
        WButton block3coords = new WButton(Text.translatable("Change Block 3 Coordinates"));

        //button logic
        block1coords.setOnClick(() -> {
            System.out.println("block 1 button pressed");

        });
        block2coords.setOnClick(() -> {
            System.out.println("block 2 button pressed");
        });
        block3coords.setOnClick(() -> {
            System.out.println("block 3 button pressed");
        });


        root.add(block1lab, 0, 0, 1, 1);
        root.add(block2lab, 0, 1, 1, 1);
        root.add(block3lab, 0, 2, 1, 1);
        root.add(area, 0, 3, 1, 1);

        root.add(block1coords, 1, 0, 1, 1);
        root.add(block2coords, 1, 1, 1, 1);
        root.add(block3coords, 1, 2, 1, 1);



    }

    public Block getBlock()
    {
        //get the block that the player is looking at
        MinecraftClient client = MinecraftClient.getInstance();
        HitResult hit = client.crosshairTarget;

        BlockHitResult blockHit = (BlockHitResult) hit;
        BlockPos blockPos = blockHit.getBlockPos();
        BlockState blockState = client.world.getBlockState(blockPos);
        Block block = blockState.getBlock();

        if(hit.getType() == HitResult.Type.BLOCK)
        {
            return block;
        }
        else
        {
            return null;
        }
    }
}
