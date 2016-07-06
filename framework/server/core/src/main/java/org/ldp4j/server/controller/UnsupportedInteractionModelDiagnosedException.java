/**
 * #-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=#
 *   This file is part of the LDP4j Project:
 *     http://www.ldp4j.org/
 *
 *   Center for Open Middleware
 *     http://www.centeropenmiddleware.com/
 * #-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=#
 *   Copyright (C) 2014-2016 Center for Open Middleware.
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
 *   Artifact    : org.ldp4j.framework:ldp4j-server-core:0.2.1
 *   Bundle      : ldp4j-server-core-0.2.1.jar
 * #-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=#
 */
package org.ldp4j.server.controller;

import javax.ws.rs.core.Response.Status;

import org.ldp4j.application.engine.context.UnsupportedInteractionModelException;

/**
 * According to the LDP specification (see section 5.2.3.4) If any requested
 * interaction model cannot be honored, the server must fail the request.
 * However, the specification does not mandate how the server has to fail. The
 * most sensible response status code is one that makes clear that the problem
 * is on the client side and that the user should not retry the operation. That
 * is, return a BAD REQUEST error.
 */
public class UnsupportedInteractionModelDiagnosedException extends DiagnosedException {

	private static final long serialVersionUID = -2989547588179065603L;

	public UnsupportedInteractionModelDiagnosedException(OperationContext context, UnsupportedInteractionModelException cause) {
		super(
			context,
			cause,
			Diagnosis.
				create().
				statusCode(Status.BAD_REQUEST).
				diagnostic(cause.getMessage()).
				mandatory(true));
	}

}