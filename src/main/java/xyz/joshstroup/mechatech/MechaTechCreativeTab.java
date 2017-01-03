package xyz.joshstroup.mechatech;

import java.util.List;
import javax.annotation.ParametersAreNonnullByDefault;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import xyz.joshstroup.mechatech.info.ModInfo;
import xyz.joshstroup.mechatech.item.MechaTechItems;
import xyz.joshstroup.mechatech.block.MechaTechBlocks;

public class MechaTechCreativeTab extends CreativeTabs
{
    List list;

    public static MechaTechCreativeTab INSTANCE = new MechaTechCreativeTab();

    public MechaTechCreativeTab()
    {
        super(ModInfo.MODID);
    }

    @Override
    @MethodsReturnNonnullByDefault
    public Item getTabIconItem()
    {
        return MechaTechItems.itemPliers;
    }

    @Override
    @ParametersAreNonnullByDefault
    public void displayAllRelevantItems(List list)
    {
        this.list = list;

        for (Item item : MechaTechItems.getItemList())
        {
            item.getSubItems(item, this, (List<ItemStack>) list);
        }
        for(Block block : MechaTechBlocks.getBlockList())
        {
            Item item = Item.getItemFromBlock(block);
            block.getSubBlocks(item, this, (List<ItemStack>) list);
        }
    }
}
