precision highp float;
// VERTEX SHADER
uniform mat4 modelViewProjectionMatrix;

attribute vec2 texture0;
attribute vec4 position;
varying vec4 v_color;
varying vec2 v_texCoord;


void main()
{

v_texCoord.x = texture0.x;
v_texCoord.y = texture0.y;
v_color = vec4(a_colour,1.0);

gl_Position =  modelViewProjectionMatrix * vec4(position.xyz,1.0)
}



 
 
