#version 330

uniform sampler2D atlas;
uniform float size;

in vec3 pass_color;
in vec2 pass_texCoord;

out vec4 outColor;

void main() {
    float smoothing = 1.0 / (16.0 * size);
    float distance = 1.0 - texture(atlas, pass_texCoord).a;
    float alpha = 1.0 - smoothstep(0.5 - smoothing, 0.5 + smoothing, distance);

    //if(alpha <= 0.0)
    //    discard;
    
    outColor = vec4(1.0, 1.0, 0, alpha);
}