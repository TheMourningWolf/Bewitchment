package com.bewitchment.common.ritual;

import com.bewitchment.common.item.ModItems;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Optional;

public class RitualConjurationWither extends RitualImpl {

	public RitualConjurationWither(ResourceLocation registryName, NonNullList<Ingredient> input, NonNullList<ItemStack> output, int timeInTicks, int circles, int altarStartingPower, int powerPerTick) {
		super(registryName, input, output, timeInTicks, circles, altarStartingPower, powerPerTick);
	}

	@Override
	public void onFinish(EntityPlayer player, TileEntity tile, World world, BlockPos pos, NBTTagCompound data, BlockPos effectivePosition, int covenSize) {
		if (!world.isRemote) {
			EntityWither entitywither = new EntityWither(world);
			BlockPos blockpos1 = pos.add(0, 0, 0);
			entitywither.setLocationAndAngles(blockpos1.getX() + 0.5D, blockpos1.getY() + 0.55D, blockpos1.getZ() + 0.5D, 0F, 0F);
			for (EntityPlayerMP entityplayermp : world.getEntitiesWithinAABB(EntityPlayerMP.class, entitywither.getEntityBoundingBox().grow(50.0D))) {
				CriteriaTriggers.SUMMONED_ENTITY.trigger(entityplayermp, entitywither);
			}
			world.spawnEntity(entitywither);
			entitywither.ignite();
		}
	}

	@Override
	public NonNullList<ItemStack> getOutput(NonNullList<ItemStack> input, NBTTagCompound data) {
		NonNullList<ItemStack> oldOutput = super.getOutput(input, data);
		Optional<ItemStack> oldAthame = input.parallelStream().filter(is -> is.getItem() == ModItems.athame).findFirst();
		if (oldAthame.isPresent()) {
			oldOutput.parallelStream().filter(is -> is.getItem() == ModItems.athame).findFirst().ifPresent(is -> is.setItemDamage(is.getItemDamage() + 50));
		}
		return oldOutput;
	}

}
