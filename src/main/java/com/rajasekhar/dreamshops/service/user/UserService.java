package com.rajasekhar.dreamshops.service.user;

import com.rajasekhar.dreamshops.exceptions.ResourceNotFoundException;
import com.rajasekhar.dreamshops.model.User;
import com.rajasekhar.dreamshops.repository.UserRepository;
import com.rajasekhar.dreamshops.request.CreateuserRequest;
import com.rajasekhar.dreamshops.request.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User not fount!"));
    }

    @Override
    public User createUser(CreateuserRequest request) {
        return Optional.of(request)
                .filter(user ->!=userRepository.existByEmail(request.getEmail())
                    .map(req->{
                        User user1 =new User();
                        user1.setEmail(request.getEmail());
                        user1.setFirstName(request.getFirstName());
                        user1.setLastName(request.getLastName());
                        userRepository.save(user1);
                        return userRepository.save(user1);
                    }).orElseThrow(()-> new ResourceNotFoundException("User not found!"));
                );
    }

    @Override
    public User updateUser(UserUpdateRequest request,Long userId) {
        return userRepository.findById(userId).map(existingUser->{
            existingUser.setFirstName(request.firstName);
            existingUser.getLastName(request.lastName);
            return userRepository.save(existingUser);

        }).orElseThrow(()->new ResourceNotFoundException("User not found!"))
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId).ifPresentOrElse(userRepository::delete,()->{
            throw new ResourceNotFoundException("User not found!");
        });
    }
}
