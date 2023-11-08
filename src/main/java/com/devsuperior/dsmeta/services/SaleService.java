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

    public Page<SaleMinDTO> findAll(String minDate, String maxDate, String name, Pageable pageable) {

        try {
            LocalDate startDate = null;
            LocalDate endDate = null;

            if (minDate != null) {
                startDate = LocalDate.parse(minDate);
                minDate = startDate.toString();
            } else {
                startDate = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault()).minusYears(1L);
                minDate = startDate.toString();
            }

            if (maxDate != null) {
                endDate = LocalDate.parse(maxDate);
                maxDate = endDate.toString();
            } else {
                endDate = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
                maxDate = endDate.toString();
            }

            Page<Sale> result = repository.findAll(LocalDate.parse(minDate),
                    LocalDate.parse(maxDate), name,
                    pageable);
            Page<SaleMinDTO> dto = result.map(x -> new SaleMinDTO(x));

            return dto;

        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Data informada é invalida");
        }
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
