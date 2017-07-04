package tp.paw.khet.webapp.auth;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenHandler {

	@Autowired
	private String tokenSigningKey;

	@Autowired
	private UserDetailsService userDetailsService;

	public UserDetails parseUserFromToken(final String token) {
		final String username = Jwts.parser()
				.setSigningKey(tokenSigningKey)
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
		return userDetailsService.loadUserByUsername(username);
	}

	public String createTokenForUser(final String username) {
		return Jwts.builder()
				.setId(UUID.randomUUID().toString())
				.setSubject(username)
				.signWith(SignatureAlgorithm.HS512, tokenSigningKey)
				.compact();
	}
}
