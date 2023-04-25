package com.g1app.engine.controller;

import com.g1app.engine.base.ResponseStatusCode;
import com.g1app.engine.base.ResponseStatusMessages;
import com.g1app.engine.models.*;
import com.g1app.engine.repositories.*;
import com.g1app.engine.security.AuthScope;
import com.g1app.engine.security.JwtTokenProvider;
import com.g1app.engine.userdirectory.request.*;
import com.g1app.engine.userdirectory.response.*;
import com.g1app.engine.utils.CommonConstant;
import com.g1app.engine.utils.UrlConstant;
import com.g1app.engine.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(UrlConstant.BOOKING)
public class BookingController {

    @Autowired
    JwtTokenProvider tokenProvider;
    @Autowired
    AddressMasterRepository addressMasterRepository;
    @Autowired
    PackageMasterRepository packageMasterRepository;
    @Autowired
    TestMasterRepository testMasterRepository;
    @Autowired
    ProfileMasterRepository profileMasterRepository;
    @Autowired
    OrderMasterRepository orderMasterRepository;
    @Autowired
    OrderAddressMasterRepository orderAddressMasterRepository;
    @Autowired
    OrderItemRepository orderItemRepository;
    @Autowired
    FamilyMembersRepository familyMembersRepository;
    @Autowired
    UserRepository repository;

