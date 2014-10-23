package DistanceFieldGlyphs;

import com.threed.jpct.ITextureEffect;
import com.threed.jpct.Texture;

/**
 * Extracts the alpha channel from a texture.
 * 
 * @author EgonOlsen
 * 
 */
public class TexelGrabber implements ITextureEffect {

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