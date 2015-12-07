package gdg.ninja.croplib.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * 自定义的ImageView控制，可对图片进行多点触控缩放和拖动
 * 
 * @author guolin
 */
public class ZoomImageView extends ImageView {

	/**
	 * 初始化状态常�?
	 */
	public static final int STATUS_INIT = 1;

	/**
	 * 图片放大状�?�常�?
	 */
	public static final int STATUS_ZOOM_OUT = 2;

	/**
	 * 图片缩小状�?�常�?
	 */
	public static final int STATUS_ZOOM_IN = 3;

	/**
	 * 图片拖动状�?�常�?
	 */
	public static final int STATUS_MOVE = 4;

	/**
	 * 用于对图片进行移动和缩放变换的矩�?
	 */
	private Matrix matrix = new Matrix();

	/**
	 * 待展示的Bitmap对象
	 */
	private Bitmap sourceBitmap;

	/**
	 * 记录当前操作的状态，可�?��?�为STATUS_INIT、STATUS_ZOOM_OUT、STATUS_ZOOM_IN和STATUS_MOVE
	 */
	private int currentStatus;

	/**
	 * ZoomImageView控件的宽�?
	 */
	private int width;

	/**
	 * ZoomImageView控件的高�?
	 */
	private int height;

	/**
	 * 记录两指同时放在屏幕上时，中心点的横坐标�?
	 */
	private float centerPointX;

	/**
	 * 记录两指同时放在屏幕上时，中心点的纵坐标�?
	 */
	private float centerPointY;

	/**
	 * 记录当前图片的宽度，图片被缩放时，这个�?�会�?起变�?
	 */
	private float currentBitmapWidth;

	/**
	 * 记录当前图片的高度，图片被缩放时，这个�?�会�?起变�?
	 */
	private float currentBitmapHeight;

	/**
	 * 记录上次手指移动时的横坐�?
	 */
	private float lastXMove = -1;

	/**
	 * 记录上次手指移动时的纵坐�?
	 */
	private float lastYMove = -1;

	/**
	 * 记录手指在横坐标方向上的移动距离
	 */
	private float movedDistanceX;

	/**
	 * 记录手指在纵坐标方向上的移动距离
	 */
	private float movedDistanceY;

	/**
	 * 记录图片在矩阵上的横向偏移�??
	 */
	private float totalTranslateX;
	/**
	 */
	private float totalMoveX;
	/**
	 */
	private float totalMoveY;

	/**
	 * 记录图片在矩阵上的纵向偏移�??
	 */
	private float totalTranslateY;

	/**
	 * 记录图片在矩阵上的�?�缩放比�?
	 */
	private float totalRatio;

	/**
	 * 记录手指移动的距离所造成的缩放比�?
	 */
	private float scaledRatio;

	/**
	 * 记录图片初始化时的缩放比�?
	 */
	private float initRatio;

	/**
	 * 记录上次两指之间的距�?
	 */
	private double lastFingerDis;

	Paint cornerOfRectPaint, circlePointPaint, shadowLayerPaint;
	private final float CIRCLE_SIZE = 25;
	private final float FRAME_STROKE_WIDTH = 5;
	private int outsideColor = Color.argb(200, 0, 0, 0);
	private int frameColor = Color.WHITE;
		private Point leftTop, rightBottom, center, previous;
	private Point cornerTopLeft, cornerBottomRight;
	private int imageScaledWidth;
	private int imageScaledHeight;

	private int BUFFER = 20;
private int first=1;
	private Handler mHandler;

	private enum AffectedSide{
		LEFT, RIGHT, TOP, BOTTOM, DRAG, NONE
	}
	/**
	 * ZoomImageView构�?�函数，将当前操作状态设为STATUS_INIT�?
	 * 
	 * @param context
	 * @param
	 * @param
	 */
	public ZoomImageView(Context context){
		super(context);
		initCropView();
	}

	public ZoomImageView(Context context, AttributeSet attrs){
		super(context, attrs, 0);
		currentStatus = STATUS_INIT;
		initCropView();
	}

