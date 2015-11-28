package de.meinkraft;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StructureLoader {
	
	public static void loadStructure(World world, int x, int y, int z, InputStream path) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(path));
		
		String line;
		while((line = br.readLine()) != null) {
			String xyz = line.split(":")[0];
			world.setBlockAt(x + Integer.parseInt(xyz.split(",")[0]), y + Integer.parseInt(xyz.split(",")[1]), z + Integer.parseInt(xyz.split(",")[2]), Block.getBlockById(Integer.parseInt(line.split(":")[1])));
		}
		br.close();
	}
	
}
