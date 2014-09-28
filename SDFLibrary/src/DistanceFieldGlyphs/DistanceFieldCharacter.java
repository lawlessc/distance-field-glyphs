package DistanceFieldGlyphs;

import com.threed.jpct.GLSLShader;
import com.threed.jpct.IRenderHook;
import com.threed.jpct.Object3D;
import com.threed.jpct.SimpleVector;
/*
 * This is a renderhook for rendering each
 * character in a text. 
 * 
 */


public class DistanceFieldCharacter {
	
	String id;

	float   bottomLeftx;
	float   bottomLefty;

	float width;
	float height;
	
	float realwidth;
	float realheight;

	float offsetX;
	float offsetY;
	
	float advanceX;
	
	
	DistanceFieldCharacter(int Size,float x,float y,float w,float h,float offsetx,float offsety,float advance,GLSLShader Ov,
			 int screenx,int screeny)
	{
		float mainTextureSize=(float)Size;
		
		bottomLeftx = (x/mainTextureSize);
		bottomLefty = (y/mainTextureSize);

		width = mainTextureSize/w ;
		height = mainTextureSize/h ;
		
		realwidth  = (w/(float)screenx);   
		realheight = (h/(float)screeny);
		
		offsetX= (offsetx/(float)screenx);
		offsetY= (offsety/(float)screeny);
		advanceX= advance/(float)screenx;
	}

    
}