    @PostMapping(UrlConstant.SAVE_ADDRESS)
    public ResponseEntity<ResponseAddressId> addUserAddress(@RequestBody AddressMaster request,
                                                            @RequestHeader("Authorization") String token) {
        ResponseAddressId response = new ResponseAddressId();
        if (token == null || token.isEmpty()) {
            response.message = ResponseStatusMessages.INVALID_TOKEN.getValue();
            response.statusCode = ResponseStatusCode.INVALID_TOKEN.getValue();
        }
        String bearer = tokenProvider.resolveToken(token);

        if (tokenProvider.validateToken(bearer, AuthScope.USER)) {
            UUID customerId = UUID.fromString(tokenProvider.getUsername(bearer));
            UUID addressId = UUID.randomUUID();
            if (request.getAddressId() == null) {
                request.setAddressId(addressId);

            }
            if (request.getCustomerId() == null) {
                request.setCustomerId(customerId);
            }
            addressMasterRepository.save(request);

            response.addressId = addressId;
            response.message = ResponseStatusMessages.SUCCESS.getValue();
            response.statusCode = ResponseStatusCode.SUCCESS.getValue();
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(UrlConstant.GET_USER_ADDRESS)
    public ResponseEntity<ResponseAddressList> getUserAddress(@RequestHeader("Authorization") String token) {
        ResponseAddressList response = new ResponseAddressList();
        if (token == null || token.isEmpty()) {
            response.message = ResponseStatusMessages.INVALID_TOKEN.getValue();
            response.statusCode = ResponseStatusCode.INVALID_TOKEN.getValue();
        }
        String bearer = tokenProvider.resolveToken(token);

        if (tokenProvider.validateToken(bearer, AuthScope.USER)) {
            UUID customerId = UUID.fromString(tokenProvider.getUsername(bearer));

            List<AddressMaster> ls = addressMasterRepository.findByCustomerId(customerId);
            for (AddressMaster am : ls) {
                ResponseAddressInfo info = new ResponseAddressInfo();
                info.addressLine1 = am.getAddressLine1();
                info.addressLine2 = am.getAddressLine2();
                info.addressType = am.getAddressType();
                info.geo_address = am.getGeoAddress();
                info.isPrimaryAddress = am.isPrimaryAdd;
                info.city = am.getCity();
                info.landmark = am.getLandmark();
                info.latitude = am.getLatitude();
                info.longitude = am.getLongitude();
                info.pincode = am.getPincode();
                info.addressId = am.getAddressId();
                response.addressList.add(info);

            }
            response.message = ResponseStatusMessages.SUCCESS.getValue();
            response.statusCode = ResponseStatusCode.SUCCESS.getValue();
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(UrlConstant.DELETE_ADDRESS)
    public ResponseEntity<ResponseMessage> deleteAddress(@RequestBody RequestAddressId body,
                                                         @RequestHeader("Authorization") String token) {
        ResponseMessage response = new ResponseMessage();

        if (token == null || token.isEmpty()) {
            response.message = ResponseStatusMessages.INVALID_TOKEN.getValue();
            response.statusCode = ResponseStatusCode.INVALID_TOKEN.getValue();
        }
        String bearer = tokenProvider.resolveToken(token);

        if (tokenProvider.validateToken(bearer, AuthScope.USER)) {
            UUID customerId = UUID.fromString(tokenProvider.getUsername(bearer));

            if (body.addressId == null) {
                response.message = ResponseStatusMessages.MISSING_PARAMETER.getValue();
                response.statusCode = ResponseStatusCode.WRONG_INPUT.getValue();
            }
            AddressMaster addressMaster = addressMasterRepository.findByAddressId(body.addressId);
            if (addressMaster != null) {
                addressMasterRepository.deleteById(body.addressId);
                response.message = ResponseStatusMessages.SUCCESS.getValue();
                response.statusCode = ResponseStatusCode.SUCCESS.getValue();
            }
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(UrlConstant.GET_BOOKING_SLOTS)
    public ResponseEntity<ResponseSlots> getSlots() {
        ResponseSlots response = new ResponseSlots();

        LocalDate localDate = LocalDate.now();
        long timeLimit = 223000;
        String str = String.valueOf(LocalTime.now());
        String result = str.substring(0, str.indexOf("."));
        String currentTime = result.replaceAll("[-+^:.]*", "");
        long value = Long.parseLong(currentTime);

        LocalDate startDate;
        LocalDate endDate;

        if (value >= timeLimit) {
            startDate = localDate.plusDays(2);
            endDate = localDate.plusDays(17);
        } else {
            startDate = localDate.plusDays(1);
            endDate = localDate.plusDays(15);
        }
        response.fromDate = String.valueOf(startDate);
        response.toDate = String.valueOf(endDate);

        response.slots1 = CommonConstant.TIME_SLOT_1;
        response.slots2 = CommonConstant.TIME_SLOT_2;
        response.slots3 = CommonConstant.TIME_SLOT_3;
        response.slots4 = CommonConstant.TIME_SLOT_4;
        response.slots5 = CommonConstant.TIME_SLOT_5;
        response.slots6 = CommonConstant.TIME_SLOT_6;
        response.slots7 = CommonConstant.TIME_SLOT_7;
        response.slots8 = CommonConstant.TIME_SLOT_8;
        response.slots9 = CommonConstant.TIME_SLOT_9;

        response.message = ResponseStatusMessages.SUCCESS.getValue();
        response.statusCode = ResponseStatusCode.SUCCESS.getValue();


        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping(UrlConstant.ORDER_BOOKING)
    public ResponseEntity<ResponseOrderBooking> order(@RequestBody RequestOrderBooking body,
                                                      @RequestHeader("Authorization") String token) {
        ResponseOrderBooking response = new ResponseOrderBooking();
        if (token == null || token.isEmpty()) {
            response.message = ResponseStatusMessages.INVALID_TOKEN.getValue();
            response.statusCode = ResponseStatusCode.INVALID_TOKEN.getValue();
        }

        String bearer = tokenProvider.resolveToken(token);
        long date = body.appointmentDate;
        String time = body.appointmentTimeSlot;

        if (tokenProvider.validateToken(bearer, AuthScope.USER)) {
            UUID customerId = UUID.fromString(tokenProvider.getUsername(bearer));

            if (body.addressId != null && customerId != null) {
                if (!checkAddress(customerId, body.addressId)) {
                    response.message = "Address not found";
                    response.statusCode = -7;
                    return new ResponseEntity<>(response, HttpStatus.OK);
                }
            }

            if (body.items.isEmpty()) {
                response.message = "No Order Found";
                response.statusCode = -7;
                return new ResponseEntity<>(response, HttpStatus.OK);

            } else {
                // UUID orderId = UUID.randomUUID();
                String visibleOrderId = Utils.OrderId();
                UUID bookingReferenceId = UUID.randomUUID();

                for (ResponseOrderInfo ri : body.items) {
                    boolean isCombo;
                    UUID itemId = ri.id;
                    String type = ri.type;
                    float price = ri.amount;

                    List<UUID> member = ri.members;
                    for (UUID id : member) {
                        UUID orderCustomerId = id;
                        if (type.equalsIgnoreCase("package")) {

                            AddressMaster addressMaster = addressMasterRepository.findByAddressId(body.addressId);
                            OrderAddressMaster orderAdd = new OrderAddressMaster();
                            orderAdd.setAddressId(UUID.randomUUID());
                            orderAdd.setAddressLine1(addressMaster.getAddressLine1());
                            orderAdd.setAddressLine2(addressMaster.getAddressLine2());
                            orderAdd.setAddressType(addressMaster.getAddressType());
                            orderAdd.setCity(addressMaster.getCity());
                            orderAdd.setGeoAddress(addressMaster.getGeoAddress());
                            orderAdd.setLandmark(addressMaster.getLandmark());
                            orderAdd.setLatitude(addressMaster.getLatitude());
                            orderAdd.setLongitude(addressMaster.getLongitude());
                            orderAdd.setPrimaryAdd(addressMaster.isPrimaryAdd);
                            orderAdd.setPincode(addressMaster.getPincode());
                            orderAdd.setMapStaticUrl(addressMaster.getMapStaticUrl());
                            orderAdd.setCustomerId(orderCustomerId);

                            PackageMaster pm = packageMasterRepository.findByPackageId(itemId);

                            OrderMaster orderMaster = new OrderMaster();
                            orderMaster.setOrderId(UUID.randomUUID());
                            orderMaster.setCouponCode(body.couponCode);
                            orderMaster.setOrderDateTime(System.currentTimeMillis());
                            orderMaster.setPickupDateTime(body.appointmentDate);
                            orderMaster.setVisibleOrderId(String.valueOf(UUID.randomUUID()));
                            orderMaster.setCustomerId(orderCustomerId);
                            orderMaster.setBookingReference(bookingReferenceId);
                            orderMaster.setTotalOrderAmount(pm.getPackagePrice());
                            orderMaster.setAddressId(orderAdd.getAddressId());
                            orderMaster.setBookedBy(customerId);

                            OrderItems orderItems = new OrderItems();
                            orderItems.setBookedForCustomerId(orderCustomerId);
                            orderItems.setOrderId(orderMaster.getOrderId());
                            orderItems.setOrderItemsId(UUID.randomUUID());
                            orderItems.setItemMrp(pm.getPackagePrice());
                            orderItems.setItemDiscount(pm.getPackageDiscountValue());
                            orderItems.setItemId(itemId);
                            orderItems.setBookedForCustomerId(orderCustomerId);
                            orderItems.setItemType(type);
                            orderItems.setItemAmount(price);
                            orderItems.setVisibleOrderId(orderMaster.getVisibleOrderId());

                            orderAddressMasterRepository.save(orderAdd);

                            orderMasterRepository.save(orderMaster);
                            orderItemRepository.save(orderItems);
                        }
                        else if (type.equalsIgnoreCase("profile")) {
                            AddressMaster addressMaster = addressMasterRepository.findByAddressId(body.addressId);
                            OrderAddressMaster orderAdd = new OrderAddressMaster();
                            orderAdd.setAddressId(UUID.randomUUID());
                            orderAdd.setAddressLine1(addressMaster.getAddressLine1());
                            orderAdd.setAddressLine2(addressMaster.getAddressLine2());
                            orderAdd.setAddressType(addressMaster.getAddressType());
                            orderAdd.setCity(addressMaster.getCity());
                            orderAdd.setGeoAddress(addressMaster.getGeoAddress());
                            orderAdd.setLandmark(addressMaster.getLandmark());
                            orderAdd.setLatitude(addressMaster.getLatitude());
                            orderAdd.setLongitude(addressMaster.getLongitude());
                            orderAdd.setPrimaryAdd(addressMaster.isPrimaryAdd);
                            orderAdd.setPincode(addressMaster.getPincode());
                            orderAdd.setMapStaticUrl(addressMaster.getMapStaticUrl());
                            orderAdd.setCustomerId(orderCustomerId);


                            ProfileMaster proMaster = profileMasterRepository.findByProfileId(itemId);

                            OrderMaster orderMaster = new OrderMaster();
                            orderMaster.setOrderId(UUID.randomUUID());
                            orderMaster.setCouponCode(body.couponCode);
                            orderMaster.setOrderDateTime(System.currentTimeMillis());
                            orderMaster.setPickupDateTime(body.appointmentDate);
                            orderMaster.setVisibleOrderId(String.valueOf(UUID.randomUUID()));
                            orderMaster.setCustomerId(orderCustomerId);
                            orderMaster.setBookingReference(bookingReferenceId);
                            orderMaster.setTotalOrderAmount(proMaster.getProfilePrice());
                            orderMaster.setAddressId(orderAdd.getAddressId());
                            orderMaster.setBookedBy(customerId);

                            OrderItems orderItems = new OrderItems();
                            orderItems.setBookedForCustomerId(orderCustomerId);
                            orderItems.setOrderId(orderMaster.getOrderId());
                            orderItems.setOrderItemsId(UUID.randomUUID());
                            orderItems.setItemMrp(proMaster.getProfilePrice());
                            orderItems.setItemDiscount(proMaster.getProfileDiscountValue());
                            orderItems.setItemId(itemId);
                            orderItems.setBookedForCustomerId(orderCustomerId);
                            orderItems.setItemType(type);
                            orderItems.setItemAmount(price);
                            orderItems.setVisibleOrderId(orderMaster.getVisibleOrderId());

                            orderAddressMasterRepository.save(orderAdd);

                            orderMasterRepository.save(orderMaster);
                            orderItemRepository.save(orderItems);
                        }
                        else if (type.equalsIgnoreCase("test")) {

                            AddressMaster addressMaster = addressMasterRepository.findByAddressId(body.addressId);
                            OrderAddressMaster orderAdd = new OrderAddressMaster();
                            orderAdd.setAddressId(UUID.randomUUID());
                            orderAdd.setAddressLine1(addressMaster.getAddressLine1());
                            orderAdd.setAddressLine2(addressMaster.getAddressLine2());
                            orderAdd.setAddressType(addressMaster.getAddressType());
                            orderAdd.setCity(addressMaster.getCity());
                            orderAdd.setGeoAddress(addressMaster.getGeoAddress());
                            orderAdd.setLandmark(addressMaster.getLandmark());
                            orderAdd.setLatitude(addressMaster.getLatitude());
                            orderAdd.setLongitude(addressMaster.getLongitude());
                            orderAdd.setPrimaryAdd(addressMaster.isPrimaryAdd);
                            orderAdd.setPincode(addressMaster.getPincode());
                            orderAdd.setMapStaticUrl(addressMaster.getMapStaticUrl());
                            orderAdd.setCustomerId(orderCustomerId);


                            TestMaster tm = testMasterRepository.findByTestId(itemId);

                            OrderMaster orderMaster = new OrderMaster();
                            orderMaster.setOrderId(UUID.randomUUID());
                            orderMaster.setCouponCode(body.couponCode);
                            orderMaster.setOrderDateTime(System.currentTimeMillis());
                            orderMaster.setPickupDateTime(body.appointmentDate);
                            orderMaster.setVisibleOrderId(String.valueOf(UUID.randomUUID()));
                            orderMaster.setCustomerId(orderCustomerId);
                            orderMaster.setBookingReference(bookingReferenceId);
                            orderMaster.setTotalOrderAmount(tm.getTestPrice());
                            orderMaster.setAddressId(orderAdd.getAddressId());
                            orderMaster.setBookedBy(customerId);

                            OrderItems orderItems = new OrderItems();
                            orderItems.setBookedForCustomerId(orderCustomerId);
                            orderItems.setOrderId(orderMaster.getOrderId());
                            orderItems.setOrderItemsId(UUID.randomUUID());
                            orderItems.setItemMrp(tm.getTestPrice());
                            orderItems.setItemDiscount(tm.getTestDiscountValue());
                            orderItems.setItemId(itemId);
                            orderItems.setBookedForCustomerId(orderCustomerId);
                            orderItems.setItemType(type);
                            orderItems.setItemAmount(price);
                            orderItems.setVisibleOrderId(orderMaster.getVisibleOrderId());

                            orderAddressMasterRepository.save(orderAdd);

                            orderMasterRepository.save(orderMaster);
                            orderItemRepository.save(orderItems);
                        }
                    }

                }
                // response.orderId = orderId;
                response.visibleOrderId = visibleOrderId;
                response.referenceId = bookingReferenceId;

            }
            response.message = "Order Confirmed";
            response.statusCode = 1;


        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(UrlConstant.GET_ONGOING_ORDER)
    public ResponseEntity<ResponseOngoingOrderDetails> getOngoingOrderHistory(@RequestBody RequestByUUID body,
                                                                @RequestHeader("Authorization") String token) {
        ResponseOngoingOrderDetails response = new ResponseOngoingOrderDetails();
        if (token == null || token.isEmpty()) {
            response.message = ResponseStatusMessages.INVALID_TOKEN.getValue();
            response.statusCode = ResponseStatusCode.INVALID_TOKEN.getValue();
        }

        String bearer = tokenProvider.resolveToken(token);
        if (tokenProvider.validateToken(bearer, AuthScope.USER)) {
            UUID parentCustomerId = UUID.fromString(tokenProvider.getUsername(bearer));
            UUID customerId = body.value;

            List<FamilyMember> ls = familyMembersRepository.findByParentUserID(parentCustomerId);
            if (!ls.isEmpty()) {
                for (FamilyMember fm : ls) {
                    UUID custId = fm.getCustomerID();
                    List<OrderMaster> orderMaster = orderMasterRepository.findByCustomerIdAndBookedBy(custId, parentCustomerId);
                    if (!orderMaster.isEmpty()) {
                        for (OrderMaster om : orderMaster) {
                            if(om.getAmountReceived() == 0){
                                response.editOrder = true;
                                response.cancelOrder = true;
                            }
                            if(om.getOrderStatus() == 1 && om.getOrderStatus()!= 1){
                                response.editOrder = true;
                                response.cancelOrder = true;
                            }
                            UUID addressId = om.getAddressId();
                            OrderAddressMaster orderAddressMaster = orderAddressMasterRepository.findByAddressId(addressId);
                            ResponseOngoingOrderInfo info = new ResponseOngoingOrderInfo();
                            info.addressLine1 = orderAddressMaster.getAddressLine1();
                            info.addressLine2 = orderAddressMaster.getAddressLine2();
                            info.cityName = orderAddressMaster.getCity();
                            info.pincode = orderAddressMaster.getPincode();

                            User u = repository.findByCustomerID(customerId);
                            info.userName = u.getFirstName() + " " + u.getLastName();
                            UUID orderID = om.getOrderId();

                            // OrderMaster orderM = orderMasterRepository.orderDetail(customerId);
                            OrderMaster orderM = orderMasterRepository.findByOrderId(orderID);
                            if (orderM != null) {
                                // if(orderM !=null) {
                                if (orderM.getOrderStatus() <= 11) {
                                    UUID orderId = orderM.getOrderId();
                                    UUID referenceId = orderM.getBookingReference();
                                    info.orderAmount = orderM.getTotalOrderAmount();
                                    info.date = orderM.getOrderDateTime();
                                    info.orderID = orderId;
                                    info.orderPlacedAt = orderM.getOrderDateTime();
                                    info.paymentStatus = orderM.getPaymentStatus();

                                    OrderItems oi = orderItemRepository.findByOrderId(orderId);
                                    info.itemType = oi.getItemType();
                                    info.itemId = oi.getItemId();
                                    if (info.itemType.equalsIgnoreCase("package")) {
                                        PackageMaster pm = packageMasterRepository.findByPackageId(info.itemId);
                                        if (pm != null) {
                                            info.itemName = pm.getPackageTags();
                                        }
                                    } else if (info.itemType.equalsIgnoreCase("test")) {
                                        TestMaster tm = testMasterRepository.findByTestId(info.itemId);
                                        if (tm != null) {
                                            info.itemName = tm.getTestTags();
                                        }
                                    } else if (info.itemType.equalsIgnoreCase("profile")) {
                                        ProfileMaster pf = profileMasterRepository.findByProfileId(info.itemId);
                                        if (pf != null) {
                                            info.itemName = pf.getProfileTags();
                                        }
                                    }
                                    response.orders.add(info);
                                    response.statusCode = 1;
                                    response.message = "success";
                                }
                            }
                        }

                    }
                }
            }
        }
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping(UrlConstant.GET_ORDER_HISTORY)
    public ResponseEntity<ResponseOngoingOrderDetails> getOrderHistory(@RequestBody RequestByUUID body,
                                                                       @RequestHeader("Authorization") String token) {
        ResponseOngoingOrderDetails response = new ResponseOngoingOrderDetails();
        if (token == null || token.isEmpty()) {
            response.message = ResponseStatusMessages.INVALID_TOKEN.getValue();
            response.statusCode = ResponseStatusCode.INVALID_TOKEN.getValue();
        }

        String bearer = tokenProvider.resolveToken(token);
        if (tokenProvider.validateToken(bearer, AuthScope.USER)) {
            UUID parentCustomerId = UUID.fromString(tokenProvider.getUsername(bearer));
            UUID customerId = body.value;

            List<FamilyMember> ls = familyMembersRepository.findByParentUserID(parentCustomerId);
            if (!ls.isEmpty()) {
                for (FamilyMember fm : ls) {
                    UUID custId = fm.getCustomerID();
                    List<OrderMaster> orderMaster = orderMasterRepository.findByCustomerIdAndBookedBy(custId,parentCustomerId);
                    if (!orderMaster.isEmpty()) {
                        for (OrderMaster om : orderMaster) {
                            UUID addressId = om.getAddressId();
                            UUID orderID = om.getOrderId();
                            ResponseOngoingOrderInfo info = new ResponseOngoingOrderInfo();
                            OrderMaster orderM = orderMasterRepository.findByOrderId(orderID);
                            if (orderM != null) {
                                if (orderM.getOrderStatus() > 11) {
                                    UUID orderId = orderM.getOrderId();
                                    UUID referenceId = orderM.getBookingReference();
                                    info.orderAmount = orderM.getTotalOrderAmount();
                                    info.date = orderM.getOrderDateTime();
                                    info.orderID = orderId;
                                    info.orderPlacedAt = orderM.getOrderDateTime();
                                    info.paymentStatus = orderM.getPaymentStatus();

                                    OrderItems oi = orderItemRepository.findByOrderId(orderId);
                                    info.itemType = oi.getItemType();
                                    info.itemId = oi.getItemId();
                                    if (info.itemType.equalsIgnoreCase("package")) {
                                        PackageMaster pm = packageMasterRepository.findByPackageId(info.itemId);
                                        if (pm != null) {
                                            info.itemName = pm.getPackageTags();
                                        }
                                    } else if (info.itemType.equalsIgnoreCase("test")) {
                                        TestMaster tm = testMasterRepository.findByTestId(info.itemId);
                                        if (tm != null) {
                                            info.itemName = tm.getTestTags();
                                        }
                                    } else if (info.itemType.equalsIgnoreCase("profile")) {
                                        ProfileMaster pf = profileMasterRepository.findByProfileId(info.itemId);
                                        if (pf != null) {
                                            info.itemName = pf.getProfileTags();
                                        }
                                    }
                                    User u = repository.findByCustomerID(customerId);
                                    info.userName = u.getFirstName() +" "+u.getLastName();

                                    OrderAddressMaster orderAddressMaster = orderAddressMasterRepository.findByAddressId(addressId);
                                    info.addressLine1 = orderAddressMaster.getAddressLine1();
                                    info.addressLine2 = orderAddressMaster.getAddressLine2();
                                    info.cityName = orderAddressMaster.getCity();
                                    info.pincode = orderAddressMaster.getPincode();
                                    response.orders.add(info);
                                    response.statusCode = 1;
                                    response.message = "success";
                                }else{
                                    response.message= "No Order found";
                                    response.statusCode = -7;
                                }
                            }
                        }
                    }
                }
            }
        }else{
            response.message = ResponseStatusMessages.INVALID_TOKEN.getValue();
            response.statusCode = ResponseStatusCode.INVALID_TOKEN.getValue();
        }
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping(UrlConstant.GET_ORDER_DETAILS)
    public ResponseEntity<ResponseGetOrderDetails> getOrderDetails(@RequestBody RequestByUUID body,
                                                                   @RequestHeader("Authorization") String token) {
        ResponseGetOrderDetails response = new ResponseGetOrderDetails();
        if (token == null || token.isEmpty()) {
            response.message = ResponseStatusMessages.INVALID_TOKEN.getValue();
            response.statusCode = ResponseStatusCode.INVALID_TOKEN.getValue();
        }

        String bearer = tokenProvider.resolveToken(token);
        if (tokenProvider.validateToken(bearer, AuthScope.USER)) {
            UUID parentCustomerId = UUID.fromString(tokenProvider.getUsername(bearer));
            UUID orderId = body.value;

            OrderMaster orderMaster = orderMasterRepository.findByOrderId(orderId);

            if(orderMaster != null) {
                UUID customerId = orderMaster.getCustomerId();
                User u  = repository.findByCustomerID(customerId);
                if(u!=null) {
                    response.username = u.getFirstName() + " " + u.getLastName();
                }
                response.orderDate = orderMaster.getOrderDateTime();
                response.orderId = orderMaster.getOrderId();
                response.totalAmount = orderMaster.getTotalOrderAmount();

                UUID addressID = orderMaster.getAddressId();
                if(addressID!=null){
                    OrderAddressMaster ordAddMaster = orderAddressMasterRepository.findByAddressId(addressID);
                    response.address1 = ordAddMaster.getAddressLine1();
                    response.address2 = ordAddMaster.getAddressLine2();
                    response.landmark = ordAddMaster.getLandmark();
                    response.pincode = ordAddMaster.getPincode();
                }
//                OrderItems oi = orderItemRepository.findByOrderId(orderId);
//                if(oi!=null){
//
//                }
                response.message=ResponseStatusMessages.SUCCESS.getValue();
                response.statusCode = ResponseStatusCode.SUCCESS.getValue();
            }
        }else{
            response.message=ResponseStatusMessages.INVALID_TOKEN.getValue();
            response.statusCode = ResponseStatusCode.INVALID_TOKEN.getValue();
        }
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/editOrder")
    public ResponseEntity<ResponseOrderBooking> editOrder(@RequestBody RequestEditOrderBooking body,
                                                      @RequestHeader("Authorization") String token) {
        ResponseOrderBooking response = new ResponseOrderBooking();
        if (token == null || token.isEmpty()) {
            response.message = ResponseStatusMessages.INVALID_TOKEN.getValue();
            response.statusCode = ResponseStatusCode.INVALID_TOKEN.getValue();
        }

        String bearer = tokenProvider.resolveToken(token);
        if (tokenProvider.validateToken(bearer, AuthScope.USER)) {
            UUID customerId = UUID.fromString(tokenProvider.getUsername(bearer));

            if (body.addressId != null && customerId != null) {
                if (!checkAddress(customerId, body.addressId)) {
                    response.message = "Address not found";
                    response.statusCode = -7;
                    return new ResponseEntity<>(response, HttpStatus.OK);
                }
            }
            if (body.items.isEmpty()) {
                response.message = "No Order Found";
                response.statusCode = -7;
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                String visibleOrderId = Utils.OrderId();
                UUID bookingReferenceId = UUID.randomUUID();

                for (ResponseOrderInfo ri : body.items) {
                    boolean isCombo;
                    UUID itemId = ri.id;
                    String type = ri.type;
                    float price = ri.amount;

                    List<UUID> member = ri.members;
                    for (UUID id : member) {
                        UUID orderCustomerId = id;

                        OrderAddressMaster orderAdd = orderAddressMasterRepository.findByAddressId(body.addressId);
                        orderAdd.setAddressLine1(orderAdd.getAddressLine1());
                        orderAdd.setAddressLine2(orderAdd.getAddressLine2());
                        orderAdd.setAddressType(orderAdd.getAddressType());
                        orderAdd.setCity(orderAdd.getCity());
                        orderAdd.setGeoAddress(orderAdd.getGeoAddress());
                        orderAdd.setLandmark(orderAdd.getLandmark());
                        orderAdd.setLatitude(orderAdd.getLatitude());
                        orderAdd.setLongitude(orderAdd.getLongitude());
                        orderAdd.setPrimaryAdd(orderAdd.isPrimaryAdd);
                        orderAdd.setPincode(orderAdd.getPincode());
                        orderAdd.setMapStaticUrl(orderAdd.getMapStaticUrl());
                        orderAdd.setCustomerId(orderCustomerId);

                        if (type.equalsIgnoreCase("package")) {

                            PackageMaster pm = packageMasterRepository.findByPackageId(itemId);
                            OrderMaster orderMaster = orderMasterRepository.findByOrderId(body.orderId);
                            orderMaster.setCouponCode(body.couponCode);
                            orderMaster.setOrderDateTime(System.currentTimeMillis());
                            orderMaster.setPickupDateTime(body.appointmentDate);
                            orderMaster.setVisibleOrderId(String.valueOf(UUID.randomUUID()));
                            orderMaster.setCustomerId(orderCustomerId);
                            orderMaster.setBookingReference(bookingReferenceId);
                            orderMaster.setTotalOrderAmount(pm.getPackagePrice());
                            orderMaster.setAddressId(orderAdd.getAddressId());
                            orderMaster.setBookedBy(customerId);

                            OrderItems orderItems = orderItemRepository.findByOrderId(body.orderId);
                            orderItems.setBookedForCustomerId(orderCustomerId);
                            orderItems.setOrderId(orderMaster.getOrderId());

                            orderItems.setItemMrp(pm.getPackagePrice());
                            orderItems.setItemDiscount(pm.getPackageDiscountValue());
                            orderItems.setItemId(itemId);
                            orderItems.setBookedForCustomerId(orderCustomerId);
                            orderItems.setItemType(type);
                            orderItems.setItemAmount(price);
                            orderItems.setVisibleOrderId(orderMaster.getVisibleOrderId());

                            orderAddressMasterRepository.save(orderAdd);
                            orderMasterRepository.save(orderMaster);
                            orderItemRepository.save(orderItems);
                        }
                        else if (type.equalsIgnoreCase("profile")) {

                            PackageMaster pm = packageMasterRepository.findByPackageId(itemId);

                            OrderMaster orderMaster = orderMasterRepository.findByOrderId(body.orderId);
                            orderMaster.setCouponCode(body.couponCode);
                            orderMaster.setOrderDateTime(System.currentTimeMillis());
                            orderMaster.setPickupDateTime(body.appointmentDate);
                            orderMaster.setVisibleOrderId(String.valueOf(UUID.randomUUID()));
                            orderMaster.setCustomerId(orderCustomerId);
                            orderMaster.setBookingReference(bookingReferenceId);
                            orderMaster.setTotalOrderAmount(pm.getPackagePrice());
                            orderMaster.setAddressId(orderAdd.getAddressId());
                            orderMaster.setBookedBy(customerId);

                            OrderItems orderItems = orderItemRepository.findByOrderId(body.orderId);
                            orderItems.setBookedForCustomerId(orderCustomerId);
                            orderItems.setOrderId(orderMaster.getOrderId());

                            orderItems.setItemMrp(pm.getPackagePrice());
                            orderItems.setItemDiscount(pm.getPackageDiscountValue());
                            orderItems.setItemId(itemId);
                            orderItems.setBookedForCustomerId(orderCustomerId);
                            orderItems.setItemType(type);
                            orderItems.setItemAmount(price);
                            orderItems.setVisibleOrderId(orderMaster.getVisibleOrderId());

                            orderAddressMasterRepository.save(orderAdd);
                            orderMasterRepository.save(orderMaster);
                            orderItemRepository.save(orderItems);
                        }
                        else if (type.equalsIgnoreCase("test")) {
                            PackageMaster pm = packageMasterRepository.findByPackageId(itemId);

                            OrderMaster orderMaster = orderMasterRepository.findByOrderId(body.orderId);
                            orderMaster.setCouponCode(body.couponCode);
                            orderMaster.setOrderDateTime(System.currentTimeMillis());
                            orderMaster.setPickupDateTime(body.appointmentDate);
                            orderMaster.setVisibleOrderId(String.valueOf(UUID.randomUUID()));
                            orderMaster.setCustomerId(orderCustomerId);
                            orderMaster.setBookingReference(bookingReferenceId);
                            orderMaster.setTotalOrderAmount(pm.getPackagePrice());
                            orderMaster.setAddressId(orderAdd.getAddressId());
                            orderMaster.setBookedBy(customerId);

                            OrderItems orderItems = orderItemRepository.findByOrderId(body.orderId);
                            orderItems.setBookedForCustomerId(orderCustomerId);
                            orderItems.setOrderId(orderMaster.getOrderId());

                            orderItems.setItemMrp(pm.getPackagePrice());
                            orderItems.setItemDiscount(pm.getPackageDiscountValue());
                            orderItems.setItemId(itemId);
                            orderItems.setBookedForCustomerId(orderCustomerId);
                            orderItems.setItemType(type);
                            orderItems.setItemAmount(price);
                            orderItems.setVisibleOrderId(orderMaster.getVisibleOrderId());

                            orderAddressMasterRepository.save(orderAdd);
                            orderMasterRepository.save(orderMaster);
                            orderItemRepository.save(orderItems);
                        }
                    }

                }
                response.visibleOrderId = visibleOrderId;
                response.referenceId = bookingReferenceId;
            }
            response.message = "Edit Order Confirmed";
            response.statusCode = 1;


        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }




    @PostMapping("/referenceId")
    public ResponseEntity<ResponseReferenceDetails> getDetailsByReference(@RequestBody RequestByReferenceId body){
       ResponseReferenceDetails response = new ResponseReferenceDetails();
        if(body!=null){
            int totalAmount = orderMasterRepository.getAmountByReference(body.referenceId);
            response.Amount = String.valueOf(totalAmount);
            response.ProductCode = "testproduct01";
            response.statusCode=1;
            response.message = "Success";

        }else{
            response.statusCode=-7;
            response.message = "Pass Valid Reference Id";
        }

        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    private boolean checkAddress(UUID customerId, UUID addressId){
       AddressMaster addressMaster = addressMasterRepository.findByCustomerIdAndAddressId(customerId,addressId);
       if(addressMaster != null){
           return true;
       }else{
        return false;
       }
    }
}
