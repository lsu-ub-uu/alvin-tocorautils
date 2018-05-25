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
package se.uu.ub.cora.alvin.tocorautils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import se.uu.ub.cora.client.CoraClientConfig;

public class CountryFromDbToCoraServer {

	static FromDbToCoraFactory fromDbToCoraFactory = null;

	public static void main(String[] args) {

		try {
			String fromDbToCoraFactoryClassName = getFromDbToCoraFactoryClassName(args);
			fromDbToCoraFactory = tryToCreateCoraClientFactory(fromDbToCoraFactoryClassName);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}

		CoraClientConfig coraClientConfig = createCoraClientConfig(args);
		DbConfig dbConfig = createDbConfig(args);
		String coraClientFactoryClassName = "se.uu.ub.cora.client.CoraClientFactoryImp";
		CountryFromDbToCora countryFromDbToCora = fromDbToCoraFactory
				.factorForCountryItems(coraClientFactoryClassName, coraClientConfig, dbConfig);
		ImportResult importResult = countryFromDbToCora.importCountries();
		if (!importResult.listOfFails.isEmpty()) {
			for (String fail : importResult.listOfFails) {
				// TODO: Join strings
				throw new RuntimeException(importResult.listOfFails.get(0));
			}
		}

	}

	private static String getFromDbToCoraFactoryClassName(String[] args) {
		return args[0];
	}

	private static FromDbToCoraFactory tryToCreateCoraClientFactory(
			String fromDbToCoraFactoryClassName)
			throws NoSuchMethodException, ClassNotFoundException, IllegalAccessException,
			InvocationTargetException, InstantiationException {
		Constructor<?> constructor = Class.forName(fromDbToCoraFactoryClassName).getConstructor();
		return (FromDbToCoraFactory) constructor.newInstance();
	}

	private static CoraClientConfig createCoraClientConfig(String[] args) {
		String userId = args[1];
		String appToken = args[2];
		String appTokenVerifierUrl = args[3];
		String coraUrl = args[4];
		return new CoraClientConfig(userId, appToken, appTokenVerifierUrl, coraUrl);
	}

	private static DbConfig createDbConfig(String[] args) {
		String dbUserId = args[5];
		String dbPassword = args[6];
		String dbUrl = args[7];
		return new DbConfig(dbUserId, dbPassword, dbUrl);
	}

}
