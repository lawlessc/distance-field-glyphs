package DistanceFieldGlyphs;

import java.io.Serializable;

import android.content.res.Resources;

import com.threed.jpct.GLSLShader;
import com.threed.jpct.IRenderHook;
import com.threed.jpct.Loader;
import com.threed.jpct.Object3D;
import com.threed.jpct.Primitives;
import com.threed.jpct.Texture;
import com.threed.jpct.TextureInfo;
import com.threed.jpct.TextureManager;
import com.threed.jpct.World;

                                                   //serialization is desktop jpct only i believe
public class SpatialGlyph implements IRenderHook /*, Serializable*/  {
	
	
	private Object3D plane;
	private World world;
	
	
	
	SpatialGlyph(int texture ,Resources res ,String glyphname ,GLSLShader shader)
	{
		TextureManager tm = TextureManager.getInstance();
		Texture glyph = new Texture(res.openRawResource(texture),true);
		tm.addTexture(glyphname, glyph);

	
		plane = Primitives.getPlane(1, 1.0F);
        plane.setTexture(glyphname);
	    TextureManager.getInstance().getTexture(glyphname).setMipmap(false);



//		shader = new GLSLShader(Loader.loadTextFile(res.openRawResource(R.raw.vertexshader_offset)), Loader.loadTextFile(res.openRawResource(R.raw.fragmentshader_offset)));
//		plane.setShader(shader);
//		plane.setSpecularLighting(true);
//		shader.setStaticUniform("invRadius", 0.0003f);
//
//		plane.build();
//		plane.strip();
//
//		world.addObject(plane);
	}
	
	
	
	public void setAttachmentObject()
	{
		
		
	}
	
	public void addToWorld()
	{
		world.addObject(plane);
	}
	
	public void removeFromWorld()
	{
		world.addObject(plane);
	}




	@Override
	public void afterRendering(int arg0) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void beforeRendering(int arg0) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void onDispose() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public boolean repeatRendering() {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public void setCurrentObject3D(Object3D arg0) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void setCurrentShader(GLSLShader arg0) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void setTransparency(float arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	
	

}
