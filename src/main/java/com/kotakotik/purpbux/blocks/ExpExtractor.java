package com.kotakotik.purpbux.blocks;

import com.kotakotik.purpbux.ModItems;
import com.kotakotik.purpbux.utils.PlayerUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
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
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;
import java.util.List;

public class ExpExtractor extends Block {
    private static final DirectionProperty FACING = BlockStateProperties.FACING;

    public ExpExtractor() {
        super(Block.Properties
                .create(Material.IRON)
                .hardnessAndResistance(3, 10)
                .harvestLevel(2)
                .harvestTool(ToolType.PICKAXE)
                .setRequiresTool()
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

            if (!worldIn.isRemote) {
                player.giveExperiencePoints(neededExp * -1);
            }
        }
        return ActionResultType.CONSUME;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> list, ITooltipFlag flagIn) {
        list.add(new TranslationTextComponent("message.purpbux_exp_extractor", neededExp));
    }
}
