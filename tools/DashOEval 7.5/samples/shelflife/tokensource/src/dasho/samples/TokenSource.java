package dasho.samples;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

class TokenSource {
	static Reader getToken() {
		final InputStream stream = TokenSource.class.getClassLoader().getResourceAsStream("expiry.dat");
		if(stream == null){
			throw new RuntimeException("Could not read token expiry.dat");
		}
		return new InputStreamReader(stream);
	}
}