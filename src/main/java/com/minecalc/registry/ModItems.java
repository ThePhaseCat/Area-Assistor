package com.minecalc.registry;

import com.minecalc.items.test;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;

public class ModItems {

    //Items
    public static final test testItem = new test();

    public static void RegisterItems()
    {
        Registry.register(Registry.ITEM, "minecalc:test", testItem);
    }
}
