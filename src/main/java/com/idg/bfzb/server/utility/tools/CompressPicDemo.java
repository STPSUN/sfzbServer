package com.idg.bfzb.server.utility.tools;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

/*******************************************************************************
 * 缩略图类（通用） 本java类能将jpg、bmp、png、gif图片文件，进行等比或非等比的大小转换。 具体使用方法
 * compressPic(大图片路径,生成小图片路径,大图片文件名,生成小图片文名,生成小图片宽度,生成小图片高度,是否等比缩放(默认为true))
 * 图片剪裁 图片切割 方法具体使用： cutImageIO(原图路径+图片名,目标路径+图片名,x坐标,y坐标,width,height)
 */
public class CompressPicDemo {
	private File file = null; // 文件对象
	private String inputDir; // 输入图路径
	private String outputDir; // 输出图路径
	private String inputFileName; // 输入图文件名
	private String outputFileName; // 输出图文件名
	private int outputWidth = 100; // 默认输出图片宽
	private int outputHeight = 100; // 默认输出图片高
	private int quality = 0; // 默认精度，不建议修改
	private boolean proportion = true; // 是否等比缩放标记(默认为等比缩放)
	private FileOutputStream out;

	public CompressPicDemo() { // 初始化变量
		inputDir = "";
		outputDir = "";
		inputFileName = "";
		outputFileName = "";
		outputWidth = 100;
		outputHeight = 100;
	}

	public void setInputDir(String inputDir) {
		this.inputDir = inputDir;
	}

	public void setOutputDir(String outputDir) {
		this.outputDir = outputDir;
	}

	public void setInputFileName(String inputFileName) {
		this.inputFileName = inputFileName;
	}

	public void setOutputFileName(String outputFileName) {
		this.outputFileName = outputFileName;
	}

	public void setOutputWidth(int outputWidth) {
		this.outputWidth = outputWidth;
	}

	public void setOutputHeight(int outputHeight) {
		this.outputHeight = outputHeight;
	}

	public void setWidthAndHeight(int width, int height) {
		this.outputWidth = width;
		this.outputHeight = height;
	}

	/*
	 * 获得图片大小 传入参数 String path ：图片路径
	 */
	public long getPicSize(String path) {
		file = new File(path);
		return file.length();
	}

