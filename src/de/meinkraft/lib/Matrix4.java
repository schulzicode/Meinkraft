package de.meinkraft.lib;

public class Matrix4 {
	
	public static final int XYZ = 0;
	public static final int ZXY = 1;
	public static final int YZX = 2;
	public static final int ZYX = 3;
	public static final int YXZ = 4;
	public static final int XZY = 5;
	
	private float[][] m;
	
	public Matrix4() {
		m = new float[4][4];
	}
	
	public Matrix4 loadIdentity() {
		m[0][0] = 1; m[0][1] = 0; m[0][2] = 0; m[0][3] = 0;
		m[1][0] = 0; m[1][1] = 1; m[1][2] = 0; m[1][3] = 0;
		m[2][0] = 0; m[2][1] = 0; m[2][2] = 1; m[2][3] = 0;
		m[3][0] = 0; m[3][1] = 0; m[3][2] = 0; m[3][3] = 1;
		
		return this;
	}
	
	public Matrix4 initTranslation(Vector3 v) {
		return initTranslation(v.getX(), v.getY(), v.getZ());
	}
	
	public Matrix4 initTranslation(float x, float y, float z) {
		m[0][0] = 1; m[0][1] = 0; m[0][2] = 0; m[0][3] = x;
		m[1][0] = 0; m[1][1] = 1; m[1][2] = 0; m[1][3] = y;
		m[2][0] = 0; m[2][1] = 0; m[2][2] = 1; m[2][3] = z;
		m[3][0] = 0; m[3][1] = 0; m[3][2] = 0; m[3][3] = 1;
		
		return this;
	}
	
	public Matrix4 initRotation(Vector3 v, int order) {
		return initRotation(v.getX(), v.getY(), v.getZ(), order);
	}
	
	public Matrix4 initRotation(float x, float y, float z, int order) {
		Matrix4 rx = new Matrix4();
		Matrix4 ry = new Matrix4();
		Matrix4 rz = new Matrix4();
		
		x = (float) Math.toRadians(x);
		y = (float) Math.toRadians(y);
		z = (float) Math.toRadians(z);
		
		rx.m[0][0] = 1; rx.m[0][1] = 0; 					rx.m[0][2] = 0; 					rx.m[0][3] = 0;
		rx.m[1][0] = 0; rx.m[1][1] = (float) Math.cos(x); 	rx.m[1][2] = (float) -Math.sin(x); 	rx.m[1][3] = 0;
		rx.m[2][0] = 0; rx.m[2][1] = (float) Math.sin(x); 	rx.m[2][2] = (float) Math.cos(x); 	rx.m[2][3] = 0;
		rx.m[3][0] = 0; rx.m[3][1] = 0; 					rx.m[3][2] = 0; 					rx.m[3][3] = 1;
		
		ry.m[0][0] = (float) Math.cos(y); 	ry.m[0][1] = 0; ry.m[0][2] = (float) Math.sin(y); 	ry.m[0][3] = 0;
		ry.m[1][0] = 0; 					ry.m[1][1] = 1; ry.m[1][2] = 0; 					ry.m[1][3] = 0;
		ry.m[2][0] = (float) -Math.sin(y); 	ry.m[2][1] = 0; ry.m[2][2] = (float) Math.cos(y); 	ry.m[2][3] = 0;
		ry.m[3][0] = 0; 					ry.m[3][1] = 0; ry.m[3][2] = 0; 					ry.m[3][3] = 1;
		
		rz.m[0][0] = (float) Math.cos(z); 	rz.m[0][1] = (float) -Math.sin(z); 	rz.m[0][2] = 0; rz.m[0][3] = 0;
		rz.m[1][0] = (float) Math.sin(z); 	rz.m[1][1] = (float) Math.cos(z); 	rz.m[1][2] = 0; rz.m[1][3] = 0;
		rz.m[2][0] = 0; 					rz.m[2][1] = 0; 					rz.m[2][2] = 1; rz.m[2][3] = 0;
		rz.m[3][0] = 0; 					rz.m[3][1] = 0; 					rz.m[3][2] = 0; rz.m[3][3] = 1;
		
		switch(order) {
		case XYZ:
			m = rz.mul(ry.mul(rx)).getM();
			break;
		case ZXY:
			m = ry.mul(rx.mul(rz)).getM();
			break;
		case YZX:
			m = rx.mul(rz.mul(ry)).getM();
			break;
		case ZYX:
			m = rx.mul(ry.mul(rz)).getM();
			break;
		case YXZ:
			m = rz.mul(rx.mul(ry)).getM();
			break;
		case XZY:
			m = ry.mul(rz.mul(rx)).getM();
			break;
		}
		
		return this;
	}
	
	public Matrix4 initScale(Vector3 v) {
		return initScale(v.getX(), v.getY(), v.getZ());
	}
	
