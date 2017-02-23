package com.learning.securedapp.web.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>WebAppUtils class.</p>
 *
 * @author rajakolli
 * @version $Id: $Id
 */
public class WebAppUtils {
	
	private WebAppUtils(){
		super();
	}

	/**
	 * <p>getURLWithContextPath.</p>
	 *
	 * @param request a {@link javax.servlet.http.HttpServletRequest} object.
	 * @return a {@link java.lang.String} object.
	 */
	public static String getURLWithContextPath(HttpServletRequest request) {
		return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath();
	}

}
