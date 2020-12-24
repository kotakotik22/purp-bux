package com.kotakotik.purpbux.tiles;

import com.kotakotik.purpbux.ClientStorage;
import com.kotakotik.purpbux.ModItems;
import com.kotakotik.purpbux.ModTiles;
import net.darkhax.bookshelf.block.tileentity.TileEntityBasicTickable;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Iterator;

public class BuxStationTile extends TileEntityBasicTickable {
    public int progress = 0;
    public int totalProgress = totalProgressGlobal;
    public int stationVersion = 1;
    public static final int totalProgressGlobal = 20 * 5;

    public int getProgress() {
        return progress;
    }

    public int getTotalProgress() {
        return totalProgress;
    }

    public final ItemStackHandler itemInputHandler = createHandler(Items.PAPER, ModItems.EXP_BOTTLE.get());
    /*
         0: paper
         1: exp bottle
     */
//    public final ItemStackHandler itemExpHandler = createHandler(ModItems.EXP_BOTTLE.get());
    public final ItemStackHandler itemBuxHandler = createHandler(false, ModItems.PURP_BUX.get());

    public final LazyOptional<IItemHandler> inputHandler = LazyOptional.of(() -> itemInputHandler);
    public final LazyOptional<IItemHandler> buxHandler = LazyOptional.of(() -> itemBuxHandler);

    public BuxStationTile() {
        super(ModTiles.BUX_STATION_TILE.get());
    }

    void setWorking(boolean working) {
        assert world != null;
        BlockState blockState = world.getBlockState(pos);
        world.setBlockState(pos, blockState.with(BlockStateProperties.POWERED, working),
                Constants.BlockFlags.NOTIFY_NEIGHBORS + Constants.BlockFlags.BLOCK_UPDATE);
    }

    public int getProgressPercentage() {
        if (progress == 0 || totalProgress == 0) {
            return 0;
        }
        return (int) ((float) progress / totalProgress * 100);
    }

    @Override
    public void onTileTick() {
        assert world != null;
        if (world.isRemote) return;

        ItemStack paper = itemInputHandler.getStackInSlot(0);
        ItemStack exp_bottle = itemInputHandler.getStackInSlot(1);
        ItemStack out_bux = itemBuxHandler.getStackInSlot(0);
        if (!paper.isEmpty() && !exp_bottle.isEmpty() && out_bux.getCount() < 64) {
            progress++;
            if (progress >= totalProgress) {
                progress = 0;
                ItemStack paper_copy = paper.copy();
                ItemStack exp_bottle_copy = exp_bottle.copy();
                paper_copy.setCount(paper.getCount() - 1);
                exp_bottle_copy.setCount(exp_bottle.getCount() - 1);
                itemInputHandler.setStackInSlot(0, paper_copy);
                itemInputHandler.setStackInSlot(1, exp_bottle_copy);
                ItemStack out_bux_copy = out_bux.copy();
                if (out_bux_copy.isEmpty()) {
                    out_bux_copy = new ItemStack(ModItems.PURP_BUX.get());
                } else {
                    out_bux_copy.setCount(out_bux.getCount() + 1);
                }
                itemBuxHandler.setStackInSlot(0, out_bux_copy);
            }
            setWorking(true);
            markDirty();
            sync(false);
        }  else {
            setWorking(false);

            if(progress != 0) {
                markDirty();
                progress = 0;
                sync(false);
            }
        }
    }

    @Override
    public void remove() {
        super.remove();
        buxHandler.invalidate();
        inputHandler.invalidate();
    }

    private ItemStackHandler createHandler(boolean allowInput, Item... items) {
        return new ItemStackHandler(items.length) {
            private boolean checkIfOk(int slot, ItemStack stack) {
//                return stack.getItem().equals(item);
//                .filter((item) -> {
//                    return item.equals(stack.getItem());
//                });
                int i = 0;
                for (Iterator<Item> it = Arrays.stream(items).iterator(); it.hasNext(); ) {
                    Item item = it.next();
                    if (slot == i && stack.getItem() == item) {
                        return true;
                    }
                    i++;
                }
                return false;
            }

            @Override
            protected void onContentsChanged(int slot) {
                markDirty();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return checkIfOk(slot, stack);
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                if (!checkIfOk(slot, stack) || !allowInput) {
                    return stack;
                }
                return super.insertItem(slot, stack, simulate);
            }
        };
    }

    private ItemStackHandler createHandler(Item... items) {
        return createHandler(true, items);
    }

    public final ItemStackHandler[] handlers = new ItemStackHandler[]{
            itemBuxHandler,
            itemInputHandler,
    };

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (side == Direction.DOWN) {
                return buxHandler.cast();
            }
            return inputHandler.cast();

        }
        return super.getCapability(cap, side);
    }

//    @Override
//    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
//        BlockState blockState = world.getBlockState(pos);
//        read(blockState, pkt.getNbtCompound());
//        if (ClientStorage.BuxStationCurrentPos != null) {
//            if (ClientStorage.BuxStationCurrentPos.equals(pos)) {
//                CompoundNBT nbt = pkt.getNbtCompound();
//                ClientStorage.BuxStationProgress = nbt.getInt("progress");
//                ClientStorage.BuxStationTotalProgress = nbt.getInt("totalProgress");
//            }
//        }
//    }

    @Override
    public void serialize(CompoundNBT tag) {
        tag.put("inputInv", itemInputHandler.serializeNBT());
        tag.put("buxInv", itemBuxHandler.serializeNBT());

        tag.putInt("progress", progress);
        tag.putInt("totalProgress", totalProgress);

        tag.putInt("stationVersion", stationVersion);
    }

    @Override
    public void deserialize(CompoundNBT tag) {
        if (tag.getInt("stationVersion") < 1) {
            serialize(tag);
        }
        itemInputHandler.deserializeNBT(tag.getCompound("inputInv"));
        itemBuxHandler.deserializeNBT(tag.getCompound("buxInv"));

        totalProgress = tag.getInt("totalProgress");
        progress = tag.getInt("progress");

        if (tag.contains("stationVersion")) {
            stationVersion = tag.getInt("stationVersion");
        }
    }

    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT nbtTagCompound = new CompoundNBT();
        write(nbtTagCompound);
        int tileEntityType = 22;
        return new SUpdateTileEntityPacket(this.pos, tileEntityType, nbtTagCompound);
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        BlockState blockState = world.getBlockState(pos);
        read(blockState, pkt.getNbtCompound());
        if (ClientStorage.BuxStationCurrentPos != null) {
            if (ClientStorage.BuxStationCurrentPos.equals(pos)) {
                CompoundNBT nbt = pkt.getNbtCompound();
                ClientStorage.BuxStationProgress = nbt.getInt("progress");
                ClientStorage.BuxStationTotalProgress = nbt.getInt("totalProgress");
            }
        }
    }
}
