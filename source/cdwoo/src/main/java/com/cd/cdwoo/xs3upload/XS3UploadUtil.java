package com.cd.cdwoo.xs3upload;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import com.alibaba.fastjson.JSONObject;
import com.cd.cdwoo.util.CDWooLogger;
import com.cd.cdwoo.util.PropertiesReaderUtils;
/**
 * @author chendong
 */
public class XS3UploadUtil {
  /**
   * xs3ak
   */
	public static final String XS3ACCESSKEY = PropertiesReaderUtils.getProperties("com/fang/olapservice/core/xs3/xs3upload.properties", "XS3ACCESSKEY");
	/**
	 * xs3sk
	 */
	public static final String XS3SECRETKEY = PropertiesReaderUtils.getProperties("com/fang/olapservice/core/xs3/xs3upload.properties", "XS3SECRETKEY");
	/**
	 * 
	 */
	public static final String XS3ENDPOINT = PropertiesReaderUtils.getProperties("com/fang/olapservice/core/xs3/xs3upload.properties", "XS3ENDPOINT");
	/**
	 * upload bucket name
	 */
	public static final String XS3BUCKETNAME =PropertiesReaderUtils.getProperties("com/fang/olapservice/core/xs3/xs3upload.properties", "XS3BUCKETNAME");
	public static final String SUCCESSCODE = "100";
	public static final String ERRORCODE = "102";
	public static final String GETFILETYPEHOST = PropertiesReaderUtils.getProperties("com/fang/olapservice/core/xs3/xs3upload.properties", "GETFILETYPEHOST");
  /**
   * 读取文件指定字节长度
   */
  private static final Integer BYTESIZE = 1024;
	public static String sign4new(String putPolicy) throws Exception{
    String encodePutPolicy = EncodeUtils.urlsafeEncode(putPolicy);
    String singSk = EncryptUtil.sha1Hex(encodePutPolicy.getBytes(), XS3SECRETKEY);//签名
    String skValue = EncodeUtils.urlsafeEncode(singSk);//Base64编码
    return XS3ACCESSKEY+":"+skValue+":"+encodePutPolicy;
  }
  /**
   * 
   * @param data data
   * @return String 
   */
  private static String encodeBase64(byte[] data) {
    String base64 = new String(Base64.encodeBase64(data));
    if (base64.endsWith("\r\n")) {
      base64 = base64.substring(0, base64.length() - 2);
    }
    if (base64.endsWith("\n")) {
      base64 = base64.substring(0, base64.length() - 1);
    }
    return base64;
  }
  public static String httpPost(String url, Map<String, String> params, Map<String, String> headMap, File file) {
    String response = "";
    HttpPost httpPost = null;
    CloseableHttpResponse ht = null;
    InputStream is = null;
    BufferedReader br = null;
    try {
        httpPost = new HttpPost(url);
        List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
        if (file != null) {
            MultipartEntityBuilder mEntityBuilder = MultipartEntityBuilder.create();
            FileBody fileBody = new FileBody(file, ContentType.APPLICATION_OCTET_STREAM, file.getName());
            mEntityBuilder.addPart("file", fileBody);
            mEntityBuilder.addTextBody("desc", file.getName());
            if (params != null && params.size() > 0) {
                for(Map.Entry<String, String> entry:params.entrySet()){
                    mEntityBuilder.addTextBody(entry.getKey(), entry.getValue(), ContentType.create("text/plain", Charset.forName("UTF-8")));
                }
            }
            httpPost.setEntity(mEntityBuilder.build());
        } else if (params != null && params.size() > 0) {
            for(Map.Entry<String, String> entry:params.entrySet()){
                paramsList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            HttpEntity he = null;
            try {
                he = new UrlEncodedFormEntity(paramsList, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            httpPost.setEntity(he);
        }
        if (headMap != null && headMap.size() > 0) {
            for(Map.Entry<String, String> entry:headMap.entrySet()){
                httpPost.setHeader(entry.getKey(),entry.getValue());
            }
        }
        if (!httpPost.containsHeader("User-Agent"))
            httpPost.addHeader("User-Agent", "wcs-java-sdk-2.0.0");
        CloseableHttpClient hc = new DefaultHttpClient();
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(30000).build();//设置请求和传输超时时间
        httpPost.setConfig(requestConfig);
        ht = hc.execute(httpPost);
        HttpEntity het = ht.getEntity();
        is = het.getContent();
        br = new BufferedReader(new InputStreamReader(is, "utf8"));
        String readLine;
        StringBuffer sb = new StringBuffer();
        while ((readLine = br.readLine()) != null) {
            sb.append(readLine);
        }
        response += sb.toString();
        int status = ht.getStatusLine().getStatusCode();
        String result = null;
        if (status == 200) {
            result = response;
            CDWooLogger.info(result);
        }
        result = response;
        CDWooLogger.info(result);
        return result;
      } catch (IOException e) {
          e.printStackTrace();
          return null;
      } finally {
          IOUtils.closeQuietly(is);
          IOUtils.closeQuietly(br);
          IOUtils.closeQuietly(ht);
          if (httpPost != null) {
              httpPost.releaseConnection();
          }
      }
  }
  /**
   * java restful上传文件方法。
   * @param file 文件
   * @param suffix 文件后缀
   * @return 文件路径
   * @throws Exception
   */
  public static String uploadFile2CNC(File file, String suffix) throws Exception {
    Map<String, String> params = new HashMap<String, String>();
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("scope", XS3BUCKETNAME);
    map.put("deadline", System.currentTimeMillis()+300000);
    map.put("callbackUrl", "http://itbs.light.fang.com/olapservice/CNCUloadCallBack/upload2CNCCallback.do");
    map.put("callbackBody", "imageInfo=$(imageInfo)&bucket=$(bucket)&key=$(key)&businessLine=bdp.core.hb");
    String sign = sign4new(JSONObject.toJSONString(map));
    CDWooLogger.error(sign);
    params.put("token", sign);
    params.put("key", UUID.randomUUID() + ".png");
    params.put("x:position", "this is position");
    return httpPost("http://chendong.up9.v1.wcsapi.com/file/upload", params, null, file);
  }
  public static void main(String[] args) throws Exception {
    /*File file = new File("C:\\Users\\user\\Pictures\\500x500_90_1.jpg");
    uploadFile2CNC(file, "jpg");*/
    //deleteCNCFile("06475278-08de-4cdb-a143-1192e469909a.png");
    /*Map<String, Object> map = new HashMap<>();
    map.put("scope", XS3BUCKETNAME);
    map.put("deadline", System.currentTimeMillis()+300000);
    String sign = sign4new(JSONObject.toJSONString(map));
    System.out.println(sign + "\n" + UUID.randomUUID().toString()+".jpg");*/
    //deleteFile("http://s3.bj.xs3cnc.com/tel400/bdp.core.hb/8ad9d0fbc3204cc08de5f75bfd0d52cc.mp4");
  }
  /**
   * 获取上传token
   * @param businessLine 业务线名称
   * @return token值
   * @throws Exception
   */
  public static String getCNCUploadToken(String businessLine) throws Exception {
    Map<String, Object> map = new HashMap<>();
    map.put("scope", XS3BUCKETNAME);
    map.put("deadline", System.currentTimeMillis()+300000);
    map.put("callbackUrl", "http://itbs.light.fang.com/olapservice/CNCUloadCallBack/upload2CNCCallback.do");
    map.put("callbackBody", "imageInfo=$(imageInfo)&bucket=$(bucket)&key=$(key)&businessLine=" + businessLine);
    String sign = sign4new(JSONObject.toJSONString(map));
    return sign;
  }
  /**
   * 删除方法
   * @param key 文件名称
   * @throws Exception
   */
  public static void deleteCNCFile(String key) throws Exception {
    Map<String, String> params = new HashMap<String, String>();
    String deleteUrl = EncodeUtils.urlsafeEncode("bdp-upload-file:" + key);
    CDWooLogger.info(deleteUrl);
    String signingStr = "/delete/" + deleteUrl+"\n";
    String singSk = EncryptUtil.sha1Hex(signingStr.getBytes(), XS3SECRETKEY);//签名
    String skValue = EncodeUtils.urlsafeEncode(singSk);//Base64编码
    String sign = XS3ACCESSKEY+":"+skValue;
    params.put("Authorization", sign);
    httpPost("http://chendong.mgr9.v1.wcsapi.com/delete/"+deleteUrl, null, params, null);
  }
}
