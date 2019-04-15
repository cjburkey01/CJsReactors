package com.cjburkey.cjsreactors.tile;

import com.cjburkey.cjsreactors.ModConfig;
import com.cjburkey.cjsreactors.block.BlockReactorController;
import com.cjburkey.cjsreactors.block.IReactorCasing;
import com.cjburkey.cjsreactors.block.IReactorContent;
import com.cjburkey.cjsreactors.capability.IReactorTile;
import com.cjburkey.cjsreactors.proxy.CommonProxy;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;

/**
 * Created by CJ Burkey on 2019/04/14
 */
public final class TileReactorController extends ReactorTile {

    private final ReactorBounds bounds = new ReactorBounds();
    private boolean created = false;

    public void updateReactor(@Nullable EntityPlayer checker) {
        if (world.isRemote) return;

        created = false;
        final ReactorBounds newBounds = new ReactorBounds();

        EnumFacing zAxis = world.getBlockState(getPos()).getValue(BlockReactorController.FACING).getOpposite();
        EnumFacing xAxis = zAxis.rotateY();

        checkBoundsOnAxis(getPos(), newBounds, xAxis, true, false, false);
        BlockPos corner = getPos().offset(xAxis.getOpposite(), Math.abs(newBounds.minX));
        checkBoundsOnAxis(corner, newBounds, EnumFacing.UP, false, true, false);
        corner = corner.offset(EnumFacing.DOWN, Math.abs(newBounds.minY));
        checkBoundsOnAxis(corner, newBounds, zAxis, false, false, true);

        bounds.set(newBounds);
        checkBounds(checker, xAxis, zAxis);
    }

    private void checkBoundsOnAxis(BlockPos corner, ReactorBounds bounds, EnumFacing direction, boolean x, boolean y, boolean z) {
        IBlockState at;

        BlockPos current = corner;
        if (x) bounds.maxX--;
        if (y) bounds.maxY--;
        if (z) bounds.maxZ--;
        do {
            current = current.offset(direction);
            at = world.getBlockState(current);
            if (x) bounds.maxX++;
            if (y) bounds.maxY++;
            if (z) bounds.maxZ++;
        } while (at.getBlock() instanceof IReactorCasing);

        current = corner;
        if (x) bounds.minX++;
        if (y) bounds.minY++;
        if (z) bounds.minZ++;
        do {
            current = current.offset(direction.getOpposite());
            at = world.getBlockState(current);
            if (x) bounds.minX--;
            if (y) bounds.minY--;
            if (z) bounds.minZ--;
        } while (at.getBlock() instanceof IReactorCasing);
    }

    private void checkBounds(@Nullable EntityPlayer checker, EnumFacing xAxis, EnumFacing zAxis) {
        final int mi = ModConfig.minReactorSize;
        final int ma = ModConfig.maxReactorSize;
        final int w = bounds.width() + 1;
        final int h = bounds.height() + 1;
        final int l = bounds.length() + 1;
        if (w < mi || w > ma || h < mi || h > ma || l < mi || l > ma) {
            sendMessage(checker, "error.reactor_wrong_size", w, h, l);
            return;
        }

        if (bounds.minZ != 0) {
            sendMessage(checker, "error.controller_bad_placement", getPos().getX(), getPos().getY(), getPos().getZ());
            return;
        }

        BlockPos fb = checkReactorCasingFace(xAxis, EnumFacing.UP, zAxis, bounds.minX, bounds.maxX, bounds.minY, bounds.maxY, 0, bounds.maxZ);
        if (fb != null) {
            sendMessage(checker, "error.reactor_casing_wrong", fb.getX(), fb.getY(), fb.getZ());
            return;
        }
        BlockPos lr = checkReactorCasingFace(zAxis, EnumFacing.UP, xAxis, 0, bounds.maxZ, bounds.minY, bounds.maxY, bounds.minX, bounds.maxX);
        if (lr != null) {
            sendMessage(checker, "error.reactor_casing_wrong", lr.getX(), lr.getY(), lr.getZ());
            return;
        }
        BlockPos tb = checkReactorCasingFace(xAxis, zAxis, EnumFacing.UP, bounds.minX, bounds.maxX, 0, bounds.maxZ, bounds.minY, bounds.maxY);
        if (tb != null) {
            sendMessage(checker, "error.reactor_casing_wrong", tb.getX(), tb.getY(), tb.getZ());
            return;
        }

        BlockPos contentError = checkReactorContent(xAxis, zAxis);
        if (contentError != null) {
            sendMessage(checker, "error.reactor_content_wrong", contentError.getX(), contentError.getY(), contentError.getZ());
            return;
        }

        BlockPos min = getPos()
                .offset(xAxis, bounds.minX)
                .offset(EnumFacing.UP, bounds.minY)
                .offset(zAxis, bounds.minZ);
        BlockPos max = getPos()
                .offset(xAxis, bounds.maxX)
                .offset(EnumFacing.UP, bounds.maxY)
                .offset(zAxis, bounds.maxZ);
        sendMessage(checker, "success.reactor_created", min.getX(), min.getY(), min.getZ(), max.getX(), max.getY(), max.getZ());

        created = true;
    }

