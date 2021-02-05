package com.techbirdssolutions.customerdisplay.controller;


import com.techbirdssolutions.customerdisplay.service.DisplayService;
import com.techbirdssolutions.customerdisplay.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/display")
@CrossOrigin("*")
public class DisplayRESTController {

    @Autowired
    DisplayService displayService;

    @Autowired
    WordService wordService;

    @Value("${nextcustomerline1}")
    private String nextcustomerline1;

    @Value("${nextcustomerline2}")
    private String nextcustomerline2;

    @PostMapping("/show/item")
    public ResponseEntity<?> showitem(@RequestParam String display,@RequestParam String pname,
                                      @RequestParam String price, @RequestParam String qty,
                                      @RequestParam String total,@RequestParam String grandtotal){
        displayService.findport(display);
        displayService.writeTwoLine(
                wordService.upperLine(pname,price,qty,total),
                wordService.downLine("Total",grandtotal)
        );
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping("/clear")
    public ResponseEntity<?> displayClear(@RequestParam String display){
        displayService.findport(display);
        displayService.writeTwoLine(
                this.nextcustomerline1,
                wordService.downLine(this.nextcustomerline2)
        );
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping("/show/balance")
    public ResponseEntity<?> showbalance(@RequestParam String display,@RequestParam String total,
                                      @RequestParam String cash, @RequestParam String balance){
        displayService.findport(display);
        displayService.writeTwoLine(
                "Total : "+total+"  Cash : "+cash,
                wordService.downLine("Balance",balance)
        );
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping("/reset")
    public ResponseEntity<?> displayreset(@RequestParam String display){
        displayService.findport(display);
        displayService.clear();
        displayService.resetdisplays();
        return new ResponseEntity<>(null, HttpStatus.OK);
    }


}
