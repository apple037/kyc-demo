package com.wanda.kyc.utils;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.wanda.kyc.dto.GoogleLoginDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;


@Component
public class GoogleUtil {

	@Value("${google.client-id:null}")
	private String clientId;

	public GoogleLoginDto verify(String idTokenString) {
		try {
			GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
					// Specify the CLIENT_ID of the app that accesses the backend:
					.setAudience(Collections.singletonList(clientId))
					// Or, if multiple clients access the backend:
					//.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
					.build();

			// (Receive idTokenString by HTTPS POST)

			GoogleIdToken idToken = verifier.verify(idTokenString);
			if (idToken != null) {
				GoogleIdToken.Payload payload = idToken.getPayload();

				// Print user identifier
				String userId = payload.getSubject();
				System.out.println("User ID: " + userId);

				// Get profile information from payload
				String email = payload.getEmail();
				boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
				String name = (String) payload.get("name");
				String pictureUrl = (String) payload.get("picture");
				String locale = (String) payload.get("locale");
				String familyName = (String) payload.get("family_name");
				String givenName = (String) payload.get("given_name");

				// Use or store profile information
				// ...
				return GoogleLoginDto.builder()
						.email(email)
						.emailVerified(emailVerified)
						.name(name)
						.build();
			} else {
				System.out.println("Invalid ID token.");
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}
}
