package com.consistent.services.liferay.application;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.stream.XMLStreamException;

import org.osgi.service.component.annotations.Component;

import com.consistent.services.liferay.constants.Constants;
import com.consistent.services.liferay.interf.Autentification;
import com.consistent.services.liferay.interf.SAX;
import com.consistent.services.liferay.segurity.AutentificationImp;
import com.consistent.services.liferay.util.Util;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

/**
 * @author liferay
 */
@ApplicationPath("/xml-rateHotelRoom")
@Component(immediate = true, service = Application.class)
public class GetRatesApplication extends Application {
	
	private static final Log log = LogFactoryUtil.getLog(GetRatesApplication.class);
    
	final SAX sax = new Util();
	
	public Set<Object> getSingletons() {
		return Collections.<Object>singleton(this);
	}
	
	@GET
	@Path("/getHotelRoomRates")
	@Produces(MediaType.APPLICATION_XML)
	public Response getRate(
			@Context HttpServletRequest request, 
			@Context HttpHeaders headers,
			@QueryParam("siteID") String siteID,
			@QueryParam("brandcode") String brandcode,
			@QueryParam("language") String languaje,
			@QueryParam("channel") String channel,
			@QueryParam("bookingdate") String bookingdate,
			@QueryParam("hotelcode") String hotelcode,
			@QueryParam("checkindate") String checkindate,
			@QueryParam("checkoutdate") String checkoutdate) throws PortalException, IOException, XMLStreamException {
		log.info("<-------- Metodo getRateList Normal getHotelRoomRates--------->");
		// Estableciendo el siteId del sitio
		Constants.SITE_ID = Long.parseLong(siteID);
		// Estableciendo la marca
		Constants.CODIGODEMARCA = brandcode;
		// Estableciendo el lenguaje
		Constants.LENGUAJE = languaje;
		log.info("language select: "+Constants.LENGUAJE);
		// Codigo del hotel
		Constants.CODIGODEHOTEL = hotelcode;
		//Estableciendo canal
		Constants.CHANNEL = channel;
		//Fechas
		Constants.CHECKINDATE = checkindate;
		Constants.CHECKOUTDATE = checkoutdate;
		
		final Autentification autentification = new AutentificationImp(request, headers);
		if(autentification.isAutentificationBasic()){
			return Response.status(Response.Status.OK).entity(sax.getXML()).build();
		}else{
			return Response.status(Response.Status.UNAUTHORIZED).entity("La autenticación no es soportada").build();
		}
	}

	@GET
	@Path("/getHotelRoomRatesOptimizado")
	@Produces(MediaType.APPLICATION_XML)
	public Response getRatesOptimizado(
			@Context HttpServletRequest request, 
			@Context HttpHeaders headers,
			@QueryParam("siteID") String siteID,
			@QueryParam("brandcode") String brandcode,
			@QueryParam("language") String languaje,
			@QueryParam("channel") String channel,
			@QueryParam("bookingdate") String bookingdate,
			@QueryParam("hotelcode") String hotelcode,
			@QueryParam("checkindate") String checkindate,
			@QueryParam("checkoutdate") String checkoutdate,
			@QueryParam("contractcodes") String contractcodes) throws PortalException, IOException, XMLStreamException {
		log.info("<-------- Metodo getRateList Optimizado --------->");	
		// Estableciendo el siteId del sitio
		Constants.SITE_ID = Long.parseLong(siteID);
		// Estableciendo la marca
		Constants.CODIGODEMARCA = brandcode;
		// Estableciendo el lenguaje
		Constants.LENGUAJE = languaje;
		log.info("language select: "+Constants.LENGUAJE);
		// Estrableciendo contractcodes
		Constants.validContractCodes(contractcodes);
		// Codigo del hotel
		Constants.CODIGODEHOTEL = hotelcode;
		//Estableciendo canal
		Constants.CHANNEL = channel;
		//Fechas
		Constants.CHECKINDATE = checkindate;
		Constants.CHECKOUTDATE = checkoutdate;
		
		
		final Autentification autentification = new AutentificationImp(request, headers);
		if(autentification.isAutentificationBasic()){
			log.info("<-------- Proceso Porcesando XML --------->");
			return Response.status(Response.Status.OK).entity(sax.getXML()).build();
		}else{
			return Response.status(Response.Status.UNAUTHORIZED).entity("La autenticación no es soportada").build();
		}
	}

}