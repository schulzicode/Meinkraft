#version 150

in vec3 texCoord;
in float ao;
in float fogFactor;

uniform sampler2DArray texture0;

out vec4 color;

void main() {
    color = texture(texture0, texCoord);
    
    if(color.a == 0)
        discard;
    
    color *= ao / 3.0;
    color = mix(vec4(1), color, fogFactor);
}