package com.cjburkey.cjsreactors.block;

import javax.annotation.Nonnull;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Created by CJ Burkey on 2019/04/14
 */
public final class BlockReactorController extends BlockReactorCasingBlock {

    private static final PropertyDirection FACING = BlockHorizontal.FACING;

    BlockReactorController() {
        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        setDefaultFacing(worldIn, pos, state);
    }

    @SuppressWarnings("deprecation")
    @Override
    @Nonnull
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    private void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state) {
        if (!worldIn.isRemote) {
            IBlockState atNorth = worldIn.getBlockState(pos.north());
            IBlockState atSouth = worldIn.getBlockState(pos.south());
            IBlockState atWest = worldIn.getBlockState(pos.west());
            IBlockState atEast = worldIn.getBlockState(pos.east());
            EnumFacing dir = state.getValue(FACING);

            if (dir == EnumFacing.NORTH && atNorth.isFullBlock() && !atSouth.isFullBlock())
                dir = EnumFacing.SOUTH;
            else if (dir == EnumFacing.SOUTH && atSouth.isFullBlock() && !atNorth.isFullBlock())
                dir = EnumFacing.NORTH;
            else if (dir == EnumFacing.WEST && atWest.isFullBlock() && !atEast.isFullBlock())
                dir = EnumFacing.EAST;
            else if (dir == EnumFacing.EAST && atEast.isFullBlock() && !atWest.isFullBlock())
                dir = EnumFacing.WEST;

            worldIn.setBlockState(pos, state.withProperty(FACING, dir));
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    @Nonnull
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing dir = EnumFacing.getFront(meta);
        if (dir.getAxis() == EnumFacing.Axis.Y) {
            dir = EnumFacing.NORTH;
        }
        return getDefaultState().withProperty(FACING, dir);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getIndex();
    }

    @SuppressWarnings("deprecation")
    @Override
    @Nonnull
    public IBlockState withRotation(IBlockState state, Rotation rot) {
        return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
    }

    @SuppressWarnings("deprecation")
    @Override
    @Nonnull
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
        return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
    }

    @Override
    @Nonnull
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

}
