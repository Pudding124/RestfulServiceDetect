package ntou.mingjen.rest.parse;

import io.swagger.models.Scheme;
import io.swagger.models.Swagger;
import io.swagger.models.auth.SecuritySchemeDefinition;
import io.swagger.parser.SwaggerParser;
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

    @RequestMapping(value = "/swagger", method = RequestMethod.POST)
    public void parseSwaggerService(@RequestBody String SwaggerDoc){
        String title = null;
        String description = null;
        String host = null;
        String basePath = null;

        // Service Level check
        int operationQuantity = 0;
        List<Scheme> schemes = null;
        Map<String, SecuritySchemeDefinition> securityDefinitions = null;
        List<String> produces = null;

        // save restful feture
        ArrayList<String> feture = new ArrayList<String>();

        Swagger swagger = new SwaggerParser().parse(SwaggerDoc);
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
                break;
            }
        }

        if(securityDefinitions != null){
            for(String key : securityDefinitions.keySet()){
                SecuritySchemeDefinition securitySchemeDefinition = securityDefinitions.get(key);
                if(securitySchemeDefinition.getType().equals("basic")){
                    feture.add("User authentication");
                    break;
                }else if(securitySchemeDefinition.getType().equals("apiKey")){
                    feture.add("User authentication");
                    break;
                }else if(securitySchemeDefinition.getType().equals("oauth2")){
                    feture.add("User authentication");
                    break;
                }
            }
        }

        if(produces != null){
            for(String produce : produces){
                if(produce.equals("application/json")){
                    feture.add("Output format JSON");
                    break;
                }
            }
        }

        for(String p : swagger.getPaths().keySet()){
            operationQuantity++;
        }
        if(operationQuantity <= 20){
            feture.add("At most 20 operations");
        }

        for(String str : feture){
            log.info(str);
        }

    }

}
