package org.rossijr.elasticsearchcrud.controller;

import org.rossijr.elasticsearchcrud.model.User;
import org.rossijr.elasticsearchcrud.sevice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.saveUser(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<User>> getUser(@PathVariable String id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping
    public ResponseEntity<Iterable<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }

    /**
     * <p>Find user by name</p>
     * <p>Important to note that this is not the best way of implementing this kind of query, but it is a way to example
     * the use of Elasticsearch with Spring Data Elasticsearch, but better approaches for Controller and Service architecture
     * layers should be taken into consideration.</p>
     * @param params query parameters
     * @return list of users
     */
    @GetMapping("/query")
    public ResponseEntity<Object> getUserByQuery(@RequestParam Map<String, String> params) {
        return ResponseEntity.ok(userService.searchUsersByParams(params));
    }

}
