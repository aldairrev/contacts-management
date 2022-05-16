package pe.aldairrev.cm.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.aldairrev.cm.models.User;
import pe.aldairrev.cm.repositories.UserRepository;

@Service
public class UserService implements IService<User, Long> {

    @Autowired
    private UserRepository repository;

    @Override
    public User create(User t) {
        User userByEmail = repository.findByEmail(t.getEmail());
        if (userByEmail != null) {
            String message = "This email already exist in our database.";
            throw new IllegalStateException(message);
        }
        return repository.create(t);
    }

    @Override
    public User delete(Long id) {
        User user = repository.findById(id);
        if (user == null) {
            String message = "User with id: " + id + ", does no exist.";
            throw new IllegalStateException(message);
        }
        boolean isDeleted = repository.deleteById(id);
        if (!isDeleted) {
            String message = "Can't delete this user";
            throw new IllegalStateException(message);
        }
        return user;
    }

    @Override
    public User find(Long id) {
        User user = repository.findById(id);
        if (user == null) {
            String message = "User with id: " + id + ", does not exist.";
            throw new IllegalStateException(message);
        }
        return user;
    }

    @Override
    public List<User> getAll() {
        return repository.findAll();
    }

    @Override
    public User update(User t) {
        User old_user = repository.findById(t.getId());
        if (old_user == null) {
            String message = "User with id " + t.getId() + ", does not exist.";
            throw new IllegalStateException(message);
        }

        User userByEmail = repository.findByEmail(t.getEmail());
        boolean isSameEmail = old_user.getEmail().equals(t.getEmail());
        if (userByEmail != null && !isSameEmail) {
            String message = "This email already exist in our database.";
            throw new IllegalStateException(message);
        }

        User userByUsername = repository.findByUsername(t.getUsername());
        boolean isSameUsername = old_user.getUsername().equals(t.getUsername());
        if (userByUsername != null && !isSameUsername) {
            String message = "This username already exist in our database.";
            throw new IllegalStateException(message);
        }
        
        return repository.update(t);
    }
}
