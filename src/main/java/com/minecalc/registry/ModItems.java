package com.minecalc.registry;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;

public class ModItems {

    //Items
    public static final Item test = new Item(new Item.Settings().group(ItemGroup.MISC));

    public static void RegisterItems()
    {
        Registry.register(Registry.ITEM, "minecalc:test", test);
    }
}
