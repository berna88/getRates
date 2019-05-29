package com.consistent.services.liferay.util;

import java.io.IOException;
import java.util.HashSet;

import javax.xml.stream.XMLStreamException;

import com.consistent.services.liferay.interf.SAX;
import com.consistent.services.liferay.constants.Constants;
import com.consistent.services.liferay.interf.Mapping;
import com.consistent.services.liferay.sax.HotelMapping;
import com.consistent.services.liferay.sax.MarcaMapping;
import com.consistent.services.liferay.sax.RateMapping;
import com.liferay.portal.kernel.exception.PortalException;

public class Util extends RateMapping implements SAX {
	
	public String getXML() throws PortalException, XMLStreamException, IOException {
		// Se iteran los rates para la inserción en marca
				final HashSet<RateMapping> rates = getArticlesByCodeBrand();
				final HotelMapping hotels = new HotelMapping();
				final Mapping mapping = new MarcaMapping("", Constants.CODIGODEMARCA, Constants.getNameBrand(Constants.CODIGODEMARCA), "", Constants.LENGUAJE, rates, hotels.getHotels());
				return mapping.getMapping();
	}	  
}
