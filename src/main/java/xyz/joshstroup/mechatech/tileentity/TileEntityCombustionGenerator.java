package xyz.joshstroup.mechatech.tileentity;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ITickable;

import xyz.joshstroup.mechatech.render.gui.container.ContainerCombustionGenerator;
import xyz.joshstroup.mechatech.util.inventory.InventoryTileEntity;

public class TileEntityCombustionGenerator extends TileEntity implements ITickable
{
    private int burnTimeRemaining;
    private int totalBurnTime;

    public InventoryTileEntity generatorItemStacks = new InventoryTileEntity(this, 2);

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        // Load slot contents from NBT
        NBTTagList nbtTagList = compound.getTagList("Items", 10);
        this.generatorItemStacks = new InventoryTileEntity(this, 2);

        for(int i = 0; i < nbtTagList.tagCount(); ++i)
        {
            NBTTagCompound nbtTagCompound = nbtTagList.getCompoundTagAt(i);
            int j = nbtTagCompound.getByte("Slot");

            if(j >= 0 && j < this.generatorItemStacks.getSizeInventory())
            {
                this.generatorItemStacks.setInventorySlotContents(j, ItemStack.loadItemStackFromNBT(nbtTagCompound));
            }
        }

        this.burnTimeRemaining = compound.getInteger("BurnTime");
        this.totalBurnTime = TileEntityFurnace.getItemBurnTime(this.generatorItemStacks.getStackInSlot(ContainerCombustionGenerator.FUEL));
    }

    @Override
    @MethodsReturnNonnullByDefault
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setInteger("BurnTime", this.burnTimeRemaining);

        NBTTagList nbtTagList = new NBTTagList();

        for(int i = 0; i < this.generatorItemStacks.getSizeInventory(); ++i)
        {
            if(this.generatorItemStacks.getStackInSlot(i) != null)
            {
                NBTTagCompound nbtTagCompound = new NBTTagCompound();
                nbtTagCompound.setByte("Slot", (byte) i);
                this.generatorItemStacks.getStackInSlot(i).writeToNBT(nbtTagCompound);
                nbtTagList.appendTag(nbtTagCompound);
            }
        }

        compound.setTag("Items", nbtTagList);

        return compound;
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
    {
        NBTTagCompound compound = new NBTTagCompound();
        this.readFromNBT(compound);
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket()
    {
        NBTTagCompound compound = new NBTTagCompound();
        this.writeToNBT(compound);
        return new SPacketUpdateTileEntity(this.getPos(), this.getBlockMetadata(), compound);
    }

    @Override
    @MethodsReturnNonnullByDefault
    public NBTTagCompound getUpdateTag()
    {
        return writeToNBT(new NBTTagCompound());
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
            --this.burnTimeRemaining;
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
        for(int i = 0; i < this.generatorItemStacks.getSizeInventory(); ++i)
        {
            this.generatorItemStacks.setInventorySlotContents(i, null);
        }
    }
}
