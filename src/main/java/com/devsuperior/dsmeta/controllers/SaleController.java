package com.devsuperior.dsmeta.controllers;

import com.devsuperior.dsmeta.dto.SummaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.services.SaleService;

import java.util.List;

@RestController
@RequestMapping(value = "/sales")
public class SaleController {

    @Autowired
    private SaleService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<SaleMinDTO> findById(@PathVariable Long id) {
        SaleMinDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping(value = "/report")
    public ResponseEntity<Page<SaleMinDTO>> findAllReport(@RequestParam(required = false) String minDate,
                                                          @RequestParam(required = false) String maxDate,
                                                          @RequestParam(name = "name", defaultValue = "") String name, Pageable pageable) {
        Page<SaleMinDTO> result = service.findAll(minDate, maxDate, name, pageable);
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/summary")
    public ResponseEntity<List<SummaryDTO>> summaryFindAll(@RequestParam(required = false) String minDate,
                                                           @RequestParam(required = false) String maxDate) {
        List<SummaryDTO> result = service.summaryFindAll(minDate, maxDate);
        return ResponseEntity.ok(result);
    }
}
