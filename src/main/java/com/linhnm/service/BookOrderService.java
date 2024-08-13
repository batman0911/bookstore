package com.linhnm.service;

import com.linhnm.model.dto.BookOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Created by linhnm on August 2024
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class BookOrderService {
    public BookOrder.Response orderBook(BookOrder.Request request) {
        /**
         * This method is used to book an order
         * create transaction with status created
         * create transaction log
         */
        log.info("Order book with payment code: {}", request.paymentCode());
        return new BookOrder.Response(1L, "SUCCESS");
    }
}
