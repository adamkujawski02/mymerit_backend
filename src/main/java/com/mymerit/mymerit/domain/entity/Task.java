package com.mymerit.mymerit.domain.entity;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.data.annotation.AccessType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.springframework.data.annotation.AccessType.Type.PROPERTY;

@Data
@Document("tasks")
public class Task {
    @Id
    private String id;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Instructions are required")
    private String instructions;

    @NotNull(message = "Opening time is required")
    private LocalDateTime opensAt;

    @NotNull(message = "Closing time is required")
    private LocalDateTime closesAt;

    @NotNull(message = "Reward is required")
    private Integer reward;

    @NotEmpty(message = "Allowed languages are required")
    private Set<String> allowedLanguages;

    private List<Solution> solutions = new ArrayList<>();

    public Task(String title, String instructions, LocalDateTime opensAt, LocalDateTime closesAt, Integer reward, Set<String> allowedLanguages) {
        this.title = title;
        this.instructions = instructions;
        this.opensAt = opensAt;
        this.closesAt = closesAt;
        this.reward = reward;
        this.allowedLanguages = allowedLanguages;
    }

    public Integer getSolutionCount() {
        return solutions.size();
    }

    //@AccessType(PROPERTY)
    public TaskStatus getStatus() {
        LocalDateTime now = LocalDateTime.now();

        if (this.getClosesAt().isBefore(now)) {
            return TaskStatus.EXPIRED;
        } else if (this.getOpensAt().isAfter(now)) {
            return TaskStatus.NOT_YET_OPEN;
        } else {
            return TaskStatus.OPEN;
        }
    }

    public void addSolution(Solution solution){
        if(getStatus() == TaskStatus.OPEN) {
            solutions.add(solution);
        }
    }
}
