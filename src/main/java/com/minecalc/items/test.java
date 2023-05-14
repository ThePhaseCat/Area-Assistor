package com.minecalc.items;

import com.minecalc.gui.testScreen;
import net.minecraft.block.Blocks;
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

import java.util.ArrayList;

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
        //get the name of the block
        String blockName = context.getWorld().getBlockState(context.getBlockPos()).getBlock().getName().getString();
        MinecraftClient.getInstance().player.sendChatMessage(blockName, Text.of("Test"));

        //get the area of the 3 blocks
        if (counter == 0) {
            block1 = context.getBlockPos();
            counter++;
        }
        else if (counter == 1) {
            if (!context.getBlockPos().equals(block1)) {
                block2 = context.getBlockPos();
                counter++;
            }
        }
        else if (counter == 2) {
            if (!context.getBlockPos().equals(block1) && !context.getBlockPos().equals(block2)) {
                block3 = context.getBlockPos();
                counter++;
            }
        }
        else {
            MinecraftClient.getInstance().player.sendChatMessage(String.valueOf(calcArea()), Text.of("Test"));
            getBlocks();
        }

        return ActionResult.SUCCESS;
    }

    public int calcArea() {

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

    //get all the blocks in the area selected
    public void getBlocks() {
        String test = MinecraftClient.getInstance().world.getBlockState(new BlockPos(block1.getX(), block1.getY(), block1.getZ())).getBlock().getName().getString();
        LOGGER.info(test);

        ArrayList<String> blockNames = new ArrayList<String>();
        int startx = block1.getX();
        int starty = block1.getY();
        int startz = block1.getZ();

        int endx = block2.getX();
        int endy = block2.getY();
        int endz = block2.getZ();

        for (int x = startx; x <= endx; x++) {
            for (int y = starty; y <= endy; y++) {
                for (int z = startz; z <= endz; z++) {
                    String blockName = MinecraftClient.getInstance().world.getBlockState(new BlockPos(x, y, z)).getBlock().getName().getString();
                    blockNames.add(blockName);
                }
            }
        }

        //print everything to logger
        for (int i = 0; i < blockNames.size(); i++) {
            LOGGER.info(blockNames.get(i));
        }
        //maybe put a ui here to show all the blocks?
    }
}
