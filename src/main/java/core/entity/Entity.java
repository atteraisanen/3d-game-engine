package core.entity;

import org.joml.Vector3f;

public class Entity {
    private Model model;
    private Vector3f pos, rotation;
    private BoxCollider boxCollider;
    private float scale;
    private boolean isStatic;
    private boolean collision;
    private String name;

    public Entity(String name, Model model, Vector3f pos, Vector3f rotation, float scale, boolean isStatic, boolean collision) {
        this.model = model;
        this.pos = pos;
        this.rotation = rotation;
        this.scale = scale;
        this.isStatic = isStatic;
        this.collision = collision;
        this.boxCollider = null;
        this.name = name;
    }

    public Entity(String name, Model model, Vector3f pos, Vector3f rotation, float scale, boolean isStatic, boolean collision, BoxCollider boxCollider) {
        this.model = model;
        this.pos = pos;
        this.rotation = rotation;
        this.scale = scale;
        this.isStatic = isStatic;
        this.collision = collision;
        this.boxCollider = boxCollider;
        this.name = name;
    }

    public boolean isStatic() {
        return isStatic;
    }

    public void setStatic(boolean aStatic) {
        isStatic = aStatic;
    }

    public boolean isCollision() {
        return collision;
    }

    public void setCollision(boolean collision) {
        this.collision = collision;
    }

    public void incPos(float x, float y, float z) {
        this.pos.x += x;
        this.pos.y += y;
        this.pos.z += z;
    }

    public void setPos(float x, float y, float z) {
        this.pos.x = x;
        this.pos.y = y;
        this.pos.z = z;
    }

    public void incRotation(float x, float y, float z) {
        this.rotation.x += x;
        this.rotation.y += y;
        this.rotation.z += z;
    }

    public void setRotation(float x, float y, float z) {
        this.rotation.x = x;
        this.rotation.y = y;
        this.rotation.z = z;
    }

    public Model getModel() {
        return model;
    }

    public Vector3f getPos() {
        return pos;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public float getScale() {
        return scale;
    }

    public BoxCollider getBoxCollider() {
        return boxCollider;
    }

    public void setBoxCollider(BoxCollider boxCollider) {
        this.boxCollider = boxCollider;
    }

    public float distanceTo(Vector3f p) {
        return (float)Math.sqrt(Math.pow(this.pos.x - p.x, 2f) + Math.pow(pos.y - p.y, 2f) + Math.pow(pos.z - p.z, 2f));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
