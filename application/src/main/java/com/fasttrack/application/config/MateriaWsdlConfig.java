package com.fasttrack.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@Configuration
public class MateriaWsdlConfig extends WsdlConfig {
	@Bean
	public XsdSchema materiaSchema() {
		return new SimpleXsdSchema(new ClassPathResource("schemas/materia.xsd"));
	}

	@Bean(name = "materia")
	public DefaultWsdl11Definition materiaWsdl(XsdSchema materiaSchema) {
		return createWsdlDefinition("MateriaPort", "/ws", "http://fasttrack.com/application/materia", materiaSchema);
	}
}
