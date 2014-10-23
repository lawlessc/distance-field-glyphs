package DistanceFieldGlyphs;

import com.threed.jpct.ITextureEffect;
import com.threed.jpct.Texture;

/**
 * Merges the height map into the alpha channel of the normal map.
 * 
 * @author EgonOlsen
 * 
 */

public class AlphaMerger implements ITextureEffect {

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
