package core.utils;

import core.entity.BoxCollider;
import core.entity.Entity;
import org.joml.Vector3f;

public class Collisions {

    //Check collision between two AABs (Axis Aligned Bounding Boxes)
    public static boolean isColliding(Vector3f aPos, BoxCollider aCollider, Vector3f bPos, BoxCollider bCollider) {
        boolean isColliding = (aCollider.getMinX() + aPos.x) <= (bCollider.getMaxX() + bPos.x) &&
                (aCollider.getMaxX() + aPos.x) >= (bCollider.getMinX() + bPos.x) &&
                (aCollider.getMinY() + aPos.y) <= (bCollider.getMaxY() + bPos.y) &&
                (aCollider.getMaxY() + aPos.y) >= (bCollider.getMinY() + bPos.y) &&
                (aCollider.getMinZ() + aPos.z) <= (bCollider.getMaxZ() + bPos.z) &&
                (aCollider.getMaxZ() + aPos.z) >= (bCollider.getMinZ() + bPos.z);
        return isColliding;
    }
    public static boolean isPointInsideAABB(Vector3f ePos, BoxCollider boxCollider, Vector3f point) {
        boolean isColliding = point.x >= (boxCollider.getMinX() + ePos.x) &&
                point.x <= (boxCollider.getMaxX() + ePos.x) &&
                point.y >= (boxCollider.getMinY() + ePos.y) &&
                point.y <= (boxCollider.getMaxY() + ePos.y) &&
                point.z >= (boxCollider.getMinZ() + ePos.z) &&
                point.z >= (boxCollider.getMaxZ() + ePos.z);
        return isColliding;
    }
}
