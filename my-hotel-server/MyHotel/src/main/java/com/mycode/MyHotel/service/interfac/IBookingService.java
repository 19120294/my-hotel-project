package com.mycode.MyHotel.service.interfac;

import com.mycode.MyHotel.dto.Response;
import com.mycode.MyHotel.entity.Booking;

public interface IBookingService {

    Response saveBooking(Long roomId, Long userId, Booking bookingRequest);

    Response findBookingByConfirmationCode(String confirmationCode);

    Response getAllBookings();

    Response cancelBooking(Long bookingId);

}