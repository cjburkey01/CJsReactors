package com.cjburkey.cjsreactors;

import com.cjburkey.cjsreactors.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import static com.cjburkey.cjsreactors.ModInfo.*;

@SuppressWarnings("unused")
@Mod(modid = MODID, name = NAME, version = VERSION)
public final class CJsReactors {

    @SidedProxy(clientSide = PROXY_BASE + "ClientProxy", serverSide = PROXY_BASE + "ServerProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void construct(FMLConstructionEvent e) {
        proxy.construct(e);
    }

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent e) {
        proxy.preinit(e);
    }

    @Mod.EventHandler
    public void onInit(FMLInitializationEvent e) {
        proxy.init(e);
    }

    @Mod.EventHandler
    public void onPostInit(FMLPostInitializationEvent e) {
        proxy.postinit(e);
    }

}