    private void sendMessage(@Nullable EntityPlayer player, String translation, Object... data) {
        if (player != null) player.sendMessage(new TextComponentTranslation(translation, data));
    }

    private BlockPos checkReactorCasingFaces(EnumFacing xAxis, EnumFacing yAxis, EnumFacing zAxis, int minX, int maxX, int minY, int maxY, int z) {
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                BlockPos p = getPos()
                        .offset(xAxis, x)
                        .offset(yAxis, y)
                        .offset(zAxis, z);
                if (isInvalidReactorBlock(p, true)) {
                    return p;
                }
            }
        }
        return null;
    }

    private BlockPos checkReactorCasingFace(EnumFacing xAxis, EnumFacing yAxis, EnumFacing zAxis, int minX, int maxX, int minY, int maxY, int minZ, int maxZ) {
        BlockPos at = checkReactorCasingFaces(xAxis, yAxis, zAxis, minX, maxX, minY, maxY, minZ);
        if (at != null) return at;
        return checkReactorCasingFaces(xAxis, yAxis, zAxis, minX, maxX, minY, maxY, maxZ);
    }

    private BlockPos checkReactorContent(EnumFacing xAxis, EnumFacing zAxis) {
        for (int x = bounds.minX + 1; x < bounds.maxX; x++) {
            for (int y = bounds.minY + 1; y < bounds.maxY; y++) {
                for (int z = bounds.minZ + 1; z < bounds.maxZ; z++) {
                    BlockPos p = getPos().offset(xAxis, x)
                            .offset(EnumFacing.UP, y)
                            .offset(zAxis, z);
                    if (isInvalidReactorBlock(p, false)) {
                        return p;
                    }
                }
            }
        }
        return null;
    }

    private boolean isInvalidReactorBlock(BlockPos pos, boolean casing) {
        if (world.isAirBlock(pos)) return false;
        TileEntity entityAt = world.getTileEntity(pos);
        IReactorTile tile = null;
        if (entityAt != null && entityAt.hasCapability(CommonProxy.reactorTileCapability(), EnumFacing.NORTH)) {
            tile = entityAt.getCapability(CommonProxy.reactorTileCapability(), EnumFacing.NORTH);
        }
        if (tile != null) return casing != tile.isCasingBlock();

        Block at = world.getBlockState(pos).getBlock();
        if (casing) return !(at instanceof IReactorCasing);
        return !(at instanceof IReactorContent);
    }

    @Override
    public String getTileName() {
        return "reactor_controller";
    }

    private static final class ReactorBounds {

        private int minX;
        private int minY;
        private int minZ;
        private int maxX;
        private int maxY;
        private int maxZ;

        private ReactorBounds(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
            set(minX, minY, minZ, maxX, maxY, maxZ);
        }

        private ReactorBounds() {
            this(0, 0, 0, 0, 0, 0);
        }

        private void set(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
            this.minX = minX;
            this.minY = minY;
            this.minZ = minZ;
            this.maxX = maxX;
            this.maxY = maxY;
            this.maxZ = maxZ;
        }

        private void set(ReactorBounds other) {
            set(other.minX, other.minY, other.minZ, other.maxX, other.maxY, other.maxZ);
        }

        private int width() {
            return maxX - minX;
        }

        private int height() {
            return maxY - minY;
        }

        private int length() {
            return maxZ - minZ;
        }

    }

}
