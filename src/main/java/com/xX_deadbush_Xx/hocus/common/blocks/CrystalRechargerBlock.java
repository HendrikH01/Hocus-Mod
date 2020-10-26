package com.xX_deadbush_Xx.hocus.common.blocks;

import javax.annotation.Nullable;

import com.xX_deadbush_Xx.hocus.api.inventory.SimpleItemHandler;
import com.xX_deadbush_Xx.hocus.common.register.ModTileEntities;
import com.xX_deadbush_Xx.hocus.common.tile.CrystalRechargerTile;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class CrystalRechargerBlock extends Block {

    public CrystalRechargerBlock(Properties builder) {
        super(builder);
    }


    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return ModTileEntities.CRYSTAL_RECHARGER_TILE.get().create();
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult result) {
        if (!worldIn.isRemote) {
            TileEntity tile = worldIn.getTileEntity(pos);
            if (tile instanceof CrystalRechargerTile) {
                NetworkHooks.openGui((ServerPlayerEntity) player, (CrystalRechargerTile) tile, pos);
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public void onPlayerDestroy(IWorld worldIn, BlockPos pos, BlockState state) {
        super.onPlayerDestroy(worldIn, pos, state);
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if(tileEntity instanceof CrystalRechargerTile) {
            InventoryHelper.dropItems(worldIn.getWorld(), pos, ((SimpleItemHandler) (((CrystalRechargerTile) tileEntity).getItemHandler())).toNonNullList());
        }
    }
}
