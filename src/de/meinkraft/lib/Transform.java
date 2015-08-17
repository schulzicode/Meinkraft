package de.meinkraft.lib;

public class Transform {
	
	private Vector3 translation;
	private Vector3 rotation;
	private Vector3 scale;
	
	public Transform() {
		this(new Vector3());
	}
	
	public Transform(Transform transform) {
		this(transform.getTranslation(), transform.getRotation(), transform.getScale());
	}
	
	public Transform(Vector3 translation) {
		this(translation, new Vector3());
	}
	
	public Transform(Vector3 translation, Vector3 rotation) {
		this(translation, rotation, new Vector3(1));
	}
	
	public Transform(Vector3 translation, Vector3 rotation, Vector3 scale) {
		this.translation = translation;
		this.rotation = rotation;
		this.scale = scale;
	}
	
	public Matrix4 getTransformationMatrix() {
		Matrix4 t = new Matrix4().initTranslation(translation);
		Matrix4 r = new Matrix4().initRotation(rotation, Matrix4.XYZ);
		Matrix4 s = new Matrix4().initScale(scale);
		
		return t.mul(r.mul(s));
	}
	
	public Vector3 getTranslation() {
		return translation;
	}
	
	public void setTranslation(Vector3 translation) {
		this.translation = translation;
	}
	
	public Vector3 getRotation() {
		return rotation;
	}
	
	public void setRotation(Vector3 rotation) {
		this.rotation = rotation;
	}
	
	public Vector3 getScale() {
		return scale;
	}
	
	public void setScale(Vector3 scale) {
		this.scale = scale;
	}
	
}
