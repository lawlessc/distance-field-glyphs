precision lowp float;
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

uniform float screenposx;
uniform float screenposy;

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
//y = 1 + (x-A)*(10-1)/(B-A)
 // float toRet=   /*1+*/((x)*(realwidth))/(texturesizex);
 
float oldrange = (texturesizex)  ;
float newrange = (realwidth)  ;
float newvalue = (((x - texturesizex) * newrange) / oldrange);

  
 //   float toRet=   /*1+*/(x-screenposx)*((screenposx+realwidth)-screenposx)/((screenposx+texturesizex)-screenposx);
  //toRet*=2;
 // toRet -=1;
//  toRet+=0.5;

 // newvalue*=2;
//   newvalue -=1;
//newvalue -=1;
return newvalue;
}

float normalizeY(float y)
{
//y = 1 + (x-A)*(10-1)/(B-A)
//float toRet=   /*1+*/((y)*(realheight))/(texturesizey);


float oldrange = (texturesizey)  ;
float newrange = (realheight)  ;
float newvalue = (((y - texturesizey) * newrange) / oldrange);

//float toRet=   /*1+*/(y-screenposy)*((screenposy+realheight)-screenposy)/((screenposy+texturesizey)-screenposy);
 // toRet*=2;
 // toRet -=1;
 
  //newvalue*=2;
 // newvalue +=1;
 
  
return newvalue;
}



void main()
{

v_texCoord.x = bottomLeftx + (texture0.x/width);
v_texCoord.y = bottomLefty + (texture0.y/height);
v_color = vec4(a_colour,1.0);



vec4 nearlast = projectionMatrix*((modelViewMatrix  * position));


//vec4 newpos = vec4(       ,   ,0,0);
vec4 newpos = vec4( ((offsetx)*2)-1 ,((offsety)*2)-1,0,0);

gl_Position  = vec4(normalizeX(nearlast.x+1)-1,normalizeY(nearlast.y+1)-1,   nearlast.z,nearlast.w  )+newpos ;

//vec4 newpos = vec4(offsetx,offsety,0,0);
//vec4 nearlast = projectionMatrix*((modelViewMatrix  * position) +newpos);

//gl_Position  = vec4(normalizeX(nearlast.x),normalizeY(nearlast.y),nearlast.z,nearlast.w);


}



 
 
