package de.meinkraft;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import org.lwjgl.BufferUtils;

public class ChunkLoader extends Thread {
	
	private Queue<Chunk> queue;
	
	public ChunkLoader() {
		queue = new LinkedBlockingQueue<Chunk>();
		
		start();
	}
	
	public void addChunk(Chunk chunk) {
		queue.add(chunk);
	}
	
	private void generateChunks() {
		if(!queue.isEmpty()) {
			Chunk chunk;
			
			if(queue.element().isGenerated()) {
				chunk = queue.poll();
				chunk.load();
				System.out.println("Chunk loaded!");
			} else {
				chunk = queue.peek();
				
				WorldGenerator.FLAT.generate(chunk);
				System.out.println("Chunk generated!");
			}
		}
	}
	
	@Override
	public void run() {
		while(isAlive()) {
			generateChunks();
			
			try {
				sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
