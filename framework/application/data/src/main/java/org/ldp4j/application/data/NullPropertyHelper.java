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

import org.ldp4j.application.vocabulary.Term;

import com.google.common.base.MoreObjects;

final class NullPropertyHelper implements PropertyHelper {

	private final URI propertyId;

	private final IndividualPropertyHelperImpl iph;

	NullPropertyHelper(URI propertyId) {
		this.propertyId = propertyId;
		this.iph = new IndividualPropertyHelperImpl(new NullIndividualHelper(), this);
	}

	@Override
	public <T> T firstValue(Class<? extends T> aClazz) {
		return null;
	}

	@Override
	public IndividualHelper firstIndividual() {
		return new NullIndividualHelper();
	}

	@Override
	public <T, S extends Individual<T, S>> T firstIndividual(Class<? extends S> clazz) {
		return null;
	}

	@Override
	public <T> IndividualPropertyHelper withLiteral(T rawValue) {
		return new IndividualPropertyHelperImpl(new NullIndividualHelper(), this);
	}

	@Override
	public <T> IndividualPropertyHelper withIndividual(Name<?> id) {
		return this.iph;
	}

	@Override
	public <T> IndividualPropertyHelper withIndividual(URI id) {
		return new IndividualPropertyHelperImpl(new NullIndividualHelper(), this);
	}

	@Override
	public <T> IndividualPropertyHelper withIndividual(String id) {
		return new IndividualPropertyHelperImpl(new NullIndividualHelper(), this);
	}

	@Override
	public <T> IndividualPropertyHelper withIndividual(Term id) {
		return new IndividualPropertyHelperImpl(new NullIndividualHelper(), this);
	}

	@Override
	public String toString() {
		return
			MoreObjects.
				toStringHelper(getClass()).
					add("propertyId", this.propertyId).
					toString();
	}

}