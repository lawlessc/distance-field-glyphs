package com.threed.jpct.shader;

import java.lang.reflect.Field;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import DistanceFieldGlyphs.DistanceFieldFactory;
import DistanceFieldGlyphs.SpatialGlyph;
import android.app.Activity;
import android.content.res.Resources;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;

import com.threed.jpct.Camera;
import com.threed.jpct.FrameBuffer;
import com.threed.jpct.GLSLShader;
import com.threed.jpct.ITextureEffect;
import com.threed.jpct.Light;
import com.threed.jpct.Loader;
import com.threed.jpct.Logger;
import com.threed.jpct.Object3D;
import com.threed.jpct.Primitives;
import com.threed.jpct.RGBColor;
import com.threed.jpct.SimpleVector;
import com.threed.jpct.Texture;
import com.threed.jpct.TextureInfo;
import com.threed.jpct.TextureManager;
import com.threed.jpct.World;
import com.threed.jpct.util.MemoryHelper;

/**
 * 
 * 
 * @author EgonOlsen
 * 
 */
public class HelloShader extends Activity implements OnScaleGestureListener {

	// Used to handle pause and resume...
	private static HelloShader master = null;

	private ScaleGestureDetector gestureDec = null;

	private GLSurfaceView mGLView;
	private MyRenderer renderer = null;
	private FrameBuffer fb = null;
	private World world = null;
	private RGBColor back = new RGBColor(50, 50, 100);

	private float touchTurn = 0;
	private float touchTurnUp = 0;

	private float xpos = -1;
	private float ypos = -1;

	private Texture font = null;

	private Object3D plane;
	private Light light;

	private GLSLShader shader = null;

	private float scale = 0.05f;
	
	DistanceFieldFactory latin_txt;
	DistanceFieldFactory rongorongo_txt;
	DistanceFieldFactory hieroglyphs_txt;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		Logger.log("onCreate");
		Logger.setLogLevel(Logger.LL_DEBUG);

		if (master != null) {
			copy(master);
		}

		super.onCreate(savedInstanceState);
		mGLView = new GLSurfaceView(getApplication());

		// Enable the OpenGL ES2.0 context
		mGLView.setEGLContextClientVersion(2);

		renderer = new MyRenderer();
		mGLView.setRenderer(renderer);
		setContentView(mGLView);

