package DistanceFieldGlyphs;

import java.io.Serializable;

import android.content.res.Resources;

import com.threed.jpct.GLSLShader;
import com.threed.jpct.IRenderHook;
import com.threed.jpct.Loader;
import com.threed.jpct.Object3D;
import com.threed.jpct.Primitives;
import com.threed.jpct.SimpleVector;
import com.threed.jpct.Texture;
import com.threed.jpct.TextureInfo;
import com.threed.jpct.TextureManager;
import com.threed.jpct.World;

                                                   //serialization is desktop jpct only i believe
public class SpatialGlyph implements IRenderHook /*, Serializable*/  {
	
	
	private Object3D plane;
	private World world;
	SimpleVector colour;
	GLSLShader shader;
	
	
	SpatialGlyph(int texture ,Resources res ,String glyphname ,GLSLShader shader, World world, SimpleVector colour)
	{
		
		this.world=world;
		this.colour= colour;
		this.shader=shader;
		TextureManager tm = TextureManager.getInstance();
		Texture glyph = new Texture(res.openRawResource(texture),true);
		glyph.setMipmap(false);
		tm.addTexture(glyphname, glyph);
       
	
		plane = Primitives.getPlane(1, 40.0F);
        plane.setTexture(glyphname);
	    TextureManager.getInstance().getTexture(glyphname).setMipmap(false);

        
        plane.setRenderHook(this);
      	plane.setShader(shader);
      	plane.setTransparency(3);
      	plane.setBillboarding(true);   
	}
	
	
	
	public void setAttachmentObject(Object3D parent)
	{
		addToWorld();
		parent.addChild(plane);
		plane.translate(0,10,0);
		
		//plane.
		
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
		shader.setUniform("a_colour",colour);
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
