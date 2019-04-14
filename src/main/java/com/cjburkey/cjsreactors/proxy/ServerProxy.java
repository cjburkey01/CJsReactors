package com.cjburkey.cjsreactors.proxy;

import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Created by CJ Burkey on 2019/04/07
 */
@SuppressWarnings("unused")
public final class ServerProxy extends CommonProxy {

    public void construct(FMLConstructionEvent e) {
        super.construct(e);
    }

    public void preinit(FMLPreInitializationEvent e) {
        super.preinit(e);
    }

    public void init(FMLInitializationEvent e) {
        super.init(e);
    }

    public void postinit(FMLPostInitializationEvent e) {
        super.postinit(e);
    }

}
