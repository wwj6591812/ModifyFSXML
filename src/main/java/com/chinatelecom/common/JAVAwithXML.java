package com.chinatelecom.common;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.chinatelecom.common.PropertiesHelper.PropertiesConfig;
import com.chinatelecom.model.Allocations;

public class JAVAwithXML {

	private static PropertiesConfig conf;

	public static Allocations transferXMLToJava() throws JAXBException,
			IOException {
		Properties prop = PropertiesHelper.load("modifyQueueACL.properties");
		conf = PropertiesHelper.createConfig(prop);
		JAXBContext jc = JAXBContext.newInstance(Allocations.class);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		Allocations allocations = (Allocations) unmarshaller
				.unmarshal(new File(conf.get(Constant.FAIR_SCHEDULER_ADDRESS,
						"conf/fair-scheduler.xml")));
		return allocations;
	}

	public static void transferJavaToXML(Allocations allocations)
			throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(Allocations.class);
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
		marshaller.marshal(
				allocations,
				new File(conf.get(Constant.FAIR_SCHEDULER_ADDRESS,
						"conf/fair-scheduler.xml")));
	}
}
