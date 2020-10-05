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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.IOException;

@RestController
@EnableWebMvc
public class IndexController {

	@GetMapping("/")
	public String index() {
		try {
			// 1. URL 선언
			String connUrl = "https://www.daum.net";
			// 2. HTML 가져오기
			Document doc = Jsoup.connect(connUrl).header("content-type", "html; charset=utf-8").get();

			String a = "<htmlbody><b>112002*b</b>1가A희<a>B1**e</a>aa<div>a</div>a</body></html>";
			System.out.println(html2text(a));
			System.out.println(step3(html2text(a)));
			System.out.println(step4(step3(html2text(a))));
			return html2text(doc.toString());
		} catch (IOException e) {
			// Exp : Connection Fail
			System.out.print(e);
			e.printStackTrace();

		}

		return "index";
	}
	public String html2text(String html) {
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
		// Arrays.sort(alphabetArr, String.CASE_INSENSITIVE_ORDER);
		Arrays.sort(alphabetArr, new Comparator<String>(){

			@Override
			public int compare(String o1, String o2) {
				Pattern upper = Pattern.compile("[^A-Z]");
				Pattern lower = Pattern.compile("[^a-z]");
				Boolean a = upper.matcher(o1).find();
				return 0;
			}

		});
		System.out.println(Arrays.toString(numberArr));
		System.out.println(Arrays.toString(alphabetArr));
		Map<String, String> map = new HashMap<>();
		map.put("alphabet", alphabet);
		map.put("number", number);
		return map;
	}

}
