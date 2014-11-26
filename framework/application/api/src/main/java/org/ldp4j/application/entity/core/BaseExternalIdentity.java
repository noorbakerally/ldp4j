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
 *   Artifact    : org.ldp4j.framework:ldp4j-application-api:1.0.0-SNAPSHOT
 *   Bundle      : ldp4j-application-api-1.0.0-SNAPSHOT.jar
 * #-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=#
 */
package org.ldp4j.application.entity.core;

import static com.google.common.base.Preconditions.checkNotNull;

import java.net.URI;

import org.ldp4j.application.entity.ExternalIdentity;
import org.ldp4j.application.entity.IdentityVisitor;

import com.google.common.base.Objects;

final class BaseExternalIdentity extends BaseIdentity implements ExternalIdentity {

	private final URI location;

	private BaseExternalIdentity(URI identifier, URI location) {
		super(identifier);
		this.location = location;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public URI location() {
		return this.location;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void accept(IdentityVisitor visitor) {
		visitor.visitExternal(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return super.hashCode()+Objects.hashCode(this.location);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		boolean result=super.equals(obj);
		if(result && obj.getClass()==getClass()) {
			BaseExternalIdentity that=(BaseExternalIdentity)obj;
			result=Objects.equal(this.location, that.location);
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void toString(StringHelper helper) {
		helper.add("location",this.location);

	}

	static BaseExternalIdentity create(URI location) {
		checkNotNull(location,"External location cannot be null");
		return new BaseExternalIdentity(IdentifierUtil.createExternalIdentifier(location),location);
	}

}