package core.utils;

public class Time {
    private float deltaTime;
    private static Time instance;
    private float timeSinceLastFixedUpdate = 0;
    private Time() {
    }

    public static Time getInstance() {
        if(instance == null) {
            instance = new Time();
        }
        return instance;
    }

    public void setDeltaTime(float time) {
        deltaTime = time;
    }

    public float getDeltaTime() {
        return deltaTime;
    }

    public void setTimeSinceLastFixedUpdate(float time) {
        timeSinceLastFixedUpdate = time;
    }

    public float getTimeSinceLastFixedUpdate() {
        return timeSinceLastFixedUpdate;
    }
}
