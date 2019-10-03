package com.le.app.service;

import com.le.app.dto.IrregularVerbDto;
import com.le.app.model.IrregularVerb;
import com.le.app.model.User;
import com.le.app.repository.IrregularVerbRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class IrregularVerbService {
    @Autowired
    private UserService userService;
    private final IrregularVerbRepository irregularVerbRepository;


    @Autowired
    public IrregularVerbService(IrregularVerbRepository irregularVerbRepository) {
        this.irregularVerbRepository = irregularVerbRepository;

    }

    public List<IrregularVerbDto> findAll() {

        if (!userService.isRequestFromLoginedUser()) {
            System.out.println(" after !irregularVerbService.isRequestFromLoginedUser ----------------");
            List<IrregularVerbDto> irregularVerbsDto;
            irregularVerbsDto = findAllForNotLoginedUser()
                    .stream()
                    .map(irregularVerb -> IrregularVerbDto.fromIrregularVerb(irregularVerb))
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
        User loginedUser = userService.getLoginedUser();

        List<IrregularVerb> irregularVerbsLearnt = new ArrayList<>(loginedUser.getIrregularVerbsLearnt());
        System.out.println("irregularVerbsLearnt = " + irregularVerbsLearnt.size());
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


    public Optional<IrregularVerb> findById(Long id) {
        return irregularVerbRepository.findById(id);
    }

    public IrregularVerb save(IrregularVerb irregularVerb) {
        return irregularVerbRepository.save(irregularVerb);
    }


//    public void addIrregularVerbToLearntByUser(Long id) {
//        //User loginedUser = userService.getLoginedUser();
//        User loginedUser = userService.findById(1L);
//        IrregularVerb learntIrregularVerb = findById(id).get();
//        learntIrregularVerb.addUser(loginedUser);
//
//        //loginedUser.addIrregularVerbToLearnt(irregularVerbRepository.findById(id).get());
//    }


//    public void removeIrregularVerbFromLearntByUser(Long id) {
//        //User loginedUser = userService.getLoginedUser();
//        //User loginedUser = userService.findById(1L);
//        //loginedUser.removeIrregularVerbFromLearnt(irregularVerbRepository.findById(id).get());
//        User loginedUser = userService.findById(1L);
//        IrregularVerb learntIrregularVerb = findById(id).get();
//        learntIrregularVerb.removeUser(loginedUser);
//    }

    public void saveAll(List<IrregularVerb> irregularVerbs) {
        irregularVerbRepository.saveAll(irregularVerbs);
    }

    public void deleteById(Long id) {
        irregularVerbRepository.deleteById(id);
    }


}
