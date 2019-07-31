package com.consistent.services.liferay.sax;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.consistent.services.liferay.constants.Constants;
import com.consistent.services.liferay.interf.Mapping;
import com.consistent.services.liferay.sax.RoomMapping;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

public class RoomMapping {
private static final Log log = LogFactoryUtil.getLog(RoomMapping.class);
	
	private String articleId;
	private String code;
	private String name;
    private String title;
    private String keywords;
    private String description;
    private String shortDescription;
    private List<String> mediaLinks;
    private String articleTitle;
    
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
	
	 public List<String> getMediaLinks() {
			return mediaLinks;
		}

		public void setMediaLinks(List<String> mediaLinks) {
			this.mediaLinks = mediaLinks;
		}

		public String getArticleId() {
	        return articleId;
	    }

	    public void setArticleId(String articleId) {
	        this.articleId = articleId;
	    }

	    public String getTitle() {
	        return title;
	    }

	    public void setTitle(String title) {
	        this.title = title;
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

		public String getKeywords() {
			return keywords;
		}

		public void setKeywords(String keywords) {
			this.keywords = keywords;
		}

		public String getArticleTitle() {
			return articleTitle;
		}

		public void setArticleTitle(String articleTitle) {
			this.articleTitle = articleTitle;
		}

		public static Log getLog() {
			return log;
		}
		
		

    public RoomMapping(String articleId, String code, String name, String title, String keywords,
				String description, String shortDescription, List<String> mediaLinks, String articleTitle) {
			super();
			this.articleId = articleId;
			this.code = code;
			this.name = name;
			this.title = title;
			this.keywords = keywords;
			this.description = description;
			this.shortDescription = shortDescription;
			this.mediaLinks = mediaLinks;
			this.articleTitle = articleTitle;
		}
    
    public RoomMapping(){
    	this.articleId = "";
		this.code = "";
		this.name = "";
		this.title = "";
		this.keywords = "";
		this.description = "";
		this.shortDescription = "";
		this.mediaLinks = new ArrayList<>();
		this.articleTitle = "";
    }

	public void  RoomContent(JournalArticle content, String locale) {
    	
        this.articleId = content.getArticleId();
        this.title = content.getTitle(locale);
        this.articleTitle = content.getTitle(locale);
        Document docXML = null;
        try {
            docXML = SAXReaderUtil.read(content.getContentByLocale(locale));
            this.code = docXML.valueOf("//dynamic-element[@name='codeRoom']/dynamic-content/text()");
            this.name = docXML.valueOf("//dynamic-element[@name='nameRoom']/dynamic-content/text()");
            this.keywords = docXML.valueOf("//dynamic-element[@name='keywordsRoom']/dynamic-content/text()");
            this.description= docXML.valueOf("//dynamic-element[@name='descriptionRoom']/dynamic-content/text()");
            this.shortDescription=docXML.valueOf("//dynamic-element[@name='shortDescriptionRoom']/dynamic-content/text()");
        
            List<Node> mediaNodes = docXML.selectNodes("//dynamic-element[@name='mediaLinksRoom']/dynamic-element");
			List<String> mediaArray=new ArrayList<String>();
			for(Node mediaNode : mediaNodes){				
				String pie= mediaNode.valueOf("dynamic-element[@name='footer']/dynamic-content/text()");				
				String link= mediaNode.valueOf("dynamic-content/text()");				
				String type_image= mediaNode.valueOf("dynamic-element[@name='typeRoom']/dynamic-content/text()");						
				if(!link.trim().equals("")){
					JSONObject object=JSONFactoryUtil.createJSONObject();
					object.put("link", link);
					object.put("pie", pie);
					object.put("type_image", type_image);
					mediaArray.add(object.toJSONString());
				}	
								
            }
			this.mediaLinks=sanitizeArray(mediaArray);

        	} catch (DocumentException e) {
	            log.error("ERROR get to XML", e);
	        }
    }
    
    public List<String> sanitizeArray(List<String> arraySan){
    	if(arraySan.size()>0){
	    	while(arraySan.size()<2){
				JSONObject object=JSONFactoryUtil.createJSONObject();
				arraySan.add(object.toJSONString());				
			}
    	}
    	return arraySan;    	
    }
	
  
   
	public String mappngRoom() throws XMLStreamException{
	  StringWriter stringWriter = new StringWriter();
	  XMLOutputFactory xMLOutputFactory = XMLOutputFactory.newInstance();
  	  XMLStreamWriter xMLStreamWriter =
      xMLOutputFactory.createXMLStreamWriter(stringWriter);
		 
	        xMLStreamWriter.writeStartElement("room");
		        xMLStreamWriter.writeStartElement("guid");
		        	xMLStreamWriter.writeCharacters(getArticleId());
		        xMLStreamWriter.writeEndElement();
		        xMLStreamWriter.writeStartElement("code");
	        		xMLStreamWriter.writeCharacters(getCode());
	        	xMLStreamWriter.writeEndElement();
	        	xMLStreamWriter.writeStartElement("name");
     			xMLStreamWriter.writeCharacters(getName());
     		xMLStreamWriter.writeEndElement();
     		xMLStreamWriter.writeStartElement("title");
 				xMLStreamWriter.writeCharacters(getTitle());
 			xMLStreamWriter.writeEndElement();
 			xMLStreamWriter.writeStartElement("language");
					xMLStreamWriter.writeCharacters(Constants.LENGUAJE);
				xMLStreamWriter.writeEndElement();
				xMLStreamWriter.writeStartElement("keyword");
					xMLStreamWriter.writeCharacters(getKeywords());
				xMLStreamWriter.writeEndElement();
				xMLStreamWriter.writeStartElement("shortDescription");
					xMLStreamWriter.writeDTD(shortDescription);
				xMLStreamWriter.writeEndElement();
				xMLStreamWriter.writeStartElement("description");
					xMLStreamWriter.writeDTD(description);
				xMLStreamWriter.writeEndElement();
				xMLStreamWriter.writeStartElement("order");
					xMLStreamWriter.writeCharacters(Mapping.order);
				xMLStreamWriter.writeEndElement();
				xMLStreamWriter.writeStartElement("channel");
					xMLStreamWriter.writeCharacters(Mapping.channel);
				xMLStreamWriter.writeEndElement();
				

				//mediaLink section
				/*mediaLink section*/
		         JSONArray ArrayMediaLinks = JSONFactoryUtil.createJSONArray();
		         List<String> MeliaLinkList = getMediaLinks();
					for (String mediaLinkItem : MeliaLinkList) {
						JSONObject myObject;
						try {
							
							myObject = JSONFactoryUtil.createJSONObject(mediaLinkItem);
							ArrayMediaLinks.put(myObject);
						} catch (JSONException e) {
							log.error("Error converter json"+e);
						}
						
					}
					xMLStreamWriter.writeStartElement("medialinks");		   
			         xMLStreamWriter.writeStartElement("medialink");
			         
					   xMLStreamWriter.writeStartElement("keyword");
					   xMLStreamWriter.writeEndElement();
					         for (int i = 0; i < ArrayMediaLinks.length(); i++) {
									JSONObject jsonobject = ArrayMediaLinks.getJSONObject(i);
								    String link = jsonobject.getString("link");
								    String type_image = jsonobject.getString("type_image");
									xMLStreamWriter.writeStartElement("multimedia");
						            xMLStreamWriter.writeAttribute("type",type_image);
							        xMLStreamWriter.writeStartElement("url");
							        xMLStreamWriter.writeCharacters(link);
							        xMLStreamWriter.writeEndElement();
						            xMLStreamWriter.writeEndElement();
								}
					         xMLStreamWriter.writeStartElement("thumbnail");
					         xMLStreamWriter.writeEndElement();
					         xMLStreamWriter.writeStartElement("type");
					         xMLStreamWriter.writeEndElement();
				      xMLStreamWriter.writeEndElement();
		         xMLStreamWriter.writeEndElement();
		          //mediaLink section
				
	        xMLStreamWriter.writeEndElement();
     
     xMLStreamWriter.flush();
     xMLStreamWriter.close();

    String xmlString = stringWriter.getBuffer().toString();
    xmlString=xmlString.replaceAll("\\<\\?xml(.+?)\\?\\>", "").trim();
    xmlString = xmlString.replaceAll("&nbsp;", "");
    return xmlString;

	}
}
