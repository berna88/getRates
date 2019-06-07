package com.consistent.services.liferay.segurity;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import com.consistent.services.liferay.interf.Autentification;
import com.liferay.portal.kernel.exception.NoSuchUserException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.PasswordTrackerLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.PortalUtil;

public class AutentificationImp implements Autentification {
	
	private static final Log log = LogFactoryUtil.getLog(AutentificationImp.class);
	
	private @Context HttpServletRequest request;
	private @Context HttpHeaders headers;
	private String token;
	private User user;
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public AutentificationImp(@Context HttpServletRequest request, @Context HttpHeaders headers){
		this.request = request;
		this.headers = headers;
	}
	@Override
	public boolean isAutentificationBasic() throws UnsupportedEncodingException, PortalException {
		try {
			token = Optional.ofNullable(headers.getRequestHeader(HttpHeaders.AUTHORIZATION)).orElse(Collections.<String> emptyList()).stream().collect(Collectors.joining(": "));
			//Validando token
			isValidToken(token);
			String[] tokens = (new String(Base64.getDecoder().decode(token.split(" ")[1]), "UTF-8")).split(":");
			//validando tokens
			isValidTokens(tokens);
			//Obteniendo datos del usuario
			user = UserLocalServiceUtil.getUserByEmailAddress(PortalUtil.getCompanyId(request), tokens[0]);
			//Validando la contraseña
			boolean validUser = PasswordTrackerLocalServiceUtil.isSameAsCurrentPassword(user.getUserId(), tokens[1]);
			return validUser;
		} catch (NoSuchUserException e) {
			// TODO: handle exception
			log.error("las credenciales del usuario no son correctas");
			return false;
		}catch (ArrayIndexOutOfBoundsException e) {
			// TODO: handle exception
			log.error("Autentificacion no soportada");
			return false;
		}
		
		
	}
	/**
	 * @author bernardohernandez
	 * @param token
	 * @return Devuelve un verdadero false si el token viene nulo
	 */
	private boolean isValidToken(String token){
		
		if(token == null || !token.startsWith("Basic")) {
			Response.status(Response.Status.UNAUTHORIZED).entity("La autenticación no es soportada").build();
				//log.error(Response.status(Response.Status.UNAUTHORIZED).entity("La autenticación no es soportada").build());			
			return false;
		}
		return true;
	}
	/**
	 * @author bernardohernandez
	 * @param tokens
	 * @return Devuelve un false si la cadena viene null
	 */
	private boolean isValidTokens(String[] tokens){
		if(tokens == null) {
			log.error(Response.status(Response.Status.UNAUTHORIZED).entity("El mecanismo de autenticación ha fallado").build());
			return false;
		}
		return true;
	}
}
