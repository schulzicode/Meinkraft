package de.meinkraft;

public abstract class Block {

	public static final Block AIR = new BlockAir(0, Material.AIR, BlockType.AIR);
	public static final Block DIRT = new BlockDirt(1, Material.GROUND, BlockType.CUBE);
	
	private final int id;
	private final Material material;
	private final BlockType type;
	
	public Block(int id, Material material, BlockType type) {
		this.id = id;
		this.material = material;
		this.type = type;
	}

//	public abstract ItemStack getItemDrop();
//	public abstract float[]? getVertices();
	
	public int getId() {
		return id;
	}
	
	public Material getMaterial() {
		return material;
	}
	
	public BlockType getType() {
		return type;
	}
	
}
