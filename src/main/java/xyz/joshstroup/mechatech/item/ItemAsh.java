package xyz.joshstroup.mechatech.item;

import net.minecraft.item.Item;

import xyz.joshstroup.mechatech.MechaTechCreativeTab;

public class ItemAsh extends Item
{
    public ItemAsh(String unlocalizedName)
    {
        super();
        setRegistryName(unlocalizedName);
        setCreativeTab(MechaTechCreativeTab.INSTANCE);
        setUnlocalizedName(this.getRegistryName().toString().replace("mechatech:", ""));
        setMaxStackSize(64);
    }
}
