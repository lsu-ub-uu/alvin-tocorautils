/*
 * Copyright 2019 Uppsala University Library
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
package se.uu.ub.cora.alvin.tocorautils.update;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import org.testng.annotations.Test;

import se.uu.ub.cora.javaclient.apptoken.AppTokenClientFactoryImp;
import se.uu.ub.cora.javaclient.cora.http.ApptokenBasedClient;
import se.uu.ub.cora.javaclient.rest.RestClientFactoryImp;

public class HistoricItemUpdaterRunnerTest {
	@Test
	public void testConstructorIsPrivate() throws NoSuchMethodException, IllegalAccessException,
			InvocationTargetException, InstantiationException {
		Constructor<HistoricItemUpdaterRunner> constructor = HistoricItemUpdaterRunner.class
				.getDeclaredConstructor();
		assertTrue(Modifier.isPrivate(constructor.getModifiers()));
		constructor.setAccessible(true);
		constructor.newInstance();
	}

	@Test
	public void testMainMethod() throws ClassNotFoundException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException, InstantiationException {
		String args[] = new String[] { "http://localhost:8080/apptoken", "someBaseUrl",
				"someUserId", "someAppToken",
				"se.uu.ub.cora.alvin.tocorautils.update.ClientUpdaterSpy" };
		HistoricItemUpdaterRunner.main(args);
		assertTrue(HistoricItemUpdaterRunner.historicItemUpdater instanceof ClientUpdaterSpy);

		ClientUpdaterSpy historicItemUpdater = (ClientUpdaterSpy) HistoricItemUpdaterRunner.historicItemUpdater;
		assertTrue(historicItemUpdater.getCoraClient() instanceof ApptokenBasedClient);
		ApptokenBasedClient coraClient = (ApptokenBasedClient) historicItemUpdater.getCoraClient();

		AppTokenClientFactoryImp appTokenClientFactory = (AppTokenClientFactoryImp) coraClient
				.getAppTokenClientFactory();
		assertEquals(appTokenClientFactory.getAppTokenVerifierUrl(),
				"http://localhost:8080/apptoken");

		RestClientFactoryImp restClientFactory = (RestClientFactoryImp) coraClient
				.getRestClientFactory();
		assertEquals(restClientFactory.getBaseUrl(), "someBaseUrl");

		assertEquals(coraClient.getUserId(), "someUserId");
		assertEquals(coraClient.getAppToken(), "someAppToken");

		assertTrue(historicItemUpdater.updateWasCalled);
	}
}
