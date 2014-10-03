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
 *   Artifact    : org.ldp4j.framework:ldp4j-server-application:1.0.0-SNAPSHOT
 *   Bundle      : ldp4j-server-application-1.0.0-SNAPSHOT.jar
 * #-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=#
 */
package org.ldp4j.application.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;


final class MutableDataSet implements DataSet {

	private final Name<?> name;
	private final Map<Object,Individual<?,?>> individuals;
	private final IndividualFactory factory;
	
	public MutableDataSet(Name<?> name) {
		this.name = name;
		this.individuals=new LinkedHashMap<Object, Individual<?,?>>();
		this.factory=new IndividualFactory(this);
	}
	
	@Override
	public Name<?> name() {
		return this.name;
	}

	@Override
	public int numberOfIndividuals() {
		return this.individuals.size();
	}

	@Override
	public boolean hasIndividuals() {
		return !this.individuals.isEmpty();
	}

	@Override
	public Collection<Individual<?, ?>> individuals() {
		return new ArrayList<Individual<?,?>>(this.individuals.values());
	}

	@Override
	public Set<Object> individualIds() {
		return new LinkedHashSet<Object>(this.individuals.keySet());
	}

	@Override
	public boolean hasIndividual(Object id) {
		return this.individuals.containsKey(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> Individual<T,?> individualOfId(T id) {
		return (Individual<T,?>)this.individuals.get(id);
	}

	@Override
	public <T, S extends Individual<T, S>> S individual(T id, Class<? extends S> clazz) {
		Individual<T,?> result=individualOfId(id);
		if(result==null) {
			result=factory.newIndividual(clazz,id);
			this.individuals.put(id,result);
		} else if (!clazz.isInstance(result)) {
			throw new IllegalStateException("Existing individual '"+id+"' is not compatible with '"+clazz.getCanonicalName()+"' ("+result.getClass().getCanonicalName()+")");
		}
		return clazz.cast(result);
	}

	@Override
	public Iterator<Individual<?, ?>> iterator() {
		return individuals().iterator();
	}

}