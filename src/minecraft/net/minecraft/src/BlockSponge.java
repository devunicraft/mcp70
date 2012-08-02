package net.minecraft.src;

public class BlockSponge extends Block
{
    protected BlockSponge(int par1)
    {
        super(par1, Material.sponge);
        blockIndexInTexture = 48;
        setCreativeTab(CreativeTabs.tabBlock);
    }
}
