package org.soneech.springcourse.service;

import org.soneech.springcourse.model.User;
import org.soneech.springcourse.repository.UserRepository;
import org.soneech.springcourse.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty())
            throw new UserNotFoundException("Пользователь с таким id не найден");

        return user.get();
    }

    @Transactional
    public void save(User user) {
        setInitialData(user);
        userRepository.save(user);
    }
    
    @Transactional
    public void update(Long id, User user) {
        user.setId(id);
        setUpdatedData(user);
        userRepository.save(user);
    }

    @Transactional
    public void delete(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty())
            throw new UserNotFoundException("Пользователь с таким id не найден");
        userRepository.delete(user.get());
    }

    private void setInitialData(User user) {
        user.setCreatedAt(LocalDateTime.now());
        setUpdatedData(user);
    }
    
    private void setUpdatedData(User user) {
        user.setUpdatedAt(LocalDateTime.now());
    }
}
