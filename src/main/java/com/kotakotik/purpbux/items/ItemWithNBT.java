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
        CompoundNBT nbt = stack.getTag();
        addNBT(nbt);
        stack.setTag(nbt);
        super.onCreated(stack, worldIn, playerIn);
    }

    abstract void addNBT(CompoundNBT nbt);
}
