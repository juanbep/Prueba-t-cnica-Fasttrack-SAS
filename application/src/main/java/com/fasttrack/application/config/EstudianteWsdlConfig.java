package com.fasttrack.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@Configuration
public class EstudianteWsdlConfig extends WsdlConfig {

	@Bean
	public XsdSchema estudianteSchema() {
		return new SimpleXsdSchema(new ClassPathResource("schemas/estudiante.xsd"));
	}

	@Bean(name = "estudiante")
	public DefaultWsdl11Definition estudianteWsdl(XsdSchema estudianteSchema) {
		return createWsdlDefinition("EstudiantePort", "/ws", "http://fasttrack.com/application/estudiante",
				estudianteSchema);
	}
}
