package xyz.joshstroup.mechatech.item;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import xyz.joshstroup.mechatech.info.ItemInfo;
import xyz.joshstroup.mechatech.info.ModInfo;

public class MechaTechItems
{
    private static ArrayList<Item> itemList = new ArrayList<Item>();

    public static Item itemPliers;
    public static Item itemAsh;

    public static ArrayList<Item> getItemList()
    {
        return itemList;
    }

    public static void init()
    {
        itemPliers = new ItemPliers(ItemInfo.ITEM_PLIERS_UNLOCALIZED_NAME);
        itemAsh = new ItemAsh(ItemInfo.ITEM_ASH_UNLOCALIZED_NAME);

        register(itemPliers);
        register(itemAsh);
    }

    public static void initRenderers()
    {
        for (Item item : itemList) {
            registerRenderer(item);
        }
    }

    private static void register(Item item)
    {
        itemList.add(item);

        GameRegistry.register(item);
    }

    @SideOnly(Side.CLIENT)
    private static void registerRenderer(Item item)
    {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item,
                                                                               0,
                                                                               new ModelResourceLocation(ModInfo.MODID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
    }
}
