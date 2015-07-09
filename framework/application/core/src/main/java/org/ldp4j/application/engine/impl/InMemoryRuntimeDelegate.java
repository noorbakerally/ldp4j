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
 *   Artifact    : org.ldp4j.framework:ldp4j-application-core:1.0.0-SNAPSHOT
 *   Bundle      : ldp4j-application-core-1.0.0-SNAPSHOT.jar
 * #-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=#
 */
package org.ldp4j.application.engine.impl;

import org.ldp4j.application.engine.constraints.ConstraintReportRepository;
import org.ldp4j.application.engine.endpoint.EndpointRepository;
import org.ldp4j.application.engine.spi.PersistencyManager;
import org.ldp4j.application.engine.spi.RuntimeDelegate;
import org.ldp4j.application.engine.spi.ServiceRegistry;

public final class InMemoryRuntimeDelegate extends RuntimeDelegate {

	private final InMemoryServiceRegistry serviceRegistry;
	private final InMemoryPersistencyManager persistencyManager;

	public InMemoryRuntimeDelegate() {
		this.serviceRegistry = new InMemoryServiceRegistry();
		this.persistencyManager= new InMemoryPersistencyManager();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ServiceRegistry getServiceRegistry() {
		return this.serviceRegistry;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PersistencyManager getPersistencyManager() {
		return this.persistencyManager;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ConstraintReportRepository getConstraintReportRepository() {
		return this.persistencyManager.constraintReportRepository();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EndpointRepository getEndpointRepository() {
		return this.persistencyManager.endpointRepository();
	}

}