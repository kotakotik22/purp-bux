package com.kotakotik.purpbux.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;

public abstract class ItemWithNBT extends Item {
    public ItemWithNBT(Properties properties) {
        super(properties);
    }

    @Override
    public void onCreated(ItemStack stack, World worldIn, PlayerEntity playerIn) {
        super.onCreated(stack, worldIn, playerIn);
        CompoundNBT nbt = stack.getTag();
        addDefaultNBT(nbt);
        stack.setTag(nbt);
    }

    abstract void addDefaultNBT(CompoundNBT nbt);
}