	public Matrix4 initScale(float x, float y, float z) {
		m[0][0] = x; m[0][1] = 0; m[0][2] = 0; m[0][3] = 0;
		m[1][0] = 0; m[1][1] = y; m[1][2] = 0; m[1][3] = 0;
		m[2][0] = 0; m[2][1] = 0; m[2][2] = z; m[2][3] = 0;
		m[3][0] = 0; m[3][1] = 0; m[3][2] = 0; m[3][3] = 1;
		
		return this;
	}
	
	public Matrix4 initPerspective(float fov, float aspectRatio, float zNear, float zFar) {
		float t = (float) (zNear * Math.tan(Math.PI / 180.0f * (fov / 2)));
		float b = -t;
		float r = t * aspectRatio;
		float l = -r;
		
		m[0][0] = (2 * zNear) / (r - l);	m[0][1] = 0; 						m[0][2] = (r + l) / (r - l); 				m[0][3] = 0;
		m[1][0] = 0; 						m[1][1] = (2 * zNear) / (t - b);	m[1][2] = (t + b) / (t - b); 				m[1][3] = 0;
		m[2][0] = 0; 						m[2][1] = 0; 						m[2][2] = -(zFar + zNear) / (zFar - zNear);	m[2][3] = (-2 * zFar * zNear) / (zFar - zNear);
		m[3][0] = 0; 						m[3][1] = 0; 						m[3][2] = -1; 								m[3][3] = 0;
		
		return this;
	}
	
	public Matrix4 initOrthographic(float left, float right, float top, float bottom, float zNear, float zFar) {
		m[0][0] = 2.0f / (right - left); 	m[0][1] = 0; 						m[0][2] = 0; 						m[0][3] = -(right + left) / (right - left);
		m[1][0] = 0; 						m[1][1] = 2.0f / (top - bottom); 	m[1][2] = 0; 						m[1][3] = -(top + bottom) / (top - bottom);
		m[2][0] = 0; 						m[2][1] = 0; 						m[2][2] = -2.0f / (zFar - zNear);	m[2][3] = -(zFar + zNear) / (zFar - zNear);
		m[3][0] = 0; 						m[3][1] = 0; 						m[3][2] = 0; 						m[3][3] = 1;
		
		return this;
	}
	
	public Vector3 transform(Vector4 v) {
		return new Vector3(m[0][0] * v.getX() + m[1][0] * v.getY() + m[2][0] * v.getZ() + m[3][0] * v.getW(),
							m[0][1] * v.getX() + m[1][1] * v.getY() + m[2][1] * v.getZ() + m[3][1] * v.getW(),
							m[0][2] * v.getX() + m[1][2] * v.getY() + m[2][2] * v.getZ() + m[3][2] * v.getW());
	}
	
