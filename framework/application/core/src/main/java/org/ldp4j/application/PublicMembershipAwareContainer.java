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
package org.ldp4j.application;

import java.net.URI;

import org.ldp4j.application.data.Individual;
import org.ldp4j.application.domain.LDP;
import org.ldp4j.application.domain.RDF;
import org.ldp4j.application.endpoint.Endpoint;
import org.ldp4j.application.template.MembershipAwareContainerTemplate;
import org.ldp4j.application.template.ResourceTemplate;
import org.ldp4j.application.vocabulary.Term;

public abstract class PublicMembershipAwareContainer<T extends MembershipAwareContainerTemplate> extends PublicContainer {

	private final Class<? extends T> templateClass;

	protected PublicMembershipAwareContainer(ApplicationContext applicationContext, Endpoint endpoint, Class<? extends T> templateClass) {
		super(applicationContext, endpoint);
		this.templateClass = templateClass;
	}

	protected final T containerTemplate() {
		ResourceTemplate template = template();
		if(!this.templateClass.isInstance(template)) {
			throw new IllegalStateException("Invalid container template exception: expected an instance of class "+this.templateClass.getCanonicalName()+" but got an instance of "+template.getClass().getCanonicalName());
		}
		return this.templateClass.cast(template);
	}
	
	protected abstract Term containerType();

	final void fillInMemberMetadata(Individual<?,?> individual, Context ctx) {
		URI predicate = containerTemplate().membershipPredicate();
		switch(containerTemplate().membershipRelation()) {
		case HAS_MEMBER:
			populateHasMember(individual, ctx, predicate);
			break;
		case IS_MEMBER_OF:
			populateIsMemberOf(individual, ctx, predicate);
			break;
		
		}
	}

	private void populateIsMemberOf(Individual<?, ?> individual, Context ctx, URI predicate) {
		for(PublicResource member:members()) {
			Individual<?,?> tmp=ctx.newIndividual(member);
			tmp.addValue(
				predicate, 
				individual);
		}
	}

	private void populateHasMember(Individual<?, ?> individual, Context ctx, URI predicate) {
		for(PublicResource member:members()) {
			individual.addValue(
				predicate, 
				ctx.newIndividual(member));
		}
	}
	
	@Override
	protected void fillInMetadata(Individual<?, ?> individual, Context ctx) {
		super.fillInMetadata(individual, ctx);
		T template = containerTemplate();
		individual.
			addValue(
				ctx.property(RDF.TYPE), 
				ctx.reference(containerType())).
			addValue(
				ctx.property(LDP.MEMBERSHIP_RESOURCE),
				ctx.newIndividual(parent())).	
			addValue(
				ctx.property(template.membershipRelation().term()), 
				ctx.value(template.membershipPredicate()));
	}

}