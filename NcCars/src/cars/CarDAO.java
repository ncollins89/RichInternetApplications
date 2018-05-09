/**
 * 
 */
package cars;

/**
 * @author Collins
 *
 */

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Stateless
@LocalBean
public class CarDAO {

	@PersistenceContext
	private EntityManager em;
	
    protected Car processRow(ResultSet rs) throws SQLException {
		 Car car = new Car();
	     car.setId(rs.getInt("id"));
	     car.setMake(rs.getString("make"));
	     car.setModel(rs.getString("model"));
	     car.setYear(rs.getString("year"));
	     car.setCountry(rs.getString("country"));
	     car.setDescription(rs.getString("description"));
	     car.setPicture(rs.getString("picture"));
	     return car;
	}
	
	// Search for all cars  
    public List<Car> getAllcars() {
    	List<Car> list = new ArrayList<Car>();
        Connection c = null;
    	String sql = "Select * From cars";
        try {
            c = ConnectionHelper.getConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                list.add(processRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
		return null;
	}
	
	// Search for cars by there ID's
	public Car getCar(int id) {
		String sql = "Select * From cars Where id = ?";
        Car car = null;
        Connection c = null;
        try {
            c = ConnectionHelper.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                car = processRow(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
		return null;
	}
	
	// Search for cars by there make
	public List<Car> getcarsByMake(String make) {
		List<Car> list = new ArrayList<Car>();
        Connection c = null;
    	String sql = "Select * From cars as e " +
			"WHERE UPPER(make) LIKE ? " +	
			"ORDER BY make";
        try {
            c = ConnectionHelper.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, "%" + make.toUpperCase() + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(processRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
		return null;
	}
	
	// Create a new entry 
	public void save(Car car) {
		Connection c = null;
        PreparedStatement ps = null;
        try {
            c = ConnectionHelper.getConnection();
            ps = c.prepareStatement("INSERT INTO cars (make, model, year, country, description, picture) VALUES (?, ?, ?, ?, ?, ?)",
            new String[] { "ID" });
            ps.setString(1, car.getMake());
            ps.setString(2, car.getModel());
            ps.setString(3, car.getYear());
            ps.setString(4, car.getCountry());
            ps.setString(5, car.getDescription());
            ps.setString(6, car.getPicture());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            
            int id = 0;
            id = rs.getInt(1);
            car.setId(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}	
	}

	// Update a current entry
    public Car update(Car car) {
        Connection c = null;
        try {
            c = ConnectionHelper.getConnection();
            PreparedStatement ps = c.prepareStatement("UPDATE cars SET make=?, model=?, year=?, country=?, description=?, picture=? WHERE id=?");
            ps.setString(1, car.getMake());
            ps.setString(2, car.getModel());
            ps.setString(3, car.getYear());
            ps.setString(4, car.getCountry());
            ps.setString(5, car.getDescription());
            ps.setString(6, car.getPicture());
            ps.setInt(7, car.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
        return car;
    }
	
	 // Delete a current entry
	public boolean delete(int id) {
		Connection c = null;
        try {
            c = ConnectionHelper.getConnection();
            PreparedStatement ps = c.prepareStatement("DELETE FROM cars WHERE id=?");
            ps.setInt(1, id);
            int count = ps.executeUpdate();
            return count == 1;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}		
	}
}
