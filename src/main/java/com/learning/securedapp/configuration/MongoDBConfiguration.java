package com.learning.securedapp.configuration;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoExceptionTranslator;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.WriteResultChecking;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoConverter;

import com.learning.securedapp.exception.SecuredAppException;
import com.learning.securedapp.exception.SecuredAppExceptionBean;
import com.mongodb.MongoClient;
import com.mongodb.WriteConcern;

/**
 * <p>MongoDBConfiguration class.</p>
 *
 * @author rajakolli
 */
@Configuration
public class MongoDBConfiguration extends AbstractMongoConfiguration {

	/** {@inheritDoc} */
	@Override
	protected String getDatabaseName() {
		return "test";
	}

	/** {@inheritDoc} */
	@Override
	protected List<String> getMappingBasePackages() {
		return Arrays.asList("com.learning.securedapp.domain");
	}

	/**
	 * <p>mongoConverter.</p>
	 *
	 * @return a {@link org.springframework.data.mongodb.core.convert.MongoConverter} object.
	 * @throws com.learning.securedapp.exception.SecuredAppException if any.
	 */
	@Bean
	public MongoConverter mongoConverter() throws SecuredAppException {
		MappingMongoConverter converter = null;
		try {
			DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDbFactory());
			converter = new MappingMongoConverter(dbRefResolver, mongoMappingContext());
			converter.setTypeMapper(new DefaultMongoTypeMapper(null));
		} catch (ClassNotFoundException e) {
			SecuredAppExceptionBean faultBean = new SecuredAppExceptionBean();
			faultBean.setFaultCode("MONGO2");
			faultBean.setFaultString("Unable to find MongoDB entities");
			throw new SecuredAppException(faultBean, e);
		}

		return converter;
	}

	/** {@inheritDoc} */
	@Bean
	@Override
	public MongoDbFactory mongoDbFactory() {
		return new SimpleMongoDbFactory(mongoClient(), getDatabaseName());
	}

	/**
	 * <p>mongoClient.</p>
	 *
	 * @return a {@link com.mongodb.MongoClient} object.
	 */
	@Bean(destroyMethod = "close")
	public MongoClient mongoClient() {
		return new MongoClient();
	}

	/** {@inheritDoc} */
	@Bean
	@Override
	public MongoTemplate mongoTemplate() throws Exception {
		MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory(), mongoConverter());
		mongoTemplate.setWriteConcern(WriteConcern.JOURNALED);
		mongoTemplate.setWriteResultChecking(WriteResultChecking.EXCEPTION);
		return mongoTemplate;
	}

	/**
	 * <p>exceptionTranslator.</p>
	 *
	 * @return a {@link org.springframework.data.mongodb.core.MongoExceptionTranslator} object.
	 */
	@Bean
	public MongoExceptionTranslator exceptionTranslator() {
		return new MongoExceptionTranslator();
	}

}
