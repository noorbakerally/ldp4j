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
 *   Artifact    : org.ldp4j.commons:ldp4j-commons-http:0.3.0-SNAPSHOT
 *   Bundle      : ldp4j-commons-http-0.3.0-SNAPSHOT.jar
 * #-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=#
 */
package org.ldp4j.http;

import static java.util.Objects.requireNonNull;

import java.nio.charset.Charset;
import java.util.Map.Entry;

public final class MediaTypes {

	private static final String REFERENCE_MEDIA_TYPE_CANNOT_BE_NULL = "Reference media type cannot be null";
	private static final String MEDIA_TYPE_CANNOT_BE_NULL = "Media type cannot be null";

	/**
	 * The key for the standard 'charset' media type parameter
	 */
	public static final String PARAM_CHARSET   = "charset";

	/**
	 * The key for the standard 'quality' media type parameter
	 */
	public static final String PARAM_QUALITY   = "q";

	/**
	 * The wildcard type/subtype
	 */
	public static final String WILDCARD_TYPE   = "*";

	private MediaTypes() {
	}

	/**
	 * Parse the given String into a single {@code MediaType}.
	 *
	 * @param mediaType
	 *            the string to parse
	 * @return the mime type
	 * @throws InvalidMediaTypeException
	 *             if the string cannot be parsed
	 */
	public static MediaType fromString(final String mediaType) {
		return ImmutableMediaType.fromString(mediaType);
	}

	/**
	 * Indicates whether the {@linkplain MediaType#type() type} is the
	 * wildcard character <code>&#42;</code>.
	 *
	 * @param mediaType
	 *            the instance to verify
	 * @return whether the type of the specified instance is a wildcard
	 * @throws NullPointerException
	 *             if the specified instance is null
	 */
	public static boolean isWildcardType(final MediaType mediaType) {
		requireNonNull(mediaType,MEDIA_TYPE_CANNOT_BE_NULL);
		return WILDCARD_TYPE.equals(mediaType.type());
	}

	/**
	 * Indicates whether the {@linkplain MediaType#subType() subtype} is the
	 * wildcard character <code>&#42;</code>.
	 *
	 * @param mediaType
	 *            the instance to verify
	 * @return whether the subtype of the specified instance is a wildcard
	 * @throws NullPointerException
	 *             if the specified instance is null
	 */
	public static boolean isWildcardSubType(final MediaType mediaType) {
		requireNonNull(mediaType,MEDIA_TYPE_CANNOT_BE_NULL);
		return WILDCARD_TYPE.equals(mediaType.subType());
	}

	/**
	 * Indicates whether the {@linkplain MediaType#suffix() suffix} is
	 * {@code null}.
	 *
	 * @param mediaType
	 *            the instance to verify
	 * @return {@code true} if the suffix of the specified instance is not
	 *         {@code null}; {@code false} otherwise.
	 * @throws NullPointerException
	 *             if the specified instance is null
	 */
	public static boolean isStructured(final MediaType mediaType) {
		requireNonNull(mediaType,MEDIA_TYPE_CANNOT_BE_NULL);
		return mediaType.suffix()!=null;
	}

	/**
	 * Indicate whether the former {@code MediaType} includes the latter.
	 * <p>
	 * For instance, {@code text/*} includes {@code text/plain} and
	 * {@code text/html}, and {@code application/*+xml} includes
	 * {@code application/soap+xml}, etc. This method is <b>not</b> symmetric.
	 *
	 * @param one
	 *            the reference media type with which to compare
	 * @param other
	 *            the media type to compare
	 * @return {@code true} if the reference media type includes the compared
	 *         media type; {@code false} otherwise
	 */
	public static boolean includes(final MediaType one, final MediaType other) {
		return areCompatible(one, other,false);
	}

	/**
	 * Indicate whether the former {@code MediaType} is compatible with the
	 * latter.
	 * <p>
	 * For instance, {@code text/*} is compatible with {@code text/plain},
	 * {@code text/html}, and vice versa. In effect, this method is similar to
	 * {@link #includes}, except that it <b>is</b> symmetric.
	 *
	 * @param one
	 *            the reference media type with which to compare
	 * @param other
	 *            the media type to compare
	 * @return {@code true} if both media types are compatible; {@code false}
	 *         otherwise
	 */
	public static boolean areCompatible(final MediaType one, final MediaType other) {
		return areCompatible(one,other,true);
	}

	/**
	 * Format the media type in a HTTP-header compliant manner using preferred
	 * format.
	 *
	 * @param mediaType
	 *            the media type to format
	 * @return the compliant representation of the media type
	 * @see <a href="https://tools.ietf.org/html/rfc7231#section-3.1.1.1">[RFC
	 *      7231] Hypertext Transfer Protocol (HTTP/1.1): Semantics and Content,
	 *      section 3.1.1.1</a>
	 */
	public static String toHeader(final MediaType mediaType) {
		requireNonNull(mediaType,REFERENCE_MEDIA_TYPE_CANNOT_BE_NULL);
		final StringBuilder builder=
			new StringBuilder().
				append(mediaType.type().toLowerCase()).
				append('/').
				append(mediaType.subType().toLowerCase());
		final String suffix=mediaType.suffix();
		if(suffix!=null) {
			builder.append('+').append(suffix.toLowerCase());
		}
		final Charset charset=mediaType.charset();
		if(charset!=null) {
			builder.append(";charset=").append(charset.name().toLowerCase());
		}
		for(Entry<String,String> entry:mediaType.parameters().entrySet()) {
			final String key=entry.getKey();
			if(isStandardParameter(key)) {
				continue;
			}
			builder.append(';').append(key.toLowerCase()).append('=').append(entry.getValue());
		}
		if(mediaType.hasWeight()) {
			builder.append(';').append("q=").append(mediaType.weight());
		}
		return builder.toString();
	}

	/**
	 * Check whether or not the specified parameter is a standard Media Type
	 * parameter
	 *
	 * @param parameter
	 *            the parameter to check
	 * @return {@code true} if the parameter is standard, or {@code false}
	 *         otherwise.
	 */
	public static boolean isStandardParameter(final String parameter) {
		return PARAM_CHARSET.equalsIgnoreCase(parameter) || PARAM_QUALITY.equalsIgnoreCase(parameter);
	}

	private static boolean areCompatible(final MediaType one, final MediaType other, boolean symmetric) {
		requireNonNull(one,REFERENCE_MEDIA_TYPE_CANNOT_BE_NULL);
		if(other==null) {
			return false;
		}
		if(!equalsIgnoreCase(one.suffix(), other.suffix())) {
			return false;
		}
		return haveCompatibleMediaRange(one, other, symmetric);
	}

	private static boolean haveCompatibleMediaRange(final MediaType one, final MediaType other, boolean symmetric) {
		if(isWildcardType(one)) {
			return true;
		}
		if(symmetric && isWildcardType(other)) {
			return true;
		}
		return
			one.type().equalsIgnoreCase(other.type()) &&
			haveCompatibleSubtype(one, other, symmetric);
	}

	private static boolean haveCompatibleSubtype(final MediaType one, final MediaType other, boolean symmetric) {
		if(isWildcardSubType(one)) {
			return true;
		}
		if(symmetric && isWildcardSubType(other)) {
			return true;
		}
		return one.subType().equalsIgnoreCase(other.subType());
	}

	private static boolean equalsIgnoreCase(String str, String anotherStr) {
		return str==null ? anotherStr==null : str.equalsIgnoreCase(anotherStr);
	}

}