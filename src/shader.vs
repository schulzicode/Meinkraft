#version 150
#extension GL_ARB_explicit_attrib_location: enable

layout(location = 0) in vec3 inPosition;
layout(location = 1) in vec3 inTexCoord;
layout(location = 2) in float inAO;

uniform mat4 p;
uniform mat4 v;
uniform mat4 m;

uniform bool enableFog;

const float fogDensity = 0.04;
const float fogStart = 60;

out vec3 position;
out vec3 texCoord;
out float ao;
out float fogFactor;

void main() {
	gl_Position = p * v * m * vec4(inPosition, 1);
    position = inPosition;
    texCoord = inTexCoord;
    ao = inAO;
    
    float fogFragCoord = length(v * m * vec4(inPosition, 1));
    if(!enableFog || fogFragCoord < fogStart)
        fogFragCoord = 0;
    else
        fogFragCoord = fogFragCoord - fogStart;
    
    fogFactor = exp(-fogDensity * fogDensity * fogFragCoord * fogFragCoord * 1.442695);
    fogFactor = clamp(fogFactor, 0.0, 1.0);
}