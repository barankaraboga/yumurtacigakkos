package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Iterator;


public class yumurtaciGakkosGame extends Game implements Screen {

	private Game games;
	public yumurtaciGakkosGame() {
		games = this;
	}
	private SpriteBatch sayfa;
	private OrthographicCamera kamera;
	private Texture sepetTexture;
	private Texture yumurtaTexture;
	private Rectangle sepetSekil;
	private Sound kirilmaSesi;
    private boolean degisim = false;
	private String yumurtaTuru;
	//curuk
	private Rectangle curukYumurtam;
	private Texture curukTexture;
	int orientation;
	//sonradan
	private Texture arkaplan;
	private Sprite arkaSprite;
	// sonradan 2
	private String kacirmaYazi;
	private int kacirmaDeger;
	private  int  time ;
	private int sayac;
	private String timeText;
	private int skor;
	private String skorYazi;
	public BitmapFont bitmapFont;
	private ArrayList<Rectangle> yumurtalarDizi;
	private ArrayList<Rectangle> curuklerDizi;
	private long sonYumurta;
	private long sonCurukYumurta;
	private int renderX;
	private int renderY;

	private int curukHiz;


	@Override
	public void create () {

 		//sonrada2 yazi
        yumurtaTuru = "yumurta.png";

		skor = 0 ;
		skorYazi = "Skorun : 0";

		time  = 0;
		timeText  = "Süre :  0";

		kacirmaDeger = 0 ;
		kacirmaYazi = "Kacirdiklarin: 0";
		orientation   = Gdx.input.getRotation();

		bitmapFont = new BitmapFont();

		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("GoodDog.otf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 45;
		parameter.color = Color.BROWN;
		parameter.shadowOffsetX = 2 ;
		parameter.shadowOffsetY = 1;
		parameter.shadowColor = Color.BLACK;


		bitmapFont = generator.generateFont(parameter);

		//sonradan 2 sonlan

		kamera = new OrthographicCamera();
		kamera.setToOrtho(false,480,800);
		sayfa = new SpriteBatch();
		sepetTexture = new Texture("sepet.png");
		yumurtaTexture = new Texture("yumurta.png");
		curukTexture = new Texture("curuk.png");

		kirilmaSesi = Gdx.audio.newSound(Gdx.files.internal("kirilma.wav"));

		yumurtalarDizi = new ArrayList<Rectangle>();
		curuklerDizi = new ArrayList<Rectangle>();

		yumurtaUret();
		sepetSekil = new Rectangle();
		sepetSekil.width = 64;
		sepetSekil.height = 64;
		sepetSekil.setX(400-32);
		sepetSekil.setY(20);

		arkaplan = new Texture("arka.png");
		arkaSprite = new Sprite(arkaplan);

		//ivme
		renderX=100;
		renderY=100;
	}

	public void arkaPlanCiz()
	{
		arkaSprite.draw(sayfa);
	}

	@Override
	public void render () {
		if(TimeUtils.millis()- sonYumurta > 1000)
		{	timeText = "Süre : "+sayac++ +" sn.";
			yumurtaUret();
//			curukUret();
		}

		// curuk icin


		Iterator<Rectangle> iter = yumurtalarDizi.iterator();
		//Iterator<Rectangle> iter2 = curuklerDizi.iterator();

		while(iter.hasNext())
		{


			Rectangle yumurta = iter.next();
         //   Rectangle curuk = iter.next();
			yumurta.y -= 200 * Gdx.graphics.getDeltaTime();
			//curuk.y -=200*Gdx.graphics.getDeltaTime();



			if(yumurta.y + 64 < 0) {
				iter.remove();
				//iter2.remove();
			}



			if(yumurta.overlaps(sepetSekil))
			{
				skor ++ ;
				skorYazi =	"Skorun : "+skor;
				kirilmaSesi.play();

				if(yumurtaTuru == "curuk.png")
				{
					skor = skor-1;
					skorYazi = "Skorun : "+skor;
				}

				iter.remove();
				degisim = true;
				//iter2.remove();
			}
		}

	/*	if(Gdx.input.isTouched())
		{
			Vector3 dokunmaPozisyonu = new Vector3();
			dokunmaPozisyonu.set(Gdx.input.getX(),Gdx.input.getY(),0);
			kamera.unproject(dokunmaPozisyonu);
			//sepetSekil.x = dokunmaPozisyonu.x - 64/2;

		} */
		Vector3 dokunmaPozisyonu = new Vector3();
		//	Matrix4 matrix = new Matrix4();
		//	Gdx.input.getRotationMatrix(matrix.val);
			dokunmaPozisyonu.set(Gdx.input.getAccelerometerX(),Gdx.input.getAccelerometerY(),0);
			kamera.unproject(dokunmaPozisyonu);
		//	sepetSekil.x = dokunmaPozisyonu.x-64/2;
			sepetSekil.x -= Gdx.input.getAccelerometerX()*5;
		//	sepetSekil.y = Gdx.input.getAccelerometerY()*100;
           if(sepetSekil.x < 0.037790763)
		   {
			   sepetSekil.x = (float) 0.037790763;
		   }
			if(sepetSekil.x > 412.3784)
			{
				sepetSekil.x = (float)412.3784;
			}




		//ivme sensoru

		sayfa.begin();

		//ivme


/*		if(renderX < 0) renderX = 0;
		if(renderX > Gdx.graphics.getWidth() - 200 ){
			renderX = Gdx.graphics.getWidth() - 200;
			sepetSekil.x = renderX;
		//	System.out.println(renderX);
		}
*/
		System.out.println("ivme : "+sepetSekil.x);

		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		kamera.update();
		sayfa.setProjectionMatrix(kamera.combined);
//		sayfa.begin();
		arkaPlanCiz();
		sayfa.draw(sepetTexture, sepetSekil.x, sepetSekil.y, sepetSekil.width, sepetSekil.height);
		for(Rectangle yumurta: yumurtalarDizi) {
			sayfa.draw(yumurtaTexture, yumurta.x, yumurta.y);
		//	sayfa.draw(curukTexture, yumurta.x+y, yumurta.y+x);
		}
	/*	for(Rectangle curuk: curuklerDizi)
			sayfa.draw(curukTexture, curuk.x, curuk.y); */

		bitmapFont.draw(sayfa,skorYazi,40,700);
		bitmapFont.draw(sayfa,timeText,40,750);
		//bitmapFont.draw(sayfa,kacirmaYazi,25,300);
		sayfa.end();

	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose(){
		sayfa.dispose();
		bitmapFont.dispose();
		sepetTexture.dispose();
		yumurtaTexture.dispose();
		curukTexture.dispose();
	}

	private  void curukUret()
	{
		Rectangle curuk = new Rectangle();
		curuk.x  = MathUtils.random(0,480-64);
		curuk.y = 800;
		curuk.width = 64;
		curuk.height = 64;
		curukTexture =  new Texture("curuk.png");
		curuklerDizi.add(curuk);

		sonCurukYumurta = TimeUtils.millis();
	}

	private void yumurtaUret() {
		Rectangle yumurta = new Rectangle();
	    Rectangle curuk = new Rectangle();
		yumurta.x = MathUtils.random(0, 480 - 64);
		yumurta.y = 800;
		yumurta.width = 64;
		yumurta.height = 64;

		/*curuk.x = MathUtils.random(0, 480 - 64);
		curuk.y = 800;
		curuk.width = 64;
		curuk.height = 64; */

		yumurtaTexture = new Texture("curuk.png");
		//curukTexture = new Texture("yumurta.png");

		yumurtalarDizi.add(yumurta);
		//yumurtalarDizi.add(curuk);
		int degis = (int)(Math.random()*10);
		System.out.println("Degis Deger:  "+degis);

		if(degis>5)
		{
			yumurtaTuru = "curuk.png";
			yumurtaTexture = new Texture(yumurtaTuru);
			yumurtalarDizi.add(yumurta);

		} else
		{
			yumurtaTuru = "yumurta.png";
			yumurtaTexture = new Texture(yumurtaTuru);
			yumurtalarDizi.add(yumurta);
		}

		//int secim = (int) (Math.random()*1) ;
		int secim = 0;
		/*if(secim == 1)
		{
			secim = 0;
			yumurtaTexture = new Texture("yumurta.png");
			yumurtalarDizi.add(yumurta);
		}else
		{
			secim = 1;
			yumurtaTexture = new Texture("curuk.png");
			yumurtalarDizi.add(curuk);
		} */



		/*
		if(degisim == true)
		{
			yumurtaTuru = "curuk.png";
			yumurtaTexture = new Texture(yumurtaTuru);
		     yumurtalarDizi.add(yumurta);
			//degisim =false;
		}*/



		sonYumurta = TimeUtils.millis();
	}

/*	private void curukUret()
	{

		Rectangle curuk = new Rectangle();
		curuk.x = MathUtils.random(0, 800 - 64);
		curuk.y = 480;
		curuk.width = 64;
		curuk.height = 64;
		curuklerDizi.add(curuk);
		sonCurukYumurta = TimeUtils.nanoTime();
	} */
}
