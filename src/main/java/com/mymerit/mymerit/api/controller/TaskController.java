package com.mymerit.mymerit.api.controller;


import com.mymerit.mymerit.api.payload.request.SolutionRequest;
import com.mymerit.mymerit.api.payload.response.UserTaskResponse;
import com.mymerit.mymerit.domain.entity.Solution;
import com.mymerit.mymerit.domain.entity.Task;

import com.mymerit.mymerit.domain.service.SolutionService;
import com.mymerit.mymerit.domain.service.TaskService;

import com.mymerit.mymerit.domain.service.UserDetailsImpl;
import com.mymerit.mymerit.domain.service.UserDetailsServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
public class TaskController {
    TaskService taskService;
    UserDetailsServiceImpl userDetailsService;
    SolutionService solutionService;
    TaskController(TaskService taskService, UserDetailsServiceImpl userDetailsService, SolutionService solutionService){
        this.taskService = taskService;
        this.userDetailsService = userDetailsService;
        this.solutionService = solutionService;
    }

    @GetMapping("/task/{id}")
    ResponseEntity<UserTaskResponse> getTaskById(@PathVariable String id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        String userId = userDetails.getId();
        List<Solution> solutionList = solutionService.getSolutionsForUser(userId);

        Solution foundSolution = solutionList.stream()
                .filter(task -> task.getTaskId().equals(id))
                .findFirst()
                .orElse(null);

        Optional<Task> optionalTask = taskService.findById(id);
        Task foundTask = optionalTask.get();

        UserTaskResponse taskResponse = new UserTaskResponse(
                foundTask.getId(),
                foundTask.getTopic(),
                foundTask.getDescription(),
                foundTask.getReleaseDate(),
                foundTask.getExpiryDate(),
                foundTask.getReward(),
                foundTask.getCompany(),
                foundTask.getAllowedTechnologies(),
                foundSolution,
                foundTask.getTimeLeft()
        );


        return ResponseEntity.ok().body(taskResponse);
    }

    @GetMapping("/task/{taskId}/solution/{solutionId}")
    ResponseEntity<Solution> getSolutionById(@PathVariable String taskId, @PathVariable String solutionId){
        return taskService.findSolutionById(taskId,solutionId).map(solution -> ResponseEntity.ok().body(solution))
                .orElse(ResponseEntity.notFound().build());
    }


   /* @GetMapping("/{id}/solutions")
    public ResponseEntity<List<Solution>> getSolutions(@PathVariable String id) {
        List<Solution> solutions = taskService.getSolutions(id).map(solution -> ResponseEntity.ok().body(solution))
                .orElse(ResponseEntity.notFound().build());
        return ResponseEntity.ok(solutions);
    }

*/

  /*  @GetMapping("/{id}/solutions")
    public ResponseEntity<List<Solution>> findSolutions(@PathVariable String taskId) {
        List<Task> tasksWithSolutions = taskService.getSolutions(taskId)
                .stream()
                .map(Solution::getSolution)
                .collect(Collectors.toList());

        return ResponseEntity.ok(tasksWithSolutions);
    }
*/

    @DeleteMapping("/task/{id}")
    ResponseEntity<Void> deleteTaskById(@PathVariable String id) {
        if (taskService.deleteById(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/tasks")
    public ResponseEntity<List<Task>> getTasks(
            @RequestParam(required = false) Collection<List<String>> languages,
            @RequestParam(defaultValue = "0") Integer minCredits,
            @RequestParam(defaultValue = "9999") Integer maxCredits,
            @RequestParam(defaultValue = "asc") String order,
            @RequestParam(defaultValue = "0") int page) {

        Page<Task> taskPage;


        System.out.println(languages);
        System.out.println(minCredits);
        System.out.println(maxCredits);
        System.out.println(order);

        if ("desc".equals(order)) {
            taskPage = taskService.getTasksByOrderDesc(languages, minCredits, maxCredits, PageRequest.of(page, 10));
        } else if ("asc".equals(order)) {
            taskPage = taskService.getTasksByOrderAsc(languages, minCredits, maxCredits, PageRequest.of(page, 10));
        } else {
            taskPage = taskService.getTasks(languages, minCredits, maxCredits, PageRequest.of(page, 10));
        }

        List<Task> tasks = taskPage.getContent();
        return ResponseEntity.ok(tasks);
    }

    @PostMapping("/task/{id}/solution")
    public ResponseEntity<Solution> addSolutionToTask(@PathVariable String id, @RequestBody SolutionRequest solution, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String userId = userDetails.getId();
        //jakies gety
        Solution solutionRes = new Solution(id, userId,solution.getFiles());
        return ResponseEntity.ok(solutionRes);
    }

    @PostMapping("/task")
    public ResponseEntity<Task> addTask(@RequestBody Task task){
       return  ResponseEntity.ok(taskService.addTask(task));
    }
}
