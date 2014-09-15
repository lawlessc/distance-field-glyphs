package com.threed.jpct.extprimitives;

import com.threed.jpct.Object3D;
import com.threed.jpct.SimpleVector;

public class ExtendedPrimitives {
	/**
	 * Creates a sphere with a specific radius.
	 * @param radius of the sphere
	 * @param quads Number of quads that should be used for the sphere. You should seriously not use more quads than
	 * you need!
	 * @return the created sphere
	 */
	public static Object3D createSphere(float radius, int quads) {
		float size = 2 * radius;
		return createEllipsoid(new SimpleVector(size, size, size), quads);
	}

	/**
	 * Creates an ellipsoid with given width, height and depth.
	 * @param size Vector that contains width, height and depth
	 * @param quads Number of quads that should be used for the sphere. You should seriously not use more quads than
	 * you need!
	 * @return the created ellipsoid
	 */
	public static Object3D createEllipsoid(SimpleVector size, int quads) {
		int yQuads = Math.max(quads / 2 + 1, 3);
		Object3D obj = new Object3D(2 * quads * yQuads);
		
		// Prepare vertices
		SimpleVector[][] v = new SimpleVector[quads][yQuads];
		for (int y = 0; y < yQuads; y++) {
			float yAngle = (float) (Math.PI * y / (yQuads - 1));
			float yPos = - 0.5f * size.y * (float) Math.cos(yAngle);
			float yRadius = (float) Math.sin(yAngle);
			for (int x = 0; x < quads; x++) {
				float xAngle = (float) (2 * Math.PI * x / quads);
				float xPos = 0.5f * size.x * (float) Math.cos(xAngle) * yRadius;
				float zPos = 0.5f * size.z * (float) Math.sin(xAngle) * yRadius;
				
				v[x][y] = new SimpleVector(xPos, yPos, zPos);
			}
		}
		
		// Create quads
		float v0 = 0;
		float v1 = 1f / (yQuads - 1);
		float vStep = v1;
		for (int y = 0; y + 1 < yQuads; y++) {
			for (int x = 0; x < quads; x++) {
				float u0 = 2f * (float) x / quads;
				float u1 = 2f * (float) (x + 1) / quads;
				if (y > 0) {
					obj.addTriangle(v[x][y], u0, v0,
							v[x][y + 1], u0, v1,
							v[(x + 1) % quads][y], u1, v0);
				}
				if (y + 2 < yQuads) {
					obj.addTriangle(v[(x + 1) % quads][y], u1, v0,
							v[x][y + 1], u0, v1,
							v[(x + 1) % quads][y + 1], u1, v1);
				}
			}
			
			v0 = v1;
			v1 += vStep;
		}
		
		return obj;
	}
	
	
	
	
	
	
	
	
	/**
	 * Creates an ellipsoid with given width, height and depth.
	 * @param size Vector that contains width, height and depth
	 * @param quads Number of quads that should be used for the sphere. You should seriously not use more quads than
	 * you need!
	 * @param uScale texture u scale, default is 2f
	 * @param vScale texture v scale, default is 1f
	 * @return the created ellipsoid
	 */
	public static Object3D createEllipsoid(SimpleVector size, int quads, float uScale, float vScale) {
		int yQuads = Math.max(quads / 2 + 1, 3);
		Object3D obj = new Object3D(2 * quads * yQuads);
		
		// Prepare vertices
		SimpleVector[][] v = new SimpleVector[quads][yQuads];
		for (int y = 0; y < yQuads; y++) {
			float yAngle = (float) (Math.PI * y / (yQuads - 1));
			float yPos = - 0.5f * size.y * (float) Math.cos(yAngle);
			float yRadius = (float) Math.sin(yAngle);
			for (int x = 0; x < quads; x++) {
				float xAngle = (float) (2 * Math.PI * x / quads);
				float xPos = 0.5f * size.x * (float) Math.cos(xAngle) * yRadius;
				float zPos = 0.5f * size.z * (float) Math.sin(xAngle) * yRadius;
				
				v[x][y] = new SimpleVector(xPos, yPos, zPos);
			}
		}
		
		// Create quads
		float v0 = 0;
		float v1 = vScale / (yQuads - 1);
		float vStep = v1;
		for (int y = 0; y + 1 < yQuads; y++) {
			for (int x = 0; x < quads; x++) {
				float u0 = uScale * (float) x / quads;
				float u1 = uScale * (float) (x + 1) / quads;
				if (y > 0) {
					obj.addTriangle(v[x][y], u0, v0,
							v[x][y + 1], u0, v1,
							v[(x + 1) % quads][y], u1, v0);
				}
				if (y + 2 < yQuads) {
					obj.addTriangle(v[(x + 1) % quads][y], u1, v0,
							v[x][y + 1], u0, v1,
							v[(x + 1) % quads][y + 1], u1, v1);
				}
			}
			
			v0 = v1;
			v1 += vStep;
		}
		
		return obj;
	}
	
	
	
	
}