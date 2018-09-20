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
                }else{
                    log.error("error read swagger local file: {}", serviceFile);
                }
                //Files.move(Paths.get("./src/main/resources/swagger/error/" + serviceFile), Paths.get("./src/main/resources/swagger/finish/" + serviceFile));
                log.info("finish move file {} to finish folder.", serviceFile);
            } catch (Exception e) {
                log.error("error parsing on {}", serviceFile);
                //try {
                //	Files.move(Paths.get("./src/main/resources/swagger/error/" + serviceFile), Paths.get("./src/main/resources/swagger/guru/" + serviceFile));
                //} catch (IOException e1) {
                //	log.info("error on move file to error folder", e);
                //}
            }
        }
        log.info("RestStyleUrls :{}",fetureCount.getRestStyleUrls());
        log.info("AtMostTwentyOperation :{}",fetureCount.getAtMostTwentyOperation());
        log.info("HttpsSupport :{}",fetureCount.getHttpsSupport());
        log.info("ExplainErrorMessages :{}",fetureCount.getExplainErrorMessages());
        log.info("HttpSatausCodeUse :{}",fetureCount.getHttpSatausCodeUse());
        log.info("OutputJson :{}",fetureCount.getOutputJson());
        log.info("InputJson :{}",fetureCount.getInputJson());
        log.info("UserAuthentication :{}",fetureCount.getUserAuthentication());
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
