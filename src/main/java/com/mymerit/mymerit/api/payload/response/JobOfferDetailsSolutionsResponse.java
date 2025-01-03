package com.mymerit.mymerit.api.payload.response;


import com.mymerit.mymerit.domain.entity.Feedback;
import com.mymerit.mymerit.domain.entity.JobOffer;
import com.mymerit.mymerit.domain.entity.Solution;
import com.mymerit.mymerit.domain.entity.Task;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@Data
public class JobOfferDetailsSolutionsResponse extends JobOfferDetailsResponse{
    private List<Solution> solutions; // id

    public JobOfferDetailsSolutionsResponse(JobOffer jobOffer) {
        super.setId(jobOffer.getId());
        super.setJobTitle(jobOffer.getJobTitle());
        super.setDescription(jobOffer.getDescription());
        super.setRequiredSkills(jobOffer.getRequiredSkills());
        super.setPreferredSkills(jobOffer.getPreferredSkills());
        super.setWorkLocations(jobOffer.getWorkLocations());
        super.setTechnologies(jobOffer.getTechnologies());
        super.setCompany(jobOffer.getCompany());
        super.setTask(createTaskResponse(jobOffer.getTask()));
        super.setSalary(jobOffer.getSalary());
        super.setExperience(jobOffer.getExperience());
        super.setEmploymentType(jobOffer.getEmploymentType());
        super.setOpensAt(jobOffer.getTask().getOpensAt());
        super.setClosesAt(jobOffer.getTask().getClosesAt());
        super.setStatus(jobOffer.getTask().getStatus());

        this.solutions = jobOffer.getTask().getSolutions().stream().sorted(Comparator.comparing(Solution::getSubmitDate).reversed()).toList();
    }

    public  UserTaskDetailsResponse createTaskResponse(Task task){
        return new UserTaskDetailsResponse(
                task.getId(),
                task.getTitle(),
                task.getInstructions(),
                task.getOpensAt(),
                task.getClosesAt(),
                task.getReward(),
                task.getAllowedLanguages(),
                task.getMemoryLimit(),
                task.getTimeLimit(),
                task.getStatus(),
                null,
                null,
                task.getTemplateFiles(),
                task.getTests()
        );
    }
}
