package com.cjburkey.cjsreactors.proxy;

import com.cjburkey.cjsreactors.Helpers;
import com.cjburkey.cjsreactors.tile.TileReactorFuelInput;
import com.cjburkey.cjsreactors.tile.TileReactorPowerOutput;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Created by CJ Burkey on 2019/04/07
 */
public class CommonProxy {

    public void construct(FMLConstructionEvent e) {
    }

    public void preinit(FMLPreInitializationEvent e) {
    }

    public void init(FMLInitializationEvent e) {
        Helpers.addTile(TileReactorFuelInput.class);
        Helpers.addTile(TileReactorPowerOutput.class);
    }

    public void postinit(FMLPostInitializationEvent e) {
    }

}
