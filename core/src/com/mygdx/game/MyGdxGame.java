package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import java.util.HashMap;

public class MyGdxGame extends ApplicationAdapter {
    static final float STEP_TIME = 1f / 60f;
    static final int VELOCITY_ITERATIONS = 6;
    static final int POSITION_ITERATIONS = 2;
    static final float SCALE = 0.5f;

    TextureAtlas textureAtlas;
    SpriteBatch batch;
    final HashMap<String, Sprite> sprites = new HashMap<String, Sprite>();

    OrthographicCamera camera;
    ExtendViewport viewport;

    World world;
    Box2DDebugRenderer debugRenderer;
    Body idle;
    Body ground;
    float accumulator = 0;

    @Override
    public void create() {
        camera = new OrthographicCamera();
        viewport = new ExtendViewport(200, 150, camera);

        batch = new SpriteBatch();
        textureAtlas = new TextureAtlas("graphic.atlas");
        addSprites();

        Box2D.init();
        world = new World(new Vector2(0, -10), true);

        debugRenderer = new Box2DDebugRenderer();

        idle = createBody("adventurer-attack1", 100, 75, 0);
    }

    private Body createBody(String name, float x, float y, float rotation) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        Body body = world.createBody(bodyDef);
        body.setTransform(x, y, rotation);

        PolygonShape box = new PolygonShape();
        box.setAsBox(
                sprites.get(name).getWidth() / 3,
                sprites.get(name).getHeight() / 2);
        
        FixtureDef fixtureDef = new FixtureDef();

        fixtureDef.shape = box;
        fixtureDef.density = 2f;
        fixtureDef.restitution = 0.2f;
        fixtureDef.friction = 0.2f;

        body.createFixture(fixtureDef);

        box.dispose();

        return body;
    }

    private void createGround() {
        if (ground != null) world.destroyBody(ground);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.friction = 1;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(camera.viewportWidth, 1);

        fixtureDef.shape = shape;

        ground = world.createBody(bodyDef);
        ground.createFixture(fixtureDef);
        ground.setTransform(0, 0, 0);

        shape.dispose();
    }

    private void drawSprite(String name, float x, float y, float degrees) {
        Sprite sprite = sprites.get(name);
        sprite.setPosition(x - sprite.getWidth() / 2, y - sprite.getHeight() / 2);
//        sprite.setRotation(degrees);
        sprite.setOrigin(0f, 0f);
        sprite.draw(batch);
    }

    private void addSprites() {
        Array<AtlasRegion> regions = textureAtlas.getRegions();

        for (AtlasRegion region : regions) {
            Sprite sprite = textureAtlas.createSprite(region.name);

            float width = sprite.getWidth() * SCALE;
            float height = sprite.getHeight() * SCALE;

            sprite.setSize(width, height);

            sprites.put(region.name, sprite);
        }
    }

    private void stepWorld() {
        float delta = Gdx.graphics.getDeltaTime();

        accumulator += Math.min(delta, 0.25f);

        if (accumulator >= STEP_TIME) {
            accumulator -= STEP_TIME;
            world.step(STEP_TIME, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        batch.setProjectionMatrix(camera.combined);
        createGround();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stepWorld();

        batch.begin();

        Vector2 position = idle.getPosition();
        float degrees = (float) Math.toDegrees(idle.getAngle());
        drawSprite("adventurer-attack1", position.x, position.y, degrees);

        batch.end();

        debugRenderer.render(world, camera.combined);
    }

    @Override
    public void dispose() {
        textureAtlas.dispose();
        sprites.clear();
        world.dispose();
        debugRenderer.dispose();
    }
}