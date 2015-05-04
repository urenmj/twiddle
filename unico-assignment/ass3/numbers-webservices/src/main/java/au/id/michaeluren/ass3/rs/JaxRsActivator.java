
package au.id.michaeluren.ass3.rs;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * A class extending {@link Application} and annotated with @ApplicationPath is the Java EE 6 "no XML" approach to activating
 * JAX-RS.
 * 
 * <p>
 * Resources are served relative to the servlet path specified in the {@link ApplicationPath} annotation.
 * </p>
 */
@ApplicationPath("/rest")
public class JaxRsActivator extends Application {
	
	private Set<Class<?>> classes = new HashSet<Class<?>>();
	
	public JaxRsActivator() {
//		classes.add(ParamExceptionMapper.class); // doesn't seem to work - trying to override auto type conversion to int
//		classes.add(SystemExceptionMapper.class);
	}
	
	@Override
	public Set<Class<?>> getClasses() {
		return classes;
	}
}
