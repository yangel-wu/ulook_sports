package sports.sports.Utils;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by sreay on 14-10-24.
 */
public class BitmapUtil {


	/*
        * 旋转图片
        * @param angle
        * @param bitmap
        * @return Bitmap
        */
	public static Bitmap rotaingImageView(int angle , Bitmap bitmap) {
		//旋转图片 动作
		Matrix matrix = new Matrix();;
		matrix.postRotate(angle);
		System.out.println("angle2=" + angle);
		// 创建新的图片
		Bitmap rotaedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
				bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		return rotaedBitmap;
	}
	public static void setPictureDegreeZero(String path) {
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			// 修正图片的旋转角度，设置其不旋转。这里也可以设置其旋转的角度，可以传值过去，
			// 例如旋转90度，传值ExifInterface.ORIENTATION_ROTATE_90，需要将这个值转换为String类型的
			exifInterface.setAttribute(ExifInterface.TAG_ORIENTATION, "no");
			exifInterface.saveAttributes();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	/**
	 * 从exif信息获取图片旋转角度
	 *
	 * @param path
	 * @return
	 */
	public static int readPictureDegree(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}

	/**
	 * 对图片进行压缩选择处理
	 *
	 * @param picPath
	 * @return
	 */
	public static Bitmap compressRotateBitmap(String picPath) {
		Bitmap bitmap = null;
		int degree = readPictureDegree(picPath);
		if (degree == 90) {
			bitmap = featBitmapToSuitable(picPath, 500, 1.8f);
			bitmap = rotate(bitmap, 90);
		} else {
			bitmap = featBitmapToSuitable(picPath, 500, 1.8f);
		}
		return bitmap;
	}

	/**
	 * 转换bitmap为字节数组
	 *
	 * @param bitmap
	 * @return
	 */
	public static byte[] bitmapToBytes(Bitmap bitmap) {
		final int size = bitmap.getWidth() * bitmap.getHeight() * 4;
		final ByteArrayOutputStream out = new ByteArrayOutputStream(size);
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
		byte[] image = out.toByteArray();
		if (out != null) {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return image;

	}

	/**
	 * 获取合适尺寸的图片 图片的长或高中较大的值要 < suitableSize*factor
	 *
	 * @param path
	 * @param suitableSize
	 * @return
	 */
	public static Bitmap featBitmapToSuitable(String path, int suitableSize,
			float factor) {
		Bitmap bitmap = null;
		try {
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(path, options);
			options.inJustDecodeBounds = false;
			options.inSampleSize = 1;
			options.inPreferredConfig = Bitmap.Config.RGB_565;
			options.inPurgeable = true;
			options.inInputShareable = true;
			int bitmap_w = options.outWidth;
			int bitmap_h = options.outHeight;
			int max_edge = bitmap_w > bitmap_h ? bitmap_w : bitmap_h;
			while (max_edge / (float) suitableSize > factor) {
				options.inSampleSize <<= 1;
				max_edge >>= 1;
			}
			return BitmapFactory.decodeFile(path, options);
		} catch (Exception e) {
		}
		return bitmap;
	}

	public static Bitmap featBitmap(String path, int width) {
		Bitmap bitmap = null;
		try {
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(path, options);
			options.inJustDecodeBounds = false;
			options.inSampleSize = 1;
			options.inPreferredConfig = Bitmap.Config.RGB_565;
			options.inPurgeable = true;
			options.inInputShareable = true;
			int bitmap_w = options.outWidth;
			while (bitmap_w / (float) width > 2) {
				options.inSampleSize <<= 1;
				bitmap_w >>= 1;
			}
			return BitmapFactory.decodeFile(path, options);
		} catch (Exception e) {
		}
		return bitmap;
	}

	public static Bitmap loadBitmap(String path, int maxSideLen) {
		if (null == path) {
			return null;
		}
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);
		options.inJustDecodeBounds = false;
		options.inSampleSize = Math.max(options.outWidth / maxSideLen, options.outHeight / maxSideLen);
		if (options.inSampleSize < 1) {
			options.inSampleSize = 1;
		}
		options.inPreferredConfig = Bitmap.Config.RGB_565;
		try {
			Bitmap bitmap = BitmapFactory.decodeFile(path, options);
			if (bitmap != bitmap) {
				bitmap.recycle();
			}
			return bitmap;
		} catch (OutOfMemoryError e) {
			LogUtils.e("higo", e.toString());
		}
		return null;
	}

	public static Bitmap loadBitmap(String path) {
		if (null == path) {
			return null;
		}
		BitmapFactory.Options options = new BitmapFactory.Options();
		//不对图进行压缩
		options.inSampleSize = 1;
		options.inPreferredConfig = Bitmap.Config.ARGB_8888;
		try {
			Bitmap bitmap = BitmapFactory.decodeFile(path, options);
			return bitmap;
		} catch (OutOfMemoryError e) {
			LogUtils.e("higo", e.toString());
		}
		return null;
	}

	public static Bitmap loadFromAssets(Activity activity, String name, int sampleSize,Bitmap.Config config) {
		AssetManager asm = activity.getAssets();
		try {
			InputStream is = asm.open(name);
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize = sampleSize;
			options.inPreferredConfig = config;
			try {
				Bitmap bitmap = BitmapFactory.decodeStream(is);
				is.close();
				return bitmap;
			} catch (OutOfMemoryError e) {
				LogUtils.e("decode bitmap ",e.toString());
			}
		} catch (IOException e) {
			LogUtils.e("bitmaputil",e.toString());
			e.printStackTrace();
		}
		return null;
	}

	public static Bitmap decodeByteArrayUnthrow(byte[] data, BitmapFactory.Options opts) {
		try {
			return BitmapFactory.decodeByteArray(data, 0, data.length, opts);
		} catch (Throwable e) {
			LogUtils.e("bitmaputil",e.toString());
		}

		return null;
	}

	public static Bitmap rotateAndScale(Bitmap b, int degrees, float maxSideLen) {

		return rotateAndScale(b, degrees, maxSideLen, true);
	}

	// Rotates the bitmap by the specified degree.
	// If a new bitmap is created, the original bitmap is recycled.
	public static Bitmap rotate(Bitmap b, int degrees) {
		if (degrees != 0 && b != null) {
			Matrix m = new Matrix();
			m.setRotate(degrees);
			try {
				Bitmap b2 = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), m, true);
				if (null != b2 && b != b2) {
					b.recycle();
					b = b2;
				}
			} catch (OutOfMemoryError ex) {
				// We have no memory to rotate. Return the original bitmap.
			}
		}
		return b;
	}

	public static Bitmap rotateNotRecycleOriginal(Bitmap b, int degrees) {
		if (degrees != 0 && b != null) {
			Matrix m = new Matrix();
			m.setRotate(degrees);
			try {
				return Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), m, true);
			} catch (OutOfMemoryError ex) {
				// We have no memory to rotate. Return the original bitmap.
			}
		}
		return b;
	}

	public static Bitmap rotateAndScale(Bitmap b, int degrees, float maxSideLen, boolean recycle) {
		if (null == b || degrees == 0 && b.getWidth() <= maxSideLen + 10 && b.getHeight() <= maxSideLen + 10) {
			return b;
		}

		Matrix m = new Matrix();
		if (degrees != 0) {
			m.setRotate(degrees);
		}

		float scale = Math.min(maxSideLen / b.getWidth(), maxSideLen / b.getHeight());
		if (scale < 1) {
			m.postScale(scale, scale);
		}
		LogUtils.e("degrees: " + degrees ,", scale: " + scale);

		try {
			Bitmap b2 = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), m, true);
			if (null != b2 && b != b2) {
				if (recycle) {
					b.recycle();
				}
				b = b2;
			}
		} catch (OutOfMemoryError e) {
		}

		return b;
	}

	public static boolean saveBitmap2file(Bitmap bmp, File file, Bitmap.CompressFormat format, int quality) {
		if (file.isFile())
			file.delete();
		OutputStream stream = null;
		try {
			stream = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			LogUtils.e(e.toString(),"error");
			return false;
		}

		return bmp.compress(format, quality, stream);
	}
}