		gestureDec = new ScaleGestureDetector(this.getApplicationContext(), this);
	}

	@Override
	protected void onPause() {
		Logger.log("onPause");
		super.onPause();
		mGLView.onPause();
	}

	@Override
	protected void onResume() {
		Logger.log("onResume");
		super.onResume();
		mGLView.onResume();
	}

	@Override
	protected void onStop() {
		Logger.log("onStop");
		super.onStop();
	}

	private void copy(Object src) {
		try {
			Logger.log("Copying data from master Activity!");
			Field[] fs = src.getClass().getDeclaredFields();
			for (Field f : fs) {
				f.setAccessible(true);
				f.set(this, f.get(src));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public boolean onTouchEvent(MotionEvent me) {

		gestureDec.onTouchEvent(me);

		if (me.getAction() == MotionEvent.ACTION_DOWN) {
			xpos = me.getX();
			ypos = me.getY();
			return true;
		}

		if (me.getAction() == MotionEvent.ACTION_UP) {
			xpos = -1;
			ypos = -1;
			touchTurn = 0;
			touchTurnUp = 0;
			return true;
		}

		if (me.getAction() == MotionEvent.ACTION_MOVE) {
			float xd = me.getX() - xpos;
			float yd = me.getY() - ypos;

			xpos = me.getX();
			ypos = me.getY();

			touchTurn = xd / -100f;
			touchTurnUp = yd / -100f;
			return true;
		}

		try {
			Thread.sleep(15);
		} catch (Exception e) {
			// No need for this...
		}

		return super.onTouchEvent(me);
	}

	protected boolean isFullscreenOpaque() {
		return true;
	}

	class MyRenderer implements GLSurfaceView.Renderer {

		private int fps = 0;
		private int lfps = 0;

		private long time = System.currentTimeMillis();

		public MyRenderer() {
			Texture.defaultToMipmapping(true);
			Texture.defaultTo4bpp(true);
		}

		public void onSurfaceChanged(GL10 gl, int w, int h) {
			if (fb != null) {
				fb.dispose();
			}

			Resources res = getResources();

			fb = new FrameBuffer(w, h);

			if (master == null) {
				world = new World();
                
				Camera cam = world.getCamera();
				TextureManager tm = TextureManager.getInstance();

				Texture face = new Texture(res.openRawResource(R.raw.face));
				Texture normals = new Texture(res.openRawResource(R.raw.face_norm), true);
				Texture height = new Texture(res.openRawResource(R.raw.face_height2));

				plane = Primitives.getPlane(1, 100);
				////////////////////////////////////////////////////////////////////////////
				//////////////This code demonstrates the distance field factories being set up

				latin_txt = new DistanceFieldFactory(fb,world,res,
						R.raw.characterpositions,R.raw.sdffont ,"latintexture");
				

			   rongorongo_txt = new DistanceFieldFactory(fb,world,res,
						R.raw.rongopos , R.raw.rongosdf,"rongotexture");
				
			   
			   hieroglyphs_txt = new DistanceFieldFactory(fb,world,res,
						R.raw.hieroglyphspos , R.raw.hieroglyphssdf,"hierotexture");
			   
			   
			   SpatialGlyph testxx = latin_txt.createGlyph(R.raw.eye_of_horussdf, new SimpleVector(1f, 1f,1f), res,"testexture");
			   //testxx.setAttachmentObject(plane);
			   
			   testxx.setAttachmentObjectCamera(cam, plane, 4.5f,  3.5f);
			   ////////This is the same factories being used to generate text;
			   
			   rongorongo_txt.createText("Hello World ABCPp Kk", 1.0f, 1.4f, 1f)	;
	
			   latin_txt.createText("Hello World !2�$%^321 ", 0.9f, 1.0f, 1f)	;
			   
			   
			   hieroglyphs_txt.createText("Hello World ", 1.0f, 0.5f, 1f)	;
			  

			 
			   
			  
			/////////////////////////////////////////////////////////////////	

				TexelGrabber grabber = new TexelGrabber();
				height.setEffect(grabber);
				height.applyEffect();
				int[] heighties = grabber.getAlpha();

				AlphaMerger setter = new AlphaMerger(heighties);
				normals.setEffect(setter);
				normals.applyEffect();

				font = new Texture(res.openRawResource(R.raw.numbers));
				font.setMipmap(false);

				tm.addTexture("face", face);
				tm.addTexture("normals", normals);

				TextureInfo ti = new TextureInfo(TextureManager.getInstance().getTextureID("face"));
				ti.add(TextureManager.getInstance().getTextureID("normals"), TextureInfo.MODE_BLEND);

				plane.setTexture(ti);

				shader = new GLSLShader(Loader.loadTextFile(res.openRawResource(R.raw.vertexshader_offset)), Loader.loadTextFile(res.openRawResource(R.raw.fragmentshader_offset)));
				plane.setShader(shader);
				plane.setSpecularLighting(true);
				shader.setStaticUniform("invRadius", 0.0003f);

				plane.build();
				plane.strip();

				world.addObject(plane);

				light = new Light(world);
				light.enable();

				light.setIntensity(60, 50, 50);
				light.setPosition(SimpleVector.create(-10, -50, -100));

				world.setAmbientLight(10, 10, 10);

				
				cam.moveCamera(Camera.CAMERA_MOVEOUT, 70);
				cam.lookAt(plane.getTransformedCenter());

				MemoryHelper.compact();

				world.compileAllObjects();

				if (master == null) {
					Logger.log("Saving master Activity!");
					master = HelloShader.this;
				}
			}
		}

		public void onSurfaceCreated(GL10 gl, EGLConfig config) {
			Logger.log("onSurfaceCreated");
		}

		public void onDrawFrame(GL10 gl) {
			if (touchTurn != 0) {
				plane.rotateY(touchTurn);
				touchTurn = 0;
			}

			if (touchTurnUp != 0) {
				plane.rotateX(touchTurnUp);
				touchTurnUp = 0;
			}

			shader.setUniform("heightScale", scale);

			fb.clear(back);
			world.renderScene(fb);
			world.draw(fb);
			blitNumber(lfps, 5, 5);
			fb.display();

			if (System.currentTimeMillis() - time >= 1000) {
				lfps = fps;
				fps = 0;
				time = System.currentTimeMillis();
			}
			fps++;
		}

		private void blitNumber(int number, int x, int y) {
			if (font != null) {
				String sNum = Integer.toString(number);

				for (int i = 0; i < sNum.length(); i++) {
					char cNum = sNum.charAt(i);
					int iNum = cNum - 48;
					fb.blit(font, iNum * 5, 0, x, y, 5, 9, 5, 9, 10, true, null);
					x += 5;
				}
			}
		}
	}

	public boolean onScale(ScaleGestureDetector detector) {
		float div = detector.getCurrentSpan() - detector.getPreviousSpan();
		div /= 5000;

		scale += div;

		if (scale > 0.063f) {
			scale = 0.063f;
		}
		if (scale < 0) {
			scale = 0;
		}

		return true;
	}

	public boolean onScaleBegin(ScaleGestureDetector detector) {
		// TODO Auto-generated method stub
		return true;
	}

	public void onScaleEnd(ScaleGestureDetector detector) {
		// TODO Auto-generated method stub
	}

	/**
	 * Merges the height map into the alpha channel of the normal map.
	 * 
	 * @author EgonOlsen
	 * 
	 */
	private static class AlphaMerger implements ITextureEffect {

		private int[] alpha = null;

		public AlphaMerger(int[] alpha) {
			this.alpha = alpha;
		}

		public void apply(int[] arg0, int[] arg1) {
			int end = arg1.length;
			for (int i = 0; i < end; i++) {
				arg0[i] = arg1[i] & 0x00ffffff | alpha[i];
			}
		}

		public boolean containsAlpha() {
			return true;
		}

		public void init(Texture arg0) {
			// TODO Auto-generated method stub
		}
	}

	/**
	 * Extracts the alpha channel from a texture.
	 * 
	 * @author EgonOlsen
	 * 
	 */
	private static class TexelGrabber implements ITextureEffect {

		private int[] alpha = null;

		public void apply(int[] arg0, int[] arg1) {
			alpha = new int[arg1.length];
			int end = arg1.length;
			for (int i = 0; i < end; i++) {
				alpha[i] = (arg1[i] << 24);
			}
		}

		public int[] getAlpha() {
			return alpha;
		}

		public boolean containsAlpha() {
			return true;
		}

		public void init(Texture arg0) {
			// TODO Auto-generated method stub
		}
	}
}
