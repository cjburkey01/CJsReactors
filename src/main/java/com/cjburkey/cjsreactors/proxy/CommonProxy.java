package com.cjburkey.cjsreactors.proxy;

import com.cjburkey.cjsreactors.Helpers;
import com.cjburkey.cjsreactors.capability.IReactorTile;
import com.cjburkey.cjsreactors.tile.TileReactorController;
import com.cjburkey.cjsreactors.tile.TileReactorFuelInput;
import com.cjburkey.cjsreactors.tile.TileReactorPowerOutput;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Created by CJ Burkey on 2019/04/07
 */
public class CommonProxy {

    @CapabilityInject(IReactorTile.class)
    private static Capability<IReactorTile> CAPABILITY_REACTOR_TILE;

    public void construct(FMLConstructionEvent e) {
    }

    public void preinit(FMLPreInitializationEvent e) {
        CapabilityManager.INSTANCE.register(IReactorTile.class, new IReactorTile.Storage(), IReactorTile.Impl::new);
    }

    public void init(FMLInitializationEvent e) {
        Helpers.addTile(TileReactorController.class);
        Helpers.addTile(TileReactorFuelInput.class);
        Helpers.addTile(TileReactorPowerOutput.class);
    }

    public void postinit(FMLPostInitializationEvent e) {
    }

    public static Capability<IReactorTile> reactorTileCapability() {
        return CAPABILITY_REACTOR_TILE;
    }

}
