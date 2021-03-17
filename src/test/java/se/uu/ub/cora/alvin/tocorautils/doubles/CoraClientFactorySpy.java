/*
 * Copyright 2018 Uppsala University Library
 *
 * This file is part of Cora.
 *
 *     Cora is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Cora is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Cora.  If not, see <http://www.gnu.org/licenses/>.
 */
package se.uu.ub.cora.alvin.tocorautils.doubles;

import se.uu.ub.cora.javaclient.cora.CoraClient;
import se.uu.ub.cora.javaclient.cora.CoraClientFactory;

public class CoraClientFactorySpy implements CoraClientFactory {

	public CoraClientSpy factored;
	public String userId;
	public String appToken;

	public CoraClientFactorySpy() {
		// TODO Auto-generated constructor stub
	}

	public CoraClientFactorySpy(String appTokenVerifierUrl, String baseUrl) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public CoraClient factor(String userId, String appToken) {
		this.userId = userId;
		this.appToken = appToken;
		factored = new CoraClientSpy();
		return factored;
	}

	public static CoraClientFactory usingAppTokenVerifierUrlAndBaseUrl(String appTokenVerifierUrl,
			String baseUrl) {
		// TODO Auto-generated method stub
		return new CoraClientFactorySpy(appTokenVerifierUrl, baseUrl);
	}

	@Override
	public CoraClient factorUsingAuthToken(String authToken) {
		// TODO Auto-generated method stub
		return null;
	}

}
