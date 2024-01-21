package com.example.securityl.controller;


import com.example.securityl.request.CreateUserRequest;
import com.example.securityl.request.UpdateUserRequest;
import com.example.securityl.response.CreateResponse;
import com.example.securityl.response.DeleteResponse;
import com.example.securityl.response.UpdateUserResponse;
import com.example.securityl.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000/", maxAge = 3600)
public class AdminController {
    private final UserService userService;
//    private static final Logger logger = (Logger) LoggerFactory.getLogger(UserController.class);

    @PostMapping("/createUser")
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<CreateResponse> create(@RequestBody CreateUserRequest request) {
        try {
            return ResponseEntity.ok(userService.createUser(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(CreateResponse.builder()
                    .status("Create staff fail")
                    .message(e.getMessage())
                    .build());
        }
    }

    @PutMapping("/updateUser/{email}")
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<UpdateUserResponse> updateStaff(
            @PathVariable String email,
            @RequestBody UpdateUserRequest updateUserRequest) {
        try {
            return ResponseEntity.ok(userService.updateUser(email, updateUserRequest).getBody());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(UpdateUserResponse.builder()
                    .status("Update fail")
                    .message(e.getMessage())
                    .build());
        }
    }

    @DeleteMapping("/delete/{email}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<DeleteResponse> deleteUser (
            @PathVariable String email){
        try{
            return ResponseEntity.ok(userService.deleteUser(email).getBody());
        }catch (Exception e){
            return ResponseEntity.badRequest().body(DeleteResponse.builder()
                            .status("Delete fail")
                            .message(e.getMessage())
                    .build());
        }
    }
}
