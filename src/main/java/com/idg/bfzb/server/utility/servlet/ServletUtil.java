package com.idg.bfzb.server.utility.servlet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.idg.bfzb.server.utility.tools.DateUtil;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class ServletUtil {

    //服务器标识
    private static String hostName;

    //响应的ContentType内容
    private static final String RESPONSE_CONTENTTYPE = "application/json";

    //响应编码
    private static final String RESPONSE_CHARACTERENCODING = "utf-8";

    //业务名称的缩写
    private static final String BIZ_NAME = "";

    private static Logger log = Logger.getLogger(ServletUtil.class);

    static {
        try {
            InetAddress netAddress = InetAddress.getLocalHost();
            hostName = netAddress.getHostName();
            log.info(String.format("Server %1$s started.", hostName));
        } catch (UnknownHostException e) {
            log.error("netAddress.getHostName failed", e);
        }
    }

    /**
     * 生成参数不正确报文
     *
     * @param response 响应报文
     */
    public static String createParamErrorResponse(HttpServletResponse response) {
        final String code = "REQUIRE_ARGUMENT";
        String message = "缺少参数";
        return createErrorResponse(400, 400, code, message, response);
    }


    /**
     * 认证失败
     *
     * @param response 响应报文
     */
    public static String createAuthorizationErrorResponse(HttpServletResponse response) {
        final String code = "AUTH_INVALID_TOKEN";
        String message = "请求认证失败！请按规范在Header报文头中附上正确的Authorization认证属性!";
        return createErrorResponse(401, 401, code, message, response);
    }

    /**
     * 授权失败
     *
     * @param response 响应报文
     */
    public static String createAuthorizeErrorResponse(HttpServletResponse response) {
        final String code = "AUTH_DENIED";
        String message = "请求失败，没有访问或操作该资源的权限!";
        return createErrorResponse(403, 403, code, message, response);
    }

    /**
     * 授权失败
     *
     * @param response 响应报文
     */
    public static String createAuthorizeErrorResponse(HttpServletResponse response, String message) {
        final String code = "AUTH_DENIED";
        return createErrorResponse(403, 403, code, message, response);
    }

    /**
     * 路径不存在
     *
     * @param response 响应报文
     */
    public static String createNotFoundErrorResponse(HttpServletResponse response) {
        final String code = "NOT_FOUND";
        String message = "请求的URL路径不存在!";
        return createErrorResponse(404, 404, code, message, response);
    }



    /**
     * 生成错误报文
     *
     * @param httpStatus http状态
     * @param res_code   接口返回状态吗
     * @param code       接口返回正文
     * @param message    接口返回消息
     * @param response   servlet响应
     * @return 接口输出报文
     */
    public static String createErrorResponse(Integer httpStatus, Integer res_code, Object code,
                                             String message, HttpServletResponse response) {
        code = BIZ_NAME + code;
        PrintWriter printWriter = null;
        String jsonString = "";
        try {
            response.setCharacterEncoding(RESPONSE_CHARACTERENCODING);
            response.setContentType(RESPONSE_CONTENTTYPE);
            response.setStatus(httpStatus);

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("code", code);
            map.put("message", message);
            //map.put("request_id", requestId==null?"":requestId);
            //map.put("host_id", hostName);
            map.put("res_code", res_code);
            map.put("server_time", DateUtil.formatISO8601DateString(new Date()));

            printWriter = response.getWriter();
            jsonString = JSON.toJSONString(map, SerializerFeature.WriteMapNullValue);
            printWriter.write(jsonString);
            printWriter.flush();
        } catch (Exception e) {
            log.error("createResponse failed", e);
        } finally {
            if (null != printWriter) printWriter.close();
        }

        return jsonString;
    }


    public static String createSuccessResponse(Object result, HttpServletResponse response) {
        Map<String, Object> parameter = new HashMap<String, Object>();
        try {
            Field[] fields = new Field[2];
             fields[0] = result.getClass().getDeclaredField("result");
            fields[1] = result.getClass().getDeclaredField("returnData");

            for (Field field : fields) {
                String fieldName = field.getName();
                Object jsonobject = null;
                String firstLetter = fieldName.substring(0, 1).toUpperCase();
                String getMethodName = "get" + firstLetter + fieldName.substring(1);
                Method getMethod;
                try {
                    getMethod = result.getClass().getMethod(getMethodName);
                    Object  o = getMethod.invoke(result);
                    String str = JSONObject.toJSONString(o, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);
                    jsonobject = JSONObject.parse(str);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (jsonobject != null) {
                    parameter.put(fieldName, jsonobject);
                }
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("result",parameter.get("result"));
            map.put("return_data",parameter.get("returnData"));
            return createSuccessResponse(HttpStatus.OK.value(), map, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return createSuccessResponse(HttpStatus.OK.value(), result, response);

    }

    /**
     * 生成成功报文
     *
     * @param httpCode http状态吗
     * @param result   接口返回正文
     * @param response servlet响应
     */
    public static String createSuccessResponse(Integer httpCode, Object result, HttpServletResponse response) {
        return createSuccessResponse(httpCode, result, SerializerFeature.WriteMapNullValue, null, response);
    }
    /**
     * 生成成功报文（允许传入日期格式）
     *
     * @param httpCode http状态吗
     * @param result   接口返回正文
     * @param response servlet响应
     */
    public static String createSuccessResponse(Integer httpCode, Object result,String dateFormat ,HttpServletResponse response) {
        return createSuccessResponse(httpCode, result, SerializerFeature.WriteMapNullValue, null,dateFormat, response);
    }
    public static String createSuccessResponse(Integer httpCode, String message, Object result, HttpServletResponse response) {
        return createSuccessResponse(httpCode, message, result, SerializerFeature.WriteMapNullValue, null, response);
    }


    public static String createSuccessResponse(Integer httpCode, Object result, SerializeFilter filter, HttpServletResponse response) {

        return createSuccessResponse(httpCode, result, SerializerFeature.WriteMapNullValue, filter, response);

    }

    public static String createSuccessResponse(Integer httpCode, Object result, SerializerFeature serializerFeature, HttpServletResponse response) {
        return createSuccessResponse(httpCode, result, serializerFeature, null, response);
    }
    /**
     * 允许传入日期格式化
     * @param httpCode
     * @param result
     * @param serializerFeature
     * @param filter
     * @param dateFormat
     * @param response
     * @return
     */
    public static String createSuccessResponse(Integer httpCode, Object result, SerializerFeature serializerFeature, SerializeFilter filter,String dateFormat ,HttpServletResponse response) {
        PrintWriter printWriter = null;
        String jsonString = "";
        try {
            response.setCharacterEncoding(RESPONSE_CHARACTERENCODING);
            response.setContentType(RESPONSE_CONTENTTYPE);
            response.setStatus(httpCode);
            printWriter = response.getWriter();
            if (null != result) {
                if (null != filter) {
                    jsonString = JSONObject.toJSONString(result, filter, serializerFeature);
                } else {
                    //jsonString = JSONObject.toJSONString(result, serializerFeature);
                    jsonString = JSONObject.toJSONStringWithDateFormat(result,dateFormat,serializerFeature);
                }
                printWriter.write(jsonString);
            }
            printWriter.flush();

        } catch (Exception e) {
            log.error("createResponse failed", e);
        } finally {
            if (null != printWriter) printWriter.close();
        }
        return jsonString;
    }
    public static String createSuccessResponse(Integer httpCode, Object result, SerializerFeature serializerFeature, SerializeFilter filter, HttpServletResponse response) {
        PrintWriter printWriter = null;
        String jsonString = "";
        try {
            response.setCharacterEncoding(RESPONSE_CHARACTERENCODING);
            response.setContentType(RESPONSE_CONTENTTYPE);
            response.setStatus(httpCode);
            printWriter = response.getWriter();
            if (null != result) {
                if (null != filter) {
                    jsonString = JSONObject.toJSONString(result, filter, serializerFeature);
                } else {
                    //jsonString = JSONObject.toJSONString(result, serializerFeature);
                    jsonString = JSONObject.toJSONStringWithDateFormat(result,"yyyy-MM-dd HH:mm:ss",serializerFeature);
                }
                printWriter.write(jsonString);
            }
            printWriter.flush();

        } catch (Exception e) {
            log.error("createResponse failed", e);
        } finally {
            if (null != printWriter) printWriter.close();
        }
        return jsonString;
    }

    public static String createSuccessResponse(Integer httpCode, String message, Object result, SerializerFeature serializerFeature, SerializeFilter filter, HttpServletResponse response) {
        PrintWriter printWriter = null;
        String jsonString = "";
        try {
            response.setCharacterEncoding(RESPONSE_CHARACTERENCODING);
            response.setContentType(RESPONSE_CONTENTTYPE);
            response.setStatus(httpCode);
            printWriter = response.getWriter();
            Map<String, Object> map = new HashMap<String, Object>();
            if (null != result) {
                map.put("res_code", httpCode);
                map.put("message", message);
                map.put("data", result);
                if (null != filter) {
                    jsonString = JSONObject.toJSONString(map, filter, serializerFeature);
                } else {
                    jsonString = JSONObject.toJSONString(map, serializerFeature);
                }
                printWriter.write(jsonString);
            }
            printWriter.flush();

        } catch (Exception e) {
            log.error("createResponse failed", e);
        } finally {
            if (null != printWriter) printWriter.close();
        }
        return jsonString;
    }
}
