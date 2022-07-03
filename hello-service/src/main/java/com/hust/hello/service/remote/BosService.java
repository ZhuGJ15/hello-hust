package com.hust.hello.service.remote;

import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.services.bos.BosClient;
import com.baidubce.services.bos.BosClientConfiguration;
import com.baidubce.services.bos.model.BosObject;
import com.baidubce.services.bos.model.ObjectMetadata;
import com.baidubce.services.bos.model.PutObjectResponse;
import com.hust.hello.common.builder.SystemExceptionBuilder;
import com.hust.hello.common.enums.FileTypeEnum;
import com.hust.hello.common.exception.SystemException;
import com.hust.hello.common.utils.BosPathUtils;
import com.hust.hello.common.utils.HelloStringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.InputStream;
import java.net.URL;

/**
 * Copyright zhuganjun ©. All Rights Reserved.
 *
 * @author: zhuganjun
 * @date: 2022/3/6 21:57
 * @description:
 */
@Slf4j
@Service
public class BosService {

    @Value("${BaiDuCloud.BOS.AK}")
    private String ACCESS_KEY_ID;

    @Value("${BaiDuCloud.BOS.SK}")
    private String SECRET_ACCESS_KEY;

    @Value("${BaiDuCloud.BOS.BucketName}")
    private String BUCKET_NAME;

    @Value("${BaiDuCloud.BOS.EndPoint}")
    private String ENDPOINT;

    private BosClient bosClient;

    @PostConstruct
    public void initBosClient(){
        log.info("init bos client. AK: {}, SK: {}, BUCKET: {}, ENDPOINT: {}",
                ACCESS_KEY_ID, SECRET_ACCESS_KEY, BUCKET_NAME, ENDPOINT);
        BosClientConfiguration bosClientConfig = new BosClientConfiguration();
        bosClientConfig.setCredentials(new DefaultBceCredentials(ACCESS_KEY_ID, SECRET_ACCESS_KEY));
        bosClientConfig.setEndpoint(ENDPOINT);
        bosClient = new BosClient(bosClientConfig);
        log.info("init bos client. success.");
    }

    @PreDestroy
    public void shutdown(){
        bosClient.shutdown();
        log.info("shutdown bos client. success");
    }

    /**
     * upload file input stream to bos with specified key, and return etag
     */
    public String upload(InputStream inputStream, String key){
        try{
            log.info("BosService. upload. start. key:{} ", key);
            long uploadTime = System.currentTimeMillis();
            PutObjectResponse putObjectResponse = bosClient.putObject(BUCKET_NAME, key, inputStream);
            uploadTime = System.currentTimeMillis() - uploadTime;
            log.info("BosService. upload. done. eTag:{}, uploadTime:{}",
                    putObjectResponse.getETag(), uploadTime);
            return putObjectResponse.getETag();
        } catch (Exception e){
            log.error("BosService. upload. fail. ", e);
            throw SystemExceptionBuilder.createBosException("上传文件失败");
        }
    }

    public String upload(InputStream inputStream, String key, Long contentLength){
        try{
            log.info("BosService. upload. start. key:{}, contentLength:{}", key, contentLength);
            ObjectMetadata objectMeta = new ObjectMetadata();
            objectMeta.setContentLength(contentLength);
            long uploadTime = System.currentTimeMillis();
            PutObjectResponse putObjectResponse = bosClient.putObject(BUCKET_NAME, key, inputStream, objectMeta);
            uploadTime = System.currentTimeMillis() - uploadTime;
            log.info("BosService. upload. done. eTag:{}, uploadTime:{}", putObjectResponse.getETag(), uploadTime);
            return putObjectResponse.getETag();
        } catch (Exception e){
            log.error("BosService. upload. fail. ", e);
            throw SystemExceptionBuilder.createBosException("上传文件失败");
        }
    }

    public BosObject download(String key){
        try{
            log.info("BosService. download. start. key:{}", key);
            long downloadTime = System.currentTimeMillis();
            BosObject object = bosClient.getObject(BUCKET_NAME, key);
            downloadTime = System.currentTimeMillis() - downloadTime;
            log.info("BosService. download. done. eTag:{}, downloadTime:{}", object.getObjectMetadata().getETag(), downloadTime);
            return object;
        }catch (Exception e){
            log.error("BosService. download. fail. ", e);
            throw SystemExceptionBuilder.createBosException("下载文件失败");
        }
    }

    public String getDownloadUrl(String key, int expirationTime){
        try{
            log.info("BosService. getDownloadUrl. start. key:{}, expirationTime:{}", key, expirationTime);
            URL url = bosClient.generatePresignedUrl(BUCKET_NAME, key, expirationTime);
            log.info("BosService. getDownloadUrl. done. url:{}", url.toString());
            return url.toString();
        } catch (Exception e){
            log.error("BosService. getDownloadUrl. fail. ", e);
            return "";
        }
    }

    /**
     * 用上传默认头像的方式，创建bos文件夹
     */
    public String createFolder(String userId, Byte sex){
        try{
            log.info("BosService. createFolder. userId:{}, sex:{}", userId, sex);
            String bosKey = HelloStringUtils.generateFileObjectPath(FileTypeEnum.PIC_AVATAR, userId);
            InputStream inputStream = null;
            if(0 == sex){
                inputStream = this.getClass().getResourceAsStream("/image/girl/girl_1.png");
                bosKey = bosKey + "girl_1.png";
            } else {
                inputStream = this.getClass().getResourceAsStream("/image/boy/boy_1.png");
                bosKey = bosKey + "boy_1.png";
            }
            upload(inputStream, bosKey);
            String avatarPath = getDownloadUrl(bosKey, -1);
            log.info("BosService. createFolder. done.");
            return avatarPath;
        } catch (Exception e){
            log.error("BosService. createFolder. error.");
            throw SystemExceptionBuilder.createBosException("用户头像上传失败");
        }
    }

    /**
     * 上传默认贴吧头像，并创建文件夹
     */
    public void createFolder(String barId) {
        try{
            log.info("BosService. createFolder. barId:{}", barId);
            String bosKey = BosPathUtils.generateBarFileKey(barId, FileTypeEnum.BAR_AVATAR, BosPathUtils.BAR_DEFAULT_AVATAR);
            InputStream inputStream = this.getClass().getResourceAsStream("/image/bar/default_avatar.png");
            upload(inputStream, bosKey);
            log.info("BosService. createFolder. done.");
        } catch (Exception e){
            log.error("BosService. createFolder. error.");
            throw SystemExceptionBuilder.createBosException("贴吧头像上传失败");
        }
    }

    public boolean isExist(String key){
            return bosClient.doesObjectExist(BUCKET_NAME, key);
    }

}
