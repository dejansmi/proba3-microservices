package com.dejans.proba3microservices;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLParser;
import java.time.Instant;
import java.time.LocalDate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Proba3Controller {
	static ObjectMapper mapper = new ObjectMapper();

	protected Logger logger = Logger.getLogger(Proba3Controller.class.getName());

	@RequestMapping("/proba3/test")
	public String all3() {
		logger.info("proba3-microservice all() invoked");
		return "Proba3 TEST";
	}

	@RequestMapping("/proba3/json")
	public String getPaymentOrder(HttpServletRequest req) throws JsonProcessingException {
		logger.info("proba3-microservice all() invoked ");
		PaymentOrder paymentOrder = new PaymentOrder("R3254345354", "RS5323423234", 12345, 2, new Date());
		String paymentOrderJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(paymentOrder);
		// String paymentOrderJson = ":PROBA";
		return "Proba3 tekst " + paymentOrderJson;
	}

	@RequestMapping("/proba3/array")
	public String getPaymentOrderList(HttpServletRequest req) throws JsonProcessingException {
		logger.info("proba3-microservice all() invoked");
		ArrayList<PaymentOrder> list = new ArrayList<PaymentOrder>();
		PaymentOrder paymentOrder = new PaymentOrder("R3254345354", "RS5323423234", 12345, 2, new Date());
		list.add(paymentOrder);
		paymentOrder = new PaymentOrder("SR3254345354", "SR5323423234", 54321, 2, new Date());
		list.add(paymentOrder);
		String paymentOrderJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(list);
		// String paymentOrderJson = ":PROBA";
		return "Proba3 tekst " + paymentOrderJson;
	}

	@RequestMapping("/proba3/model")
	public String getModel(HttpServletRequest req) throws JsonProcessingException {
		logger.info("proba3-microservice getConfig() invoked");
		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		try {
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			ModelDefinition modelLocal = mapper.readValue(new File("model.yml"), ModelDefinition.class);
			//String first = configDomainLocal.getDomains().getFirst();
			//String second = configDomainLocal.getDomains().getSecond();
			String pom0 = modelLocal.toString();
			pom0 += modelLocal.toCheck();

			return pom0;
		} catch (Exception e) {
			// TODO;   Auto-generated catch block
			e.printStackTrace();
			return "Error";
		}

		// String paymentOrderJson = ":PROBA";
	}

	@RequestMapping("/proba3/treemap")
	public String getTreeMap(HttpServletRequest req) throws JsonProcessingException {
		logger.info("proba3-microservice getConfig() invoked");

		try {
			String pom = new String();

			File fileName = new File("Files/domains.yml");
			TreeMapYamlParse tMYP = new TreeMapYamlParse(fileName);

			fileName = new File("Files/model.yml");
			tMYP.addConfiguration(fileName);

			fileName = new File("Files/databases.yml");
			tMYP.addConfiguration(fileName);

			fileName = new File("Files/api.yml");
			tMYP.addConfiguration(fileName);

			pom = tMYP.toString();

			ModelDefinitionTree modelTree = new ModelDefinitionTree(tMYP);
			ValidateByDefinition.setModel(modelTree);

			Freemarkertest fmt = new Freemarkertest();

			OurORM o = new OurORM(modelTree);
			Pets pets = new Pets();
			Instant t1 = Instant.now();
			String xpetTxt = new String();

			try {
				Pet pet = new Pet();
				pet.setId(172);
				pet.setName(null);
				pet.setTag("Maca");
				pet.setTag("Kuca");
				LocalDate date = LocalDate.of(2015, Calendar.FEBRUARY, 4);
				pet.setBorn(null);
				List<Pet> lpet = new ArrayList<Pet>();
				lpet.add(pet);
				pet = new Pet();
				pet.setId(173);
				pet.setName("Pas");
				pet.setTag("Kuca");
				date = LocalDate.of(2015, Calendar.FEBRUARY, 5);
				pet.setBorn(null);
				lpet.add(pet);
				Pet petOLD = new Pet();
				petOLD.setId(null);
				pet = new Pet();
				pet.setOLD(petOLD);
				pet.setId(null);
				pet.setName("Mama");
				pet.setTag(null);
				pet.setStatus("FU");
				pet.setId(null);
				pet.setName("Kupus");
				pet.setTag("Marconi");
				lpet.add(pet);
				pet = new Pet();
				Integer ids = 4;
				pet.setId(ids);
				pet.setName("Mama");
				pet.setTag(null);
				date = LocalDate.of(1982, 4, 1);
				pet.setBorn(date);
				pet.setStatus("FU");
				ids = 6;
				pet.setId(ids);
				pet.setName("Kapa");
				String dugi = "123456789 ";
				dugi += dugi + dugi + dugi + dugi + dugi + dugi + dugi + dugi;
				pet.setTag(dugi);
				date = LocalDate.now();
				pet.setBorn(date);
				lpet.add(pet);
				pet = new Pet();
				pet.setId(172);
				pet.setStatus("D");
				lpet.add(pet);

				pets.setPets(lpet);
				ExceptionHandlings.checkExceptionsThrow();
				o.updIns1(pets);

				Pets spet = new Pets();

				QueryBuilder qWhere = new QueryBuilder(modelTree, "Pet");
				qWhere = qWhere.selectItems("name", "tag", "born").item("id").le().constant(6).or().item("name").eq()
						.constant("A").forUpdate();
				Instant ts = Instant.now();

				o.selectObjects(qWhere, spet);

				Instant t2 = Instant.now();
				long sek = java.time.Duration.between(t1, t2).toMillis();

				System.out.println(sek);
				sek = java.time.Duration.between(ts, t2).toMillis();
				System.out.println(sek);

				for (Pet xpet : spet.getList()) {
					if (xpet.getName().equals("A")) {
						LocalDate localDate = LocalDate.now();
						xpet.setBorn(localDate);
					}
					xpetTxt += (xpet.getId() + " " + xpet.getName() + " " + xpet.getTag() + " " + xpet.getBorn()
							+ "<br/>");

				}
				o.updIns1(spet);
			} catch (Exception e) {
				
				xpetTxt = ExceptionHandlings.catchHandlingsHTTP(e);
			}

			return "Proba3 Tree: <p>" + xpetTxt + "</p><p><pre>" + pom + "</pre></p>";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Error";
		}

		// String paymentOrderJson = ":PROBA";
	}

	@RequestMapping("/proba3/config")
	public String getConfig(HttpServletRequest req) throws JsonProcessingException {
		logger.info("proba3-microservice getConfig() invoked");

		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		try {
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			ConfigDomain configDomainLocal = mapper.readValue(new File("Files/test.yml"), ConfigDomain.class);
			//String first = configDomainLocal.getDomains().getFirst();
			//String second = configDomainLocal.getDomains().getSecond();
			String pom0 = configDomainLocal.getDomains().toString();

			YAMLFactory factory = new YAMLFactory();
			YAMLParser parser = factory.createParser(new File("Files/model.yml"));
			String pom = "";
			while (parser.nextToken() != null) {
				if (parser.currentToken() == JsonToken.END_ARRAY) {
					pom += " END ARRAY\n";
					pom += parser.getCurrentName();
					pom += "\n" + parser.getText() + "\n";
				} else if (parser.currentToken() == JsonToken.END_OBJECT) {
					pom += " END_OBJECT\n";
					pom += parser.getCurrentName();
					pom += "\n" + parser.getText() + "\n";
				} else if (parser.currentToken() == JsonToken.FIELD_NAME) {
					pom += " FIELD_NAME\n";
					pom += parser.getCurrentName();
					pom += "\n" + parser.getText() + "\n";
				} else if (parser.currentToken() == JsonToken.NOT_AVAILABLE) {
					pom += " NOT_AVAILABLE\n";
					pom += parser.getCurrentName();
					pom += "\n" + parser.getText() + "\n";
				} else if (parser.currentToken() == JsonToken.START_ARRAY) {
					pom += " START_ARRAY\n";
					pom += parser.getCurrentName();
					pom += "\n" + parser.getText() + "\n";
				} else if (parser.currentToken() == JsonToken.START_OBJECT) {
					pom += " START_OBJECT\n";
					pom += parser.getCurrentName();
					pom += "\n" + parser.getText() + "\n";
				} else if (parser.currentToken() == JsonToken.VALUE_EMBEDDED_OBJECT) {
					pom += " VALUE_EMBEDDED_OBJECT\n";
					pom += parser.getCurrentName();
					pom += "\n" + parser.getText() + "\n";
				} else if (parser.currentToken() == JsonToken.VALUE_FALSE) {
					pom += " VALUE_FALSE\n";
					pom += parser.getCurrentName();
					pom += "\n" + parser.getText() + "\n";
				} else if (parser.currentToken() == JsonToken.VALUE_NULL) {
					pom += " VALUE_NULL\n";
					pom += parser.getCurrentName();
					pom += "\n" + parser.getText() + "\n";
				} else if (parser.currentToken() == JsonToken.VALUE_NUMBER_FLOAT) {
					pom += " VALUE_NUMBER_FLOAT\n";
					pom += parser.getCurrentName();
					pom += "\n" + parser.getText() + "\n";
				} else if (parser.currentToken() == JsonToken.VALUE_NUMBER_INT) {
					pom += " VALUE_NUMBER_INT\n";
					pom += parser.getCurrentName();
					pom += "\n" + parser.getText() + "\n";
				} else if (parser.currentToken() == JsonToken.VALUE_STRING) {
					pom += " VALUE_STRING\n";
					pom += parser.getCurrentName();
					pom += "\n" + parser.getText() + "\n";
				} else if (parser.currentToken() == JsonToken.VALUE_TRUE) {
					pom += " VALUE_TRUE\n";
					pom += parser.getCurrentName();
					pom += "\n" + parser.getText() + "\n";
				}

			}

			YamlMap yamlMap = new YamlMap();

			String pom1 = yamlMap.PrintHashMapString();

			return "Proba3: <p>" + pom0 + "</p><p><pre>" + pom + "</pre></p><p>" + pom1 + "</pom>";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Error";
		}

		// String paymentOrderJson = ":PROBA";
	}

	@RequestMapping("/proba3/")
	public String all(HttpServletRequest req) {
		logger.info("proba3-microservice all() invoked");
		return "Proba3 tekst " + req.getPathInfo();
	}

	@RequestMapping("/proba3/**")
	public String others(HttpServletRequest req) {
		logger.info("proba3-microservice OTHERS all() invoked");
		return "OTHERS CALLS in Proba3 ";
	}

}
