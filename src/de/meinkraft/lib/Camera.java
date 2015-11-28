package de.meinkraft.lib;

import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_DISABLED;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_NORMAL;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_E;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_SHIFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_Q;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_1;

public class Camera implements InputController {
	
	public static final float MOV_SPEED = 5;
	public static final float ROT_SPEED = 0.1f;
	
	private boolean lockMov, lockRot;
	
	private Matrix4 projection;
	private Transform transform;
	
	public Camera(Matrix4 projection) {
		this.projection = projection;
		this.transform = new Transform();
	}
	
	@Override
	public void input() {
		if(Input.getCursorMode() == GLFW_CURSOR_DISABLED) {
			if(Input.getKeyDown(GLFW_KEY_ESCAPE)) {
				Input.setCursorMode(GLFW_CURSOR_NORMAL);
				Input.setMousePos(Display.getWidth() / 2, Display.getHeight() / 2);
			} else {
				if(!lockMov) {
					Vector3 forward = getDirection();
					Vector3 sideward = new Vector3(getViewMatrix().get(0, 0), getViewMatrix().get(0, 1), getViewMatrix().get(0, 2));
					
					float MOV_SPEED = Camera.MOV_SPEED;
					if(Input.getKey(GLFW_KEY_LEFT_SHIFT))
						MOV_SPEED *= 15;
					
					if(Input.getKey(GLFW_KEY_W))
						transform.setTranslation(transform.getTranslation().add(forward.mul(MOV_SPEED * Time.getDelta())));
					
					if(Input.getKey(GLFW_KEY_S))
						transform.setTranslation(transform.getTranslation().add(forward.mul(-MOV_SPEED * Time.getDelta())));
					
					if(Input.getKey(GLFW_KEY_A))
						transform.setTranslation(transform.getTranslation().add(sideward.mul(MOV_SPEED * Time.getDelta())));
					
					if(Input.getKey(GLFW_KEY_D))
						transform.setTranslation(transform.getTranslation().add(sideward.mul(-MOV_SPEED * Time.getDelta())));
					
					if(Input.getKey(GLFW_KEY_Q))
						transform.setTranslation(transform.getTranslation().add(new Vector3(0, -MOV_SPEED, 0).mul(Time.getDelta())));
					
					if(Input.getKey(GLFW_KEY_E))
						transform.setTranslation(transform.getTranslation().add(new Vector3(0, MOV_SPEED, 0).mul(Time.getDelta())));
				}
				
				if(!lockRot) {
					int x = Input.getMouseDX();
					int y = Input.getMouseDY();
					
					if(x != 0)
						transform.getRotation().setY(transform.getRotation().getY() + (float) x * ROT_SPEED);
					
					if(y != 0)
						transform.getRotation().setX(transform.getRotation().getX() + (float) y * ROT_SPEED);
				}
			}
		} else if(Input.getCursorMode() == GLFW_CURSOR_NORMAL) {
			if(Input.getMouseDown(GLFW_MOUSE_BUTTON_1))
				Input.setCursorMode(GLFW_CURSOR_DISABLED);
		}
	}
	
	public void update() {
//		// AUDIO
//		alListener(AL_POSITION, transform.getTranslation().negate().asFloatBuffer());
//		alListener(AL_VELOCITY, new Vector3().asFloatBuffer());
//		
//		Vector3 at = getViewMatrix().zVector3().negate();
//		Vector3 up = getViewMatrix().yVector3();
//		FloatBuffer buffer = BufferUtils.createFloatBuffer(6).put(new float[] {at.getX(), at.getY(), at.getZ(), up.getX(), up.getY(), up.getZ()});
//		buffer.flip();
//		
//		alListenerfv(AL_ORIENTATION, buffer);
	}
	
	public Matrix4 getViewMatrix() {
		Matrix4 t = new Matrix4().initTranslation(transform.getTranslation());
		Matrix4 r = new Matrix4().initRotation(transform.getRotation(), Matrix4.YXZ);
		
		return r.mul(t);
	}
	
	public Vector3 getDirection() {
		return new Vector3(getViewMatrix().get(2, 0), getViewMatrix().get(2, 1), getViewMatrix().get(2, 2));
	}
	
	public void lockMovement(boolean lock) {
		lockMov = lock;
	}
	
	public void lockRotation(boolean lock) {
		lockRot = lock;
	}
	
	public Matrix4 getProjectionMatrix() {
		return projection;
	}
	
	public void setProjectionMatrix(Matrix4 projection) {
		this.projection = projection;
	}
	
	public Transform getTransform() {
		return transform;
	}
	
	public void setTransform(Transform transform) {
		this.transform = transform;
	}
	
}
