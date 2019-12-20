package com.janfranco.survivorbird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import java.util.Random;

public class SurvivorBird extends ApplicationAdapter {

	Random random;
	SpriteBatch batch;
	float[] beeX;
	float[][] beeY;
	Texture bg, bird, bee;
	float birdX, birdY, gravity, velocity, beeVelocity;
	int numberOfBees, distance, gameState, score, maxScore, scoredEnemy;
	Circle birdCircle;
	Circle[][] beeCircles;
	BitmapFont font, gameOverFont;

	@Override
	public void create () {
		batch = new SpriteBatch();
		bg = new Texture("bg.png");
		bird = new Texture("bird.png");
		bee = new Texture("bee.png");
		birdX = Gdx.graphics.getWidth() / 4;
		birdY = Gdx.graphics.getHeight() / 4;
		gameState = 0;
		score = 0;
		maxScore = 0;
		velocity = 0;
		beeVelocity = 4;
		gravity = 0.5f;
		numberOfBees = 4;
		distance = bee.getWidth();
		beeX = new float[numberOfBees];
		beeY = new float[numberOfBees][3];
		birdCircle = new Circle();
		beeCircles = new Circle[numberOfBees][3];
		random = new Random();
		for(int i=0; i<numberOfBees; i++) {
			beeX[i] = Gdx.graphics.getWidth() - bee.getWidth() / 2 + i * distance;
			beeY[i][0] = (random.nextFloat() - .5f) * Gdx.graphics.getHeight();
			beeY[i][1] = (random.nextFloat() - .5f) * Gdx.graphics.getHeight();
			beeY[i][2] = (random.nextFloat() - .5f) * Gdx.graphics.getHeight();
			beeCircles[i][0] = new Circle();
			beeCircles[i][1] = new Circle();
			beeCircles[i][2] = new Circle();
		}
		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(4);
		gameOverFont = new BitmapFont();
		gameOverFont.setColor(Color.PURPLE);
		gameOverFont.getData().setScale(8);
	}

	@Override
	public void render () {
		batch.begin();
		batch.draw(bg, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		if (gameState == 2) {
			gameOverFont.draw(batch, "Your score = " + score + "\nMaximum score = " +
							maxScore + "\nClick to play again!", Gdx.graphics.getWidth() / 3,
					Gdx.graphics.getHeight() / 2);
		}

		if (Gdx.input.justTouched()) {
			if (gameState == 0)
				gameState = 1;
			else if (gameState == 2) {
				score = 0;
				gameState = 1;
				birdX = Gdx.graphics.getWidth() / 4;
				birdY = Gdx.graphics.getHeight() / 4;
				for(int i=0; i<numberOfBees; i++) {
					beeX[i] = Gdx.graphics.getWidth() - bee.getWidth() / 2 + i * distance;
					beeY[i][0] = (random.nextFloat() - .5f) * Gdx.graphics.getHeight();
					beeY[i][1] = (random.nextFloat() - .5f) * Gdx.graphics.getHeight();
					beeY[i][2] = (random.nextFloat() - .5f) * Gdx.graphics.getHeight();
					beeCircles[i][0] = new Circle();
					beeCircles[i][1] = new Circle();
					beeCircles[i][2] = new Circle();
				}
			}
		}

		if (gameState == 1) {

			if (beeX[scoredEnemy] < birdX) {
				score++;
				scoredEnemy++;
				if (scoredEnemy == numberOfBees)
					scoredEnemy = 0;
			}

			for(int i=0; i<numberOfBees; i++) {
				if(beeX[i] < Gdx.graphics.getWidth() / 15) {
					beeX[i] += numberOfBees * distance;
					beeY[i][0] = (random.nextFloat() - .5f) * Gdx.graphics.getHeight();
					beeY[i][1] = (random.nextFloat() - .5f) * Gdx.graphics.getHeight();
					beeY[i][2] = (random.nextFloat() - .5f) * Gdx.graphics.getHeight();
				}
				else
					beeX[i] -= beeVelocity;

				beeCircles[i][0] = new Circle(beeX[i] + Gdx.graphics.getWidth() / 30,
						beeY[i][0] + Gdx.graphics.getHeight() / 20 + Gdx.graphics.getHeight() / 2,
						Gdx.graphics.getWidth() / 30);
				beeCircles[i][1] = new Circle(beeX[i] + Gdx.graphics.getWidth() / 30,
						beeY[i][1] + Gdx.graphics.getHeight() / 20 + Gdx.graphics.getHeight() / 2,
						Gdx.graphics.getWidth() / 30);
				beeCircles[i][2] = new Circle(beeX[i] + Gdx.graphics.getWidth() / 30,
						beeY[i][2] + Gdx.graphics.getHeight() / 20 + Gdx.graphics.getHeight() / 2,
						Gdx.graphics.getWidth() / 30);

				batch.draw(bee, beeX[i], Gdx.graphics.getHeight() / 2 + beeY[i][0],
						Gdx.graphics.getWidth() / 15, Gdx.graphics.getHeight() / 10);
				batch.draw(bee, beeX[i], Gdx.graphics.getHeight() / 2 + beeY[i][1],
						Gdx.graphics.getWidth() / 15, Gdx.graphics.getHeight() / 10);
				batch.draw(bee, beeX[i], Gdx.graphics.getHeight() / 2 + beeY[i][2],
						Gdx.graphics.getWidth() / 15, Gdx.graphics.getHeight() / 10);
			}

			if (Gdx.input.justTouched())
				if (birdY < Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 10)
					velocity = -8;
			if (birdY > 0) {
				velocity += gravity;
				birdY -= velocity;
			}
			else {
				gameState = 2;
				if (score > maxScore)
					maxScore = score;
			}
		}

		batch.draw(bird, birdX, birdY, Gdx.graphics.getWidth() / 12,
				Gdx.graphics.getHeight() / 10);
		font.draw(batch, score + " Max: " + maxScore, 100, 200);
		batch.end();

		birdCircle.set(birdX + Gdx.graphics.getWidth() / 24,
				birdY + Gdx.graphics.getWidth() / 24, Gdx.graphics.getWidth() / 24);

		for(int i=0; i<numberOfBees; i++) {
			if (Intersector.overlaps(birdCircle, beeCircles[i][0]) ||
					Intersector.overlaps(birdCircle, beeCircles[i][1]) ||
					Intersector.overlaps(birdCircle, beeCircles[i][2])) {
				gameState = 2;
				if (score > maxScore)
					maxScore = score;
			}
		}

	}
	
	@Override
	public void dispose () {

	}
}
