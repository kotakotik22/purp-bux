package com.kotakotik.purpbux;

import com.kotakotik.purpbux.containers.BuxStationContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;

public class ModContainers {
    public static final RegistryObject<ContainerType<BuxStationContainer>> BUX_STATION_CONTAINER = Registration.CONTAINERS.register("bux_station", () -> IForgeContainerType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        World world = inv.player.getEntityWorld();
        return new BuxStationContainer(windowId, world, pos, inv, inv.player);
    }));

    static void register() {}
}
