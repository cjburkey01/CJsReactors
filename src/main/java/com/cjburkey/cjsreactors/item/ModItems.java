package com.cjburkey.cjsreactors.item;

import com.cjburkey.cjsreactors.ModInfo;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.util.Objects;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by CJ Burkey on 2019/04/07
 */
@Mod.EventBusSubscriber(modid = ModInfo.MODID)
public final class ModItems {

    // -- Static handlers -- //

    private static Object2ObjectOpenHashMap<String, Item> items;

    @SuppressWarnings("unused")
    @SubscribeEvent
    public static void onRegisterItems(RegistryEvent.Register<Item> event) {
        if (items != null) items.values().forEach(event.getRegistry()::register);
    }

    @SuppressWarnings("unused")
    @SubscribeEvent
    public static void onRegisterModels(ModelRegistryEvent event) {
        if (items != null) items.values().forEach(item ->
                ModelLoader.setCustomModelResourceLocation(item,
                        0,
                        new ModelResourceLocation(Objects.requireNonNull(item.getRegistryName()), "inventory")));
    }

    private static Item addItem(String path, Item item) {
        if (items == null) items = new Object2ObjectOpenHashMap<>();
        items.put(path, item
                .setUnlocalizedName(path)
                .setRegistryName(ModInfo.MODID, path));
        return item;
    }

    public static void addBlockItem(String path, Block block) {
        addItem(path, new ItemBlock(block));
    }

}
