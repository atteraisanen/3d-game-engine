package test;

import core.*;
import core.entity.*;
import core.entity.terrain.BlendMapTerrain;
import core.entity.terrain.Terrain;
import core.entity.terrain.TerrainTexture;
import core.lighting.DirectionalLight;
import core.lighting.PointLight;
import core.lighting.SpotLight;
import core.rendering.RenderManager;
import core.utils.Collisions;
import core.utils.Consts;
import core.utils.Time;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestGame implements ILogic {

    private final RenderManager renderer;
    private final WindowManager window;
    private final ObjectLoader loader;
    private SceneManager sceneManager;


    private Camera camera;

    Vector3f cameraInc;
    Random rnd = new Random();



    public TestGame() {
        renderer = new RenderManager();
        window = Launcher.getWindowManager();
        loader = new ObjectLoader();
        camera = new Camera();
        cameraInc = new Vector3f(0,0,0);
        sceneManager = new SceneManager(-90);
    }
    @Override
    public void init() throws Exception {
        renderer.init();

        Model model = loader.loadObjModel("/models/cube2.obj");
        model.setTexture(new Texture(loader.loadTexture("textures/stone.png")), 1f);

        Model car = loader.loadObjModel("/models/carFixed.obj");
        car.setTexture(new Texture(loader.loadTexture("textures/car.png")));

        TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("textures/terrain.png"));
        TerrainTexture redTexture = new TerrainTexture(loader.loadTexture("textures/flowers.png"));
        TerrainTexture greenTexture = new TerrainTexture(loader.loadTexture("textures/stone.png"));
        TerrainTexture blueTexture = new TerrainTexture(loader.loadTexture("textures/dirt.png"));
        TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("textures/blendmap.png"));

        BlendMapTerrain blendMapTerrain = new BlendMapTerrain(backgroundTexture, redTexture, greenTexture, blueTexture);


        Terrain terrain = new Terrain(new Vector3f(0,0,-800), loader, new Material(new Vector4f(0.0f,0.0f,0.0f,0.0f), 1.0f),
                blendMapTerrain, blendMap);
        Terrain terrain2 = new Terrain(new Vector3f(-800,0,-800), loader, new Material(new Vector4f(0.0f,0.0f,0.0f,0.0f), 1.0f),
                blendMapTerrain, blendMap);
        sceneManager.addTerrain(terrain);
        sceneManager.addTerrain(terrain2);

        for(int i = 0; i < 500; i++) {
            float x = -800 + rnd.nextFloat() * (1600-(-800));
            float z = rnd.nextFloat() * -800;
            float y = rnd.nextFloat() * 50;
            sceneManager.addEntity(new Entity("Cube " + i,model, new Vector3f(x, y, z),
                    new Vector3f(rnd.nextFloat() * 800,rnd.nextFloat() * 800, rnd.nextFloat() * 800), 1, true, false));
        }
        BoxCollider blockCollider = new BoxCollider(-1, 1, -0.9f, 1.1f, -1, 1);
        sceneManager.addEntity(new Entity("Collidercube1", model, new Vector3f(0,50,-5), new Vector3f(0,0,0), 1, false, true, blockCollider));
        sceneManager.addEntity(new Entity("Collidercube2",model, new Vector3f(0,2,-5), new Vector3f(0,0,0), 1, true, true, blockCollider));




        //directional light
        float lightIntensity = 1f;
        Vector3f lightPosition = new Vector3f(0, 300, -800);
        Vector3f lightColour = new Vector3f(1,1,1);
        sceneManager.setDirectionalLight(new DirectionalLight(lightColour, lightPosition, lightIntensity));
        //sceneManager.setPointLights(new PointLight[]{});
        //sceneManager.setSpotLights(new SpotLight[]{});

    }

    @Override
    public void input() {

        //Camera movement
        cameraInc.set(0,0,0);
        if(window.isKeyPressed(GLFW.GLFW_KEY_W))
            cameraInc.z = -1;
        if(window.isKeyPressed(GLFW.GLFW_KEY_S))
            cameraInc.z = 1;
        if(window.isKeyPressed(GLFW.GLFW_KEY_A))
            cameraInc.x = -1;
        if(window.isKeyPressed(GLFW.GLFW_KEY_D))
            cameraInc.x = 1;
        if(window.isKeyPressed(GLFW.GLFW_KEY_LEFT_CONTROL))
            cameraInc.y = -1;
        if(window.isKeyPressed(GLFW.GLFW_KEY_SPACE))
            cameraInc.y = 1;
    }

    @Override
    public void update(MouseInput mouseInput) {
        fixedUpdate();
        camera.movePosition(cameraInc.x * Consts.CAMERA_MOVE_SPEED * Time.getInstance().getDeltaTime(),
                cameraInc.y * Consts.CAMERA_MOVE_SPEED * Time.getInstance().getDeltaTime(),
                cameraInc.z * Consts.CAMERA_MOVE_SPEED * Time.getInstance().getDeltaTime());
        if(mouseInput.isRightButtonPress()) {
            Vector2f rotVec = mouseInput.getDisplVec();
            camera.moveRotation(rotVec.x * Consts.MOUSE_SENSITIVITY,
                    rotVec.y * Consts.MOUSE_SENSITIVITY, 0);
            mouseInput.setDisplVec(0,0);
        }

        for(Entity entity : sceneManager.getEntities()) {
            renderer.processEntity(entity);
        }

        for(Terrain terrain : sceneManager.getTerrains()) {
            renderer.processTerrain(terrain);
        }

        for(Entity e1 : sceneManager.getEntities()) {
            Vector3f previousPos = e1.getPos();
            boolean collided = false;
            if(e1.isCollision()) {
                for(Entity e2: sceneManager.getEntities()) {
                    if(e2.isCollision() && !(e2.getName() == e1.getName())) {
                        if(Collisions.isColliding(e1.getPos(), e1.getBoxCollider(), e2.getPos(), e2.getBoxCollider())) {
                            collided = true;
                        }
                    }
                }
            }
            if(!e1.isStatic() && !collided) {
                e1.setPos(e1.getPos().x, e1.getPos().y - 9.81f * Time.getInstance().getDeltaTime(), e1.getPos().z);
            }


        }
    }

    public void fixedUpdate() {
        Time.getInstance().setTimeSinceLastFixedUpdate(Time.getInstance().getTimeSinceLastFixedUpdate() + Time.getInstance().getDeltaTime());
        if(Time.getInstance().getTimeSinceLastFixedUpdate() >= 0.02f) {
            Time.getInstance().setTimeSinceLastFixedUpdate(0);

        }
    }

    @Override
    public void render() {
        renderer.render(camera, sceneManager);
    }

    @Override
    public void cleanup() {
        renderer.cleanup();
        loader.cleanup();
    }
}
