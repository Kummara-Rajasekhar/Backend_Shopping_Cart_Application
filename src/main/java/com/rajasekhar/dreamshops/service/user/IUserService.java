package com.rajasekhar.dreamshops.service.user;

import com.rajasekhar.dreamshops.model.User;
import com.rajasekhar.dreamshops.request.CreateuserRequest;
import com.rajasekhar.dreamshops.request.UserUpdateRequest;

public interface IUserService {
    
    
    User getUserById(Long userId);

    User createUser(CreateuserRequest request);
    User updateUser(UserUpdateRequest request,Long id);
    void deleteUser(Long userId);
}
