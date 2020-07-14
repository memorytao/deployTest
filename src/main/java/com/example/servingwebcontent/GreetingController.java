package com.example.servingwebcontent;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

@Controller
public class GreetingController {

	@GetMapping("/greeting")
	public String greeting(@RequestParam(name = "country", required = false, defaultValue = "All") String country,
			Model model) {

		try {
			HttpResponse<String> response = Unirest.get("https://coronavirus-19-api.herokuapp.com/countries")
					.asString();

			JSONArray jsonArr = new JSONArray(response.getBody());

			if (country == null || country.equals("All")) {
				model.addAttribute("country", response.getBody());
			} else {

				for (int i = 0; i < jsonArr.length(); i++) {
					JSONObject array_element = (JSONObject) jsonArr.get(i);

					if (array_element.getString("country").equals(country)) {
						model.addAttribute("country", array_element.toString());
						break;
					}
					continue;
				}
			}

		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "greeting";
	}

}
