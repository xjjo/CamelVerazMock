package veraz_cfx_service_mock.org.util;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.TypeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class GetPropertyJDBCHelper {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	private final String SELECT_PROPERTY = "SELECT pptvalue.pptValue FROM pptvalue INNER JOIN property on pptvalue.ppt_id = property.ppt_id WHERE property.pptName = ? and pptvalue.state = 1";
	
	public String perform(String propertyName){
		
		String value = jdbcTemplate.queryForObject(SELECT_PROPERTY, String.class, propertyName);
		
		
		return value;
		
		
	}
	
}
