package com.consistent.services.liferay.interf;

import java.io.IOException;

import javax.xml.stream.XMLStreamException;

import com.liferay.portal.kernel.exception.PortalException;

public interface SAX {
	public String getXML() throws PortalException, XMLStreamException, IOException;
}
