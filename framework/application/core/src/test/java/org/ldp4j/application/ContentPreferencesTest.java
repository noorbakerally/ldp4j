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


import org.junit.Before;
import org.junit.Test;
import org.ldp4j.application.ContentPreferences.Preference;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class ContentPreferencesTest {

	private static final ContentPreferences OMIT_CONTAINMENT_TRIPLES = 
			ContentPreferences.
				builder().
					withOmit(Preference.CONTAINMENT_TRIPLES).
					build();

	private static final ContentPreferences OMIT_MEMBERSHIP_TRIPLES = 
			ContentPreferences.
				builder().
					withOmit(Preference.MEMBERSHIP_TRIPLES).
					build();

	private static final ContentPreferences INCLUDE_MINIMAL_CONTAINER= 
			ContentPreferences.
				builder().
					withInclude(Preference.MINIMAL_CONTAINER).
					build();

	private static final ContentPreferences INCLUDE_EMPTY_CONTAINER= 
			ContentPreferences.
				builder().
					withInclude(Preference.EMPTY_CONTAINER).
					build();

	private static final ContentPreferences INCLUDE_ALL = 
			ContentPreferences.
				builder().
					withInclude(Preference.CONTAINMENT_TRIPLES).
					withInclude(Preference.MEMBERSHIP_TRIPLES).
					build();

	private static final ContentPreferences OMIT_ALL = 
			ContentPreferences.
				builder().
					withOmit(Preference.CONTAINMENT_TRIPLES).
					withOmit(Preference.MEMBERSHIP_TRIPLES).
					build();

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testIsRequired$omit$contaimentTriples() throws Exception {
		assertThat(OMIT_CONTAINMENT_TRIPLES.isRequired(Preference.MEMBERSHIP_TRIPLES),equalTo(true));
		assertThat(OMIT_CONTAINMENT_TRIPLES.isRequired(Preference.CONTAINMENT_TRIPLES),equalTo(false));
	}

	@Test
	public void testIsRequired$omit$membershipTriples() throws Exception {
		assertThat(OMIT_MEMBERSHIP_TRIPLES.isRequired(Preference.MEMBERSHIP_TRIPLES),equalTo(false));
		assertThat(OMIT_MEMBERSHIP_TRIPLES.isRequired(Preference.CONTAINMENT_TRIPLES),equalTo(true));
	}

	@Test
	public void testIsRequired$omit$all() throws Exception {
		assertThat(OMIT_ALL.isRequired(Preference.MEMBERSHIP_TRIPLES),equalTo(false));
		assertThat(OMIT_ALL.isRequired(Preference.CONTAINMENT_TRIPLES),equalTo(false));
	}
	
	@Test
	public void testIsRequired$include$all() throws Exception {
		assertThat(INCLUDE_ALL.isRequired(Preference.MEMBERSHIP_TRIPLES),equalTo(true));
		assertThat(INCLUDE_ALL.isRequired(Preference.CONTAINMENT_TRIPLES),equalTo(true));
	}
	
	@Test
	public void testIsRequired$include$minimalContainer() throws Exception {
		assertThat(INCLUDE_MINIMAL_CONTAINER.isRequired(Preference.MEMBERSHIP_TRIPLES),equalTo(false));
		assertThat(INCLUDE_MINIMAL_CONTAINER.isRequired(Preference.CONTAINMENT_TRIPLES),equalTo(false));
		assertThat(INCLUDE_MINIMAL_CONTAINER.isRequired(Preference.MINIMAL_CONTAINER),equalTo(true));
		assertThat(INCLUDE_MINIMAL_CONTAINER.isRequired(Preference.EMPTY_CONTAINER),equalTo(true));
	}

	@Test
	public void testIsRequired$include$emptyContainer() throws Exception {
		assertThat(INCLUDE_EMPTY_CONTAINER.isRequired(Preference.MEMBERSHIP_TRIPLES),equalTo(false));
		assertThat(INCLUDE_EMPTY_CONTAINER.isRequired(Preference.CONTAINMENT_TRIPLES),equalTo(false));
		assertThat(INCLUDE_EMPTY_CONTAINER.isRequired(Preference.MINIMAL_CONTAINER),equalTo(true));
		assertThat(INCLUDE_EMPTY_CONTAINER.isRequired(Preference.EMPTY_CONTAINER),equalTo(true));
	}
}
