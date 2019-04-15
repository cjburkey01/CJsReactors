package com.cjburkey.cjsreactors.capability;

import com.cjburkey.cjsreactors.tile.TileReactorController;
import javax.annotation.Nullable;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

/**
 * Created by CJ Burkey on 2019/04/14
 */
public interface IReactorTile {

    void updateController(@Nullable TileReactorController controller);

    @Nullable
    TileReactorController getController();

    boolean isCasingBlock();

    class Storage implements Capability.IStorage<IReactorTile> {

        @Override
        @Nullable
        public NBTBase writeNBT(Capability<IReactorTile> capability, IReactorTile instance, EnumFacing side) {
            return new NBTTagCompound();
        }

        @Override
        public void readNBT(Capability<IReactorTile> capability, IReactorTile instance, EnumFacing side, NBTBase nbt) {
        }

    }

    class Impl implements IReactorTile {

        private TileReactorController controller;

        @Override
        public void updateController(@Nullable TileReactorController controller) {
            this.controller = controller;
        }

        @Override
        @Nullable
        public TileReactorController getController() {
            return controller;
        }

        @Override
        public boolean isCasingBlock() {
            return false;
        }

    }

}
