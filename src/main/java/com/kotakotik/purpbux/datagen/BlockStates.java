package com.kotakotik.purpbux.datagen;

import com.kotakotik.purpbux.ModBlocks;
import com.kotakotik.purpbux.Purpbux;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.data.DataGenerator;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.Function;

public class BlockStates extends BlockStateProvider {
    public BlockStates(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, Purpbux.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        ResourceLocation buxStationSide = new ResourceLocation(Purpbux.MODID, "block/bux_station");

        BlockModelBuilder modelBuxStation = models().cube("bux_station", buxStationSide, new ResourceLocation(Purpbux.MODID, "block/bux_station_top"), new ResourceLocation(Purpbux.MODID, "block/bux_station_front"), buxStationSide, buxStationSide, buxStationSide)
                .texture("particle", buxStationSide);
        BlockModelBuilder modelBuxStationPowered = models().cube("bux_station_powered", buxStationSide, new ResourceLocation(Purpbux.MODID, "block/bux_station_top"), new ResourceLocation(Purpbux.MODID, "block/bux_station_powered"), buxStationSide, buxStationSide, buxStationSide)
                .texture("particle", buxStationSide);
        orientedBlock(ModBlocks.BUX_STATION.get(), state -> {
            if (state.get(BlockStateProperties.POWERED)) {
                return modelBuxStationPowered;
            } else {
                return modelBuxStation;
            }
        });

        ResourceLocation expGenerator = new ResourceLocation(Purpbux.MODID, "block/exp_extractor");

        orientedBlock(ModBlocks.EXP_EXTRACTOR.get(), state -> models().cube("exp_extractor",
                new ResourceLocation(Purpbux.MODID, "block/exp_extractor_bottom"),
                new ResourceLocation(Purpbux.MODID, "block/exp_extractor_top"),
                new ResourceLocation(Purpbux.MODID, "block/exp_extractor_front"),
                expGenerator,
                expGenerator,
                expGenerator)
                .texture("particle", expGenerator));
    }

    private void orientedBlock(Block block, Function<BlockState, ModelFile> modelFunc) {
        getVariantBuilder(block)
                .forAllStates(state -> {
                    Direction dir = state.get(BlockStateProperties.FACING);
                    return ConfiguredModel.builder()
                            .modelFile(modelFunc.apply(state))
                            .rotationX(dir.getAxis() == Direction.Axis.Y ?  dir.getAxisDirection().getOffset() * -90 : 0)
                            .rotationY(dir.getAxis() != Direction.Axis.Y ? ((dir.getHorizontalIndex() + 2) % 4) * 90 : 0)
                            .build();
                });
    }
}
