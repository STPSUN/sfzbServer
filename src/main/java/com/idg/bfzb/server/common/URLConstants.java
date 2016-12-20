package com.idg.bfzb.server.common;

public class URLConstants {
/**
 *运营平台的  URL  都配置到这里  命名格式   类名(字母全大写)+“_”+方法(字母全大写)   = “服务地址”
 */
	
	
	/**
	 * 科目分类接口
	 */
   public final static String CRMENTCUSTOMCONTROLLER_CUSTOMPAGELIST = "/pageList";
   
   public final static String CRMCONTROLLER_PACKAGEPAGELIST = "/pageList";
   
   public final static String CRMCONTROLLER_ADD = "/add";
   
   public final static String CRMCONTROLLER_UPDATE = "/update";
   
   public final static String CRMCONTROLLER_DELETE = "/delete";
   
   /**
    * 登录以及首页操作
    */
   public final static String  lOGINCONTROLLER_IMAGE= "/image";
   public final static String  lOGINCONTROLLER_LOGIN= "/login";
   public final static String  lOGINCONTROLLER_MAIN= "/main";
   public final static String  lOGINCONTROLLER_QRYMENUDATA= "/qryMenuData";
   public final static String  lOGINCONTROLLER_LOGINOUT= "/loginOut";
   public final static String  INDEXCONTROLLER_QUERYMENUS= "/queryMenus";
   public final static String  INDEXCONTROLLER_QRYSELFMSG= "/qrySelfMsg";
   public final static String  INDEXCONTROLLER_QRYFASTMENULIST= "/qryFastMenuList";
   public final static String  INDEXCONTROLLER_QRYSELFFASTMENU= "/qryselfFastMenu";
   public final static String  INDEXCONTROLLER_ADDFASTMENU= "/addFastMenu/{menuIds}";
   public final static String  INDEXCONTROLLER_DELETEFASTMENU= "/deleteFastMenu/{menuId}";
   /**
    * 后台内容管理模块
    */
   public final static String  CONTENT_AD_BANNERS = "/adBanners";
   public final static String  CONTENT_FRIENDSHIP_LINK = "/friendshipLink";
   public final static String  CONTENT_WEB_NEWS = "/webNewsManager";
   /**
    * 项目管理
    */
   public final static String  CONTENT_PROJECT_MANAGER = "/projectManager";
   public final static String  CONTENT_FALLBACK_MANAGER = "/fallbackManager";
   public final static String  CONTENT_REJECTION_MANAGER = "/rejectionManager";
   public final static String  CONTENT_WARRANTY_MANAGER = "/warrantyManager";
   public final static String  CONTENT_TENRDER_MANAGER = "/tenderManager";
   public final static String  CONTENT_AFTERSALE_MANAGER = "/afterSaleManager";
   /**
    * 上传
    */
   public static final String  FILECONTROLLER_UPLOADVIDEO = "/video";//上传视频
   public static final String  FILECONTROLLER_UPLOADDOC = "/doc";//上传文档
   public static final String  FILECONTROLLER_UPLOADIMAGE = "/image";//上传图片
   public static final String  FILECONTROLLER_UPLOADHEADPIC = "/headPic";//上传头像
   public static final String  FILECONTROLLER_DOWNFILE = "/downFile";//下载
   public static final String  FILECONTROLLER_SHOWFILE = "/showFile";//预览
   public static final String  FILECONTROLLER_GETIMPORTMODEL = "/getImportModel";//下载导入模版

}
