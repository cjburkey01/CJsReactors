package com.cjburkey.cjsreactors.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

/**
 * Created by CJ Burkey on 2019/04/14
 */
@SuppressWarnings("WeakerAccess")
public class BlockReactorBasicBlock extends Block {

    BlockReactorBasicBlock() {
        super(Material.IRON);

        setHardness(1.0f);
        setSoundType(SoundType.METAL);
        setCreativeTab(CreativeTabs.REDSTONE);
        setHarvestLevel("pickaxe", 1);
    }

}
