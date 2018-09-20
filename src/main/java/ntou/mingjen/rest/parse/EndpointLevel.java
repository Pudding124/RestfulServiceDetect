package ntou.mingjen.rest.parse;

import io.swagger.models.Swagger;
import ntou.mingjen.rest.bean.FetureCount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class EndpointLevel {
    Logger log = LoggerFactory.getLogger(EndpointLevel.class);

    public void parseSwaggerEndpoint(Swagger swagger, String baseUrl, ArrayList<String> feture, FetureCount fetureCount){
        for (String p : swagger.getPaths().keySet()) {
            if (swagger.getPaths().get(p).getDelete() != null) {
                fetureCount.setEndpointNumber(fetureCount.getEndpointNumber()+1);
                boolean checkRestful = p.contains("{");
                String endpoint = baseUrl+p;
                io.swagger.models.Operation swaggerOperation = swagger.getPaths().get(p).getDelete();

                if(checkRestful){
                    feture.add("REST-style URls");
                    fetureCount.setRestStyleUrls(fetureCount.getRestStyleUrls()+1);
                }
                feture = checkStatusCodeAndErrorMessages(swaggerOperation, feture, fetureCount);
                feture = checkInputFormatAndOutputFormat(swaggerOperation, feture, fetureCount);
            }
            if (swagger.getPaths().get(p).getGet() != null) {
                fetureCount.setEndpointNumber(fetureCount.getEndpointNumber()+1);
                boolean checkRestful = p.contains("{");
                String endpoint = baseUrl+p;
                io.swagger.models.Operation swaggerOperation = swagger.getPaths().get(p).getGet();
                if(checkRestful){
                    feture.add("REST-style URls");
                    fetureCount.setRestStyleUrls(fetureCount.getRestStyleUrls()+1);
                }
                feture = checkStatusCodeAndErrorMessages(swaggerOperation, feture, fetureCount);
                feture = checkInputFormatAndOutputFormat(swaggerOperation, feture, fetureCount);
            }
            if (swagger.getPaths().get(p).getPatch() != null) {
                fetureCount.setEndpointNumber(fetureCount.getEndpointNumber()+1);
                boolean checkRestful = p.contains("{");
                String endpoint = baseUrl+p;
                io.swagger.models.Operation swaggerOperation = swagger.getPaths().get(p).getPatch();
                if(checkRestful){
                    feture.add("REST-style URls");
                    fetureCount.setRestStyleUrls(fetureCount.getRestStyleUrls()+1);
                }
                feture = checkStatusCodeAndErrorMessages(swaggerOperation, feture, fetureCount);
                feture = checkInputFormatAndOutputFormat(swaggerOperation, feture, fetureCount);
            }
            if (swagger.getPaths().get(p).getPost() != null) {
                fetureCount.setEndpointNumber(fetureCount.getEndpointNumber()+1);
                boolean checkRestful = p.contains("{");
                String endpoint = baseUrl+p;
                io.swagger.models.Operation swaggerOperation = swagger.getPaths().get(p).getPost();
                if(checkRestful){
                    feture.add("REST-style URls");
                    fetureCount.setRestStyleUrls(fetureCount.getRestStyleUrls()+1);
                }
                feture = checkStatusCodeAndErrorMessages(swaggerOperation, feture, fetureCount);
                feture = checkInputFormatAndOutputFormat(swaggerOperation, feture, fetureCount);
            }
            if (swagger.getPaths().get(p).getPut() != null) {
                fetureCount.setEndpointNumber(fetureCount.getEndpointNumber()+1);
                boolean checkRestful = p.contains("{");
                String endpoint = baseUrl+p;
                io.swagger.models.Operation swaggerOperation = swagger.getPaths().get(p).getPut();
                if(checkRestful){
                    feture.add("REST-style URls");
                    fetureCount.setRestStyleUrls(fetureCount.getRestStyleUrls()+1);
                }
                feture = checkStatusCodeAndErrorMessages(swaggerOperation, feture, fetureCount);
                feture = checkInputFormatAndOutputFormat(swaggerOperation, feture, fetureCount);
            }
        }
    }

    public ArrayList<String> checkStatusCodeAndErrorMessages(io.swagger.models.Operation swaggerOperation, ArrayList<String> feture, FetureCount fetureCount){
        if(swaggerOperation.getResponses() != null){
            feture.add("HTTP status code use");
            fetureCount.setHttpStatusCodeUse(fetureCount.getHttpStatusCodeUse()+1);
            for(String key : swaggerOperation.getResponses().keySet()){
                if(key.toLowerCase().equals("default")){
                    feture.add("Explain Error messages");
                    fetureCount.setExplainErrorMessages(fetureCount.getExplainErrorMessages()+1);
                    break;
                }
                int statusCode = Integer.valueOf(key); //小心接收到 default
                if(statusCode >= 300){
                    feture.add("Explain Error messages");
                    fetureCount.setExplainErrorMessages(fetureCount.getExplainErrorMessages()+1);
                    log.info("status code :{}", key);
                    break;
                }
            }
        }
        return feture;
    }

    public ArrayList<String> checkInputFormatAndOutputFormat(io.swagger.models.Operation swaggerOperation, ArrayList<String> feture, FetureCount fetureCount){
        if(swaggerOperation.getConsumes() != null){
            if(swaggerOperation.getConsumes().contains("application/json")){
                fetureCount.setInputJson(fetureCount.getInputJson()+1);
                if(!feture.contains("Input format JSON")){
                    feture.add("Input format JSON");
                }
            }
        }else if(feture.contains("Input format JSON")){
            fetureCount.setInputJson(fetureCount.getInputJson()+1);
        }

        if(swaggerOperation.getProduces() != null){
            if(swaggerOperation.getProduces().contains("application/json")){
                fetureCount.setOutputJson(fetureCount.getOutputJson()+1);
                if(!feture.contains("Output format JSON")){
                    feture.add("Output format JSON");
                }
            }
        }else if(feture.contains("Output format JSON")){
            fetureCount.setOutputJson(fetureCount.getOutputJson()+1);
        }

        return  feture;
    }
}
