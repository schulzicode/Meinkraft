package de.meinkraft;

import java.util.Random;

public class OpenSimplexNoise {
	
	private OpenSimplexNoiseOctave[] o;
	private double[] f;
	private double[] a;
	
	private double persistance;
	
	public OpenSimplexNoise(int largestFeature, double persistance, long seed) {
		this.persistance = persistance;
		
		int numberOfOctaves = (int) Math.ceil(Math.log10(largestFeature) / Math.log10(2));
		
		o = new OpenSimplexNoiseOctave[numberOfOctaves];
		f = new double[numberOfOctaves];
		a = new double[numberOfOctaves];
		
		Random r = new Random(seed);
		
		for(int i = 0; i < numberOfOctaves; i++) {
			o[i] = new OpenSimplexNoiseOctave(r.nextInt());
			
			f[i] = Math.pow(2, i);
			a[i] = Math.pow(persistance, o.length - i);
		}
	}
	
	public double getNoise(int x, int y) {
		double result = 0;
		
		for(int i = 0; i < o.length; i++)
			result += o[i].eval(x / f[i], y / f[i]) * a[i];
		
		return result;
	}
	
	public double getNoise(double x, double y, double z) {
		double result = 0;
		
		for(int i = 0; i < o.length; i++) {
			double f = Math.pow(2, i);
			double a = Math.pow(persistance, o.length - i);
			result += o[i].eval(x / f, y / f, z / f) * a;
		}
		
		return result;
	}
	
}
