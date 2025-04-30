package com.fasttrack.application.config;

import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.XsdSchema;

public abstract class WsdlConfig {
	protected DefaultWsdl11Definition createWsdlDefinition(String portTypeName, String locationUri,
			String targetNamespace, XsdSchema schema) {
		DefaultWsdl11Definition wsdl = new DefaultWsdl11Definition();
		wsdl.setPortTypeName(portTypeName);
		wsdl.setLocationUri(locationUri);
		wsdl.setTargetNamespace(targetNamespace);
		wsdl.setSchema(schema);
		return wsdl;
	}
}
