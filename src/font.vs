#version 330

uniform mat4 p;
uniform mat4 v;
uniform mat4 m;

layout(location = 0) in vec2 position;
layout(location = 1) in vec2 texCoord;
layout(location = 2) in vec3 color;

out vec3 pass_color;
out vec2 pass_texCoord;

void main() {
    gl_Position = p * v * m * vec4(position, 0.0, 1.0);
    pass_color = color;
    pass_texCoord = texCoord;
}