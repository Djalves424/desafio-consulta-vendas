package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.devsuperior.dsmeta.dto.SummaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class SaleService {

    @Autowired
    private SaleRepository repository;

    public SaleMinDTO findById(@PathVariable Long id) {
        Optional<Sale> result = repository.findById(id);
        Sale entity = result.get();
        return new SaleMinDTO(entity);
    }

    public Page<SaleMinDTO> findAll(@PathVariable Pageable pageable) {
        Page<Sale> result = repository.findAll(pageable);
        return result.map(x -> new SaleMinDTO(x));
    }

    public List<SummaryDTO> summaryFindAll(String minDate, String maxDate) {
        try {
            LocalDate dateInitial = null;
            LocalDate dateFim = null;
            if (minDate != null) {
                dateInitial = LocalDate.parse(minDate);
                minDate = dateInitial.toString();
            } else {
                dateInitial = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault()).minusYears(1L);
                minDate = dateInitial.toString();
            }

            if (maxDate != null) {
                dateFim = LocalDate.parse(maxDate);
                maxDate = dateFim.toString();
            } else {
                dateFim = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
                maxDate = dateFim.toString();

            }
            List<SummaryDTO> list = repository.summaryFindAll(LocalDate.parse(minDate), LocalDate.parse(maxDate));
            List<SummaryDTO> dto = list.stream().map(x -> new SummaryDTO(x)).collect(Collectors.toList());

            return dto;

        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Data informada é invalida");
        }
    }
}