	public Matrix4 transpose() {
		try {
			Matrix4 mat = (Matrix4) this.clone();
			
			for(int x = 0; x < 4; x++) {
				for(int y = 0; y < 4; y++) {
					m[x][y] = mat.m[y][x];
				}
			}
			
			return this;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * A new matrix will be returned
	 * @return Returns the inverse of this matrix
	 */
	public Matrix4 inverse() {
		Matrix4 matrix = new Matrix4();
		
		matrix.m[0][0] = m[1][1] * m[2][2] * m[3][3] + m[1][2] * m[2][3] * m[3][1] + m[1][3] * m[2][1] * m[3][2] - m[1][1] * m[2][3] * m[3][2] - m[1][2] * m[2][1] * m[3][3] - m[1][3] * m[2][2] * m[3][1];
		matrix.m[0][1] = m[0][1] * m[2][3] * m[3][2] + m[0][2] * m[2][1] * m[3][3] + m[0][3] * m[2][2] * m[3][1] - m[0][1] * m[2][2] * m[3][3] - m[0][2] * m[2][3] * m[3][1] - m[0][3] * m[2][1] * m[3][2];
		matrix.m[0][2] = m[0][1] * m[1][2] * m[3][3] + m[0][2] * m[1][3] * m[3][1] + m[0][3] * m[1][1] * m[3][2] - m[0][1] * m[1][3] * m[3][2] - m[0][2] * m[1][1] * m[3][3] - m[0][3] * m[1][2] * m[3][1];
		matrix.m[0][3] = m[0][1] * m[1][3] * m[2][2] + m[0][2] * m[1][1] * m[2][3] + m[0][3] * m[1][2] * m[2][1] - m[0][1] * m[1][2] * m[2][3] - m[0][2] * m[1][3] * m[2][1] - m[0][3] * m[1][1] * m[2][2];
		matrix.m[1][0] = m[1][0] * m[2][3] * m[3][2] + m[1][2] * m[2][0] * m[3][3] + m[1][3] * m[2][2] * m[3][0] - m[1][0] * m[2][2] * m[3][3] - m[1][2] * m[2][3] * m[3][0] - m[1][3] * m[2][0] * m[3][2];
		matrix.m[1][1] = m[0][0] * m[2][2] * m[3][3] + m[0][2] * m[2][3] * m[3][0] + m[0][3] * m[2][0] * m[3][2] - m[0][0] * m[2][3] * m[3][2] - m[0][2] * m[2][0] * m[3][3] - m[0][3] * m[2][2] * m[3][0];
		matrix.m[1][2] = m[0][0] * m[1][3] * m[3][2] + m[0][2] * m[1][0] * m[3][3] + m[0][3] * m[1][3] * m[3][0] - m[0][0] * m[1][2] * m[3][3] - m[0][2] * m[1][3] * m[3][0] - m[0][3] * m[1][0] * m[3][2];
		matrix.m[1][3] = m[0][0] * m[1][2] * m[2][3] + m[0][2] * m[1][3] * m[2][0] + m[0][3] * m[1][0] * m[2][2] - m[0][0] * m[1][3] * m[2][2] - m[0][2] * m[1][0] * m[2][3] - m[0][3] * m[1][2] * m[2][0];
		matrix.m[2][0] = m[1][0] * m[2][1] * m[3][3] + m[1][1] * m[2][3] * m[3][0] + m[1][3] * m[2][0] * m[3][1] - m[1][0] * m[2][3] * m[3][1] - m[1][1] * m[2][0] * m[3][3] - m[1][3] * m[2][1] * m[3][0];
		matrix.m[2][1] = m[0][0] * m[2][3] * m[3][1] + m[0][1] * m[2][0] * m[3][3] + m[0][3] * m[2][1] * m[3][0] - m[0][0] * m[2][1] * m[3][3] - m[0][1] * m[2][3] * m[3][0] - m[0][3] * m[2][0] * m[3][1];
		matrix.m[2][2] = m[0][0] * m[1][1] * m[3][3] + m[0][1] * m[1][3] * m[3][0] + m[0][3] * m[1][0] * m[3][1] - m[0][0] * m[1][3] * m[3][1] - m[0][1] * m[1][0] * m[3][3] - m[0][3] * m[1][1] * m[3][0];
		matrix.m[2][3] = m[0][0] * m[1][3] * m[2][1] + m[0][1] * m[1][0] * m[2][3] + m[0][3] * m[1][1] * m[2][0] - m[0][0] * m[1][1] * m[2][3] - m[0][1] * m[1][3] * m[2][0] - m[0][3] * m[1][0] * m[2][1];
		matrix.m[3][0] = m[1][0] * m[2][2] * m[3][1] + m[1][1] * m[2][0] * m[3][2] + m[1][2] * m[2][1] * m[3][0] - m[1][0] * m[2][1] * m[3][2] - m[1][1] * m[2][2] * m[3][0] - m[1][2] * m[2][0] * m[3][1];
		matrix.m[3][1] = m[0][0] * m[2][1] * m[3][2] + m[0][1] * m[2][2] * m[3][0] + m[0][2] * m[2][0] * m[3][1] - m[0][0] * m[2][2] * m[3][1] - m[0][1] * m[2][0] * m[3][2] - m[0][2] * m[2][1] * m[3][0];
		matrix.m[3][2] = m[0][0] * m[1][2] * m[3][1] + m[0][1] * m[1][0] * m[3][2] + m[0][2] * m[1][1] * m[3][0] - m[0][0] * m[1][1] * m[3][2] - m[0][1] * m[1][2] * m[3][0] - m[0][2] * m[1][0] * m[3][1];
		matrix.m[3][3] = m[0][0] * m[1][1] * m[2][2] + m[0][1] * m[1][2] * m[2][0] + m[0][2] * m[1][0] * m[2][1] - m[0][0] * m[1][2] * m[2][1] - m[0][1] * m[1][0] * m[2][2] - m[0][2] * m[1][1] * m[2][0];
		
		return matrix;
	}
	
	public Matrix4 mul(Matrix4 v) {
		Matrix4 r = new Matrix4();
		
		for(int x = 0; x < 4; x++) {
			for(int y = 0; y < 4; y++) {
				r.set(x, y, m[x][0] * v.get(0, y) +
							m[x][1] * v.get(1, y) +
							m[x][2] * v.get(2, y) +
							m[x][3] * v.get(3, y));
			}
		}
		
		return r;
	}
	
	public float get(int x, int y) {
		return m[x][y];
	}
	
	public void set(int x, int y, float v) {
		m[x][y] = v;
	}
	
	public float[][] getM() {
		return m;
	}
	
	public void setM(float[][] m) {
		this.m = m;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		int c = 0;
		for(int x = 0; x < 4; x++) {
			for(int y = 0; y < 4; y++) {
				if(c > 3) {
					sb.append("\n");
					c = 0;
				}
				
				sb.append(m[x][y] + " | ");
				c++;
			}
		}
		
		return sb.toString();
	}
	
}
