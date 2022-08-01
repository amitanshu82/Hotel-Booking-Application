package com.upgrade.booking.services;

import com.upgrade.booking.dtos.BookingInfoDto;
import com.upgrade.booking.exceptions.DuplicateTransactionException;
import com.upgrade.booking.exceptions.InvalidPaymentMethodException;
import com.upgrade.booking.exceptions.NoBookingFoundException;
import com.upgrade.booking.models.BookingRequest;
import com.upgrade.booking.entities.BookingInfoEntity;
import com.upgrade.booking.Repositories.BookingRepository;
import com.upgrade.booking.models.TransactionRequest;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Random;


@Service
public class BookingServiceImpl implements BookingService{
    @Value("${paymentService.url}")
    private String paymentServiceUrl;

    @Value("${message.topic.name}")
    private String topic;
    String key = "transaction";

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    KafkaProducer<String,String> kafkaProducer;

    /**
     * method to save booking info to DB and returns BookingInfoDto as response
     * @param bookingRequest
     * @return
     */
    @Transactional
    @Override
    public BookingInfoDto saveBooking(BookingRequest bookingRequest) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        BookingInfoEntity booking = new BookingInfoEntity();
        booking.setFromDate(Date.valueOf(bookingRequest.getFromDate()));
        booking.setToDate(Date.valueOf(bookingRequest.getToDate()));
        booking.setAadharNumber(bookingRequest.getAadharNumber());
        booking.setNumOfRooms(bookingRequest.getNumOfRooms());
        booking.setBookedOn(Date.valueOf(LocalDate.now().format(formatter)));
        booking.setRoomNumbers(getRandomRoomNumbers(bookingRequest.getNumOfRooms()));
        booking.setRoomPrice(calculateRoomPrice(bookingRequest.getNumOfRooms(),bookingRequest.getFromDate(),bookingRequest.getToDate()));
        BookingInfoEntity bookedInfo = bookingRepository.save(booking);
        mapper.getConfiguration().setAmbiguityIgnored(true);
        BookingInfoDto response = mapper.map(booking, BookingInfoDto.class);
        response.setId(booking.getBookingId());
        return response;
    }

    /**
     * handle the request for posting payment related to a booking id. It calls Payment service and after having the
     * transactionId raise Kafka events
     * @param bookingId
     * @param transactionRequest
     * @return
     */
    @Override
    public BookingInfoDto saveTransactionForBookingId(int bookingId,TransactionRequest transactionRequest) {
        try {
            if (!transactionRequest.getPaymentMode().equals("UPI") && !transactionRequest.getPaymentMode().equals("CARD"))
                throw new InvalidPaymentMethodException();

            BookingInfoEntity bookingInfo = bookingRepository.findById(bookingId).orElseThrow(NoBookingFoundException::new);

            if (bookingInfo != null && bookingInfo.getTransactionId() != 0) {
                throw new DuplicateTransactionException();
            }
            HttpEntity<TransactionRequest> request = new HttpEntity<>(transactionRequest);
            int transactionId = restTemplate.exchange(paymentServiceUrl, HttpMethod.POST, request, Integer.class).getBody();
            bookingInfo.setTransactionId(transactionId);
            bookingRepository.save(bookingInfo);
            publishMessage(bookingInfo);
            mapper.getConfiguration().setAmbiguityIgnored(true);
            BookingInfoDto response = mapper.map(bookingInfo, BookingInfoDto.class);
            response.setId(bookingInfo.getBookingId());
            return response;
        }catch (HttpClientErrorException e){
            throw new HttpClientErrorException(e.getMessage(),e.getStatusCode(),e.getStatusText(),e.getResponseHeaders(),null,null);
        }
    }

    private void publishMessage(BookingInfoEntity bookingInfo) {
        String message = "Booking confirmed for user with aadhaar number: " + bookingInfo.getAadharNumber() +    "    |    "  + "Here are the booking details:    " + bookingInfo.toString();
        kafkaProducer.send(new ProducerRecord<String,String>(topic,key,message));
    }

    private int calculateRoomPrice(int numOfRooms, LocalDate fromDate, LocalDate toDate) {
        long noOfDaysBetween = ChronoUnit.DAYS.between(fromDate, toDate);
        return ((int)(1000* numOfRooms*noOfDaysBetween));
    }


    private String getRandomRoomNumbers(int count) {
        Random rand = new Random();
        int upperBound = 100;
        ArrayList<String>numberList = new ArrayList<String>();

        for (int i=0; i<count; i++){
            numberList.add(String.valueOf(rand.nextInt(upperBound)));
        }

        return numberList.toString().replace("]","").replace("[","");
    }
}
