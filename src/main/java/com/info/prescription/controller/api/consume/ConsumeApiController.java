package com.info.prescription.controller.api.consume;

import com.info.prescription.viewModel.api.MinConceptItem;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping(value = "/")
public class ConsumeApiController {
    private final String baseUrl = "https://rxnav.nlm.nih.gov/REST/interaction/interaction.json?rxcui=341248";

    @RequestMapping(value = "/getMinConceptItem/list")
    public String getMinConceptItem(Model model) throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        URI uri = new URI(baseUrl);
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-COM-PERSIST", "true");
        headers.set("X-COM-LOCATION", "USA");

        try {
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
            ResponseEntity<String> myResult = restTemplate.exchange(uri, HttpMethod.GET, null, String.class);
//          Map<String, String> map = restTemplate.getForObject(uri, Map.class);

            String[] str = myResult.getBody().split("]");
            String v = str[4];
            String[] rt = v.split("}");
            String dd = rt[4];
            String[] rtd = dd.split(",");
            String name = dd.split(",")[2].split(":")[1];
            int nameLength = name.length();
            String tty = dd.split(",")[3].split(":")[1];
            int ttyLength = tty.length();

            String rxcui = dd.split(",")[1].split(":")[2];
            int rxcuiLength = rxcui.length();

            List<MinConceptItem> minConceptItemList = new ArrayList<>();
            MinConceptItem minConceptItem = new MinConceptItem();
            minConceptItem.setRxcui(rxcui.substring(1, rxcuiLength - 1));
            minConceptItem.setName(name.substring(1, nameLength - 1));
            minConceptItem.setTty(tty.substring(1, ttyLength - 1));
            minConceptItemList.add(minConceptItem);
            model.addAttribute("minConceptItemList", minConceptItemList);

            return "api_response/api_response";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Api not reachable");
            return "api_response/api_response";

        }
    }
}
