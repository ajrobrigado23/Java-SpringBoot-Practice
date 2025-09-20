package com.luv2code.springboot.cruddemo.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.luv2code.springboot.cruddemo.entity.Employee;
import com.luv2code.springboot.cruddemo.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {

    // Inject the Service Layer
    private EmployeeService employeeService;

    // Inject the object mapper library
    private ObjectMapper objectMapper;

    // quick and dirty: inject employee dao (use constructor injection)
    // 'refactor the code to inject the employee service'
    public EmployeeRestController(EmployeeService employeeService, ObjectMapper objectMapper) {
        this.employeeService = employeeService;
        this.objectMapper = objectMapper;
    }

    // expose "/employees" and return a list of employees
    @GetMapping("/employees")
    public List<Employee> findAll() {
        return this.employeeService.findAll();
    }

    // add mapping for GET /employee/{employeeId}
    @GetMapping("/employees/{employeeId}")
    public Employee getEmployee(@PathVariable int employeeId) {

        Employee theEmployee = this.employeeService.findById(employeeId);

        if (theEmployee == null) {
            throw new RuntimeException("Employee id not found - " + employeeId);
        }

        return theEmployee;
    }

    // add mapping for POST /employees - add new employee
    @PostMapping("/employees")
    public Employee addEmployee(@RequestBody Employee theEmployee) {

        // also just in case they pass an id in JSON ... set id to 0
        // this is to force a save of new item ... instead of update
        theEmployee.setId(0);

        Employee dbEmployee = this.employeeService.save(theEmployee);

        return dbEmployee;
    }

    // add mapping for PUT /employees - update existing employee
    @PutMapping("/employees")
    public Employee updateEmployee(@RequestBody Employee theEmployee) {

        Employee dbEmployee = this.employeeService.save(theEmployee);

        return dbEmployee;
    }

    // add mapping for PATCH /employees/{employeeId} - patch employee ... partial update
    @PatchMapping("/employees/{employeeId}")
    public Employee patchEmployee(@PathVariable int employeeId,
                                  // JSON data is passed in as p Map of Key - value pairs
                                  @RequestBody Map<String, Object> patchPayLoad) {

        // retrieve data from the database using employee ID
        Employee tempEmployee = this.employeeService.findById(employeeId);

        // throw exception if null
        if (tempEmployee == null) {
            throw new RuntimeException("Employee id not found - " + employeeId);
        }

        // throw exception if request body contains "id" key
        if (patchPayLoad.containsKey("id")) {
            throw new RuntimeException("Employee id not allowed in request body - " + employeeId);
        }

        // Apply patch payload to employee
        Employee patchedEmployee = apply(patchPayLoad, tempEmployee);

        // Save it into the database
        Employee dbEmployee = this.employeeService.save(patchedEmployee);

        return dbEmployee;
    }

    private Employee apply(Map<String, Object> patchPayLoad, Employee tempEmployee) {

        // Convert data to JSON object nodes
        ObjectNode employeeNode = this.objectMapper.convertValue(tempEmployee, ObjectNode.class);

        // Convert the patchPayload map to a JSON object node
        ObjectNode patchNode = this.objectMapper.convertValue(patchPayLoad, ObjectNode.class);

        // Merge the patch updates into the employee node
        employeeNode.setAll(patchNode);

        // Convert JSON object node back to Employee object
        return this.objectMapper.convertValue(employeeNode, Employee.class);
    }

    // add mapping for DELETE /employee/{employeeId} -delete employee
    @DeleteMapping("/employees/{employeeId}")
    public String deleteEmployee(@PathVariable int employeeId) {

        // Check if the employee exist in the database
        Employee tempEmployee = this.employeeService.findById(employeeId);

        // throw exception if null
        if (tempEmployee == null) {
            throw new RuntimeException("Employee id not found - " + employeeId);
        }

        this.employeeService.deleteById(employeeId);

        return "Deleted employee id - " + employeeId;
    }
}
