#version 110

uniform mat4 p;
uniform mat4 v;
uniform mat4 m;

varying vec4 color;

void main() {
    gl_Position = p * v * m * gl_Vertex;
    color = gl_Color;
}