package com.example.test.controllers;

import net.minidev.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.example.test.models.PostRequest;
import java.sql.Array;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@DisplayName("IndexController Test")
public class IndexControllerTest extends AbstractControllerTest {

	@Autowired
	private IndexController indexController;

	@Override
	protected Object controller() {
		return indexController;
	}

	@Test
	@DisplayName("test1")
	public void test1() throws Exception {
		PostRequest request = new PostRequest();
		request.setUrl("http://naver.com");
		request.setType("html");
		request.setGroupNumber("3");

		ResultActions actions = mockMvc.perform(post("/")
			.contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
			.content(new ObjectMapper().writeValueAsString(request))
			);
		actions
		.andExpect(status().isOk())
		;
		String response = actions.andReturn().getResponse().getContentAsString();
		ObjectMapper mapper = new ObjectMapper();
		JSONObject json = mapper.readValue(response, JSONObject.class);
		String share = json.getAsString("share");
		String remainder = json.getAsString("remainder");
		assertTrue(share.length() % 3 == 0);
		assertTrue(remainder.length() < 3);
	}

	@Test
	@DisplayName("test2")
	public void test2() {
		String html = "<html><body><div>asd</div></body></html>";
		String type = "text";
		String result = indexController.step2(html, type);
		assertEquals(result, "asd");
	}

	@Test
	@DisplayName("test3")
	public void test3() {
		String text = "asd123%^#$%#$%가나다";
		String result = indexController.step3(text);
		assertEquals(result, "asd123");
	}

	@Test
	@DisplayName("test4")
	public void test4() {
		String text = "AAA123BBab876";
		Map<String, String[]> result = indexController.step4(text);
		String[] numberArr = result.get("number");
		String[] alphabetArr = result.get("alphabet");
		assertEquals(String.join("", numberArr), "123678");
		assertEquals(String.join("", alphabetArr), "AAAaBBb");
	}

	@Test
	@DisplayName("test5")
	public void test5() {
		String[] number = {"1","2","3","4","5"};
		String[] alphabet = {"A", "a", "B", "b"};
		String result = indexController.step5(alphabet, number);
		assertEquals(result, "A1a2B3b45");
	}

	@Test
	@DisplayName("test6")
	public void test6() {
		String text = "A1a2B3b456";
		int devideCount = 3;
		Map<String, String> result = indexController.step6(text, devideCount);
		String share = result.get("share");
		String remainder = result.get("remainder");
		assertEquals(share, "A1a2B3b45");
		assertEquals(remainder, "6");
	}

}