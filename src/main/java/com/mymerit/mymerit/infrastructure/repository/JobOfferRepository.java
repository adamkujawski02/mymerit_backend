package com.mymerit.mymerit.infrastructure.repository;


import com.mymerit.mymerit.domain.entity.JobOffer;

import com.mymerit.mymerit.domain.models.OfferType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Range;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface JobOfferRepository extends MongoRepository<JobOffer, String> {

    Optional<JobOffer> findByIdAndOfferType(String id, OfferType offerTypr);

    Page<JobOffer> findByJobTitleContainingIgnoreCaseAndSalaryBetweenAndTaskRewardBetweenAndTaskClosesAtBetweenAndOfferType(
            String jobTitle,
            Range<Integer> salaryRange,
            Range<Integer> creditsRange,
            Range<Date> dateRange,
            OfferType offerType,
            Pageable pageable
    );

    JobOffer findByOfferType(OfferType offerType);
    Page<JobOffer> findByJobTitleContainingIgnoreCaseAndTaskAllowedLanguagesInIgnoreCaseAndSalaryBetweenAndTaskRewardBetweenAndTaskClosesAtBetweenAndOfferType(
            String jobTitle,
            Set<String> languages,
            Range<Integer> salaryRange,
            Range<Integer> creditsRange,
            Range<Date> dateRange,
            OfferType offerType,
            Pageable pageable
    );


}
