package com.craftsmanasia.utils;

import java.io.InputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.Resource;

import com.craftsmanasia.model.filter.SearchFilter;

public class OrderByUtility {
	
	private static final Map<String, Map<String, String>> MAPPINGS;
	
	static {
		MAPPINGS = new HashMap<String, Map<String, String>>();
	}

	public static String getOrderByString(SearchFilter filter) {
		if (filter.isOrdered()) {
			List<OrderingProperty> orderingProperties = filter.getOrderingProperties();
			
			if (orderingProperties == null || orderingProperties.size() == 0) {
				return null;
			}
			
			String filterName = filter.getClass().getName();
			Map<String, String> mapping = MAPPINGS.get(filterName);
			
			if (mapping == null || mapping.size() == 0) {
				return null;
			}
			
			Collections.sort(orderingProperties, new Comparator<OrderingProperty>() {
				@Override
				public int compare(OrderingProperty o1, OrderingProperty o2) {
					return (o1.getPriority() - o2.getPriority());
				}
	            
	        });
			
			StringBuilder sb = new StringBuilder();
			for (OrderingProperty orderingProperty : orderingProperties) {
				String propertyName = orderingProperty.getProperty();
				String column = mapping.get(propertyName);
				
				if (StringUtils.isBlank(column)) {
					continue;
				}
				
				if (sb.length() > 0) {
					sb.append(", ");
				} 
				
				sb.append(column);
				
				if (orderingProperty.isAsc()) {
					sb.append(" ASC");
				} else {
					sb.append(" DESC");
				}
			}
			
			return sb.toString();
		}
		
		return null;
	}
	
	private static void init(InputStream is) throws Exception {
		Builder parser = new Builder();
		Document doc = parser.build(is);
		
		Element root = doc.getRootElement();
		
		Elements filters = root.getChildElements();
		
		for (int i = 0; i < filters.size(); i ++) {
			Element filter = filters.get(i);
			
			String name = filter.getAttributeValue("name");
			
			Map<String, String> mapping = new HashMap<String, String>();
			
			MAPPINGS.put(name, mapping);
			
			Elements properties = filter.getChildElements();
			
			for (int j = 0; j < properties.size(); j ++) {
				Element property = properties.get(j);
				
				String propertyName = property.getAttributeValue("name");
				String column = property.getAttributeValue("column");
				
				mapping.put(propertyName, column);
			}
		}
		
	}

	public void setConfigFile(Resource configFile) throws Exception {
		if (configFile == null || configFile.getInputStream() == null) {
			throw new Exception("Cannot load configuration file!");
		}
		
		InputStream is = configFile.getInputStream();
		init(is);
	}
}
