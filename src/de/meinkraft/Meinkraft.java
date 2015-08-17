package de.meinkraft;

import de.meinkraft.lib.Matrix4;
import de.meinkraft.lib.Transform;
import de.meinkraft.lib.Vector3;

public class Meinkraft {
	
	public Meinkraft() {
		Transform t = new Transform(new Vector3(5, 3, 2), new Vector3(90, 90, 90));
		Matrix4 m = t.getTransformationMatrix().inverse().mul(t.getTransformationMatrix());
		System.out.println(m.toString());
	}
	
	public void input() {
		
	}
	
	public void update() {
		
	}
	
	public void render() {
		
	}
	
}
