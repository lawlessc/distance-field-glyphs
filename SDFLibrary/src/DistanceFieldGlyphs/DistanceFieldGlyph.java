package DistanceFieldGlyphs;

import java.io.FileNotFoundException;
import java.util.Map;

import android.util.Log;

import com.threed.jpct.FrameBuffer;
import com.threed.jpct.GLSLShader;
import com.threed.jpct.IRenderHook;
import com.threed.jpct.Object3D;
import com.threed.jpct.SimpleVector;
import com.threed.jpct.World;
import com.threed.jpct.util.Overlay;

public class DistanceFieldGlyph  implements IRenderHook {
	
	private static final String a_colour = "a_colour";

	SimpleVector colour;
	Overlay characterOverlay;
	GLSLShader glyphShader = null;
	Object3D body;

	public DistanceFieldGlyph(String text, World world,FrameBuffer  framebufferReference,int x, int y,float scale, SimpleVector colour,
			         GLSLShader shader
			               
			)
	{
		this.colour = colour;
		glyphShader = shader;
		characterOverlay = new Overlay(world, framebufferReference,"characters");

	   
	    characterOverlay.setNewCoordinates(0,0,0,0);
		characterOverlay.setDepth(0.0f);
	    
		body =characterOverlay.getObject3D();
		body.setRenderHook(this);
		body.setShader(glyphShader);	
		body.setTransparency(3);

	}

	@Override
	public void afterRendering(int arg0) {
	}

	@Override
	public void beforeRendering(int arg0) {
		glyphShader.setUniform(a_colour,colour);
	}

	@Override
	public boolean repeatRendering() {
		return false;
	}
	
	@Override
	public void onDispose() {
	}

	@Override
	public void setCurrentObject3D(Object3D arg0) {
	}

	@Override
	public void setCurrentShader(GLSLShader arg0) {
	}

	@Override
	public void setTransparency(float arg0) {
	}








}
