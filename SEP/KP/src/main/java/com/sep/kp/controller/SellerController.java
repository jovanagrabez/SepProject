package com.sep.kp.controller;

import com.sep.kp.model.DTO.CreateSellerDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;
import java.util.Map;

@Controller
@CrossOrigin("https://localhost:8762")

@RequestMapping(value = "/api/seller")
public class SellerController {

    @GetMapping
    public Model showPage(Model model) {
        model.addAttribute("someBean", new CreateSellerDto()); //assume SomeBean has a property called datePlanted
        return model;
    }

    @PostMapping()
    public String showPage(@RequestBody @ModelAttribute("someBean") CreateSellerDto bean, BindingResult errors, Model model) {

        System.out.println("Date planted: " + bean.getBankName()); //in reality, you'd use a logger instead :)
        return "redirect:someOtherPage";
    }

    @CrossOrigin
    @GetMapping(value = "/{sellerId}")
    public ModelAndView newSellerForm(@PathVariable Long sellerId) {
        Map<String, CreateSellerDto> someBean = new HashMap<>();
//        someBean.put("merchantId", "");
//        someBean.put("bankName", "");
//        someBean.put("clientId", "");
//        someBean.put("clientSecret", "");
//        someBean.put("bitcoinToken", "");
        someBean.put("someBean", new CreateSellerDto());


        return new ModelAndView("registerSeller", someBean);
//        return new RedirectView("registerSeller");
    }
}
