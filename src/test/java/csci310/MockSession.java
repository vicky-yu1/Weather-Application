package csci310;

import java.util.Enumeration;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import java.util.HashMap;

//mock HttpSession only to be used for testing purposes
@SuppressWarnings("deprecation")
public class MockSession implements HttpSession {

	private HashMap<String, Object> attributes = new HashMap<String, Object>(); 
	
	
	public MockSession() {
		attributes.put("cities", null);
	}
	
	
	@Override
	public Object getAttribute(String name) {
			if(name!= null) {
				return attributes.get(name);
			}
			else {
				return null;
			}
		
		}
	
	@Override
	public void setAttribute(String name, Object value) {
		attributes.put(name, value);
	}

	
	//other unumplemented methods below
	//we only need to implement those 2 methods used for testing


	@Override
	public Enumeration<String> getAttributeNames() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public long getCreationTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getLastAccessedTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ServletContext getServletContext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setMaxInactiveInterval(int interval) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getMaxInactiveInterval() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public HttpSessionContext getSessionContext() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void removeAttribute(String name) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object getValue(String name) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void removeValue(String name) {
		// TODO Auto-generated method stub

	}

	@Override
	public void invalidate() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isNew() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void putValue(String name, Object value) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public String[] getValueNames() {
		// TODO Auto-generated method stub
		return null;
	}

}
