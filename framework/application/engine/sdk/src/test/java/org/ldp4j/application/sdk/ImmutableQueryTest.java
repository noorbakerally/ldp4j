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
 *   Artifact    : org.ldp4j.framework:ldp4j-application-engine-sdk:0.2.0-SNAPSHOT
 *   Bundle      : ldp4j-application-engine-sdk-0.2.0-SNAPSHOT.jar
 * #-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=#
 */
package org.ldp4j.application.sdk;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.sameInstance;

import org.junit.Test;
import org.ldp4j.application.ext.Parameter;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public class ImmutableQueryTest {

	private static final String UNEXISTING_PARAMETER = "unexisting";
	private static final ImmutableList<String> SINGLE_VALUED_RAW_VALUES = ImmutableList.of("value3");
	private static final ImmutableList<String> MULTI_VALUED_RAW_VALUES = ImmutableList.of("value1","value2");

	private static final String PARAMETER_1 = "parameter name 1";
	private static final String PARAMETER_2 = "parameter name 2";

	private ImmutableQueryParameter singleValuedParameter;
	private ImmutableQueryParameter multiValuedParameter;

	private ImmutableQueryParameter singleValuedParameter() {
		if(this.singleValuedParameter==null) {
			this.singleValuedParameter=ImmutableQueryParameter.create(PARAMETER_1, SINGLE_VALUED_RAW_VALUES);
		}
		return this.singleValuedParameter;
	}

	private ImmutableQueryParameter multiValuedParameter() {
		if(this.multiValuedParameter==null) {
			this.multiValuedParameter=ImmutableQueryParameter.create(PARAMETER_2, MULTI_VALUED_RAW_VALUES);
		}
		return this.multiValuedParameter;
	}

	private ImmutableQuery sut() {
		ImmutableMap<String,ImmutableQueryParameter> parameterMap=
			ImmutableMap.
				<String,ImmutableQueryParameter>builder().
					put(PARAMETER_1,singleValuedParameter()).
					put(PARAMETER_2,multiValuedParameter()).
					build();
		return ImmutableQuery.create(parameterMap);
	}

	@Test(expected=NullPointerException.class)
	public void failOnCreationWithNullParameterMap() {
		ImmutableQuery.create(null);
	}

	@Test
	public void testGetAllParameterNames() {
		assertThat(sut().parameterNames(),contains(PARAMETER_1,PARAMETER_2));
	}

	@Test
	public void testHasAllParameterNames() {
		assertThat(sut().hasParameter(PARAMETER_1),equalTo(true));
		assertThat(sut().hasParameter(PARAMETER_2),equalTo(true));
	}

	@Test
	public void testHasNoOtherParameterNames() {
		assertThat(sut().hasParameter(UNEXISTING_PARAMETER),equalTo(false));
	}

	@Test
	public void testKeepsAllParameters() {
		assertThat(sut().getParameter(PARAMETER_1),sameInstance((Parameter)singleValuedParameter()));
		assertThat(sut().getParameter(PARAMETER_2),sameInstance((Parameter)multiValuedParameter()));
	}

	@Test
	public void testReturnsEmptyParametersForUnknownNames() {
		assertThat(sut().getParameter(UNEXISTING_PARAMETER),instanceOf(NullQueryParameter.class));
	}

}