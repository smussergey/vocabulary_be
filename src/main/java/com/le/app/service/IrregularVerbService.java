package com.le.app.service;

import com.le.app.model.dto.IrregularVerbDto;
import com.le.app.model.IrregularVerb;
import com.le.app.model.User;
import com.le.app.repository.IrregularVerbRepository;
import com.le.app.util.excelfilereader.IrregularVerbsExcelFileReader;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Transactional
@Service
// @Cacheable
public class IrregularVerbService {
    @Autowired
    private UserService userService;
    @Autowired
    private IrregularVerbsExcelFileReader excelFileReader;
    private final IrregularVerbRepository irregularVerbRepository;


    @Autowired
    public IrregularVerbService(IrregularVerbRepository irregularVerbRepository) {
        this.irregularVerbRepository = irregularVerbRepository;

    }

    @Transactional(readOnly = true)
    public List<IrregularVerbDto> findAll() {

        if (!userService.isRequestFromLoginedUser()) {
            System.out.println(" after !irregularVerbService.isRequestFromLoginedUser ----------------");
            List<IrregularVerbDto> irregularVerbsDto;
            irregularVerbsDto = findAllForNotLoginedUser()
                    .stream()
                    .map(IrregularVerbDto::fromIrregularVerb)
                    .collect(Collectors.toList());
            return irregularVerbsDto;
        } else {
            return findAllForLoginedUser();
        }
    }


    private List<IrregularVerb> findAllForNotLoginedUser() {
        return irregularVerbRepository.findAll();
    }

    private List<IrregularVerbDto> findAllForLoginedUser() {
        User loginedUser = userService.findLoginedUser();

        List<IrregularVerb> irregularVerbsLearnt = new ArrayList<>(loginedUser.getIrregularVerbsLearnt());
//        System.out.println("irregularVerbsLearnt = " + irregularVerbsLearnt.size());
        List<IrregularVerb> irregularVerbsAll = irregularVerbRepository.findAll();

        List<IrregularVerbDto> irregularVerbsDto = new ArrayList<>();

        for (IrregularVerb irregularVerb : irregularVerbsAll) {

            if (irregularVerbsLearnt.contains(irregularVerb)) {
                IrregularVerbDto irregularVerbDto = IrregularVerbDto.fromIrregularVerb(irregularVerb);
                irregularVerbDto.setLearnt(true);
                irregularVerbsDto.add(irregularVerbDto);
            } else irregularVerbsDto.add(IrregularVerbDto.fromIrregularVerb(irregularVerb));
        }
        return irregularVerbsDto;
    }


    @Transactional //(readOnly = true)
    public Optional<IrregularVerb> findById(Long id) {
        return irregularVerbRepository.findById(id);
    }

    @Transactional()
    public IrregularVerb save(IrregularVerb irregularVerb) {
        return irregularVerbRepository.save(irregularVerb);
    }

    @Transactional()
    public void saveAll(List<IrregularVerb> irregularVerbs) {
        irregularVerbRepository.saveAll(irregularVerbs);
    }

    @Transactional()
    public void deleteById(Long id) {
        irregularVerbRepository.deleteById(id);
    }

    public List<IrregularVerb> ParseAllFromExcelFile() {
        List<IrregularVerb> irregularVerbs = null;
        try {
            irregularVerbs = excelFileReader.parseExcelFile();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
        return irregularVerbs;
    }

}
