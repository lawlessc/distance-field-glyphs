package DistanceFieldGlyphs;

import java.io.Serializable;

import android.content.res.Resources;

import com.threed.jpct.Camera;
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
	
	
	public Object3D plane;
	
	SimpleVector colour;
	GLSLShader shader;
	
	float offsetUp;
	float offsetRight;
	
	//these are to keep the object attached and rotating with the camera
	Camera    camera;// if connected to a camera
	Object3D subject;
	float distOut;
	//SimpleVector offset;
	
	
	SpatialGlyph(int texture ,Resources res ,String glyphname ,GLSLShader shader, SimpleVector colour)
	{
		
		//this.world=world;
		this.colour= colour;
		this.shader=shader;
		TextureManager tm = TextureManager.getInstance();
		Texture glyph = new Texture(res.openRawResource(texture),true);
		glyph.setMipmap(false);
		tm.addTexture(glyphname, glyph);
       
	
		plane = Primitives.getPlane(1, 4.9F);
        plane.setTexture(glyphname);
	    TextureManager.getInstance().getTexture(glyphname).setMipmap(false);
        
        plane.setRenderHook(this);
      	plane.setShader(shader);
      	plane.setTransparency(3);
      	plane.setBillboarding(true);   
	}
	

	
	
	//set a spatial glyph to be attached to a camera, and a distance in front of it.
	public void setAttachmentCamera(Camera cam , float dist , float offsetSide, float offsetUp)
	{
	camera = cam;
    this.distOut = dist;
	this.offsetRight=offsetSide;
	this.offsetUp=offsetUp;
	this.subject=null;
	}
	
	
	
	
	
	
	public void setAttachmentObjectCamera(Camera cam , Object3D obj , float offsetSide, float offsetUp)
	{
	camera = cam;
	this.subject=obj;
	
	this.offsetRight=offsetSide;
	this.offsetUp=offsetUp;
	this.distOut = 0;
	}
	

	
	public void update()
	{
		
	if(this.subject != null)
	{
    SimpleVector neworg = subject.getOrigin();
    
    SimpleVector side   = new SimpleVector(camera.getSideVector());
    side.scalarMul(offsetRight);
    
    SimpleVector up   = new SimpleVector(camera.getUpVector());
    up.scalarMul(offsetUp);
    
    neworg.add(side);
    neworg.add(up);

	plane.setOrigin(neworg);
	}
	else
	{	
	    SimpleVector neworg = camera.getDirection();
	    neworg.scalarMul(distOut);
	    
	    SimpleVector side   = new SimpleVector(camera.getSideVector());
	    side.scalarMul(offsetRight);
	    
	    SimpleVector up   = new SimpleVector(camera.getUpVector());
	    up.scalarMul(offsetUp);
	    
	    neworg.add(side);
	    neworg.add(up);

		plane.setOrigin(neworg);
	}
	
	
	
	
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
