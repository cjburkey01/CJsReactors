package com.cjburkey.cjsreactors.block;

import com.cjburkey.cjsreactors.tile.TileReactorPowerOutput;
import javax.annotation.Nonnull;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created by CJ Burkey on 2019/04/14
 */
public final class BlockReactorPowerOutput extends BlockReactorCasingBlock implements ITileEntityProvider {

    @Override
    @Nonnull
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
        return new TileReactorPowerOutput();
    }

}
