package com.minecalc.registry;

import com.minecalc.items.test;
import com.minecalc.items.test2;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;

public class ModItems {

    //Items
    public static final test testItem = new test();
    public static final test2 testItem2 = new test2();

    public static void RegisterItems()
    {
        Registry.register(Registry.ITEM, "minecalc:test", testItem);
        Registry.register(Registry.ITEM, "minecalc:test2", testItem2);
    }
}
