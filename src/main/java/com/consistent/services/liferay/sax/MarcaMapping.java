package com.consistent.services.liferay.sax;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashSet;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.consistent.services.liferay.interf.Mapping;
import com.consistent.services.liferay.sax.RateMapping;
import com.liferay.portal.kernel.exception.PortalException;

public class MarcaMapping implements Mapping{
	//private static final Log log = LogFactoryUtil.getLog(MarcaMapping.class);
		protected String guid;
		protected String code;
		protected String name;
		protected String title;
		protected String language;
		protected HashSet<RateMapping> rateMapping;
		protected HashSet<String> hotels;
		
		
		
		public MarcaMapping(String guid, String code, String name, String title, String language, HashSet<RateMapping> rateMapping) {
			super();
			this.guid = guid;
			this.code = code;
			this.name = name;
			this.title = title;
			this.language = language;
			this.rateMapping = rateMapping;
		}
		public MarcaMapping(String guid, String code, String name, String title, String language, HashSet<RateMapping> rateMapping, HashSet<String> hotels) {
			super();
			this.guid = guid;
			this.code = code;
			this.name = name;
			this.title = title;
			this.language = language;
			this.rateMapping = rateMapping;
			this.hotels = hotels;
		}
		public MarcaMapping() {
			super();
			this.guid = "";
			this.code = "";
			this.name = "";
			this.title = "";
			this.language = "";
			this.rateMapping = new HashSet<RateMapping>();
		}


		public String getGuid() {
			return guid;
		}




		public void setGuid(String guid) {
			this.guid = guid;
		}




		public String getCode() {
			return code;
		}




		public void setCode(String code) {
			this.code = code;
		}




		public String getName() {
			return name;
		}




		public void setName(String name) {
			this.name = name;
		}




		public String getTitle() {
			return title;
		}




		public void setTitle(String title) {
			this.title = title;
		}




		public String getLanguage() {
			return language;
		}




		public void setLanguage(String language) {
			this.language = language;
		}




		public HashSet<RateMapping> getRateMapping() {
			return rateMapping;
		}




		public void setRateMapping(HashSet<RateMapping> rateMapping) {
			this.rateMapping = rateMapping;
		}




		@Override
		public String getMapping() throws XMLStreamException, IOException, PortalException {
			StringWriter stringWriter = new StringWriter();
			XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();
			XMLStreamWriter xMLStreamWriter = xmlOutputFactory.createXMLStreamWriter(stringWriter);
			xMLStreamWriter.writeStartDocument();
				xMLStreamWriter.writeStartElement("contents");
					xMLStreamWriter.writeStartElement("content");
						xMLStreamWriter.writeStartElement("brand");
							xMLStreamWriter.writeStartElement("guid");
								xMLStreamWriter.writeCharacters(guid);
							xMLStreamWriter.writeEndElement();
							xMLStreamWriter.writeStartElement("code");
								xMLStreamWriter.writeCharacters(code);
							xMLStreamWriter.writeEndElement();
							xMLStreamWriter.writeStartElement("name");
								xMLStreamWriter.writeCharacters(name);
							xMLStreamWriter.writeEndElement();
							xMLStreamWriter.writeStartElement("title");
								xMLStreamWriter.writeCharacters(title);
							xMLStreamWriter.writeEndElement();
							xMLStreamWriter.writeStartElement("language");
								xMLStreamWriter.writeCharacters(language);
							xMLStreamWriter.writeEndElement();
							xMLStreamWriter.writeStartElement("keyword");
								xMLStreamWriter.writeCharacters("");
							xMLStreamWriter.writeEndElement();
							xMLStreamWriter.writeStartElement("order");
								xMLStreamWriter.writeCharacters(order);
							xMLStreamWriter.writeEndElement();
							xMLStreamWriter.writeStartElement("channel");
								xMLStreamWriter.writeCharacters(channel);
							xMLStreamWriter.writeEndElement();
							xMLStreamWriter.writeStartElement("rates");
					
								for (RateMapping rateMappingR : rateMapping) {
									xMLStreamWriter.writeDTD(rateMappingR.getMapping());
								}
							xMLStreamWriter.writeEndElement();//rates
							for (String hotel : hotels) {
								xMLStreamWriter.writeCharacters(hotel);
							}
							
						xMLStreamWriter.writeEndElement();//brand
					xMLStreamWriter.writeEndElement();//content
				xMLStreamWriter.writeEndElement();//contents
			xMLStreamWriter.writeEndDocument();
			xMLStreamWriter.flush();
			xMLStreamWriter.close();
			String result = stringWriter.getBuffer().toString();
			result = result.replace("&lt;", "<").replace("&gt;", ">");
			stringWriter.close(); 
			//log.info(result);
			return result;
		}
}