	// 图片处理
	public boolean compressPic(File file, String outputDir) {
		try {
			Image img = ImageIO.read(file);
			// 判断图片格式是否正确
			if (img.getWidth(null) == -1) {
				return false;
			} else {
				int newWidth;
				int newHeight;
				// 判断是否是等比缩放
				if (this.proportion == true) {
					// 为等比缩放计算输出的图片宽度及高度
					double rate1 = ((double) img.getWidth(null))
							/ (double) outputWidth + 0.1;
					double rate2 = ((double) img.getHeight(null))
							/ (double) outputHeight + 0.1;
					// 根据缩放比率大的进行缩放控制
					double rate = rate1 > rate2 ? rate1 : rate2;
					newWidth = (int) (((double) img.getWidth(null)) / rate);
					newHeight = (int) (((double) img.getHeight(null)) / rate);
				} else {
					newWidth = outputWidth; // 输出的图片宽度
					newHeight = outputHeight; // 输出的图片高度
				}
				BufferedImage tag = new BufferedImage((int) newWidth,
						(int) newHeight, BufferedImage.TYPE_INT_RGB);

				/*
				 * Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢
				 */
				tag.getGraphics().drawImage(
						img.getScaledInstance(newWidth, newHeight,
								Image.SCALE_SMOOTH), 0, 0, null);
				FileOutputStream out = new FileOutputStream(outputDir);
				// JPEGImageEncoder可适用于其他图片类型的转换
				JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
				if (quality != 0) {
					JPEGEncodeParam jep = JPEGCodec
							.getDefaultJPEGEncodeParam(tag);
					/* 压缩质量 使用默认精度1 */
					jep.setQuality(1, true);
					encoder.encode(tag, jep);
				} else {
					encoder.encode(tag);
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return true;
	}

	// 图片处理
	public String compressPic() {
		try {

			// 获得源文件
			file = new File(inputDir + inputFileName);
			if (!file.exists()) {
				return "";
			}
			Image img = ImageIO.read(file);
			// 判断图片格式是否正确
			if (img.getWidth(null) == -1) {
				System.out.println(" can't read,retry!" + "<BR>");
				return "no";
			} else {
				int newWidth;
				int newHeight;
				// 判断是否是等比缩放
				if (this.proportion == true) {
					// 为等比缩放计算输出的图片宽度及高度
					double rate1 = ((double) img.getWidth(null))
							/ (double) outputWidth + 0.1;
					double rate2 = ((double) img.getHeight(null))
							/ (double) outputHeight + 0.1;
					// 根据缩放比率大的进行缩放控制
					double rate = rate1 > rate2 ? rate1 : rate2;
					newWidth = (int) (((double) img.getWidth(null)) / rate);
					newHeight = (int) (((double) img.getHeight(null)) / rate);
				} else {
					newWidth = outputWidth; // 输出的图片宽度
					newHeight = outputHeight; // 输出的图片高度
				}
				BufferedImage tag = new BufferedImage((int) newWidth,
						(int) newHeight, BufferedImage.TYPE_INT_RGB);

				/*
				 * Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢
				 */
				tag.getGraphics().drawImage(
						img.getScaledInstance(newWidth, newHeight,
								Image.SCALE_SMOOTH), 0, 0, null);
				out = new FileOutputStream(outputDir + outputFileName);
				// JPEGImageEncoder可适用于其他图片类型的转换
				JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);

				if (quality != 0) {
					JPEGEncodeParam jep = JPEGCodec
							.getDefaultJPEGEncodeParam(tag);
					/* 压缩质量 使用默认精度1 */
					jep.setQuality(1, true);
					encoder.encode(tag, jep);
				} else {
					encoder.encode(tag);
				}

			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
		return "ok";
	}

	public String compressPic(String inputDir, String outputDir,
			String inputFileName, String outputFileName) {
		// 输入图路径
		this.inputDir = inputDir;
		// 输出图路径
		this.outputDir = outputDir;
		// 输入图文件名
		this.inputFileName = inputFileName;
		// 输出图文件名
		this.outputFileName = outputFileName;
		return compressPic();
	}

	/**
	 * 使用默认精度压缩图片，手机端使用
	 * 
	 * @param inputDir
	 * @param outputDir
	 * @param inputFileName
	 * @param outputFileName
	 * @param width
	 * @param height
	 * @param gp
	 * @return
	 */
	public String compressPic(String inputDir, String outputDir,
			String inputFileName, String outputFileName, int width, int height,
			boolean gp) {
		// 输入图路径
		this.inputDir = inputDir;
		// 输出图路径
		this.outputDir = outputDir;
		// 输入图文件名
		this.inputFileName = inputFileName;
		// 输出图文件名
		this.outputFileName = outputFileName;
		// 设置图片长宽
		setWidthAndHeight(width, height);
		// 是否是等比缩放 标记
		this.proportion = gp;

		return compressPic();
	}

	/**
	 * 提供quality精度设置压缩图片，建议设置为<1>
	 * 
	 * @param inputDir
	 * @param outputDir
	 * @param inputFileName
	 * @param outputFileName
	 * @param width
	 * @param height
	 * @param gp
	 * @param quality
	 * @return
	 */
	public String compressPic(String inputDir, String outputDir,
			String inputFileName, String outputFileName, int width, int height,
			boolean gp, int quality) {
		// 输入图路径
		this.inputDir = inputDir;
		// 输出图路径
		this.outputDir = outputDir;
		// 输入图文件名
		this.inputFileName = inputFileName;
		// 输出图文件名
		this.outputFileName = outputFileName;
		// 设置图片长宽
		setWidthAndHeight(width, height);
		// 是否是等比缩放 标记
		this.proportion = gp;
		// 设置压缩精度
		this.quality = quality;
		return compressPic();
	}

	/**
	 * 图片切割
	 * 
	 * @param imagePath
	 *            原图地址【路径加图片文件】
	 * @param outPutPath
	 *            图片存放地址【路径加图片文件】
	 * @param x
	 *            目标切片坐标 X轴起点
	 * @param y
	 *            目标切片坐标 Y轴起点
	 * @param w
	 *            目标切片 宽度
	 * @param h
	 *            目标切片 高度
	 */
	public Boolean cutImageIO(String imagePath, String outPutPath, int x,
			int y, int width, int height) {

		boolean flag = false;
		/** 对图片裁剪，并把裁剪完的新图片保存 */
		FileInputStream is = null;
		ImageInputStream iis = null;
		try {
			// 读取图片文件
			is = new FileInputStream(imagePath);
			/*
			 * 返回包含所有当前已注册 ImageReader 的 Iterator，这些 ImageReader 声称能够解码指定格式。
			 * 参数：formatName - 包含非正式格式名称 . （例如 "jpeg" 或 "tiff"）等 。
			 */
			Iterator<ImageReader> it = ImageIO
					.getImageReadersByFormatName("jpg");
			ImageReader reader = it.next();
			// 获取图片流
			iis = ImageIO.createImageInputStream(is);
			/*
			 * <p>iis:读取源.true:只向前搜索 </p>.将它标记为 ‘只向前搜索’。
			 * 此设置意味着包含在输入源中的图像将只按顺序读取，可能允许 reader 避免缓存包含与以前已经读取的图像关联的数据的那些输入部分。
			 */
			reader.setInput(iis, true);
			/*
			 * <p>描述如何对流进行解码的类<p>.用于指定如何在输入时从 Java Image I/O
			 * 框架的上下文中的流转换一幅图像或一组图像。用于特定图像格式的插件 将从其 ImageReader 实现的
			 * getDefaultReadParam 方法中返回 ImageReadParam 的实例。
			 */
			ImageReadParam param = reader.getDefaultReadParam();
			/*
			 * 图片裁剪区域。Rectangle 指定了坐标空间中的一个区域，通过 Rectangle 对象
			 * 的左上顶点的坐标（x，y）、宽度和高度可以定义这个区域。
			 */
			Rectangle rect = new Rectangle(x, y, width, height);
			// 提供一个 BufferedImage，将其用作解码像素数据的目标。
			param.setSourceRegion(rect);
			/*
			 * 使用所提供的 ImageReadParam 读取通过索引 imageIndex 指定的对象，并将 它作为一个完整的
			 * BufferedImage 返回。
			 */
			BufferedImage bi = reader.read(0, param);
			// 保存新图片
			ImageIO.write(bi, "jpg", new File(outPutPath));
			flag = true;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					System.out.println("FileInputStream关闭失败");
					e.printStackTrace();
				}
			}
		}
		return flag;

	}

}