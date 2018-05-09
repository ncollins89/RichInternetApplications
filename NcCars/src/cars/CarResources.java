/**
 * 
 */
package cars;

/**
 * @author Collins
 *
 */
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/cars")
@Stateful
@LocalBean

public class CarResources {

	@EJB
	private CarDAO carDao;
	
	// Search for all cars
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response findAll() {
		List<Car> cars=carDao.getAllcars();
		return Response.status(200).entity(cars).build();
	}
	
	// Search for cars by there ID's
	@GET 
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/{id}")
	public Response findCarById(@PathParam("id") int id) {
		Car car=carDao.getCar(id);
		return Response.status(200).entity(car).build();
	}
	
	// Search for cars by there make
	@GET 
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/search/{query}")
	public Response findByMake(@PathParam("query") String make) {
		List<Car> cars=carDao.getcarsByMake(make);
		return Response.status(200).entity(cars).build();
	}
	
	// Create a new entry
	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	public Response saveCar(Car car) {
		carDao.save(car);
		return Response.status(201).entity(car).build();
	}
	
	// Update a current entry
	@PUT
	@Path("/{id}")
	@Consumes("application/json")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response updateCar(Car car) {
		carDao.update(car);
		return Response.status(200).entity(car).build();
	}
	
	// Delete a current entry
	@DELETE
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response deleteCar(@PathParam("id") int id) {
		carDao.delete(id);
		return Response.status(204).build();
	}
}
