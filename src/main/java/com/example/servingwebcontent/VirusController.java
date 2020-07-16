package com.example.servingwebcontent;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;


@RestController
@RequestMapping("/api")
public class VirusController {
	

	@CrossOrigin(origins = "${fix.cores}")
	@GetMapping("/world")
	public String getAllContry(@RequestParam(defaultValue = "All", required = false, name = "name") String country,
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
				}
			}

		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (String) model.getAttribute("country");
	}


	@GetMapping("/country")
	public String getByCountry(@RequestParam(name = "name", required = false, defaultValue = "All") String country,
			Model model) {
		getAllContry(country, model);
		return (String) model.getAttribute("country");
	}

	@GetMapping("/index")
	String index() {
		return "index";
	}
}
