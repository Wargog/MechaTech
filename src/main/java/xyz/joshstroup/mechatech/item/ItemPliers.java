package xyz.joshstroup.mechatech.item;

import net.minecraft.item.Item;

import xyz.joshstroup.mechatech.MechaTechCreativeTab;

public class ItemPliers extends Item
{
    public ItemPliers(String unlocalizedName)
    {
        super();
        setRegistryName(unlocalizedName);
        setCreativeTab(MechaTechCreativeTab.INSTANCE);
        setUnlocalizedName(this.getRegistryName().toString().replace("mechatech:", ""));
        setMaxStackSize(1);
    }
}