	public ZoomImageView(Context context, AttributeSet attrs, int defStyle){
		super(context, attrs, defStyle);
		currentStatus = STATUS_INIT;
		initCropView();
	}
	private void initCropView(){
		cornerOfRectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		cornerOfRectPaint.setColor(frameColor);
		cornerOfRectPaint.setStyle(Paint.Style.STROKE);
		cornerOfRectPaint.setStrokeWidth(FRAME_STROKE_WIDTH);

		shadowLayerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		shadowLayerPaint.setColor(outsideColor);

		leftTop = new Point();
		rightBottom = new Point();
		previous = new Point();
		center = new Point();

		cornerTopLeft = new Point();
		cornerBottomRight = new Point();

		mHandler = new Handler();

	}

	/**
	 * 将待展示的图片设置进来
	 * 
	 * @param bitmap
	 *            待展示的Bitmap对象
	 */
	public void setImageBitmap(Bitmap bitmap) {
		sourceBitmap = bitmap;
		invalidate();
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		if (changed) {
			// 分别获取到ZoomImageView的宽度和高度
			width = getWidth();
			height = getHeight();
		}
	}
	private void drawShadowOverlay(Canvas canvas){
		Rect shadow[] = new Rect[4];
		shadow[0] = new Rect(cornerTopLeft.x, cornerTopLeft.y, rightBottom.x,
				leftTop.y);
		shadow[1] = new Rect(rightBottom.x, cornerTopLeft.y,
				cornerBottomRight.x, cornerBottomRight.y);
		shadow[2] = new Rect(cornerTopLeft.x, rightBottom.y, rightBottom.x,
				cornerBottomRight.y);
		shadow[3] = new Rect(cornerTopLeft.x, leftTop.y, leftTop.x,
				rightBottom.y);
		for(int i = 0; i < 4; i++){
			canvas.drawRect(shadow[i], shadowLayerPaint);
		}
	}
	private void resetPoints(){
		first=2;
		center.set(getWidth() / 2, getHeight() / 2);
		leftTop.set(5,
				(center.y)-(width/3*2/2));
		rightBottom.set( getWidth()-5, leftTop.y
				+width/3*2);
		currentBitmapWidth = sourceBitmap.getWidth() * totalRatio;
		currentBitmapHeight = sourceBitmap.getHeight() * totalRatio;
		Log.e("zoomimageview",currentBitmapWidth+"imageScaledHeight"+imageScaledHeight+"totalRatio="+totalRatio);

		invalidate();
	}
	public Bitmap getCroppedImage(){
		Bitmap originalBitmap = sourceBitmap;
		float[] f = new float[9];
		getImageMatrix().getValues(f);
		imageScaledWidth = Math.round(currentBitmapWidth
				* f[Matrix.MSCALE_X]);
		imageScaledHeight = Math.round(currentBitmapHeight
				* f[Matrix.MSCALE_Y]);
		float ratioOfBmpVsScreen_X = originalBitmap.getWidth()
				/ (float) imageScaledWidth;
		float ratioOfBmpVsScreen_Y = originalBitmap.getHeight()
				/ (float) imageScaledHeight;
		//求出图片放大移动后左上点的坐标和右下角点的坐标，和控件的中心点计算距离 换算所占百分比
		int rightButtomX=(int)totalTranslateX+imageScaledWidth;//右下角的图片横坐标x
		int rightButtomY=(int)totalTranslateY+imageScaledHeight;//右下角的图片纵坐标y
		int distanceLeft=(int)Math.abs(totalTranslateX)+center.x;//距离左面
		int distanceRight=(int)rightButtomX-center.x;//距离右面
		int distanceTop=center.y-(int)(totalTranslateY);//距离上面
		int distanceButtom=(int)rightButtomY-center.y;//距离下面

		float percentageLeft= distanceLeft*1f/(distanceRight*1f+distanceLeft*1f);//左面比例
		float percentageRight= distanceRight*1f/(distanceRight*1f+distanceLeft*1f);//右面
		float perLeft=percentageLeft+percentageRight;
		float percentageTop= distanceTop*1f/(distanceTop*1f+distanceButtom*1f);//上面
		float percentageButtom= distanceButtom*1f/(distanceTop*1f+distanceButtom*1f);//下面
		float perTop=percentageTop+percentageButtom;

		float x = leftTop.x - (center.x - imageScaledWidth *percentageLeft);
		float y = leftTop.y - (center.y - imageScaledHeight * percentageTop);
		Log.e("zoom 坐标",x+"、"+y+" center.x="+center.x+" center.Y="+center.y+" leftTopY="+leftTop.y
				+" imageScaledHeight="+imageScaledHeight);

		x=x<0?0:x;
		y=y<0?0:y;
		int croppedImgWidth = (int) ((rightBottom.x - leftTop.x) * ratioOfBmpVsScreen_X);
		int croppedImgHeight = (int) ((rightBottom.y - leftTop.y) * ratioOfBmpVsScreen_Y);
		Log.e("zoom","原图宽高："+originalBitmap.getHeight()+"、"+originalBitmap.getWidth()
						+"图片剪裁点坐标："+(int) (x * ratioOfBmpVsScreen_X)+"、"+(int) (y * ratioOfBmpVsScreen_Y)
						+"percentageLeft="+percentageLeft+"percentageTop="+percentageTop
		);
		Log.e("zoomXY",totalTranslateX+"get剪裁"+totalTranslateY+"imageScaledWidth"+imageScaledWidth+"imageScaledHeight="+imageScaledHeight);

		Bitmap croppedBmp = Bitmap.createBitmap(originalBitmap,
				(int) (x * ratioOfBmpVsScreen_X),
				(int) (y * ratioOfBmpVsScreen_Y), croppedImgWidth,
				croppedImgHeight);

		originalBitmap.recycle(); // Recycle bitmap as soon as we don't use it

		return croppedBmp;
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getActionMasked()) {
		case MotionEvent.ACTION_POINTER_DOWN:
			if (event.getPointerCount() == 2) {
				// 当有两个手指按在屏幕上时，计算两指之间的距离
				lastFingerDis = distanceBetweenFingers(event);
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if (event.getPointerCount() == 1) {
				// 只有单指按在屏幕上移动时，为拖动状�??
				float xMove = event.getX();
				float yMove = event.getY();
				if (lastXMove == -1 && lastYMove == -1) {
					lastXMove = xMove;
					lastYMove = yMove;
				}
				float[] f = new float[9];
				getImageMatrix().getValues(f);
				imageScaledHeight = Math.round(currentBitmapHeight
						* f[Matrix.MSCALE_Y]);
				currentStatus = STATUS_MOVE;
				movedDistanceX = xMove - lastXMove;
				movedDistanceY = yMove - lastYMove;
				totalMoveX=totalMoveX+movedDistanceX;
				totalMoveY=totalMoveY+movedDistanceY;
				// 进行边界�?查，不允许将图片拖出边界
				if (totalTranslateX +movedDistanceX> 0) {
					movedDistanceX = 0;
				} else if (width - (totalTranslateX + movedDistanceX) > currentBitmapWidth) {
					movedDistanceX = 0;
				}
				if (totalTranslateY + movedDistanceY >getHeight()/2-(width/3*2/2)) {
					movedDistanceY = 0;
				} else if (totalTranslateY + movedDistanceY +imageScaledHeight<getHeight()/2+(width/3*2/2)) {
					movedDistanceY = 0;
				}
				// 调用onDraw()方法绘制图片
				invalidate();
				lastXMove = xMove;
				lastYMove = yMove;


			} else if (event.getPointerCount() == 2) {
				// 有两个手指按在屏幕上移动时，为缩放状�?
				centerPointBetweenFingers(event);
				double fingerDis = distanceBetweenFingers(event);
				if (fingerDis > lastFingerDis) {
					currentStatus = STATUS_ZOOM_OUT;
				} else {
					currentStatus = STATUS_ZOOM_IN;
				}
				// 进行缩放倍数�?查，�?大只允许将图片放�?4倍，�?小可以缩小到初始化比�?
				if ((currentStatus == STATUS_ZOOM_OUT && totalRatio < 4 * initRatio)
						|| (currentStatus == STATUS_ZOOM_IN && totalRatio > initRatio)) {
					scaledRatio = (float) (fingerDis / lastFingerDis);
					totalRatio = totalRatio * scaledRatio;
					if (totalRatio > 4 * initRatio) {
						totalRatio = 4 * initRatio;
					} else if (totalRatio < initRatio) {
						totalRatio = initRatio;
					}
					// 调用onDraw()方法绘制图片
					invalidate();
					lastFingerDis = fingerDis;
				}
			}
			break;
		case MotionEvent.ACTION_POINTER_UP:
			if (event.getPointerCount() == 2) {
				// 手指离开屏幕时将临时值还�?
				lastXMove = -1;
				lastYMove = -1;
			}
			break;
		case MotionEvent.ACTION_UP:
			// 手指离开屏幕时将临时值还�?
			lastXMove = -1;
			lastYMove = -1;
			break;
		default:
			break;
		}
		return true;
	}

