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

    public static BlockPos block1;
    public static BlockPos block2;
    public static BlockPos block3;

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
        WButton block1coords = new WButton(Text.translatable("Change Block 1 Coords"));
        WButton block2coords = new WButton(Text.translatable("Change Block 2 Coords"));
        WButton block3coords = new WButton(Text.translatable("Change Block 3 Coords"));

        //button logic
        block1coords.setOnClick(() -> {
            System.out.println("block 1 button pressed");
            block1 = getBlockPos();
            System.out.println(block1);
        });
        block2coords.setOnClick(() -> {
            System.out.println("block 2 button pressed");
            block2 = getBlockPos();
            System.out.println(block2);
        });
        block3coords.setOnClick(() -> {
            System.out.println("block 3 button pressed");
            block3 = getBlockPos();
            System.out.println(block3);
        });


        root.add(block1lab, 1, 1, 1, 1);
        root.add(block2lab, 1, 3, 1, 1);
        root.add(block3lab, 1, 5, 1, 1);
        root.add(area, 1, 7, 1, 1);

        root.add(block1coords, 7, 1, 10, 1);
        root.add(block2coords, 7, 3, 10, 1);
        root.add(block3coords, 7, 5, 10, 1);



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
}
