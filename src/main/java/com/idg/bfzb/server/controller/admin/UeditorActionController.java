package com.idg.bfzb.server.controller.admin;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
//import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.alibaba.fastjson.JSONObject;
import com.baidu.ueditor.ActionEnter;
import com.idg.bfzb.server.common.Constants;
import com.idg.bfzb.server.controller.api.FileController;
import com.idg.bfzb.server.utility.tools.ConfigFileUtils;

/**
 * Uditor操作相关action请求
 * @author chen
 *
 */
@Controller
@RequestMapping(value="/pages/assets/ueditor/jsp/")
public class UeditorActionController {
	private static Logger log = Logger.getLogger(UeditorActionController.class);
	@RequestMapping(value="controller")
	public void ueditorController(HttpServletRequest servletRequest,HttpServletResponse servletResponse) {
		
		String action = servletRequest.getParameter("action");
		if(action.equals("config")){
			try {
				servletRequest.setCharacterEncoding( "utf-8" );
				servletResponse.setHeader("Content-Type" , "text/html");
				
				String rootPath = servletRequest.getSession().getServletContext().getRealPath("/");
				//config.json所在路径
				String configText =  new ActionEnter( servletRequest, rootPath + "WEB-INF/pages/assets/ueditor/jsp/" ).exec();
				servletResponse.getWriter().write(configText);
				
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//图片文件上传
		else if(action.equals("uploadimage")){
			//图片上传成功的JSON串
			/*{
				"state": "SUCCESS",
				"original": "80px - \u526f\u672c (2).jpg",
				"size": "13252",
				"title": "1465731377326075274.jpg",
				"type": ".jpg",
				"url": "/ueditor/jsp/upload/image/20160612/1465731377326075274.jpg"
			}*/
			HashMap<String,String> jsonMap = fileUpload(servletRequest,Constants.FILE_IMAGE_PATH,Constants.FILE_TYPE_IMG);
			String json = JSONObject.toJSONString(jsonMap);
			
			try {
				servletResponse.getWriter().print(json);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 文件上传
	 * @param request
	 * @param response
	 * @param uploadType
	 * @return
	 */
	public HashMap<String, String> fileUpload(HttpServletRequest request,String filePath,String fileType) {
		//filePath = request.getSession().getServletContext().getRealPath("/")+"pages/course/"+filePath;//服务器目录
		HashMap<String, String> apiResponse = new HashMap<String, String>();
		
//		List<AttachmentEntity> entitys = new ArrayList<AttachmentEntity>();
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
//						String fileUrl = file.getName();
						String myFileName = file.getOriginalFilename();
						// 如果名称不为“”,说明该文件存在，否则说明该文件不存在
						if (!"".equals(myFileName.trim())) {
							String ext = FileController.getExt(myFileName);// 获取后缀名
							if (!FileController.filterFiles(ext)) {
								apiResponse.put("state", "文件不存在！");
								return apiResponse;
							}
							
//							int type = 3;// 获取文件类型
//							if (ext.equals("txt") || ext.equals("doc")
//									|| ext.equals("docx") || ext.equals("pdf")
//									|| ext.equals("pptx") || ext.equals("ppt")) {
//								type = 1;
//							} else if (ext.equals("rmvb") || ext.equals("avi")||ext.equals("flv")||ext.equals("3pg")||ext.equals("f4v")||ext.equals("mov")||ext.equals("mpeg")||ext.equals("mp4")||ext.equals("mpg")||ext.equals("wmv")) {
//								type = 2;
//							} else if (ext.equalsIgnoreCase("jpeg") || ext.equalsIgnoreCase("jpg")||ext.equalsIgnoreCase("gif")||ext.equalsIgnoreCase("bmp")||ext.equalsIgnoreCase("png")){
//								type = 3;
//							} else {
//								type = 4;
//							}
							// 重命名上传后的文件名
							String fileName = FileController.formatDateToString("yyyyMMddHHmmssSSS", new Date()) + "." + ext;

							// 获取文件大小
							long size = file.getSize();
							//验证文件大小不超过20M
							if(size>Constants.FILE_MAX_SIZE){
								apiResponse.put("state", "文件超过20M！");
								return apiResponse;
							}
							File isfile = new File(filePath);
							if (!isfile.exists() && !isfile.isDirectory()) {
								isfile.mkdirs();
							}
							String path = filePath + fileName;
							File localFile = new File(path);
							file.transferTo(localFile);
//							//生成文件的md5码
//							String md5 = DigestUtils.md5Hex(new FileInputStream(localFile));
//							log.info("文件已上传，正在生成MD5码");
//							AttachmentEntity item = new AttachmentEntity();
//							item.setAttchId(UUID.randomUUID().toString());
//							item.setAttchName(myFileName);
//							item.setAttchRename(fileName);
//							item.setTargetId("1");
//							item.setTargetType("headImg");
//							item.setAttchSize(size);
//							item.setAttchUrl(fileName);
//							item.setCreateTime(new Timestamp(new Date().getTime()));
//							item.setFileType(fileType);
//							item.setFileMd5(md5);
//							item.setState((short)0);
//							attachmentService.addAttachment(item);
//							
//							if(type==3){//图片类型不做批量上传，同时生成小图，并返回大小图地址
//								Map<String,Map<String,String>> map = new HashMap<String,Map<String,String>>();
//								Map<String,String> map1 = new HashMap<String,String>();
//								map1.put("attch_id", item.getAttchId());
//								map1.put("source_file_name", item.getAttchName());
//								map1.put("image_url", ConfigFileUtils.HEAD_ICON_URL+item.getAttchUrl());
//								//小图片
//								String attchUrl = filePath+fileName;
//								String smallAttchUrl = attchUrl.replace(".","_small.");
//								FileController.resizeImage(attchUrl,smallAttchUrl,Constants.FILE_IMG_WIDTH,Constants.FILE_IMG_HEIGHT);
//								item.setAttchId(UUID.randomUUID().toString());
//								item.setAttchUrl(new File(smallAttchUrl).getName());
//								attachmentService.addAttachment(item);//
//								Map<String,String> map2 = new HashMap<String,String>();
//								map2.put("attch_id", item.getAttchId());
//								map2.put("source_file_name", item.getAttchName());
//								map2.put("image_url", ConfigFileUtils.HEAD_ICON_URL+item.getAttchUrl());
//								map.put("big_image", map1);
//								map.put("small_image", map2);
//								apiResponse.setData(map);
//								apiResponse.setMessage(APIResponse.SUCESS_MSG);
//								return apiResponse;
//							}
//							entitys.add(item);
							
							apiResponse.put("state", "SUCCESS");
							apiResponse.put("original", fileName);
							apiResponse.put("size", localFile.getTotalSpace()+"");
							apiResponse.put("title", fileName);
							apiResponse.put("type", ext);
							apiResponse.put("url", ConfigFileUtils.HEAD_ICON_URL+fileName);
						}
					}
				}
			} else {
				apiResponse.put("state", "文件系统为空！");
			}
		} catch (Exception ex) {
			log.error("getCourseList failed", ex);
			apiResponse.put("state", "文件上传异常！");
		}
		return apiResponse;
	}
}