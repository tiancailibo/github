package com.example.biaobiao;

import com.baidu.mobads.IconsAd;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class GameView extends SurfaceView implements SurfaceHolder.Callback
{
	SurfaceHolder surface;
	private Paint paint = new Paint();
	private Paint paint1 = new Paint();//连击数画笔

	Bitmap p1;
	private int[] picture={R.drawable.enemy,R.drawable.man1,R.drawable.man2};
	private int[] hero={R.drawable.biao1,R.drawable.biao2};
	private int i,j;//图片数组控制
	private Bitmap bullet = null;
	private Bitmap man = null;
	private Bitmap enemy = null;
	private Bitmap lifetag = null;
	private Bitmap lifeheart = null;
	private Bitmap gradetag = null;
	private Bitmap heroman = null;
	private Bitmap hero1 = null;
	private Bitmap hero2 = null;
	private Bitmap background = null;
	private Bitmap rope = null;
	private Bitmap buffer = null;
	private Bitmap background_end=null;
	private SoundPool pool; 
	private boolean isRunning = true;
    private float rx, ry;	//子弹初始坐标
	private int moveDirection =0;
	private float enemyplace;
    private boolean flag;
    private boolean flag1;//控制减分
    private boolean flag2;//连击数闪现

    private int width,length;
    private int score=100;
    private int life=3;
    private float bullet_speed=(float)40.0;
    private float enemy_speed=(float)10.0;
    private boolean collesion_flag;
    private int kill;//连续击杀数
	private int maxgrade;
	MusicPlayer mp;
	private int sound;
	
	public GameView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		surface = this.getHolder();
		surface.addCallback(this);
		//createImage();
		
		//new Thread(new Repaint()).start();
		 paint.setColor(Color.YELLOW);
		 paint.setTextAlign(Align.RIGHT);
		 paint.setTextSize(40);
		 
		 paint1.setColor(Color.RED);
		 paint1.setTextAlign(Align.RIGHT);
		 paint1.setTextSize(40);
		
		 setFocusable(true);
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);//传输数据
		maxgrade = sp.getInt("grade", 1);
		mp = MusicPlayer.getMusicPlayer(context);
			}
    
	public void createImage(){
		
		Resources res=getResources(); 
		BitmapDrawable bmpDraw6=(BitmapDrawable)res.getDrawable(R.drawable.background);    
		background = bmpDraw6.getBitmap();
		
		BitmapDrawable bmpDraw7=(BitmapDrawable)res.getDrawable(R.drawable.rope);    
		rope = bmpDraw7.getBitmap();

		
		BitmapDrawable bmpDraw=(BitmapDrawable)res.getDrawable(R.drawable.feibiao);    
		bullet = bmpDraw.getBitmap();
		
		BitmapDrawable bmpDraw1=(BitmapDrawable)res.getDrawable(hero[i]);    	
		man = bmpDraw1.getBitmap();
	  	
		
		BitmapDrawable bmpDraw2=(BitmapDrawable)res.getDrawable(picture[j]);    
		enemy = bmpDraw2.getBitmap();
		
			BitmapDrawable bmpDraw3=(BitmapDrawable)res.getDrawable(R.drawable.lifetag);    
		lifetag = bmpDraw3.getBitmap();

		BitmapDrawable bmpDraw4=(BitmapDrawable)res.getDrawable(R.drawable.life);    
		lifeheart = bmpDraw4.getBitmap();
		
		BitmapDrawable bmpDraw5=(BitmapDrawable)res.getDrawable(R.drawable.gradetag);    
		gradetag = bmpDraw5.getBitmap();
		
		BitmapDrawable bmpDraw8=(BitmapDrawable)res.getDrawable(R.drawable.end);    
		background_end = bmpDraw8.getBitmap();
		

		}
	public static Drawable resizeImage(Bitmap bitmap, int w, int h) {

        // load the origial Bitmap
        Bitmap BitmapOrg = bitmap;

        int width = BitmapOrg.getWidth();
        int height = BitmapOrg.getHeight();
        int newWidth = w;
        int newHeight = h;

        // calculate the scale
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        // create a matrix for the manipulation
        Matrix matrix = new Matrix();
        // resize the Bitmap
        matrix.postScale(scaleWidth, scaleHeight);
        // if you want to rotate the Bitmap
        // matrix.postRotate(45);

        // recreate the new Bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width,
                        height, matrix, true);

        // make a Drawable from Bitmap to allow to set the Bitmap
        // to the ImageView, ImageButton or what ever
        return new BitmapDrawable(resizedBitmap);

}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction()==MotionEvent.ACTION_DOWN){ //处理屏幕屏点下事件 手指点击屏幕时触发
			
			moveDirection =1;
			i=1;
			if(flag1==false)
			{
			//    mp.playSound(MusicPlayer.getBullet());
				flag2=false;
				score-=10;
			    flag1=true;	
			}
//			Toast toast=Toast.makeText(getContext(), "ss",1);
//			toast.show();
			}else if(event.getAction()==MotionEvent.ACTION_UP){//处理屏幕屏抬起事件  手指离开屏幕时触发
				if(life>0){
					mp.playSound(MusicPlayer.getBullet());
				}
		}
		return true;  //此处需要返回true 才可以正常处理move事件 详情见后面的  说明
 
	}

	
	 public void ondraw(){
//        rx=(float) (0.0625*length);
//        ry=(float)(0.52*width);
        //enemyplace=(10/480)*width;
		Canvas c =this.surface.lockCanvas();
		if(life>0)
		{
		//	c.drawColor(Color.BLACK);
			c.drawBitmap(background, getMatrix(), null);
			 c.drawBitmap(rope, (float) (0.848*length),0,null);
			c.drawBitmap(bullet, rx,ry, null);
		    c.drawBitmap(man, (float) (0.0625*length),(float)(0.146*width),null);
		    c.drawBitmap(enemy,(float)(0.755*length),enemyplace,null);
		    c.drawBitmap(gradetag,(float)(0.2*length),(float)(0.004*width),null);
		    c.drawText(score+"",(float)(0.59*length),(float)(0.114*width),paint);
		    if(flag2==true)
		    {
		    	c.drawText("连击数+"+kill,(float)(0.59*length),(float)(0.414*width),paint1);
		    //	flag2=false;
		    	
		    }
		    
		    c.drawBitmap(lifetag,(float)(0.0625*length),(float)(0.856*width),paint);
           for(int k=0;k<life;k++){
        	    c.drawBitmap(lifeheart,(float)(0.2725*length)+(float)(k*55),(float)(0.856*width),paint);
              }
         }
		else
		{
			c.drawBitmap(background_end, getMatrix(), null);

		}
		//c.drawText(life+"",(float)(0.0625*length),(float)(0.896*width),paint);
    	surface.unlockCanvasAndPost(c);
    }


	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		length=arg2;
		width=arg3;
		 rx=(float) (0.1425*length);
	     ry=(float)(0.52*width);
	     enemyplace=(float)(10/480)*width;
	    
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		//ondraw();
				new Thread(new Repaint()).start();
			
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
	      isRunning=false;
	      
	}
	
	//碰撞检测
	public void collesion(){
		if((float)(0.6*length)<rx&&ry-enemyplace<100&&rx<(float)(0.7*length)&&ry-enemyplace>20)
		{
			
				j=1;
				collesion_flag=true;
				
		}
		
	}
	
	
	//enemy移动
	public void enemyaction(){
		//ennemy上下移动
		if(flag==false)	{
			enemyplace+=enemy_speed;
			if(enemyplace>(float)(0.685*width)){
				flag=true;
			}
		}
		if(flag==true){
			enemyplace-=enemy_speed;
			if(enemyplace<0){
				flag=false;
			}
		}

	}
	
	
