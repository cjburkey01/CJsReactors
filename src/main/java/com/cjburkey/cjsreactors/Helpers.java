package com.cjburkey.cjsreactors;

import com.cjburkey.cjsreactors.tile.ITile;
import java.util.UUID;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by CJ Burkey on 2019/04/12
 */
public final class Helpers {

    public static NBTTagCompound writeUuid(NBTTagCompound nbt, UUID uuid) {
        if (uuid == null) return nbt;
        nbt.setLong("least", uuid.getLeastSignificantBits());
        nbt.setLong("most", uuid.getMostSignificantBits());
        return nbt;
    }

    public static UUID readUuid(NBTTagCompound nbt) {
        if (!nbt.hasKey("least") || !nbt.hasKey("most")) return null;
        long least = nbt.getLong("least");
        long most = nbt.getLong("most");
        return new UUID(least, most);
    }

    public static <T extends TileEntity & ITile> void addTile(Class<T> tileEntity) {
        try {
            GameRegistry.registerTileEntity(tileEntity, new ResourceLocation(ModInfo.MODID, tileEntity.newInstance().getTileName()));
        } catch (InstantiationException | IllegalAccessException e) {
            Log.error("Failed to initialize tile entity of type {}:", tileEntity.getName());
            e.printStackTrace();
        }
    }

}
