package xyz.joshstroup.mechatech.item;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

import xyz.joshstroup.mechatech.info.ItemInfo;

public class MechaTechItems
{
    public static Item itemPliers;

    public static void init()
    {
        itemPliers = new ItemPliers(ItemInfo.ITEM_PLIERS_UNLOCALIZED_NAME);

        register(itemPliers);
    }

    private static void register(Item item)
    {
        GameRegistry.register(item);
    }
}
