package com.kotakotik.purpbux.blocks;

import com.kotakotik.purpbux.ModItems;
import com.kotakotik.purpbux.utils.PlayerUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class ExpExtractor extends Block {
    private static final DirectionProperty FACING = BlockStateProperties.FACING;

    public ExpExtractor() {
        super(Block.Properties
                .create(Material.IRON)
                .hardnessAndResistance(3, 10)
                .harvestLevel(1)
                .sound(SoundType.METAL));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);

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

    private final static int neededExp = 50;

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        Hand hand = PlayerUtils.getHandWithItem(player, Items.GLASS_BOTTLE);

        if (hand != null && player.experienceTotal >= neededExp) {
            ItemStack bottle = player.getHeldItem(hand);
            PlayerUtils.fillBottle(player, hand, bottle, new ItemStack(ModItems.EXP_BOTTLE.get()));

            player.giveExperiencePoints(neededExp * -1);
        }
        return ActionResultType.CONSUME;
    }
}