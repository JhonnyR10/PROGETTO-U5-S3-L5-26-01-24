package giovannilongo.PROGETTOU5S3L5260124.controllers;

import giovannilongo.PROGETTOU5S3L5260124.entities.Utente;
import giovannilongo.PROGETTOU5S3L5260124.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UtenteController {
    @Autowired
    UtenteService utenteService;

    @GetMapping("")
    public Page<Utente> getUsers(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
        return utenteService.getUser(page, size, sortBy);
    }

    @GetMapping("/{userId}")
    public Utente findById(@PathVariable long userId) {
        return utenteService.findById(userId);
    }


    @PutMapping("/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Utente getUserByIdAndUpdate(@PathVariable Long userId, @RequestBody Utente modifiedUserPayload) {
        return utenteService.findByIdAndUpdate(userId, modifiedUserPayload);
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getUserByIdAndDelete(@PathVariable Long userId) {
        utenteService.findByIdAndDelete(userId);
    }

//    @PatchMapping("/{userId}/avatar")
//    public Utente uploadAvatar(@RequestParam("avatar") MultipartFile file, @PathVariable long userId) {
//        try {
//            return usersService.uploadAvatar(userId, file);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

    // /me endpoints
    @GetMapping("/me")
    public Utente getProfile(@AuthenticationPrincipal Utente currentUser) {
        return currentUser;
    }


    @PutMapping("/me")
    public Utente getMeAndUpdate(@AuthenticationPrincipal Utente currentUser, @RequestBody Utente body) {
        return utenteService.findByIdAndUpdate(currentUser.getId(), body);
    }

    @DeleteMapping("/me")
    public void getMeAnDelete(@AuthenticationPrincipal Utente currentUser) {
        utenteService.findByIdAndDelete(currentUser.getId());
    }

}