	/**
	 * 根据currentStatus的�?�来决定对图片进行什么样的绘制操作�??
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		switch (currentStatus) {
		case STATUS_ZOOM_OUT:
		case STATUS_ZOOM_IN:
			zoom(canvas);
			canvas.drawRect(leftTop.x, leftTop.y, rightBottom.x, rightBottom.y,
					cornerOfRectPaint);
			drawShadowOverlay(canvas);
			break;
		case STATUS_MOVE:
			move(canvas);
			canvas.drawRect(leftTop.x, leftTop.y, rightBottom.x, rightBottom.y,
					cornerOfRectPaint);
			drawShadowOverlay(canvas);
			break;
		case STATUS_INIT:
			initBitmap(canvas);
			canvas.drawRect(leftTop.x, leftTop.y, rightBottom.x, rightBottom.y,
					cornerOfRectPaint);
			drawShadowOverlay(canvas);
		default:
			canvas.drawBitmap(sourceBitmap, matrix, null);

			canvas.drawRect(leftTop.x, leftTop.y, rightBottom.x, rightBottom.y,
					cornerOfRectPaint);
			drawShadowOverlay(canvas);
			break;
		}
	}


	/**
	 * 对图片进行缩放处理�??
	 * 
	 * @param canvas
	 */
	private void zoom(Canvas canvas) {
		matrix.reset();
		// 将图片按总缩放比例进行缩放
		matrix.postScale(totalRatio, totalRatio);
		float scaledWidth = sourceBitmap.getWidth() * totalRatio;
		float scaledHeight = sourceBitmap.getHeight() * totalRatio;
		float translateX = 0f;
		float translateY = 0f;

		if (totalTranslateX +movedDistanceX> 0) {
			movedDistanceX = 0;
		} else if (width - (totalTranslateX + movedDistanceX) > currentBitmapWidth) {
			movedDistanceX = 0;
		}
		if (totalTranslateY + movedDistanceY >getHeight()/2-(width/3*2/2)) {
			movedDistanceY = 0;
		} else if (totalTranslateY + movedDistanceY +imageScaledHeight<getHeight()/2+(width/3*2/2)) {
			movedDistanceY = 0;
		}
		// 如果当前图片宽度小于屏幕宽度，则按屏幕中心的横坐标进行水平缩放�?�否则按两指的中心点的横坐标进行水平缩放
//		if (currentBitmapWidth < width) {
//			translateX = (width - scaledWidth) / 2f;
//		} else {
			translateX = totalTranslateX * scaledRatio + centerPointX * (1 - scaledRatio);
//			 进行边界�?查，保证图片缩放后在水平方向上不会偏移出屏幕
			if (translateX> 0) {
				translateX = 0;
			} else if (width - (translateX) > currentBitmapWidth){
				translateX = 0;
			}
			else if (width - translateX > scaledWidth) {
				translateX = width - scaledWidth;
			}
//		}
//		 如果当前图片高度小于屏幕高度，则按屏幕中心的纵坐标进行垂直缩放�?�否则按两指的中心点的纵坐标进行垂直缩放
//		if (currentBitmapHeight < height) {
//			translateY = (height - scaledHeight) / 2f;
//		} else {
			translateY = totalTranslateY * scaledRatio + centerPointY * (1 - scaledRatio);
			// 进行边界�?查，保证图片缩放后在垂直方向上不会偏移出屏幕
			if (translateY > 0) {
//				translateY = 0;
			} else if

					(height - translateY > scaledHeight) {
				translateY = height - scaledHeight;
			}
//		}
		// 缩放后对图片进行偏移，以保证缩放后中心点位置不变
		matrix.postTranslate(translateX, translateY);
		totalTranslateX = translateX;
		totalTranslateY = translateY;
		Log.e("zoomXY",totalTranslateX+"=zoom="+totalTranslateY);
		currentBitmapWidth = scaledWidth;
		currentBitmapHeight = scaledHeight;
		canvas.drawBitmap(sourceBitmap, matrix, null);
	}

