package com.kotakotik.purpbux.items;

import com.kotakotik.purpbux.utils.PlayerUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.util.*;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class ExpBottle extends Item {
    public ExpBottle(Properties properties) {
        super(properties);
    }

    @Nonnull
    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
//        System.out.println(worldIn.isRemote);

        if(!worldIn.isRemote) {
            ((PlayerEntity) entityLiving).giveExperiencePoints(20);
        }

        PlayerUtils.giveOrDropItem((PlayerEntity) entityLiving, Items.GLASS_BOTTLE);

        return super.onItemUseFinish(stack, worldIn, entityLiving);
    }

    public int getUseDuration(ItemStack stack) {
        return 40;
    }

    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }

    //
//    public SoundEvent getDrinkSound() {
//        return SoundEvents.DRINK;
//    }
//
    public SoundEvent getEatSound() {
        return SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP;
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        return DrinkHelper.startDrinking(worldIn, playerIn, handIn);
    }
}
