package com.idg.bfzb.server.controller.admin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.alibaba.fastjson.JSONObject;
import com.idg.bfzb.server.common.Constants;
import com.idg.bfzb.server.common.ErrorCode;
import com.idg.bfzb.server.common.model.APIResponse;
import com.idg.bfzb.server.common.model.PageInfo;
import com.idg.bfzb.server.content.model.WebNewsAdminRequest;
import com.idg.bfzb.server.content.model.WebNewsAdminResponse;
import com.idg.bfzb.server.content.model.dto.TContAdvertisementEntity;
import com.idg.bfzb.server.content.service.WebNewsMaganerService;
import com.idg.bfzb.server.controller.api.FileController;
import com.idg.bfzb.server.file.model.dto.AttachmentEntity;
import com.idg.bfzb.server.file.service.AttachmentService;
import com.idg.bfzb.server.utility.servlet.ServletUtil;
import com.idg.bfzb.server.utility.tools.ConfigFileUtils;
/**
 * 项目操作相关action请求
 * @author chen
 *
 */
@Controller
@RequestMapping(value="/admin/webNewsManager/action")
public class WebNewsManagerActionController {
	@Autowired
	private WebNewsMaganerService webNewsService; 
	@Autowired
	private AttachmentService attachmentService;
	private static Logger log = Logger.getLogger(WebNewsManagerActionController.class);
	/**
     * 获取网站新闻列表
     * @param projectRequest
     * @param servletRequest
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public void getWebNewsList(WebNewsAdminRequest projectRequest,HttpServletRequest servletRequest,HttpServletResponse servletResponse) {
    	//String page = request.getParameter("page"); // 取得当前页数,注意这是jqgrid自身的参数
		//String rows = request.getParameter("rows"); // 取得每页显示行数，,注意这是jqgrid自身的参数
		JSONObject jo=new JSONObject();
    	Integer pageNum = 0;
    	Integer pageSize = 0;
    	try {
    		pageNum = Integer.parseInt(servletRequest.getParameter("page"));
		} catch (Exception e) {
			pageNum = 1;
		}
    	try {
    		pageSize = Integer.parseInt(servletRequest.getParameter("rows"));
		} catch (Exception e) {
			pageSize = 10;
		}
    	PageRequest pageable = new PageRequest(pageNum-1, pageSize);
    	PageInfo<WebNewsAdminResponse> pageInfo = this.webNewsService.getWebNewsList(projectRequest,pageable);
    	jo.put("rows", pageInfo.getPageData());
		jo.put("records", pageInfo.getTotalRows());
		jo.put("total", pageInfo.getTotalPages());
		ServletUtil.createSuccessResponse(200, jo, servletResponse);
    }
    /**
     * 保存网站新闻
     * @param projectRequest
     * @param servletRequest
     * @return
     */
    @RequestMapping(value = "/saveWebNews", method = RequestMethod.POST)
    @ResponseBody
    public void saveWebNews(HttpServletRequest servletRequest,HttpServletResponse servletResponse) {
    	//1通过 2不通过
    	String action = servletRequest.getParameter("action");
    	String advId = servletRequest.getParameter("advId");
    	String title = servletRequest.getParameter("title");
    	String advLink = servletRequest.getParameter("advLink");
    	String advContent = servletRequest.getParameter("advContent");
    	String attchId = servletRequest.getParameter("attchId");
    	String advKeyword = servletRequest.getParameter("advKeyword");
    	
    	TContAdvertisementEntity adver = new TContAdvertisementEntity();
    	adver.setAdvId(advId);
    	adver.setTitle(title);
    	adver.setAdvLink(advLink);
    	adver.setAdvContent(advContent);
    	adver.setAdvImg(attchId);
    	adver.setAdvType("news");
    	adver.setAdvKeyword(advKeyword);
    	
    	APIResponse ret = null;
    	if(StringUtils.isEmpty(action)){
    		ret = new APIResponse();
    		ret.setSucess(false);
    		ret.setMessage("数据不能为空！");
    	}else {
    		//add
    		if(action.equals("1")){
    			adver.setAdvId(UUID.randomUUID().toString());
    			ret = webNewsService.addWebNews(adver);
    		}
    		//update
    		else{
    			ret = webNewsService.updateWebNews(adver);
    		}
    	}
    	
    	ServletUtil.createSuccessResponse(200, ret, servletResponse);
    }
    /**
     * 删除网站新闻
     * @param projectRequest
     * @param servletRequest
     * @return
     */
    @RequestMapping(value = "/deleteWebNewsByAdvId", method = RequestMethod.POST)
    @ResponseBody
    public void deleteWebNewsByAdvId(HttpServletRequest servletRequest,HttpServletResponse servletResponse) {
    	String advId = servletRequest.getParameter("advId");
    	
    	APIResponse ret = null;
    	if(StringUtils.isEmpty(advId)){
    		ret = new APIResponse();
    		ret.setSucess(false);
    		ret.setMessage("数据不能为空！");
    	}else {
			ret = webNewsService.deleteWebNewsByAdvId(advId);
    	}
    	
    	ServletUtil.createSuccessResponse(200, ret, servletResponse);
    }
	/**
	 * 上传图片文件
	 * 
	 * @param request
	 * @param response
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@RequestMapping(value = "/images/upload", method = RequestMethod.POST)
	@ResponseBody
	public void uploadImage(HttpServletRequest request,HttpServletResponse response)
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
			
			JSONObject jo=new JSONObject();
			jo.put("data", apiResponse);
			jo.put("res_code", 200);
			response.getWriter().print(jo);
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
//						String fileUrl = file.getName();
						String myFileName = file.getOriginalFilename();
						// 如果名称不为“”,说明该文件存在，否则说明该文件不存在
						if (!"".equals(myFileName.trim())) {
							String ext = FileController.getExt(myFileName);// 获取后缀名
							if (!FileController.filterFiles(ext)) {
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
							String fileName = FileController.formatDateToString("yyyyMMddHHmmssSSS", new Date()) + "." + ext;

							// 获取文件大小
							long size = file.getSize();
							//验证文件大小不超过20M
							if(size>Constants.FILE_MAX_SIZE){
								apiResponse.setErrorCode(ErrorCode.FILE_SIZE_OVER);
								return apiResponse;
							}
							File isfile = new File(filePath);
							if (!isfile.exists() && !isfile.isDirectory()) {
								isfile.mkdirs();
							}
							String path = filePath + fileName;
							File localFile = new File(path);
							file.transferTo(localFile);
							//生成文件的md5码
							String md5 = DigestUtils.md5Hex(new FileInputStream(localFile));
							log.info("文件已上传，正在生成MD5码");
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
							item.setFileMd5(md5);
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
								FileController.resizeImage(attchUrl,smallAttchUrl,Constants.FILE_IMG_WIDTH,Constants.FILE_IMG_HEIGHT);
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
    /**
     * 获取新闻内容
     * @param projectRequest
     * @param servletRequest
     * @return
     */
    @RequestMapping(value = "/getWebNewsContentByAdvId", method = RequestMethod.GET)
    @ResponseBody
    public void getWebNewsContentByAdvId(HttpServletRequest servletRequest,HttpServletResponse servletResponse) {
    	
    	String advId = servletRequest.getParameter("advId");
    	APIResponse ret = null;
    	if(StringUtils.isEmpty(advId)){
    		ret = new APIResponse();
    		ret.setSucess(false);
    		ret.setMessage("网站广告ID不能为空！");
    	}else {
    		
    		ret = this.webNewsService.getWebNewsContentByAdvId(advId);
    	}
    	
		ServletUtil.createSuccessResponse(200, ret, "yyyy/MM/dd",servletResponse);
    }
}