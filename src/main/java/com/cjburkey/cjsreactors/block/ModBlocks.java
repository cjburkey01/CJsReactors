package com.cjburkey.cjsreactors.block;

import com.cjburkey.cjsreactors.ModInfo;
import com.cjburkey.cjsreactors.item.ModItems;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by CJ Burkey on 2019/04/07
 */
@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = ModInfo.MODID)
public final class ModBlocks {

    public static Block blockReactorCasing = addBlock("reactor_casing", new BlockReactorCasingBlock());
    public static Block blockReactorController = addBlock("reactor_controller", new BlockReactorController());
    public static Block blockReactorPowerOutput = addBlock("reactor_power_output", new BlockReactorPowerOutput());
    public static Block blockReactorFuelInput = addBlock("reactor_fuel_input", new BlockReactorFuelInput());
    public static Block blockReactorFuelCell = addBlock("reactor_fuel_cell", new BlockReactorContentBlock());

    // -- Static handlers -- //

    private static Object2ObjectOpenHashMap<String, Block> blocks;

    @SuppressWarnings("unused")
    @SubscribeEvent
    public static void onRegisterBlocks(RegistryEvent.Register<Block> event) {
        if (blocks != null) blocks.values().forEach(event.getRegistry()::register);
    }

    private static Block addBlock(String path, Block block) {
        if (blocks == null) blocks = new Object2ObjectOpenHashMap<>();
        blocks.put(path, block
                .setUnlocalizedName(path)
                .setRegistryName(ModInfo.MODID, path));
        ModItems.addBlockItem(path, block);
        return block;
    }

}
