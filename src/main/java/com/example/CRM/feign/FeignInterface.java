package com.example.CRM.feign;

import com.example.CRM.dto.TicketStoreDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url = "http://10.20.5.50:8083",value = "email")
public interface FeignInterface {
    @PostMapping("/email/ticketStore")
    Boolean storeTicket(@RequestBody TicketStoreDTO ticketStoreDTO);

    @PostMapping("email/rejectTicket")
    Boolean rejectTicket(@RequestParam("ticketId") String ticketId,@RequestParam("time") Long time);
}
