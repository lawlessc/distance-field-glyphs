precision highp float;
// VERTEX SHADER
//uniform mat4 modelViewProjectionMatrix;
uniform mat4 modelViewMatrix;
uniform mat4 projectionMatrix;

//These are for selecting the correct character from the texture
uniform float bottomLeftx;
uniform float bottomLefty;
uniform float width;
uniform float height;
//////////////////////////////

uniform float realwidth;
uniform float realheight;

uniform float texturesizex;
uniform float texturesizey;

uniform float offsetx;
uniform float offsety;


uniform vec3 a_colour;

attribute vec2 texture0;
attribute vec4 position;


varying vec4 v_color;
varying vec2 v_texCoord;


float normalizeX(float x)
{
float oldrange = (texturesizex)  ;
float newrange = (realwidth)  ;
float newvalue = (((x - texturesizex) * newrange) / oldrange);

return newvalue;
}

float normalizeY(float y)
{
//y = 1 + (x-A)*(10-1)/(B-A)

float oldrange = (texturesizey)  ;
float newrange = (realheight)  ;
float newvalue = (((y - texturesizey) * newrange) / oldrange);

return newvalue;
}


void main()
{

v_texCoord.x = bottomLeftx + (texture0.x/width);
v_texCoord.y = bottomLefty + (texture0.y/height);
v_color = vec4(a_colour,1.0);


vec4 nearlast = projectionMatrix*((modelViewMatrix  * position));


vec4 newpos = vec4( ((offsetx)*2.0)-1.0 ,((offsety)*2.0)+1.0,0,0);

gl_Position  = vec4(normalizeX(nearlast.x+1.0)-1.0,normalizeY(nearlast.y+1.0)-1.0,   nearlast.z,nearlast.w  )+newpos ;




}



 
 
