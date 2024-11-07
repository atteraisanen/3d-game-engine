package core.utils;

import org.joml.Vector3f;
import org.joml.Vector4f;

public class Consts {
    public static final String TITLE = "3D ENGINE TEST PROJECT";

    public static final float MOUSE_SENSITIVITY = 0.2f;
    public static final Vector4f DEFAULT_COLOUR = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
    public static final Vector3f AMBIENT_LIGHT = new Vector3f(0.3f,0.3f,0.3f);
    public static final float SPECULAR_POWER = 100f;
    public static final int MAX_SPOT_LIGHTS = 5;
    public static final int MAX_POINT_LIGHTS = 5;

    public static final float CAMERA_MOVE_SPEED = 3.0f;
}
