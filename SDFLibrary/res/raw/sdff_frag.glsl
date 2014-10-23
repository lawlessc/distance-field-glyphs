precision highp float;
// FRAGMENT SHADER

uniform sampler2D textureUnit0;
varying vec2 v_texCoord;
varying vec4 v_color;
//varying float dontFill;

void main()
{ 
// retrieve distance from texture
float mask = texture2D( textureUnit0, v_texCoord).a;
// use current drawing color
vec4 clr;
clr = v_color;
// perform simple thresholding
if( mask < 0.5 )
clr.a = 0.0;
else
clr.a=1.0;

clr.a *= smoothstep(0.25, 0.75, mask);

gl_FragColor = clr; 
}

