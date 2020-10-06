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
	public String index() {
		try {
			String connUrl = "https://www.daum.net";
			Document doc = Jsoup.connect(connUrl).header("content-type", "html; charset=utf-8").get();
			String a = "<htmlbody><b>112002*b</b>1가A희<a>B1**e</a>aa<div>a</div>a</body></html>";
			System.out.println(step2(a));
			System.out.println(step3(step2(a)));
			System.out.println(step4(step3(step2(a))));
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

	public Map<String, String> step4(String text) {
		String number = text.replaceAll("[^0-9]", "");
		String alphabet = text.replaceAll("[^a-z^A-Z]", "");
		String[] numberArr = number.split("");
		String[] alphabetArr = alphabet.split("");
		System.out.println(Arrays.toString(numberArr));
		System.out.println(Arrays.toString(alphabetArr));
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
		Map<String, String> map = new HashMap<>();
		map.put("alphabet", String.join("", alphabetArr));
		map.put("number", String.join("", numberArr));
		return map;
	}

	public String step5(String alphabet, String number) {
		return "";
	}

}
