package co.com.neomedios.OCRApi;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;



/**
 * Root resource (exposed at "text" path)
 */
@Path("electores")
public class OcrResource {
	
	private OcrService ocrs = new OcrService();
    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     * @throws IOException 
     */
    @GET
    @Path("/{cedula}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIt(@PathParam("cedula") String cedula) throws IOException {
    	
        return Response.ok(ocrs.getOcrText(cedula), MediaType.APPLICATION_JSON).build();
    }
}
