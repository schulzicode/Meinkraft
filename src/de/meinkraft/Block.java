package de.meinkraft;

import java.lang.reflect.Field;

public abstract class Block {

	public static final Block AIR = new BlockAir(0, Material.AIR, BlockType.AIR);
	public static final Block STONE = new BlockStone(1, Material.GROUND, BlockType.CUBE, 1,1,1,1,1,1);
	public static final Block DIRT = new BlockDirt(2, Material.GROUND, BlockType.CUBE, 2,2,2,2,2,2);
	public static final Block GRASS = new BlockGrass(3, Material.GROUND, BlockType.CUBE, 3,3,3,3,0,2);
	public static final Block ROSE = new BlockRose(4, Material.PLANT, BlockType.PLANT, 12);
	public static final Block TALL_GRASS = new BlockTallGrass(5, Material.PLANT, BlockType.PLANT, 39);
	public static final Block WOODEN_PLANKS = new BlockWoodenPlanks(6, Material.GROUND, BlockType.CUBE, 4,4,4,4,4,4);
	
	private final int id;
	private final Material material;
	private final BlockType type;
	private final int[] textures;
	
	public Block(int id, Material material, BlockType type, int... textures) {
		this.id = id;
		this.material = material;
		this.type = type;
		this.textures = textures;
	}

//	public abstract ItemStack getItemDrop();
//	public abstract float[]? getVertices();
	
	public static Block getBlockById(int id) {
		for(Field field : Block.class.getDeclaredFields()) {
			if(field.getType().getSimpleName().equals("Block")) {
				try {
					Block block = (Block) field.get(field.getName());
					
					if(block.id == id)
						return block;
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		
		return null;
	}
	
	public int getId() {
		return id;
	}
	
	public Material getMaterial() {
		return material;
	}
	
	public BlockType getType() {
		return type;
	}
	
	public int[] getTextures() {
		return textures;
	}
	
}
