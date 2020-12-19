package com.kotakotik.purpbux.items;

import com.kotakotik.purpbux.ModItems;
import com.kotakotik.purpbux.Purpbux;
import com.kotakotik.purpbux.utils.PlayerUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public abstract class AbstractWallet extends ItemWithNBT {
    abstract int getCapacity();

    public abstract Item getPreviousWallet();

    @Nonnull
    public abstract Item getMaterial();

    public abstract String getWalletName();

    public abstract IItemProvider getProvider();

    public AbstractWallet() {
        super(
                new Item.Properties().group(Purpbux.TAB)
        );

    }

    @Override
    public void onCreated(ItemStack stack, World worldIn, PlayerEntity playerIn) {
        super.onCreated(stack, worldIn, playerIn);
    }

    @Override
    void addDefaultNBT(CompoundNBT nbt) {
    }

    @Override
    public int getItemStackLimit(ItemStack stack) {
        return 1;
    }

    public ItemStack setStoredBux(ItemStack itemStack, int bux) {
        CompoundNBT nbt = itemStack.serializeNBT();
        nbt.putInt("storedBux", bux);
        itemStack.setTag(nbt);
        return itemStack;
    }

    public int getStoredBux(ItemStack itemStack) {
        CompoundNBT nbt = itemStack.getTag();
        if (nbt != null) {
            return nbt.getInt("storedBux");
        } else {
            return 0;
        }
    }

    private TranslationTextComponent ttc(String key, Object... args) {
        return new TranslationTextComponent(key, args);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> list, ITooltipFlag flagIn) {
        list.add(ttc("message.purpbux_wallet_info", getCapacity()));
        list.add(ttc("message.purpbux_wallet", getStoredBux(stack)));
        list.add(ttc("message.purpbux_wallet_add"));
        list.add(ttc("message.purpbux_wallet_remove"));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        if (playerIn.isSneaking()) {
            int storedBux = getStoredBux(stack);
            if (storedBux <= 64) {
                if (storedBux < 1) {
                    return ActionResult.resultConsume(stack);
                } else {
                    PlayerUtils.giveOrDropItem(playerIn, ModItems.PURP_BUX.get(), storedBux);
                    setStoredBux(stack, 0);
                }
            } else {
                PlayerUtils.giveOrDropItem(playerIn, ModItems.PURP_BUX.get(), 64);
                setStoredBux(stack, storedBux - 64);
            }
        } else {
            int toAdd = 0;
            int currentBux = getStoredBux(stack);

            for (int i = 0; i < playerIn.inventory.getSizeInventory(); i++) {
                ItemStack stackInSlot = playerIn.inventory.getStackInSlot(i);

                if (stackInSlot.getItem().equals(ModItems.PURP_BUX.get())) {
                    if (stackInSlot.getCount() + (currentBux + toAdd) <= getCapacity()) {
                        playerIn.inventory.removeStackFromSlot(i);
                        toAdd += stackInSlot.getCount();
                    } else {
                        int stackCount = stackInSlot.getCount();
                        int t1 = getCapacity() - (currentBux + toAdd);
                        stackInSlot.setCount(stackCount - t1);
                        toAdd += t1;
                    }
                }
            }

            setStoredBux(stack, toAdd + currentBux);
        }
        return ActionResult.resultSuccess(stack);
    }
}
