package com.cjburkey.cjsreactors.tile;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;

/**
 * Created by CJ Burkey on 2019/04/14
 */
public class TileReactorFuelInput extends ReactorTile implements IInventory {

    private ItemStack itemStack = ItemStack.EMPTY;

    @Override
    @Nonnull
    public String getTileName() {
        return "reactor_fuel_input";
    }

    @Override
    @Nonnull
    public String getName() {
        return getTileName();
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @Override
    @Nonnull
    public ITextComponent getDisplayName() {
        return new TextComponentTranslation("tile.%s.name", getName());
    }

    @Override
    public int getSizeInventory() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return itemStack.isEmpty();
    }

    @Override
    @Nonnull
    public ItemStack getStackInSlot(int index) {
        return index == 0 ? itemStack : ItemStack.EMPTY;
    }

    @Override
    @Nonnull
    public ItemStack decrStackSize(int index, int count) {
        if (itemStack.isEmpty()) return ItemStack.EMPTY;

        if (itemStack.getCount() > count) {
            ItemStack at = itemStack.copy();
            at.setCount(count);
            itemStack.setCount(itemStack.getCount() - count);
            return at;
        }
        ItemStack at = itemStack;
        itemStack = ItemStack.EMPTY;
        return at;
    }

    @Override
    @Nonnull
    public ItemStack removeStackFromSlot(int index) {
        if (index == 0) {
            ItemStack at = itemStack;
            itemStack = ItemStack.EMPTY;
            return at;
        }
        return ItemStack.EMPTY;
    }

    @Override
    public void setInventorySlotContents(int index, @Nonnull ItemStack stack) {
        if (index == 0) itemStack = stack;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUsableByPlayer(@Nonnull EntityPlayer player) {
        return true;
    }

    @Override
    public void openInventory(@Nonnull EntityPlayer player) {
    }

    @Override
    public void closeInventory(@Nonnull EntityPlayer player) {
    }

    @Override
    public boolean isItemValidForSlot(int index, @Nonnull ItemStack stack) {
        return true;
    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {
    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear() {
        itemStack = ItemStack.EMPTY;
    }

    @Override
    @Nonnull
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        itemStack = new ItemStack(compound.getCompoundTag("contents"));
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        compound.setTag("contents", itemStack.serializeNBT());
        super.readFromNBT(compound);
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return true;
        return super.hasCapability(capability, facing);
    }

    @SuppressWarnings("unchecked")
    @Override
    @Nullable
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return (T) this;
        return super.getCapability(capability, facing);
    }

}
