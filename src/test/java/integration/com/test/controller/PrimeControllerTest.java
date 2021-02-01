package integration.com.test.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prime.MainApplication;
import com.prime.vo.PrimeResopnseVO;

/**
 * @author missp
 *
 */
@SpringBootTest(classes = MainApplication.class)
@AutoConfigureMockMvc
public class PrimeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private CacheManager cacheManager;

	@Test
	public void validRangeJsonResponseTest() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/prime/{number}", "19")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(requestBuilder).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.Initials").value(19))
				.andExpect(jsonPath("$.Primes").value(Matchers.contains(2, 3, 5, 7, 11, 13, 17, 19)));

	}

	@Test
	public void produceXMLResponseTest() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/prime/{number}", "13")
				.contentType(MediaType.TEXT_HTML_VALUE).accept(MediaType.APPLICATION_XML_VALUE);

		mockMvc.perform(requestBuilder).andDo(MockMvcResultHandlers.print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_XML_VALUE))
				.andExpect(xpath("/primeResopnseVO/initials/text()").string(Matchers.equalTo("13")))
				.andExpect(xpath("/primeResopnseVO/primes/text()").nodeCount(6))
				.andExpect(xpath("/primeResopnseVO/primes[2]/text()").string("3"))
				.andExpect(xpath("/primeResopnseVO/primes[6]/text()").string("13"));
	}

	@Test
	public void invalidProducerTest() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/prime/{number}", "-10")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_FORM_URLENCODED_VALUE);

		mockMvc.perform(requestBuilder).andExpect(status().isNotAcceptable());
	}

	@Test
	public void validHeaderAlgoTypeTest() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/prime/{number}", "12")
				.header("algorithm-type", "naive").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(requestBuilder).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("Initials").value(12))
				.andExpect(jsonPath("Primes").value(Matchers.contains(2, 3, 5, 7, 11)));

	}

	@Test
	public void emptyHeaderAlgoTypeTest() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/prime/{number}", "11").header("algorithm-type", "")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(requestBuilder).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("Initials").value(11))
				.andExpect(jsonPath("Primes").value(Matchers.contains(2, 3, 5, 7, 11)));

	}

	@Test
	public void invalidHeaderAlgoTypeTest() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/prime/{number}", "15")
				.header("algorithm-type", "save").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(requestBuilder).andExpect(status().isBadRequest())
				.andExpect(jsonPath("errorCode").value("INVALID.ALGORITHM.FOUND"))
				.andExpect(jsonPath("errorMessage").value("No algorithm available for algo type : save"));
	}

	@Test
	public void invalidRangeTest() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/prime/{number}", "-10")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(requestBuilder).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errorCode").value("NON.NATURAL.NUMBER"))
				.andExpect(jsonPath("$.errorMessage").value("Number range should be positive."));
	}

	@Test
	public void badRequestTest() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/prime/{number}", "aa")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(requestBuilder).andExpect(status().isBadRequest());
	}

	@Test
	public void verifyCachingTest() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/prime/{number}", "25").header("algorithm-type", "")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
		// get the result form web response
		PrimeResopnseVO webResponse = new ObjectMapper().readValue(result.getResponse().getContentAsString(),
				PrimeResopnseVO.class);

		// retrieved the data from cache
		List<Integer> cache = cacheManager.getCache("primeList").get(25, List.class);

		// verify the cache
		assertTrue(webResponse.getPrimes().size() == cache.size());
		assertTrue(webResponse.getPrimes().containsAll(cache));
	}

}
