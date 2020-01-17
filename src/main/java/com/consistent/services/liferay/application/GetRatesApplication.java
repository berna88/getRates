package com.consistent.services.liferay.application;

import com.consistent.services.liferay.constants.Constants;
import com.consistent.services.liferay.interf.SAX;
import com.consistent.services.liferay.util.Util;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.stream.XMLStreamException;

import org.osgi.service.component.annotations.Component;

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
	public String getRate(
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
		if(null == siteID) {
			throw new WebApplicationException(Response.Status.NO_CONTENT);
		}else if(siteID.isEmpty()) {
			throw new WebApplicationException(Response.Status.NO_CONTENT);
		}
		if(null == brandcode) {
			throw new WebApplicationException(Response.Status.NO_CONTENT);
		}else if(brandcode.isEmpty()){
			throw new WebApplicationException(Response.Status.NO_CONTENT);
		}else{
			Constants.SITE_ID = Long.parseLong(siteID);
			// Estableciendo la marca
			Constants.CODIGODEMARCA = brandcode;
			// Estableciendo el lenguaje
			Constants.LENGUAJE = languaje;
			log.info("language select: "+Constants.LENGUAJE);
			// Codigo del hotel
			if(null == hotelcode) {
				Constants.CODIGODEHOTEL = "";
			}else {
				Constants.CODIGODEHOTEL = hotelcode;
			}
			//Estableciendo canal
			Constants.CHANNEL = channel;
			//Fechas
			Constants.CHECKINDATE = checkindate;
			Constants.CHECKOUTDATE = checkoutdate;
			String result = sax.getXML().toString();
			result = result.replace("&nbsp;", "");
			return result;
		}
		
	}

	@GET
	@Path("/getHotelRoomRatesOptimizado")
	@Produces(MediaType.APPLICATION_XML)
	public String getRatesOptimizado(
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
		
		if(null == siteID) {
			throw new WebApplicationException(Response.Status.NO_CONTENT);
		}else if(siteID.isEmpty()) {
			throw new WebApplicationException(Response.Status.NO_CONTENT);
		}
		// Estableciendo la marca
		if(null == brandcode) {
			throw new WebApplicationException(Response.Status.NO_CONTENT);
		}else if(brandcode.isEmpty()){
			throw new WebApplicationException(Response.Status.NO_CONTENT);
		}else{
			// Estableciendo el siteId del sitio
						Constants.SITE_ID = Long.parseLong(siteID);
						Constants.CODIGODEMARCA = brandcode;
						// Estableciendo el lenguaje
						Constants.LENGUAJE = languaje;
						log.info("language select: "+Constants.LENGUAJE);
						// Estrableciendo contractcodes
						Constants.validContractCodes(contractcodes);
						// Codigo del hotel
						if(null == hotelcode) {
							Constants.CODIGODEHOTEL = "";
						}else {
							Constants.CODIGODEHOTEL = hotelcode;
						}
						//Estableciendo canal
						Constants.CHANNEL = channel;
						//Fechas
						Constants.CHECKINDATE = checkindate;
						Constants.CHECKOUTDATE = checkoutdate;
						//log.info("+++++"+sax.getXML().concat("++++++++"));
						String result = sax.getXML().toString();
						result = result.replace("&nbsp;", "");
						return result;
		}
		
	}

}