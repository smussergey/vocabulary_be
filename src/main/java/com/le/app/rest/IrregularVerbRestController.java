package com.le.app.rest;

import com.le.app.dto.IrregularVerbDto;
import com.le.app.model.IrregularVerb;
import com.le.app.service.IrregularVerbService;
import com.le.app.service.UserService;
import com.le.app.validation.ValidationError;
import com.le.app.validation.ValidationErrorBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;


@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class IrregularVerbRestController {
    @Autowired
    UserService userService;
    private final IrregularVerbService irregularVerbService;

    @Autowired
    public IrregularVerbRestController(IrregularVerbService irregularVerbService) {
        this.irregularVerbService = irregularVerbService;
    }


    @GetMapping("/content/iv")
    public ResponseEntity<List<IrregularVerbDto>> getAllIrregularVerbs() {
        return ResponseEntity.ok(irregularVerbService.findAll());
    }


    @GetMapping("/iv/{id}")
    public ResponseEntity<IrregularVerb> findIrregularVerbById(@PathVariable Long id) {
        Optional<IrregularVerb> irregularVerbOptional = irregularVerbService.findById(id);
        if (irregularVerbOptional.isPresent()) {
            return ResponseEntity.ok(irregularVerbOptional.get());
        }
        return ResponseEntity.notFound().build(); // check where status is showed: Create a builder with a NOT_FOUND status.
    }


    @RequestMapping(path = "/iv/save/one", method = {RequestMethod.POST,
            RequestMethod.PUT})
    public ResponseEntity<?> createOrUpdate(@Valid @RequestBody IrregularVerb irregularVerb,
                                            Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().
                    body(ValidationErrorBuilder.fromBindingErrors(errors));
        }
        IrregularVerb resultIrregularVerb = irregularVerbService.save(irregularVerb);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().
                path("/{id}")
                .buildAndExpand(resultIrregularVerb.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PostMapping(path = "/iv/save/allfromfile")
    public ResponseEntity<String> getAllFromExcelFile() {
        try {
            List<IrregularVerb> irregularVerbs = null;
            irregularVerbs = irregularVerbService.getAllFromExcelFile();
            irregularVerbService.saveAll(irregularVerbs);
            return ResponseEntity.ok("Saved all from Exel file");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Can not parse Excel file");
        }
    }

    @DeleteMapping("/iv/{id}")
    public ResponseEntity<IrregularVerb> deleteById(@PathVariable Long id) {
        irregularVerbService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    @ExceptionHandler // it handles bad requests
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ValidationError handleException(Exception exception) {
        return new ValidationError(exception.getMessage());
    }
}
