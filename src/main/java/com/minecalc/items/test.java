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
        return TypedActionResult.success(playerEntity.getStackInHand(hand));
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context)
    {
        //get the block position
        BlockPos pos = context.getBlockPos();
        LOGGER.info(String.valueOf(pos));
        //send a message in chat
        MinecraftClient.getInstance().player.sendChatMessage("Pos: " + pos, Text.of("Test"));
        if(counter == 0)
        {
            block1 = pos;
            counter++;
        }
        else if(counter == 1)
        {
            block2 = pos;
            counter++;
        }
        else if(counter == 2)
        {
            block3 = pos;
            counter = 0;

            //calculate the area with the 3 blocks
            int area = (block3.getX() - block1.getX()) * (block3.getZ() - block1.getZ() * (block3.getY() - block1.getY()));
            LOGGER.info(String.valueOf(area));
            //send a message in chat
            MinecraftClient.getInstance().player.sendChatMessage("Area: " + area, Text.of("Test"));
        }

        return ActionResult.SUCCESS;
    }

}
