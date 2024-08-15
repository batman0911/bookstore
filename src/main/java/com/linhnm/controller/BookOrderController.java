package com.linhnm.controller;

import com.linhnm.common.Context;
import com.linhnm.common.response.CommonResponse;
import com.linhnm.model.dto.BookOrder;
import com.linhnm.service.BookOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Created by linhnm on August 2024
 */
@RestController
@RequestMapping("/api/v${api.version}/book-order")
@Slf4j
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('USER')")
public class BookOrderController {
    private final BookOrderService bookOrderService;

    @PostMapping("/order/{code}")
    public CommonResponse<BookOrder.Response> orderBook(
            @PathVariable String code, @RequestAttribute(name = "ctx") Context context) {
        BookOrder.Response response = bookOrderService.orderBook(new BookOrder.Request(code, context.getUsername()));
        return CommonResponse.of(response);
    }

    @GetMapping("/order/payment-qr")
    public CommonResponse<String> getPaymentQRCode() {
        return CommonResponse.of("https://cache.giaohangtietkiem.vn/d/0f8cf05cc9a24b23dc597e866c682105.jpg");
    }
}
