package DistanceFieldGlyphs;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
//import java.util.HashMap;
//import java.util.Map;
import java.util.Scanner;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import android.graphics.Rect;
import android.graphics.RectF;


import com.example.sdflibrary.R;
import com.threed.jpct.FrameBuffer;
import com.threed.jpct.GLSLShader;

import com.threed.jpct.Loader;

import com.threed.jpct.SimpleVector;
import com.threed.jpct.Texture;
//
import com.threed.jpct.TextureManager;
import com.threed.jpct.World;

//import com.threed.jpct.util.Overlay;

public class DistanceFieldFactory  {
	
	
	public GLSLShader characterShader = null;
	public GLSLShader glyphShader = null;
	
	
	static Bitmap Allcharacters;
	static int textureSize = 128;
   // int screen_dimension_x;
   // int screen_dimension_y;
	String textureName= null; //As their may be factories for multiple fonts, it's necessary to name their textures uniquely
	
	FrameBuffer framebufferReference;
    World worldReference;
    
    
    
    Map<String,DistanceFieldCharacter> characterData;
    
    
    int bitmapID;
	
	public  DistanceFieldFactory(FrameBuffer fb,World wr ,Resources res , 
			                     int character_positions_text, int bitmapid,String texturename)
	{
		framebufferReference=fb;
		//screen_dimension_x = framebufferReference.getWidth();
		//screen_dimension_y = framebufferReference.getHeight();
		worldReference=wr;
		bitmapID=bitmapid;
		textureName=texturename;
		setup(res,character_positions_text,bitmapid);
	}
	
	public SpatialGlyph createGlyph(int textureref, SimpleVector colour , Resources res ,String texturename )
	{
		SpatialGlyph newGlyph = new SpatialGlyph(textureref, res, texturename, glyphShader,worldReference,colour);
		return newGlyph;
	}
	
	

	
	public    DistanceFieldString   createText(String text,float x, float y, float scale)
	{
       DistanceFieldString testString = new  DistanceFieldString(text, worldReference,framebufferReference, 
                                              x,y, scale,new SimpleVector(1f,1f,1f),
                                              characterData,characterShader,
                                              framebufferReference.getWidth(), framebufferReference.getHeight(),
                                              textureName
                                              );
	return testString;
	}
	

	public void setup(Resources res ,int character_positions_text,int bitmapid)
	{
		characterShader = new GLSLShader(Loader.loadTextFile(res.openRawResource(R.raw.sdff_char_vert)), 
                Loader.loadTextFile(res.openRawResource(R.raw.sdff_frag)));
		
		glyphShader = new GLSLShader(Loader.loadTextFile(res.openRawResource(R.raw.sdf_vert)), 
                Loader.loadTextFile(res.openRawResource(R.raw.sdff_frag)));
		
		
		
		Allcharacters = getbmp(res ,bitmapid);
		
		   TextureManager tm = TextureManager.getInstance();
		   Texture tex=new Texture(Allcharacters);
		   tex.setMipmap(false);
		   tm.addTexture(textureName, tex);
		
	
		try {
			readCharacterPositions(res,character_positions_text);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * This should only be called at the on loading or at the start of game/program
	 * There is no reason to call it in game.
	 * @param res
	 * @throws FileNotFoundException
	 */
	public void readCharacterPositions(Resources res,int id) throws FileNotFoundException
	{
		int i=0;
		Scanner sc = new Scanner(res.openRawResource(id));
		
		
		
		while(sc.hasNextLine()) {
			
		
			String[] numstrs = sc.nextLine().split("\\s+"); // split by white space
			
			if(i == 1)
			{
				
				characterData= new HashMap<String,DistanceFieldCharacter>(Integer.parseInt(numstrs[1].substring(6)));
			
			}
			
			
			 
			if(i >= 2)
			{
		    String  ID =   numstrs[1].replaceAll("\\D", "");
		    int  x  =   Integer.parseInt(numstrs[2].substring(2));
		    int  y   =   Integer.parseInt(numstrs[3].substring(2));
		    float width = (float)Integer.parseInt(numstrs[4].substring(6));
		    float height = (float) Integer.parseInt(numstrs[5].substring(7));
		    float offsetX=Float.parseFloat(numstrs[6].substring(8));
		    float offsetY=Float.parseFloat(numstrs[7].substring(8));
		    
		    float ad =  Float.parseFloat(numstrs[8].substring(9));
		    
		    
		   
		                                             
		    DistanceFieldCharacter newChar = new DistanceFieldCharacter(Allcharacters.getWidth(),
		    		                                x,y,width,height ,offsetX,offsetY ,ad,
		    		                                characterShader,  
		    		                                framebufferReference.getWidth(),
		    		                                framebufferReference.getHeight());
		    characterData.put(ID, newChar);
			}
			

		i++;
		}
	
		
	}
	
	public static Bitmap printCharacterToBitmap(Rect origin ,RectF position) {
	    Bitmap bmOverlay  = Bitmap.createBitmap(textureSize, textureSize, Bitmap.Config.ARGB_4444);
	    Canvas canvas = new Canvas(bmOverlay);
	    canvas.drawBitmap(Allcharacters, origin, position, null);
	    return bmOverlay;
	}
	
	public  Bitmap getbmp(Resources res, int id){
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPreferredConfig = Bitmap.Config.ARGB_4444;
		options.inScaled=false;
		Bitmap bitmap = BitmapFactory.decodeResource(res,id,options);
	return bitmap;
	}
	
}
