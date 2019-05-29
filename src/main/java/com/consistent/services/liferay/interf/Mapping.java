package com.consistent.services.liferay.interf;

import java.io.IOException;

import javax.xml.stream.XMLStreamException;

import com.liferay.portal.kernel.exception.PortalException;

public interface Mapping {
	public final String order = "0";
	public final String channel = "www";
	public String getMapping() throws XMLStreamException, IOException, PortalException ;
}
