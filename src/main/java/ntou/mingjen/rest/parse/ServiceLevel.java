package ntou.mingjen.rest.parse;

import io.swagger.models.Scheme;
import io.swagger.models.Swagger;
import io.swagger.models.auth.SecuritySchemeDefinition;
import io.swagger.parser.SwaggerParser;
import ntou.mingjen.rest.bean.FetureCount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Security;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class ServiceLevel {

    Logger log = LoggerFactory.getLogger(ServiceLevel.class);

    EndpointLevel endpointLevel;

    @RequestMapping(value = "/swagger", method = RequestMethod.POST)
    public void parseSwaggerService(@RequestBody String swaggerDoc, FetureCount fetureCount){
        endpointLevel = new EndpointLevel();
        String title = null;
        String description = null;
        String host = null;
        String basePath = null;
        String baseUrl = null;
        boolean httpsFlag = false;

        // Service Level check
        int operationQuantity = 0;
        List<Scheme> schemes = null;
        Map<String, SecuritySchemeDefinition> securityDefinitions = null;
        List<String> produces = null;

        // save restful feture
        ArrayList<String> feture = new ArrayList<>();

        Swagger swagger = new SwaggerParser().parse(swaggerDoc);
        title = swagger.getInfo().getTitle();
        description = swagger.getInfo().getDescription();
        host = swagger.getHost();
        basePath = swagger.getBasePath();
        schemes = swagger.getSchemes();
        securityDefinitions = swagger.getSecurityDefinitions();
        produces = swagger.getProduces();

        for(Scheme scheme : schemes){
            if(scheme.toValue().toLowerCase().equals("https")){
                feture.add("HTTPS support");
                fetureCount.setHttpsSupport(fetureCount.getHttpsSupport()+1);
                httpsFlag = true;
                break;
            }
        }

        if(basePath == null){
            if(host.subSequence(host.length()-1,host.length()).equals("/")){
                if(httpsFlag){
                    baseUrl = "https://" + host.substring(0, host.length()-1);
                }else if(!httpsFlag){
                    baseUrl = "http://" + host.substring(0, host.length()-1);
                }
            }else{
                if(httpsFlag){
                    baseUrl = "https://" + host;
                }else if(!httpsFlag){
                    baseUrl = "http://" + host;
                }
            }
        }else{
            if(basePath.subSequence(basePath.length()-1,basePath.length()).equals("/")){
                if(httpsFlag){
                    baseUrl = "https://" + host + basePath.substring(0, basePath.length()-1);
                }else if(!httpsFlag){
                    baseUrl = "http://" + host + basePath.substring(0, basePath.length()-1);
                }
            }else{
                if(httpsFlag){
                    baseUrl = "https://" + host + basePath;
                }else if(!httpsFlag){
                    baseUrl = "http://" + host + basePath;
                }
            }
        }

        if(securityDefinitions != null){
            for(String key : securityDefinitions.keySet()){
                SecuritySchemeDefinition securitySchemeDefinition = securityDefinitions.get(key);
                if(securitySchemeDefinition.getType().equals("basic")){
                    feture.add("User authentication");
                    fetureCount.setUserAuthentication(fetureCount.getUserAuthentication()+1);
                    break;
                }else if(securitySchemeDefinition.getType().equals("apiKey")){
                    feture.add("User authentication");
                    fetureCount.setUserAuthentication(fetureCount.getUserAuthentication()+1);
                    break;
                }else if(securitySchemeDefinition.getType().equals("oauth2")){
                    feture.add("User authentication");
                    fetureCount.setUserAuthentication(fetureCount.getUserAuthentication()+1);
                    break;
                }
            }
        }

        if(produces != null){
            for(String produce : produces){
                if(produce.equals("application/json")){
                    feture.add("Output format JSON");
                    fetureCount.setOutputJson(fetureCount.getOutputJson()+1);
                    break;
                }
            }
        }

        for(String p : swagger.getPaths().keySet()){
            operationQuantity++;
        }
        if(operationQuantity <= 20){
            feture.add("At most 20 operations");
            fetureCount.setAtMostTwentyOperation(fetureCount.getAtMostTwentyOperation()+1);
        }

//        for(String str : feture){
//            log.info(str);
//        }
        endpointLevel.parseSwaggerEndpoint(swagger, baseUrl, feture, fetureCount);

    }


}
