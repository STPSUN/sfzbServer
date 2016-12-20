package com.idg.bfzb.server.controller.api;

import com.idg.bfzb.server.content.service.WebNewsMaganerService;
import com.idg.bfzb.server.controller.BaseController;
import com.idg.bfzb.server.homepage.service.HomepageService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
/**
 * 类名称：
 * 类描述：
 * 创建人：chen
 * 创建日期：2016/10/25
 */
@Controller
@RequestMapping("/api/homepage")
public class HomepageController extends BaseController {

    @Autowired
    private HomepageService ahomeService;
    @Autowired
    private WebNewsMaganerService webNewsService;
    private Logger logger = LoggerFactory.getLogger(HomepageController.class);

    /**
     * 获取首页banners  [GET]
     * @param servletRequest
     */
    @RequestMapping(value = "actions/banners/query", method = RequestMethod.GET)
    @ResponseBody
    public Object bannersQuery(HttpServletRequest servletRequest) {
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("请求URL:%1$s.",servletRequest.getRequestURI()));
        }
        String adv_location = servletRequest.getParameter("adv_location");
        String adv_client_type = servletRequest.getParameter("adv_client_type");
        //测试用
        return this.ahomeService.getHomePageBanners(adv_client_type, adv_location);
    }
    /**
     * 获取首页webnews  [GET]
     * @param servletRequest
     */
    @RequestMapping(value = "actions/webnews/query", method = RequestMethod.GET)
    @ResponseBody
    public Object webnewsQuery(HttpServletRequest servletRequest) {
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("请求URL:%1$s.",servletRequest.getRequestURI()));
        }
        
    	Integer pageNum = 0;
    	Integer pageSize = 0;
    	try {
    		pageNum = Integer.parseInt(servletRequest.getParameter("offset"));
		} catch (Exception e) {
			pageNum = 1;
		}
    	try {
    		pageSize = Integer.parseInt(servletRequest.getParameter("size"));
		} catch (Exception e) {
			pageSize = 10;
		}
    	PageRequest pageable = new PageRequest(pageNum-1, pageSize);
        return this.ahomeService.getWebNewsList(pageable);
    }
    /**
     * 获取首页webnews相关文章  [GET]
     * @param servletRequest
     */
    @RequestMapping(value = "actions/webnews/relevant", method = RequestMethod.GET)
    @ResponseBody
    public Object webnewsRelevant(HttpServletRequest servletRequest) {
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("请求URL:%1$s.",servletRequest.getRequestURI()));
        }
        
    	Integer pageNum = 0;
    	Integer pageSize = 0;
    	try {
    		pageNum = Integer.parseInt(servletRequest.getParameter("offset"));
		} catch (Exception e) {
			pageNum = 1;
		}
    	try {
    		pageSize = Integer.parseInt(servletRequest.getParameter("size"));
		} catch (Exception e) {
			pageSize = 10;
		}
    	String keyWord = servletRequest.getParameter("key_word");
    	String webNewsAdvId = servletRequest.getParameter("adv_id");
    	PageRequest pageable = new PageRequest(pageNum-1, pageSize);
        return this.ahomeService.getWebNewsRelevantList(pageable,keyWord,webNewsAdvId);
    }
    /**
     * 获取首页webnews上一篇和下一篇文章  [GET]
     * @param servletRequest
     */
    @RequestMapping(value = "actions/webnews/queryprenest", method = RequestMethod.GET)
    @ResponseBody
    public Object queryprenest(HttpServletRequest servletRequest) {
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("请求URL:%1$s.",servletRequest.getRequestURI()));
        }
        
    	String webNewsAdvId = servletRequest.getParameter("adv_id");
        return this.ahomeService.getWebNewsqueryPreNest(webNewsAdvId);
    }
    /**
     * 获取首页webnews详情  [GET]
     * @param servletRequest
     */
    @RequestMapping(value = "actions/webnews/{adv_id}/detail", method = RequestMethod.GET)
    @ResponseBody
    public Object webnewsDetail(@PathVariable("adv_id")String advId,HttpServletRequest servletRequest) {
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("请求URL:%1$s.",servletRequest.getRequestURI()));
        }
        
        return this.ahomeService.getWebNewsDetail(advId);
    }
    /**
     * 根据webnewsID更新咨询阅读量  [GET]
     * @param servletRequest
     */
    @RequestMapping(value = "actions/webnews/updateReadCount", method = RequestMethod.POST)
    @ResponseBody
    public Object updateReadCount(HttpServletRequest servletRequest) {
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("请求URL:%1$s.",servletRequest.getRequestURI()));
        }
        
        String advId = servletRequest.getParameter("adv_id");
		return this.ahomeService.updateReadCount(advId);
    }
}
