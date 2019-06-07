package com.consistent.services.liferay.interf;

import java.io.UnsupportedEncodingException;

import com.liferay.portal.kernel.exception.PortalException;

public interface Autentification {
	/**
	 * @author Fernando Martinez
	 * @return Devuelve un true, si el usuario ingreso correctamente sus credenciales en Basic Authentification
	 * @throws UnsupportedEncodingException
	 * @throws PortalException
	 */
	boolean isAutentificationBasic() throws UnsupportedEncodingException, PortalException;
	
}
