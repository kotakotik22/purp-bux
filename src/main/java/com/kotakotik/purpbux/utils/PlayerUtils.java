package com.kotakotik.purpbux.utils;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;

public class PlayerUtils {
    public static Hand getHandWithItem(PlayerEntity player, Item item) {
        if (player.getHeldItemOffhand().getCount() > 0 && player.getHeldItemOffhand().getItem() == item) {
            return Hand.OFF_HAND;
        } else if (player.getHeldItemMainhand().getCount() > 0 && player.getHeldItemMainhand().getItem() == item) {
            return Hand.MAIN_HAND;
        } else {
            return null;
        }
    }

    public static Hand getHandWithItemWithTag(PlayerEntity player, ITag.INamedTag<Item> tag) {
        ResourceLocation tagrl = ItemTags.getCollection().getDirectIdFromTag(tag);
        if (player.getHeldItemOffhand().getCount() > 0 && player.getHeldItemOffhand().getItem().getTags().contains(tagrl)) {
            return Hand.OFF_HAND;
        } else if (player.getHeldItemMainhand().getCount() > 0 && player.getHeldItemMainhand().getItem().getTags().contains(tagrl)) {
            return Hand.MAIN_HAND;
        } else {
            return null;
        }
    }

    public static ItemStack getItemStackWithTagInMainOrOffHand(PlayerEntity player, ITag.INamedTag<Item> tag) {
        Hand hand = getHandWithItemWithTag(player, tag);
        if (hand == Hand.MAIN_HAND) {
            return player.getHeldItemMainhand();
        } else if (hand == Hand.OFF_HAND) {
            return player.getHeldItemOffhand();
        } else {
            return null;
        }
    }

    public static ItemStack getItemStackInMainOrOffHand(PlayerEntity player, Item item) {
        Hand hand = getHandWithItem(player, item);
        if (hand == Hand.MAIN_HAND) {
            return player.getHeldItemMainhand();
        } else if (hand == Hand.OFF_HAND) {
            return player.getHeldItemOffhand();
        } else {
            return null;
        }
    }

    public static boolean giveOrDropItem(PlayerEntity player, ItemStack item) {
        if (player.inventory.addItemStackToInventory(item)) {
            return true;
        } else {
            player.dropItem(item, false);
            return false;
        }
    }

    public static boolean giveOrDropItem(PlayerEntity player, Item item, Integer count) {
        return giveOrDropItem(player, new ItemStack(item, count));
    }

    public static boolean giveOrDropItem(PlayerEntity player, Item item) {
        return giveOrDropItem(player, item, 1);
    }


    /*
        Used to fill up a bucket or bottle
     */
    public static void fillBucket(PlayerEntity player, Hand hand, ItemStack bucket, ItemStack newBucket, SoundEvent soundEvent) {
        if (!player.world.isRemote) {
            bucket.shrink(1);
            if (bucket.isEmpty()) {
                player.setHeldItem(hand, newBucket);
            } else if (!player.inventory.addItemStackToInventory(newBucket)) {
                player.dropItem(newBucket, false);
            }
            player.world.playSound(null, getPlayerBlockPos(player), soundEvent, SoundCategory.BLOCKS, 1.0F, 1.0F);
        }
    }

    public static void fillBucket(PlayerEntity player, Hand hand, ItemStack bucket, ItemStack newBucket) {
        fillBucket(player, hand, bucket, newBucket, SoundEvents.ITEM_BUCKET_FILL);
    }

    public static boolean fillBucket(PlayerEntity player, ItemStack newBucket, SoundEvent soundEvent) {
        Hand hand = getHandWithItem(player, Items.BUCKET);
        ItemStack bucket = getItemStackInMainOrOffHand(player, Items.BUCKET);

        if (hand != null) {
            fillBucket(player, hand, bucket, newBucket);
            return true;
        } else {
            return false;
        }
    }

    public static boolean fillBucket(PlayerEntity player, ItemStack newBucket) {
        return fillBucket(player, newBucket, SoundEvents.ITEM_BUCKET_FILL);
    }

    public static boolean fillBucket(PlayerEntity player, Item newBucket) {
        return fillBucket(player, new ItemStack(newBucket));
    }

    public static BlockPos getPlayerBlockPos(PlayerEntity player) {
        return new BlockPos(player.getPosX(), player.getPosY(), player.getPosZ());
    }

    public static void fillBottle(PlayerEntity player, Hand hand, ItemStack bucket, ItemStack newBucket, SoundEvent soundEvent) {
//        ItemStack itemstack4 = PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.WATER);
        player.addStat(Stats.USE_CAULDRON);
        bucket.shrink(1);
        if (bucket.isEmpty()) {
            player.setHeldItem(hand, newBucket);
        } else if (!player.inventory.addItemStackToInventory(newBucket)) {
            player.dropItem(newBucket, false);
        } else if (player instanceof ServerPlayerEntity) {
            ((ServerPlayerEntity) player).sendContainerToPlayer(player.container);
        }

        player.world.playSound(null, getPlayerBlockPos(player), soundEvent, SoundCategory.BLOCKS, 1.0F, 1.0F);
    }

    public static void fillBottle(PlayerEntity player, Hand hand, ItemStack bucket, ItemStack newBucket) {
        fillBottle(player, hand, bucket, newBucket, SoundEvents.ITEM_BOTTLE_FILL);
    }
}
