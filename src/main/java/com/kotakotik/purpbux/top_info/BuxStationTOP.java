package com.kotakotik.purpbux.top_info;

import com.kotakotik.purpbux.blocks.BuxStation;
import com.kotakotik.purpbux.tiles.BuxStationTile;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoAccessor;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class BuxStationTOP extends BuxStation implements IProbeInfoAccessor {
    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, PlayerEntity playerEntity, World world, BlockState blockState, IProbeHitData iProbeHitData) {
        if (probeMode.equals(ProbeMode.DEBUG) || probeMode.equals(ProbeMode.EXTENDED)) {
            Block block = blockState.getBlock();
            if (block instanceof BuxStation) {
                BuxStation station = (BuxStation) block;
                BuxStationTile tile = station.getTileEntity();

                iProbeInfo.progress(tile.progress, tile.totalProgress);
            }
        }
    }
}
