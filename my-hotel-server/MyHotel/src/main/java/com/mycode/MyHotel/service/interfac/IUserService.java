package com.mycode.MyHotel.service.interfac;

import com.mycode.MyHotel.dto.LoginRequest;
import com.mycode.MyHotel.dto.Response;
import com.mycode.MyHotel.entity.User;

public interface IUserService {
    Response register(User user);

    Response login(LoginRequest loginRequest);

    Response getAllUsers();

    Response getUserBookingHistory(String userId);

    Response deleteUser(String userId);

    Response getUserById(String userId);

    Response getMyInfo(String email);

}