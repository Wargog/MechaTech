package xyz.joshstroup.mechatech.tileentity;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import xyz.joshstroup.mechatech.block.BlockCombustionGenerator;
import xyz.joshstroup.mechatech.item.MechaTechItems;
import xyz.joshstroup.mechatech.render.gui.container.ContainerCombustionGenerator;

public class TileEntityCombustionGenerator extends TileEntity implements IInventory, ITickable
{
    private int burnTimeRemaining;
    private int totalBurnTime;

    public ItemStack[] generatorItemStacks;
    
    public TileEntityCombustionGenerator()
	{
		this.generatorItemStacks = new ItemStack[this.getSizeInventory()];
	}

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        final byte NBT_TYPE_COMPOUND = 10;       // See NBTBase.createNewByType() for a listing
		NBTTagList dataForAllSlots = compound.getTagList("Items", NBT_TYPE_COMPOUND);

		NBTTagList list = compound.getTagList("Items", 10);
	    for(int i = 0; i < list.tagCount(); ++i)
	    {
	        NBTTagCompound stackTag = list.getCompoundTagAt(i);
	        int slot = stackTag.getByte("Slot") & 255;
	        this.setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(stackTag));
	    }

        this.burnTimeRemaining = compound.getInteger("BurnTime");
        this.totalBurnTime = TileEntityFurnace.getItemBurnTime(this.getStackInSlot(ContainerCombustionGenerator.FUEL));
    }

    @Override
    @MethodsReturnNonnullByDefault
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
    	super.writeToNBT(compound);

        compound.setInteger("BurnTime", this.burnTimeRemaining);

        NBTTagList list = new NBTTagList();
        for(int i = 0; i < this.getSizeInventory(); ++i)
        {
            if(this.getStackInSlot(i) != null)
            {
                NBTTagCompound stackTag = new NBTTagCompound();
                stackTag.setByte("Slot", (byte) i);
                this.getStackInSlot(i).writeToNBT(stackTag);
                list.appendTag(stackTag);
            }
        }

        compound.setTag("Items", list);

        return compound;
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState)
    {
        return oldState.getBlock() != newState.getBlock();
    }

    @Override
    @Nullable
    public SPacketUpdateTileEntity getUpdatePacket()
    {
          NBTTagCompound updateTagDescribingTileEntityState = getUpdateTag();
          final int METADATA = 0;
          return new SPacketUpdateTileEntity(this.pos, METADATA, updateTagDescribingTileEntityState);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
    {
          NBTTagCompound updateTagDescribingTileEntityState = pkt.getNbtCompound();
          handleUpdateTag(updateTagDescribingTileEntityState);
    }

    /**
     * Creates a tag containing the TileEntity information, used by vanilla to transmit from server to client
     * Warning - although our getUpdatePacket() uses this method, vanilla also calls it directly, so don't remove it.
     */
    @Override
    public NBTTagCompound getUpdateTag()
    {
  		NBTTagCompound nbtTagCompound = new NBTTagCompound();
  		writeToNBT(nbtTagCompound);
        return nbtTagCompound;
    }

    /**
     * Populates this TileEntity with information from the tag, used by vanilla to transmit from server to client
     * Warning - although our onDataPacket() uses this method, vanilla also calls it directly, so don't remove it.
     */
    @Override
    public void handleUpdateTag(NBTTagCompound tag)
    {
      this.readFromNBT(tag);
    }

    public int getInventoryStackLimit()
    {
        return 64;
    }

    public boolean isBurning()
    {
        return this.burnTimeRemaining > 0;
    }

    public void update()
    {
        if(this.isBurning())
        {
            this.burnTimeRemaining--;

            // TODO: Implement a better solution for block lighting
            if(this.getWorld().getLightBrightness(this.getPos()) != 1.0F)
            {
                this.getWorld().getBlockState(this.getPos()).getBlock().setLightLevel(1.0F);
            }

            if(!this.getWorld().getBlockState(this.getPos()).getValue(BlockCombustionGenerator.POWERED) && this.getWorld().getBlockState(this.getPos()).getBlock() instanceof BlockCombustionGenerator)
            {
                this.getWorld().setBlockState(this.getPos(), this.getWorld().getBlockState(this.getPos()).withProperty(BlockCombustionGenerator.POWERED, true));
            }
        }


        // Remove fuel and convert it to ash unless the output slot is full
        if(!this.isBurning() && this.getStackInSlot(ContainerCombustionGenerator.FUEL) != null)
        {
            if(this.getStackInSlot(ContainerCombustionGenerator.OUTPUT) != null && this.getStackInSlot(ContainerCombustionGenerator.OUTPUT).stackSize == this.getInventoryStackLimit())
            {
                return;
            }

            this.decrStackSize(ContainerCombustionGenerator.FUEL, 1);
            this.incrStackSize(ContainerCombustionGenerator.OUTPUT, 1, MechaTechItems.itemAsh);
            // Reset burn time to new Item since we have converted the old one to ash
            this.burnTimeRemaining = TileEntityFurnace.getItemBurnTime(this.getStackInSlot(ContainerCombustionGenerator.FUEL));

            return;
        }

        if(!this.isBurning())
        {
            // Turn off light source if the block is no longer burning
            if(this.getWorld().getLightBrightness(this.getPos()) != 0.0F)
            {
                this.getWorld().getBlockState(this.getPos()).getBlock().setLightLevel(0.0F);
            }

            if(this.getWorld().getBlockState(this.getPos()).getValue(BlockCombustionGenerator.POWERED) && this.getWorld().getBlockState(this.getPos()).getBlock() instanceof BlockCombustionGenerator)
            {
                this.getWorld().setBlockState(this.getPos(), this.getWorld().getBlockState(this.getPos()).withProperty(BlockCombustionGenerator.POWERED, false));
            }
        }
    }

    public int getField(int id)
    {
        switch(id)
        {
            case 0:
                return this.burnTimeRemaining;
            case 1:
                return this.totalBurnTime;
            default:
                return 0;
        }
    }

    public void setField(int id, int value)
    {
        switch(id)
        {
            case 0:
                this.burnTimeRemaining = value;
            case 1:
                this.totalBurnTime = value;
        }
    }

    public int getFieldCount()
    {
        return 2;
    }

    public void clear()
    {
        for(int i = 0; i < this.getSizeInventory(); ++i)
        {
            this.setInventorySlotContents(i, null);
        }
    }
    
    @Override
    @MethodsReturnNonnullByDefault
    public String getName()
    {
        return "Combustion Generator";
    }

    @Override
    public boolean hasCustomName()
    {
        return false;
    }

    @Override
    @MethodsReturnNonnullByDefault
    public ITextComponent getDisplayName()
    {
        return new TextComponentString("Combustion Generator");
    }

    @Override
    public int getSizeInventory()
    {
        return 2;
    }

    @Override
    public ItemStack getStackInSlot(int slot)
    {
        if(slot < 0 || slot >= this.getSizeInventory())
        {
            return null;
        }

        return this.generatorItemStacks[slot];
    }

    @Override
    public ItemStack decrStackSize(int slot, int count)
    {
        if(this.getStackInSlot(slot) != null)
        {
            ItemStack itemstack;

            if(this.getStackInSlot(slot).stackSize <= count)
            {
                itemstack = this.getStackInSlot(slot);
                this.setInventorySlotContents(slot, null);
                this.markDirty();
                return itemstack;
            }
            else
            {
                itemstack = this.getStackInSlot(slot).splitStack(count);

                if(this.getStackInSlot(slot).stackSize <= 0)
                {
                    this.setInventorySlotContents(slot, null);
                }
                else
                {
                    //Just to show that changes happened
                    this.setInventorySlotContents(slot, this.getStackInSlot(slot));
                }

                this.markDirty();
                return itemstack;
            }
        }
        else
        {
            return null;
        }
    }

    public ItemStack incrStackSize(int slot, int count, Item intendedItem)
    {
        if(this.getStackInSlot(slot) == null)
        {
            ItemStack itemstack = new ItemStack(intendedItem);
            itemstack.stackSize = MathHelper.clamp_int(count, 1, this.getInventoryStackLimit());

            this.setInventorySlotContents(slot, itemstack);
            this.markDirty();

            return itemstack;
        }
        else
        {
            ItemStack itemstack = this.getStackInSlot(slot);
            itemstack.stackSize += MathHelper.clamp_int(count, 1, this.getInventoryStackLimit());

            this.setInventorySlotContents(slot, itemstack);
            this.markDirty();

            return itemstack;
        }
    }

    @Override
    public ItemStack removeStackFromSlot(int slot)
    {
        ItemStack stack = this.getStackInSlot(slot);
        this.setInventorySlotContents(slot, null);
        return stack;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack)
    {
        if(slot < 0 || slot >= this.getSizeInventory())
        {
            return;
        }

        if(stack != null && stack.stackSize > this.getInventoryStackLimit())
        {
            stack.stackSize = this.getInventoryStackLimit();
        }

        if(stack != null && stack.stackSize == 0)
        {
            stack = null;
        }

        this.generatorItemStacks[slot] = stack;
        this.markDirty();
    }
    
    @Override
    @ParametersAreNonnullByDefault
    public boolean isUseableByPlayer(EntityPlayer player)
    {
        return !this.isInvalid() && this.getDistanceSq(player.posX, player.posY, player.posZ) < 64;
    }

    @Override
    @ParametersAreNonnullByDefault
    public void openInventory(EntityPlayer player)
    {

    }

    @Override
    @ParametersAreNonnullByDefault
    public void closeInventory(EntityPlayer player)
    {

    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean isItemValidForSlot(int slot, ItemStack stack)
    {
    	if (slot == ContainerCombustionGenerator.OUTPUT)
        {
            return false;
        }
        else if (slot != 0)
        {
            return true;
        }
        else
        {
            ItemStack itemstack = this.generatorItemStacks[0];
            return TileEntityFurnace.isItemFuel(stack) || !SlotFurnaceFuel.isBucket(stack) && (itemstack == null || itemstack.getItem() != Items.BUCKET);
        }
    }
}
