package com.minecalc.items;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class test extends Item {

    public static final Logger LOGGER = LoggerFactory.getLogger("minecalc");

    //variables for block positions
    public static BlockPos block1;
    public static BlockPos block2;
    public static BlockPos block3;

    public static int counter = 0;


    public test() {
        super(new Item.Settings().group(ItemGroup.MISC));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
        playerEntity.playSound(SoundEvents.BLOCK_WOOL_BREAK, 1.0F, 1.0F);
        counter = 0;
        MinecraftClient.getInstance().player.sendChatMessage("Counter Reset", Text.of("Test"));
        return TypedActionResult.success(playerEntity.getStackInHand(hand));
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context)
    {
        //get the area of the 3 blocks
        if (counter == 0) {
            block1 = context.getBlockPos();
            counter++;
            MinecraftClient.getInstance().player.sendChatMessage("Block 1 set", Text.of("Test"));
        }
        else if (counter == 1) {
            if (!context.getBlockPos().equals(block1)) {
                block2 = context.getBlockPos();
                counter++;
                MinecraftClient.getInstance().player.sendChatMessage("Block 2 set", Text.of("Test"));
            }
            else {
                MinecraftClient.getInstance().player.sendChatMessage("Block 1 and Block 2 cannot be the same", Text.of("Test"));
            }
        }
        else if (counter == 2) {
            if (!context.getBlockPos().equals(block1) && !context.getBlockPos().equals(block2)) {
                block3 = context.getBlockPos();
                counter++;
                MinecraftClient.getInstance().player.sendChatMessage("Block 3 set", Text.of("Test"));
            }
            else {
                MinecraftClient.getInstance().player.sendChatMessage("Block 3 cannot be the same as Block 1 or Block 2", Text.of("Test"));
            }
        }
        else {
            MinecraftClient.getInstance().player.sendChatMessage(String.valueOf(calcArea()), Text.of("Test"));
        }

        return ActionResult.SUCCESS;
    }

    public int calcArea() {

        int length = block2.getX() - block1.getX();
        int width = block2.getZ() - block1.getZ();
        int height = block3.getY() - block2.getY();

        MinecraftClient.getInstance().player.sendChatMessage("Length: " + length, Text.of("Test"));
        MinecraftClient.getInstance().player.sendChatMessage("Width: " + width, Text.of("Test"));
        MinecraftClient.getInstance().player.sendChatMessage("Height: " + height, Text.of("Test"));


        int area = length * width * height;
        if (area < 0)
        {
            area = area * -1;
        }
        return area;
    }

}
