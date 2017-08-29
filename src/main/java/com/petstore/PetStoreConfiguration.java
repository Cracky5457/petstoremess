package com.petstore;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.petstore.dto.base.RESTResponse;

@Configuration
public class PetStoreConfiguration extends WebMvcConfigurerAdapter {
	
	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		for (HttpMessageConverter<?> converter : converters) {
			if (converter instanceof AbstractJackson2HttpMessageConverter) {
				AbstractJackson2HttpMessageConverter c = (AbstractJackson2HttpMessageConverter) converter;
				ObjectMapper objectMapper = c.getObjectMapper();
				// objectMapper.setSerializationInclusion(Include.NON_NULL);

				PropertyFilter theFilter = getPsFilter();
				FilterProvider filters = new SimpleFilterProvider().addFilter("psFilter", theFilter);

				objectMapper.setFilterProvider(filters);
			}
		}

		super.extendMessageConverters(converters);
	}
	
	public static PropertyFilter getPsFilter() {
		PropertyFilter theFilter = new SimpleBeanPropertyFilter() {
			@Override
			public void serializeAsField

			(Object pojo, JsonGenerator jgen, SerializerProvider provider, PropertyWriter writer)
					throws Exception {
				if (include(writer)) {
					if (pojo instanceof RESTResponse) {
						RESTResponse resp = ((RESTResponse) pojo);
						if (!resp.isIncludeMetaAttributes()) {
							if (writer.getName().equals("_errorFields")
									|| writer.getName().equals("_errorMessage")
									|| writer.getName().equals("_warningMessage")
									|| writer.getName().equals("_status")
									|| writer.getName().equals("_successMessage")) {
								// Do not serialize

							} else {
								writer.serializeAsField(pojo, jgen, provider);
							}
						} else {
							writer.serializeAsField(pojo, jgen, provider);
						}
						/*
						 * if (!writer.getName().equals("intValue")) {
						 * writer.serializeAsField(pojo, jgen,
						 * provider); return; } int intValue = 3; if
						 * (intValue >= 0) {
						 * writer.serializeAsField(pojo, jgen,
						 * provider); }
						 */
					} else {
						writer.serializeAsField(pojo, jgen, provider);
					}
				} else if (!jgen.canOmitFields()) { // since 2.3
					writer.serializeAsOmittedField(pojo, jgen, provider);
				}
			}

			@Override
			protected boolean include(BeanPropertyWriter writer) {
				return true;
			}

			@Override
			protected boolean include(PropertyWriter writer) {
				return true;
			}
		};
		
		return theFilter;
	}
}

