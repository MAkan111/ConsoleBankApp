package ru.makan1.bankapp.service;

import org.springframework.stereotype.Component;
import ru.makan1.bankapp.model.User;
import ru.makan1.bankapp.repository.InMemoryUserRepository;

import java.util.ArrayList;

@Component
public class UserService {

    private final UserAccountService userAccountService;
    private final InMemoryUserRepository inMemoryUserRepository;

    public UserService(UserAccountService userAccountService, InMemoryUserRepository inMemoryUserRepository) {
        this.userAccountService = userAccountService;
        this.inMemoryUserRepository = inMemoryUserRepository;
    }

    public void createUser(String login) {
        if (inMemoryUserRepository.findByLogin(login) != null) {
            throw new IllegalArgumentException("Пользователь с таким логином уже есть");
        }

        User user = new User();
        user.setLogin(login);
        user.setAccountList(new ArrayList<>());

        inMemoryUserRepository.save(user);
        userAccountService.createUserAccount(login);
    }

    public void showAllUsers() {
        System.out.println(inMemoryUserRepository.findAll().toString());
    }
}
