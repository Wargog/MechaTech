package xyz.joshstroup.mechatech.util.inventory;

import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.SlotFurnaceOutput;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.math.MathHelper;

public class SlotOutput extends SlotFurnaceOutput {

	public SlotOutput(EntityPlayer player, IInventory inventoryIn,
			int slotIndex, int xPosition, int yPosition) {
		super(player, inventoryIn, slotIndex, xPosition, yPosition);
	}
	
	@Override
	protected void onCrafting(ItemStack stack) {
		
	}
	
}
