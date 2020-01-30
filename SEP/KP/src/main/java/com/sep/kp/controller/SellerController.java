package com.sep.kp.controller;

import com.sep.kp.model.DTO.CreateSellerDto;
import com.sep.kp.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private SellerService sellerService;

    @GetMapping
    public Model showPage(Model model) {
        model.addAttribute("someBean", new CreateSellerDto()); //assume SomeBean has a property called datePlanted
        return model;
    }

    @PostMapping()
    public String showPage(@RequestBody @ModelAttribute("someBean") CreateSellerDto bean, BindingResult errors, Model model) {

        this.sellerService.newSellerPaymentMethods(bean);
        System.out.println("Date planted: " + bean.getBankName()); //in reality, you'd use a logger instead :)
        return "redirect:https://localhost:4200";
    }

    @CrossOrigin
    @GetMapping(value = "/{sellerId}")
    public ModelAndView newSellerForm(@PathVariable Long sellerId) {
        Map<String, CreateSellerDto> someBean = new HashMap<>();

        CreateSellerDto createSellerDto = new CreateSellerDto();
        createSellerDto.setUserId(sellerId.toString());
        someBean.put("someBean", createSellerDto);


        return new ModelAndView("registerSeller", someBean);
//        return new RedirectView("registerSeller");
    }
}
