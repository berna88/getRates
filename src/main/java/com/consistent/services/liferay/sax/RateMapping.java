package com.consistent.services.liferay.sax;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashSet;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import com.consistent.services.liferay.portal.Portal;
import com.consistent.services.liferay.constants.Constants;
import com.consistent.services.liferay.interf.Mapping;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

public class RateMapping extends Portal implements Mapping {
	
private static final Log log = LogFactoryUtil.getLog(RateMapping.class);
	
	private String guid;
	private String code;
	private String name;
	private String title;
	private String language;
	private String keyword;
	private String shortDescription;
	private String description;
	private String order = Mapping.order;
	private String channel = Mapping.channel;
	private String benefits;
	private String restrictions;
	private String enddate;
	private String currency;
	
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
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getShortDescription() {
		return shortDescription;
	}
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getBenefits() {
		return benefits;
	}
	public void setBenefits(String benefits) {
		this.benefits = benefits;
	}
	public String getRestrictions() {
		return restrictions;
	}
	public void setRestrictions(String restrictions) {
		this.restrictions = restrictions;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	//Constructor vacio
	public RateMapping(){
		this.guid = "";
		this.code = "";
		this.name = "";
		this.title = "";
		this.language = "";
		this.keyword = "";
		this.shortDescription = "";
		this.description = "";
		this.benefits = "";
		this.restrictions = "";
		this.enddate = "";
		this.currency = "";
	}
	//Constructor con parametros
	public RateMapping(String guid, String code, String name, String title, String language, String keyword,
			String shortDescription, String description, String benefits,
			String restrictions, String enddate, String currency) {
		super();
		this.guid = guid;
		this.code = code;
		this.name = name;
		this.title = title;
		this.language = language;
		this.keyword = keyword;
		this.shortDescription = shortDescription;
		this.description = description;
		this.benefits = benefits;
		this.restrictions = restrictions;
		this.enddate = enddate;
		this.currency = currency;
	}
	
	@Override
	public String getMapping() throws XMLStreamException, IOException{
		StringWriter stringWriter = new StringWriter();
		XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();
		XMLStreamWriter xMLStreamWriter = xmlOutputFactory.createXMLStreamWriter(stringWriter);
		
			xMLStreamWriter.writeStartElement("rate");
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
					xMLStreamWriter.writeCharacters(keyword);
				xMLStreamWriter.writeEndElement();
				xMLStreamWriter.writeStartElement("shortDescription");
					xMLStreamWriter.writeDTD(shortDescription);
				xMLStreamWriter.writeEndElement();
				xMLStreamWriter.writeStartElement("description");
					xMLStreamWriter.writeDTD(description);
				xMLStreamWriter.writeEndElement();
				xMLStreamWriter.writeStartElement("order");
					xMLStreamWriter.writeCharacters(order);
				xMLStreamWriter.writeEndElement();
				xMLStreamWriter.writeStartElement("channel");
					xMLStreamWriter.writeCharacters(channel);
				xMLStreamWriter.writeEndElement();
				xMLStreamWriter.writeStartElement("benefits");
					xMLStreamWriter.writeDTD(benefits);
				xMLStreamWriter.writeEndElement();
				xMLStreamWriter.writeStartElement("restrictions");
					xMLStreamWriter.writeDTD(restrictions);
				xMLStreamWriter.writeEndElement();
				xMLStreamWriter.writeStartElement("enddate");
					xMLStreamWriter.writeCharacters(enddate);
				xMLStreamWriter.writeEndElement();
				xMLStreamWriter.writeStartElement("currency");
					xMLStreamWriter.writeCharacters(currency);
				xMLStreamWriter.writeEndElement();
				xMLStreamWriter.writeStartElement("mediaLinks");
					xMLStreamWriter.writeStartElement("mediaLink");
						xMLStreamWriter.writeStartElement("type");
							xMLStreamWriter.writeCharacters("0");
						xMLStreamWriter.writeEndElement();
						xMLStreamWriter.writeStartElement("keyword");
							xMLStreamWriter.writeCharacters("0");
						xMLStreamWriter.writeEndElement();
						xMLStreamWriter.writeStartElement("multimedia");
							xMLStreamWriter.writeStartElement("url");
								xMLStreamWriter.writeCharacters("0");
							xMLStreamWriter.writeEndElement();
						xMLStreamWriter.writeEndElement();
					xMLStreamWriter.writeEndElement();
				xMLStreamWriter.writeEndElement();
			xMLStreamWriter.writeEndElement();
		xMLStreamWriter.flush();
		xMLStreamWriter.close();
		String result = stringWriter.getBuffer().toString();
		stringWriter.close(); 
		return result;
	}
	
	//Metodo que contiene todos los elementos en Rate
	private HashSet<RateMapping> getWebContentRate(String locale) throws PortalException{
		log.info("<---------- Metodo getWebContentRate Sin contrato---------->");
		final AssetEntryQuery assetEntryQuery = new AssetEntryQuery();
		//Coleccion donde se van a guardar lista de rates
		final HashSet<RateMapping> rates = new HashSet<RateMapping>();
		Long categoryId = getCategory(Constants.CODIGODEMARCA);
		assetEntryQuery.setClassTypeIds(new long[]{Constants.STRUCTURE_RATE_ID} );
		assetEntryQuery.setGroupIds(new long[]{Constants.SITE_ID} );
		assetEntryQuery.setAnyCategoryIds(new long[] { categoryId });
		assetEntryQuery.setClassName("com.liferay.journal.model.JournalArticle");
		//convirtiendo la lista en hashSet
		final HashSet<AssetEntry> assetEntryList = new HashSet<AssetEntry>(AssetEntryLocalServiceUtil.getEntries(assetEntryQuery));
		log.info("Tamaño de elemento por categorias: "+assetEntryList.size());
		RateMapping mapping = new RateMapping();
		try {
			for (AssetEntry ae : assetEntryList) {
				// JournalArticleResource journalArticleResource = JournalArticleResourceLocalServiceUtil.getJournalArticleResource(ae.getClassPK());
			    // JournalArticle journalArticle = JournalArticleLocalServiceUtil.getLatestArticle(journalArticleResource.getResourcePrimKey());
			    final JournalArticle journalArticle = JournalArticleLocalServiceUtil.getLatestArticle(ae.getClassPK());
								
								Document document = null;
								
								document = SAXReaderUtil.read(journalArticle.getContentByLocale(locale));
									
									if(!Constants.CHECKINDATE.isEmpty()){
										if(getIntervals(Constants.CHECKINDATE, Constants.CHECKOUTDATE, document.valueOf("//dynamic-element[@name='finalDateBooking']/dynamic-content/text()"))){
											mapping = new RateMapping(); 
											mapping.code = document.valueOf("//dynamic-element[@name='codeRate']/dynamic-content/text()");
											mapping.name = document.valueOf("//dynamic-element[@name='nameRate']/dynamic-content/text()");
											mapping.title = document.valueOf("//dynamic-element[@name='codeRate']/dynamic-content/text()").concat("-").concat(document.valueOf("//dynamic-element[@name='keywordRate']/dynamic-content/text()"));
											mapping.keyword = document.valueOf("//dynamic-element[@name='keywordRate']/dynamic-content/text()");
											mapping.description = document.valueOf("//dynamic-element[@name='descriptionLongRate']/dynamic-content/text()");
											mapping.shortDescription = document.valueOf("//dynamic-element[@name='shortDescriptionRate']/dynamic-content/text()");
											mapping.benefits = document.valueOf("//dynamic-element[@name='benefitsRate']/dynamic-content/text()");
											mapping.restrictions = document.valueOf("//dynamic-element[@name='Restrictions1']/dynamic-content/text()");
											mapping.currency = document.valueOf("//dynamic-element[@name='currencyRate']/dynamic-content/text()");
											mapping.enddate = document.valueOf("//dynamic-element[@name='finalDateBooking']/dynamic-content/text()");
											mapping.guid = journalArticle.getArticleId();
											mapping.language = Constants.LENGUAJE;
										}
									}else{
										mapping = new RateMapping(); 
										mapping.code = document.valueOf("//dynamic-element[@name='codeRate']/dynamic-content/text()");
										mapping.name = document.valueOf("//dynamic-element[@name='nameRate']/dynamic-content/text()");
										mapping.title = document.valueOf("//dynamic-element[@name='codeRate']/dynamic-content/text()").concat("-").concat(document.valueOf("//dynamic-element[@name='keywordRate']/dynamic-content/text()"));
										mapping.keyword = document.valueOf("//dynamic-element[@name='keywordRate']/dynamic-content/text()");
										mapping.description = document.valueOf("//dynamic-element[@name='descriptionLongRate']/dynamic-content/text()");
										mapping.shortDescription = document.valueOf("//dynamic-element[@name='shortDescriptionRate']/dynamic-content/text()");
										mapping.benefits = document.valueOf("//dynamic-element[@name='benefitsRate']/dynamic-content/text()");
										mapping.restrictions = document.valueOf("//dynamic-element[@name='Restrictions1']/dynamic-content/text()");
										mapping.currency = document.valueOf("//dynamic-element[@name='currencyRate']/dynamic-content/text()");
										mapping.enddate = document.valueOf("//dynamic-element[@name='finalDateBooking']/dynamic-content/text()");
										mapping.guid = journalArticle.getArticleId();
										mapping.language = Constants.LENGUAJE;
									}
									rates.add(mapping);
			}
		} catch (Exception e) {
			log.error("module getWebContentRate: "+e);
		}
		return rates;
		
	}
	
	// Metodo que obtiene los rate por filtro
	private HashSet<RateMapping> getWebContentRateFilter(String[] codesSplit,String locale) throws PortalException{
		log.info("<---------- Metodo getWebContentRate Con filtros ---------->");
		AssetEntryQuery assetEntryQuery = new AssetEntryQuery();
		final HashSet<RateMapping> rates = new HashSet<>();
		Long categoryId = getCategory(Constants.CODIGODEMARCA);
		assetEntryQuery.setAnyCategoryIds(new long[] { categoryId });
		assetEntryQuery.setClassTypeIds(new long[]{Constants.STRUCTURE_RATE_ID} );
		assetEntryQuery.setGroupIds(new long[]{Constants.SITE_ID} );
		assetEntryQuery.setClassName("com.liferay.journal.model.JournalArticle");
		HashSet<AssetEntry> assetEntryList = new HashSet<AssetEntry> (AssetEntryLocalServiceUtil.getEntries(assetEntryQuery));
		log.info("Tamaño de elemento filtrados: "+assetEntryList.size());
		RateMapping mapping;
		try {
			for (AssetEntry ae : assetEntryList) {
			    //JournalArticleResource journalArticleResource = JournalArticleResourceLocalServiceUtil.getJournalArticleResource(ae.getClassPK());
			    //JournalArticle journalArticle = JournalArticleLocalServiceUtil.getLatestArticle(journalArticleResource.getResourcePrimKey());
			    JournalArticle journalArticle = JournalArticleLocalServiceUtil.getLatestArticle(ae.getClassPK());
			    
			    		for(int i = 0; i < codesSplit.length;i++)
						{
							if(journalArticle.getContent().contains(codesSplit[i]))
							   {
								
								Document document = null;
								
								document = SAXReaderUtil.read(journalArticle.getContentByLocale(locale));
								
								mapping = new RateMapping(); 
								mapping.code = document.valueOf("//dynamic-element[@name='codeRate']/dynamic-content/text()");
								mapping.name = document.valueOf("//dynamic-element[@name='nameRate']/dynamic-content/text()");
								mapping.title = document.valueOf("//dynamic-element[@name='codeRate']/dynamic-content/text()").concat("-").concat(document.valueOf("//dynamic-element[@name='keywordRate']/dynamic-content/text()"));
								mapping.keyword = document.valueOf("//dynamic-element[@name='keywordRate']/dynamic-content/text()");
								mapping.description = document.valueOf("//dynamic-element[@name='descriptionLongRate']/dynamic-content/text()");
								mapping.shortDescription = document.valueOf("//dynamic-element[@name='shortDescriptionRate']/dynamic-content/text()");
								mapping.benefits = document.valueOf("//dynamic-element[@name='benefitsRate']/dynamic-content/text()");
								mapping.restrictions = document.valueOf("//dynamic-element[@name='Restrictions1']/dynamic-content/text()");
								mapping.currency = document.valueOf("//dynamic-element[@name='currencyRate']/dynamic-content/text()");
								mapping.enddate = document.valueOf("//dynamic-element[@name='finalDateBooking']/dynamic-content/text()");
								mapping.guid = journalArticle.getArticleId();
								mapping.language = Constants.LENGUAJE;
								rates.add(mapping);
							   
							   break;
							   }
						}
			}
		} catch (Exception e) {
			log.error("module getWebContentRate: "+e);
		}
		return rates;
		
	}
	
	// Metodo que filtra por codigo
	public HashSet<RateMapping> getArticlesByCodeBrand() throws PortalException{
				
				HashSet<RateMapping> rates = new HashSet<>();
				
				String locale = Constants.getLanguaje();// Contiene el lenguaje
				
				log.info("Contratos: "+ Constants.CONTRACTCODES);
				// condicion para filtrar contratos
				if(!Constants.CONTRACTCODES.equals("")){
					String codes = Constants.CONTRACTCODES;// vienen los contratos filtrados
					Constants.CONTRACTCODES = "";// se restablece el valor
					String[] codesSplit = codes.split(",");
					rates = getWebContentRateFilter(codesSplit,locale);
				}else{
					log.info("Sin contratos");
					rates = getWebContentRate(locale);
					
				}
				return rates;
			}
	
	private boolean getIntervals(String i, String f, String date){
		
		boolean estado = false;
		try {
			String d = date.replace('/','-');
			String init = i.replace('/','-');
			String end = f.replace('/','-');
			String fi = (end.equals(""))? DateTime.now().toString():end;
			DateTime inicio = new DateTime(init);
			DateTime fin = new DateTime(fi);
			Interval interval = new Interval(inicio, fin);
			if(d.equals("")){
				estado = true;
			}
			estado = interval.contains(new DateTime(d));
			
			} catch (IllegalArgumentException e) {
			// TODO: handle exception
				e.getStackTrace();
			}
		return estado;
	}
}
