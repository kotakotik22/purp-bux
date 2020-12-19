package com.kotakotik.purpbux.tiles;

import com.kotakotik.purpbux.ClientStorage;
import com.kotakotik.purpbux.ModItems;
import com.kotakotik.purpbux.ModTiles;
import net.darkhax.bookshelf.block.tileentity.TileEntityBasicTickable;
import net.minecraft.block.BlockState;
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

public class BuxStationTile extends TileEntityBasicTickable {
    public int progress = 0;
    public int totalProgress = 20 * 5;

    public int getProgress() {
        return progress;
    }

    public int getTotalProgress() {
        return totalProgress;
    }

    public final ItemStackHandler itemHandler = createHandler();

    public final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);

    public BuxStationTile() {
        super(ModTiles.BUX_STATION_TILE.get());
    }

    void setWorking(boolean working) {
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

        ItemStack paper = itemHandler.getStackInSlot(0);
        ItemStack exp_bottle = itemHandler.getStackInSlot(1);
        ItemStack out_bux = itemHandler.getStackInSlot(2);
        if (!paper.isEmpty() && !exp_bottle.isEmpty() && out_bux.getCount() < 64) {
            progress++;
            if (progress >= totalProgress) {
                progress = 0;
                ItemStack paper_copy = paper.copy();
                ItemStack exp_bottle_copy = exp_bottle.copy();
                paper_copy.setCount(paper.getCount() - 1);
                exp_bottle_copy.setCount(exp_bottle.getCount() - 1);
                itemHandler.setStackInSlot(0, paper_copy);
                itemHandler.setStackInSlot(1, exp_bottle_copy);
                ItemStack out_bux_copy = out_bux.copy();
                if(out_bux_copy.isEmpty()) {
                    out_bux_copy = new ItemStack(ModItems.PURP_BUX.get());
                } else {
                    out_bux_copy.setCount(out_bux.getCount() + 1);
                }
                itemHandler.setStackInSlot(2, out_bux_copy);
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
        handler.invalidate();
    }

    private ItemStackHandler createHandler() {
        return new ItemStackHandler(3) {
            private boolean checkIfOk(int slot, ItemStack stack) {
                if(slot == 0) {
                    return stack.getItem() == Items.PAPER;
                } else if(slot == 1) {
                    return stack.getItem() == ModItems.EXP_BOTTLE.get();
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
                if(!checkIfOk(slot, stack)) {
                    return stack;
                }
                return super.insertItem(slot, stack, simulate);
            }
        };
    }


    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && side != Direction.DOWN /* Completely disables hopper output, TODO: enable station hopper output and make it only extract bux */) {
            return handler.cast();
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
        tag.put("inv", itemHandler.serializeNBT());

        tag.putInt("progress", progress);
        tag.putInt("totalProgress", totalProgress);
    }

    @Override
    public void deserialize(CompoundNBT tag) {
        itemHandler.deserializeNBT(tag.getCompound("inv"));
        totalProgress = tag.getInt("totalProgress");
        progress = tag.getInt("progress");
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
