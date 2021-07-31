package com.mygdx.game;


import com.badlogic.gdx.ApplicationAdapter;
		import com.badlogic.gdx.Gdx;
		import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MyGdxGame extends ApplicationAdapter {


	SpriteBatch spriteBatch;
	Texture playerTexture;
	Texture obstacleTexture;
	Pixmap playerPixmap;
	Pixmap obstaclePixmap;
	Sprite player;
	ShapeRenderer debug;
	Sprite obstacle;
	BitmapFont myFont;
	Array<Sprite> obstacles;
	Array<Sprite> coins;
	TextButton increaseSpeed;
	TextButton increaseCoinMultiplier;
	TextButton playGame;
	Skin mySkin;
	int totalCoins = 0;
	int gameState = 0;
	int speed = 1;
	int coinMultiplier = 1;
	int coinsAmount = 0;
	int lives = 3;
	Stage stage;
	float circleX = 200;
	float circleY = 100;
	int radius = 30;
	int moveFactor = 10;
	int obstacleMoveFactor = 5;
	@Override
	public void create() {

		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);

		mySkin = new Skin(Gdx.files.internal("glassy/skin/glassy-ui.json"));
		debug = new ShapeRenderer();
		spriteBatch = new SpriteBatch();
		myFont = new BitmapFont();
		myFont.getData().setScale(2f, 2f);
		increaseSpeed = new TextButton("Increase Speed: 5 coins", mySkin, "small");
		increaseCoinMultiplier = new TextButton("Increase Coin Multiplier: 10 coins", mySkin, "small");
		playGame = new TextButton("Play Again?", mySkin, "small");
		playGame.setPosition(700, 50);
		increaseSpeed.setPosition(100, 200);
		increaseCoinMultiplier.setPosition(500, 200);

		increaseSpeed.addListener(new InputListener(){
			@Override
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				if (totalCoins - 5 < 0){
					return;
				} else{
					totalCoins -= 5;
					speed += 1;
				}

			}
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				System.out.println("hello world");
				return true;
			}
		});
		increaseCoinMultiplier.addListener(new InputListener(){
			@Override
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				if (totalCoins - 5 < 0){
					return;
				} else{
					totalCoins -= 5;
					coinMultiplier += 1;
				}

			}
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				System.out.println("hello world");
				return true;
			}
		});

		playGame.addListener(new InputListener(){
			@Override
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				initaliseGame();
				gameState = 0;
				lives = 3;
				coinsAmount = 0;


			}
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

				return true;
			}
		});
		stage.addActor(playGame);
		stage.addActor(increaseSpeed);
		stage.addActor(increaseCoinMultiplier);

		playerPixmap = new Pixmap(20, 20, Pixmap.Format.RGBA8888);
		playerPixmap.setBlending(Pixmap.Blending.None);
		playerPixmap.setColor(Color.WHITE);
		playerPixmap.fillRectangle(0, 0, 50, 50);
		playerTexture = new Texture(playerPixmap);
		playerPixmap.dispose();

		obstaclePixmap = new Pixmap(60, 60, Pixmap.Format.RGBA8888);
		obstaclePixmap.setBlending(Pixmap.Blending.None);
		obstaclePixmap.setColor(Color.WHITE);
		obstaclePixmap.fillCircle(30, 30, 30);
		obstacleTexture = new Texture(obstaclePixmap);
		obstaclePixmap.dispose();




		initaliseGame();

	}

	void initaliseGame(){
		player = createCircleSprite(128f / 255f, 0f, 0f, 1f);

		player.getBoundingRectangle().setHeight(64 * 2);
		player.getBoundingRectangle().setWidth(64 * 2);

		obstacles = new Array<>();
		for(int i = 0; i < 30; i++){
			obstacles.add(createObstacleSprite(255, 255, 255, 1f));
		}

		coins = new Array<>();
		for(int i = 0; i < 2; i++){
			coins.add(createCoins());
		}

	}


	Sprite createCircleSprite(float r, float g, float b, float a) {
		// every sprite references on the same Texture object
		Sprite sprite = new Sprite(playerTexture, 50, 50 );
		sprite.setColor(r, g, b, a);
		// I just set random positions, but you should handle them differently of course
		sprite.setCenter(100, 100);
		return sprite;
	}


	Sprite createObstacleSprite(float r, float g, float b, float a) {
		// every sprite references on the same Texture object
		Sprite sprite = new Sprite(obstacleTexture, 60, 60);
		sprite.setColor(r, g, b, a);
		// I just set random positions, but you should handle them differently of course
		sprite.setCenter(
				MathUtils.random(Gdx.graphics.getWidth()) + 1280,
				MathUtils.random(Gdx.graphics.getHeight()));
		return sprite;
	}

	Sprite createCoins() {
		// every sprite references on the same Texture object
		Sprite sprite = new Sprite(obstacleTexture, 60, 60);
		sprite.setColor(255,255,0, 1);
		// I just set random positions, but you should handle them differently of course
		sprite.setCenter(
				MathUtils.random(Gdx.graphics.getWidth()) + 1280,
				MathUtils.random(Gdx.graphics.getHeight()));
		return sprite;
	}
	@Override
	public void render() {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if(gameState == 0 ){
			renderGameplay();
		} else{
			renderStore();
		}
	}

	@Override
	public void dispose() {

	}

	public void renderStore() {

		spriteBatch.begin();
		myFont.draw(spriteBatch, "Speed Increase: " + speed, 500, 500);
		myFont.draw(spriteBatch, "Coin Multiplier: " + coinMultiplier, 500, 450);
		myFont.draw(spriteBatch, "Total Coins: " + totalCoins, 600, 600);
		/*increaseSpeed.draw(spriteBatch, 1f);
		increaseCoinMultiplier.draw(spriteBatch, 1f);*/
		spriteBatch.end();
		stage.act();
		stage.draw();

	}
	public void renderGameplay() {
		if (Gdx.input.isTouched()) {

			player.setPosition(Gdx.input.getX() - player.getWidth() / 2, (Gdx.graphics.getHeight() - Gdx.input.getY()) -  player.getHeight() / 2);

		}

		if(Gdx.input.isKeyPressed(Input.Keys.W)){
			System.out.println(player.getY());
			System.out.println(Gdx.graphics.getHeight() - 500);
			if(player.getY() + (moveFactor * speed) > Gdx.graphics.getHeight() - 50 ){
				player.setPosition(player.getX(), player.getY());
			} else {
				player.setPosition(player.getX(), player.getY() + (moveFactor * speed));
			}

		}
		else if(Gdx.input.isKeyPressed(Input.Keys.S)){
			if(player.getY() - (moveFactor * speed) < 0 ){
				player.setPosition(player.getX(), player.getY());
			} else {
				player.setPosition(player.getX(), player.getY() - (moveFactor * speed));
			}

		}

		if(Gdx.input.isKeyPressed(Input.Keys.A)){
			if(player.getX() - (moveFactor * speed) < 0){
				player.setPosition(player.getX(), player.getY() );
			} else {
				player.setPosition(player.getX() - (moveFactor * speed), player.getY() );
			}

		}
		else if(Gdx.input.isKeyPressed(Input.Keys.D)){
			if(player.getX() + (moveFactor * speed) >  Gdx.graphics.getWidth() - 30){
				player.setPosition(player.getX(), player.getY() );
			} else {
				player.setPosition(player.getX() + (moveFactor * speed), player.getY() );
			}

		}


		for(Sprite obstacle: obstacles){
			if(obstacle.getBoundingRectangle().overlaps(player.getBoundingRectangle())){
				obstacles.removeValue(obstacle, true);
				lives -= 1;
				if (lives <= 0 ){
					gameState = 1;
				}
				obstacles.add(createObstacleSprite(255, 255, 255, 1f));
			}
		}

		for(Sprite coin: coins){
			if(coin.getBoundingRectangle().overlaps(player.getBoundingRectangle())){
				coins.removeValue(coin, true);
				coinsAmount += 1 * coinMultiplier;
				totalCoins += 1 * coinMultiplier;
				System.out.println(coinsAmount);
				coins.add(createCoins());
			}
		}



		Gdx.gl.glClearColor(.25f, .25f, .25f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		/*shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(0, 1, 0, 1);
		shapeRenderer.circle(circleX, circleY, radius);
		shapeRenderer.end();*/

		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		for(Sprite obstacle: obstacles){
			if(obstacle.getX() < 0 ){
				obstacles.removeValue(obstacle, true);
				obstacles.add(createObstacleSprite(255, 255, 255, 1f));

				obstacle.setPosition(1280, MathUtils.random(Gdx.graphics.getHeight()));
			}
			obstacle.setPosition(obstacle.getX() - obstacleMoveFactor, obstacle.getY());

		}

		for(Sprite coin: coins){
			if(coin.getX() < 0 ){
				coins.removeValue(coin, true);
				coins.add(createCoins());

				coin.setPosition(1280, MathUtils.random(Gdx.graphics.getHeight()));
			}
			coin.setPosition(coin.getX() - obstacleMoveFactor, coin.getY());

		}
		spriteBatch.begin();
		if(lives  <= 0 ){
			myFont.draw(spriteBatch, "DEAD", 25, 700);

		}
		else {

			myFont.draw(spriteBatch, "LIVES: " + "X".repeat(lives), 25, 700);

		}
		myFont.draw(spriteBatch, "COINS: " + coinsAmount, 25, 650);
		/*myFont.draw(spriteBatch, "LIVES", 25, 600);*/
		player.draw(spriteBatch);
		for(Sprite obstacle: obstacles){
			obstacle.draw(spriteBatch);
		}
		for(Sprite coin: coins){
			coin.draw(spriteBatch);
		}


		spriteBatch.end();

		debug.begin(ShapeRenderer.ShapeType.Line);
		/*debug.rect(player.getBoundingRectangle().getX(),
				player.getBoundingRectangle().getY(),
				player.getBoundingRectangle().getWidth(),
				player.getBoundingRectangle().getHeight());
		for(Sprite obstacle: obstacles){
			debug.rect(obstacle.getBoundingRectangle().getX(),
					obstacle.getBoundingRectangle().getY(),
					obstacle.getBoundingRectangle().getWidth(),
					obstacle.getBoundingRectangle().getHeight());
		}*/

		debug.end();
	}
}
