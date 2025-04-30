package com.fasttrack.application.config;


import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.ws.soap.server.endpoint.interceptor.PayloadValidatingInterceptor;
import org.springframework.xml.validation.XmlValidator;
import org.springframework.xml.validation.XmlValidatorFactory;
import org.springframework.xml.xsd.XsdSchema;
import org.springframework.xml.xsd.XsdSchemaCollection;

@Configuration
public class ValidationConfig {
	
	@Bean
    public XsdSchemaCollection schemaCollection() {
        Resource[] schemas = new Resource[] {
            new ClassPathResource("schemas/estudiante.xsd"),
            new ClassPathResource("schemas/materia.xsd"),
            new ClassPathResource("schemas/estudiante_materia.xsd")
        };

        return new XsdSchemaCollection() {
            @Override
            public XmlValidator createValidator() {
                try {
                    return XmlValidatorFactory.createValidator(schemas, "http://www.w3.org/2001/XMLSchema");
                } catch (IOException e) {
                    throw new RuntimeException("Failed to create XML validator", e);
                }
            }

			@Override
			public XsdSchema[] getXsdSchemas() {
				// TODO Auto-generated method stub
				return null;
			}
  
        };
    }

	@Bean
    public PayloadValidatingInterceptor validatingInterceptor(XsdSchemaCollection schemaCollection) {
        PayloadValidatingInterceptor interceptor = new PayloadValidatingInterceptor();
        interceptor.setXsdSchemaCollection(schemaCollection);
        interceptor.setValidateRequest(true);
        interceptor.setValidateResponse(false);
        interceptor.setAddValidationErrorDetail(true);
        interceptor.setFaultStringOrReason("Campos invalidos");
        return interceptor;
    }

}
