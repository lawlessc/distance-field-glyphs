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

public class DistanceFieldString  implements IRenderHook {
	
	private static final String a_colour = "a_colour";
	private static final String bottomLeftx = "bottomLeftx";
	private static final String bottomLefty = "bottomLefty";
	private static final String width = "width";
	private static final String height = "height";
	
	private static final String realwidth = "realwidth";
	private static final String realheight = "realheight";
	
	private static final String offsetx = "offsetx";
	private static final String offsety = "offsety";
	
	int screenx;
	int screeny;
	

	float screenposx;
	float screenposy;
	
	float x;
	float y;

	float texturesizex;
	float texturesizey;
	//float scale;
	
	float firstposDecX;
	float firstposDecY;
	SimpleVector colour;

	DistanceFieldCharacter[] charactersList;
	Overlay characterOverlay;
	GLSLShader Overlayshader_ = null;
	Object3D body;
    int renderingIndex;
	float advances;
	
	
	
	 // float ad = 0;
	  /**
		 * This is supposed to be called by the factory, not the user.
		 * @param res
		 * @throws FileNotFoundException
		 */
	public DistanceFieldString(String text, World world,FrameBuffer  framebufferReference,float x, float y,float scale, SimpleVector colour,
			         Map<String,DistanceFieldCharacter> characterData,GLSLShader shader,
			         int scx,
			         int scy          
			)
	{
		
		this.x=x;
		this.y=y;
		
		this.colour = colour;
		Overlayshader_ = shader;
		characterOverlay = new Overlay(world, framebufferReference,"characters");
	    renderingIndex=0;
	    advances=0;
	    charactersList = new DistanceFieldCharacter[text.length()];
	    
		for (int i = 0;i < text.length(); i++)
		 {
			
			 int  charnum=(int) text.charAt(i);                     //charnum
			 charactersList[i]=  characterData.get(Integer.toString(charnum));
		 }
	    
	    setUpOverlayStart();
		body =characterOverlay.getObject3D();
		body.setRenderHook(this);
		body.setShader(Overlayshader_);	
		body.setTransparency(3);
		
		screenx=scx;
		screeny=scy;
		
		screenposx  = (screenx/2);
		screenposy =  (screeny/2);
		texturesizex = 256/screenposx;
		texturesizey = 256/screenposy;
		

		
	}
	
	public void setUpOverlayStart()
	{
		 characterOverlay.setNewCoordinates((int)screenposx,(int)screenposx, (int)(screenposx+(256)),(int) (screenposy+(256)));
		 characterOverlay.setDepth(0.0f);
	}

	@Override
	public void afterRendering(int arg0) {
		// TODO Auto-generated method stub	
		//i MIGHT WANT TO SET SETUPOVERLAYSTART ETC HERE
	}

	@Override
	public void beforeRendering(int arg0) {
		renderNum(0);
		Overlayshader_.setUniform(offsetx,0f);
		Overlayshader_.setUniform(offsety,charactersList[0].offsetY);	
	}
	
	
	
	void renderNum(int renderingIndex)
	{	
		Overlayshader_.setUniform(a_colour,colour);
		Overlayshader_.setUniform(bottomLeftx,charactersList[renderingIndex].bottomLeftx);
		Overlayshader_.setUniform(bottomLefty, charactersList[renderingIndex].bottomLefty);	

		Overlayshader_.setUniform("screenposx",x);
		Overlayshader_.setUniform("screenposy",y);
		
		Overlayshader_.setUniform("texturesizex",texturesizex);
		Overlayshader_.setUniform("texturesizey",texturesizey);

		Overlayshader_.setUniform(realwidth,(charactersList[renderingIndex].realwidth));
		Overlayshader_.setUniform(realheight,(charactersList[renderingIndex].realheight));

		
		Overlayshader_.setUniform(width,charactersList[renderingIndex].width);
		Overlayshader_.setUniform(height,charactersList[renderingIndex].height);
	}
	
	
	@Override
	public boolean repeatRendering() {
		renderingIndex++;
		
		if(renderingIndex < charactersList.length && renderingIndex > 0)
		{
		if( 	renderingIndex==1)
		{
		advances = charactersList[0].advanceX;
		}
		
		

		renderNum(renderingIndex);

    	Overlayshader_.setUniform(offsetx, advances);
		Overlayshader_.setUniform(offsety,charactersList[renderingIndex].offsetY);
		advances = advances + (charactersList[renderingIndex].advanceX);
		
		return true;
		}

		setUpOverlayStart();
		renderingIndex=0;
		advances=0;
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
