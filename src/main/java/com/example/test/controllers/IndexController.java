package com.example.test.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.io.IOException;
import com.example.test.models.PostRequest;
import org.springframework.web.bind.annotation.RequestBody;
import javax.validation.Valid;
@RestController
@EnableWebMvc
public class IndexController {

	@PostMapping("/")
	public Map<String, String> calculator(@Valid @RequestBody PostRequest body) throws IOException {
		String connUrl = body.getUrl();
		Document doc = Jsoup.connect(connUrl).header("content-type", "html; charset=utf-8").get();

		String step2 = step2(doc.toString(), body.getType());

		String step3 = step3(step2);

		Map<String, String[]> step4 = step4(step3);

		String step5 = step5(step4.get("alphabet"), step4.get("number"));

		Map<String, String> step6 = step6(step5, Integer.valueOf(body.getGroupNumber()));

		return step6;
	}
	public String step2(String html, String type) {
		if (type == "text") return Jsoup.parse(html).text();
		return html;
	}

	public String step3(String text) {
		return text.replaceAll("[ !@#$%^&*(),.?\":{}|<>가-힣]", "");
	}

	public Map<String, String[]> step4(String text) {
		String number = text.replaceAll("[^0-9]", "");
		String alphabet = text.replaceAll("[^a-z^A-Z]", "");
		String[] numberArr = number.trim().length() == 0 ? new String[0] : number.split("");
		String[] alphabetArr = alphabet.trim().length() == 0 ? new String[0] : alphabet.split("");
		Arrays.sort(numberArr);
		Arrays.sort(alphabetArr, new Comparator<String>(){
			@Override
			public int compare(String o1, String o2) {
				double a = (Character.codePointAt(o1, 0) >= 97 ? Character.codePointAt(o1, 0) : Character.codePointAt(o1, 0) + 31.5);
				double b = (Character.codePointAt(o2, 0) >= 97 ? Character.codePointAt(o2, 0) : Character.codePointAt(o2, 0) + 31.5);
				return (int)((a - b) * 2);
			}

		});

		Map<String, String[]> map = new HashMap<>();

		map.put("alphabet", alphabetArr);
		map.put("number", numberArr);
		return map;
	}

	public String step5(String[] alphabet, String[] number) {
		String[] arr = new String[alphabet.length + number.length];
		int maxLength = Math.max(alphabet.length, number.length);
		int arrayIndex = 0;

		for (int i = 0; i < maxLength; i++) {
			if (alphabet.length > i) arr[arrayIndex++] = alphabet[i];
			if (number.length > i) arr[arrayIndex++] = number[i];
		}

		return String.join("", arr);
	}

	public Map<String, String> step6(String text, int divideCount) {
		int remainderIndex = text.length() % divideCount;
		Map<String, String> map = new HashMap<>();
		map.put("share", text.substring(0, text.length() - remainderIndex));
		map.put("remainder", text.substring(text.length() - remainderIndex));
		return map;
	}

}
