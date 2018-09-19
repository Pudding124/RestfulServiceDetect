package ntou.mingjen.rest.parse;

import io.swagger.models.Swagger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class EndpointLevel {
    Logger log = LoggerFactory.getLogger(EndpointLevel.class);

    public void parseSwaggerEndpoint(Swagger swagger, String baseUrl, ArrayList<String> feture){
        for (String p : swagger.getPaths().keySet()) {
            if (swagger.getPaths().get(p).getDelete() != null) {
                boolean checkRestful = p.contains("{");
                String endpoint = baseUrl+p;
                io.swagger.models.Operation swaggerOperation = swagger.getPaths().get(p).getDelete();
                if(checkRestful){
                    feture.add("REST-style URls");
                }
            }
            if (swagger.getPaths().get(p).getGet() != null) {
                boolean checkRestful = p.contains("{");
                String endpoint = baseUrl+p;
                io.swagger.models.Operation swaggerOperation = swagger.getPaths().get(p).getGet();
                if(checkRestful){
                    feture.add("REST-style URls");
                }
            }
            if (swagger.getPaths().get(p).getPatch() != null) {
                boolean checkRestful = p.contains("{");
                String endpoint = baseUrl+p;
                io.swagger.models.Operation swaggerOperation = swagger.getPaths().get(p).getPatch();
                if(checkRestful){
                    feture.add("REST-style URls");
                }
            }
            if (swagger.getPaths().get(p).getPost() != null) {
                boolean checkRestful = p.contains("{");
                String endpoint = baseUrl+p;
                io.swagger.models.Operation swaggerOperation = swagger.getPaths().get(p).getPost();
                if(checkRestful){
                    feture.add("REST-style URls");
                }
            }
            if (swagger.getPaths().get(p).getPut() != null) {
                boolean checkRestful = p.contains("{");
                String endpoint = baseUrl+p;
                io.swagger.models.Operation swaggerOperation = swagger.getPaths().get(p).getPut();
                if(checkRestful){
                    feture.add("REST-style URls");
                }
            }
        }
    }

    public ArrayList<String> checkStatusCodeAndErrorMessages(io.swagger.models.Operation swaggerOperation, ArrayList<String> feture){
        for(String key : swaggerOperation.getResponses().keySet()){
            io.swagger.models.Response response = swaggerOperation.getResponses().get(key);
        }
        return null;
    }
}
