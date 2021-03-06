package ntou.mingjen.rest;

import ntou.mingjen.rest.bean.FetureCount;
import ntou.mingjen.rest.parse.EndpointLevel;
import ntou.mingjen.rest.parse.ServiceLevel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RunGuruLocalData {

    Logger log = LoggerFactory.getLogger(RunGuruLocalData.class);

    @Test
    public void readGuruFiles(){
        FetureCount fetureCount = new FetureCount();
        File sDocFolder = new File("./src/main/resources/GuruSwaggerDoc");
        for (String serviceFile : sDocFolder.list()) {
            log.info("parse swagger guru file: {}", serviceFile);
            try {
                // do something
                String document = readLocalSwagger("./src/main/resources/GuruSwaggerDoc/" + serviceFile);
                if(document != null){
                    ServiceLevel serviceLevel = new ServiceLevel();
                    serviceLevel.parseSwaggerService(document, fetureCount);
                    fetureCount.setSwaggerDoc(fetureCount.getSwaggerDoc()+1);
                }else{
                    log.error("error read swagger local file: {}", serviceFile);
                }
                Files.move(Paths.get("./src/main/resources/GuruSwaggerDoc/" + serviceFile), Paths.get("./src/main/resources/finish/" + serviceFile));
                log.info("finish move file {} to finish folder.", serviceFile);
            } catch (Exception e) {
                log.error("error parsing on {}", serviceFile);
                //log.info("error :{}",e);
                try {
                	Files.move(Paths.get("./src/main/resources/GuruSwaggerDoc/" + serviceFile), Paths.get("./src/main/resources/error/" + serviceFile));
                } catch (IOException e1) {
                	log.info("error on move file to error folder", e);
                }
            }
        }
        log.info("SwaggerDoc :{}",fetureCount.getSwaggerDoc());
        log.info("->");
        log.info("HttpsSupport :{}",fetureCount.getHttpsSupport());
        log.info("AtMostTwentyOperation :{}",fetureCount.getAtMostTwentyOperation());
        log.info("UserAuthentication :{}",fetureCount.getUserAuthentication());
        log.info("");
        log.info("EndpointNumber :{}",fetureCount.getEndpointNumber());
        log.info("->");
        log.info("RestStyleUrls :{}",fetureCount.getRestStyleUrls());
        log.info("InputJson :{}",fetureCount.getInputJson());
        log.info("OutputJson :{}",fetureCount.getOutputJson());
        log.info("HttpStatusCodeUse :{}",fetureCount.getHttpStatusCodeUse());
        log.info("ExplainErrorMessages :{}",fetureCount.getExplainErrorMessages());
        log.info("NoAPIUseInApps :{}",fetureCount.getNoAPIUseInApps());
    }

    // For testing
    public String readLocalSwagger(String path) {
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(path));
            return new String(encoded, "UTF-8");
        } catch (IOException e) {
            System.err.println("read swagger error");
            return null;
        }

    }

}
