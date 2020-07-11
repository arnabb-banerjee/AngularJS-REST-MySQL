package tracogo.word;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.sun.jersey.multipart.FormDataParam;

import entity.InputInfo;

@Path("word")
public class MyResource {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
    public List<InputInfo> getList()
    {
		return InputInfo.getList("");
 	}    
	
	@GET
	@Path("/{keyword}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<InputInfo> getList(@PathParam("keyword") String keyword)
    {
		return InputInfo.getList(keyword);
 	}    
	
	@POST
    @Path("/save")
	@Produces({MediaType.TEXT_PLAIN})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public String save(
    		@FormDataParam("jsondata") @DefaultValue("") String jsondata)
    {
    	try {
    		if(InputInfo.save(0, new Gson().fromJson(jsondata, InputInfo.class).getWord()))
    		{
    			return "Done";
    		}
    		
        	return "No";
    	}
    	catch (Exception e) {
			return e.toString();
		}
    }
	
	@POST
    @Path("/del")
	@Produces({MediaType.TEXT_PLAIN})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public String delete(
    		@FormDataParam("jsondata") @DefaultValue("") String jsondata)
    {
    	try {
    		if(InputInfo.delete(new Gson().fromJson(jsondata, InputInfo.class)))
    		{
    			return "Done";
    		}
    		
        	return "No";
    	}
    	catch (Exception e) {
			return e.toString();
		}
    }
}