	/**
	 * 对图片进行平移处�?
	 * 
	 * @param canvas
	 */
	private void move(Canvas canvas) {
		matrix.reset();
		// 根据手指移动的距离计算出总偏移�??
		float translateX = totalTranslateX+movedDistanceX;
		float translateY = totalTranslateY + movedDistanceY;
		// 先按照已有的缩放比例对图片进行缩�?
		matrix.postScale(totalRatio, totalRatio);
		// 再根据移动距离进行偏移?
		float scaledWidth = sourceBitmap.getWidth() * totalRatio;
		float scaledHeight = sourceBitmap.getHeight() * totalRatio;
		matrix.postTranslate(translateX, translateY);
		totalTranslateX = translateX;
		totalTranslateY = translateY;
		Log.e("zoomXY",totalTranslateX+"=move="+totalTranslateY);
		currentBitmapWidth = scaledWidth;
		currentBitmapHeight = scaledHeight;
		canvas.drawBitmap(sourceBitmap, matrix, null);
	}

	/**
	 * 对图片进行初始化操作，包括让图片居中，以及当图片大于屏幕宽高时对图片进行压缩�?
	 * 
	 * @param canvas
	 */
	private void initBitmap(Canvas canvas) {
		if (sourceBitmap != null) {
			matrix.reset();
			int cropHeight=width/3*2;
			int bitmapWidth = sourceBitmap.getWidth();
			int bitmapHeight = sourceBitmap.getHeight();
			if (bitmapWidth > width || bitmapHeight > height) {
				if (bitmapWidth - width > bitmapHeight - height) {
					// 当图片宽度大于屏幕宽度时，将图片等比例压缩，使它可以完全显示出来
					float ratio = width / (bitmapWidth * 1.0f);
					matrix.postScale(ratio, ratio);
					float translateY = (getHeight()/2-(width/3*2/2)) / 2f;
					// 在纵坐标方向上进行偏移，以保证图片居中显
					matrix.postTranslate(0, translateY);
					totalTranslateY = translateY;
					totalRatio = initRatio = ratio;
				} else {
					// 当图片高度大于屏幕高度时，将图片等比例压缩，使它可以完全显示出来
					float ratio = height / (bitmapHeight * 1.0f);
					matrix.postScale(ratio, ratio);
					float translateX = (width - (bitmapWidth * ratio)) / 2f;
					// 在横坐标方向上进行偏移，以保证图片居中显示
					matrix.postTranslate(translateX, 0);
					totalTranslateX = translateX;
					totalRatio = initRatio = ratio;
				}
				currentBitmapWidth = bitmapWidth * initRatio;
				currentBitmapHeight = bitmapHeight * initRatio;
			} else {

				float ratio;
				float Wratio=width/(bitmapWidth*1.0f);
				float Hratio=cropHeight/(bitmapHeight*1.0f);
				ratio=Wratio>Hratio?Wratio:Hratio;
				// 当图片的宽高都小于屏幕宽高时，直接让图片居中显示
				float translateX = (width - (bitmapWidth * ratio)) / 2f;
				float translateY = (height -(bitmapHeight * ratio)) / 2f;
				matrix.postScale(ratio, ratio);
				matrix.postTranslate(translateX, translateY);
				totalTranslateX = translateX;
				totalTranslateY = translateY;
				Log.e("zoomXY",totalTranslateX+"=init="+totalTranslateY);
				totalRatio = initRatio = ratio;
				currentBitmapWidth = bitmapWidth * initRatio;
				currentBitmapHeight = bitmapHeight * initRatio;
			}
			canvas.drawBitmap(sourceBitmap, matrix, null);
		}
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				if (first==1){
					resetPoints();
				}

			}
		}, 100);
	}

	/**
	 * 计算两个手指之间的距离�??
	 * 
	 * @param event
	 * @return 两个手指之间的距�?
	 */
	private double distanceBetweenFingers(MotionEvent event) {
		float disX = Math.abs(event.getX(0) - event.getX(1));
		float disY = Math.abs(event.getY(0) - event.getY(1));
		return Math.sqrt(disX * disX + disY * disY);
	}

	/**
	 * 计算两个手指之间中心点的坐标�?
	 * 
	 * @param event
	 */
	private void centerPointBetweenFingers(MotionEvent event) {
		float xPoint0 = event.getX(0);
		float yPoint0 = event.getY(0);
		float xPoint1 = event.getX(1);
		float yPoint1 = event.getY(1);
		centerPointX = (xPoint0 + xPoint1) / 2;
		centerPointY = (yPoint0 + yPoint1) / 2;
	}

}
