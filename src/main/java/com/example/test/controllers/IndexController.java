package com.example.test.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.io.IOException;

@RestController
@EnableWebMvc
public class IndexController {

	@GetMapping("/")
	public String calculator() {
		try {
			String connUrl = "https://www.daum.net";
			Document doc = Jsoup.connect(connUrl).header("content-type", "html; charset=utf-8").get();
			String a = "<html><body>123456789</body></html>";
			String step2 = step2(a);
			System.out.println(step2);
			String step3 = step3(step2);
			System.out.println(step3);
			Map<String, String[]> step4 = step4(step3);
			System.out.println(step4);
			String step5 = step5(step4.get("alphabet"), step4.get("number"));
			System.out.println(step5);
			String step6 = step6(step5, 10);
			return step2(doc.toString());
		} catch (IOException e) {
			// Exp : Connection Fail
			System.out.print(e);
			e.printStackTrace();

		}

		return "index";
	}
	public String step2(String html) {
		return Jsoup.parse(html).text();
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
		System.out.println(Arrays.toString(numberArr));
		System.out.println(Arrays.toString(alphabetArr));
		Map<String, String[]> map = new HashMap<>();
		System.out.println(String.join("", alphabetArr));
		System.out.println(String.join("", numberArr));
		map.put("alphabet", alphabetArr);
		map.put("number", numberArr);
		return map;
	}

	public String step5(String[] alphabet, String[] number) {
		String[] arr = new String[alphabet.length + number.length];
		int maxLength = Math.max(alphabet.length, number.length);
		int arrayIndex = 0;
		System.out.println(alphabet.length);
		System.out.println(number.length);
		for (int i = 0; i < maxLength; i++) {
			if (alphabet.length > i) arr[arrayIndex++] = alphabet[i];
			if (number.length > i) arr[arrayIndex++] = number[i];
		}
		System.out.println(Arrays.toString(arr));

		return String.join("", arr);
	}

	public String step6(String text, int divideCount) {
		System.out.println(text.length());
		int remainderIndex = text.length() % divideCount;
		System.out.println(remainderIndex);
		System.out.println(text.substring(0, text.length() - remainderIndex));
		System.out.println(text.substring(text.length() - remainderIndex));
		return "";
	}

}
