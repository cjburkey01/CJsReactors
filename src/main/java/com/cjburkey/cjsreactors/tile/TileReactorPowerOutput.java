package com.cjburkey.cjsreactors.tile;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

/**
 * Created by CJ Burkey on 2019/04/14
 */
public class TileReactorPowerOutput extends ReactorTile implements IEnergyStorage {

    private final int bufferSize = 1024;
    private int bufferContents = 0;

    @Override
    public String getTileName() {
        return "reactor_power_output";
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        return 0;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        int after = bufferContents - maxExtract;
        if (after < 0) {
            int b4 = bufferContents;
            if (!simulate) bufferContents = 0;
            return b4;
        }
        if (!simulate) bufferContents = after;
        return maxExtract;
    }

    @Override
    public int getEnergyStored() {
        return bufferContents;
    }

    @Override
    public int getMaxEnergyStored() {
        return bufferSize;
    }

    @Override
    public boolean canExtract() {
        return true;
    }

    @Override
    public boolean canReceive() {
        return false;
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY) return true;
        return super.hasCapability(capability, facing);
    }

    @SuppressWarnings("unchecked")
    @Override
    @Nullable
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY) return (T) this;
        return super.getCapability(capability, facing);
    }

}
