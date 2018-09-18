package ntou.mingjen.rest.parse;

import io.swagger.models.Swagger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class EndpointLevel {
    Logger log = LoggerFactory.getLogger(EndpointLevel.class);

    public void parseSwaggerEndpoint(Swagger swagger,String baseUrl, ArrayList<String> feture){
        for (String p : swagger.getPaths().keySet()) {
            if (swagger.getPaths().get(p).getDelete() != null) {
                io.swagger.models.Operation swaggerOperation = swagger.getPaths().get(p).getDelete();
            }
            if (swagger.getPaths().get(p).getGet() != null) {
                io.swagger.models.Operation swaggerOperation = swagger.getPaths().get(p).getGet();
            }
            if (swagger.getPaths().get(p).getPatch() != null) {
                io.swagger.models.Operation swaggerOperation = swagger.getPaths().get(p).getPatch();
            }
            if (swagger.getPaths().get(p).getPost() != null) {
                io.swagger.models.Operation swaggerOperation = swagger.getPaths().get(p).getPost();
            }
            if (swagger.getPaths().get(p).getPut() != null) {
                io.swagger.models.Operation swaggerOperation = swagger.getPaths().get(p).getPut();
            }
        }
    }
}
