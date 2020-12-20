package com.kotakotik.purpbux.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Items;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.common.util.Constants;

public class SveltebuxGravestone extends Block {
    private static final DirectionProperty FACING = BlockStateProperties.FACING;
    public static final BooleanProperty HAS_FLOWER = BooleanProperty.create("has_flower");

    public static final VoxelShape SHAPE_N = VoxelShapes.combineAndSimplify(Block.makeCuboidShape(2, 0, 1, 14, 1, 15), Block.makeCuboidShape(2, 1, 12, 14, 15, 15), IBooleanFunction.OR);
    public static final VoxelShape SHAPE_E = VoxelShapes.combineAndSimplify(Block.makeCuboidShape(1, 0, 2, 15, 1, 14), Block.makeCuboidShape(1, 1, 2, 4, 15, 14), IBooleanFunction.OR);
    public static final VoxelShape SHAPE_S = VoxelShapes.combineAndSimplify(Block.makeCuboidShape(2, 0, 1, 14, 1, 15), Block.makeCuboidShape(2, 1, 1, 14, 15, 4), IBooleanFunction.OR);
    public static final VoxelShape SHAPE_W = VoxelShapes.combineAndSimplify(Block.makeCuboidShape(1, 0, 2, 15, 1, 14), Block.makeCuboidShape(12, 1, 2, 15, 15, 14), IBooleanFunction.OR);

    public SveltebuxGravestone() {
        super(
                Block.Properties.create(Material.ROCK, MaterialColor.STONE).setRequiresTool().hardnessAndResistance(2.0F, 6.0F).harvestLevel(0).harvestTool(ToolType.PICKAXE)
        );
        this.setDefaultState(
                this.getStateContainer().getBaseState()
                        .with(HAS_FLOWER, false)
        );
    }

    void setHasFlower(boolean hasFlower, BlockState state, World world, BlockPos pos) {
        world.setBlockState(pos, state.with(HAS_FLOWER, hasFlower),
                Constants.BlockFlags.NOTIFY_NEIGHBORS + Constants.BlockFlags.BLOCK_UPDATE);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch (state.get(FACING)) {
            case SOUTH:
                return SHAPE_S;
            case EAST:
                return SHAPE_E;
            case WEST:
                return SHAPE_W;
            default:
                return SHAPE_N;
        }
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(
                FACING,
                HAS_FLOWER
        );
        super.fillStateContainer(builder);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.toRotation(state.get(FACING)));
    }

    @Override
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        if (entityIn instanceof ItemEntity) {
            if (((ItemEntity) entityIn).getItem().getItem().equals(Items.POPPY)) {
                boolean hasFlower = state.get(HAS_FLOWER);
                if (!hasFlower) {
                    setHasFlower(true, state, worldIn, pos);
                    entityIn.remove();
                }
            }
        }
    }
}