private class Repaint implements Runnable{
		
		public void run() {
			while(isRunning){
				createImage();
				ondraw();
  		    	if(life>0)
				{
  		    		enemyaction();
				}
				if(moveDirection == 1){
					rx+=bullet_speed;               
				}
			    collesion();
				
				if(rx>(float)(0.938*length)&&rx<(float)(0.938*length)+bullet_speed)
				{
					moveDirection=0;
					i=0;
					flag1=false;
					flag2=true;
					j=0;//enemy不同形态
//					}
//					else
//					{
//						j=2;//大于200分enemy换形态
//					}
					
					rx=(float)(0.1425*length);
					if(collesion_flag)
					{   
						enemy_speed+=1;
						score=score+50+kill;
						collesion_flag=false;
						bullet_speed+=1;
						mp.playSound(MusicPlayer.getDie());
						kill+=1;//击中目标，连续击杀数加一
					}
					else
					{
						life-=1;
						kill=0;
					}
					if(score>maxgrade)
					{
						SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getContext());	
					    Editor editor = sp.edit();
					    editor.putInt("grade", score);
					    editor.commit();
					}
				}
			
							
								
				//刷新
								try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
@Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
//// TODO Auto-generated method stub
   if (keyCode == KeyEvent.KEYCODE_BACK) 
   {
     isRunning = false;
    }
   return super.onKeyDown(keyCode, event);
}
//

}
