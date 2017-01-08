package xyz.joshstroup.mechatech.block;

import javax.annotation.ParametersAreNonnullByDefault;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import xyz.joshstroup.mechatech.MechaTech;
import xyz.joshstroup.mechatech.MechaTechCreativeTab;
import xyz.joshstroup.mechatech.tileentity.TileEntityCombustionGenerator;

public class BlockCombustionGenerator extends Block implements ITileEntityProvider
{
    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

    public BlockCombustionGenerator(Material material, String unlocalizedName)
    {
        super(material);
        setCreativeTab(MechaTechCreativeTab.INSTANCE);
        setRegistryName(unlocalizedName);
        setUnlocalizedName(this.getRegistryName().toString().replace("mechatech:", ""));
    }

    @Override
    @MethodsReturnNonnullByDefault
    @ParametersAreNonnullByDefault
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, ItemStack itemStack)
    {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    @Override
    @MethodsReturnNonnullByDefault
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(FACING).getHorizontalIndex();
    }

    @Override
    @MethodsReturnNonnullByDefault
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos blockPos, IBlockState state,
                                    EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side,
                                    float hitX, float hitY, float hitZ)
    {
        if(!world.isRemote)
        {
            player.openGui(MechaTech.INSTANCE, 0, world, blockPos.getX(), blockPos.getY(), blockPos.getZ());
        }

        return true;
    }

    @Override
    @MethodsReturnNonnullByDefault
    @ParametersAreNonnullByDefault
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileEntityCombustionGenerator();
    }
}
