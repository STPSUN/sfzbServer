package com.idg.bfzb.server.controller.api;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.idg.bfzb.server.common.Constants;
import com.idg.bfzb.server.common.ErrorCode;
import com.idg.bfzb.server.common.model.APIResponse;
import com.idg.bfzb.server.file.model.dto.AttachmentEntity;
import com.idg.bfzb.server.file.service.AttachmentService;
import com.idg.bfzb.server.utility.tools.ConfigFileUtils;

@Controller
@RequestMapping(value="api/sys")
public class FileController {
	private static Logger log = Logger.getLogger(FileController.class);
	
	@Autowired
	private AttachmentService attachmentService;
	
	@RequestMapping(value ="/",method = RequestMethod.GET)
	public String printWelcome(ModelMap model) {
		return "index2";
	}
	
	/**
	 * 上传图片文件
	 * 
	 * @param request
	 * @param response
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@RequestMapping(value = "/images/actions/upload", method = RequestMethod.POST)
	@ResponseBody
	public APIResponse uploadImage(HttpServletRequest request,HttpServletResponse response)
			throws Exception {
			APIResponse apiResponse = new APIResponse();
			//调用上传
			apiResponse = fileUpload(request,Constants.FILE_IMAGE_PATH,Constants.FILE_TYPE_IMG);
			/*//生成小图
			if(APIResponse.SUCESS_MSG.equals(apiResponse.getMessage())){
				List<AttachmentEntity>  attachs = (List<AttachmentEntity>)apiResponse.getData();
				for(AttachmentEntity attach:attachs){
					String attchUrl = Constants.FILE_IMAGE_PATH+attach.getAttchUrl();
					String smallAttchUrl = attchUrl.replace(".","_small.");
					resizeImage(attchUrl,smallAttchUrl,Constants.FILE_IMG_WIDTH,Constants.FILE_IMG_HEIGHT);
				}
			}*/
			return apiResponse;
	}
	/**
	 * 上传其他文件
	 * 
	 * @param request
	 * @param response
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@RequestMapping(value = "/other/actions/upload", method = RequestMethod.POST)
	@ResponseBody
	public APIResponse upload(HttpServletRequest request,HttpServletResponse response)
			throws Exception {
			APIResponse apiResponse = new APIResponse();
			//调用上传
			apiResponse = fileUpload(request,Constants.FILE_OTHER_PATH,Constants.FILE_TYPE_OTHER);
			return apiResponse;
	}
	

	/**
	 * 文件上传
	 * @param request
	 * @param response
	 * @param uploadType
	 * @return
	 */
	public APIResponse fileUpload(HttpServletRequest request,String filePath,String fileType) {
		//filePath = request.getSession().getServletContext().getRealPath("/")+"pages/course/"+filePath;//服务器目录
		APIResponse apiResponse = new APIResponse();
		List<AttachmentEntity> entitys = new ArrayList<AttachmentEntity>();
		try {
			// 创建一个通用的多部分解析器
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
					request.getSession().getServletContext());
			// 判断 request 是否有文件上传,即多部分请求
			if (multipartResolver.isMultipart(request)) {
				// 转换成多部分request
				//MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
				MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
				MultipartHttpServletRequest multiRequest = resolver.resolveMultipart(request);
				// 取得request中的所有文件名
				Iterator<String> iter = multiRequest.getFileNames();
				while (iter.hasNext()) {
					// 取得上传文件
					MultipartFile file = multiRequest.getFile(iter.next());
					if (file != null) {
						// 取得当前上传文件的文件名称
						String fileUrl = file.getName();
						String myFileName = file.getOriginalFilename();
						// 如果名称不为“”,说明该文件存在，否则说明该文件不存在
						if (!"".equals(myFileName.trim())) {
							String ext = getExt(myFileName);// 获取后缀名
							if (!filterFiles(ext)) {
								apiResponse.setErrorCode(ErrorCode.FILE_EXT_WRONG);
								return apiResponse;
							}
							
							int type = 3;// 获取文件类型
							if (ext.equals("txt") || ext.equals("doc")
									|| ext.equals("docx") || ext.equals("pdf")
									|| ext.equals("pptx") || ext.equals("ppt")) {
								type = 1;
							} else if (ext.equals("rmvb") || ext.equals("avi")||ext.equals("flv")||ext.equals("3pg")||ext.equals("f4v")||ext.equals("mov")||ext.equals("mpeg")||ext.equals("mp4")||ext.equals("mpg")||ext.equals("wmv")) {
								type = 2;
							} else if (ext.equalsIgnoreCase("jpeg") || ext.equalsIgnoreCase("jpg")||ext.equalsIgnoreCase("gif")||ext.equalsIgnoreCase("bmp")||ext.equalsIgnoreCase("png")){
								type = 3;
							} else {
								type = 4;
							}
							// 重命名上传后的文件名
							String fileName = formatDateToString("yyyyMMddHHmmssSSS", new Date()) + "." + ext;

							// 获取文件大小
							long size = file.getSize();
							//验证文件大小不超过20M
							if(size>Constants.FILE_MAX_SIZE){
								apiResponse.setErrorCode(ErrorCode.FILE_SIZE_OVER);
								return apiResponse;
							}
							File isfile = new File(filePath);
							if (!isfile.exists() && !isfile.isDirectory()) {
								boolean isOk = isfile.mkdirs();
							}
							String path = filePath + fileName;
							File localFile = new File(path);
							file.transferTo(localFile);
							//生成文件的md5码
							//String md5 = DigestUtils.md5Hex(new FileInputStream(localFile));
							//log.info("文件已上传，正在生成MD5码");
							AttachmentEntity item = new AttachmentEntity();
							item.setAttchId(UUID.randomUUID().toString());
							item.setAttchName(myFileName);
							item.setAttchRename(fileName);
							item.setTargetId("1");
							item.setTargetType("headImg");
							item.setAttchSize(size);
							item.setAttchUrl(fileName);
							item.setCreateTime(new Timestamp(new Date().getTime()));
							item.setFileType(fileType);
							//item.setFileMd5(md5);
							item.setState((short)0);
							attachmentService.addAttachment(item);
							
							if(type==3){//图片类型不做批量上传，同时生成小图，并返回大小图地址
								Map<String,Map<String,String>> map = new HashMap<String,Map<String,String>>();
								Map<String,String> map1 = new HashMap<String,String>();
								map1.put("attch_id", item.getAttchId());
								map1.put("source_file_name", item.getAttchName());
								map1.put("image_url", ConfigFileUtils.HEAD_ICON_URL+item.getAttchUrl());
								//小图片
								String attchUrl = filePath+fileName;
								String smallAttchUrl = attchUrl.replace(".","_small.");
								resizeImage(attchUrl,smallAttchUrl,Constants.FILE_IMG_WIDTH,Constants.FILE_IMG_HEIGHT);
								item.setAttchId(UUID.randomUUID().toString());
								item.setAttchUrl(new File(smallAttchUrl).getName());
								attachmentService.addAttachment(item);//
								Map<String,String> map2 = new HashMap<String,String>();
								map2.put("attch_id", item.getAttchId());
								map2.put("source_file_name", item.getAttchName());
								map2.put("image_url", ConfigFileUtils.HEAD_ICON_URL+item.getAttchUrl());
								map.put("big_image", map1);
								map.put("small_image", map2);
								apiResponse.setData(map);
								apiResponse.setMessage(APIResponse.SUCESS_MSG);
								return apiResponse;
							}
							entitys.add(item);
						}
					}
				}
				apiResponse.setData(entitys);
				apiResponse.setMessage(APIResponse.SUCESS_MSG);
			} else {
				apiResponse.setErrorCode(ErrorCode.FILE_IS_EMPTY);
			}
		} catch (Exception ex) {
			log.error("getCourseList failed", ex);
			apiResponse.setErrorCode(ErrorCode.FILE_UPLOAD_FAIL);
		}
		return apiResponse;
	}
	
	 /*** 
     * 功能 :调整图片大小
     * @param srcImgPath 原图片路径 
     * @param distImgPath  转换大小后图片路径 
     * @param width   转换后图片宽度 
     * @param height  转换后图片高度 
     */  
    public static void resizeImage(String srcImgPath, String distImgPath,  
            int width, int height) throws IOException {  
  
        File srcFile = new File(srcImgPath);
        String imgType = getExt(srcImgPath);
        Image srcImg = ImageIO.read(srcFile);
        BufferedImage buffImg = null;
        buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);  
        buffImg.getGraphics().drawImage(  
                srcImg.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0,  
                0, null);
        ImageIO.write(buffImg,"JPEG", new File(distImgPath));
  
    }  
	

	/**
	 * 转换想要的时间格式
	 */
	public static String formatDateToString(String pattern, Date date) {
		if (date == null || pattern == null)
			return "";
		return new SimpleDateFormat(pattern).format(date);
	}

	/**
	 * 获取文件名的后缀
	 */
	public static String getExt(String fileName) {
		return fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
	}

	/**
	 * 获取文件名
	 * @param fileName
	 * @return
	 */
	public static String getFileName(String fileName) {
		String[] str = fileName.split("\\.");
		return str[0];
	}

	public static boolean filterFiles(String fileName) {
		String reg = "(JPEG|jpeg|JPG|jpg|GIF|gif|BMP|bmp|PNG|png|mp3|mp4|ppt|pptx|doc|docx|xls|xlsx|pdf|flv|3pg|mov|f4v|zip|txt|avi|mpeg|mpg|wmv)$";
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(fileName.toLowerCase());
		return matcher.find();
	}
}