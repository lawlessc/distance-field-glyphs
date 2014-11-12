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
	
	
	private Object3D plane;
	
	private World world;
	SimpleVector colour;
	GLSLShader shader;
	
	
	//these are to keep the object attached and rotating with the camera
	Camera    camera;// if connected to a camera
	Object3D  camobj;
	Object3D  subject;
	//SimpleVector offset;
	
	SpatialGlyph(int texture ,Resources res ,String glyphname ,GLSLShader shader, World world, SimpleVector colour)
	{
		
		this.world=world;
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
	
	
	

	
	public void setAttachmentObjectCamera(Camera cam , Object3D obj)
	{
	camera = cam;
	
	camobj =// Object3D.createDummyObj();
			 Primitives.getBox(1, 1);
	subject= obj;
	addToWorld();
	camobj.setOrigin(new SimpleVector(0,0,0));
	plane.translate(new SimpleVector(0,0,10));
	//plane.translate(new SimpleVector(0,0,10));
	
	
//	if(plane.hasParent(camobj) == false)
//	{
//	camobj.addChild(plane);
//	}
	

	}
	
	public void addToWorld()
	{
		world.addObject(camobj);
		world.addObject(plane);
	}
	
	
	
	public void update()
	{	
  // camobj.setOrigin(subject.getOrigin());
  
   //camobj.translate(subject.getOrigin());
		
   plane.setOrigin(subject.getOrigin());
   
 //  camobj.setCenter(subject.getOrigin());
  
 //  plane
   plane.setOrientation(camera.getDirection().normalize(), camera.getUpVector());
  
	
   
  // System.out.println("Plane Origin"+ plane.getOrigin() +"translation"+ plane.getTranslation() );
   
   
//	System.out.println("Camera direction  vector"+ camera.getDirection());
//	System.out.println("Camera up  vector"+ camera.getUpVector());
	
 //  camobj.setr
	}
	
	
	
	
	
	
	
	public void removeFromWorld()
	{
		
		if(plane.hasParent(camobj) == true)
		{
		plane.removeParent(camobj);
		}
		world.removeObject(plane);
		
		camera=null;
		camobj=null;
		subject=null;
		
		
	}
	
//	public SimpleVector positionRotation(SimpleVector focalPoint)
//	{
//		
//		SimpleVector direction = new SimpleVector(
//				Math.cos(verticalAngle) * Math.sin(horizontalAngle),
//				Math.sin(verticalAngle),
//				Math.cos(verticalAngle) * Math.cos(horizontalAngle)
//				);
//		direction.scalarMul(400/(scaleFactor));
//		focalPoint.sub(direction);
//			
//	return focalPoint;
//	}
	





	@Override
	public void afterRendering(int arg0) {
		shader.setUniform("a_colour",colour);
		// TODO Auto-generated method stub
		
	}



	@Override
	public void beforeRendering(int arg0) {
		update();
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
