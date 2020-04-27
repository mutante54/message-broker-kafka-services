package br.example.net.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.example.net.exception.HttpRequestUtilsException;

public class HttpRequestUtils<T extends Serializable, V> {

	private String url;

	public HttpRequestUtils(String url) {
		this.url = url;
	}

	/**
	 * Executa um request HTTP [POST] com um objeto serializável (Json) no
	 * RequestBody
	 * 
	 * @param obj          Objeto serializável a ser enviado no corpo do request
	 *                     (obrigatório)
	 * @param headerParams Atributos adicionais do header ou <code>null</code> caso
	 *                     não seja necessário
	 * @return Objeto serializável de retorno (se houver)
	 * @throws HttpRequestUtilsException
	 */
	public V post(T t, HashMap<String, String> headerParams) throws HttpRequestUtilsException {

		try {

			URL url = new URL(this.url);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json; utf-8");
			con.setRequestProperty("Accept", "application/json");

			if (headerParams != null) {
				headerParams.forEach((k, v) -> {
					con.setRequestProperty(k, v);
				});
			}

			con.setDoOutput(true);

			ObjectMapper mapper = new ObjectMapper();

			// Object to JSON in String
			String jsonInString = mapper.writeValueAsString(t);

			try (OutputStream os = con.getOutputStream()) {
				byte[] input = jsonInString.getBytes("utf-8");
				os.write(input, 0, input.length);
			}

			int status = con.getResponseCode();

			if (status != HttpURLConnection.HTTP_OK) {
				throw new HttpRequestUtilsException(
						"Ocorreu um erro ao realizar a requisição para [ " + this.url + " ]");
			}

			try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
				StringBuilder response = new StringBuilder();
				String responseLine = null;
				while ((responseLine = br.readLine()) != null) {
					response.append(responseLine.trim());
				}

				br.close();
				con.disconnect();

				if (!StringUtils.isEmpty(response.toString())) {
					// JSON from String to Object
					TypeReference<V> ref = new TypeReference<V>() {
					};
					return (V) mapper.readValue(response.toString(), ref);
				}

				return null;

			}

		} catch (Exception e) {
			throw new HttpRequestUtilsException("Ocorreu um erro ao realizar a requisição para [ " + this.url + " ]", e);
		}
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
