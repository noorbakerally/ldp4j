/**
 * #-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=#
 *   This file is part of the LDP4j Project:
 *     http://www.ldp4j.org/
 *
 *   Center for Open Middleware
 *     http://www.centeropenmiddleware.com/
 * #-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=#
 *   Copyright (C) 2014 Center for Open Middleware.
 * #-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=#
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *             http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 * #-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=#
 *   Artifact    : org.ldp4j.framework:ldp4j-application-data:1.0.0-SNAPSHOT
 *   Bundle      : ldp4j-application-data-1.0.0-SNAPSHOT.jar
 * #-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=#
 */
package org.ldp4j.application.data;

import java.net.URI;

import org.ldp4j.application.data.Individual;
import org.ldp4j.application.data.Property;
import org.ldp4j.application.data.Value;

final class PropertyHelperImpl implements PropertyHelper {

	private URI propertyId;
	private Individual<?, ?> individual;

	PropertyHelperImpl(URI propertyId, Individual<?,?> individual) {
		this.propertyId = propertyId;
		this.individual = individual;
	}

	private Property getProperty() {
		return this.individual.property(propertyId);
	}

	@Override
	public <T> T firstValue(final Class<? extends T> aClazz) {
		Property property=getProperty();
		if(property==null) {
			return null;
		}
		LiteralValueExtractor<T> extractor =
			new LiteralValueExtractor<T>(new LiteralAdapter<T>(aClazz));
		for(Value value:property) {
			value.accept(extractor);
			if(extractor.isAvailable()) {
				break;
			}
		}
		return extractor.getValue();
	}

	@Override
	public <T, S extends Individual<T,S>> T firstIndividual(final Class<? extends S> clazz) {
		Property property=getProperty();
		if(property==null) {
			return null;
		}
		IndividualExtractor<T,S> extractor=new IndividualExtractor<T,S>(clazz);
		for(Value value:property) {
			value.accept(extractor);
			if(extractor.isAvailable()) {
				return extractor.getValue().id();
			}
		}
		return null;
	}

	@Override
	public <T> IndividualPropertyHelper withLiteral(T rawValue) {
		if(rawValue!=null) {
			Literal<T> value = DataSetUtils.newLiteral(rawValue);
			this.individual.addValue(this.propertyId,value);
		}
		return new IndividualPropertyHelperImpl(new IndividualHelperImpl(this.individual),this);
	}

}