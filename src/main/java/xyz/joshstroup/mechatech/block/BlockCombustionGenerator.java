package xyz.joshstroup.mechatech.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import xyz.joshstroup.mechatech.MechaTechCreativeTab;

public class BlockCombustionGenerator extends Block
{
    public BlockCombustionGenerator(Material material, String unlocalizedName)
    {
        super(material);
        setCreativeTab(MechaTechCreativeTab.INSTANCE);
        setRegistryName(unlocalizedName);
        setUnlocalizedName(this.getRegistryName().toString().replace("mechatech:", ""));
    }
}
