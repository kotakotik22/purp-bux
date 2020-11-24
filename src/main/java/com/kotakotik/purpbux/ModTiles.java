package com.kotakotik.purpbux;

import com.kotakotik.purpbux.tiles.BuxStationTile;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;

public class ModTiles {
    public static final RegistryObject<TileEntityType<?>> BUX_STATION_TILE = Registration.TILES.register("bux_station", () -> TileEntityType.Builder.create(BuxStationTile::new, ModBlocks.BUX_STATION.get()).build(null));

    static void register() {

    }
}
